import cv2
import numpy as np
import math
import random
import shapely.geometry
import shapely.affinity
import logging

import tensorflow
from sklearn import preprocessing
logging.basicConfig(filename='log_detectPlate.log',filemode='w', format='%(levelname)s\t%(message)s', level=logging.DEBUG)



# is can be char
CHAR_MIN_AREA = 30
CHAR_MIN_W    = 2
CHAR_MIN_H    = 8
CHAR_MIN_WHRATIO = 0.15
CHAR_MAX_WHRATIO = 1.0

# Cluster char
CLUSTER_MIN_NUM_CHAR   = 2
CLUSTER_MAX_ANGLE_CHAR = 15.0
MAX_DIFF_AREA   = 0.4
MAX_DIFF_WIDTH  = 0.6
MAX_DIFF_HEIGHT = 0.2
MAX_DISTANCE_MULTI = 3.2

# PLATE
PLATE_WIDTH_PADD_MULTI = 1.8
PLATE_HEIGHT_PADD_MULTI = 1.5


# Combine Plate
MIN_DIFF_ANGLE = -20
MAX_DIFF_ANGLE =  20

MIN_PLATE_RATIO = 0.7
MAX_PLATE_RATIO = 1.3
MIN_OVERLAP_RATIO = 0.75
MAX_OVERLAP_RATIO = 1

# Char detect
CHAR_SIZE = (28,28)




#get grayScale and threshold 
def preprocess(imgOrigin):
    
    imgHSV = cv2.cvtColor(imgOrigin, cv2.COLOR_BGR2HSV)
    hue, saturation, imgGray = cv2.split(imgHSV)
    # imgGray = cv2.cvtColor(imgOrigin, cv2.COLOR_BGR2GRAY)

    # MaximizeContrast
    structuringElement = cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3))
    imgTopHat = cv2.morphologyEx(imgGray, cv2.MORPH_TOPHAT, structuringElement)
    imgBlackHat = cv2.morphologyEx(imgGray, cv2.MORPH_BLACKHAT, structuringElement)
    imgGrayAddTopHat = cv2.add(imgGray, imgTopHat)
    imgGrayMinusBlackHat = cv2.subtract(imgGrayAddTopHat, imgBlackHat)
    imgBlurred = cv2.GaussianBlur(imgGrayMinusBlackHat, (5, 5), 0)  # GAUSSIAN_SMOOTH_FILTER_SIZE (5,5)
    imgThresh = cv2.adaptiveThreshold(imgBlurred, 255.0, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY_INV, 19, 9) #ADAPTIVE_THRESH_BLOCK_SIZE 19, ADAPTIVE_THRESH_WEIGHT 9
    

    return imgGray, imgThresh

def findCharsFromImg(imgThresh):
    """
    Input: ImgThresh\n
    Return: List possible chars. (removed noise)
    """
    listChars = []

    imgThreshCopy = imgThresh.copy()
    imgContours, contours, npaHierarchy = cv2.findContours(imgThreshCopy, cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)
    logging.debug("Num contours first: %s", len(contours) )
    
    imgContours = np.zeros((imgThresh.shape[0], imgThresh.shape[1], 3), np.uint8)
    # draw Contours:
    for i, item in enumerate(contours):

        isChar = isCanBeChar(item)
        if(isChar):
            listChars.append(isChar)
            cv2.drawContours(imgContours, contours, i, (255.0, 255.0, 255.0) )
            
    logging.debug("Len list chars after: %s", len(listChars))
    # cv2.imshow("Draw Contour", imgContours)
    # cv2.waitKey(0)

    #END DRAW CONTOUR

    return listChars



def isCanBeChar(contour):
    
    char = None
    boundRect = cv2.boundingRect(contour)
    X, Y, W, H = boundRect
    # logging.debug("%s %s %s %s", X, Y, W, H)
    boundArea = W * H
    WHRatio = float(W)/float(H)

    if(W > CHAR_MIN_W and H > CHAR_MIN_H and boundArea > CHAR_MIN_AREA and WHRatio > CHAR_MIN_WHRATIO and WHRatio < CHAR_MAX_WHRATIO):
        centerX = (X+X+W)/2.0
        centerY = (Y+Y+H)/2.0
        # diagonal= math.sqrt(W**2 + H**2)
        char = {
            "centerX": centerX,
            "centerY": centerY, 
            "position": (X, Y, W, H), 
            "boundArea": boundArea,
            "contour" : contour,
            "isChar": True
        }
        # char = (centerX, centerY, X, Y, W, H, boundArea, contour)

    return char



def findClusterChar(char, listChars, maxAngle): 
    clusterChar = []
    # numContinue = 0
    # logging.debug("listChar find CLuster: %s", listChars)
    for pChar in listChars: 
        if pChar == char:
            # numContinue += 1
            # logging.debug("Numcontinue: %s", numContinue)
            continue
        # item = pChar
        # logging.debug("possible Machingchar: %s", item["position"])

        centerX, centerY, W, H, boundArea = char["centerX"], char["centerY"],char["position"][2],char["position"][3], char["boundArea"]
        pcenterX, pcenterY, pW, pH, pboundArea = pChar["centerX"], pChar["centerY"],pChar["position"][2],pChar["position"][3], pChar["boundArea"]

        # pcenterX, pcenterY, pW, pH, pboundArea, = pChar
        # logging.debug("Char: %s %s %s %s %s", pcenterX, pcenterY, pW, pH, pboundArea)
        # Compute distance two char
        diffX = abs(pcenterX - centerX)
        diffY = abs(pcenterY - centerY)
        distanceTwoChar = math.sqrt(diffX**2 + diffY**2)
        # Angle two char
        if (diffX ==0): # can't divide by 0 => angle = 90
            angleTwoChar = 90
        else:
            angleTwoChar = math.atan(float(diffY)/float(diffX))*(180.0/math.pi)

        # Norm 2 W, H Multiple:
        maxDistance = math.sqrt(W**2 + H**2)*MAX_DISTANCE_MULTI

        # Diffirent area, w, h between two char
        diffArea  = float(abs(pboundArea - boundArea))/boundArea
        diffWidth = float(abs(pW - W))/W
        diffHeight= float(abs(pH - H))/H
        
        if(distanceTwoChar < maxDistance and angleTwoChar < maxAngle 
            and diffArea < MAX_DIFF_AREA and diffWidth < MAX_DIFF_WIDTH and diffHeight < MAX_DIFF_HEIGHT):
            
            clusterChar.append(pChar)

    return clusterChar


def findListClusterChar(listChars, minNumChar, maxAngle):
    listClusterChar = []

    for char in listChars:
        clusterChar = findClusterChar(char, listChars, maxAngle)
        clusterChar.append(char)

        if( len(clusterChar) < minNumChar ):
            continue

        listClusterChar.append(clusterChar)
        
        listCharRemain = []
        for item in listChars:
            if item not in clusterChar:
                listCharRemain.append(item)

        #recursive
        recuListClusterChar = findListClusterChar(listCharRemain, minNumChar, maxAngle)
        
        for item in recuListClusterChar:
            listClusterChar.append(item)
        
        break

    return listClusterChar

#Extract Plate
def getPlateFromClusterChar(clusterChar):

    plate = {
        'img': None,
        'imgGray': None,
        'imgThresh': None,
        'isTwoRow': False,
        'isPlate': False,
        'rrLocation': None,
        'strChars': ""
    }

    clusterChar.sort(key = lambda x: x["centerX"])
    plateCenterX = (clusterChar[0]["centerX"] + clusterChar[-1]["centerX"])/2.0
    plateCenterY = (clusterChar[0]["centerY"] + clusterChar[-1]["centerY"])/2.0
    
    plateWidth = int((clusterChar[-1]["position"][0] + clusterChar[-1]["position"][2] - clusterChar[0]["position"][0])*PLATE_WIDTH_PADD_MULTI)  # (X_END+W_END - X_FIRST)*EXTEND_AREA

    avgPlateHeight = sum(item['position'][3] for item in clusterChar) / len(clusterChar)
    
    plateHeight = int(avgPlateHeight*PLATE_HEIGHT_PADD_MULTI)

    # plate angle
    plateOpposite = clusterChar[-1]["centerY"] - clusterChar[0]["centerY"]
    plateHipote   = math.hypot(clusterChar[-1]["centerX"] - clusterChar[0]["centerX"],clusterChar[-1]["centerY"] - clusterChar[0]["centerY"] )

    plateAngle = math.asin(plateOpposite/plateHipote) *(180/math.pi)

    plate["rrLocation"] = (plateCenterX, plateCenterY, plateWidth, plateHeight, plateAngle)

    return plate

def getContour(location):
    cx, cy, w, h, angle = location
    c = shapely.geometry.box(-w/2.0, -h/2.0, w/2.0, h/2.0)
    rc = shapely.affinity.rotate(c, angle)
    return shapely.affinity.translate(rc, cx, cy)


def getRelativeArea(locationP1, locationP2):
    contour1 = getContour(locationP1)
    contour2 = getContour(locationP2)
    
    area1 = contour1.area
    area2 = contour2.area

    intersectArea = contour1.intersection(contour2).area 
    relativeA1 = intersectArea/area1
    relativeA2 = intersectArea/area2

    return relativeA1, relativeA2, area1, area2, intersectArea


def combinePlates(imgOrigin, listPlates):
    setImg = set([])
    for i, plate in enumerate(listPlates):
        for j in range(i):
            plateLocation = plate["rrLocation"]
            jplateLocation = listPlates[j]["rrLocation"]
            plateCenterX, plateCenterY, plateWidth, plateHeight, plateAngle = plateLocation
            jplateCenterX, jplateCenterY, jplateWidth, jplateHeight, jplateAngle = jplateLocation

            heightRatio = jplateHeight*1.0/plateHeight
            diffAngle = jplateAngle - plateAngle
            plaleDistanceRatio = math.hypot(jplateCenterX - plateCenterX, jplateCenterY - plateCenterY) / plateHeight

            #Check direction
            if( MIN_DIFF_ANGLE < diffAngle and diffAngle < MAX_DIFF_ANGLE and MIN_PLATE_RATIO < plaleDistanceRatio and plaleDistanceRatio < MAX_PLATE_RATIO
                and MIN_PLATE_RATIO < heightRatio and heightRatio < MAX_PLATE_RATIO ):
                
                relativeA1, relativeA2, area1, area2, intersectArea = getRelativeArea(plateLocation, jplateLocation)

                if ( MIN_OVERLAP_RATIO < relativeA1  and relativeA1 < MAX_OVERLAP_RATIO and MIN_PLATE_RATIO < relativeA2 and relativeA2 <  MIN_PLATE_RATIO ):
                    if area1 < area2:
                        setImg.add(i)
                        break
                    else:
                        setImg.add(j)
                        continue
                if intersectArea >0:
                    combineCenterX = (plateCenterX + jplateCenterX)/2
                    combineCenterY = (plateCenterY + jplateCenterY)/2
                    if plateWidth > jplateWidth:
                        combineWidth = plateWidth
                    else:
                        combineWidth = jplateWidth
                    combineHeight = plateHeight + jplateHeight
                    combineAngle = (plateAngle + jplateAngle)/2

                    plate["isTwoRow"] = True
                    plate["rrLocation"] = (combineCenterX, combineCenterY, combineWidth, combineHeight, combineAngle)
                    setImg.add(j)
                    break
    
    listIndex = list(setImg)
    listPlateReturn = []
    for i, plate in enumerate(listPlates):
        if i not in listIndex and plate["isTwoRow"]:
            plateReturn = rotationPlate(imgOrigin, plate)
            _, _, width, height, _ = plateReturn["rrLocation"]
            # shapeRatio = width/height
            if plateReturn["img"] is not None:
                listPlateReturn.append(plateReturn)
    
    return listPlateReturn



def rotationPlate(imgOrigin, plate):
    plateCenterX, plateCenterY, plateWidth, plateHeight, plateAngle = plate["rrLocation"]
    rotationMatrix = cv2.getRotationMatrix2D( (plateCenterX, plateCenterY), plateAngle, 1.0)

    imgHeight, imgWidth, _ = imgOrigin.shape
    imgRotated = cv2.warpAffine(imgOrigin, rotationMatrix, (imgWidth, imgHeight))
    imgCropped = cv2.getRectSubPix(imgRotated, (plateWidth, plateHeight), (plateCenterX, plateCenterY) )
    plate["img"] = imgCropped
    
    return plate

def getBounding(data):
    m = np.median(data)
    std = np.std(data)
    ratio = std / m
    # if ratio is small, use large size from standard deviation
    n = [1, 3][ratio < 0.05]

    return m + n * std, m - n * std

# mode = 1 for list of List, mode = 0 for list
def getEqualHeightList(listChars, mode=0):
    if mode:
        listHeight = [x[0]["position"][3] for x in listChars]
    else:
        listHeight = [x["position"][3] for x in listChars]
    
    upperBound, lowerBound = getBounding(listHeight)
    if mode:
        listChars = [x for x in listChars if x[0]["position"][3] <= upperBound and x[0]["position"][3] >= lowerBound]
    else:
        listChars = [x for x in listChars if x["position"][3] <= upperBound and x["position"][3] >= lowerBound]
    return listChars

# remove inner chars
def removeInnerChars(listOfCharsInPlate):
    for i, char1 in enumerate(listOfCharsInPlate):
        for j, char2 in enumerate(listOfCharsInPlate):
            charStart_1X, charStart_1Y = char1["position"][0], char1["position"][1]
            charEnd_1X, charEnd_1Y = char1["position"][0] + char1["position"][2], char1["position"][1] + char1["position"][3]
                
            charStart_2X, charStart_2Y = char2["position"][0], char2["position"][1]
            charEnd_2X, charEnd_2Y = char2["position"][0] + char2["position"][2], char2["position"][1] + char2["position"][3]
            if charStart_1X < charStart_2X and charStart_1Y < charStart_2Y and charEnd_1X > charEnd_2X and charEnd_1Y > charEnd_2Y:
                char2["isChar"] = False
            else:
                char2["isChar"] = True
    
    listOfChars = [char for char in listOfCharsInPlate if char["isChar"] == True]
    return listOfChars

def removeDistanceChar(charsInPlate):
    for i, char1 in enumerate(charsInPlate):
        validChar = False
        diagonalSize1 = math.hypot(char1["position"][2], char1["position"][3])
        
        for j, char2 in enumerate(charsInPlate):
            relativeDistance = math.sqrt( (char1["centerX"] - char2["centerX"])**2 + (char1["centerY"] - char2["centerY"])**2 ) / diagonalSize1
            if relativeDistance > 0 and relativeDistance < 1:
                validChar = True
        
        if not validChar:
            char1.isChar = False
            
    listOfChars = [char for char in charsInPlate if char["isChar"] == True]
    return listOfChars

def getListChars(listPlates):
    
    for plate in listPlates:
        plate["imgGray"] , plate["imgThresh"] = preprocess(plate["img"])
        
        # cv2.imshow("plate Gray", plate["imgGray"])
        # cv2.imshow("plate Thresh",plate["imgThresh"])

        adaptivePlate = cv2.adaptiveThreshold(plate["imgGray"],255,cv2.ADAPTIVE_THRESH_GAUSSIAN_C,cv2.THRESH_BINARY,11,2)
        # blurPlate = cv2.GaussianBlur(adaptivePlate, (5,5),0)
        # _, processedPlate = cv2.threshold(blurPlate,0,255,cv2.THRESH_BINARY+cv2.THRESH_OTSU)

        # cv2.imshow("adaptive", adaptivePlate)
        # cv2.imshow("BluePlate", blurPlate)
        # cv2.imshow("processedPlate", processedPlate)

        # plate["imgThresh"] = cv2.resize(plate["imgThresh"],(0, 0), fx = 1.6, fy = 1.6)
        # cv2.imshow("threash", plate["imgThresh"])
        # cv2.waitKey(0)

        listChar = findCharsFromImg(adaptivePlate)
        listChar.sort(key = lambda Char: Char["centerX"])
        logging.debug("len list char: %s", len(listChar))
        # for item in listChar:
        #     logging.debug("---> Char: %s", (item["centerX"], item["centerY"]))
        
        #20:04 15/10
        listClusterChars= findListClusterChar(listChar, 3, 10) # Min char, max angle


        if len(listClusterChars) == 0:
            continue
        print(len(listClusterChars))

        listClusterChar1 = [getEqualHeightList(x) for x in listClusterChars]
        listClusterChar2 = getEqualHeightList(listClusterChar1, mode=1)
        listClusterChar3 = [removeDistanceChar(x) for x in listClusterChar2]
        listOfCharsInPlate = [char for listChars in listClusterChar3 for char in listChars]
        listOfCharsInPlate = removeInnerChars(listOfCharsInPlate)
        
        # logging.debug("--------------> Start point:")
        # for char in listOfCharsInPlate:
        #     logging.debug("---> Char: %s", (char["centerX"], char["centerY"]))
        
        if len(listOfCharsInPlate) >= 6:
            plate["isPlate"] = True
            
            # showListOfLists(plate, listOfCharsInPlate)

            plate["strChar"] = getStrCharFromPlate(plate["imgGray"], listOfCharsInPlate)


def getStrCharFromPlate(imgThresh, listChar):
    height, width = imgThresh.shape
    listChar.sort(key = charPlace)        # sort chars
    imgThreshColor = np.zeros((height, width, 3), np.uint8)
    cv2.cvtColor(imgThresh, cv2.COLOR_GRAY2BGR, imgThreshColor) 


    listCharImg = []
    for i, currentChar in enumerate(listChar):   
        pt1 = (currentChar["position"][0], currentChar["position"][1])
        pt2 = ((currentChar["position"][0] + currentChar["position"][2]), (currentChar["position"][1] + currentChar["position"][3]))

        cv2.rectangle(imgThreshColor, pt1, pt2, (255,255,0), 2) 


        imgROI = imgThresh[pt1[1]:pt2[1], pt1[0]:pt2[0]]
        imgROIResized = cv2.resize(imgROI, CHAR_SIZE)
        # cv2.imshow("IMROI_"+ str(i),imgROI)
        
        # retreive binary image from the char images
        adaptivePlate = cv2.adaptiveThreshold(imgROIResized,255,cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY_INV,11,2)
        blurPlate = cv2.GaussianBlur(adaptivePlate, (5,5),0)
        _, im = cv2.threshold(blurPlate,0,255,cv2.THRESH_BINARY+cv2.THRESH_OTSU)
        
        listCharImg.append(im)

        # cv2.imwrite("resize_" + str(i)+ ".jpg", im)
        # cv2.imshow("img_thresh", imgThresh)

    #17/10 DONE TRUE FOR THIS
         
    # cv2.imshow("imgThreshColor", imgThreshColor)
    # cv2.waitKey(0)

    listStrChar = recognizeChar(listCharImg)

    return "".join(listStrChar)

def charPlace(char):
    return char["centerX"] + 10 * char["centerY"]


def recognizeChar(listCharImg):
    listCharImg = np.asarray(listCharImg, dtype=np.float32)

    labelEncode = preprocessing.LabelEncoder()
    labels = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']

    # labels = list("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ")
    labelEncode.fit(labels)
    
    x = tensorflow.random_normal((1,784))
    # model = CNN_Model()
    return ["0", "1"]



# def showListOfLists(possiblePlate, listOfCharsInPlate):
#     height, width, numChannels = possiblePlate["img"].shape
#     imgContours = np.zeros((height, width, 3), np.uint8)

#     intRandomBlue = random.randint(0, 255)
#     intRandomGreen = random.randint(0, 255)
#     intRandomRed = random.randint(0, 255)

#     contours = []

#     for matchingChar in listOfCharsInPlate:
#         contours.append(matchingChar["contour"])

#     cv2.drawContours(imgContours, contours, -1, (intRandomBlue, intRandomGreen, intRandomRed))

    # cv2.imshow("7.combineListOfLists", imgContours)
    # cv2.waitKey(0)


def main():
    imgOrigin = cv2.imread("testIMG/plate.jpg")
    height, width, numChannels = imgOrigin.shape
    
    imgContours = np.zeros((height, width, 3), np.uint8)

    imgGray, imgPreprocess = preprocess(imgOrigin)
    # cv2.imshow('imgPreprocess',imgPreprocess)
    # cv2.waitKey(0)

    listChars = findCharsFromImg(imgPreprocess)
    listChars = sorted(listChars, key=lambda x: x["centerX"])
    
    listClusterChars = findListClusterChar(listChars, CLUSTER_MIN_NUM_CHAR, CLUSTER_MAX_ANGLE_CHAR)

    logging.debug("Len List Cluster Char: %s", len(listClusterChars))

    imgContours = np.zeros((height, width, 3), np.uint8)


    # DRAW CLUSTER CHAR
    colors = {
        1: (0,255,255), 
        2: (255,0,255),
        3: (255,255,0),
        4: (0,0,255),
        5: (0,255,0)
    }

    for i,clusterChar in enumerate (listClusterChars):
        
        color = colors.get(i, (255,0,0))  
        
        contours = []
        for char in clusterChar:
            contours.append(char["contour"])

        cv2.drawContours(imgContours, contours, -1, color)

    # cv2.imshow("contours", imgContours)
    # cv2.imwrite("imgcontour.jpg", imgContours)
    # cv2.waitKey(0)
    #End draw contours

    listPlates = []
    for clusterChar in listClusterChars:
        plate = getPlateFromClusterChar(clusterChar)
        listPlates.append(plate)

    #12/10
    listPlates = combinePlates(imgOrigin, listPlates)
    print ("Len list plate:", len(listPlates))
    logging.info("Len list plate: %s", len(listPlates))
    # print len(listPlates)
    for i in range(0, len(listPlates)):
        location = ((listPlates[i]["rrLocation"][0], listPlates[i]["rrLocation"][1]), (listPlates[i]["rrLocation"][2], listPlates[i]["rrLocation"][3]), listPlates[i]["rrLocation"][4])
        p2fRectPoints = cv2.boxPoints(location)

        cv2.line(imgContours, tuple(p2fRectPoints[0]), tuple(p2fRectPoints[1]), (0,255,255), 2)
        cv2.line(imgContours, tuple(p2fRectPoints[1]), tuple(p2fRectPoints[2]), (0,255,255), 2)
        cv2.line(imgContours, tuple(p2fRectPoints[2]), tuple(p2fRectPoints[3]), (0,255,255), 2)
        cv2.line(imgContours, tuple(p2fRectPoints[3]), tuple(p2fRectPoints[0]), (0,255,255), 2)

        # cv2.imshow("4a contours2 ", imgContours)

        # cv2.imshow("4b plate", listPlates[i]["img"])


    # 15/10
    
    # logging.info("List Plate: %s", listPlates)
    if(len(listPlates) ==0):
        print("No plate!")
        return

    listCharsInPlates = getListChars(listPlates)


    # cv2.waitKey(0)
    cv2.destroyAllWindows()

import time
stime = time.time()
main()
etime = time.time()
print ("ESTIMATE TIME: ", etime - stime)
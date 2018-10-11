import cv2
import numpy as np
import math
import random
import logging

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


logging.basicConfig(filename='log_detectPlate.log',filemode='w', format='%(levelname)s\t%(message)s', level=logging.DEBUG)


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
            "contour" : contour
        }
        # char = (centerX, centerY, X, Y, W, H, boundArea, contour)

    return char



def findClusterChar(char, listChars): 
    clusterChar = [char]
    numContinue = 0
    # logging.debug("listChar find CLuster: %s", listChars)
    for pChar in listChars: 
        if pChar == char:
            numContinue += 1
            # logging.debug("Numcontinue: %s", numContinue)
            continue

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

        if(distanceTwoChar < maxDistance and angleTwoChar < CLUSTER_MAX_ANGLE_CHAR 
            and diffArea < MAX_DIFF_AREA and diffWidth < MAX_DIFF_WIDTH and diffHeight < MAX_DIFF_HEIGHT):
            
            logging.debug("---> APPEND %s", pChar["position"])
            clusterChar.append(pChar)


    return clusterChar


def findListClusterChar(listChars):
    
    listClusterChar = []

    for char in listChars:
        clusterChar = findClusterChar(char, listChars)

        if( len(clusterChar) < CLUSTER_MIN_NUM_CHAR ):
            continue

        listClusterChar.append(clusterChar)
        
        listCharRemain = []
        for item in listChars:
            if item not in clusterChar:
                listCharRemain.append(item)

        #recursive
        recuListClusterChar = findListClusterChar(listCharRemain)
        
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
    
    plateWidth = int(clusterChar[-1]["position"][0] + clusterChar[-1]["position"][2] - clusterChar[0]["position"][0])*PLATE_WIDTH_PADD_MULTI  # (X_END+W_END - X_FIRST)*EXTEND_AREA

    avgPlateHeight = sum(item['position'][3] for item in clusterChar) / len(clusterChar)
    
    # for item in clusterChar:
    #     logging.debug("Cluster char: %s", item["position"])
    # logging.debug("AVG height: %s", avgPlateHeight)

    plateHeight = avgPlateHeight*PLATE_HEIGHT_PADD_MULTI

    # plate angle
    plateOpposite = clusterChar[-1]["centerY"] - clusterChar[0]["centerY"]
    plateHipote   = math.hypot(clusterChar[-1]["centerX"] - clusterChar[0]["centerX"],clusterChar[-1]["centerY"] - clusterChar[0]["centerY"] )

    plateAngle = math.asin(plateOpposite/plateHipote) *(180/math.pi)

    plate["rrLocation"] = (plateCenterX, plateCenterY, plateWidth, plateHeight, plateAngle)

    return plate


def combinePlates(imgOrigin, listPlates):

    for i, plate in enumerate(listPlates):
        for j in range(i-1):
            plateCenterX, plateCenterY, plateWidth, plateHeight, plateAngle = plate["rrLocation"]
            jplateCenterX, jplateCenterY, jplateWidth, jplateHeight, jplateAngle = listPlates[j]["rrLocation"]

            heightRatio = jplateHeight*1.0/plateHeight
            diffAngle = jplateAngle - plateAngle
            plaleDistanceRatio = math.hypot(jplateCenterX - plateCenterX, jplateCenterY - plateCenterY) / plateHeight
            plateHeightRatio = jplateHeight/plateHeight

            if( MIN_DIFF_ANGLE < diffAngle and diffAngle < MAX_DIFF_ANGLE and MIN_PLATE_RATIO < plaleDistanceRatio and plaleDistanceRatio < MAX_PLATE_RATIO
                and MIN_PLATE_RATIO < heightRatio and heightRatio < MAX_PLATE_RATIO ):
                









def main():
    imgOrigin = cv2.imread("testIMG/plate.jpg")
    height, width, numChannels = imgOrigin.shape
    
    imgGrayScene = np.zeros((height, width, 1), np.uint8)
    imgThreshScene = np.zeros((height, width, 1), np.uint8)
    imgContours = np.zeros((height, width, 3), np.uint8)

    imgGray, imgPreprocess = preprocess(imgOrigin)

    listChars = findCharsFromImg(imgPreprocess)
    listChars = sorted(listChars, key=lambda x: x["centerX"])

    listClusterChars = findListClusterChar(listChars)

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

    # cv2.imshow("3", imgContours)

    #End draw contours

    listPlates = []
    for clusterChar in listClusterChars:
        plate = getPlateFromClusterChar(clusterChar)
        listPlates.append(plate)
    
    listPlates = combinePlates(imgOrigin, listPlates)


    
    # cv2.imshow('plate',imgGray)
    # cv2.imshow('plate2',imgPreprocess)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

import time
stime = time.time()
main()
etime = time.time()
print "ESTIMATE TIME: ", etime - stime
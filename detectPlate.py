import cv2
import numpy as np
import math
import random
import logging

# is can be char
CHAR_MIN_AREA = 30
CHAR_MIN_W    = 2
CHAR_MIN_H    = 8
CHAR_MIN_RATIO = 0.15
CHAR_MAX_RATIO = 1.0

# Cluster char
CLUSTER_MIN_NUM_CHAR   = 2
CLUSTER_MAX_ANGLE_CHAR = 15.0
MAX_DIFF_AREA   = 0.4
MAX_DIFF_WIDTH  = 0.6
MAX_DIFF_HEIGHT = 0.2
MAX_DISTANCE_MULTI = 3.2



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
    logging.debug("Num contours: %s", len(contours) )
    
    imgContours = np.zeros((imgThresh.shape[0], imgThresh.shape[1], 3), np.uint8)
    # draw Contours:
    for i, item in enumerate(contours):

        isChar = isCanBeChar(item)
        if(isChar):
            listChars.append(isChar)
            cv2.drawContours(imgContours, contours, i, (255.0, 255.0, 255.0) )
            
    logging.debug("Len list chars: %s", len(listChars))
    cv2.imshow("Draw Contour", imgContours)

    return listChars



def isCanBeChar(contour):
    
    char = None
    boundRect = cv2.boundingRect(contour)
    X, Y, W, H = boundRect
    # logging.debug("%s %s %s %s", X, Y, W, H)
    boundArea = W * H
    WHRatio = float(W)/float(H)

    if(W > CHAR_MIN_W and H > CHAR_MIN_H and boundArea > CHAR_MIN_AREA and WHRatio > CHAR_MIN_RATIO and WHRatio < CHAR_MAX_RATIO):
        centerX = (X+X+W)*1.0/2
        centerY = (Y+Y+H)*1.0/2
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
        # logging.debug("Distance: %s, angle: %s, diffArea: %s, diffW: %s, diffH: %s",distanceTwoChar, angleTwoChar, diffArea, diffWidth, diffHeight)




        # SAI O DAY......
        logging.debug("---> COMPARE: %s < %s, %s <%s, %s < %s, %s < %s, %s < %s", distanceTwoChar, maxDistance, angleTwoChar, CLUSTER_MAX_ANGLE_CHAR, diffArea, MAX_DIFF_AREA,  diffWidth, MAX_DIFF_WIDTH, diffHeight, MAX_DIFF_HEIGHT)
        if(distanceTwoChar < maxDistance and angleTwoChar < CLUSTER_MAX_ANGLE_CHAR 
            and diffArea < MAX_DIFF_AREA and diffWidth < MAX_DIFF_WIDTH and diffHeight < MAX_DIFF_HEIGHT):
            
            logging.info("---> APPEND %s", pChar["position"])
            clusterChar.append(pChar)


    return clusterChar


def findListClusterChar(listChars):
    
    listClusterChar = []
    
    for item in listChars:
        logging.debug("Item 2: %s" , item["position"])
    




    for char in listChars:
        clusterChar = findClusterChar(char, listChars)
        logging.debug("---> Char %s Cluster char: %s", char["position"], len(clusterChar))
        # logging.debug("Cluster char: %s", len(clusterChar))

        if( len(clusterChar) < CLUSTER_MIN_NUM_CHAR ):
            continue

        listClusterChar.append(clusterChar)
        # logging.debug("---> LIST CHAR: %s", listChars)
        # logging.debug("---> CLUSTER CHAR: %s", clusterChar)
        
        listCharRemain = []
        for item in listChars:
            if item not in clusterChar:
                listCharRemain.append(item)

        # listCharRemain = list(set(listChars)- set(clusterChar))

        #recursive
        recuListClusterChar = findListClusterChar(listCharRemain)
        
        for item in recuListClusterChar:
            listClusterChar.append(item)
        
        break

    return listClusterChar



def main():
    imgOrigin = cv2.imread("test.jpg")
    height, width, numChannels = imgOrigin.shape
    logging.debug("Shape: %s, %s, %s", height, width, numChannels)
    
    imgGrayScene = np.zeros((height, width, 1), np.uint8)
    imgThreshScene = np.zeros((height, width, 1), np.uint8)
    imgContours = np.zeros((height, width, 3), np.uint8)

    logging.debug("\nGrayScale: %s,\nThreshScene: %s, \nConTours: %s", imgGrayScene.shape, imgThreshScene.shape, imgContours.shape)

    
    imgGray, imgPreprocess = preprocess(imgOrigin)

    listChars = findCharsFromImg(imgPreprocess)
    listChars = sorted(listChars, key=lambda x: x["centerX"])
    logging.debug("Sorted List Chars: \t%s", len(listChars))
    # for item in listChars:
        # logging.debug("Item: %s %s %s %s", item["centerX"], item["centerY"], item["position"][2], item["position"][3])
    


    listClusterChars = findListClusterChar(listChars)

    logging.debug("Len List Cluster Char: %s", len(listClusterChars))
    
    imgContours = np.zeros((height, width, 3), np.uint8)

    for clusterChar in listClusterChars:
        intRandomBlue = random.randint(0, 255)
        intRandomGreen = random.randint(0, 255)
        intRandomRed = random.randint(0, 255)
        
        contours = []
        for matchingChar in clusterChar:
            contours.append(matchingChar["contour"])

        cv2.drawContours(imgContours, contours, -1, (intRandomBlue, intRandomGreen, intRandomRed))

    cv2.imshow("3", imgContours)
    # cv2.imshow('plate',imgGray)
    # cv2.imshow('plate2',imgPreprocess)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

main()

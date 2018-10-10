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
    cv2.imshow("Draw Contour", imgContours)

    return listChars



def isCanBeChar(contour):
    
    char = None
    boundRect = cv2.boundingRect(contour)
    X, Y, W, H = boundRect
    # logging.debug("%s %s %s %s", X, Y, W, H)
    boundArea = W * H
    WHRatio = float(W)/float(H)

    if(W > CHAR_MIN_W and H > CHAR_MIN_H and boundArea > CHAR_MIN_AREA and WHRatio > CHAR_MIN_WHRATIO and WHRatio < CHAR_MAX_WHRATIO):
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

def getPlateFromClusterChar(clusterChar):


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

    cv2.imshow("3", imgContours)

    for clusterChar in listClusterChars:
        plate = getPlateFromClusterChar(clusterChar)


    
    # cv2.imshow('plate',imgGray)
    # cv2.imshow('plate2',imgPreprocess)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

main()

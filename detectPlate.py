import cv2
import numpy as np
import logging

# is can be char
MIN_CHAR_AREA = 30
MIN_CHAR_W    = 2
MIN_CHAR_H    = 8
MIN_WH_RATIO = 0.15
MAX_WH_RATIO = 1.0


logging.basicConfig(filename='log_detectPlate.log',filemode='w', format='%(levelname)s\t%(message)s', level=logging.DEBUG)

def main():
    imgOrigin = cv2.imread("plate.jpg")
    height, width, numChannels = imgOrigin.shape
    logging.debug("Shape: %s, %s, %s", height, width, numChannels)
    
    imgGrayScene = np.zeros((height, width, 1), np.uint8)
    imgThreshScene = np.zeros((height, width, 1), np.uint8)
    imgContours = np.zeros((height, width, 3), np.uint8)

    logging.debug("\nGrayScale: %s,\nThreshScene: %s, \nConTours: %s", imgGrayScene.shape, imgThreshScene.shape, imgContours.shape)

    
    imgGray, imgPreprocess = preprocess(imgOrigin)

    listChars = findCharsFromImg(imgPreprocess)


    # cv2.imshow('plate',imgGray)
    cv2.imshow('plate2',imgPreprocess)
    cv2.waitKey(0)
    cv2.destroyAllWindows()


#get grayScale and threshold 
def preprocess(imgOrigin):
    
    # imgHSV = cv2.cvtColor(imgOrigin, cv2.COLOR_BGR2HSV)
    # hue, saturation, imgGray = cv2.split(imgHSV)
    imgGray = cv2.cvtColor(imgOrigin, cv2.COLOR_BGR2GRAY)

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
    
    listChars = []
    countChars = 0

    imgThreshCopy = imgThresh.copy()
    imgContours, contours, npaHierarchy = cv2.findContours(imgThreshCopy, cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)
    imgContours = np.zeros((imgThresh.shape[0], imgThresh.shape[1], 3), np.uint8)
    logging.debug("Num contours: %s", len(contours) )
    
    # draw Contours:
    for i, item in enumerate(contours):
    #     cv2.drawContours(imgContours, contours, i, (0, 255.0, 255.0) ) #SCALAR_WHITE
    #     cv2.imshow("Draw Contour", imgContours)

        isChar = isCanBeChar(item)
        if(isChar):
            listChars.append(isChar)
            
    logging.debug("Len list chars: %s", len(listChars))

    

    return listChars



def isCanBeChar(contour):
    
    char = None
    boundRect = cv2.boundingRect(contour)
    X, Y, W, H = boundRect
    # logging.debug("%s %s %s %s", X, Y, W, H)
    boundArea = W * H
    WHRatio = float(W)/float(H)

    if(W > MIN_CHAR_W and H > MIN_CHAR_H and boundArea > MIN_CHAR_AREA and WHRatio > MIN_WH_RATIO and WHRatio < MAX_WH_RATIO):
        char = {"x": X, "y": Y, "w": W, "h": H }

    return char


main()
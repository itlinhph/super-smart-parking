import cv2
import numpy as np
import logging

logging.basicConfig(filename='log_detectPlate.log',filemode='w', format='%(levelname)s\t%(message)s', level=logging.DEBUG)

def main():
    imgOrigin = cv2.imread("test.jpg")
    height, width, numChannels = imgOrigin.shape
    logging.debug("Shape: %s, %s, %s", height, width, numChannels)
    
    imgGrayScene = np.zeros((height, width, 1), np.uint8)
    imgThreshScene = np.zeros((height, width, 1), np.uint8)
    imgContours = np.zeros((height, width, 3), np.uint8)

    logging.debug("\nGrayScale: %s,\nThreshScene: %s, \nConTours: %s", imgGrayScene.shape, imgThreshScene.shape, imgContours.shape)

    
    imgGray, imgPreprocess = preprocess(imgOrigin)
    # imgGrayScene, imgThreshScene = preprocess(imgOrigin)

    cv2.imshow('plate',imgGray)
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
    

main()
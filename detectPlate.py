import cv2
import numpy as np
import logging

logging.basicConfig(filename='log_detectPlate.log',filemode='w', format='%(levelname)s\t%(message)s', level=logging.DEBUG)

def main():
    imgOrigin = cv2.imread("plate.jpg")
    height, width, numChannels = imgOrigin.shape
    logging.debug("Shape: %s, %s, %s", height, width, numChannels)
    
    imgGrayscaleScene = np.zeros((height, width, 1), np.uint8)
    imgThreshScene = np.zeros((height, width, 1), np.uint8)
    imgContours = np.zeros((height, width, 3), np.uint8)

    logging.debug("\nGrayScale: %s,\nThreshScene: %s, \nConTours: %s", imgGrayscaleScene.shape, imgThreshScene.shape, imgContours.shape)

    
    imgGrayscale = preprocess(imgOrigin)
    # imgGrayscaleScene, imgThreshScene = preprocess(imgOrigin)

    cv2.imshow('plate',imgGrayscale)
    cv2.waitKey(0)
    cv2.destroyAllWindows()


#get grayScale and threshold 
def preprocess(imgOrigin):
    
    imgHSV = cv2.cvtColor(imgOrigin, cv2.COLOR_BGR2HSV)
    hue, saturation, imgGrayScale = cv2.split(imgHSV)
    
    
    return imgGrayScale
    

main()
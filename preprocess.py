import os
import cv2
import numpy as np
import logging
from skimage import io

logging.basicConfig(filename='log_preprocess.log',filemode='w', format='%(levelname)s\t%(message)s', level=logging.DEBUG)
# folderImg = "real_chars/"

def movieFolder(folderImg):
    labels = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']

    os.system("mdkir -p labels")
    for label in labels:
        os.system("mkdir -p labels/"+ label)

    for imgFile in os.listdir(folderImg):
        name, ext = os.path.splitext(imgFile)
        nameEnd = name[-1:]
        command = "mv "+ folderImg+ imgFile + " labels/"+nameEnd
        os.system(command)

        print(command)


def renameCharImg(folderAll):
    folderLabels = os.listdir(folderAll)
    print(folderLabels)
    for folder in folderLabels:
        os.system("mkdir -p labelRename/"+ folder)
        listImgs = os.listdir(folderAll+"/"+folder)
        count=1
        listImgs.sort()
        print(listImgs)
        for img in listImgs:
            # print img
            name, ext = os.path.splitext(img)
            newName = folder +"_" + str(count) + ext
            command = "cp '" + folderAll+folder+"/"+img + "' " + "labelRename/"+folder+"/"+ newName  
            os.system(command)
            print(command)
            count+=1


# renameCharImg("LabelAugmentor/")


# movieFolder("real_chars/")
# movieFolder("real_char2/")
import os
import cv2
import numpy as np
import logging
from skimage import io

logging.basicConfig(filename='log_detectPlate2.log',filemode='w', format='%(levelname)s\t%(message)s', level=logging.DEBUG)
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


def writeVector(labelFolder, fileVector):

    listImgs  = []
    listLabel = []

    listDirs = os.listdir(labelFolder)
    print('---READING IMAGE---')
    for subDir in listDirs:
        path = labelFolder + subDir + '/'
        logging.debug("---> SUB DIR: %s", path)
        print(path)
        for imgfile in os.listdir(path):
            img = cv2.imread(path + imgfile,0)
            ret,img = cv2.threshold(img,127,255,cv2.THRESH_BINARY)
            # img = io.imread(path + imgfile, as_grey=True)
            # cv2.imshow("img", img)
            # cv2.waitKey(0)
            
            # print(path + imgfile)
            logging.debug(imgfile)
            label = listDirs.index(subDir)
            if(img.shape != (28, 28)):
                print("continue!!")
                continue

            listImgs.append(img)
            listLabel.append(label)
            # logging.info("%s", img)
            
    print('---READ IMAGE COMPLETED---\n')
    
    XdataTemp = np.array(listImgs)
    
    Xdata = XdataTemp.reshape(len(XdataTemp), 28 * 28)
    Ylabel = np.array(listLabel)

    print('dataShape: ', Xdata.shape)
   
    print('   WRITING VECTOR...')
    if not os.path.exists('vector'):
        os.makedirs('vector')
    writeFile(Xdata, Ylabel, 'vector/' + fileVector)

    print('---SAVE VECTOR COMPLETE!---\n')


def writeFile(Xdata, Ylabel, filename):
    fileWrite = open(filename, 'w')
    for i in range(0, len(Ylabel)):
        fileWrite.write(str(Ylabel[i]) + ' ')
        for i2 in range(0, 784):
            fileWrite.write(str(i2) + ':' + str(Xdata[i][i2]) + ' ')

        fileWrite.write('\n')
    fileWrite.close()



writeVector("NUMBER/", "vectorNumber.txt")


# renameCharImg("LabelAugmentor/")


# movieFolder("real_chars/")
# movieFolder("real_char2/")
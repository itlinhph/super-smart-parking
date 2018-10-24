from sklearn import svm
import logging
import os
import numpy as np
import cv2
from sklearn.externals import joblib
from sklearn.model_selection import train_test_split

logging.basicConfig(filename='log_mlDetect.log',filemode='w', format='%(levelname)s\t%(message)s', level=logging.DEBUG)

def save_model_svm():

    clf = svm.SVC(kernel='rbf', C=32, probability=True, cache_size=200, gamma=0.0078125)

    xdata, ylabel = loadData("DATASET/CHAR/")
    xtrain, xtest, ytrain, ytest = train_test_split(xdata, ylabel, test_size=0.2, random_state=100)

    clf.fit(xtrain,ytrain)
    logging.debug("%s", clf)
    joblib.dump(clf, "modelChar.pkl")

    result = clf.predict(xtest)
    logging.debug("TEST : %s", result)
    logging.debug("TRAIN: %s", ytest)
    
    numTrue = 0
    for i, item in enumerate(result):
        if item == ytest[i]:
            numTrue+=1
    accurancy = numTrue*1.0/len(ytest)
    print clf
    print "Num test: ", len(ytest)
    print "Num match: ", numTrue
    print "-----> ACCURANCY: ", accurancy

def loadData(labelFolder):

    listImgs  = []
    listLabel = []

    listDirs = os.listdir(labelFolder)
    listDirs.sort()
    logging.info('---READING IMAGE---')
    for subDir in listDirs:
        path = labelFolder + subDir + '/'
        logging.debug("---> SUB DIR: %s", path)
        print(path)
        for imgfile in os.listdir(path):
            img = cv2.imread(path + imgfile,0)
            _,img = cv2.threshold(img,127,1,cv2.THRESH_BINARY)

            label = subDir
            if(img.shape != (28, 28)):
                print("continue!!")
                continue

            listImgs.append(img)
            listLabel.append(label)
            # logging.info("%s", img)
            
    print('---READ IMAGE COMPLETED---\n')
    
    XdataTemp = np.array(listImgs)
    
    Xdata = XdataTemp.reshape(len(XdataTemp), 28 * 28)
    logging.info("Xdata 2 : %s", Xdata[0])
    Ylabel = np.array(listLabel)
    logging.info("Ylabel 2: %s", Ylabel[0])

    print 'Data Shape: ', Xdata.shape
    logging.debug("----> XDATA:\n%s",Xdata.shape)
    logging.debug("----> YLABEL:\n%s",Ylabel.shape)
    
    return Xdata, Ylabel


def checkmodel():
    xdata = [0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0]
    



loadData("testFolder/")
# save_model_svm()
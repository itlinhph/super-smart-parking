# LinhPH
Super Smart Parking
===========================================

Prerequisite
------------

> - Python 2
> - OpenCV 3.4.1
> - Sklearn, shapely (Python 2)
> - Java
> - Gearman Service: Gearman Job Server, Gearman Worker (python 2), Gearman Client (Java).

Run Project
------------
- Run detect plate worker: Go to detectPlate folder and run: python detectPlate.py
- Run SmartParkingWeb: 
+ Config Apache Tomcat with NetBean.
+ Open Netbean Project and run.

Project Detail
------------
1. Detect Plate System:
- OpenCV, SVM, Gearman Worker.
- Data:  
> - GreenParking 1750 item
> - BSXM-Google 657 item => Filter => 184 item
> ==> Sum = 1934 item
- Generate Character: 11.702 item => Filter => 11275 item
> + 9618 item number / 10 labels
> + 1608 item char / 21 labels
=> Augmentor data => 17.037 (Char augmentor)
- Detect Plate by OpenCV
- Recognize character in plate by Machine Learning
- Machine Learning:
> + SVM Multiclass Kenel RBF.
> + Number: C=32, g=0.000112  => Accurancy: 98.32%
> + Char  : C=32, g=0.0078125 => Accurancy: 99.87%

2. Web site system:
> - Backend: Java web
> - Front-end: HTML, CSS, JS
> - Spring Framework with MVC
> - Gearman job with ML SYSTEM.

------------------
Contact me: fb.com/deluxe.psk

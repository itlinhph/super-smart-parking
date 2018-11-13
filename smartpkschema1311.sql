/*
SQLyog Job Agent v12.09 (64 bit) Copyright(c) Webyog Inc. All Rights Reserved.


MySQL - 5.7.24-0ubuntu0.18.04.1 : Database - smart_parking
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`smart_parking` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `smart_parking`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `status` varchar(15) COLLATE utf8_unicode_ci DEFAULT 'working' COMMENT '1: working, 0: deactive',
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `admin` */

/*Table structure for table `park` */

DROP TABLE IF EXISTS `park`;

CREATE TABLE `park` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `park_code` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `park_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `total_slots` int(10) unsigned NOT NULL,
  `description` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `park` */

insert  into `park` values (1,'BK-D5','Bãi xe D5',10,'Bãi Xe D5'),(2,'BK-KTX','Bãi xe Ký túc xá',500,'Bãi xe phục vụ sinh viên ở KTX'),(3,'BK-D9','Bãi xe D9',400,'Bãi Xe phục vụ cán bộ nhân viên BK');

/*Table structure for table `report` */

DROP TABLE IF EXISTS `report`;

CREATE TABLE `report` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `type_code` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT 'staff, ticket, vehicle, other',
  `ticket_id` bigint(20) unsigned NOT NULL,
  `description` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `img` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` varchar(15) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'pending' COMMENT '0: pending, 1: done.',
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `admin_note` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ticket_id` (`ticket_id`),
  CONSTRAINT `report_ibfk_1` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `report` */

/*Table structure for table `staff` */

DROP TABLE IF EXISTS `staff`;

CREATE TABLE `staff` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `staff_code` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `staff_name` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `park_id` int(10) unsigned NOT NULL,
  `status` varchar(15) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'working' COMMENT '1: working, 0: deactive',
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `park_id` (`park_id`),
  CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`park_id`) REFERENCES `park` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `staff` */

insert  into `staff` values (1,'S1001','202cb962ac59075b964b07152d234b70','Hà Anh Tuấn',1,'working','2018-11-09 00:00:27'),(2,'S1002','202cb962ac59075b964b07152d234b70','Nam Anh',2,'working','2018-11-09 00:46:19');

/*Table structure for table `ticket` */

DROP TABLE IF EXISTS `ticket`;

CREATE TABLE `ticket` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `vehicle_id` int(10) unsigned NOT NULL,
  `park_id` int(10) unsigned NOT NULL,
  `ticket_code` int(11) NOT NULL,
  `status` varchar(15) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'working' COMMENT '1: working, 0: expired',
  `fee` int(11) NOT NULL DEFAULT '1',
  `checkin_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `checkout_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `vehicle_id` (`vehicle_id`),
  KEY `park_id` (`park_id`),
  CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`),
  CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`park_id`) REFERENCES `park` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `ticket` */

insert  into `ticket` values (1,2,1,1203,'expired',1,'2018-11-09 07:30:21','2018-11-06 14:12:29'),(2,1,3,2034,'expired',1,'2018-11-09 07:45:12','2018-11-06 14:12:29'),(3,1,1,8493,'working',1,'2018-11-15 07:30:21',NULL),(17,3,1,5566,'working',1,'2018-11-15 07:45:12',NULL),(18,2,2,5467,'working',1,'2018-11-15 07:45:12',NULL);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `status` varchar(15) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'working' COMMENT '0: pedding, 1: working, 2:banned',
  `fullname` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `coin_remain` int(10) unsigned NOT NULL DEFAULT '0',
  `note` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `user` */

insert  into `user` values (1,'linhph','202cb962ac59075b964b07152d234b70','working','Phan Hồng Lĩnh','linhphanhust@gmail.com','964976895',0,'No note','2018-10-31 16:10:13'),(2,'trangdt','c4ca4238a0b923820dcc509a6f75849b','working','Trang DT','trangdt@ghtk.vn','123456789',0,NULL,'2018-10-31 17:27:28'),(3,'linhphan','202cb962ac59075b964b07152d234b70','working','Phan Hồng Lĩnh','linhphan@gmail.com','111222333',0,NULL,'2018-11-01 17:37:04'),(4,'linhphan1','81dc9bdb52d04dc20036dbd8313ed055','working','Phan Hồng Lĩnh','changvt2401@gmail.com','09123232333',0,NULL,'2018-11-01 17:42:56'),(5,'111','698d51a19d8a121ce581499d7b701668','working','Nguyễn Đình Mẫn','linhph@gmail.com','111',0,NULL,'2018-11-01 18:15:35'),(6,'lanem','698d51a19d8a121ce581499d7b701668','working','Ngô Lan Anh','lananh.ngo.1994@gmail.com','1111',0,NULL,'2018-11-01 18:21:21');

/*Table structure for table `vehicle` */

DROP TABLE IF EXISTS `vehicle`;

CREATE TABLE `vehicle` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `plate` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `user_id` int(10) unsigned DEFAULT NULL,
  `model` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `img` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'resource/images/defaultImage.jpg',
  `status` varchar(15) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'pending' COMMENT '0: pendding, 1: working, 2: deactive',
  `description` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `vehicle_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `vehicle` */

insert  into `vehicle` values (1,'63-B9 999.99',3,'SH Mode','resources/images/xemay2.jpg','working','Xe máy SH xin xò lắm à nha. hihi'),(2,'47-F1 222.22',3,'Wave RSX 2012','resources/images/xemay3.jpg','pending','Màu đỏ, đẹp, còn mới ưng'),(3,'29S2-12345',3,'SH Anfa','resources/images/xemay.jpg','deactive','Con tym băng giá.'),(4,'29S2-12345',3,'SH Anfa','resources/images/xemay.jpg','deactive','xxxx'),(5,'29S2-12345',3,'Wave Anfa','resources/images/xemay.jpg','pending','Ahihi đồ ngôk');

/*Table structure for table `wrong_plate` */

DROP TABLE IF EXISTS `wrong_plate`;

CREATE TABLE `wrong_plate` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `img` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `detect_result` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `status` varchar(30) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'pending',
  `park_id` int(10) unsigned NOT NULL,
  `checkin_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `park_id` (`park_id`),
  CONSTRAINT `wrong_plate_ibfk_1` FOREIGN KEY (`park_id`) REFERENCES `park` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `wrong_plate` */

insert  into `wrong_plate` values (1,'resources/images/wrong1.jpg','29S2-12345','pending',1,'2018-11-13 19:06:23'),(2,'resources/images/wrong3.jpg','59C2-11794','pending',1,'2018-11-13 19:08:10'),(3,'resources/images/wrong4.jpg','59P1-43138','pending',1,'2018-11-13 19:08:12');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

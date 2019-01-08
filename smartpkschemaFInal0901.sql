-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: localhost    Database: smart_parking
-- ------------------------------------------------------
-- Server version	5.7.24-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `status` varchar(15) COLLATE utf8_unicode_ci DEFAULT 'working' COMMENT '1: working, 0: deactive',
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin','21232f297a57a5a743894a0e4a801fc3','working','2018-11-22 19:08:20');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `park`
--

DROP TABLE IF EXISTS `park`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `park` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `park_code` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `park_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `total_slots` int(10) unsigned NOT NULL,
  `description` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `park`
--

LOCK TABLES `park` WRITE;
/*!40000 ALTER TABLE `park` DISABLE KEYS */;
INSERT INTO `park` VALUES (1,'BK-D5','Bãi xe D5',10,'Bãi Xe D5'),(2,'BK-KTX','Bãi xe Ký túc xá',500,'Bãi xe phục vụ sinh viên ở KTX'),(3,'BK-D9','Bãi xe D9',400,'Bãi Xe phục vụ cán bộ nhân viên BK');
/*!40000 ALTER TABLE `park` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT 'staff, ticket, vehicle, other',
  `ticket_id` bigint(20) unsigned NOT NULL,
  `description` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `status` varchar(15) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'pending' COMMENT '0: pending, 1: done.',
  `admin_reply` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `processed_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ticket_id` (`ticket_id`),
  CONSTRAINT `report_ibfk_1` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES (1,'ticket',24,'Nhân viên phục vụ chưa tốt','processed','Tôi đã trả lời qua email','2018-11-28 20:06:28','2018-11-29 20:42:39'),(2,'ticket',22,'i lost my ticket','processed','admin reply kaka','2018-11-28 21:07:16','2018-11-29 20:43:42'),(44,'vehicle',17,'Hihi','processed','ahihi đồ ngok','2018-11-29 19:39:48','2018-12-26 11:43:08'),(45,'staff',28,'Nhân viên gian lận vé','pending',NULL,'2018-11-30 10:15:48',NULL),(46,'ticket',30,'Tôi bị nhận nhầm vé xe của ai đó. Hôm nay tôi không đi bằng xe này.','pending',NULL,'2019-01-09 00:14:48',NULL),(47,'other',37,'Tôi muốn gửi lời cảm ơn tới nhân viên trông xe D5 ca sáng đã rút chìa khóa giúp tôi.','pending',NULL,'2019-01-09 00:16:04',NULL);
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1,'S1001','202cb962ac59075b964b07152d234b70','Hà Anh Tuấn',1,'deactive','2018-11-09 00:00:27'),(2,'S1002','202cb962ac59075b964b07152d234b70','Nam Anh',2,'deactive','2018-11-09 00:46:19'),(3,'S1003','63e621913f19b642a32beb38a7e78a67','Ku bin',1,'working','2018-11-24 00:15:36'),(4,'S1004','36ab4bd00640a5a597aa6df0259184b5','Phan Hồng Lĩnh',3,'working','2018-11-24 19:07:36'),(5,'S1005','090aabf6b3af17eb0059c8a4f92cd392','ABC',2,'working','2018-11-24 19:09:25');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  KEY `idx_ticket_checkin_time` (`checkin_time`),
  CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`),
  CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`park_id`) REFERENCES `park` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (1,2,1,1203,'expired',1,'2018-11-30 07:30:21','2018-11-30 09:12:29'),(2,1,3,2034,'expired',1,'2018-11-30 07:45:12','2018-11-06 14:12:29'),(3,1,1,8493,'expired',1,'2018-11-27 07:30:21','2018-11-27 09:21:22'),(17,3,1,5566,'expired',1,'2018-11-27 07:45:12','2018-11-27 09:22:23'),(18,2,2,5467,'expired',1,'2018-11-27 07:45:12','2018-11-27 11:21:46'),(19,5,1,8102,'expired',1,'2018-11-30 09:11:56','2018-11-30 12:11:56'),(20,5,1,2815,'expired',1,'2018-11-30 08:15:59','2018-11-30 19:15:59'),(21,5,1,1841,'expired',1,'2018-11-30 07:17:24','2018-11-30 07:18:24'),(22,5,1,8226,'expired',1,'2018-11-30 07:03:32','2018-11-30 08:04:32'),(23,1,1,4157,'expired',1,'2018-11-30 06:04:28','2018-11-30 06:06:20'),(24,2,1,3529,'expired',1,'2018-11-21 00:06:46','2018-11-21 23:53:50'),(25,2,1,2430,'expired',1,'2018-11-30 09:34:40','2018-11-30 09:42:37'),(26,3,1,1778,'expired',1,'2018-11-30 09:35:24','2018-11-30 09:36:05'),(27,2,1,9616,'expired',1,'2018-11-30 09:41:50','2018-11-30 09:44:30'),(28,3,1,9911,'expired',1,'2018-11-30 10:12:20','2018-11-30 10:14:55'),(29,2,1,9342,'expired',1,'2018-11-30 10:14:23','2018-11-30 11:13:24'),(30,3,1,3305,'expired',1,'2018-11-30 10:18:29','2018-11-30 12:18:29'),(31,2,1,9828,'expired',1,'2018-12-26 11:46:26','2018-12-26 11:48:12'),(32,3,1,2944,'expired',1,'2019-01-08 01:19:33','2019-01-08 01:21:28'),(33,7,1,7965,'expired',1,'2019-01-08 01:21:47','2019-01-08 23:56:21'),(34,10,1,1799,'expired',1,'2019-01-08 23:56:42','2019-01-09 00:00:30'),(35,8,1,4162,'expired',1,'2019-01-08 23:56:58','2019-01-08 23:59:51'),(36,8,1,6974,'expired',1,'2019-01-08 23:59:07','2019-01-09 00:01:03'),(37,3,1,2843,'expired',1,'2019-01-09 00:02:45','2019-01-09 00:04:18'),(38,4,1,7541,'working',1,'2019-01-09 00:02:54',NULL),(39,5,1,9525,'working',1,'2019-01-09 00:03:01',NULL),(40,6,1,6251,'expired',1,'2019-01-09 00:03:52','2019-01-09 00:04:08');
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `status` varchar(15) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'working' COMMENT '0: pedding, 1: working, 2:banned',
  `fullname` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `coin_remain` int(10) unsigned NOT NULL DEFAULT '0',
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'linhph','81dc9bdb52d04dc20036dbd8313ed055','working','Phan Hồng Lĩnh','linhphanhust@gmail.com','964976895',9,'2018-10-31 16:10:13'),(2,'trangdt','81dc9bdb52d04dc20036dbd8313ed055','deactive','Đào Thu Trang','trangdt@ghtk.vn','123456789',5,'2018-10-31 17:27:28'),(3,'linhphan','202cb962ac59075b964b07152d234b70','working','Phan Hồng Lĩnh','linhphan@gmail.com','1112223',23,'2018-11-01 17:37:04'),(4,'tuannn','81dc9bdb52d04dc20036dbd8313ed055','working','Nguyễn Ngọc Tuấn','changvt2401@gmail.com','09123232333',4,'2018-11-01 17:42:56'),(5,'mannd','81dc9bdb52d04dc20036dbd8313ed055','deactive','Nguyễn Đình Mẫn','linhph@gmail.com','111',2,'2018-11-01 18:15:35'),(6,'lanem','698d51a19d8a121ce581499d7b701668','deactive','Ngô Lan Anh','lananh.ngo.1994@gmail.com','1111',0,'2018-11-01 18:21:21');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle`
--

DROP TABLE IF EXISTS `vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle`
--

LOCK TABLES `vehicle` WRITE;
/*!40000 ALTER TABLE `vehicle` DISABLE KEYS */;
INSERT INTO `vehicle` VALUES (1,'59H1-54986',3,'Lead 2018','plate-14.jpg','working','Xe máy lead màu đen đời 2018'),(2,'68F5-5727',3,'Lead 2017','plate-12.jpg','pending','Màu đỏ, SYM'),(3,'95G1-01643',3,'Dream 2001','plate-01.jpg','working','Dream đời 2001'),(4,'59L2-06377',1,'Wave Anfa','plate-02.jpg','working','Wave Anfa khá mới.'),(5,'59S2-35205',2,'Ablade','plate-03.jpg','working','Ablade không gương chiếu hậu'),(6,'59C1-65331',4,'Wave Anfa','plate-04.jpg','working','Wave anfa trắng'),(7,'37M1-07918',4,'Honda Vision','wrongxx.jpg','working','Vision phiên bản smartkey'),(8,'59U1-66141',4,'Yamaha','wrong1.jpg','working','Yamaha màu đỏ.'),(9,'59V1-07473',5,'Vision','plate-13.jpg','working','Vision trắng'),(10,'54V1-4819',1,'SYM','wrong2.jpg','working','SYM đen.'),(11,'59T1-08264',5,'Yamaha','plate-05.jpg','pending','Yamaha Sirius'),(12,'77C1-39373',2,'Vision','plate-06.jpg','pending','Vision 2018, hình thức mới.');
/*!40000 ALTER TABLE `vehicle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wrong_plate`
--

DROP TABLE IF EXISTS `wrong_plate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wrong_plate` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `img` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `detect_result` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `fixed_plate` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` varchar(30) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'pending',
  `park_id` int(10) unsigned NOT NULL,
  `checkin_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `park_id` (`park_id`),
  KEY `idx_wrong_plate_checkin_time` (`checkin_time`),
  CONSTRAINT `wrong_plate_ibfk_1` FOREIGN KEY (`park_id`) REFERENCES `park` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wrong_plate`
--

LOCK TABLES `wrong_plate` WRITE;
/*!40000 ALTER TABLE `wrong_plate` DISABLE KEYS */;
INSERT INTO `wrong_plate` VALUES (1,'wrong1.jpg','29S2-12345','29S2-12345','fixed',1,'2018-11-13 19:06:23'),(2,'wrong3.jpg','59C2-11794','29S2-12345','fixed',1,'2018-11-13 19:08:10'),(3,'wrong4.jpg','59P1-43138','47F1-22222','fixed',1,'2018-11-13 19:08:12'),(4,'wrong1.jpg','59U1-41','47F1-22222','fixed',1,'2018-11-20 23:53:44'),(6,'xemay3.jpg','47F1-22222','47F1-22222','fixed',1,'2018-11-21 00:05:09'),(7,'true5.jpg','23B4-9722','23B4-9711','fixed',1,'2018-11-21 00:07:30'),(8,'true4.jpg','77C1-39373','47F1-22222','fixed',1,'2018-11-22 19:02:25'),(9,'true2.jpg','59S2-35205','59S2-5205','fixed',1,'2018-11-30 09:41:17'),(10,'wrongxx.jpg','37M1-407918','37M1-07918','fixed',1,'2019-01-08 01:19:58'),(11,'wrong1.jpg','59U1-41','59U1-66141','fixed',1,'2019-01-08 01:20:19'),(12,'wrong2.jpg','V148-19','54V1-4819','fixed',1,'2019-01-08 01:26:26'),(13,'wrong1.jpg','59U1-41','59U1-66141','fixed',1,'2019-01-08 23:58:48'),(14,'false.jpg','',NULL,'pending',1,'2019-01-09 00:02:13'),(15,'wrong1.jpg','59U1-41',NULL,'pending',1,'2019-01-09 00:02:22'),(16,'plate-04.jpg','59C1-65331','59C1-65331','fixed',1,'2019-01-09 00:03:12');
/*!40000 ALTER TABLE `wrong_plate` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-09  0:18:43

# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.5.42)
# Database: csci992
# Generation Time: 2016-10-31 21:56:58 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table doctor_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `doctor_info`;

CREATE TABLE `doctor_info` (
  `doctor_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `phone_num` varchar(10) NOT NULL,
  `date_of_birth` date NOT NULL,
  `hospital` varchar(50) NOT NULL,
  `department` varchar(50) NOT NULL,
  `registration_date` datetime DEFAULT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'pending',
  `user_id` int(11) NOT NULL,
  `registration_number` varchar(50) DEFAULT NULL,
  `action_date` datetime DEFAULT NULL,
  `license_no` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`doctor_id`),
  UNIQUE KEY `Unique_doc_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `doctor_info` WRITE;
/*!40000 ALTER TABLE `doctor_info` DISABLE KEYS */;

INSERT INTO `doctor_info` (`doctor_id`, `title`, `first_name`, `last_name`, `phone_num`, `date_of_birth`, `hospital`, `department`, `registration_date`, `status`, `user_id`, `registration_number`, `action_date`, `license_no`)
VALUES
	(1,'Mr.','Kunal','Kanere','12345666','1987-09-16','Sydney Central','Cardiology','2016-09-07 00:00:00','approved',3,'KHD4789','2016-10-26 18:56:14','0'),
	(2,'Mr.','Hrudai','Haaa','1544531','1989-10-22','Wollongong Hospital','Physiotherapy','2016-10-12 00:00:00','pending',6,'MKJD0383','2016-10-26 18:56:14','0'),
	(3,'Mr','James','Bond','007','2007-07-07','Wollongong','Cardio','2016-10-27 10:42:01','pending',7,'724752hh7',NULL,'0'),
	(4,'Mr','Ning','Wang','5346545231','2000-10-28','Wollongong','Physio','2016-10-27 10:47:41','approved',4,'tyjgtyk4247','2016-10-27 10:47:53','0'),
	(11,'Dr','Albert','Einstein','0784743444','1995-10-06','Warrawong Hospital','Psychiatry','2009-10-24 10:47:22','pending',11,'h334gvbg','2016-10-04 10:48:02','0'),
	(12,'Dr','Drake','Remore','0478478454','1991-10-22','General Hospital','Neurology','2011-10-19 10:49:30','pending',12,'D9834kdjjh','2016-10-15 10:49:37','0'),
	(13,'Dr','Pengyu','Huo','4445545245','1991-10-31','Wollongong','Cardio','2016-10-27 10:49:03','pending',5,'ufgty87587587','2016-10-26 10:49:12','0'),
	(14,'Mr','Tan','Hao','654654serf','2016-10-27','Gong','Pediatrician','2016-10-27 10:50:36','rejected',2,'ufykfgyikuy847558','2016-10-27 10:50:44','0'),
	(24,'Dr','Mathew','Hayden','8945038','1993-10-22','Sydney ','Orthopedic','1995-10-15 11:00:46','pending',24,'t4gr4g4476','2016-10-20 11:01:09','0'),
	(22,'Dr','Sue','Lee','9457845745','1995-10-23','Melbourne Research Institute','Radiology','1997-10-17 10:55:36','rejected',22,'g344ggg','2016-10-29 10:55:50','0'),
	(25,'Dr','James','Anderson','07098','1995-10-16','Brisbane','Urology','2012-10-26 11:03:01','pending',25,'fvergjk445','2016-10-26 11:03:19','0'),
	(29,'dasdsad','asdasd','asdsad','12312','2016-11-15','asdasd','asdas','2016-11-01 04:24:22','approved',36,NULL,'2016-11-01 04:30:15','123123');

/*!40000 ALTER TABLE `doctor_info` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table drugs
# ------------------------------------------------------------

DROP TABLE IF EXISTS `drugs`;

CREATE TABLE `drugs` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `serial` varchar(20) NOT NULL,
  `producer` varchar(50) NOT NULL,
  `package` varchar(30) NOT NULL,
  `price` decimal(15,2) DEFAULT NULL,
  `type` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

LOCK TABLES `drugs` WRITE;
/*!40000 ALTER TABLE `drugs` DISABLE KEYS */;

INSERT INTO `drugs` (`id`, `name`, `serial`, `producer`, `package`, `price`, `type`)
VALUES
	(1,'Herceptin','L01XC03','Roche','100 tablets',500.01,1),
	(2,'Sorafenib','L01XE05','Bayer','200 capsules',800.10,1),
	(3,'Glivec','L01XE01','Novartis','80 capsules',730.01,1),
	(4,'Iressa','L01XE02','AstraZeneca','160 tablets',480.30,1);

/*!40000 ALTER TABLE `drugs` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table orders
# ------------------------------------------------------------

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` varchar(16) NOT NULL DEFAULT '',
  `prescription_id` int(11) NOT NULL,
  `phar_id` int(11) NOT NULL,
  `order_time` datetime NOT NULL,
  `pick_time` date NOT NULL,
  `status` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;

INSERT INTO `orders` (`id`, `order_id`, `prescription_id`, `phar_id`, `order_time`, `pick_time`, `status`)
VALUES
	(20,'20161025120113',1,4,'2016-10-25 00:00:00','2016-11-23',3),
	(28,'20161026012358',2,4,'2016-10-26 00:00:00','2016-11-14',3),
	(29,'20161026062224',1,4,'2016-10-26 06:22:24','2016-12-21',0),
	(30,'20161028001506',2,4,'2016-10-28 00:15:06','2016-11-22',0);

/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table patient_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `patient_info`;

CREATE TABLE `patient_info` (
  `patient_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(11) DEFAULT NULL,
  `last_name` varchar(11) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `medicare_no` int(11) DEFAULT NULL,
  `phone_no` varchar(11) DEFAULT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'pending',
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `patient_info` WRITE;
/*!40000 ALTER TABLE `patient_info` DISABLE KEYS */;

INSERT INTO `patient_info` (`patient_id`, `first_name`, `last_name`, `date_of_birth`, `medicare_no`, `phone_no`, `status`, `user_id`)
VALUES
	(4,'ning','wang','1982-09-30',123456,'4321','approved',4),
	(1,'Pengyu','Huo','1993-10-11',345435,'0466598763','pending',5),
	(5,'Tan','hao','1992-10-11',3475345,'66593245','approved',2),
	(6,'Hrudai','Pendli','2016-10-05',563756727,'52855452','pending',6),
	(7,'Bruce','Wills','1991-10-22',734747,'074747','pending',7),
	(9,'Mathew','Perry','1998-10-21',2143,'424545646','pending',9),
	(10,'David','Schimmer','1993-10-10',756752,'0536788','pending',10),
	(11,'Stefan','a','1993-10-26',25567,'0457886656','pending',1),
	(14,'a','b','2016-10-11',123123123,'1232131','pending',17),
	(13,'Kunal','Kanere','1996-10-12',2147483647,'45454545','pending',3);

/*!40000 ALTER TABLE `patient_info` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table person
# ------------------------------------------------------------

DROP TABLE IF EXISTS `person`;

CREATE TABLE `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `First_Name` varchar(25) DEFAULT NULL,
  `Last_Name` varchar(25) DEFAULT NULL,
  `Street_Name` varchar(25) DEFAULT NULL,
  `City` varchar(25) DEFAULT NULL,
  `State` varchar(25) DEFAULT NULL,
  `Country` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;

INSERT INTO `person` (`id`, `First_Name`, `Last_Name`, `Street_Name`, `City`, `State`, `Country`)
VALUES
	(1,'Java','Honk','John St.','NY','NY','USA'),
	(2,'Stefan','Chen','Gilmore St.','Wollongong','NSW','AU'),
	(3,'Kunal','Kanere','Bay ST','Rockdale','NSW','AU'),
	(4,'Vignesh','Saktivel','XYZ','Wollongong','NSw','AU'),
	(5,'Syed','Tehsin','ABC','Rockdale','NSW','AU'),
	(6,'Hao','Tan','PQR','Wollongong','NSW','AU'),
	(7,'Yi','Fang','asd','Wollongong','NSW','AU'),
	(8,'Saimonw','L','jkl','Toga','Toga','TO'),
	(9,'Hrudai','P','ghj','Sydney','NSW','AU'),
	(10,'John','Smith','Cairo St','Melbourne','VIC','AU'),
	(11,'Tim','Cook','Square St','New York','New York','USA');

/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pharmacist_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pharmacist_info`;

CREATE TABLE `pharmacist_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `street` varchar(80) NOT NULL,
  `postcode` varchar(10) DEFAULT NULL,
  `suburb` varchar(30) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `registration_date` datetime DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `registration_number` varchar(50) DEFAULT NULL,
  `action_date` datetime DEFAULT NULL,
  `longitude` decimal(15,8) NOT NULL,
  `latitude` decimal(15,8) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

LOCK TABLES `pharmacist_info` WRITE;
/*!40000 ALTER TABLE `pharmacist_info` DISABLE KEYS */;

INSERT INTO `pharmacist_info` (`id`, `user_id`, `name`, `street`, `postcode`, `suburb`, `phone`, `registration_date`, `status`, `registration_number`, `action_date`, `longitude`, `latitude`)
VALUES
	(1,8,'Hospital Hill Pharmacy','338 Crown St','2500','Wollongong','(02) 4229 5868','2016-10-01 00:00:00','approved',NULL,NULL,150.88584920,-34.42501410),
	(2,10,'Wollongong City Pharmacy','237 Crown St','2500','Wollongong','(02) 4229 5724','2016-10-05 00:00:00','pending',NULL,NULL,150.89246090,-34.42542420),
	(3,11,'Harrison\'s Pharmacy Wollongong','132 Crown St','2500','Wollongong','(02) 4229 1622','2016-09-06 00:00:00','pending',NULL,NULL,150.89599300,-34.42502740),
	(4,12,'Downtown MediAdvice Pharmacy','60 Crown St','2500','Wollongong','(02) 4229 1025','2016-08-16 00:00:00','approved',NULL,NULL,150.89935680,-34.42555090),
	(5,23,'xingdoudayaofang','44 Gilmore St','hahahaha','Wollongong','2133',NULL,'pending',NULL,NULL,-34.42349030,150.87435550);

/*!40000 ALTER TABLE `pharmacist_info` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table prescription
# ------------------------------------------------------------

DROP TABLE IF EXISTS `prescription`;

CREATE TABLE `prescription` (
  `prescription_id` int(10) NOT NULL AUTO_INCREMENT,
  `prescription_date` date NOT NULL,
  `repeat_time` int(10) NOT NULL,
  `ordered_repeat` int(11) NOT NULL,
  `status` int(1) NOT NULL COMMENT 'close - 1; still can be repreated - 0 ; haven''t been ordered since prescribe',
  `description` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `duration` int(11) NOT NULL COMMENT 'how many days we take drugs in one repeat or treatment',
  `total_price` decimal(15,2) NOT NULL,
  `latest_repeat_time` varchar(16) COLLATE utf8_unicode_ci NOT NULL DEFAULT '1980-01-01',
  `prescription_seq` varchar(25) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`prescription_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `prescription` WRITE;
/*!40000 ALTER TABLE `prescription` DISABLE KEYS */;

INSERT INTO `prescription` (`prescription_id`, `prescription_date`, `repeat_time`, `ordered_repeat`, `status`, `description`, `duration`, `total_price`, `latest_repeat_time`, `prescription_seq`)
VALUES
	(1,'2016-10-14',100,0,2,'Do not take this prescription with seafood',10,1000.60,'2016-10-10','20161014000000'),
	(2,'2016-10-08',4,0,2,'Do not take this prescription with chicken',7,1200.80,'2016-10-10','20161008000000'),
	(20,'2016-11-01',1,0,2,'dasdasdasdasd',1,500.01,'1980-01-01','P20161101034355'),
	(19,'2016-11-01',2,0,2,'asfjasfoajsfoafiasjfos',3,1960.03,'1980-01-01','P20161101033939'),
	(18,'2016-11-01',1,0,2,'',1,1500.03,'1980-01-01','P20161101022411'),
	(17,'2016-11-01',1,0,2,'',1,1500.03,'1980-01-01','P20161101022325'),
	(16,'2016-11-01',1,0,2,'',1,1500.03,'1980-01-01','P20161101021836'),
	(15,'2016-11-01',1,0,2,'',1,1500.03,'1980-01-01','P20161101021559'),
	(14,'2016-11-01',1,0,2,'',1,1000.02,'1980-01-01','P20161101021516'),
	(13,'2016-11-01',1,0,2,'',1,1000.02,'1980-01-01','P20161101021312'),
	(21,'2016-11-01',1,0,2,'adasdasdas',1,800.10,'1980-01-01','P20161101034407'),
	(22,'2016-11-01',1,0,2,'dasdasdasd',1,800.10,'1980-01-01','P20161101034419'),
	(23,'2016-11-01',1,0,2,'asdasdasdas',1,730.01,'1980-01-01','P20161101034438'),
	(24,'2016-11-01',1,0,2,'asdasdasdasd',1,480.30,'1980-01-01','P20161101034451'),
	(25,'2016-11-01',1,0,2,'adsasdasd',1,500.01,'1980-01-01','P20161101034505'),
	(26,'2016-11-01',1,0,2,'',1,500.01,'1980-01-01','P20161101043440');

/*!40000 ALTER TABLE `prescription` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table prescription_drugs
# ------------------------------------------------------------

DROP TABLE IF EXISTS `prescription_drugs`;

CREATE TABLE `prescription_drugs` (
  `pd_id` int(10) NOT NULL AUTO_INCREMENT,
  `drug_id` int(10) NOT NULL,
  `prescription_id` int(10) NOT NULL,
  `dose` int(10) NOT NULL COMMENT 'quantity',
  `description` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`pd_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

LOCK TABLES `prescription_drugs` WRITE;
/*!40000 ALTER TABLE `prescription_drugs` DISABLE KEYS */;

INSERT INTO `prescription_drugs` (`pd_id`, `drug_id`, `prescription_id`, `dose`, `description`)
VALUES
	(1,1,1,2,'take 2 pills a day'),
	(2,2,1,1,'take 3 ml a day'),
	(3,3,1,3,'take 3 caps a day'),
	(4,4,1,4,'take 10 ml a day'),
	(5,2,2,10,'take 100ml a day'),
	(6,4,2,5,'take 30ml a day'),
	(17,1,16,1,''),
	(16,1,16,1,''),
	(15,1,15,1,'1213123'),
	(14,1,15,1,''),
	(13,1,15,1,'asdasd'),
	(18,1,16,1,'asdasdasd'),
	(19,1,17,1,'112323'),
	(20,1,17,1,''),
	(21,1,17,1,''),
	(22,1,18,1,''),
	(23,1,18,1,'4444444'),
	(24,1,18,1,''),
	(25,1,19,1,'a123123132'),
	(26,3,19,2,'asdasdasd'),
	(27,1,20,1,'sdas'),
	(28,2,21,1,'12312312'),
	(29,2,22,1,'asdasdas'),
	(30,3,23,1,'dasdasdasd'),
	(31,4,24,1,'asdasd'),
	(32,1,25,1,'asdasdasdad'),
	(33,1,26,1,'');

/*!40000 ALTER TABLE `prescription_drugs` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table prescription_history
# ------------------------------------------------------------

DROP TABLE IF EXISTS `prescription_history`;

CREATE TABLE `prescription_history` (
  `history_id` int(10) NOT NULL AUTO_INCREMENT,
  `prescription_id` int(10) NOT NULL,
  `doctor_id` int(10) NOT NULL,
  `patient_id` int(10) NOT NULL,
  PRIMARY KEY (`history_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `prescription_history` WRITE;
/*!40000 ALTER TABLE `prescription_history` DISABLE KEYS */;

INSERT INTO `prescription_history` (`history_id`, `prescription_id`, `doctor_id`, `patient_id`)
VALUES
	(1,1,2,5),
	(2,2,2,5),
	(3,9,1,4),
	(5,15,1,4),
	(6,16,1,4),
	(7,17,1,4),
	(8,18,1,4),
	(9,19,1,5),
	(10,20,1,5),
	(11,21,1,5),
	(12,22,1,5),
	(13,23,1,5),
	(14,24,1,5),
	(15,25,1,4),
	(16,26,29,5);

/*!40000 ALTER TABLE `prescription_history` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table right
# ------------------------------------------------------------

DROP TABLE IF EXISTS `right`;

CREATE TABLE `right` (
  `right_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `right_name` int(11) DEFAULT NULL,
  PRIMARY KEY (`right_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



# Dump of table role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `role_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;

INSERT INTO `role` (`role_id`, `role_name`)
VALUES
	(1,'administrator'),
	(2,'doctor'),
	(3,'patient'),
	(4,'pharmacist');

/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role_right
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role_right`;

CREATE TABLE `role_right` (
  `role_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `right_id` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



# Dump of table stocks
# ------------------------------------------------------------

DROP TABLE IF EXISTS `stocks`;

CREATE TABLE `stocks` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `phar_id` int(11) NOT NULL,
  `drug_id` int(11) NOT NULL,
  `stock` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

LOCK TABLES `stocks` WRITE;
/*!40000 ALTER TABLE `stocks` DISABLE KEYS */;

INSERT INTO `stocks` (`id`, `phar_id`, `drug_id`, `stock`)
VALUES
	(1,1,1,25),
	(2,1,2,51);

/*!40000 ALTER TABLE `stocks` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL DEFAULT '',
  `role_id` int(11) DEFAULT NULL,
  `type_id` int(11) NOT NULL,
  `user_pwd` varchar(100) NOT NULL DEFAULT '',
  `email_addr` varchar(100) NOT NULL DEFAULT '',
  `reg_date` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`user_id`, `user_name`, `role_id`, `type_id`, `user_pwd`, `email_addr`, `reg_date`)
VALUES
	(1,'stefan',NULL,0,'e594f79c8da9f03759edbb85ed3fbcd5','stefan@uowmai.edu.au','2011-11-11'),
	(2,'tanhao',NULL,3,'d3f1975786cef15e13e716b572f1118c','lu11anxie@uowmail.edu.au','2077-11-10'),
	(3,'kunal',NULL,2,'3a2ee9aff957412159868b99194231f1','kunal@uowmai.edu.au','2077-11-10'),
	(4,'ning_wang',NULL,3,'5c5a6ce6aa24a019204f0ce790eb3111','nw@uowmail.edu.au','2077-11-10'),
	(5,'pengyu_huo',NULL,3,'f965b068b10c3dad498f518603223540','pyh@uowmail.edu.au','2056-10-1'),
	(6,'hrudai',NULL,2,'5f177ed01b042755617a0ccdbb66a2eb','hd@uowmail.edu.au','2014-11-4'),
	(8,'fangyi',NULL,4,'a1ac9fb2a980b96aed5a03cec25d6de6','fy@uowmail.edu.au','2016-11-1'),
	(17,'vegnesh',NULL,3,'02e66f51ea67fe49ccc0964dd35c9b7c','test@uow.edu.au','2016-10-30'),
	(23,'yaofang',NULL,4,'9a0cc9c9d51f37f96f28095ee2d489de','12313!@asdad','2016-10-31'),
	(36,'testdoc',NULL,2,'95c0d64a7388f8875b3f76c906e31da7','asdasd','2016-11-01');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_login_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_login_log`;

CREATE TABLE `user_login_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `login_date` varchar(15) NOT NULL DEFAULT '',
  `login_times` int(11) NOT NULL DEFAULT '0',
  `source` varchar(11) NOT NULL COMMENT '0-web 1-mobile',
  `user_name` varchar(100) NOT NULL DEFAULT '',
  `pwd_try_times` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `user_login_log` WRITE;
/*!40000 ALTER TABLE `user_login_log` DISABLE KEYS */;

INSERT INTO `user_login_log` (`id`, `login_date`, `login_times`, `source`, `user_name`, `pwd_try_times`)
VALUES
	(1,'2016-10-23',15,'WEB','stefan',0),
	(2,'2016-10-23',5,'WEB','tanhao',0),
	(3,'2016-10-24',1,'WEB','stefan',0),
	(4,'2016-10-25',109,'MOBILE','tanhao',0),
	(5,'2016-10-25',6,'WEB','stefan',0),
	(6,'2016-10-26',22,'WEB','stefan',0),
	(7,'2016-10-26',53,'MOBILE','tanhao',0),
	(8,'2016-10-26',9,'WEB','ning_wang',0),
	(9,'2016-10-26',8,'WEB','kunal',0),
	(10,'2016-10-26',2,'WEB','fangyi',0),
	(11,'2016-10-27',14,'WEB','kunal',0),
	(12,'2016-10-27',11,'WEB','ning_wang',0),
	(13,'2016-10-27',5,'WEB','fangyi',0),
	(14,'2016-10-27',12,'WEB','stefan',0),
	(15,'2016-10-27',14,'MOBILE','tanhao',0),
	(16,'2016-10-28',6,'MOBILE','tanhao',0),
	(17,'2016-10-29',7,'WEB','stefan',0),
	(18,'2016-10-30',5,'WEB','stefan',0),
	(19,'2016-10-30',4,'WEB','vegnesh',0),
	(20,'2016-10-31',1,'WEB','yaofang',0),
	(21,'2016-10-31',4,'WEB','fangyi',0),
	(22,'2016-10-31',15,'WEB','kunal',0),
	(23,'2016-10-31',4,'WEB','vegnesh',0),
	(24,'2016-11-01',8,'WEB','kunal',0),
	(25,'2016-11-01',1,'WEB','fangyi',0),
	(26,'2016-11-01',2,'WEB','ning_wang',0),
	(27,'2016-11-01',2,'WEB','testdoc',0),
	(28,'2016-11-01',2,'WEB','stefan',0);

/*!40000 ALTER TABLE `user_login_log` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_type
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_type`;

CREATE TABLE `user_type` (
  `type_id` int(11) unsigned NOT NULL,
  `type_name` varchar(50) DEFAULT NULL,
  UNIQUE KEY `type_id` (`type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `user_type` WRITE;
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;

INSERT INTO `user_type` (`type_id`, `type_name`)
VALUES
	(1,'administrator'),
	(2,'doctor'),
	(3,'patient'),
	(4,'pharmacist'),
	(0,'root');

/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

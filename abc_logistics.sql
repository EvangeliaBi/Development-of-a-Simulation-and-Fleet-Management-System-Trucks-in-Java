CREATE DATABASE  IF NOT EXISTS `abc_logistics` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `abc_logistics`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: abc_logistics
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cities`
--

DROP TABLE IF EXISTS `cities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `customer_count` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cities`
--

LOCK TABLES `cities` WRITE;
/*!40000 ALTER TABLE `cities` DISABLE KEYS */;
INSERT INTO `cities` VALUES (1,'Ioannina',4),(2,'Kastoria',3),(3,'Thessaloniki',6),(4,'Kavala',4),(5,'Larisa',5),(6,'Kozani',3),(7,'Veria',4),(8,'Serres',6),(9,'Xanthi',5),(10,'Volos',4),(11,'Florina',3),(12,'Kilkis',3),(13,'Drama',4),(14,'Alexandroupoli',5),(15,'Thiva',3),(16,'Athens',8),(17,'Agrinio',4),(18,'Halkida',4),(19,'Lamia',3),(20,'Trikala',4);
/*!40000 ALTER TABLE `cities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(100) NOT NULL,
  `city_name` varchar(50) NOT NULL,
  `customer_type` enum('PRODUCER','CONSUMER') NOT NULL,
  `warehouse_type` enum('SHORT','LONG') NOT NULL,
  `weekly_trips` int NOT NULL,
  `volume_per_trip` int NOT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'Customer1','Ioannina','PRODUCER','SHORT',2,100),(2,'Customer2','Ioannina','CONSUMER','LONG',2,90),(3,'Customer3','Ioannina','PRODUCER','LONG',2,110),(4,'Customer4','Ioannina','CONSUMER','SHORT',2,95),(5,'Customer5','Kastoria','PRODUCER','SHORT',2,105),(6,'Customer6','Kastoria','CONSUMER','LONG',2,100),(7,'Customer7','Kastoria','CONSUMER','SHORT',2,90),(8,'Customer8','Thessaloniki','PRODUCER','SHORT',2,120),(9,'Customer9','Thessaloniki','CONSUMER','LONG',2,100),(10,'Customer10','Thessaloniki','PRODUCER','LONG',2,110),(11,'Customer11','Thessaloniki','CONSUMER','SHORT',2,90),(12,'Customer12','Thessaloniki','PRODUCER','SHORT',2,130),(13,'Customer13','Thessaloniki','CONSUMER','LONG',2,95),(14,'Customer14','Kavala','PRODUCER','SHORT',2,105),(15,'Customer15','Kavala','CONSUMER','LONG',2,100),(16,'Customer16','Kavala','PRODUCER','LONG',2,110),(17,'Customer17','Kavala','CONSUMER','SHORT',2,90),(18,'Customer18','Larisa','PRODUCER','SHORT',2,120),(19,'Customer19','Larisa','CONSUMER','LONG',2,100),(20,'Customer20','Larisa','CONSUMER','SHORT',2,95),(21,'Customer21','Larisa','PRODUCER','LONG',2,110),(22,'Customer22','Larisa','CONSUMER','SHORT',2,100),(23,'Customer23','Kozani','PRODUCER','SHORT',2,105),(24,'Customer24','Kozani','CONSUMER','LONG',2,95),(25,'Customer25','Kozani','CONSUMER','SHORT',2,90),(26,'Customer26','Veria','PRODUCER','SHORT',2,110),(27,'Customer27','Veria','CONSUMER','LONG',2,100),(28,'Customer28','Veria','CONSUMER','SHORT',2,95),(29,'Customer29','Veria','PRODUCER','LONG',2,105),(30,'Customer30','Serres','PRODUCER','SHORT',2,120),(31,'Customer31','Serres','CONSUMER','LONG',2,100),(32,'Customer32','Serres','CONSUMER','SHORT',2,95),(33,'Customer33','Serres','PRODUCER','LONG',2,110),(34,'Customer34','Serres','CONSUMER','SHORT',2,100),(35,'Customer35','Serres','PRODUCER','LONG',2,115),(36,'Customer36','Xanthi','PRODUCER','SHORT',2,110),(37,'Customer37','Xanthi','CONSUMER','LONG',2,100),(38,'Customer38','Xanthi','CONSUMER','SHORT',2,95),(39,'Customer39','Xanthi','PRODUCER','LONG',2,105),(40,'Customer40','Xanthi','CONSUMER','SHORT',2,100),(41,'Customer41','Volos','PRODUCER','SHORT',2,110),(42,'Customer42','Volos','CONSUMER','LONG',2,100),(43,'Customer43','Volos','CONSUMER','SHORT',2,95),(44,'Customer44','Volos','PRODUCER','LONG',2,105),(45,'Customer45','Florina','PRODUCER','SHORT',2,100),(46,'Customer46','Florina','CONSUMER','LONG',2,95),(47,'Customer47','Florina','CONSUMER','SHORT',2,90),(48,'Customer48','Kilkis','PRODUCER','SHORT',2,100),(49,'Customer49','Kilkis','CONSUMER','LONG',2,95),(50,'Customer50','Kilkis','CONSUMER','SHORT',2,90),(51,'Customer51','Drama','PRODUCER','SHORT',2,110),(52,'Customer52','Drama','CONSUMER','LONG',2,100),(53,'Customer53','Drama','CONSUMER','SHORT',2,95),(54,'Customer54','Drama','PRODUCER','LONG',2,105),(55,'Customer55','Alexandroupoli','PRODUCER','SHORT',2,110),(56,'Customer56','Alexandroupoli','CONSUMER','LONG',2,100),(57,'Customer57','Alexandroupoli','CONSUMER','SHORT',2,95),(58,'Customer58','Alexandroupoli','PRODUCER','LONG',2,105),(59,'Customer59','Alexandroupoli','CONSUMER','SHORT',2,100),(60,'Customer60','Thiva','PRODUCER','SHORT',2,105),(61,'Customer61','Thiva','CONSUMER','LONG',2,100),(62,'Customer62','Thiva','CONSUMER','SHORT',2,95),(63,'Customer63','Athens','PRODUCER','SHORT',2,120),(64,'Customer64','Athens','CONSUMER','LONG',2,110),(65,'Customer65','Athens','CONSUMER','SHORT',2,100),(66,'Customer66','Athens','PRODUCER','LONG',2,115),(67,'Customer67','Athens','CONSUMER','SHORT',2,95),(68,'Customer68','Athens','PRODUCER','LONG',2,120),(69,'Customer69','Athens','CONSUMER','SHORT',2,100),(70,'Customer70','Athens','PRODUCER','LONG',2,110),(71,'Customer71','Agrinio','PRODUCER','SHORT',2,105),(72,'Customer72','Agrinio','CONSUMER','LONG',2,100),(73,'Customer73','Agrinio','CONSUMER','SHORT',2,95),(74,'Customer74','Agrinio','PRODUCER','LONG',2,110),(75,'Customer75','Halkida','PRODUCER','SHORT',2,105),(76,'Customer76','Halkida','CONSUMER','LONG',2,100),(77,'Customer77','Halkida','CONSUMER','SHORT',2,95),(78,'Customer78','Halkida','PRODUCER','LONG',2,110),(79,'Customer79','Lamia','PRODUCER','SHORT',2,100),(80,'Customer80','Lamia','CONSUMER','LONG',2,95),(81,'Customer81','Lamia','CONSUMER','SHORT',2,90),(82,'Customer82','Trikala','PRODUCER','SHORT',2,110),(83,'Customer83','Trikala','CONSUMER','LONG',2,100),(84,'Customer84','Trikala','CONSUMER','SHORT',2,95),(85,'Customer85','Trikala','PRODUCER','LONG',2,105);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route_stops`
--

DROP TABLE IF EXISTS `route_stops`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `route_stops` (
  `stop_id` int NOT NULL AUTO_INCREMENT,
  `route_id` int NOT NULL,
  `city_name` varchar(50) NOT NULL,
  `leg_km` double NOT NULL,
  PRIMARY KEY (`stop_id`),
  KEY `route_id` (`route_id`),
  CONSTRAINT `route_stops_ibfk_1` FOREIGN KEY (`route_id`) REFERENCES `routes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route_stops`
--

LOCK TABLES `route_stops` WRITE;
/*!40000 ALTER TABLE `route_stops` DISABLE KEYS */;
INSERT INTO `route_stops` VALUES (1,1,'Kozani',0),(2,1,'Kastoria',60),(3,1,'Florina',80),(4,1,'Ioannina',160),(5,1,'Kozani',180),(6,2,'Thessaloniki',0),(7,2,'Kilkis',50),(8,2,'Serres',90),(9,2,'Drama',80),(10,2,'Thessaloniki',160),(11,3,'Larisa',0),(12,3,'Trikala',50),(13,3,'Volos',90),(14,3,'Veria',100),(15,3,'Larisa',95),(16,4,'Volos',0),(17,4,'Larisa',70),(18,4,'Trikala',80),(19,4,'Volos',80),(20,5,'Thessaloniki',0),(21,5,'Serres',90),(22,5,'Drama',80),(23,5,'Kavala',90),(24,5,'Xanthi',80),(25,5,'Alexandroupoli',90),(26,5,'Thessaloniki',90),(27,6,'Athens',0),(28,6,'Halkida',60),(29,6,'Thiva',60),(30,6,'Athens',140),(31,7,'Athens',0),(32,7,'Lamia',120),(33,7,'Agrinio',130),(34,7,'Trikala',140),(35,7,'Athens',130);
/*!40000 ALTER TABLE `route_stops` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `routes`
--

DROP TABLE IF EXISTS `routes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `routes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(10) DEFAULT NULL,
  `start_city` varchar(50) DEFAULT NULL,
  `end_city` varchar(50) DEFAULT NULL,
  `distance_km` double DEFAULT NULL,
  `is_round_trip` tinyint(1) DEFAULT NULL,
  `warehouse_location` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `routes`
--

LOCK TABLES `routes` WRITE;
/*!40000 ALTER TABLE `routes` DISABLE KEYS */;
INSERT INTO `routes` VALUES (1,'R1','Kozani','Ioannina',480,1,'Kozani'),(2,'R2','Thessaloniki','Thessaloniki',380,1,'Thessaloniki'),(3,'R3','Larisa','Larisa',335,1,'Larisa'),(4,'R4','Volos','Volos',230,1,'Volos'),(5,'R5','Thessaloniki','Thessaloniki',520,1,'Thessaloniki'),(6,'R6','Athens','Athens',260,1,'Athens'),(7,'R7','Athens','Athens',520,1,'Athens');
/*!40000 ALTER TABLE `routes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trucks`
--

DROP TABLE IF EXISTS `trucks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trucks` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category` varchar(20) DEFAULT NULL,
  `capacity` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trucks`
--

LOCK TABLES `trucks` WRITE;
/*!40000 ALTER TABLE `trucks` DISABLE KEYS */;
INSERT INTO `trucks` VALUES (1,'Small',10,25),(2,'Medium',23,20),(3,'Large',45,10);
/*!40000 ALTER TABLE `trucks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouses`
--

DROP TABLE IF EXISTS `warehouses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouses` (
  `warehouse_id` int NOT NULL AUTO_INCREMENT,
  `city_name` varchar(50) NOT NULL,
  `short_term_capacity` int NOT NULL,
  `long_term_capacity` int NOT NULL,
  PRIMARY KEY (`warehouse_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouses`
--

LOCK TABLES `warehouses` WRITE;
/*!40000 ALTER TABLE `warehouses` DISABLE KEYS */;
INSERT INTO `warehouses` VALUES (1,'Thessaloniki',1000,8000),(2,'Athens',2000,15000),(3,'Larisa',500,5000),(4,'Volos',1000,3000),(5,'Kozani',2000,8000);
/*!40000 ALTER TABLE `warehouses` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-03 22:13:13

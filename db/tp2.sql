-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: tp2
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `annee`
--

DROP TABLE IF EXISTS `annee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `annee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `label` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `annee`
--

LOCK TABLES `annee` WRITE;
/*!40000 ALTER TABLE `annee` DISABLE KEYS */;
INSERT INTO `annee` VALUES (1,'2023-2024'),(2,'2022-2023');
/*!40000 ALTER TABLE `annee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `anneecourant`
--

DROP TABLE IF EXISTS `anneecourant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anneecourant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_annee` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_annee` (`id_annee`),
  CONSTRAINT `anneecourant_ibfk_1` FOREIGN KEY (`id_annee`) REFERENCES `annee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anneecourant`
--

LOCK TABLES `anneecourant` WRITE;
/*!40000 ALTER TABLE `anneecourant` DISABLE KEYS */;
INSERT INTO `anneecourant` VALUES (1,1);
/*!40000 ALTER TABLE `anneecourant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribution`
--

DROP TABLE IF EXISTS `attribution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attribution` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_annee` int DEFAULT NULL,
  `id_enseignant` int DEFAULT NULL,
  `id_ue` int DEFAULT NULL,
  `enseignant_full_name` varchar(255) DEFAULT NULL,
  `quota_cm` int DEFAULT NULL,
  `quota_td` int DEFAULT NULL,
  `quota_tp` int DEFAULT NULL,
  `quota_cm_effectuer` int DEFAULT NULL,
  `quota_td_effectuer` int DEFAULT NULL,
  `quota_tp_effectuer` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_annee` (`id_annee`),
  KEY `id_enseignant` (`id_enseignant`),
  KEY `id_ue` (`id_ue`),
  CONSTRAINT `attribution_ibfk_1` FOREIGN KEY (`id_annee`) REFERENCES `annee` (`id`),
  CONSTRAINT `attribution_ibfk_2` FOREIGN KEY (`id_enseignant`) REFERENCES `enseignant` (`id`),
  CONSTRAINT `attribution_ibfk_3` FOREIGN KEY (`id_ue`) REFERENCES `ue` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribution`
--

LOCK TABLES `attribution` WRITE;
/*!40000 ALTER TABLE `attribution` DISABLE KEYS */;
INSERT INTO `attribution` VALUES (1,1,2,6,'Sidibe Drissa',1,1,1,0,0,0);
/*!40000 ALTER TABLE `attribution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enseignant`
--

DROP TABLE IF EXISTS `enseignant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enseignant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_annee` int DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `grade` varchar(255) DEFAULT NULL,
  `quota_restant` int DEFAULT NULL,
  `quota_cm_attribuer` int DEFAULT NULL,
  `quota_td_attribuer` int DEFAULT NULL,
  `quota_tp_attribuer` int DEFAULT NULL,
  `quota_cm_effectuer` int DEFAULT NULL,
  `quota_td_effectuer` int DEFAULT NULL,
  `quota_tp_effectuer` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_annee` (`id_annee`),
  CONSTRAINT `enseignant_ibfk_1` FOREIGN KEY (`id_annee`) REFERENCES `annee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enseignant`
--

LOCK TABLES `enseignant` WRITE;
/*!40000 ALTER TABLE `enseignant` DISABLE KEYS */;
INSERT INTO `enseignant` VALUES (2,1,'Sidibe','Drissa','0556884867','dsidibe653@gmail.com','PT',150,1,1,1,0,0,0);
/*!40000 ALTER TABLE `enseignant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ue`
--

DROP TABLE IF EXISTS `ue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ue` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_annee` int DEFAULT NULL,
  `libelle` varchar(255) DEFAULT NULL,
  `nombre_heure_cm` int DEFAULT NULL,
  `nombre_heure_td` int DEFAULT NULL,
  `nombre_heure_tp` int DEFAULT NULL,
  `nombre_heure_rest_cm` int DEFAULT NULL,
  `nombre_heure_rest_td` int DEFAULT NULL,
  `nombre_heure_rest_tp` int DEFAULT NULL,
  `nombre_groupe_cm` int DEFAULT NULL,
  `nombre_groupe_td` int DEFAULT NULL,
  `nombre_groupe_tp` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_annee` (`id_annee`),
  CONSTRAINT `ue_ibfk_1` FOREIGN KEY (`id_annee`) REFERENCES `annee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ue`
--

LOCK TABLES `ue` WRITE;
/*!40000 ALTER TABLE `ue` DISABLE KEYS */;
INSERT INTO `ue` VALUES (6,1,'Analyse',200,150,100,199,149,99,3,2,1);
/*!40000 ALTER TABLE `ue` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-19 21:17:11

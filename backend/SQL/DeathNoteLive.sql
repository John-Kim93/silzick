CREATE DATABASE  IF NOT EXISTS `ssafy_web_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ssafy_web_db`;
-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ssafy_web_db
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(2000) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `hit` int NOT NULL,
  `modified_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `title` varchar(100) NOT NULL,
  `user_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX4bg73mptyc5a2bhcg7p1ycaeq` (`id`),
  KEY `IDXhhyjujkvn45g8sq8qqp7ervpl` (`user_id`),
  KEY `IDXmph7qe4yv41dlsoap3i3nojto` (`title`),
  KEY `IDX65xn7t20y41t5odlnyg95b9g1` (`hit`),
  KEY `IDXjk711ewcdq5de75sinadge90q` (`created_at`),
  KEY `IDXb1ya1wkpuajwu5k48qs231lox` (`modified_at`),
  CONSTRAINT `FKfyf1fchnby6hndhlfaidier1r` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (1,'건의사항1','2022-01-27 21:52:42',4,'2022-02-04 10:27:47','건의사항1','ssafy'),(2,'수정입니다.','2022-01-27 21:52:47',0,'2022-01-27 21:54:19','수정입니다.','ssafy');
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(2000) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `hit` int NOT NULL,
  `modified_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `title` varchar(100) NOT NULL,
  `user_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDXgw7mkx7ax3ye2iptkp6ah0y01` (`id`),
  KEY `IDX2unn94ythunm28ftba9b3bhl2` (`user_id`),
  KEY `IDXj91fphng94vhamxsv1m0txn4k` (`title`),
  KEY `IDXsxshv1p5bnp3955jfugke506u` (`hit`),
  KEY `IDX7dl9dbmlx0eyi8mkk8eaktk07` (`created_at`),
  KEY `IDXbd8rh8w8eoyok1sdxh4fpmtu7` (`modified_at`),
  CONSTRAINT `FKcvf4mh5se36inrxn7xlh2brfv` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (1,'수정입니다.','2022-01-27 20:36:53',1,'2022-01-27 20:48:03','수정','admin'),(2,'test1','2022-01-27 20:37:00',0,'2022-01-27 20:37:00','test1','admin');
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` varchar(20) NOT NULL,
  `authority` varchar(20) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `email` varchar(30) NOT NULL,
  `modified_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  KEY `IDXcyet3f06ja4hs3p0u8pu4y9yl` (`user_id`),
  KEY `IDXob8kqyqqgmefl0aco34akdtpe` (`email`),
  KEY `IDXkiqfjabx9puw3p1eg7kily8kg` (`password`),
  KEY `IDXh04c8c2mnvpd9tifjec6r04ee` (`authority`),
  KEY `IDXk5svc3xrw69o92igy9t8cxegu` (`created_at`),
  KEY `IDX1t5mu2okeia14nvc3dr4holt7` (`modified_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('admin','ROLE_ADMIN','2022-01-27 20:36:16','admin@admin.com','2022-01-27 20:36:16','{bcrypt}$2a$10$0fFqTYn1xbx605bL./Eh5uBqUaB0/RN9CwJaCVSNAMmdPO3wZ1/XO'),('ssafy','ROLE_USER','2022-01-27 20:34:14','ssafy@ssafy.com','2022-01-27 20:34:14','{bcrypt}$2a$10$KsXPVlxSkl68r9stiSB0iuTNCI7gd/k8P/Fe1W3pbxzwCbgojM.em');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-07 10:52:21

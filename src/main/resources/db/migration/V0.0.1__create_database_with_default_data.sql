CREATE TABLE `recreation_not_allowed` (`something` int(11) NOT NULL);

-- MySQL dump 10.13  Distrib 5.7.30, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: payments
-- ------------------------------------------------------
-- Server version       5.7.28

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
-- Table structure for table `allowed_type_currency`
--

DROP TABLE IF EXISTS `allowed_type_currency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `allowed_type_currency` (
                                         `id` int(11) NOT NULL AUTO_INCREMENT,
                                         `type_id` int(11) NOT NULL,
                                         `currency_id` int(11) NOT NULL,
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `allowed_type_currency_type_id_currency_id_uindex` (`type_id`,`currency_id`),
                                         KEY `allowed_type_currency_currency_id_fk` (`currency_id`),
                                         CONSTRAINT `allowed_type_currency_currency_id_fk` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`),
                                         CONSTRAINT `allowed_type_currency_payment_type_id_fk` FOREIGN KEY (`type_id`) REFERENCES `payment_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allowed_type_currency`
--

LOCK TABLES `allowed_type_currency` WRITE;
/*!40000 ALTER TABLE `allowed_type_currency` DISABLE KEYS */;
INSERT INTO `allowed_type_currency` VALUES (1,1,1),(2,2,2),(3,3,1),(4,3,2);
/*!40000 ALTER TABLE `allowed_type_currency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `canceled_payment`
--

DROP TABLE IF EXISTS `canceled_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `canceled_payment` (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `existing_payment_id` int(11) NOT NULL,
                                    `canceled_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `canceled_payments_existing_payment_id_uindex` (`existing_payment_id`),
                                    CONSTRAINT `canceled_payments_existing_payments_id_fk` FOREIGN KEY (`existing_payment_id`) REFERENCES `existing_payment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `canceled_payment`
--

LOCK TABLES `canceled_payment` WRITE;
/*!40000 ALTER TABLE `canceled_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `canceled_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `login` varchar(64) NOT NULL,
                          `encrypted_pass` varchar(128) NOT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `clients_login_uindex` (`login`),
                          KEY `clients_login_encrypted_pass_index` (`login`,`encrypted_pass`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client_action`
--

DROP TABLE IF EXISTS `client_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client_action` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `client_id` int(11) NOT NULL,
                                 `client_ip_id` int(11) NOT NULL,
                                 `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_action`
--

LOCK TABLES `client_action` WRITE;
/*!40000 ALTER TABLE `client_action` DISABLE KEYS */;
/*!40000 ALTER TABLE `client_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client_ip`
--

DROP TABLE IF EXISTS `client_ip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client_ip` (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `ip_address_int` int(11) NOT NULL,
                             `ip_address` varchar(15) NOT NULL,
                             `domain` varchar(128) DEFAULT NULL,
                             `country_code` varchar(2) DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `client_ip_ip_address_int_domain_uindex` (`ip_address_int`,`domain`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_ip`
--

LOCK TABLES `client_ip` WRITE;
/*!40000 ALTER TABLE `client_ip` DISABLE KEYS */;
/*!40000 ALTER TABLE `client_ip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `currency`
--

DROP TABLE IF EXISTS `currency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `currency` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `iso_code` varchar(3) DEFAULT NULL,
                            `coefficient` float DEFAULT NULL,
                            `is_primary` int(11) DEFAULT NULL,
                            `last_update_time` timestamp NULL DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `currency_is_primary_uindex` (`is_primary`),
                            UNIQUE KEY `currency_iso_code_uindex` (`iso_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currency`
--

LOCK TABLES `currency` WRITE;
/*!40000 ALTER TABLE `currency` DISABLE KEYS */;
INSERT INTO `currency` VALUES (1,'EUR',NULL,1,NULL),(2,'USD',NULL,NULL,NULL);
/*!40000 ALTER TABLE `currency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `currency_stamp`
--

DROP TABLE IF EXISTS `currency_stamp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `currency_stamp` (
                                  `id` int(11) DEFAULT NULL,
                                  `main_currency_id` int(11) NOT NULL,
                                  `additional_currency_id` int(11) DEFAULT NULL,
                                  `currency_rate` float DEFAULT NULL,
                                  KEY `currency_stamp_currency_id_fk` (`main_currency_id`),
                                  KEY `currency_stamp_currency_id_fk_2` (`additional_currency_id`),
                                  CONSTRAINT `currency_stamp_currency_id_fk` FOREIGN KEY (`main_currency_id`) REFERENCES `currency` (`id`),
                                  CONSTRAINT `currency_stamp_currency_id_fk_2` FOREIGN KEY (`additional_currency_id`) REFERENCES `currency` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currency_stamp`
--

LOCK TABLES `currency_stamp` WRITE;
/*!40000 ALTER TABLE `currency_stamp` DISABLE KEYS */;
/*!40000 ALTER TABLE `currency_stamp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `existing_payment`
--

DROP TABLE IF EXISTS `existing_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `existing_payment` (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `payment_type_id` int(11) NOT NULL,
                                    `client_action_id` int(11) NOT NULL,
                                    `unique_id` int(11) NOT NULL,
                                    `currency_id` int(11) NOT NULL,
                                    `payment_amount` float NOT NULL,
                                    `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `existing_payment_unique_id_uindex` (`unique_id`),
                                    KEY `made_payments_payment_types_id_fk` (`payment_type_id`),
                                    KEY `existing_payment_client_action_id_fk` (`client_action_id`),
                                    KEY `existing_payment_currency_id_fk` (`currency_id`),
                                    CONSTRAINT `existing_payment_client_action_id_fk` FOREIGN KEY (`client_action_id`) REFERENCES `client_action` (`id`),
                                    CONSTRAINT `existing_payment_currency_id_fk` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`),
                                    CONSTRAINT `existing_payment_unique_id_id_fk` FOREIGN KEY (`unique_id`) REFERENCES `unique_id` (`id`),
                                    CONSTRAINT `made_payments_payment_types_id_fk` FOREIGN KEY (`payment_type_id`) REFERENCES `payment_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `existing_payment`
--

LOCK TABLES `existing_payment` WRITE;
/*!40000 ALTER TABLE `existing_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `existing_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_fee`
--

DROP TABLE IF EXISTS `payment_fee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_fee` (
                               `id` int(11) DEFAULT NULL,
                               `cancel_id` int(11) NOT NULL,
                               `currency_id` int(11) NOT NULL,
                               `type_id` int(11) NOT NULL,
                               `fee_amount` float NOT NULL,
                               `fee_coefficient` float NOT NULL,
                               `calculated_at` datetime NOT NULL,
                               UNIQUE KEY `payment_fee_cancel_id_uindex` (`cancel_id`),
                               KEY `payment_fee_currency_id_fk` (`currency_id`),
                               KEY `payment_fee_payment_type_id_fk` (`type_id`),
                               CONSTRAINT `payment_fee_canceled_payment_id_fk` FOREIGN KEY (`cancel_id`) REFERENCES `canceled_payment` (`id`),
                               CONSTRAINT `payment_fee_currency_id_fk` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`),
                               CONSTRAINT `payment_fee_payment_type_id_fk` FOREIGN KEY (`type_id`) REFERENCES `payment_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_fee`
--

LOCK TABLES `payment_fee` WRITE;
/*!40000 ALTER TABLE `payment_fee` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment_fee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_type`
--

DROP TABLE IF EXISTS `payment_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_type` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `type_name` varchar(16) NOT NULL,
                                `fee_coefficient` float NOT NULL,
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `payment_types_type_name_uindex` (`type_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_type`
--

LOCK TABLES `payment_type` WRITE;
/*!40000 ALTER TABLE `payment_type` DISABLE KEYS */;
INSERT INTO `payment_type` VALUES (1,'TYPE1',0.05),(2,'TYPE2',0.1),(3,'TYPE3',0.15);
/*!40000 ALTER TABLE `payment_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unique_id`
--

DROP TABLE IF EXISTS `unique_id`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unique_id` (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `hash_value` varchar(32) NOT NULL,
                             `generation_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `unique_id_hash_value_uindex` (`hash_value`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unique_id`
--

LOCK TABLES `unique_id` WRITE;
/*!40000 ALTER TABLE `unique_id` DISABLE KEYS */;
/*!40000 ALTER TABLE `unique_id` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-20 11:18:48

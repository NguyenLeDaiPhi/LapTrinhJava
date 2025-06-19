-- ----------------------------
-- Host: localhost    Database: jcertpre
-- Server version: 8.0.42
-- ----------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40101 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- ----------------------------
-- Table structure for `exam_question`
-- ----------------------------
DROP TABLE IF EXISTS `exam_question`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `exam_question` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `exam_type` VARCHAR(50) DEFAULT NULL,
  `question` TEXT NOT NULL,
  `correct_answer` TEXT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

-- ----------------------------
-- Table structure for `exam_choice`
-- ----------------------------
DROP TABLE IF EXISTS `exam_choice`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `exam_choice` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `question_id` BIGINT NOT NULL,
  `content` TEXT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`question_id`) REFERENCES `exam_question`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

-- ----------------------------
-- Dumping data for table `exam_question`
-- ----------------------------
LOCK TABLES `exam_question` WRITE;
/*!40000 ALTER TABLE `exam_question` DISABLE KEYS */;

INSERT INTO `exam_question` (`id`, `exam_type`, `question`, `correct_answer`) VALUES
(1, 'JLPT-N3', 'What is the correct reading of the word “勉強”?', 'benkyou'),
(2, 'NAT-TEST N4', 'Which word best completes the sentence: “Yesterday, I went to the ___ with my friend.”', 'movie');

/*!40000 ALTER TABLE `exam_question` ENABLE KEYS */;
UNLOCK TABLES;

-- ----------------------------
-- Dumping data for table `exam_choice`
-- ----------------------------
LOCK TABLES `exam_choice` WRITE;
/*!40000 ALTER TABLE `exam_choice` DISABLE KEYS */;

INSERT INTO `exam_choice` (`id`, `question_id`, `content`) VALUES
(1, 1, 'benkyou'),
(2, 1, 'kenkyuu'),
(3, 1, 'dokusho'),
(4, 1, 'bentou'),
(5, 2, 'movie'),
(6, 2, 'cooking'),
(7, 2, 'exercise'),
(8, 2, 'photograph');

/*!40000 ALTER TABLE `exam_choice` ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

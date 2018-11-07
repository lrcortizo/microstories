CREATE DATABASE IF NOT EXISTS `dgss1819_teamB_microstories`
DEFAULT CHARACTER SET = utf8mb4
DEFAULT COLLATE utf8mb4_unicode_ci;

USE `dgss1819_teamB_microstories`;

DROP TABLE IF EXISTS `Story`;

--
-- Table structure for table `Story`
--
CREATE TABLE `Story` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `text` varchar(1000) NOT NULL,
  `author` varchar(50) NOT NULL,
  `publicationDate` date NOT NULL,
  `genre` varchar(9) NOT NULL,
  `primaryTheme` varchar(14) NOT NULL,
  `secondaryTheme` varchar(14),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- User creation
--
GRANT ALL PRIVILEGES ON dgss1819_teamB_microstories.* TO microstories@localhost IDENTIFIED BY 'microstoriespass';
FLUSH PRIVILEGES;

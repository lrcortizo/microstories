CREATE DATABASE IF NOT EXISTS `dgss1819_teamB_microstories`
DEFAULT CHARACTER SET = utf8mb4
DEFAULT COLLATE utf8mb4_unicode_ci;

USE `dgss1819_teamB_microstories`;

DROP TABLE IF EXISTS `Story`;
DROP TABLE IF EXISTS `User`;

--
-- Table structure for table `User`
--
CREATE TABLE `User` (
  `role` varchar(6) NOT NULL,
  `login` varchar(100) NOT NULL,
  `password` varchar(32) NOT NULL,
   PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `Story`
--
CREATE TABLE `Story` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `text` varchar(1000) NOT NULL,
  `author` varchar(50) NOT NULL,
  `publicationDate` datetime NOT NULL,
  `genre` varchar(9) NOT NULL,
  `primaryTheme` varchar(14) NOT NULL,
  `secondaryTheme` varchar(14),
  `views` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- User creation
--
GRANT ALL PRIVILEGES ON dgss1819_teamB_microstories.* TO microstories@localhost IDENTIFIED BY 'microstoriespass';
FLUSH PRIVILEGES;

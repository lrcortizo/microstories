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
  `publicationDate` datetime NOT NULL,
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

--
-- Data for table `Story`
--
INSERT INTO `Story` (title, text, author, publicationDate, 
genre, primaryTheme, secondaryTheme) VALUES
	('Microrrelato 1','Texto del microrrelato 1','Autor 1', '2012-06-18',
	'STORY', 'HISTORY','CHILDREN'),
	('Microrrelato 2','Texto del microrrelato 2','Autor 2', '1999-01-24',
	'NANOSTORY', 'ADVENTURE', 'ROMANTIC'),
	('Microrrelato 3','Texto del microrrelato 3','Autor 3', '2005-03-09',
	'POETRY', 'THRILLER', 'CHILDREN'),
	('Microrrelato 4','Texto del microrrelato 4','Autor 4', '2016-11-13',
	'STORY', 'CHILDREN', 'ROMANTIC'),
	('Microrrelato 5','Texto del microrrelato 5','Autor 5', '2009-08-30',
	'NANOSTORY', 'HORROR', 'THRILLER'),
	('Microrrelato 6','Texto del microrrelato 6','Autor 6', '2015-10-02',
	'POETRY', 'HORROR', 'HISTORY'),
	('Microrrelato 7','Texto del microrrelato 7','Autor 7', '2002-10-02',
	'POETRY', 'THRILLER', 'ROMANTIC');

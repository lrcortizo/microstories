USE `dgss1819_teamB_microstories`;
--
-- Data for table `User`
--
INSERT INTO `User` (role, login, password) VALUES
	('ADMIN','jose','a3f6f4b40b24e2fd61f08923ed452f34'),
	('AUTHOR','ana','22beeae33e9b2657f9610621502cd7a4'),
	('AUTHOR','juan','b4fbb95580592697dc71488a1f19277e'),
	('AUTHOR','lorena','05009e420932c21e5a68f5ef1aadd530'),
	('AUTHOR','pepe','b43b4d046860b2bd945bca2597bf9f07'),
	('AUTHOR','ramon','c818246591e3c7b64f2fc1f0d3b5570c'),
    ('AUTHOR','roberto','b674b4600a4da2fed5a2425c363de935'),
    ('AUTHOR','laura','eab2e780b6c0a48ca3e46738d64073eb');

--
-- Data for table `Story`
--
INSERT INTO `Story` (title, text, author, publicationDate,
genre, primaryTheme, secondaryTheme, views) VALUES
	('Microrrelato 1','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam vel cursus nunc. Nam posuere mauris quis odio maximus, non pharetra ipsum semper. Mauris vel augue a nunc lacinia faucibus et efficitur quam. Sed interdum ex vitae porta maximus. Maecenas tincidunt sed nulla sed tincidunt. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus eleifend posuere.','ana', '2012-06-18',
	'STORY', 'HISTORY','CHILDREN', 0),
	('Microrrelato 2','Lorem ipsum dolor sit posuere.','juan', '1999-01-24',
	'NANOSTORY', 'ADVENTURE', 'ROMANTIC', 0),
	('Microrrelato 3','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vestibulum id sed.','lorena', '2005-03-09',
	'POETRY', 'THRILLER', 'CHILDREN', 0),
	('Microrrelato 4','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam vel cursus nunc. Nam posuere mauris quis odio maximus, non pharetra ipsum semper. Mauris vel augue a nunc lacinia faucibus et efficitur quam. Sed interdum ex vitae porta maximus. Maecenas tincidunt sed nulla sed tincidunt. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus eleifend posuere.','pepe', '2016-11-13',
	'STORY', 'CHILDREN', 'ROMANTIC', 0),
	('Microrrelato 5','Lorem ipsum dolor sit posuere.','ramon', '2009-08-30',
	'NANOSTORY', 'HORROR', 'THRILLER', 0),
	('Microrrelato 6','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vestibulum id sed.','roberto', '2015-10-02',
	'POETRY', 'HORROR', 'HISTORY', 0),
	('Microrrelato 7','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vestibulum id sed.','laura', '2002-10-02',
	'POETRY', 'THRILLER', 'ROMANTIC', 0);
CREATE DATABASE content CHARACTER SET utf8 COLLATE utf8_unicode_ci;
CREATE USER 'test'@'localhost' IDENTIFIED BY 'test';
GRANT ALL ON content.* TO 'test'@'localhost';
FLUSH PRIVILEGES;
USE content;

CREATE TABLE nested_category (
	category_id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(20) NOT NULL,
	lft INT NOT NULL,
	rgt INT NOT NULL
) ENGINE InnoDB COLLATE='utf8_unicode_ci';

CREATE TABLE product(
	product_id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(40),
	category_id INT NOT NULL
) ENGINE InnoDB COLLATE='utf8_unicode_ci';


INSERT INTO nested_category
VALUES(1,'ELECTRONICS',1,20),(2,'TELEVISIONS',2,9),(3,'TUBE',3,4),
(4,'LCD',5,6),(5,'PLASMA',7,8),(6,'PORTABLE ELECTRONICS',10,19),
(7,'MP3 PLAYERS',11,14),(8,'FLASH',12,13),
(9,'CD PLAYERS',15,16),(10,'2 WAY RADIOS',17,18);

INSERT INTO product(name, category_id) VALUES('20" TV',3),('36" TV',3),
('Super-LCD 42"',4),('Ultra-Plasma 62"',5),('Value Plasma 38"',5),
('Power-MP3 5gb',7),('Super-Player 1gb',8),('Porta CD',9),('CD To go!',9),
('Family Talk 360',10);
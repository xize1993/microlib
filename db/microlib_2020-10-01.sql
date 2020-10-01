DROP database IF EXISTS `microlib`;
create database `microlib` default character set utf8mb4;
use microlib;

DROP TABLE IF EXISTS `t_author`;
CREATE TABLE `t_author` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author_name` varchar(255) DEFAULT NULL,
  `author_name_kana` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `description` varchar(2500) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `flag` bit(1) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `update_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

LOCK TABLES `t_author` WRITE;

INSERT INTO `t_author` (`id`, `author_name`, `author_name_kana`, `birthday`, `description`, `create_time`, `create_user_id`, `flag`, `update_time`, `update_user_id`)
VALUES (1,'test','',NULL,'','2020-10-01 10:50:11.010265',1,b'1','2020-10-01 10:53:41.557967',1);

UNLOCK TABLES;


DROP TABLE IF EXISTS `t_book`;

CREATE TABLE `t_book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `cover_img_url` varchar(255) DEFAULT NULL,
  `description` varchar(2500) DEFAULT NULL,
  `have_cover` bit(1) NOT NULL,
  `isbn` varchar(255) DEFAULT NULL,
  `page_count` int(11) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `publication_date` date DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `flag` bit(1) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `update_user_id` int(11) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjchbplv6o6eqfu9kdwp302a8v` (`author_id`),
  CONSTRAINT `FKjchbplv6o6eqfu9kdwp302a8v` FOREIGN KEY (`author_id`) REFERENCES `t_author` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
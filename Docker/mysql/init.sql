DROP database IF EXISTS `microlib`;
create database `microlib` default character set utf8mb4;
use microlib;

DROP TABLE IF EXISTS `t_author`;
CREATE TABLE `t_author` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author_name` varchar(50) DEFAULT NULL,
  `author_name_kana` varchar(50) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `description` text,
  `create_time` datetime(6) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `flag` tinyint(1) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `update_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

LOCK TABLES `t_author` WRITE;

INSERT INTO `t_author` (`id`, `author_name`, `author_name_kana`, `birthday`, `description`, `create_time`, `create_user_id`, `flag`, `update_time`, `update_user_id`)
VALUES (1,'test','',NULL,'','2020-10-01 10:50:11.010265',1,b'1','2020-10-01 10:53:41.557967',1);

UNLOCK TABLES;

DROP TABLE IF EXISTS `t_book`;

CREATE TABLE `t_book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `cover_img_url` varchar(500) DEFAULT NULL,
  `have_cover` tinyint(1) NOT NULL,
  `isbn` varchar(255) DEFAULT NULL,
  `page_count` int(11) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `publication_date` date DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL,
  `description` text,
  `create_user_id` int(11) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `flag` tinyint(1) DEFAULT NULL,
  `update_user_id` int(11) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
CREATE DATABASE `MeetResv` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
use MeetResv;


CREATE TABLE `mr_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `department` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `role` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `mr_meetingroom` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_no` varchar(45) COLLATE utf8_bin NOT NULL,
  `mr_ext` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `mr_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_no` varchar(45) COLLATE utf8_bin NOT NULL,
  `user` varchar(45) COLLATE utf8_bin NOT NULL,
  `day` varchar(10) COLLATE utf8_bin NOT NULL,
  `period` varchar(255) COLLATE utf8_bin NOT NULL,
  `purpose` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_time` (`room_no`,`day`,`period`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `wechat_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wechat_id` varchar(50) COLLATE utf8_bin NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

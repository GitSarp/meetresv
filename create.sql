CREATE DATABASE `MeetResv` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
use MeetResv;


CREATE TABLE `MeetResv`.`mr_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NULL,
  `password` varchar(255) NULL,
  `department` varchar(255) NULL,
  `phone` varchar(255) NULL,
  `role` tinyint(1) NULL,
  PRIMARY KEY (`id`)
);



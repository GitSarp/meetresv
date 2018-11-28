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

CREATE TABLE `MeetResv`.`mr_meetingroom` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `room_no` VARCHAR(45) NOT NULL,
  `mr_ext` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `MeetResv`.`mr_order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `mr_room_no` VARCHAR(45) NOT NULL,
  `mr_user` VARCHAR(45) NOT NULL,
  `mr_interval` VARCHAR(255) NOT NULL,
  `mr_purpose` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));


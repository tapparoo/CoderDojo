-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema codedojodb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `codedojodb` ;

-- -----------------------------------------------------
-- Schema codedojodb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `codedojodb` DEFAULT CHARACTER SET utf8 ;
USE `codedojodb` ;

-- -----------------------------------------------------
-- Table `address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `address` ;

CREATE TABLE IF NOT EXISTS `address` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `street` VARCHAR(45) NULL,
  `street_2` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(45) NULL,
  `zip` INT NULL,
  `country` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `location` ;

CREATE TABLE IF NOT EXISTS `location` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `details` VARCHAR(100) NULL,
  `address_id` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `locationcol_UNIQUE` (`name` ASC),
  INDEX `location_to_address_idx` (`address_id` ASC),
  CONSTRAINT `location_to_address`
    FOREIGN KEY (`address_id`)
    REFERENCES `address` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NULL,
  `password` VARCHAR(255) NULL,
  `enabled` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_detail`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_detail` ;

CREATE TABLE IF NOT EXISTS `user_detail` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `dob` DATE NULL,
  `nickname` VARCHAR(45) NULL,
  `phone_number` VARCHAR(45) NULL,
  `first_name` VARCHAR(100) NULL,
  `last_name` VARCHAR(100) NULL,
  `email` VARCHAR(100) NULL,
  `gender` VARCHAR(20) NULL,
  `user_image_url` VARCHAR(500) NULL,
  `user_id` INT NULL,
  `location_id` INT NULL,
  `address_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `student_has_school_location_idx` (`location_id` ASC),
  INDEX `user_has_user_id_idx` (`user_id` ASC),
  INDEX `user_has_address_idx` (`address_id` ASC),
  CONSTRAINT `user_has_school_location`
    FOREIGN KEY (`location_id`)
    REFERENCES `location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user_has_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user_has_address`
    FOREIGN KEY (`address_id`)
    REFERENCES `address` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `achievement`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `achievement` ;

CREATE TABLE IF NOT EXISTS `achievement` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(500) NULL,
  `image_url` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_achievement`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_achievement` ;

CREATE TABLE IF NOT EXISTS `user_achievement` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `achieved` TINYINT NULL,
  `achieved_date` DATE NULL,
  `user_detail_id` INT NULL,
  `achievement_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `achievement_is_owned_by_student_idx` (`user_detail_id` ASC),
  INDEX `user_achievement_references_an_achievement_idx` (`achievement_id` ASC),
  CONSTRAINT `user_achievement_is_owned_by_student`
    FOREIGN KEY (`user_detail_id`)
    REFERENCES `user_detail` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user_achievement_references_an_achievement`
    FOREIGN KEY (`achievement_id`)
    REFERENCES `achievement` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `goal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `goal` ;

CREATE TABLE IF NOT EXISTS `goal` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(500) NULL,
  `standard_achievement_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `goals_belong_to_achievement_idx` (`standard_achievement_id` ASC),
  CONSTRAINT `goals_belong_to_achievement`
    FOREIGN KEY (`standard_achievement_id`)
    REFERENCES `achievement` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_goal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_goal` ;

CREATE TABLE IF NOT EXISTS `user_goal` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `completed` TINYINT NULL,
  `completed_date` DATE NULL,
  `user_achievement_id` INT NULL,
  `goal_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `goals_belong_to_a_belt_idx` (`user_achievement_id` ASC),
  INDEX `user_goals_to_parent_goal_idx` (`goal_id` ASC),
  CONSTRAINT `user_goals_belong_to_a_user_achievement`
    FOREIGN KEY (`user_achievement_id`)
    REFERENCES `user_achievement` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user_goals_to_parent_goal`
    FOREIGN KEY (`goal_id`)
    REFERENCES `goal` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `meeting`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `meeting` ;

CREATE TABLE IF NOT EXISTS `meeting` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `location_id` INT NULL,
  `date_time` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `meeting_has_a_location_idx` (`location_id` ASC),
  CONSTRAINT `meeting_has_a_location`
    FOREIGN KEY (`location_id`)
    REFERENCES `location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `meeting_attendance`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `meeting_attendance` ;

CREATE TABLE IF NOT EXISTS `meeting_attendance` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `attended` TINYINT NULL,
  `meeting_id` INT NULL,
  `user_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `meeting_reference_idx` (`meeting_id` ASC),
  INDEX `user_reference_idx` (`user_id` ASC),
  CONSTRAINT `meeting_reference`
    FOREIGN KEY (`meeting_id`)
    REFERENCES `meeting` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user_reference`
    FOREIGN KEY (`user_id`)
    REFERENCES `user_detail` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `parent_child_relationship`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `parent_child_relationship` ;

CREATE TABLE IF NOT EXISTS `parent_child_relationship` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `parent_id` INT NULL,
  `child_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `parent_of_the_relationship_idx` (`parent_id` ASC),
  INDEX `child_of_the_relationship_idx` (`child_id` ASC),
  CONSTRAINT `parent_of_the_relationship`
    FOREIGN KEY (`parent_id`)
    REFERENCES `user_detail` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `child_of_the_relationship`
    FOREIGN KEY (`child_id`)
    REFERENCES `user_detail` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `role` ;

CREATE TABLE IF NOT EXISTS `role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_role` ;

CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` INT NULL,
  `role_id` INT NULL,
  INDEX `user_to_user_role_idx` (`user_id` ASC),
  INDEX `role_to_user_role_idx` (`role_id` ASC),
  CONSTRAINT `user_to_user_role`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `role_to_user_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE = '';
DROP USER IF EXISTS codedojouser;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'codedojouser' IDENTIFIED BY 'codedojouser';

GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE * TO 'codedojouser';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `address`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `address` (`id`, `street`, `street_2`, `city`, `state`, `zip`, `country`) VALUES (1, '123 test dr', 'apt 99', 'Boulder', 'CO', 80303, 'USA');
INSERT INTO `address` (`id`, `street`, `street_2`, `city`, `state`, `zip`, `country`) VALUES (2, '7400 E Orchard Rd ', '7400 E Orchard Rd #1450n,', 'Greenwood Village', 'CO', 80111, 'USA');
INSERT INTO `address` (`id`, `street`, `street_2`, `city`, `state`, `zip`, `country`) VALUES (3, '1331 17th st', 'Lower level(Basement)', 'Denver', 'CO', 80202, 'USA');
INSERT INTO `address` (`id`, `street`, `street_2`, `city`, `state`, `zip`, `country`) VALUES (4, '1545 S Yates st', NULL, 'Denver', 'CO', 80219, 'USA');
INSERT INTO `address` (`id`, `street`, `street_2`, `city`, `state`, `zip`, `country`) VALUES (5, '1485 Delgany St', NULL, 'Denver', 'CO', 80202, 'USA');

COMMIT;


-- -----------------------------------------------------
-- Data for table `location`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `location` (`id`, `name`, `details`, `address_id`) VALUES (1, 'Downtown Dojo at Turing', 'this is our main classroom in the area. ', 1);
INSERT INTO `location` (`id`, `name`, `details`, `address_id`) VALUES (2, 'Remote Classroom', 'SD is our backup room.', 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `user`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `user` (`id`, `username`, `password`, `enabled`) VALUES (1, 'anna', '$2a$10$H7T2hXZ16ux.5nV/04JM5uSR8CC2lUSll9p2tk8xr/DPyT7JR5Vhi', 1);
INSERT INTO `user` (`id`, `username`, `password`, `enabled`) VALUES (2, 'steve', '$2a$10$H7T2hXZ16ux.5nV/04JM5uSR8CC2lUSll9p2tk8xr/DPyT7JR5Vhi', 1);
INSERT INTO `user` (`id`, `username`, `password`, `enabled`) VALUES (3, 'adam', '$2a$10$H7T2hXZ16ux.5nV/04JM5uSR8CC2lUSll9p2tk8xr/DPyT7JR5Vhi', 1);
INSERT INTO `user` (`id`, `username`, `password`, `enabled`) VALUES (4, 'mark', '$2a$10$H7T2hXZ16ux.5nV/04JM5uSR8CC2lUSll9p2tk8xr/DPyT7JR5Vhi', 1);
INSERT INTO `user` (`id`, `username`, `password`, `enabled`) VALUES (5, 'todd', '$2a$10$H7T2hXZ16ux.5nV/04JM5uSR8CC2lUSll9p2tk8xr/DPyT7JR5Vhi', 1);
INSERT INTO `user` (`id`, `username`, `password`, `enabled`) VALUES (6, 'john', '$2a$10$H7T2hXZ16ux.5nV/04JM5uSR8CC2lUSll9p2tk8xr/DPyT7JR5Vhi', 1);
INSERT INTO `user` (`id`, `username`, `password`, `enabled`) VALUES (7, 'young', '$2a$10$H7T2hXZ16ux.5nV/04JM5uSR8CC2lUSll9p2tk8xr/DPyT7JR5Vhi', 1);
INSERT INTO `user` (`id`, `username`, `password`, `enabled`) VALUES (8, 'eric', '$2a$10$H7T2hXZ16ux.5nV/04JM5uSR8CC2lUSll9p2tk8xr/DPyT7JR5Vhi', 1);
INSERT INTO `user` (`id`, `username`, `password`, `enabled`) VALUES (9, 'jesse', '$2a$10$H7T2hXZ16ux.5nV/04JM5uSR8CC2lUSll9p2tk8xr/DPyT7JR5Vhi', 1);
INSERT INTO `user` (`id`, `username`, `password`, `enabled`) VALUES (10, 'greg', '$2a$10$H7T2hXZ16ux.5nV/04JM5uSR8CC2lUSll9p2tk8xr/DPyT7JR5Vhi', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `user_detail`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `user_detail` (`id`, `dob`, `nickname`, `phone_number`, `first_name`, `last_name`, `email`, `gender`, `user_image_url`, `user_id`, `location_id`, `address_id`) VALUES (1, '1988-03-28', 'A.J.', '123-456-7890', 'Anna', 'Jimenez', 'anna_j_@cryptokitties.org', 'f', 'https://images.pexels.com/photos/1054251/pexels-photo-1054251.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500', 1, 1, 3);
INSERT INTO `user_detail` (`id`, `dob`, `nickname`, `phone_number`, `first_name`, `last_name`, `email`, `gender`, `user_image_url`, `user_id`, `location_id`, `address_id`) VALUES (2, '1975-07-11', 'SD Steve', '098-765-4321', 'Steve', 'Thompson', 'sdsteve@sd.com', 'm', 'https://cdn.pixabay.com/photo/2014/03/12/18/42/boy-286241_1280.jpg', 2, 1, 4);
INSERT INTO `user_detail` (`id`, `dob`, `nickname`, `phone_number`, `first_name`, `last_name`, `email`, `gender`, `user_image_url`, `user_id`, `location_id`, `address_id`) VALUES (3, '1995-01-01', 'a-tappy', '567-567-5678', 'Adam', 'Tappy', 'a_tappy@myspace.com', 'm', 'https://images.pexels.com/photos/555790/pexels-photo-555790.png?auto=compress&cs=tinysrgb&dpr=2&w=500', 3, 2, 5);
INSERT INTO `user_detail` (`id`, `dob`, `nickname`, `phone_number`, `first_name`, `last_name`, `email`, `gender`, `user_image_url`, `user_id`, `location_id`, `address_id`) VALUES (4, '2000-12-31', 'Marky-mark', '543-543-5432', 'Mark', 'Mahowald', 'mark_mahowald@test.com', 'o', 'https://image.shutterstock.com/image-photo/photo-adorable-teenage-young-happy-450w-1023604108.jpg', 4, 2, 4);
INSERT INTO `user_detail` (`id`, `dob`, `nickname`, `phone_number`, `first_name`, `last_name`, `email`, `gender`, `user_image_url`, `user_id`, `location_id`, `address_id`) VALUES (5, '2000-01-01', 'John', '123-123-1234', 'John', 'Overberg', 'airtrafficcontrollguy@yahoo.com', 'm', 'https://image.shutterstock.com/image-photo/typical-american-teenager-posing-camera-450w-172430795.jpg', 6, 1, 3);
INSERT INTO `user_detail` (`id`, `dob`, `nickname`, `phone_number`, `first_name`, `last_name`, `email`, `gender`, `user_image_url`, `user_id`, `location_id`, `address_id`) VALUES (6, '1900-03-05', 'Todd', '456-456-4567', 'Todd', 'Trobridge', 'mightyrabbit@aol.com', 'm', 'https://image.shutterstock.com/image-photo/teenager-boy-on-computer-his-450w-276730304.jpg', 5, 2, 3);
INSERT INTO `user_detail` (`id`, `dob`, `nickname`, `phone_number`, `first_name`, `last_name`, `email`, `gender`, `user_image_url`, `user_id`, `location_id`, `address_id`) VALUES (7, '2000-04-05', 'Young', '720-409-1234', 'Young', 'Chen', 'cool_guy87@test.com', 'o', 'https://image.shutterstock.com/image-photo/teenage-brothers-beach-castle-background-450w-342673352.jpg', 7, 1, 3);
INSERT INTO `user_detail` (`id`, `dob`, `nickname`, `phone_number`, `first_name`, `last_name`, `email`, `gender`, `user_image_url`, `user_id`, `location_id`, `address_id`) VALUES (8, '2007-08-09', 'Eric', '303-488-1234', 'Eric', 'Vasquez', 'ev@usmc.mil', 'm', 'https://image.shutterstock.com/image-photo/friendship-communication-two-teenage-boys-450w-1110834848.jpg', 8, 2, 4);
INSERT INTO `user_detail` (`id`, `dob`, `nickname`, `phone_number`, `first_name`, `last_name`, `email`, `gender`, `user_image_url`, `user_id`, `location_id`, `address_id`) VALUES (9, '2003-11-28', 'Jesse', '720-902-1234', 'Jesse', 'Mena', 'jmena@yahoo.com', 'm', 'https://image.shutterstock.com/image-photo/teen-boy-glasses-shirt-photo-450w-1288357228.jpg', 9, 1, 4);
INSERT INTO `user_detail` (`id`, `dob`, `nickname`, `phone_number`, `first_name`, `last_name`, `email`, `gender`, `user_image_url`, `user_id`, `location_id`, `address_id`) VALUES (10, '1959-10-31', 'Greg', '303-555-1234', 'Greg', 'Kraehenbuehl', 'greg@google.com', 'm', 'https://image.shutterstock.com/image-photo/young-teenage-boy-isolated-on-450w-710074273.jpg', 10, 2, 5);

COMMIT;


-- -----------------------------------------------------
-- Data for table `achievement`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `achievement` (`id`, `name`, `description`, `image_url`) VALUES (1, 'White Belt ', 'Attend the Dojo at least 5 times, and complete the online CoderDojo Scratch Tutorials (Beginner, Intermediate, and Advanced levels) ', 'https://vignette.wikia.nocookie.net/clubpenguin/images/7/76/White_Ninja_Belt_icon.png/revision/latest/scale-to-width-down/170?cb=20151012013916');
INSERT INTO `achievement` (`id`, `name`, `description`, `image_url`) VALUES (2, 'Yellow Belt', 'Build at least three small projects on a Raspberry Pi or Arduino kit.', 'https://vignette.wikia.nocookie.net/clubpenguin/images/a/a4/Yellow_Ninja_Belt_icon.png/revision/latest/scale-to-width-down/170?cb=20151012013916');
INSERT INTO `achievement` (`id`, `name`, `description`, `image_url`) VALUES (3, 'Green Belt ', 'Come up with a new project of your own and build it!', 'https://vignette.wikia.nocookie.net/clubpenguin/images/1/10/Green_Ninja_Belt_icon.png/revision/latest/scale-to-width-down/170?cb=20150115223047');
INSERT INTO `achievement` (`id`, `name`, `description`, `image_url`) VALUES (4, 'Orange Belt', 'Build a new project with another student.', 'https://vignette.wikia.nocookie.net/clubpenguin/images/9/92/Orange_Ninja_Belt_icon.png/revision/latest/scale-to-width-down/170?cb=20151012013917');
INSERT INTO `achievement` (`id`, `name`, `description`, `image_url`) VALUES (5, 'Blue Belt', 'Work on a new project with several other students.', 'https://vignette.wikia.nocookie.net/clubpenguin/images/a/a3/Blue_Ninja_Belt_icon.png/revision/latest/scale-to-width-down/170?cb=20150115230243');
INSERT INTO `achievement` (`id`, `name`, `description`, `image_url`) VALUES (6, 'Red Belt', 'Attend the Dojo at least 5 times, and complete the online CoderDojo Scratch Tutorials at the Beginner, Intermediate, and Advanced levels\n', 'https://vignette.wikia.nocookie.net/clubpenguin/images/e/e3/Red_Ninja_Belt_icon.png/revision/latest/scale-to-width-down/170?cb=20151012013917');
INSERT INTO `achievement` (`id`, `name`, `description`, `image_url`) VALUES (7, 'Black Belt', 'Attend the Dojo at least 10 times. Build something new into the Advanced Scratch tutorial lesson.', 'https://vignette.wikia.nocookie.net/clubpenguin/images/b/b2/Black_Ninja_Belt_icon.png/revision/latest/scale-to-width-down/170?cb=20151012013918');

COMMIT;


-- -----------------------------------------------------
-- Data for table `user_achievement`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `user_achievement` (`id`, `achieved`, `achieved_date`, `user_detail_id`, `achievement_id`) VALUES (1, 1, '2019-04-7', 2, 1);
INSERT INTO `user_achievement` (`id`, `achieved`, `achieved_date`, `user_detail_id`, `achievement_id`) VALUES (2, 1, '2019-04-11', 2, 2);
INSERT INTO `user_achievement` (`id`, `achieved`, `achieved_date`, `user_detail_id`, `achievement_id`) VALUES (3, 0, NULL, 2, 3);
INSERT INTO `user_achievement` (`id`, `achieved`, `achieved_date`, `user_detail_id`, `achievement_id`) VALUES (4, 0, NULL, 2, 4);
INSERT INTO `user_achievement` (`id`, `achieved`, `achieved_date`, `user_detail_id`, `achievement_id`) VALUES (5, 1, '2019-03-28', 4, 1);
INSERT INTO `user_achievement` (`id`, `achieved`, `achieved_date`, `user_detail_id`, `achievement_id`) VALUES (6, 1, '2019-04-15', 4, 2);
INSERT INTO `user_achievement` (`id`, `achieved`, `achieved_date`, `user_detail_id`, `achievement_id`) VALUES (7, 0, NULL, 4, 3);
INSERT INTO `user_achievement` (`id`, `achieved`, `achieved_date`, `user_detail_id`, `achievement_id`) VALUES (8, 0, NULL, 4, 4);
INSERT INTO `user_achievement` (`id`, `achieved`, `achieved_date`, `user_detail_id`, `achievement_id`) VALUES (9, 0, NULL, 4, 5);

COMMIT;


-- -----------------------------------------------------
-- Data for table `goal`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (1, 'Attendance', 'Attend the Dojo at least 5 times.', 1);
INSERT INTO `goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (2, 'CoderDojo Scratch Tutorials', 'CoderDojo Scratch Tutorials at the Beginner, Intermediate, and Advanced levels', 1);
INSERT INTO `goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (3, 'Attendance', 'Attend the Dojo atleast 10 times.', 2);
INSERT INTO `goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (4, 'Builder', 'Build something new into the Advanced Scratch tutorial lesson.', 2);
INSERT INTO `goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (5, 'HTML Web Site book', 'Work through the HTML Web Site book.', 3);
INSERT INTO `goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (6, 'JavaScript Game Book', 'Work through the JavaScript game book.', 3);
INSERT INTO `goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (7, 'Build 1', 'Build a small project on a Raspberry Pi or Arduino kit.', 4);
INSERT INTO `goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (8, 'Build 2', 'Build a small project on a Raspberry Pi or Arduino kit.', 4);
INSERT INTO `goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (9, 'Build 3', 'Build a small project on a Raspberry Pi or Arduino kit. It must be something new you build into an existing lesson.', 4);
INSERT INTO `goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (10, 'Independent Build', 'Come up with a new project of your own and build it!', 5);
INSERT INTO `goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (11, 'Pair Build', 'Build a new project with another student.', 6);
INSERT INTO `goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (12, 'Group Build', 'Work on a new project with several other students.', 7);

COMMIT;


-- -----------------------------------------------------
-- Data for table `user_goal`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `user_goal` (`id`, `completed`, `completed_date`, `user_achievement_id`, `goal_id`) VALUES (1, 1, '2019-04-01', 1, 1);
INSERT INTO `user_goal` (`id`, `completed`, `completed_date`, `user_achievement_id`, `goal_id`) VALUES (2, 1, '2019-04-10', 2, 2);
INSERT INTO `user_goal` (`id`, `completed`, `completed_date`, `user_achievement_id`, `goal_id`) VALUES (3, 1, NULL, 3, 3);
INSERT INTO `user_goal` (`id`, `completed`, `completed_date`, `user_achievement_id`, `goal_id`) VALUES (4, 0, NULL, 4, 4);
INSERT INTO `user_goal` (`id`, `completed`, `completed_date`, `user_achievement_id`, `goal_id`) VALUES (5, 1, '2019-04-01', 1, 6);
INSERT INTO `user_goal` (`id`, `completed`, `completed_date`, `user_achievement_id`, `goal_id`) VALUES (6, 1, '2019-04-10', 2, 7);
INSERT INTO `user_goal` (`id`, `completed`, `completed_date`, `user_achievement_id`, `goal_id`) VALUES (7, 0, NULL, 3, 8);
INSERT INTO `user_goal` (`id`, `completed`, `completed_date`, `user_achievement_id`, `goal_id`) VALUES (8, 0, NULL, 4, 9);
INSERT INTO `user_goal` (`id`, `completed`, `completed_date`, `user_achievement_id`, `goal_id`) VALUES (9, 1, '2019-03-10', 5, 1);
INSERT INTO `user_goal` (`id`, `completed`, `completed_date`, `user_achievement_id`, `goal_id`) VALUES (10, 1, '2019-03-28', 5, 2);
INSERT INTO `user_goal` (`id`, `completed`, `completed_date`, `user_achievement_id`, `goal_id`) VALUES (11, 1, '2019-04-01', 6, 3);
INSERT INTO `user_goal` (`id`, `completed`, `completed_date`, `user_achievement_id`, `goal_id`) VALUES (12, 1, '2019-04-01', 6, 4);
INSERT INTO `user_goal` (`id`, `completed`, `completed_date`, `user_achievement_id`, `goal_id`) VALUES (13, 1, '2019-04-01', 7, 5);
INSERT INTO `user_goal` (`id`, `completed`, `completed_date`, `user_achievement_id`, `goal_id`) VALUES (14, 1, '2019-04-01', 7, 6);
INSERT INTO `user_goal` (`id`, `completed`, `completed_date`, `user_achievement_id`, `goal_id`) VALUES (15, 0, '2019-04-09', 8, 7);
INSERT INTO `user_goal` (`id`, `completed`, `completed_date`, `user_achievement_id`, `goal_id`) VALUES (17, 0, NULL, 8, 9);
INSERT INTO `user_goal` (`id`, `completed`, `completed_date`, `user_achievement_id`, `goal_id`) VALUES (18, 0, NULL, 9, 10);

COMMIT;


-- -----------------------------------------------------
-- Data for table `meeting`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (1, 'Weekly Meetup at Turing', 1, '2019-01-01 12:01');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (2, 'Weekly Meetup at Alternate Site', 2, '2019-01-03 14:00');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (3, 'Evening Meeting for beginners', 1, '2019-01-05 14:00');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (4, 'Special Tutoring for Advanced Topics', 2, '2019-01-07 14:00');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (5, 'Weekly Meetup at Turing', 1, '2019-01-08 12:01');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (6, 'Weekly Meetup at Alternate Site', 2, '2019-01-10 14:00');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (7, 'Evening Meeting for beginners', 1, '2019-01-12 14:00');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (8, 'Special Tutoring for Advanced Topics', 2, '2019-01-14 14:00');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (9, 'Weekly Meetup at Turing', 1, '2019-01-15 12:01');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (10, 'Weekly Meetup at Alternate Site', 2, '2019-01-17 14:00');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (11, 'Evening Meeting for beginners', 1, '2019-01-19 14:00');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (12, 'Special Tutoring for Advanced Topics', 2, '2019-01-21 14:00');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (13, 'Weekly Meetup at Turing', 1, '2019-01-22 12:01');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (14, 'Weekly Meetup at Alternate Site', 2, '2019-01-24 14:00');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (15, 'Evening Meeting for beginners', 1, '2019-01-26 14:00');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (16, 'Special Tutoring for Advanced Topics', 2, '2019-01-28 14:00');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (17, 'Weekly Meetup at Turing', 1, '2019-01-29 12:01');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (18, 'Weekly Meetup at Alternate Site', 2, '2019-01-31 14:00');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (19, 'Evening Meeting for beginners', 1, '2019-02-02 14:00');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (20, 'Special Tutoring for Advanced Topics', 2, '2019-02-04 14:00');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (21, 'Weekly Meetup at Turing', 1, '2019-02-05 12:01');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (22, 'Weekly Meetup at Alternate Site', 2, '2019-02-07 14:00');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (23, 'Evening Meeting for beginners', 1, '2019-02-09 14:00');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (24, 'Special Tutoring for Advanced Topics', 2, '2019-02-11 14:00');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (25, 'Weekly Meetup at Turing', 1, '2019-02-12 12:01');

COMMIT;


-- -----------------------------------------------------
-- Data for table `meeting_attendance`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (1, 1, 1, 2);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (2, 1, 1, 4);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (3, 0, 1, 5);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (4, 0, 2, 6);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (5, 1, 2, 7);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (6, 0, 2, 8);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (7, 1, 3, 9);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (8, 0, 3, 10);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (9, 1, 3, 2);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (10, 1, 4, 4);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (11, 0, 4, 5);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (12, 0, 4, 6);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (13, 1, 5, 7);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (14, 1, 5, 8);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (15, 1, 5, 9);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (16, 1, 6, 10);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (17, 1, 6, 2);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (18, 0, 6, 4);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (19, 1, 7, 5);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (20, 1, 7, 6);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (21, 0, 7, 7);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (22, 0, 8, 8);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (23, 0, 8, 9);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (24, 1, 8, 10);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (25, 1, 9, 2);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (26, 1, 9, 4);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (27, 1, 9, 5);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (28, 0, 10, 6);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (29, 0, 10, 7);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (30, 1, 10, 8);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (31, 1, 11, 9);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (32, 1, 11, 10);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (33, 1, 11, 2);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (34, 1, 12, 4);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (35, 0, 12, 5);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (36, 0, 12, 6);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (37, 0, 13, 7);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (38, 1, 13, 8);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (39, 1, 13, 9);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (40, 1, 14, 10);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (41, 1, 14, 2);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (42, 1, 14, 4);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (43, 0, 15, 5);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (44, 1, 15, 6);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (45, 1, 15, 7);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (46, 1, 16, 8);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (47, 1, 16, 9);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (48, 1, 16, 10);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (49, 1, 17, 2);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (50, 0, 17, 4);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (51, 0, 17, 5);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (52, 0, 18, 6);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (53, 1, 18, 7);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (54, 0, 18, 8);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (55, 1, 19, 9);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (56, 1, 19, 10);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (57, 1, 19, 2);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (58, 0, 20, 4);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (59, 0, 20, 5);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (60, 1, 20, 6);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (61, 1, 21, 7);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (62, 0, 21, 8);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (63, 0, 21, 9);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (64, 1, 22, 10);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (65, 1, 22, 2);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (66, 1, 22, 4);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (67, 1, 23, 5);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (68, 1, 23, 6);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (69, 1, 23, 7);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (70, 1, 24, 8);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (71, 1, 24, 9);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (72, 1, 24, 10);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (73, 1, 25, 2);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (74, 1, 25, 4);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (75, 1, 25, 5);

COMMIT;


-- -----------------------------------------------------
-- Data for table `parent_child_relationship`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `parent_child_relationship` (`id`, `parent_id`, `child_id`) VALUES (1, 3, 2);
INSERT INTO `parent_child_relationship` (`id`, `parent_id`, `child_id`) VALUES (2, 1, 2);
INSERT INTO `parent_child_relationship` (`id`, `parent_id`, `child_id`) VALUES (3, 3, 4);
INSERT INTO `parent_child_relationship` (`id`, `parent_id`, `child_id`) VALUES (4, 1, 4);
INSERT INTO `parent_child_relationship` (`id`, `parent_id`, `child_id`) VALUES (5, 3, 5);
INSERT INTO `parent_child_relationship` (`id`, `parent_id`, `child_id`) VALUES (6, 1, 5);
INSERT INTO `parent_child_relationship` (`id`, `parent_id`, `child_id`) VALUES (7, 3, 6);
INSERT INTO `parent_child_relationship` (`id`, `parent_id`, `child_id`) VALUES (8, 1, 6);

COMMIT;


-- -----------------------------------------------------
-- Data for table `role`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `role` (`id`, `name`) VALUES (1, 'ADMIN');
INSERT INTO `role` (`id`, `name`) VALUES (2, 'MENTOR');
INSERT INTO `role` (`id`, `name`) VALUES (3, 'PARENT');
INSERT INTO `role` (`id`, `name`) VALUES (4, 'STUDENT');

COMMIT;


-- -----------------------------------------------------
-- Data for table `user_role`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (2, 4);
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (3, 1);
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (4, 4);
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (5, 4);
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (6, 4);
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (7, 4);
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (8, 4);
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (9, 4);
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (10, 4);
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (1, 3);

COMMIT;


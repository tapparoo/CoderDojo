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
  `enabled` TINYINT NULL,
  `role_id` INT NULL DEFAULT 1,
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
  `achieved` TINYINT NULL,
  `student_id` INT NULL,
  `image_url` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  INDEX `achievement_is_owned_by_student_idx` (`student_id` ASC),
  CONSTRAINT `achievement_is_owned_by_student`
    FOREIGN KEY (`student_id`)
    REFERENCES `user_detail` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `goal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `goal` ;

CREATE TABLE IF NOT EXISTS `goal` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(500) NULL,
  `completed` TINYINT NULL,
  `achievement_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `goals_belong_to_a_belt_idx` (`achievement_id` ASC),
  CONSTRAINT `goals_belong_to_a_belt`
    FOREIGN KEY (`achievement_id`)
    REFERENCES `achievement` (`id`)
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
-- Table `standard_achievement`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `standard_achievement` ;

CREATE TABLE IF NOT EXISTS `standard_achievement` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(500) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `standard_goal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `standard_goal` ;

CREATE TABLE IF NOT EXISTS `standard_goal` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(500) NULL,
  `standard_achievement_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `goals_belong_to_achievement_idx` (`standard_achievement_id` ASC),
  CONSTRAINT `goals_belong_to_achievement`
    FOREIGN KEY (`standard_achievement_id`)
    REFERENCES `standard_achievement` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `authority`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `authority` ;

CREATE TABLE IF NOT EXISTS `authority` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_authority`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_authority` ;

CREATE TABLE IF NOT EXISTS `user_authority` (
  `authority_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  CONSTRAINT `authority_to_user_authority`
    FOREIGN KEY (`authority_id`)
    REFERENCES `authority` (`id`)
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
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL,
  `role_id` INT NULL,
  PRIMARY KEY (`id`),
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
INSERT INTO `location` (`id`, `name`, `details`, `address_id`) VALUES (1, 'Downtown Dojo at Turing', 'this is our main classroom in the area. ', 3);
INSERT INTO `location` (`id`, `name`, `details`, `address_id`) VALUES (2, 'Remote Classroom', 'SD is our backup room.', 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `user`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `role_id`) VALUES (1, 'admin', 'password', 1, NULL);
INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `role_id`) VALUES (2, 'student', 'password', 1, NULL);
INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `role_id`) VALUES (3, 'parent', 'password', 1, NULL);
INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `role_id`) VALUES (4, 'mentor', 'password', 1, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `user_detail`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `user_detail` (`id`, `dob`, `nickname`, `phone_number`, `user_id`, `location_id`, `address_id`) VALUES (1, '1988-03-28', 'marky-mark', '123-456-7890', 1, 1, 1);
INSERT INTO `user_detail` (`id`, `dob`, `nickname`, `phone_number`, `user_id`, `location_id`, `address_id`) VALUES (2, '1975-07-11', 'a-tappy', '098-765-4321', 2, 1, 2);
INSERT INTO `user_detail` (`id`, `dob`, `nickname`, `phone_number`, `user_id`, `location_id`, `address_id`) VALUES (3, '2009-01-01', 'A.J.', '567-567-5678', 3, 2, 3);
INSERT INTO `user_detail` (`id`, `dob`, `nickname`, `phone_number`, `user_id`, `location_id`, `address_id`) VALUES (4, '2000-12-31', 'SD Steve', '543-543-5432', 4, 2, 4);

COMMIT;


-- -----------------------------------------------------
-- Data for table `achievement`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `achievement` (`id`, `name`, `achieved`, `student_id`, `image_url`) VALUES (1, 'White Belt', 1, 2, 'https://i.imgur.com/JyUXQRv.jpg');
INSERT INTO `achievement` (`id`, `name`, `achieved`, `student_id`, `image_url`) VALUES (2, 'Yellow Belt', 1, 2, 'https://i.imgur.com/NyXNxGY.jpg');
INSERT INTO `achievement` (`id`, `name`, `achieved`, `student_id`, `image_url`) VALUES (3, 'Blue Belt', 0, 2, 'https://i.imgur.com/zQpEuuj.jpg');
INSERT INTO `achievement` (`id`, `name`, `achieved`, `student_id`, `image_url`) VALUES (4, 'Red Belt', 0, 2, 'https://i.imgur.com/9wvgRSm.jpg');
INSERT INTO `achievement` (`id`, `name`, `achieved`, `student_id`, `image_url`) VALUES (5, 'Black Belt', 0, 2, 'https://i.imgur.com/q1lSBge.jpg');

COMMIT;


-- -----------------------------------------------------
-- Data for table `goal`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `goal` (`id`, `name`, `description`, `completed`, `achievement_id`) VALUES (1, 'turn computer on', 'Turining the computer on is serious business', 1, 1);
INSERT INTO `goal` (`id`, `name`, `description`, `completed`, `achievement_id`) VALUES (2, 'install all dev tools', 'install eclipse, and MAMP', 1, 2);
INSERT INTO `goal` (`id`, `name`, `description`, `completed`, `achievement_id`) VALUES (3, 'build simple html website', 'build catlist.com, a list of cats', 0, 3);
INSERT INTO `goal` (`id`, `name`, `description`, `completed`, `achievement_id`) VALUES (4, 'get good at bootstra', 'bootstrap - the fastest way to make your website look good.', 0, 4);
INSERT INTO `goal` (`id`, `name`, `description`, `completed`, `achievement_id`) VALUES (5, '...profit?', 'get a job i guess', 0, 5);
INSERT INTO `goal` (`id`, `name`, `description`, `completed`, `achievement_id`) VALUES (6, 'login to code dojo', 'not sure how you are seeing this without logging on.', 1, 1);
INSERT INTO `goal` (`id`, `name`, `description`, `completed`, `achievement_id`) VALUES (7, 'discover stack overflow', 'all hail the supreme source of knowledge', 1, 2);
INSERT INTO `goal` (`id`, `name`, `description`, `completed`, `achievement_id`) VALUES (8, 'learn some css', 'css lets you make things pretty... or atleast less ugly.', 1, 3);
INSERT INTO `goal` (`id`, `name`, `description`, `completed`, `achievement_id`) VALUES (9, 'learn angular', 'you are a big kid now - you ar eready for the complicated stuff', 0, 4);
INSERT INTO `goal` (`id`, `name`, `description`, `completed`, `achievement_id`) VALUES (10, 'learn databases', 'the most rewarding job of all time', 0, 5);

COMMIT;


-- -----------------------------------------------------
-- Data for table `meeting`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (1, 'Meetup at Turing', 1, '2019-01-01 12:01');
INSERT INTO `meeting` (`id`, `name`, `location_id`, `date_time`) VALUES (2, 'Learn at Alternate Site', 2, '2019-02-05 14:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `meeting_attendance`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (1, 1, 1, 3);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (2, 1, 1, 2);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (3, 0, 2, 2);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (4, 0, 2, 1);
INSERT INTO `meeting_attendance` (`id`, `attended`, `meeting_id`, `user_id`) VALUES (5, 1, 1, 3);

COMMIT;


-- -----------------------------------------------------
-- Data for table `parent_child_relationship`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `parent_child_relationship` (`id`, `parent_id`, `child_id`) VALUES (1, 3, 2);
INSERT INTO `parent_child_relationship` (`id`, `parent_id`, `child_id`) VALUES (2, 1, 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `standard_achievement`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `standard_achievement` (`id`, `name`, `description`) VALUES (1, 'White Belt', 'You are just starting. this is exciting!');
INSERT INTO `standard_achievement` (`id`, `name`, `description`) VALUES (2, 'Yellow Belt', 'You have some computing basics under your belt');
INSERT INTO `standard_achievement` (`id`, `name`, `description`) VALUES (3, 'Blue Belt', 'You know enough to be your family\'s tech support!');
INSERT INTO `standard_achievement` (`id`, `name`, `description`) VALUES (4, 'Red Belt', 'You see design everywhere you go.');
INSERT INTO `standard_achievement` (`id`, `name`, `description`) VALUES (5, 'Black Belt', 'You are a master. We bow to your skills and dedication.');

COMMIT;


-- -----------------------------------------------------
-- Data for table `standard_goal`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `standard_goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (1, 'turn computer on....', 'Turning the computer on is serious bisiness', 1);
INSERT INTO `standard_goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (2, 'install all dev tools', 'install eclipse, and MAMP', 2);
INSERT INTO `standard_goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (3, 'build simple html website', 'build catlist.com, a list of cats', 3);
INSERT INTO `standard_goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (4, 'get good at bootstrap', 'bootsrap - the fastest way to make your website look good. ', 4);
INSERT INTO `standard_goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (5, '... profit?', 'get a job i guess', 5);
INSERT INTO `standard_goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (6, 'login to code dojo', 'not sure how you are seeing this without logging on.', 1);
INSERT INTO `standard_goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (7, 'discover stack overflow', 'all hail the supreme source of knowledge', 2);
INSERT INTO `standard_goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (8, 'learn some css', 'css lets you make things pretty... or atleast less ugly. ', 3);
INSERT INTO `standard_goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (9, 'learn angular', 'you are a big kid now - you are ready for the complicated stuff', 4);
INSERT INTO `standard_goal` (`id`, `name`, `description`, `standard_achievement_id`) VALUES (10, 'learn databases', 'the most rewarding job of all time. ', 5);

COMMIT;


-- -----------------------------------------------------
-- Data for table `authority`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `authority` (`id`, `name`) VALUES (1, 'ROLE_ADMIN');
INSERT INTO `authority` (`id`, `name`) VALUES (2, 'ROLE_STUDENT');
INSERT INTO `authority` (`id`, `name`) VALUES (3, 'ROLE_PARENT');
INSERT INTO `authority` (`id`, `name`) VALUES (4, 'ROLE_MENTOR');

COMMIT;


-- -----------------------------------------------------
-- Data for table `user_authority`
-- -----------------------------------------------------
START TRANSACTION;
USE `codedojodb`;
INSERT INTO `user_authority` (`authority_id`, `user_id`) VALUES (1, 1);
INSERT INTO `user_authority` (`authority_id`, `user_id`) VALUES (2, 2);
INSERT INTO `user_authority` (`authority_id`, `user_id`) VALUES (3, 3);
INSERT INTO `user_authority` (`authority_id`, `user_id`) VALUES (4, 4);

COMMIT;


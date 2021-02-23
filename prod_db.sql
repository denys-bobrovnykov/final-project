-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`hibernate_sequence`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`hibernate_sequence` ;

CREATE TABLE IF NOT EXISTS `mydb`.`hibernate_sequence` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`movie`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`movie` ;

CREATE TABLE IF NOT EXISTS `mydb`.`movie` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `poster` VARCHAR(255) NULL DEFAULT NULL,
  `release_year` INT NOT NULL,
  `title_en` VARCHAR(255) NOT NULL,
  `title_ua` VARCHAR(255) NOT NULL,
  `running_time` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `movie_title_en_uindex` (`title_en` ASC) VISIBLE,
  UNIQUE INDEX `movie_title_ua_uindex` (`title_ua` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 25
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`movie_session`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`movie_session` ;

CREATE TABLE IF NOT EXISTS `mydb`.`movie_session` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `day_of_session` DATE NOT NULL,
  `seats_available` INT NOT NULL DEFAULT '0',
  `time_start` TIME NOT NULL,
  `movie_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `movie_session_pk` (`day_of_session` ASC, `time_start` ASC) VISIBLE,
  INDEX `FKer8dsjajcvlud03f5165ajl39` (`movie_id` ASC) VISIBLE,
  CONSTRAINT `FKer8dsjajcvlud03f5165ajl39`
    FOREIGN KEY (`movie_id`)
    REFERENCES `mydb`.`movie` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 67
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`seat`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`seat` ;

CREATE TABLE IF NOT EXISTS `mydb`.`seat` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `seat_number` INT NOT NULL,
  `seat_row` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`seat_session`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`seat_session` ;

CREATE TABLE IF NOT EXISTS `mydb`.`seat_session` (
  `seat_id` INT NOT NULL,
  `session_id` INT NOT NULL,
  PRIMARY KEY (`seat_id`, `session_id`),
  INDEX `FK5labcfbwj9mmhhwt4h5mq4pur` (`session_id` ASC) VISIBLE,
  CONSTRAINT `FK5labcfbwj9mmhhwt4h5mq4pur`
    FOREIGN KEY (`session_id`)
    REFERENCES `mydb`.`movie_session` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `FK7jvumd3w5qkcwx87jtqk3ntu9`
    FOREIGN KEY (`seat_id`)
    REFERENCES `mydb`.`seat` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`user` ;

CREATE TABLE IF NOT EXISTS `mydb`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `enabled` BIT(1) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` ENUM('USER', 'ADMIN') NULL DEFAULT 'USER',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `user_email_uindex` (`email` ASC) VISIBLE,
  INDEX `user_user_role_id_fk` (`role` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 46
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`ticket`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`ticket` ;

CREATE TABLE IF NOT EXISTS `mydb`.`ticket` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `movie_session` INT NULL DEFAULT NULL,
  `seat` INT NULL DEFAULT NULL,
  `user_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKt0j59x70mggv9udy5hpup9lrm` (`seat` ASC) VISIBLE,
  INDEX `FKdvt57mcco3ogsosi97odw563o` (`user_id` ASC) VISIBLE,
  INDEX `FK2ddw7hd62eynh3qc3nbqk17e4` (`movie_session` ASC) VISIBLE,
  CONSTRAINT `FK2ddw7hd62eynh3qc3nbqk17e4`
    FOREIGN KEY (`movie_session`)
    REFERENCES `mydb`.`movie_session` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `FKdvt57mcco3ogsosi97odw563o`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`id`),
  CONSTRAINT `FKt0j59x70mggv9udy5hpup9lrm`
    FOREIGN KEY (`seat`)
    REFERENCES `mydb`.`seat` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 83
DEFAULT CHARACTER SET = utf8;

USE `mydb`;

DELIMITER $$

USE `mydb`$$
DROP TRIGGER IF EXISTS `mydb`.`set_seats_available` $$
USE `mydb`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `mydb`.`set_seats_available`
BEFORE INSERT ON `mydb`.`movie_session`
FOR EACH ROW
BEGIN
    SET NEW.seats_available = (SELECT COUNT(*) FROM seat);
END$$


USE `mydb`$$
DROP TRIGGER IF EXISTS `mydb`.`update_seats_available_for_session` $$
USE `mydb`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `mydb`.`update_seats_available_for_session`
AFTER INSERT ON `mydb`.`seat_session`
FOR EACH ROW
BEGIN
	SET SQL_SAFE_UPDATES=0;
	UPDATE movie_session  SET seats_available = (SELECT COUNT(*) FROM seat) - (SELECT COUNT(*) FROM seat_session WHERE seat_session.session_id = movie_session.id);
    SET SQL_SAFE_UPDATES=1;
END$$


DELIMITER ;

-- -----------------------------------------------------
-- Data for table `mydb`.`movie`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`movie` (`id`, `poster`, `release_year`, `title_en`, `title_ua`, `running_time`) VALUES (9, 'blithe.jpg', 2020, 'Blithe Spirit', 'Колишня з того світу', 95);
INSERT INTO `mydb`.`movie` (`id`, `poster`, `release_year`, `title_en`, `title_ua`, `running_time`) VALUES (10, 'control.jpg', 2018, 'Distorted', 'Контроль', 86);
INSERT INTO `mydb`.`movie` (`id`, `poster`, `release_year`, `title_en`, `title_ua`, `running_time`) VALUES (11, 'monster_hunter.jpg', 2021, 'Monster Hunter', 'Мисливець на монстрів', 103);
INSERT INTO `mydb`.`movie` (`id`, `poster`, `release_year`, `title_en`, `title_ua`, `running_time`) VALUES (12, 'soul.jpg', 2020, 'Soul', 'Душа', 101);
INSERT INTO `mydb`.`movie` (`id`, `poster`, `release_year`, `title_en`, `title_ua`, `running_time`) VALUES (13, 'wander_woman.jpg', 2020, 'Wonder Woman 1984', 'Диво-жінка 1984', 151);

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`movie_session`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (1, '2021-02-08', 16, '09:00:00', 9);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (2, '2021-02-08', 16, '11:00:00', 10);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (3, '2021-02-08', 16, '13:00:00', 11);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (4, '2021-02-08', 16, '16:00:00', 12);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (5, '2021-02-08', 16, '21:00:00', 13);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (6, '2021-02-09', 16, '09:00:00', 9);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (7, '2021-02-09', 16, '11:00:00', 10);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (8, '2021-02-09', 16, '13:00:00', 11);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (9, '2021-02-09', 16, '16:00:00', 12);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (10, '2021-02-09', 16, '21:00:00', 13);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (11, '2021-02-10', 16, '09:00:00', 9);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (12, '2021-02-10', 16, '11:00:00', 10);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (13, '2021-02-10', 16, '13:00:00', 11);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (14, '2021-02-10', 16, '16:00:00', 12);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (15, '2021-02-10', 16, '21:00:00', 13);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (16, '2021-02-11', 16, '09:00:00', 9);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (17, '2021-02-11', 16, '11:00:00', 10);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (18, '2021-02-11', 16, '13:00:00', 11);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (19, '2021-02-11', 16, '16:00:00', 12);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (20, '2021-02-11', 16, '21:00:00', 13);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (21, '2021-02-12', 16, '09:00:00', 9);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (22, '2021-02-12', 16, '11:00:00', 10);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (23, '2021-02-12', 16, '13:00:00', 11);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (24, '2021-02-12', 16, '16:00:00', 12);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (25, '2021-02-12', 16, '21:00:00', 13);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (26, '2021-02-13', 16, '09:00:00', 9);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (27, '2021-02-13', 16, '11:00:00', 10);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (28, '2021-02-13', 16, '13:00:00', 11);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (29, '2021-02-13', 16, '16:00:00', 12);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (30, '2021-02-13', 16, '21:00:00', 13);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (31, '2021-02-14', 16, '09:00:00', 9);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (32, '2021-02-14', 16, '11:00:00', 10);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (33, '2021-02-14', 16, '13:00:00', 11);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (34, '2021-02-14', 16, '16:00:00', 12);
INSERT INTO `mydb`.`movie_session` (`id`, `day_of_session`, `seats_available`, `time_start`, `movie_id`) VALUES (35, '2021-02-14', 16, '21:00:00', 13);

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`seat`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`seat` (`id`, `seat_number`, `seat_row`) VALUES (1, 1, 1);
INSERT INTO `mydb`.`seat` (`id`, `seat_number`, `seat_row`) VALUES (2, 2, 1);
INSERT INTO `mydb`.`seat` (`id`, `seat_number`, `seat_row`) VALUES (3, 3, 1);
INSERT INTO `mydb`.`seat` (`id`, `seat_number`, `seat_row`) VALUES (4, 1, 2);
INSERT INTO `mydb`.`seat` (`id`, `seat_number`, `seat_row`) VALUES (5, 2, 2);
INSERT INTO `mydb`.`seat` (`id`, `seat_number`, `seat_row`) VALUES (6, 3, 2);
INSERT INTO `mydb`.`seat` (`id`, `seat_number`, `seat_row`) VALUES (7, 4, 1);
INSERT INTO `mydb`.`seat` (`id`, `seat_number`, `seat_row`) VALUES (8, 5, 1);
INSERT INTO `mydb`.`seat` (`id`, `seat_number`, `seat_row`) VALUES (9, 6, 1);
INSERT INTO `mydb`.`seat` (`id`, `seat_number`, `seat_row`) VALUES (10, 7, 1);
INSERT INTO `mydb`.`seat` (`id`, `seat_number`, `seat_row`) VALUES (11, 8, 1);
INSERT INTO `mydb`.`seat` (`id`, `seat_number`, `seat_row`) VALUES (12, 4, 2);
INSERT INTO `mydb`.`seat` (`id`, `seat_number`, `seat_row`) VALUES (13, 5, 2);
INSERT INTO `mydb`.`seat` (`id`, `seat_number`, `seat_row`) VALUES (14, 6, 2);
INSERT INTO `mydb`.`seat` (`id`, `seat_number`, `seat_row`) VALUES (15, 7, 2);
INSERT INTO `mydb`.`seat` (`id`, `seat_number`, `seat_row`) VALUES (16, 8, 2);

COMMIT;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

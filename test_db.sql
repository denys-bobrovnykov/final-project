-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `test` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `test` DEFAULT CHARACTER SET utf8 ;
USE `test` ;

-- -----------------------------------------------------
-- Table `mydb`.`movie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`movie` (
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
AUTO_INCREMENT = 23
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`movie_session`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`movie_session` (
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
    REFERENCES `test`.`movie` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 66
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`seat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`seat` (
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
CREATE TABLE IF NOT EXISTS `test`.`seat_session` (
  `seat_id` INT NOT NULL,
  `session_id` INT NOT NULL,
  PRIMARY KEY (`seat_id`, `session_id`),
  INDEX `FK5labcfbwj9mmhhwt4h5mq4pur` (`session_id` ASC) VISIBLE,
  CONSTRAINT `FK5labcfbwj9mmhhwt4h5mq4pur`
    FOREIGN KEY (`session_id`)
    REFERENCES `test`.`movie_session` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `FK7jvumd3w5qkcwx87jtqk3ntu9`
    FOREIGN KEY (`seat_id`)
    REFERENCES `test`.`seat` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `enabled` BIT(1) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` ENUM('USER', 'ADMIN') NULL DEFAULT 'USER',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `user_email_uindex` (`email` ASC) VISIBLE,
  INDEX `user_user_role_id_fk` (`role` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 45
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`ticket` (
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
    REFERENCES `test`.`movie_session` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `FKdvt57mcco3ogsosi97odw563o`
    FOREIGN KEY (`user_id`)
    REFERENCES `test`.`user` (`id`),
  CONSTRAINT `FKt0j59x70mggv9udy5hpup9lrm`
    FOREIGN KEY (`seat`)
    REFERENCES `test`.`seat` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 77
DEFAULT CHARACTER SET = utf8;

USE `mydb`;

DELIMITER $$
USE `test`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `test`.`set_seats_available`
BEFORE INSERT ON `test`.`movie_session`
FOR EACH ROW
BEGIN
    SET NEW.seats_available = (SELECT COUNT(*) FROM seat);
END$$

USE `test`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `test`.`update_seats_available_for_session`
AFTER INSERT ON `test`.`seat_session`
FOR EACH ROW
BEGIN
	SET SQL_SAFE_UPDATES=0;
	UPDATE movie_session  SET seats_available = (SELECT COUNT(*) FROM seat) - (SELECT COUNT(*) FROM seat_session WHERE seat_session.session_id = movie_session.id);
    SET SQL_SAFE_UPDATES=1;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

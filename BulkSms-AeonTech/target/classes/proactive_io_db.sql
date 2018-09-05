-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mmcs_proactive_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mmcs_proactive_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mmcs_proactive_db` ;
USE `mmcs_proactive_db` ;

-- -----------------------------------------------------
-- Table `mmcs_proactive_db`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mmcs_proactive_db`.`client` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `country` TINYINT NULL,
  `name` VARCHAR(45) NOT NULL,
  `is_activated` TINYINT NULL,
  `created_on` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proactive_db`.`client_admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mmcs_proactive_db`.`client_admin` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `phone_no` VARCHAR(13) NULL,
  `client_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_client_admin_client_idx` (`client_id` ASC) VISIBLE,
  CONSTRAINT `fk_client_admin_client`
    FOREIGN KEY (`client_id`)
    REFERENCES `mmcs_proactive_db`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proactive_db`.`payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mmcs_proactive_db`.`payment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(45) NOT NULL,
  `type` TINYINT NULL,
  `account` VARCHAR(45) NULL,
  `amount` DOUBLE NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proactive_db`.`sale`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mmcs_proactive_db`.`sale` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `quantity` DOUBLE NOT NULL,
  `cost` DOUBLE NULL,
  `additional_cost` DOUBLE NULL,
  `date` DATETIME NULL,
  `status` TINYINT NULL,
  `client_id` INT NOT NULL,
  `payment_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sale_client1_idx` (`client_id` ASC) VISIBLE,
  INDEX `fk_sale_payment1_idx` (`payment_id` ASC) VISIBLE,
  CONSTRAINT `fk_sale_client1`
    FOREIGN KEY (`client_id`)
    REFERENCES `mmcs_proactive_db`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sale_payment1`
    FOREIGN KEY (`payment_id`)
    REFERENCES `mmcs_proactive_db`.`payment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proactive_db`.`confirmation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mmcs_proactive_db`.`confirmation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `amount` DOUBLE NOT NULL,
  `mpesa_no` VARCHAR(45) NOT NULL,
  `state` TINYINT NOT NULL,
  `date` DATETIME NULL,
  `client_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `mpesa_no_UNIQUE` (`mpesa_no` ASC) VISIBLE,
  INDEX `fk_confirmation_client1_idx` (`client_id` ASC) VISIBLE,
  CONSTRAINT `fk_confirmation_client1`
    FOREIGN KEY (`client_id`)
    REFERENCES `mmcs_proactive_db`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proactive_db`.`sale_cost`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mmcs_proactive_db`.`sale_cost` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `amount` DOUBLE NOT NULL,
  `message_id` VARCHAR(45) NOT NULL,
  `incurred_on` DATETIME NOT NULL,
  `client_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sale_cost_client1_idx` (`client_id` ASC) VISIBLE,
  CONSTRAINT `fk_sale_cost_client1`
    FOREIGN KEY (`client_id`)
    REFERENCES `mmcs_proactive_db`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proactive_db`.`token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mmcs_proactive_db`.`token` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(255) NOT NULL,
  `expiry_date` DATETIME NOT NULL,
  `client_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_token_client1_idx` (`client_id` ASC) VISIBLE,
  CONSTRAINT `fk_token_client1`
    FOREIGN KEY (`client_id`)
    REFERENCES `mmcs_proactive_db`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proactive_db`.`short_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mmcs_proactive_db`.`short_code` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(11) NOT NULL,
  `client_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `value_UNIQUE` (`value` ASC) VISIBLE,
  INDEX `fk_short_code_client1_idx` (`client_id` ASC) VISIBLE,
  CONSTRAINT `fk_short_code_client1`
    FOREIGN KEY (`client_id`)
    REFERENCES `mmcs_proactive_db`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proactive_db`.`sale_short_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mmcs_proactive_db`.`sale_short_code` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `amount` DOUBLE NOT NULL,
  `cost` DOUBLE NOT NULL,
  `date` DATETIME NOT NULL,
  `short_code_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sale_short_code_short_code1_idx` (`short_code_id` ASC) VISIBLE,
  CONSTRAINT `fk_sale_short_code_short_code1`
    FOREIGN KEY (`short_code_id`)
    REFERENCES `mmcs_proactive_db`.`short_code` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proactive_db`.`inventory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mmcs_proactive_db`.`inventory` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `amount` DOUBLE NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proactive_db`.`exchange_rates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mmcs_proactive_db`.`exchange_rates` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `country` TINYINT NOT NULL,
  `rwf` DOUBLE NOT NULL,
  `kes` DOUBLE NOT NULL,
  `tzs` DOUBLE NOT NULL,
  `ugx` DOUBLE NOT NULL,
  `dollar` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `country_UNIQUE` (`country` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proactive_db`.`credit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mmcs_proactive_db`.`credit` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `amount` DOUBLE NOT NULL,
  `client_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_credit_client1_idx` (`client_id` ASC) VISIBLE,
  CONSTRAINT `fk_credit_client1`
    FOREIGN KEY (`client_id`)
    REFERENCES `mmcs_proactive_db`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proactive_db`.`charges`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mmcs_proactive_db`.`charges` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `country` TINYINT NOT NULL,
  `rwf` DOUBLE NOT NULL,
  `rwf_airtel` DOUBLE NOT NULL,
  `kes` DOUBLE NOT NULL,
  `kes_airtel` DOUBLE NOT NULL,
  `tzs` DOUBLE NOT NULL,
  `tzs_airtel` DOUBLE NOT NULL,
  `ugx` DOUBLE NOT NULL,
  `ugx_airtel` DOUBLE NOT NULL,
  `other` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `country_UNIQUE` (`country` ASC) VISIBLE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

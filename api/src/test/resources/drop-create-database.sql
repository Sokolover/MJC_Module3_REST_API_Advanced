-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

DROP TABLE IF EXISTS gift_certificate,
    tag,
    tag_has_gift_certificate,
    user_account,
    user_account_has_user_role,
    user_order,
    user_order_has_gift_certificate,
    user_role CASCADE;

-- -----------------------------------------------------
-- Schema mjc
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mjc` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `mjc`;

-- -----------------------------------------------------
-- Table `mjc`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mjc`.`tag`
(
    `id`   BIGINT(100)  NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(200) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mjc`.`gift_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mjc`.`gift_certificate`
(
    `id`                         BIGINT(100)   NOT NULL AUTO_INCREMENT,
    `name`                       VARCHAR(200)  NOT NULL,
    `description`                VARCHAR(1000) NOT NULL,
    `price`                      DECIMAL(7, 2) NOT NULL,
    `create_date`                DATETIME      NOT NULL,
    `create_date_time_zone`      VARCHAR(50)   NOT NULL,
    `last_update_date`           DATETIME      NOT NULL,
    `last_update_date_time_zone` VARCHAR(50)   NOT NULL,
    `duration_in_days`           INT           NOT NULL,
    `operation`                  VARCHAR(100)  NULL,
    `updated_at_time`            DATETIME      NULL,
    `updated_at_time_zone`       VARCHAR(50)   NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mjc`.`tag_has_gift_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mjc`.`tag_has_gift_certificate`
(
    `tag_id`              BIGINT(100) NOT NULL,
    `gift_certificate_id` BIGINT(100) NOT NULL,
    PRIMARY KEY (`tag_id`, `gift_certificate_id`),
    INDEX `fk_tag_has_gift_certificate_gift_certificate1_idx` (`gift_certificate_id` ASC) VISIBLE,
    INDEX `fk_tag_has_gift_certificate_tag_idx` (`tag_id` ASC) VISIBLE,
    CONSTRAINT `fk_tag_has_gift_certificate_tag`
        FOREIGN KEY (`tag_id`)
            REFERENCES `mjc`.`tag` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_tag_has_gift_certificate_gift_certificate1`
        FOREIGN KEY (`gift_certificate_id`)
            REFERENCES `mjc`.`gift_certificate` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mjc`.`user_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mjc`.`user_account`
(
    `id`       BIGINT(100)  NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(200) NOT NULL,
    `email`    VARCHAR(200) NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
    UNIQUE INDEX `user_email_UNIQUE` (`email` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mjc`.`user_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mjc`.`user_order`
(
    `id`                         BIGINT(100)   NOT NULL AUTO_INCREMENT,
    `cost`                       DECIMAL(9, 2) NOT NULL,
    `create_date`                DATETIME      NOT NULL,
    `create_date_time_zone`      VARCHAR(50)   NOT NULL,
    `last_update_date`           DATETIME      NOT NULL,
    `last_update_date_time_zone` VARCHAR(50)   NOT NULL,
    `operation`                  VARCHAR(100)  NULL,
    `created_at_time`            DATETIME      NULL,
    `created_at_time_zone`       VARCHAR(50)   NULL,
    `user_account_id`            BIGINT(100)   NOT NULL,
    PRIMARY KEY (`id`, `user_account_id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    INDEX `fk_user_order_user_account1_idx` (`user_account_id` ASC) VISIBLE,
    CONSTRAINT `fk_user_order_user_account1`
        FOREIGN KEY (`user_account_id`)
            REFERENCES `mjc`.`user_account` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mjc`.`user_order_has_gift_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mjc`.`user_order_has_gift_certificate`
(
    `user_order_id`       BIGINT(100) NOT NULL,
    `gift_certificate_id` BIGINT(100) NOT NULL,
    PRIMARY KEY (`user_order_id`, `gift_certificate_id`),
    INDEX `fk_user_order_has_gift_certificate_gift_certificate1_idx` (`gift_certificate_id` ASC) VISIBLE,
    INDEX `fk_user_order_has_gift_certificate_user_order1_idx` (`user_order_id` ASC) VISIBLE,
    CONSTRAINT `fk_user_order_has_gift_certificate_user_order1`
        FOREIGN KEY (`user_order_id`)
            REFERENCES `mjc`.`user_order` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_user_order_has_gift_certificate_gift_certificate1`
        FOREIGN KEY (`gift_certificate_id`)
            REFERENCES `mjc`.`gift_certificate` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mjc`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mjc`.`user_role`
(
    `id`   BIGINT(100)  NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mjc`.`user_account_has_user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mjc`.`user_account_has_user_role`
(
    `user_account_id` BIGINT(100) NOT NULL,
    `user_role_id`    BIGINT(100) NOT NULL,
    PRIMARY KEY (`user_account_id`, `user_role_id`),
    INDEX `fk_user_account_has_user_role_user_role1_idx` (`user_role_id` ASC) VISIBLE,
    INDEX `fk_user_account_has_user_role_user_account1_idx` (`user_account_id` ASC) VISIBLE,
    CONSTRAINT `fk_user_account_has_user_role_user_account1`
        FOREIGN KEY (`user_account_id`)
            REFERENCES `mjc`.`user_account` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_user_account_has_user_role_user_role1`
        FOREIGN KEY (`user_role_id`)
            REFERENCES `mjc`.`user_role` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;

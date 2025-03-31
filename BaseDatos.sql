-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema bcopichincha
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bcopichincha
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bcopichincha` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `bcopichincha` ;

-- -----------------------------------------------------
-- Table `bcopichincha`.`personas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bcopichincha`.`personas` (
  `edad` INT NOT NULL,
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `identificacion` VARCHAR(10) NOT NULL,
  `direccion` VARCHAR(255) NOT NULL,
  `genero` VARCHAR(255) NOT NULL,
  `nombre` VARCHAR(255) NOT NULL,
  `telefono` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UKdpxdn543sbyt8xkvsqha0l1li` (`identificacion` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bcopichincha`.`clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bcopichincha`.`clientes` (
  `estado` BIT(1) NOT NULL,
  `id` BIGINT NOT NULL,
  `contrasena` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKtk4yna9cqo54xshjc9dpgbmc8`
    FOREIGN KEY (`id`)
    REFERENCES `bcopichincha`.`personas` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bcopichincha`.`cuentas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bcopichincha`.`cuentas` (
  `estado` BIT(1) NOT NULL,
  `saldo_inicial` DOUBLE NOT NULL,
  `cliente_id` BIGINT NOT NULL,
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `numero_cuenta` VARCHAR(255) NOT NULL,
  `tipo_cuenta` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK7h7mqvcau3mcl0mbrkdrt7fnh` (`numero_cuenta` ASC) VISIBLE,
  INDEX `FK65yk2321jpusl3fk96lqehrli` (`cliente_id` ASC) VISIBLE,
  CONSTRAINT `FK65yk2321jpusl3fk96lqehrli`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `bcopichincha`.`clientes` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bcopichincha`.`movimientos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bcopichincha`.`movimientos` (
  `saldo` DOUBLE NOT NULL,
  `valor` DOUBLE NOT NULL,
  `cuenta_id` BIGINT NOT NULL,
  `fecha` DATETIME(6) NOT NULL,
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `tipo_movimiento` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK4moe88hxuohcysas5h70mdc09` (`cuenta_id` ASC) VISIBLE,
  CONSTRAINT `FK4moe88hxuohcysas5h70mdc09`
    FOREIGN KEY (`cuenta_id`)
    REFERENCES `bcopichincha`.`cuentas` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

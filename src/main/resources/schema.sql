-- -----------------------------------------------------
-- Schema cryptus
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cryptus` DEFAULT CHARACTER SET utf8;
USE `cryptus`;

-- -----------------------------------------------------
-- Table `cryptus`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`user`
(
    `userId`         INT         NOT NULL AUTO_INCREMENT,
    `voornaam`       VARCHAR(45) NOT NULL,
    `tussenvoegsel`  VARCHAR(10) NULL,
    `achternaam`     VARCHAR(45) NOT NULL,
    `gebruikersnaam` VARCHAR(45) NOT NULL,
    `wachtwoord`     VARCHAR(45) NOT NULL,
    `salt`           VARCHAR(45) NOT NULL,
    PRIMARY KEY (`userId`)
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cryptus`.`klant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`klant`
(
    `userId`        INT         NOT NULL,
    `geboortedatum` DATE        NOT NULL,
    `straat`        VARCHAR(45) NOT NULL,
    `huisnummer`    INT         NOT NULL,
    `postcode`      VARCHAR(10) NOT NULL,
    `woonplaats`    VARCHAR(45) NOT NULL,
    `bsn`           VARCHAR(45) NOT NULL,
    `emailadres`    VARCHAR(45) NOT NULL,
    `telefoon`      VARCHAR(10) NOT NULL,
    PRIMARY KEY (`userId`),
    UNIQUE INDEX `userId_UNIQUE` (`userId` ASC) VISIBLE,
    CONSTRAINT `verzinzelf`
        FOREIGN KEY (`userId`)
            REFERENCES `cryptus`.`user` (`userId`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cryptus`.`beheerder`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`beheerder`
(
    `userId`           INT NOT NULL,
    `personeelsnummer` INT NOT NULL,
    PRIMARY KEY (`userId`),
    UNIQUE INDEX `userId_UNIQUE` (`userId` ASC) VISIBLE,
    UNIQUE INDEX `personeelsnummer_UNIQUE` (`personeelsnummer` ASC) VISIBLE,
    CONSTRAINT `verzinzelf1`
        FOREIGN KEY (`userId`)
            REFERENCES `cryptus`.`user` (`userId`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cryptus`.`bankrekening`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`bankrekening`
(
    `iban`   VARCHAR(45) NOT NULL,
    `saldo`  DECIMAL(2)  NOT NULL,
    `userId` INT         NOT NULL,
    INDEX `verzinzelf2_idx` (`userId` ASC) VISIBLE,
    PRIMARY KEY (`iban`),
    CONSTRAINT `verzinzelf2`
        FOREIGN KEY (`userId`)
            REFERENCES `cryptus`.`user` (`userId`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cryptus`.`Asset`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`Asset`
(
    `assetId`   INT         NOT NULL AUTO_INCREMENT,
    `naam`      VARCHAR(45) NOT NULL,
    `afkorting` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`assetId`)
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cryptus`.`portefeuille`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`portefeuille`
(
    `portefeuilleID` INT NOT NULL AUTO_INCREMENT,
    `userId`         INT NOT NULL,
    PRIMARY KEY (`portefeuilleID`),
    INDEX `user_portefeuille_idx` (`userId` ASC) VISIBLE,
    UNIQUE INDEX `userId_UNIQUE` (`userId` ASC) VISIBLE,
    CONSTRAINT `user_portefeuille`
        FOREIGN KEY (`userId`)
            REFERENCES `cryptus`.`user` (`userId`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cryptus`.`portefeuille_regel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`portefeuille_regel`
(
    `portefeuilleID` INT        NOT NULL,
    `assetId`        INT        NOT NULL,
    `saldo`          DECIMAL(6) NOT NULL,
    PRIMARY KEY (`portefeuilleID`, `assetId`),
    INDEX `verzinzelf5_idx` (`assetId` ASC) VISIBLE,
    CONSTRAINT `verzinzelf4`
        FOREIGN KEY (`portefeuilleID`)
            REFERENCES `cryptus`.`portefeuille` (`portefeuilleID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `verzinzelf5`
        FOREIGN KEY (`assetId`)
            REFERENCES `cryptus`.`Asset` (`assetId`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cryptus`.`transactie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`transactie`
(
    `transactieId`          INT          NOT NULL AUTO_INCREMENT,
    `datumtijd`             TIMESTAMP(2) NOT NULL,
    `kosten`                DECIMAL(2)   NOT NULL,
    `percentage`            INT          NOT NULL,
    `creditiban`            VARCHAR(45)  NOT NULL,
    `debitiban`             VARCHAR(45)  NOT NULL,
    `euroammount`           DECIMAL(2)   NOT NULL,
    `debitportefeuilleID`   INT          NOT NULL,
    `creditportefeuilleID1` INT          NOT NULL,
    `AssetId`               INT          NOT NULL,
    `assetammount`          DECIMAL(6)   NOT NULL,
    PRIMARY KEY (`transactieId`),
    INDEX `verzinzelf3_idx` (`debitportefeuilleID` ASC, `AssetId` ASC) VISIBLE,
    INDEX `verzinzelf7_idx` (`creditportefeuilleID1` ASC) VISIBLE,
    INDEX `verzinzelf9_idx` (`creditiban` ASC) VISIBLE,
    INDEX `verzinzelf10_idx` (`debitiban` ASC) VISIBLE,
    CONSTRAINT `verzinzelf3`
        FOREIGN KEY (`debitportefeuilleID`, `AssetId`)
            REFERENCES `cryptus`.`portefeuille_regel` (`portefeuilleID`, `assetId`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `verzinzelf7`
        FOREIGN KEY (`creditportefeuilleID1`)
            REFERENCES `cryptus`.`portefeuille_regel` (`portefeuilleID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `verzinzelf9`
        FOREIGN KEY (`creditiban`)
            REFERENCES `cryptus`.`bankrekening` (`iban`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `verzinzelf10`
        FOREIGN KEY (`debitiban`)
            REFERENCES `cryptus`.`bankrekening` (`iban`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cryptus`.`koers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`koers`
(
    `asseta`      INT          NOT NULL,
    `assetb`      INT          NOT NULL,
    `wisselkoers` DECIMAL(6)   NOT NULL,
    `timestamp`   TIMESTAMP(2) NOT NULL,
    PRIMARY KEY (`asseta`, `assetb`, `timestamp`),
    INDEX `verzinzelf6_idx` (`asseta` ASC) VISIBLE,
    INDEX `verzinzelf8_idx` (`assetb` ASC) VISIBLE,
    CONSTRAINT `verzinzelf6`
        FOREIGN KEY (`asseta`)
            REFERENCES `cryptus`.`Asset` (`assetId`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `verzinzelf8`
        FOREIGN KEY (`assetb`)
            REFERENCES `cryptus`.`Asset` (`assetId`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;

-- Gebruiker definiëren en toegang verlenen
# CREATE USER 'userCryptus'@'localhost' IDENTIFIED BY '12345';
# GRANT ALL PRIVILEGES ON cryptus.* TO 'userCryptus'@'localhost';

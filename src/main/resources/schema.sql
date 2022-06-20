-- -----------------------------------------------------
-- Schema cryptus
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cryptus`  ;
USE `cryptus` ;
-- -----------------------------------------------------
-- Table `cryptus`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`user`
(
    `userId`         INT          NOT NULL AUTO_INCREMENT,
    `voornaam`       VARCHAR(45)  NOT NULL,
    `tussenvoegsel`  VARCHAR(10)  NULL,
    `achternaam`     VARCHAR(45)  NOT NULL,
    `gebruikersnaam` VARCHAR(45)  NOT NULL,
    `wachtwoord`     VARCHAR(100) NOT NULL,
    PRIMARY KEY (`userId`)
);
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
    UNIQUE INDEX `userId_UNIQUE` (`userId` ),
    CONSTRAINT `klant_user`
        FOREIGN KEY (`userId`)
            REFERENCES `cryptus`.`user` (`userId`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
);
-- -----------------------------------------------------
-- Table `cryptus`.`beheerder`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`beheerder`
(
    `userId`           INT NOT NULL,
    `personeelsnummer` INT NOT NULL,
    PRIMARY KEY (`userId`),
    UNIQUE INDEX `userId_UNIQUE1` (`userId` ) ,
    UNIQUE INDEX `personeelsnummer_UNIQUE` (`personeelsnummer` ) ,
    CONSTRAINT `beheerder_user`
        FOREIGN KEY (`userId`)
            REFERENCES `cryptus`.`user` (`userId`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
);
-- -----------------------------------------------------
-- Table `cryptus`.`bankrekening`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`bankrekening`
(
    `iban`   VARCHAR(45)    NOT NULL,
    `saldo`  DECIMAL(16, 2) NOT NULL,
    `userId` INT            NOT NULL,
    INDEX `verzinzelf2_idx` (`userId` ),
    PRIMARY KEY (`iban`),
    UNIQUE INDEX `userId_UNIQUE2` (`userId` ) ,
    CONSTRAINT `bankrekening_user`
        FOREIGN KEY (`userId`)
            REFERENCES `cryptus`.`user` (`userId`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
-- -----------------------------------------------------
-- Table `cryptus`.`asset`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`asset`
(
    `assetId`   INT         NOT NULL AUTO_INCREMENT,
    `naam`      VARCHAR(45) NOT NULL,
    `afkorting` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`assetId`)
);
-- -----------------------------------------------------
-- Table `cryptus`.`portefeuille`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`portefeuille`
(
    `portefeuilleID` INT NOT NULL AUTO_INCREMENT,
    `userId`         INT NOT NULL,
    PRIMARY KEY (`portefeuilleID`),
    INDEX `user_portefeuille_idx` (`userId` ) ,
    UNIQUE INDEX `userId_UNIQUE3` (`userId` ) ,
    CONSTRAINT `portefeuille_user`
        FOREIGN KEY (`userId`)
            REFERENCES `cryptus`.`user` (`userId`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);
-- -----------------------------------------------------
-- Table `cryptus`.`portefeuille_regel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`portefeuille_regel`
(
    `portefeuille_regel_Id` INT NOT NULL AUTO_INCREMENT,
    `portefeuilleID` INT            NOT NULL,
    `assetId`        INT            NOT NULL,
    `saldo`          DECIMAL(16, 6) NOT NULL,
    PRIMARY KEY (`portefeuille_regel_Id`),
    INDEX `verzinzelf5_idx` (`assetId`) ,
    index `kutIndex` (`portefeuilleID`, `assetId`),
    CONSTRAINT `portefeuilleregel_portefeuille`
        FOREIGN KEY (`portefeuilleID`)
            REFERENCES `cryptus`.`portefeuille` (`portefeuilleID`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `portefeuilleregel_asset`
        FOREIGN KEY (`assetId`)
            REFERENCES `cryptus`.`asset` (`assetId`)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT
);
-- -----------------------------------------------------
-- Table `cryptus`.`transactie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`transactie`
(
    `transactieId`         INT            NOT NULL UNIQUE AUTO_INCREMENT,
    `datumtijd`            TIMESTAMP(2)   NOT NULL,
    `kosten`               DECIMAL(16, 2) NOT NULL,
    `creditiban`           VARCHAR(45),
    `debitiban`            VARCHAR(45),
    `euroammount`          DECIMAL(16, 2) NOT NULL,
    `debitportefeuilleID`  INT,
    `debitassetId`         INT,
    `assetammount`         DECIMAL(16, 6) NOT NULL,
    `creditportefeuilleID` INT,
    `creditassetId`        INT,
    PRIMARY KEY (`transactieId`),
    INDEX `verzinzelf3_idx` (`debitportefeuilleID` , `debitassetId` ) ,
    INDEX `verzinzelf9_idx` (`creditiban` ),
    INDEX `verzinzelf10_idx` (`debitiban` ),
    INDEX `verzinzelf7_idx` (`creditportefeuilleID`, `creditassetId`
        ),
    CONSTRAINT `transactie_debit_portefeuilleregel`
        FOREIGN KEY (`debitportefeuilleID`, `debitassetId`)
            REFERENCES `cryptus`.`portefeuille_regel` (`portefeuilleID`, `assetId`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT `transactie_credtiban`
        FOREIGN KEY (`creditiban`)
            REFERENCES `cryptus`.`bankrekening` (`iban`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT `transactie_debitiban`
        FOREIGN KEY (`debitiban`)
            REFERENCES `cryptus`.`bankrekening` (`iban`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT `transactie_credit_portefeuilleregel`
        FOREIGN KEY (`creditportefeuilleID`, `creditassetId`)
            REFERENCES `cryptus`.`portefeuille_regel` (`portefeuilleID`, `assetId`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
);
-- -----------------------------------------------------
-- Table `cryptus`.`koers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`koers`
(
    `assetId`           INT            NOT NULL,
    `datumKoers`        DATETIME       NOT NULL,
    `assetnaam`         VARCHAR(45)    NOT NULL,
    `wisselkoersEuro`   DECIMAL(16, 6) NOT NULL,
    `wisselkoersDollar` DECIMAL(16, 6) NOT NULL,
    PRIMARY KEY (`assetId`, `datumKoers`),
    INDEX `verzinzelf6_idx` (`assetId`),
    CONSTRAINT `koers_asset`
        FOREIGN KEY (`assetId`)
            REFERENCES `cryptus`.`asset` (`assetId`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);
-- -----------------------------------------------------
-- Table `cryptus`.`bankinstellingen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`bankinstelling`
(
    `adminAttribute` VARCHAR(45) NOT NULL,
    `value`          VARCHAR(45) NOT NULL,

    PRIMARY KEY (`adminAttribute`)
);
-- -----------------------------------------------------
-- Table `cryptus`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`account`
(
    `rol`    VARCHAR(45) NOT NULL,
    `status` TINYINT     NOT NULL,
    `userId` INT         NOT NULL,
    PRIMARY KEY (`userId`),
    CONSTRAINT `account_user`
        FOREIGN KEY (`userId`)
            REFERENCES `cryptus`.`user` (`userId`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);
-- CREATE USER 'userCryptus'@'localhost' IDENTIFIED BY '12345';
-- GRANT ALL PRIVILEGES ON cryptus.* TO 'userCryptus'@'localhost';

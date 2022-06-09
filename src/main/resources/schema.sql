-- -----------------------------------------------------
-- Schema cryptus
-- -----------------------------------------------------
-- CREATE SCHEMA IF NOT EXISTS `cryptus` DEFAULT CHARACTER SET utf8 ;
-- USE `cryptus` ;
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
    UNIQUE INDEX `userId_UNIQUE` (`userId` ASC) VISIBLE,
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
    UNIQUE INDEX `userId_UNIQUE` (`userId` ASC) VISIBLE,
    UNIQUE INDEX `personeelsnummer_UNIQUE` (`personeelsnummer` ASC) VISIBLE,
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
    INDEX `verzinzelf2_idx` (`userId` ASC) VISIBLE,
    PRIMARY KEY (`iban`),
    UNIQUE INDEX `userId_UNIQUE` (`userId` ASC) VISIBLE,
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
    INDEX `user_portefeuille_idx` (`userId` ASC) VISIBLE,
    UNIQUE INDEX `userId_UNIQUE` (`userId` ASC) VISIBLE,
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
    `portefeuilleID` INT            NOT NULL,
    `assetId`        INT            NOT NULL,
    `saldo`          DECIMAL(16, 6) NOT NULL,
    PRIMARY KEY (`portefeuilleID`, `assetId`),
    INDEX `verzinzelf5_idx` (`assetId` ASC) VISIBLE,
    CONSTRAINT `portefeuilleregel_portefeuille`
        FOREIGN KEY (`portefeuilleID`)
            REFERENCES `cryptus`.`portefeuille` (`portefeuilleID`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `portefeuilleregel_asset`
        FOREIGN KEY (`assetId`)
            REFERENCES `cryptus`.`asset` (`assetId`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
-- -----------------------------------------------------
-- Table `cryptus`.`transactie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cryptus`.`transactie`
(
    `transactieId`         INT            NOT NULL AUTO_INCREMENT,
    `datumtijd`            TIMESTAMP(2)   NOT NULL,
    `kosten`               DECIMAL(16, 2) NOT NULL,
    `creditiban`           VARCHAR(45)    NOT NULL,
    `debitiban`            VARCHAR(45)    NOT NULL,
    `euroammount`          DECIMAL(16, 2) NOT NULL,
    `debitportefeuilleID`  INT            NOT NULL,
    `debitassetId`         INT            NOT NULL,
    `assetammount`         DECIMAL(16, 6) NOT NULL,
    `creditportefeuilleID` INT            NOT NULL,
    `creditassetId`        INT            NOT NULL,
    PRIMARY KEY (`transactieId`),
    INDEX `verzinzelf3_idx` (`debitportefeuilleID` ASC, `debitassetId` ASC) VISIBLE,
    INDEX `verzinzelf9_idx` (`creditiban` ASC) VISIBLE,
    INDEX `verzinzelf10_idx` (`debitiban` ASC) VISIBLE,
    INDEX `verzinzelf7_idx` (`creditportefeuilleID` ASC, `creditassetId`
                             ASC) VISIBLE,
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
    INDEX `verzinzelf6_idx` (`assetId` ASC) VISIBLE,
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

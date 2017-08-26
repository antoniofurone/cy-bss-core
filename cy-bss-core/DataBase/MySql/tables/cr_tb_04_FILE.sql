CREATE TABLE BSST_FIL_FILE (
	FILE_N_FILE_ID 			INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	FILE_S_NAME				VARCHAR(30) NOT NULL,
	FILE_N_SIZE				INTEGER UNSIGNED NOT NULL,
	FILE_S_CONTENT_TYPE		VARCHAR(60) NOT NULL,
	FILE_B_CONTENT			LONGBLOB NOT NULL,
	FILE_S_TYPE				VARCHAR(30),
	FILE_S_ENTITY_NAME		VARCHAR(20),
	FILE_N_ENTITY_ID		INTEGER UNSIGNED,
	FILE_S_NOTE				VARCHAR(500),
	FILE_S_VISIBILITY		CHAR(1) NOT NULL DEFAULT 'P',	
	PRIMARY KEY(FILE_N_FILE_ID),
	CONSTRAINT BSST_FIL_FILE_CK1 CHECK(FILE_S_ENTITY_NAME IN ('Person','Company','Location','Ticket','Product')),
	CONSTRAINT BSST_FIL_FILE_CK2 CHECK(FILE_S_VISIBILITY IN ('P','R'))
);


--ALTER TABLE BSST_FIL_FILE
--ADD FILE_S_VISIBILITY CHAR(1) NOT NULL DEFAULT 'P';

--ALTER TABLE BSST_FIL_FILE
--ADD CONSTRAINT BSST_FIL_FILE_CK2 CHECK(FILE_S_VISIBILITY IN ('P','R'));




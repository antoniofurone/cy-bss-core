
DROP TABLE BSST_BOP_OPERATION_PARAM;
DROP TABLE BSST_BSO_SERVICE_OPERATION;
DROP TABLE BSST_BSV_SERVICE;

CREATE TABLE BSST_BSV_SERVICE (
	BSV_N_SERVICE_ID 		INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	BSV_S_SERVICE_NAME 	 	VARCHAR(30) NOT NULL,
	BSV_S_SERVICE_URL	 	VARCHAR(50) NOT NULL,
	PRIMARY KEY(BSV_N_SERVICE_ID),
	UNIQUE(BSV_S_SERVICE_NAME)
);

CREATE TABLE BSST_BSO_SERVICE_OPERATION (
	BSO_N_OPERATION_ID 		INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	BSV_N_SERVICE_ID 		INTEGER UNSIGNED NOT NULL,
	BSO_S_NAME	 			VARCHAR(30) NOT NULL,
	BSO_S_METHOD		 	VARCHAR(30) NOT NULL,	
	BSO_S_OPERATION_URL	 	VARCHAR(50) NOT NULL,
	BSO_S_DESCRIPTION	 	VARCHAR(50),
	PRIMARY KEY(BSO_N_OPERATION_ID),
	CONSTRAINT BSO_S_METHOD_CK CHECK(BSO_S_METHOD IN ('GET','POST')),
	CONSTRAINT BSST_BSO_SERVICE_OPERATION_FK1 FOREIGN KEY(BSV_N_SERVICE_ID) REFERENCES BSST_BSV_SERVICE(BSV_N_SERVICE_ID)
);

CREATE TABLE BSST_BOP_OPERATION_PARAM (
	BOP_PARAM_NAME 			VARCHAR(30) NOT NULL,
	BSO_N_OPERATION_ID 		INTEGER UNSIGNED,
	BOP_C_FLG_URL 			CHAR(1) 	NOT NULL DEFAULT 'N',
	BOP_S_DESCRIPTION	 	VARCHAR(50),
	BOP_C_REQUIRED	 		CHAR(1)		NOT NULL DEFAULT 'N',
	PRIMARY KEY(BOP_PARAM_NAME,BSO_N_OPERATION_ID),
	CONSTRAINT BOP_C_FLG_URL_CK CHECK(BOP_C_FLG_URL IN ('Y','N')),
	CONSTRAINT BOP_C_REQUIRED_CK CHECK(BOP_C_REQUIRED IN ('Y','N')),
	CONSTRAINT BSST_BOP_OPERATION_PARAM_FK1 FOREIGN KEY(BSO_N_OPERATION_ID) REFERENCES BSST_BSO_SERVICE_OPERATION(BSO_N_OPERATION_ID)
);

DROP TABLE BSST_USE_USER_SESSION;
DROP TABLE BSST_USR_USER;
DROP TABLE BSST_URO_ROLE;


CREATE TABLE BSST_URO_ROLE (
	URO_N_ROLE_ID 			INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	URO_S_NAME	 			VARCHAR(30) NOT NULL,
	URO_S_DESCRIPTION		VARCHAR(60),
	URO_N_PARENT_ROLE_ID 	INTEGER UNSIGNED,
	PRIMARY KEY(URO_N_ROLE_ID),
	UNIQUE(URO_S_NAME),
	CONSTRAINT BSST_URO_ROLE_FK1 FOREIGN KEY(URO_N_PARENT_ROLE_ID) REFERENCES BSST_URO_ROLE(URO_N_ROLE_ID)
);

DROP TABLE BSST_LAN_LANGUAGE;
CREATE TABLE BSST_LAN_LANGUAGE (
	LAN_N_LANG_ID 		INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	LAN_S_CODE			VARCHAR(6) NOT NULL,
	LAN_S_NAME			VARCHAR(30) NOT NULL,
	PRIMARY KEY(LAN_N_LANG_ID),
	UNIQUE(LAN_S_CODE)
);

CREATE TABLE BSST_USR_USER (
	USR_N_USER_ID	INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	USR_S_USER_ID	VARCHAR(60) NOT NULL,
	USR_S_PSW		VARCHAR(20) NOT NULL,
	USR_S_NAME		VARCHAR(50) NOT NULL,
	URO_N_ROLE_ID	INTEGER UNSIGNED NOT NULL,
	LAN_N_LANG_ID	INTEGER UNSIGNED NOT NULL,
	USR_C_ACTIVE	CHAR(1) DEFAULT 'Y',	
	PRIMARY KEY(USR_N_USER_ID),
	UNIQUE(USR_S_USER_ID),
	CONSTRAINT BSST_USR_USER_FK1 FOREIGN KEY(URO_N_ROLE_ID) REFERENCES BSST_URO_ROLE(URO_N_ROLE_ID),
	CONSTRAINT BSST_USR_USER_FK2 FOREIGN KEY(LAN_N_LANG_ID) REFERENCES BSST_LAN_LANGUAGE(LAN_N_LANG_ID)
);

CREATE TABLE BSST_USE_USER_SESSION (
	USR_N_USER_ID		INTEGER UNSIGNED NOT NULL,
	USE_S_TOKEN			CHAR(32) NOT NULL,
	USE_T_CREATE_TIME 	TIMESTAMP NOT NULL,
	USE_T_UPDATE_TIME	TIMESTAMP NOT NULL
);

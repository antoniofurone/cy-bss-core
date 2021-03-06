CREATE TABLE BSST_ATY_ATTR_TYPE(
	ATY_N_ATTR_TYPE_ID 	INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	ATY_S_NAME					VARCHAR(30) NOT NULL,
	ATY_S_DESCRIPTION			VARCHAR(100),
	PRIMARY KEY(ATY_N_ATTR_TYPE_ID),
	UNIQUE(ATY_S_NAME)
);

CREATE TABLE BSST_OBJ_OBJECT (
	OBJ_N_OBJECT_ID 	INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	OBJ_S_NAME			VARCHAR(20) NOT NULL,
	OBJ_S_ENTITY_NAME	VARCHAR(20),
	PRIMARY KEY(OBJ_N_OBJECT_ID),
	UNIQUE(OBJ_S_NAME),
	UNIQUE(OBJ_S_ENTITY_NAME)
);

CREATE TABLE BSST_ATT_ATTRIBUTE (
	ATT_N_ATTRIBUTE_ID 	INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	ATT_S_NAME			VARCHAR(30) NOT NULL,
	ATY_N_ATTR_TYPE_ID	INTEGER UNSIGNED NOT NULL,
	OBJ_N_OBJECT_ID 	INTEGER UNSIGNED NOT NULL,
	PRIMARY KEY(ATT_N_ATTRIBUTE_ID),
	UNIQUE(ATT_S_NAME, OBJ_N_OBJECT_ID),
	CONSTRAINT BSST_ATT_ATTRIBUTE_FK1 FOREIGN KEY(ATY_N_ATTR_TYPE_ID) REFERENCES BSST_ATY_ATTR_TYPE(ATY_N_ATTR_TYPE_ID),
	CONSTRAINT BSST_ATT_ATTRIBUTE_FK2 FOREIGN KEY(OBJ_N_OBJECT_ID) REFERENCES BSST_OBJ_OBJECT(OBJ_N_OBJECT_ID)
);


CREATE TABLE BSST_ATV_ATTR_VALUE (
	ATV_N_OBJ_INST_ID	INTEGER UNSIGNED NOT NULL,
	ATT_N_ATTRIBUTE_ID 	INTEGER UNSIGNED NOT NULL,
	ATV_S_VALUE			VARCHAR(300) NOT NULL,
	PRIMARY KEY(ATV_N_OBJ_INST_ID,ATT_N_ATTRIBUTE_ID),
	CONSTRAINT BSST_ATV_ATTR_VALUE_FK1 FOREIGN KEY(ATT_N_ATTRIBUTE_ID) REFERENCES BSST_ATT_ATTRIBUTE(ATT_N_ATTRIBUTE_ID)
);
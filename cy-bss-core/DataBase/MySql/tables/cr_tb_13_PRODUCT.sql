CREATE TABLE BSST_PCA_PRODUCT_CATEGORY (
	PCA_N_PRODUCT_CATEGORY_ID 	INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	PCA_S_NAME					VARCHAR(30) NOT NULL,
	PCA_N_VAT					DOUBLE NOT NULL,
	MET_N_METRIC_ID             INTEGER UNSIGNED NOT NULL,
	PCA_S_DESC					VARCHAR(100),
	UNIQUE(PCA_S_NAME),
	PRIMARY KEY(PCA_N_PRODUCT_CATEGORY_ID),
	CONSTRAINT BSST_PCA_PRODUCT_CATEGORY_FK1 FOREIGN KEY(MET_N_METRIC_ID) 
			REFERENCES BSST_MET_METRIC(MET_N_METRIC_ID)
	
);

CREATE TABLE BSST_PTY_PRODUCT_TYPE (
	PTY_N_PRODUCT_TYPE_ID 	INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	PTY_S_NAME				VARCHAR(30) NOT NULL,
	PTY_S_DESC				VARCHAR(100),
	UNIQUE(PTY_S_NAME),
	PRIMARY KEY(PTY_N_PRODUCT_TYPE_ID)
);

CREATE TABLE BSST_PRO_PRODUCT (
	PRO_N_PRODUCT_ID 			INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	PRO_S_NAME					VARCHAR(100) NOT NULL,
	PRO_S_DESC					VARCHAR(500) NOT NULL,
	PRO_S_CODE					VARCHAR(50),
	PCA_N_PRODUCT_CATEGORY_ID 	INTEGER UNSIGNED NOT NULL,
	PTY_N_PRODUCT_TYPE_ID 		INTEGER UNSIGNED NOT NULL,
	PRO_N_PRODUCT_PARENT_ID		INTEGER UNSIGNED,
	PRO_N_PRODUCER_ID			INTEGER UNSIGNED,
	PRIMARY KEY(PRO_N_PRODUCT_ID),
	UNIQUE(PRO_S_NAME),
	CONSTRAINT BSST_PRO_PRODUCT_FK1 FOREIGN KEY(PCA_N_PRODUCT_CATEGORY_ID) 
			REFERENCES BSST_PCA_PRODUCT_CATEGORY(PCA_N_PRODUCT_CATEGORY_ID),
	CONSTRAINT BSST_PRO_PRODUCT_FK2 FOREIGN KEY(PTY_N_PRODUCT_TYPE_ID) 
			REFERENCES BSST_PTY_PRODUCT_TYPE(PTY_N_PRODUCT_TYPE_ID),
	CONSTRAINT BSST_PRO_PRODUCT_FK3 FOREIGN KEY(PRO_N_PRODUCT_PARENT_ID) 
			REFERENCES BSST_PRO_PRODUCT(PRO_N_PRODUCT_ID),
	CONSTRAINT BSST_PRO_PRODUCT_FK4 FOREIGN KEY(PRO_N_PRODUCER_ID) 
			REFERENCES BSST_COM_COMPANY(COM_N_COMPANY_ID)
);




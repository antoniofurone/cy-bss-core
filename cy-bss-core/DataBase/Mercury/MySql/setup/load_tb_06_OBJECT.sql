delete from BSST_ATY_ATTR_TYPE;		

ALTER TABLE BSST_ATY_ATTR_TYPE AUTO_INCREMENT= 1;

INSERT INTO BSST_ATY_ATTR_TYPE(ATY_S_NAME,ATY_S_DESCRIPTION)
	VALUES ('String','String');
INSERT INTO BSST_ATY_ATTR_TYPE(ATY_S_NAME,ATY_S_DESCRIPTION)
	VALUES ('Integer','Integer Number');
INSERT INTO BSST_ATY_ATTR_TYPE(ATY_S_NAME,ATY_S_DESCRIPTION)
	VALUES ('Real','Real Number');
	

delete from BSST_OBJ_OBJECT;		

ALTER TABLE BSST_OBJ_OBJECT AUTO_INCREMENT= 1;
	
INSERT INTO BSST_OBJ_OBJECT(OBJ_S_NAME,OBJ_S_ENTITY_NAME)
	VALUES ('Person','Person');
INSERT INTO BSST_OBJ_OBJECT(OBJ_S_NAME,OBJ_S_ENTITY_NAME)
	VALUES ('Company','Company');
INSERT INTO BSST_OBJ_OBJECT(OBJ_S_NAME,OBJ_S_ENTITY_NAME)
	VALUES ('Location','Location');
INSERT INTO BSST_OBJ_OBJECT(OBJ_S_NAME,OBJ_S_ENTITY_NAME)
	VALUES ('Product','Product');
INSERT INTO BSST_OBJ_OBJECT(OBJ_S_NAME,OBJ_S_ENTITY_NAME)
	VALUES ('Purchase','Purchase');
INSERT INTO BSST_OBJ_OBJECT(OBJ_S_NAME,OBJ_S_ENTITY_NAME)
	VALUES ('Sale','Sale');

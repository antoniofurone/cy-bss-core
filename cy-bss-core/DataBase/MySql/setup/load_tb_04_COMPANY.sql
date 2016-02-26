delete from BSST_CRO_COMPANY_PERS_ROLE;		

ALTER TABLE BSST_CRO_COMPANY_PERS_ROLE AUTO_INCREMENT= 1;

INSERT INTO BSST_CRO_COMPANY_PERS_ROLE(CRO_S_NAME,CRO_S_DESC)
	VALUES ('Owner','Owner');
INSERT INTO BSST_CRO_COMPANY_PERS_ROLE(CRO_S_NAME,CRO_S_DESC)
	VALUES ('Employee','Employee');
INSERT INTO BSST_CRO_COMPANY_PERS_ROLE(CRO_S_NAME,CRO_S_DESC)
	VALUES ('Manager','Manager');
	

delete from BSST_CRL_COMPANY_PERS_ROLE_LANG;		
	
INSERT INTO BSST_CRL_COMPANY_PERS_ROLE_LANG(CRO_N_ROLE_ID,LAN_N_LANG_ID,CRL_S_NAME,CRL_S_DESC)
VALUES (
(SELECT CRO_N_ROLE_ID from BSST_CRO_COMPANY_PERS_ROLE WHERE CRO_S_NAME='Owner'),
(SELECT LAN_N_LANG_ID from BSST_LAN_LANGUAGE WHERE LAN_S_CODE='it'),
'Proprietario','Proprietario');

INSERT INTO BSST_CRL_COMPANY_PERS_ROLE_LANG(CRO_N_ROLE_ID,LAN_N_LANG_ID,CRL_S_NAME,CRL_S_DESC)
VALUES (
(SELECT CRO_N_ROLE_ID from BSST_CRO_COMPANY_PERS_ROLE WHERE CRO_S_NAME='Employee'),
(SELECT LAN_N_LANG_ID from BSST_LAN_LANGUAGE WHERE LAN_S_CODE='it'),
'Dipendente','Dipendente');

INSERT INTO BSST_CRL_COMPANY_PERS_ROLE_LANG(CRO_N_ROLE_ID,LAN_N_LANG_ID,CRL_S_NAME,CRL_S_DESC)
VALUES (
(SELECT CRO_N_ROLE_ID from BSST_CRO_COMPANY_PERS_ROLE WHERE CRO_S_NAME='Manager'),
(SELECT LAN_N_LANG_ID from BSST_LAN_LANGUAGE WHERE LAN_S_CODE='it'),
'Manager','Manager');
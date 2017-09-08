delete from BSST_OPE_OPERATION_GRANT;
delete from BSST_USE_USER_SESSION;
delete from BSST_USR_USER;		
delete from BSST_URO_ROLE order by URO_N_ROLE_ID desc;

ALTER TABLE BSST_URO_ROLE AUTO_INCREMENT= 1;

insert into BSST_URO_ROLE(URO_S_NAME,URO_S_DESCRIPTION,URO_N_PARENT_ROLE_ID)
	values ('User', 'Simple User',null);
insert into BSST_URO_ROLE(URO_S_NAME,URO_S_DESCRIPTION,URO_N_PARENT_ROLE_ID)
	values ('Administrator', 'Administrator User',1);
insert into BSST_URO_ROLE(URO_S_NAME,URO_S_DESCRIPTION,URO_N_PARENT_ROLE_ID)
	values ('Super Administrator', 'Super Administrator User',2);
insert into BSST_URO_ROLE(URO_S_NAME,URO_S_DESCRIPTION,URO_N_PARENT_ROLE_ID)
	values ('Partner', 'Partner User',1);

ALTER TABLE BSST_USR_USER AUTO_INCREMENT= 1;
	
insert into BSST_USR_USER(USR_S_USER_ID,USR_B_PSW,USR_B_SALT,USR_S_NAME,URO_N_ROLE_ID,LAN_N_LANG_ID)
	values ('cybss',UNHEX('51a3bb0faaf55a687a6d9a5e16d0ea1591f549bf'),UNHEX('c8fe770e4de35b1b'),'cybss',
	(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='Super Administrator'),
	(select LAN_N_LANG_ID from BSST_LAN_LANGUAGE where LAN_S_CODE='en')
	);


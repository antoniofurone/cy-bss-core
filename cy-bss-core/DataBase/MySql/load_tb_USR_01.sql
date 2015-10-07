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
	
	
delete from BSST_LAN_LANGUAGE;

ALTER TABLE BSST_LAN_LANGUAGE AUTO_INCREMENT= 1;

insert into BSST_LAN_LANGUAGE(LAN_S_CODE,LAN_S_NAME)
	values ('IT', 'Italiano');
insert into BSST_LAN_LANGUAGE(LAN_S_CODE,LAN_S_NAME)
	values ('EN', 'English');
	

ALTER TABLE BSST_USR_USER AUTO_INCREMENT= 1;
	
insert into BSST_USR_USER(USR_S_USER_ID,USR_S_PSW,USR_S_NAME,URO_N_ROLE_ID,LAN_N_LANG_ID)
	values ('cybss','cybss','cybss',
	(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='User'),
	(select LAN_N_LANG_ID from BSST_LAN_LANGUAGE where LAN_S_CODE='EN')
	);
		
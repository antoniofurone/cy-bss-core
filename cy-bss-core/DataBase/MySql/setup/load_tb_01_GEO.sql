
delete from BSST_USR_USER;		
	
delete from BSST_LAN_LANGUAGE;

ALTER TABLE BSST_LAN_LANGUAGE AUTO_INCREMENT= 1;

insert into BSST_LAN_LANGUAGE(LAN_S_CODE,LAN_S_NAME)
	values ('it', 'Italiano');
insert into BSST_LAN_LANGUAGE(LAN_S_CODE,LAN_S_NAME)
	values ('en', 'English');

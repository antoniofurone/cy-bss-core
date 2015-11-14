delete from BSST_USR_USER;		
	
delete from BSST_LAN_LANGUAGE;

ALTER TABLE BSST_LAN_LANGUAGE AUTO_INCREMENT= 1;

insert into BSST_LAN_LANGUAGE(LAN_S_CODE,LAN_S_NAME)
	values ('it', 'Italiano');
insert into BSST_LAN_LANGUAGE(LAN_S_CODE,LAN_S_NAME)
	values ('en', 'English');


delete from BSST_PER_PERSON;	
delete from BSST_CIT_CITY;	
delete from BSST_CON_COUNTRY;
ALTER TABLE BSST_CON_COUNTRY AUTO_INCREMENT= 1;

insert into BSST_CON_COUNTRY(CON_S_CODE,CON_S_NAME)
	values ('IT', 'Italia');

	
ALTER TABLE BSST_CIT_CITY AUTO_INCREMENT= 1;

insert into BSST_CIT_CITY(CIT_S_NAME,CIT_S_CODE,CIT_S_STATE_REGION,CIT_D_LOC_LAT,CIT_D_LOC_LNG,CIT_S_ZIP,CON_N_COUNTRY_ID)
	values ('Carovigno', 'B809', 'Puglia', 0, 0, '72012',
	(select CON_N_COUNTRY_ID from BSST_CON_COUNTRY where CON_S_CODE='IT'));

		
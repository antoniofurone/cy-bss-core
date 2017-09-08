delete from BSST_AVA_APP_VAR where APP_N_APP_ID=(select APP_N_APP_ID from BSST_APP_APP where APP_S_NAME='core-bss');		
delete from BSST_APP_APP where APP_S_NAME='core-bss';		

insert into BSST_APP_APP(APP_S_NAME,APP_S_DESC)
	values ('core-bss', 'Core Bss');

	
	
insert BSST_AVA_APP_VAR (APP_N_APP_ID,ARV_S_NAME,ARV_S_VALUE,ARV_C_TYPE)
values
((select APP_N_APP_ID from BSST_APP_APP where APP_S_NAME='core-bss'),'Version','Mercury','S');

insert BSST_AVA_APP_VAR (APP_N_APP_ID,ARV_S_NAME,ARV_S_VALUE,ARV_C_TYPE)
values
((select APP_N_APP_ID from BSST_APP_APP where APP_S_NAME='core-bss'),'Author','Antonio Furone','S');

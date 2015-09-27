
delete from BSST_BOP_OPERATION_PARAM;
delete from BSST_BSO_SERVICE_OPERATION;
delete from BSST_BSV_SERVICE;


ALTER TABLE BSST_BSV_SERVICE AUTO_INCREMENT = 1;
ALTER TABLE BSST_BSO_SERVICE_OPERATION AUTO_INCREMENT= 1;

-- ServiceId=1
insert into BSST_BSV_SERVICE(BSV_S_SERVICE_NAME,BSV_S_SERVICE_URL)
	values ('BssService','/cybss-service');

-- OpId=1	
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values (1,'GET','getAll','/getAll','Return all services');

-- OpId=2
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values (1,'GET','get','/get','Return service for id');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION)
		values ('ServID',2,'Y','Y','Service Id');

-- OpId=3
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values (1,'GET','getServOperations','/getServOperations','Return service operation by service id');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION)
		values ('ServID',3,'Y','Y','Service Id');

-- OpId=4
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values (1,'GET','getOperation','/getOperation','Return operation by operation id');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION)
		values ('OpID',4,'Y','Y','Operation Id');
		
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
	values ('cybss','cybss','cybss',3,2);
		

delete from BSST_BOP_OPERATION_PARAM;
delete from BSST_BSO_SERVICE_OPERATION;
delete from BSST_BSV_SERVICE;


ALTER TABLE BSST_BSV_SERVICE AUTO_INCREMENT = 1;
ALTER TABLE BSST_BSO_SERVICE_OPERATION AUTO_INCREMENT= 1;

-- ServiceId=1
insert into BSST_BSV_SERVICE(BSV_S_SERVICE_NAME,BSV_S_SERVICE_URL)
	values ('BssService','/cybss-service');
-- ServiceId=2
insert into BSST_BSV_SERVICE(BSV_S_SERVICE_NAME,BSV_S_SERVICE_URL)
	values ('BssAuth','/cybss-auth');

-- OpId=1	
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values (1,'GET','getAll','/getAll','Return all services');

-- OpId=2
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values (1,'GET','get','/{servId}/get','Return service for id');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION)
		values ('servId',2,'Y','Y','Service Id');

-- OpId=3
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values (1,'GET','getOperations','/{servId}/getServOperations','Return service operation by service id');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION)
		values ('servId',3,'Y','Y','Service Id');

-- OpId=4
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values (1,'GET','getOperation','/{opId}/getOperation','Return operation by operation id');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION)
		values ('opId',4,'Y','Y','Operation Id');

-- OpId=5
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values (2,'POST','logOn','/logOn','LogOn - Returns security token');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('userId',5,'Y','Y','User Id',1);
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('pwd',5,'Y','Y','Password',2);
		
-- OpId=6
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values (2,'GET','logOff','/logOff','LogOff - Discard current session');
		

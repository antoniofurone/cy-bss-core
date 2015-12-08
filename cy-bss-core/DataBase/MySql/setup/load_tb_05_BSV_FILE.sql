delete from BSST_OPE_OPERATION_GRANT
	where BSO_N_OPERATION_ID in 
	(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION
	where BSV_N_SERVICE_ID in (select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME 
	in ('File')));

delete from BSST_BOP_OPERATION_PARAM
	where BSO_N_OPERATION_ID in 
	(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION
	where BSV_N_SERVICE_ID in (select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME 
	in ('File')));

delete from BSST_BSO_SERVICE_OPERATION
	where BSV_N_SERVICE_ID in (select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME 
	in ('File'));
	
delete from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME 
	in ('File');



insert into BSST_BSV_SERVICE(BSV_S_SERVICE_NAME,BSV_S_SERVICE_URL)
	values ('File','/file');
	
-- upload @ File	
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='File')
	,'POST','upload','/upload','Upload File');

	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='upload' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='File')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='User'));
	
	
-- download @ File	
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='File')
	,'GET','download','/download','Download File');

	
-- remove @ File	
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='File')
	,'GET','remove','/remove','Remove File');
	
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='remove' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='File')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='User'));

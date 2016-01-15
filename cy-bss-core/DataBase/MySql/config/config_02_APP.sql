delete from BSST_OPE_OPERATION_GRANT
	where BSO_N_OPERATION_ID in 
	(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION
	where BSV_N_SERVICE_ID in (select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME 
	in ('App')));

delete from BSST_BOP_OPERATION_PARAM
	where BSO_N_OPERATION_ID in 
	(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION
	where BSV_N_SERVICE_ID in (select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME 
	in ('App')));

delete from BSST_BSO_SERVICE_OPERATION
	where BSV_N_SERVICE_ID in (select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME 
	in ('App'));
	
delete from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME 
	in ('App');

insert into BSST_BSV_SERVICE(BSV_S_SERVICE_NAME,BSV_S_SERVICE_URL)
	values ('App','/app');
	
-- add @ App
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'),'POST','add','/add','Create app');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('name',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='add' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'))
		,'Y','Y','Name',1);
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('descr',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='add' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'))
		,'Y','Y','Description',2);
		
			
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='add' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='Administrator')
		);


-- update @ App
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'),'POST','update','/{id}/update','Update app');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('id',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='update' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'))
		,'Y','Y','id',1,'number');
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('name',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='update' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'))
		,'Y','Y','Name',1);
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('descr',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='update' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'))
		,'Y','Y','Description',2);
		
			
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='update' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='Administrator')
		);

		
-- get @ App
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'),'GET','get','/{id}/get','Get app');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('id',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='get' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'))
		,'Y','Y','id',1,'number');
				
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='get' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='User')
		);


-- getByName @ App
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'),'GET','getByName','/getByName','Get app by name');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('name',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getByName' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'))
		,'N','Y','name',1);
				
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getByName' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='User')
		);

-- remove @ App
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'),'GET','remove','/{id}/remove','Remove app');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('id',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='remove' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'))
		,'Y','Y','id',1,'number');
				
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='get' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='Administrator')
		);
		
		
-- getParam @ App
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'),'GET','getParam','/{id}/getParam/{paramName}','Get app parameter');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('id',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getParam' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'))
		,'Y','Y','Id',1,'number');
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('paramName',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getParam' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'))
		,'Y','Y','Param Name',2);
		
			
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getParam' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='User')
		);
		
-- getMessage @ App
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'),'GET','getMessage','/{appId}/getMessage/{id}','Get app message');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('appId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getMessage' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'))
		,'Y','Y','App Id',1,'number');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('id',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getMessage' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'))
		,'Y','Y','Message Id',2);
	
			
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getMessage' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='User')
		);
		

-- getVariable @ App
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'),'GET','getVariable','/{id}/getVariable/{name}','Get app variable');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('id',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getVariable' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'))
		,'Y','Y','Id',1,'number');
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('name',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getVariable' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'))
		,'Y','Y','Variable Name',2);
		
			
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getVariable' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='User')
		);

-- putVariable @ App
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'),'PUT','putVariable','/{id}/putVariable','Put app variable');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('appId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='putVariable' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'))
		,'Y','Y','Id',1,'number');
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('name',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='putVariable' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'))
		,'Y','Y','Variable Name',2);
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('value',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='putVariable' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'))
		,'Y','Y','Variable Value',3);
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('type',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='putVariable' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App'))
		,'Y','Y','Variable Type',4);
		
			
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='putVariable' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='App')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='User')
		);
		
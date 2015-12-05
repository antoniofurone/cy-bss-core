delete from BSST_OPE_OPERATION_GRANT
	where BSO_N_OPERATION_ID in 
	(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION
	where BSV_N_SERVICE_ID in (select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME 
	in ('Ticket')));

delete from BSST_BOP_OPERATION_PARAM
	where BSO_N_OPERATION_ID in 
	(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION
	where BSV_N_SERVICE_ID in (select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME 
	in ('Ticket')));

delete from BSST_BSO_SERVICE_OPERATION
	where BSV_N_SERVICE_ID in (select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME 
	in ('Ticket'));
	
delete from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME 
	in ('Ticket');

insert into BSST_BSV_SERVICE(BSV_S_SERVICE_NAME,BSV_S_SERVICE_URL)
	values ('Ticket','/ticket');
	
	
-- add @ Ticket
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'),'POST','add','/add','Create ticket');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('text',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='add' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'Y','Y','Text',1);
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('categoryId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='add' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'Y','N','Category Id',2);
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('locationId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='add' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'Y','N','Location Id',3);
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('personId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='add' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'Y','N','Person Id',4);
	
		
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='add' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='User')
		);
		
-- get @ Ticket
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'),'GET','get','/{id}/get','Get Ticket');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('id',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='get' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'Y','Y','id',1);
		
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='get' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='User')
		);

-- getFiles @ Ticket
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'),'GET','getFiles','/{id}/getFiles','Get ticket files');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('id',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getFiles' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'Y','Y','id',1);
		
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getFiles' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='User')
		);
		
		
-- getCayegoryAll @ Ticket
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'),'GET','getCategoryAll','/getCategoryAll','Get Category All');
		
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getCategoryAll' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='User')
		);
		

-- getNextStates @ Ticket
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'),'GET','getNextStates','/{statusId}/getNextStates','Get Next States');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('statusId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getNextStates' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'Y','Y','statusId',1);
	
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getNextStates' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='User')
		);
		
-- changeStatus @ Ticket
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'),'POST','changeStatus','/{id}/changeStatus','Change Status');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('id',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='changeStatus' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'Y','Y','id',1);
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('statusId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='changeStatus' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'Y','Y','statusId',2);
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('note',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='changeStatus' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'Y','N','note',3);
		
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='changeStatus' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='User')
		);
		
		
		
-- update @ Ticket
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'),'POST','update','/{id}/update','Update ticket');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('id',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='update' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'Y','Y','id',1);
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('text',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='update' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'Y','Y','Text',1);
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('categoryId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='update' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'Y','N','Category Id',2);
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('locationId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='update' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'Y','N','Location Id',3);
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('personId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='update' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'Y','N','Person Id',4);
			
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='update' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='Administrator')
		);

		
-- setLocation @ Ticket
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'),'POST','setLocation','/{id}/setLocation','Set ticket location');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('id',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='setLocation' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'Y','Y','id',1);
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('latitude',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='setLocation' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'Y','Y','Latitute',2,'text');
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('longitude',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='setLocation' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'Y','Y','Longitude',3,'text');
			
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='setLocation' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='User')
		);
		
		
		
-- remove @ Ticket
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'),'GET','remove','/{id}/remove','Remove ticket');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('id',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='remove' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'Y','Y','id',1);
		
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='remove' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='Administrator')
		);

-- find @ Ticket
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'),'GET','find','/find','Find tickets');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('text',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='find' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'N','N','Text',1);
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('categoryId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='find' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'N','N','Category Id',2);

	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('statusId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='find' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'N','N','Status Id',3);

	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('personId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='find' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'N','N','Person Id',4);

	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('fromDate',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='find' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'N','N','From Date',5,'date');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('toDate',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='find' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'N','N','To Date',6,'date');
		
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('offset',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='find' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'N','N','offset',7,'number');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('size',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='find' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket'))
		,'N','N','size',8,'number');	
		
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='find' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Ticket')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='User')
		);
				

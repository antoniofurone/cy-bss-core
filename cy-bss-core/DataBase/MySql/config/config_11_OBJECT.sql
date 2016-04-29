delete from BSST_OPE_OPERATION_GRANT
	where BSO_N_OPERATION_ID in 
	(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION
	where BSV_N_SERVICE_ID in (select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME 
	in ('Object')));

delete from BSST_BOP_OPERATION_PARAM
	where BSO_N_OPERATION_ID in 
	(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION
	where BSV_N_SERVICE_ID in (select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME 
	in ('Object')));

delete from BSST_BSO_SERVICE_OPERATION
	where BSV_N_SERVICE_ID in (select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME 
	in ('Object'));
	
delete from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME 
	in ('Object');
	
insert into BSST_BSV_SERVICE(BSV_S_SERVICE_NAME,BSV_S_SERVICE_URL)
	values ('Object','/object');
	
-- getObjectAll @ Object
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'),'GET','getObjectAll','/getObjectAll','Return all objects');

-- getAttributeTypeAll @ Object
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'),'GET','getAttributeTypeAll','/getAttributeTypeAll','Return all attributes types');

-- addAttribute @ Object
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'),'POST','addAttribute','/addAttribute','Add attribute');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('name',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='addAttribute' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'))
		,'Y','Y','name',1);
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('typeId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='addAttribute' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'))
		,'Y','Y','typeId',2,'number');
		
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('objectId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='addAttribute' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'))
		,'Y','Y','objectId',3,'number');
	
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='addAttribute' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='Administrator')
		);
	

-- updateAttribute @ Object
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'),'POST','updateAttribute','/updateAttribute','Update attribute');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('id',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='updateAttribute' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'))
		,'Y','Y','id',1,'number');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('name',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='updateAttribute' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'))
		,'Y','Y','name',2);
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('typeId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='updateAttribute' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'))
		,'Y','Y','typeId',3,'number');
		
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('objectId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='updateAttribute' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'))
		,'Y','Y','objectId',4,'number');
	
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='updateAttribute' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='Administrator')
		);
		
		
-- getAttributes @ Object
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'),'GET','getAttributes','/{id}/getAttributes','Return all attributes for object');
		
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('id',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getAttributes' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'))
		,'Y','Y','id',1,'number');
		
-- getAttribute @ Object
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'),'GET','getAttribute','/{attrId}/getAttribute','Return attribute');
		
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('attrId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getAttribute' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'))
		,'Y','Y','attrId',1,'number');
		
-- getAttributeByName @ Object
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'),'GET','getAttributeByName','/{id}/getAttributeByName/{name}','Return attribute');
		
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('id',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getAttributeByName' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'))
		,'Y','Y','id',1,'number');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('name',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='getAttributeByName' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'))
		,'Y','Y','name',2);
		
-- remove @ Object
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'),'GET','removeAttribute','/{attrId}/removeAttribute','Remove attribute');
		
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('attrId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='removeAttribute' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'))
		,'Y','Y','attrId',1,'number');
		
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='removeAttribute' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='Administrator')
		);
		
-- setAttributeValue @ Object
insert into BSST_BSO_SERVICE_OPERATION (BSV_N_SERVICE_ID,BSO_S_METHOD,BSO_S_NAME,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION)
	values ((select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'),'POST','setAttributeValue','/setAttributeValue','Set attribute value');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('objInstId',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='setAttributeValue' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'))
		,'Y','Y','objInstId',1,'number');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER,BOP_S_TYPE)
		values ('id',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='setAttributeValue' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'))
		,'Y','Y','attrId',2,'number');
	
	insert into BSST_BOP_OPERATION_PARAM(BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_C_REQUIRED,BOP_S_DESCRIPTION,BOP_N_SHOW_ORDER)
		values ('value',
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='setAttributeValue' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object'))
		,'Y','Y','value',3);
		
	insert into BSST_OPE_OPERATION_GRANT(BSO_N_OPERATION_ID,URO_N_ROLE_ID)
		values(
		(select BSO_N_OPERATION_ID from BSST_BSO_SERVICE_OPERATION where BSO_S_NAME='setAttributeValue' and BSV_N_SERVICE_ID=(select BSV_N_SERVICE_ID from BSST_BSV_SERVICE where BSV_S_SERVICE_NAME='Object')),
		(select URO_N_ROLE_ID from BSST_URO_ROLE where URO_S_NAME='User')
		);

drop view BSSV_COMPANY;

create view BSSV_COMPANY
(ID,CODE,NAME,ADDRESS,ZIP,CITY_ID,CITY,COUNTRY,
FISCAL_CODE,VAT_CODE,HEAD_DEPT_ID
)
as
select
	a.COM_N_COMPANY_ID,a.COM_S_CODE,a.COM_S_NAME,a.COM_S_ADDRESS,a.COM_S_ZIP,
	a.CIT_N_CITY_ID,c.CIT_S_NAME,d.CON_S_NAME,a.COM_S_FISCAL_CODE,a.COM_S_VAT_CODE,b.CDE_N_DEPT_ID
	from BSST_COM_COMPANY a
	join BSST_CDE_COMPANY_DEPT b on a.COM_N_COMPANY_ID=b.COM_N_COMPANY_ID
	left join BSST_CIT_CITY c on a.CIT_N_CITY_ID=c.CIT_N_CITY_ID
	left join BSST_CON_COUNTRY d on c.CON_N_COUNTRY_ID=d.CON_N_COUNTRY_ID
	where b.CDE_N_PARENT_DEPT_ID is NULL;
	
	
drop view BSSV_COMPANY_DEPT;

create view BSSV_COMPANY_DEPT
(ID,CODE,NAME,ADDRESS,ZIP,CITY_ID,CITY,COUNTRY,
PARENT_DEPT_ID,PARENT_DEPT_CODE,PARENT_DEPT,
COMPANY_ID,COMPANY_CODE,COMPANY)
as
select a.CDE_N_DEPT_ID,a.CDE_S_CODE,a.CDE_S_NAME,a.CDE_S_ADDRESS,
a.CDE_S_ZIP,a.CIT_N_CITY_ID,b.CIT_S_NAME,c.CON_S_NAME,
a.CDE_N_PARENT_DEPT_ID,d.CDE_S_CODE,d.CDE_S_NAME,
a.COM_N_COMPANY_ID,e.COM_S_CODE,e.COM_S_NAME
from BSST_CDE_COMPANY_DEPT a
left join BSST_CIT_CITY b on a.CIT_N_CITY_ID=b.CIT_N_CITY_ID
left join BSST_CON_COUNTRY c on c.CON_N_COUNTRY_ID=b.CON_N_COUNTRY_ID
left join BSST_CDE_COMPANY_DEPT d on a.CDE_N_PARENT_DEPT_ID=d.CDE_N_DEPT_ID 
join BSST_COM_COMPANY e on a.COM_N_COMPANY_ID=e.COM_N_COMPANY_ID;


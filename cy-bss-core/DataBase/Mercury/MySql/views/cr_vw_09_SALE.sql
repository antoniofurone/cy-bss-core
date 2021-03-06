drop view BSSV_SALE;

create view BSSV_SALE
(
ID,
COMPANY_ID,COMPANY_CODE,COMPANY_NAME,
PRODUCT_ID,PRODUCT_NAME,
CUSTOMER_ID,CUSTOMER_CODE,CUSTOMER_NAME,
PERSON_ID,PERSON_CODE,PERSON_FIRST_NAME,PERSON_SECOND_NAME,
COMPONENT_ID,COMPONENT_CODE,COMPONENT_NAME,
COMPONENT_TYPE_CODE,COMPONENT_TYPE_NAME,
QTY_UM_ID,QTY_UM_SIMBOL,QTY,
FREQUENCY_ID,FREQUENCY_NAME,
CURRENCY_ID,CURRENCY_CODE,CURRENCY_NAME,
PRICE, AMOUNT,VAT,VAT_AMOUNT,
DATE,DATE_START,DATE_END,DATE_CLOSE,
TACIT_RENEWAL,TYPE,UPDATE_DATE,
NEW_ID,OLD_ID,NOBILLED
)
as
select
	a.SAL_N_SALE_ID, 
	b.COM_N_COMPANY_ID,b.COM_S_CODE,b.COM_S_NAME,
	c.PRO_N_PRODUCT_ID,c.PRO_S_NAME,
	d.COM_N_COMPANY_ID,d.COM_S_CODE,d.COM_S_NAME,
	e.PER_N_PERSON_ID,e.PER_S_CODE, e.PER_S_FIRST_NAME, e.PER_S_SECOND_NAME,
	h.PRC_N_PRICE_COMPONENT_ID,h.PRC_S_CODE,h.PRC_S_NAME,i.PRT_S_CODE,i.PRT_S_NAME,
	f.MES_N_METRIC_SCALE_ID, f.MES_S_SIMBOL, a.SAL_N_QUANTITY,
	l.MES_N_METRIC_SCALE_ID, l.MES_S_SIMBOL,
	g.CUR_N_CURRENCY_ID,g.CUR_S_CODE,g.CUR_S_NAME,
	a.SAL_N_PRICE,a.SAL_N_AMOUNT,a.SAL_N_VAT,a.SAL_N_VAT_AMOUNT,
	a.SAL_D_DATE,a.SAL_D_DATE_START,a.SAL_D_DATE_END,a.SAL_D_DATE_CLOSE,
	a.SAL_C_TACIT_RENEWAL,a.SAL_C_TYPE,a.SAL_D_UPDATE_DATE,
	a.SAL_N_NEW_SALE_ID,a.SAL_N_OLD_SALE_ID,
	(select count(*) from BSST_BIR_BILLABLE_REVENUE x where BIR_C_BILLED='N' and x.SAL_N_SALE_ID=a.SAL_N_SALE_ID)
from BSST_SAL_SALE a
join BSST_COM_COMPANY b on b.COM_N_COMPANY_ID=a.MNC_N_COMPANY_ID
join BSST_PRO_PRODUCT c on c.PRO_N_PRODUCT_ID=a.PRO_N_PRODUCT_ID
left join BSST_COM_COMPANY d on d.COM_N_COMPANY_ID=a.COM_N_COMPANY_ID
left join BSST_PER_PERSON e on e.PER_N_PERSON_ID=a.PER_N_PERSON_ID
left join BSST_MES_METRIC_SCALE f on f.MES_N_METRIC_SCALE_ID=a.MES_N_METRIC_SCALE_ID
join BSST_CUR_CURRENCY g on g.CUR_N_CURRENCY_ID=a.CUR_N_CURRENCY_ID
join BSST_PRC_PRICE_COMPONENT h on h.PRC_N_PRICE_COMPONENT_ID=a.PRC_N_PRICE_COMPONENT_ID
join BSST_PRT_PRICE_TYPE i on i.PRT_N_PRICE_TYPE_ID=h.PRT_N_PRICE_TYPE_ID
left join BSST_MES_METRIC_SCALE l on l.MES_N_METRIC_SCALE_ID=a.SAL_N_FREQUENCY;



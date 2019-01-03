delete from BSST_PRC_PRICE_COMPONENT where PRC_S_CODE in ('RC_MONTLY');
delete from BSST_MES_METRIC_SCALE where MES_S_NAME='Month';
delete from BSST_MET_METRIC where MET_S_NAME='Month';

insert into BSST_MET_METRIC(MET_S_NAME) value ('Month');

insert BSST_MES_METRIC_SCALE(MET_N_METRIC_ID,MES_S_NAME,MES_S_SIMBOL,MES_N_SCALE)
value 
(
	(select MET_N_METRIC_ID from BSST_MET_METRIC where MET_S_NAME='Month'),
	'Month',
	'MM',
	1.0
);

INSERT INTO BSST_PRC_PRICE_COMPONENT(PRC_S_CODE,PRC_S_NAME,PRC_S_DESC,PRT_N_PRICE_TYPE_ID,PRI_N_PERIOD_METRIC_ID)
	VALUES (
	'RC_MONTLY',
	'Montly Recurring Charge',
	'Montly Recurring Charge',
	(select PRT_N_PRICE_TYPE_ID from BSST_PRT_PRICE_TYPE where PRT_S_CODE='RC'),
	(select MET_N_METRIC_ID from BSST_MET_METRIC where MET_S_NAME='Month')
);


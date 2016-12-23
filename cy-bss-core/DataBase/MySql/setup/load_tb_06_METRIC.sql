delete from BSST_MES_METRIC_SCALE;
delete from BSST_MET_METRIC;		

ALTER TABLE BSST_MET_METRIC AUTO_INCREMENT= 1;

insert into BSST_MET_METRIC(MET_S_NAME)
values ('Piece');

insert into BSST_MET_METRIC(MET_S_NAME)
values ('Meter');

insert into BSST_MET_METRIC(MET_S_NAME)
values ('Second');



ALTER TABLE BSST_MES_METRIC_SCALE AUTO_INCREMENT= 1;

insert into BSST_MES_METRIC_SCALE(MET_N_METRIC_ID,MES_S_NAME,MES_S_SIMBOL,MES_N_FACTOR)
values (
	(select MET_N_METRIC_ID from BSST_MET_METRIC where MET_S_NAME='Piece'),
	'Piece','Pz',1.0
);
insert into BSST_MES_METRIC_SCALE(MET_N_METRIC_ID,MES_S_NAME,MES_S_SIMBOL,MES_N_FACTOR)
values (
	(select MET_N_METRIC_ID from BSST_MET_METRIC where MET_S_NAME='Piece'),
	'Pack 6 Pz','6 Pz',6.0
);
insert into BSST_MES_METRIC_SCALE(MET_N_METRIC_ID,MES_S_NAME,MES_S_SIMBOL,MES_N_FACTOR)
values (
	(select MET_N_METRIC_ID from BSST_MET_METRIC where MET_S_NAME='Piece'),
	'Pack 12 Pz','12 Pz',12.0
);
insert into BSST_MES_METRIC_SCALE(MET_N_METRIC_ID,MES_S_NAME,MES_S_SIMBOL,MES_N_FACTOR)
values (
	(select MET_N_METRIC_ID from BSST_MET_METRIC where MET_S_NAME='Piece'),
	'Pack 32 Pz','32 Pz',32.0
);

insert into BSST_MES_METRIC_SCALE(MET_N_METRIC_ID,MES_S_NAME,MES_S_SIMBOL,MES_N_FACTOR)
values (
	(select MET_N_METRIC_ID from BSST_MET_METRIC where MET_S_NAME='Meter'),
	'Meter','Mt',1.0
);
insert into BSST_MES_METRIC_SCALE(MET_N_METRIC_ID,MES_S_NAME,MES_S_SIMBOL,MES_N_FACTOR)
values (
	(select MET_N_METRIC_ID from BSST_MET_METRIC where MET_S_NAME='Meter'),
	'Kilometer','Km',1000.0
);

insert into BSST_MES_METRIC_SCALE(MET_N_METRIC_ID,MES_S_NAME,MES_S_SIMBOL,MES_N_FACTOR)
values (
	(select MET_N_METRIC_ID from BSST_MET_METRIC where MET_S_NAME='Second'),
	'Second','Sec',1.0
);
insert into BSST_MES_METRIC_SCALE(MET_N_METRIC_ID,MES_S_NAME,MES_S_SIMBOL,MES_N_FACTOR)
values (
	(select MET_N_METRIC_ID from BSST_MET_METRIC where MET_S_NAME='Second'),
	'Minute','Mi',60.0
);
insert into BSST_MES_METRIC_SCALE(MET_N_METRIC_ID,MES_S_NAME,MES_S_SIMBOL,MES_N_FACTOR)
values (
	(select MET_N_METRIC_ID from BSST_MET_METRIC where MET_S_NAME='Second'),
	'Hour','Hr',3600.0
);

-- Currency
delete from BSST_CUR_CURRENCY;

ALTER TABLE BSST_CUR_CURRENCY AUTO_INCREMENT= 1;

insert into BSST_CUR_CURRENCY (CUR_S_CODE,CUR_S_NAME)
values ('EUR','Euro');


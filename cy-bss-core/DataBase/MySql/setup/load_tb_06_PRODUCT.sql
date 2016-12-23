delete from BSST_PCA_PRODUCT_CATEGORY;

ALTER TABLE BSST_PCA_PRODUCT_CATEGORY AUTO_INCREMENT= 1;

insert into BSST_PCA_PRODUCT_CATEGORY(PCA_S_NAME,PCA_N_VAT,PCA_S_DESC)
values ('Generic',22.0,'Generic');

delete from BSST_PTY_PRODUCT_TYPE;

ALTER TABLE BSST_PTY_PRODUCT_TYPE AUTO_INCREMENT= 1;

insert into BSST_PTY_PRODUCT_TYPE(PTY_S_NAME,PTY_S_DESC)
values ('Product','Product');
insert into BSST_PTY_PRODUCT_TYPE(PTY_S_NAME,PTY_S_DESC)
values ('Service','Service');
insert into BSST_PTY_PRODUCT_TYPE(PTY_S_NAME,PTY_S_DESC)
values ('Resource','Resource');

delete from BSST_PRT_REC_PRICE_TYPE;
ALTER TABLE BSST_PRT_REC_PRICE_TYPE AUTO_INCREMENT= 1;

insert into BSST_PRT_REC_PRICE_TYPE(PRT_S_NAME,PRT_S_DESC)
values ('Usage','Usage');
insert into BSST_PRT_REC_PRICE_TYPE(PRT_S_NAME,PRT_S_DESC)
values ('Montly Recurring Charge','Montly Recurring Charge');

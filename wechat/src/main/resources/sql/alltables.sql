-- Create table
create table WECHAT_REPAIR_CALCULATOR
(
  id        INTEGER not null,
  cartype      VARCHAR2(100),
  lictype     VARCHAR2(40),
  project       VARCHAR2(200),
  price  VARCHAR2(60),
  result       VARCHAR2(500)
)
tablespace CRM_DB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

-- Create/Recreate indexes
create index WECHAT_REPAIR_CALCULATOR_FK on WECHAT_REPAIR_CALCULATOR (id)
  tablespace CRM_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints
alter table WECHAT_REPAIR_CALCULATOR
  add constraint PK_WECHAT_REPAIR_CALCULATORR primary key (id)
  using index
  tablespace CRM_DB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

-- Create sequence
create sequence SEQ_WECHAT_REPAIR_CALCULATOR
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;


ALTER table jo_user add DEALER_CODE varchar2(50);
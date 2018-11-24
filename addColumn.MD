```
#JO_USER
alter table crm.JO_USER ADD  "MEMBER_NO" varchar2(40);
comment on column JO_USER.MEMBER_NO is '会员号';

#FORD_CLUB_MEMBER
alter table crm.FORD_CLUB_MEMBER ADD  "JOB_STATUS" number(1);
comment on column FORD_CLUB_MEMBER.JOB_STATUS is '批处理标识 0或者空 未处理 1 已处理';
alter table crm.FORD_CLUB_MEMBER ADD  "MEMBER_NO" varchar2(40);
comment on column FORD_CLUB_MEMBER.MEMBER_NO is '会员号';
alter table crm.FORD_CLUB_MEMBER ADD  "OPEN_ID" varchar2(100);
comment on column FORD_CLUB_MEMBER.OPEN_ID is '微信唯一标识';
alter table crm.FORD_CLUB_MEMBER ADD  "AUTH_WAY" varchar2(10);
comment on column FORD_CLUB_MEMBER.AUTH_WAY is '认证方式';
alter table crm.FORD_CLUB_MEMBER ADD  "SOURCE" varchar2(10);
comment on column FORD_CLUB_MEMBER.SOURCE is '认证来源';
alter table crm.FORD_CLUB_MEMBER ADD  "CHANNEL_CODE" varchar2(20);
comment on column FORD_CLUB_MEMBER.CHANNEL_CODE is '渠道代码';
alter table crm.FORD_CLUB_MEMBER ADD  "LICENSE_IMG_ID" number(11);
comment on column FORD_CLUB_MEMBER.LICENSE_IMG_ID is '行驶证编号';
alter table crm.FORD_CLUB_MEMBER ADD  "LICENSE_URL" varchar2(255);
comment on column FORD_CLUB_MEMBER.LICENSE_URL is '行驶证';
alter table crm.FORD_CLUB_MEMBER ADD  "LICENSE_JSON" varchar2(2000);
comment on column FORD_CLUB_MEMBER.LICENSE_JSON is '行驶证扫码结果';

#car_owner_authen_status
alter table crm.car_owner_authen_status ADD  "TIMES" number(1);
comment on column car_owner_authen_status.TIMES is '审核次数';
alter table crm.car_owner_authen_status ADD  "AUTH_RESULT" number(1);
comment on column car_owner_authen_status.AUTH_RESULT is '审核结果';
alter table crm.car_owner_authen_status ADD  "AUTH_WAY" varchar(10);
comment on column car_owner_authen_status.AUTH_WAY is '审核方式';
alter table crm.car_owner_authen_status ADD  "AUTH_DATE" DATE;
comment on column car_owner_authen_status.AUTH_DATE is '审核时间';



```
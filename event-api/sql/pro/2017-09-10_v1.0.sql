/*以下是创建表*/

-- 代金券
CREATE TABLE WE_COUPON
(
  ID              NUMBER(11) NOT NULL
    CONSTRAINT WE_COUPON_PK
    PRIMARY KEY,
  PROJECT_CODE    VARCHAR2(150),
  PROJECT_NAME    VARCHAR2(150),
  COUPON_NO       VARCHAR2(255),
  AMOUNT          NUMBER(11),
  LOWEST_AMOUNT   NUMBER(11),
  DEALER_CODE     VARCHAR2(150),
  VIN             VARCHAR2(40),
  OPEN_ID         VARCHAR2(40),
  MEMBER_NO       VARCHAR2(40),
  MOBILE          VARCHAR2(255),
  STATUS          NUMBER(11),
  BATCH_STATUS    NUMBER(11),
  START_TIME      VARCHAR2(150),
  EXPIRED_TIME    VARCHAR2(150),
  WISHING         VARCHAR2(255),
  CREATED_BY_ID   NUMBER(11),
  CREATED_DATE    DATE      DEFAULT sysdate,
  UPDATED_BY_ID   NUMBER(11),
  UPDATED_DATE    DATE      DEFAULT sysdate,
  OPT_COUNTER     NUMBER(11),
  DELETED_DATE    DATE,
  MARK_FOR_DELETE NUMBER(1) DEFAULT 0,
  DATE_NO         VARCHAR2(40),
  AUTH_TIME       DATE,
  BATCH_NO        VARCHAR2(150),
  RECEIVED_TIME   DATE
)
/
COMMENT ON COLUMN WE_COUPON.ID IS '主键'
/
COMMENT ON COLUMN WE_COUPON.PROJECT_CODE IS '活动代码'
/
COMMENT ON COLUMN WE_COUPON.PROJECT_NAME IS '活动名称'
/
COMMENT ON COLUMN WE_COUPON.COUPON_NO IS '代金券编号'
/
COMMENT ON COLUMN WE_COUPON.AMOUNT IS '金额'
/
COMMENT ON COLUMN WE_COUPON.LOWEST_AMOUNT IS '门槛金额'
/
COMMENT ON COLUMN WE_COUPON.DEALER_CODE IS '目标（责任）经销商服务代码'
/
COMMENT ON COLUMN WE_COUPON.VIN IS '绑定VIN'
/
COMMENT ON COLUMN WE_COUPON.OPEN_ID IS '绑定OPENID'
/
COMMENT ON COLUMN WE_COUPON.MEMBER_NO IS '会员编号'
/
COMMENT ON COLUMN WE_COUPON.MOBILE IS '会员手机号'
/
COMMENT ON COLUMN WE_COUPON.STATUS IS '状态  0 未领用  1 已领用'
/
COMMENT ON COLUMN WE_COUPON.BATCH_STATUS IS '批处理状态  0 未处理  1 已处理'
/
COMMENT ON COLUMN WE_COUPON.START_TIME IS '代金券开始日期 yyyy-MM-dd'
/
COMMENT ON COLUMN WE_COUPON.EXPIRED_TIME IS '代金券失效日期 yyyy-MM-dd'
/
COMMENT ON COLUMN WE_COUPON.WISHING IS '祝福语'
/
COMMENT ON COLUMN WE_COUPON.CREATED_BY_ID IS '创建人ID'
/
COMMENT ON COLUMN WE_COUPON.CREATED_DATE IS '创建时间'
/
COMMENT ON COLUMN WE_COUPON.UPDATED_BY_ID IS '更新人ID'
/
COMMENT ON COLUMN WE_COUPON.UPDATED_DATE IS '更新时间'
/
COMMENT ON COLUMN WE_COUPON.OPT_COUNTER IS '记录操作次，乐观锁'
/
COMMENT ON COLUMN WE_COUPON.DELETED_DATE IS '删除时间'
/
COMMENT ON COLUMN WE_COUPON.MARK_FOR_DELETE IS '是否标记删除 0 未删除   1 表示删除'
/


-- 下发DMS
CREATE TABLE WE_COUPON_SEND_DMS
(
  ID               NUMBER(11) NOT NULL
    CONSTRAINT WE_COUPON_SEND_DMS
    PRIMARY KEY,
  ORDER_ID         NUMBER(11),
  SEQ_NO           VARCHAR2(40),
  NAME             VARCHAR2(40),
  VIN              VARCHAR2(40),
  CAMPAIGN_CODE    VARCHAR2(40),
  AMOUNT           VARCHAR2(40),
  USE_AMOUNT       VARCHAR2(40),
  VALID_BEGIN_DATE VARCHAR2(40),
  VALID_END_DATE   VARCHAR2(40),
  TARGET_DEALER    VARCHAR2(40),
  USE_DEALER       VARCHAR2(40),
  LOWEST_AMOUNT    VARCHAR2(40),
  LIMIT_DEDUCT     VARCHAR2(40),
  SEND_DMS_STATUS  VARCHAR2(40),
  SEND_COUNT       VARCHAR2(40),
  DMS_RESULT       VARCHAR2(40),
  CANCEL           VARCHAR2(40),
  SEND_HISTORY     VARCHAR2(2000),
  SEND_SMS         VARCHAR2(40),
  CUSTOMER_MOBILE  VARCHAR2(40),
  PERIOD_TYPE      VARCHAR2(40),
  COPIES           NUMBER(11),
  TAG              VARCHAR2(40),
  CREATED_BY_ID    NUMBER(11),
  CREATED_DATE     DATE      DEFAULT sysdate,
  UPDATED_BY_ID    NUMBER(11),
  UPDATED_DATE     DATE      DEFAULT sysdate,
  OPT_COUNTER      NUMBER(11),
  DELETED_DATE     DATE,
  MARK_FOR_DELETE  NUMBER(1) DEFAULT 0,
  BATCH_NO         VARCHAR2(150)
)
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.ID IS '主键'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.ORDER_ID IS '关联购买订单'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.SEQ_NO IS '唯一标示'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.NAME IS '电子账户名称'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.VIN IS 'VIN码'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.CAMPAIGN_CODE IS '活动编号'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.AMOUNT IS '总金额'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.USE_AMOUNT IS '已使用金额 默认0'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.VALID_BEGIN_DATE IS '开始生效日期'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.VALID_END_DATE IS '结束生效日期'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.TARGET_DEALER IS '目标（责任）经销商服务代码'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.USE_DEALER IS '使用经销商服务代码'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.LOWEST_AMOUNT IS ' 门槛金额'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.LIMIT_DEDUCT IS '抵扣限额'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.SEND_DMS_STATUS IS '是否发送DMS'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.SEND_COUNT IS '发送DMS的次数，超过3次就不做查询发送'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.DMS_RESULT IS '发送DMS的响应结果'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.CANCEL IS '是否取消'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.SEND_HISTORY IS '发送历史记录'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.SEND_SMS IS '短信是否已发送'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.CUSTOMER_MOBILE IS '认证车主手机号'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.PERIOD_TYPE IS '活动类型'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.COPIES IS '份数'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.TAG IS '标签区分 微商城跟618活动'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.CREATED_BY_ID IS '创建人ID'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.CREATED_DATE IS '创建时间'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.UPDATED_BY_ID IS '更新人ID'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.UPDATED_DATE IS '更新时间'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.OPT_COUNTER IS '记录操作次，乐观锁'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.DELETED_DATE IS '删除时间'
/
COMMENT ON COLUMN WE_COUPON_SEND_DMS.MARK_FOR_DELETE IS '是否标记删除 0 未删除   1 表示删除'
/


-- 总代金券活动配置
CREATE TABLE WE_EVENT
(
  ID              NUMBER(11) NOT NULL
    CONSTRAINT WE_EVENT
    PRIMARY KEY,
  PROJECT_CODE    VARCHAR2(40),
  PROJECT_NAME    VARCHAR2(40),
  START_TIME      VARCHAR2(150),
  END_TIME        VARCHAR2(150),
  WISHING         VARCHAR2(40),
  REMARK          VARCHAR2(150),
  CREATED_BY_ID   NUMBER(11),
  CREATED_DATE    DATE      DEFAULT sysdate,
  UPDATED_BY_ID   NUMBER(11),
  UPDATED_DATE    DATE      DEFAULT sysdate,
  OPT_COUNTER     NUMBER(11),
  DELETED_DATE    DATE,
  MARK_FOR_DELETE NUMBER(1) DEFAULT 0,
  EVENT_URL       VARCHAR2(150)
)
/
COMMENT ON COLUMN WE_EVENT.ID IS '主键'
/
COMMENT ON COLUMN WE_EVENT.PROJECT_CODE IS '活动代码'
/
COMMENT ON COLUMN WE_EVENT.PROJECT_NAME IS '活动名称'
/
COMMENT ON COLUMN WE_EVENT.START_TIME IS '活动开始时间'
/
COMMENT ON COLUMN WE_EVENT.END_TIME IS '活动结束时间'
/
COMMENT ON COLUMN WE_EVENT.WISHING IS '祝福语'
/
COMMENT ON COLUMN WE_EVENT.REMARK IS '备注'
/
COMMENT ON COLUMN WE_EVENT.CREATED_BY_ID IS '创建人ID'
/
COMMENT ON COLUMN WE_EVENT.CREATED_DATE IS '创建时间'
/
COMMENT ON COLUMN WE_EVENT.UPDATED_BY_ID IS '更新人ID'
/
COMMENT ON COLUMN WE_EVENT.UPDATED_DATE IS '更新时间'
/
COMMENT ON COLUMN WE_EVENT.OPT_COUNTER IS '记录操作次，乐观锁'
/
COMMENT ON COLUMN WE_EVENT.DELETED_DATE IS '删除时间'
/
COMMENT ON COLUMN WE_EVENT.MARK_FOR_DELETE IS '是否标记删除 0 未删除   1 表示删除'
/

-- 每日代金券活动配置
CREATE TABLE WE_EVENT_DETAIL
(
  ID              NUMBER(11) NOT NULL
    CONSTRAINT WE_EVENT_DETAIL
    PRIMARY KEY,
  DATE_NO         VARCHAR2(150),
  PROJECT_CODE    VARCHAR2(150),
  PROJECT_NAME    VARCHAR2(150),
  MAX             NUMBER(11),
  MIN             NUMBER(11),
  TOTAL_AMOUNT    NUMBER(11),
  COUNT           NUMBER(11),
  LOWEST_AMOUNT   NUMBER(11),
  START_TIME      DATE,
  END_TIME        DATE,
  VALID_TIMES     NUMBER(11),
  WISHING         VARCHAR2(40),
  REMARK          VARCHAR2(255),
  CREATED_BY_ID   NUMBER(11),
  CREATED_DATE    DATE      DEFAULT sysdate,
  UPDATED_BY_ID   NUMBER(11),
  UPDATED_DATE    DATE      DEFAULT sysdate,
  OPT_COUNTER     NUMBER(11),
  DELETED_DATE    DATE,
  MARK_FOR_DELETE NUMBER(1) DEFAULT 0
)
/
COMMENT ON COLUMN WE_EVENT_DETAIL.ID IS '主键'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.DATE_NO IS '时间批次  yyyyMMdd'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.PROJECT_CODE IS '活动代码'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.PROJECT_NAME IS '活动名称'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.MAX IS '最大额度'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.MIN IS '最小额度'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.TOTAL_AMOUNT IS '总金额'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.COUNT IS '份数'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.LOWEST_AMOUNT IS '门槛金额'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.START_TIME IS '每日开始时间 HH-mm-ss'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.END_TIME IS '每日结束时间 HH-mm-ss'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.VALID_TIMES IS '代金券有效天数'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.WISHING IS '祝福语'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.REMARK IS '备注'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.CREATED_BY_ID IS '创建人ID'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.CREATED_DATE IS '创建时间'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.UPDATED_BY_ID IS '更新人ID'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.UPDATED_DATE IS '更新时间'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.OPT_COUNTER IS '记录操作次，乐观锁'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.DELETED_DATE IS '删除时间'
/
COMMENT ON COLUMN WE_EVENT_DETAIL.MARK_FOR_DELETE IS '是否标记删除 0 未删除   1 表示删除'
/



-- 代金券页面访问日志
CREATE TABLE WE_EVENT_OPERATION_RECORD
(
  ID                  NUMBER(11) NOT NULL
    CONSTRAINT WE_EVENT_OPERATION_RECORD
    PRIMARY KEY,
  DATE_NO             VARCHAR2(150),
  PROJECT_CODE        VARCHAR2(150),
  PROJECT_NAME        VARCHAR2(150),
  OPEN_ID             VARCHAR2(150),
  OPERATION_TYPE      NUMBER(11),
  LAST_OPERATION_TIME DATE,
  CREATED_BY_ID       NUMBER(11),
  CREATED_DATE        DATE      DEFAULT sysdate,
  UPDATED_BY_ID       NUMBER(11),
  UPDATED_DATE        DATE      DEFAULT sysdate,
  OPT_COUNTER         NUMBER(11),
  DELETED_DATE        DATE,
  MARK_FOR_DELETE     NUMBER(1) DEFAULT 0
)
/
COMMENT ON COLUMN WE_EVENT_OPERATION_RECORD.ID IS '主键'
/
COMMENT ON COLUMN WE_EVENT_OPERATION_RECORD.DATE_NO IS '时间批次  yyyyMMdd'
/
COMMENT ON COLUMN WE_EVENT_OPERATION_RECORD.PROJECT_CODE IS '活动代码'
/
COMMENT ON COLUMN WE_EVENT_OPERATION_RECORD.PROJECT_NAME IS '活动名称'
/
COMMENT ON COLUMN WE_EVENT_OPERATION_RECORD.OPEN_ID IS '操作类型  0 访问页面  1 点击抽奖'
/
COMMENT ON COLUMN WE_EVENT_OPERATION_RECORD.LAST_OPERATION_TIME IS '最后记录时间'
/
COMMENT ON COLUMN WE_EVENT_OPERATION_RECORD.CREATED_BY_ID IS '创建人ID'
/
COMMENT ON COLUMN WE_EVENT_OPERATION_RECORD.CREATED_DATE IS '创建时间'
/
COMMENT ON COLUMN WE_EVENT_OPERATION_RECORD.UPDATED_BY_ID IS '更新人ID'
/
COMMENT ON COLUMN WE_EVENT_OPERATION_RECORD.UPDATED_DATE IS '更新时间'
/
COMMENT ON COLUMN WE_EVENT_OPERATION_RECORD.OPT_COUNTER IS '记录操作次，乐观锁'
/
COMMENT ON COLUMN WE_EVENT_OPERATION_RECORD.DELETED_DATE IS '删除时间'
/
COMMENT ON COLUMN WE_EVENT_OPERATION_RECORD.MARK_FOR_DELETE IS '是否标记删除 0 未删除   1 表示删除'
/

-- 认证激励统计
CREATE TABLE WE_EVENT_STATISTICS
(
  ID                      NUMBER(11) NOT NULL
    CONSTRAINT WE_EVENT_STATISTICS
    PRIMARY KEY,
  BATCH_NO                VARCHAR2(150),
  DATE_NO                 VARCHAR2(150),
  PROJECT_CODE            VARCHAR2(150),
  PROJECT_NAME            VARCHAR2(150),
  VISIT_COUNT             NUMBER(11),
  DRAW_COUNT              NUMBER(11),
  SMS_SEND_COUNT          NUMBER(11),
  SMS_FAILED_COUNT        NUMBER(11),
  AUTHEN_COUNT            NUMBER(11),
  AUTHEN_COUNT_WITH_EVENT NUMBER(11),
  WINNER_COUNT            NUMBER(11),
  ORIGINAL_WINNER_COUNT   NUMBER(11),
  CREATED_BY_ID           NUMBER(11),
  CREATED_DATE            DATE      DEFAULT sysdate,
  UPDATED_BY_ID           NUMBER(11),
  UPDATED_DATE            DATE      DEFAULT sysdate,
  OPT_COUNTER             NUMBER(11),
  DELETED_DATE            DATE,
  MARK_FOR_DELETE         NUMBER(1) DEFAULT 0
)
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.ID IS '主键'
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.BATCH_NO IS '时间批次  yyyyMMdd'
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.DATE_NO IS '时间'
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.PROJECT_CODE IS '活动代码'
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.PROJECT_NAME IS '活动名称'
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.VISIT_COUNT IS '每日访问人数'
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.DRAW_COUNT IS '每次人抽奖人数'
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.SMS_SEND_COUNT IS '短信发送数'
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.SMS_FAILED_COUNT IS '短信发送失败数量'
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.AUTHEN_COUNT IS '每日认证人数'
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.AUTHEN_COUNT_WITH_EVENT IS '因活动认证人数'
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.WINNER_COUNT IS '抽中人数'
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.ORIGINAL_WINNER_COUNT IS '老认证用户获券人数'
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.CREATED_BY_ID IS '创建人ID'
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.CREATED_DATE IS '创建时间'
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.UPDATED_BY_ID IS '更新人ID'
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.UPDATED_DATE IS '更新时间'
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.OPT_COUNTER IS '记录操作次，乐观锁'
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.DELETED_DATE IS '删除时间'
/
COMMENT ON COLUMN WE_EVENT_STATISTICS.MARK_FOR_DELETE IS '是否标记删除 0 未删除   1 表示删除'
/

-- 短信发送记录
CREATE TABLE WE_MESSAGE_SEND
(
  ID              NUMBER(11) NOT NULL
    CONSTRAINT WE_MESSAGE_SEND
    PRIMARY KEY,
  DATE_NO         VARCHAR2(40),
  PROJECT_CODE    VARCHAR2(40),
  PROJECT_NAME    VARCHAR2(40),
  SEND_CHANNEL    VARCHAR2(40),
  VIN             VARCHAR2(40),
  OPEN_ID         VARCHAR2(40),
  MEMBER_NO       VARCHAR2(40),
  MOBILE          VARCHAR2(40),
  STATUS_CODE     VARCHAR2(40),
  SEND_RESULT     VARCHAR2(40),
  ERROR_MESSAGE   VARCHAR2(40),
  SEND_TIME       DATE,
  CONTENT         VARCHAR2(1000),
  CREATED_BY_ID   NUMBER(11),
  CREATED_DATE    DATE      DEFAULT sysdate,
  UPDATED_BY_ID   NUMBER(11),
  UPDATED_DATE    DATE      DEFAULT sysdate,
  OPT_COUNTER     NUMBER(11),
  DELETED_DATE    DATE,
  MARK_FOR_DELETE NUMBER(1) DEFAULT 0,
  SEND_COUNT      NUMBER(11)
)
/
COMMENT ON COLUMN WE_MESSAGE_SEND.ID IS '主键'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.DATE_NO IS '时间'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.PROJECT_CODE IS '活动代码'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.PROJECT_NAME IS '活动名称'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.SEND_CHANNEL IS '触发方式   如：跑批  个人中心  车主认证'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.VIN IS '绑定VIN'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.OPEN_ID IS '绑定OPENID'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.MEMBER_NO IS '会员编号'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.MOBILE IS '会员手机号'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.STATUS_CODE IS '发送状态'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.SEND_RESULT IS '发送结果  成功  失败'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.ERROR_MESSAGE IS '发送结果提示'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.SEND_TIME IS '发送时间'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.CONTENT IS '短信内容'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.CREATED_BY_ID IS '创建人ID'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.CREATED_DATE IS '创建时间'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.UPDATED_BY_ID IS '更新人ID'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.UPDATED_DATE IS '更新时间'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.OPT_COUNTER IS '记录操作次，乐观锁'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.DELETED_DATE IS '删除时间'
/
COMMENT ON COLUMN WE_MESSAGE_SEND.MARK_FOR_DELETE IS '是否标记删除 0 未删除   1 表示删除'
/


/*以下是创建seq*/

CREATE SEQUENCE SEQ_WE_COUPON
  MINVALUE 1
  MAXVALUE 999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CYCLE
  CACHE 500;
commit;

CREATE SEQUENCE SEQ_WE_COUPON_SEND_DMS
  MINVALUE 1
  MAXVALUE 999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CYCLE
  CACHE 500;
commit;


CREATE SEQUENCE SEQ_WE_EVENT
  MINVALUE 1
  MAXVALUE 999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CYCLE
  CACHE 500;
commit;


CREATE SEQUENCE SEQ_WE_EVENT_DETAIL
  MINVALUE 1
  MAXVALUE 999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CYCLE
  CACHE 500;
commit;


CREATE SEQUENCE SEQ_WE_EVENT_OPERATION_RECORD
  MINVALUE 1
  MAXVALUE 999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CYCLE
  CACHE 500;
commit;


CREATE SEQUENCE SEQ_WE_EVENT_STATISTICS
  MINVALUE 1
  MAXVALUE 999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CYCLE
  CACHE 500;
commit;


CREATE SEQUENCE SEQ_WE_MESSAGE_SEND
  MINVALUE 1
  MAXVALUE 999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CYCLE
  CACHE 500;
commit;

/*以下是表 WE_EVENT 插入总活动数据*/

INSERT INTO CRM.WE_EVENT (ID, PROJECT_CODE, PROJECT_NAME, START_TIME, END_TIME, WISHING, REMARK, CREATED_BY_ID, CREATED_DATE, UPDATED_BY_ID, UPDATED_DATE, OPT_COUNTER, DELETED_DATE, MARK_FOR_DELETE, EVENT_URL)
VALUES (CRM.SEQ_WE_EVENT.nextval, 'GFB172/2017', '送豪礼人人有礼', '2017-09-12', '2017-12-09', '送礼啦，人人都有大奖！', null, null, TO_DATE('2017-09-10 18:48:09', 'YYYY-MM-DD HH24:MI:SS'), null, TO_DATE('2017-09-10 18:48:09', 'YYYY-MM-DD HH24:MI:SS'), null, null, 0, 'http://point.xiqing.info/event/static/init.html?openId=:openId');

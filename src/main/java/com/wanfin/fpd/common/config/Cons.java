package com.wanfin.fpd.common.config;




/**
 * 常量类
 * 
 * @author zzm
 * 
 */
public class Cons {
	public static class Ips{
		/**微服务产品新增*/
		public static final String WF_PRODUCT_SAVE = Global.getApiConfig("api.ips.wf")+Global.getApiConfig("api.ips.wf.product.psave");
		/**微服务产品更新 */
		public static final String WF_PRODUCT_UPDATE = Global.getApiConfig("api.ips.wf")+Global.getApiConfig("api.ips.wf.product.pupdateStatus");
		
		/**风控征信接口地址*/
		public static final String IP_FK = Global.getApiConfig("api.ips.fk");
		/**调用风控征信接口需要的token */
		public static final String IP_FK_TOKEN = Global.getApiConfig("api.ips.fk.token");
		
		
		/**规则引擎接口地址*/
		public static final String IP_KIE = Global.getApiConfig("api.ips.kie");
		/** 调用规则引擎接口需要的token */
		public static final String IP_KIE_TOKEN = Global.getApiConfig("api.ips.kie.token");
		/** 生成还款计划接口  */
		//public static final String IP_KIE_CREATEPLANS = IP_KIE+"/createPlans.html";
		public static final String IP_KIE_CREATEPLANS = IP_KIE+"/droolsRules/plansRules.html";
		/** 个人授信评分接口 */
		public static final String IP_KIE_CALCULATEPERSONALCREDIT = IP_KIE+"/risk/calculatePersonalCredit.html";

		
		
		/**W端接口地址*/
		public static final String IP_WD = Global.getApiConfig("api.ips.wd");
		/** W端新增借款接口（转理财） */
		public static final String IP_W_ADDLOANORDER = IP_WD+"/index/test/addLoanOrder.html";
		/** W端查询借款类型接口（转理财） */
		public static final String IP_W_GETCATID = IP_WD+"/index/test/getCatId.html";
		/** 微服务文件上传接口 */
		public static final String IP_WFW_UPLOAND_PATH = Global.getApiConfig("wfw.api.http.upload");
		/** 微服务查询附件接口 */
		public static final String IP_WFW_SELECT_PATH = Global.getApiConfig("wfw.api.http.select");
		/** 微服务文件服务器 */
		public static final String IP_WFW_FDFS_PATH = Global.getApiConfig("wfw.api.http.fdfs");
		/** 微服务发送站内信 */
		public static final String IP_WLETTER_SEND_PATH = Global.getApiConfig("wletter.api.http.send");
		/** 微服务发送营销短信 */
		public static final String IP_WMARKET_SEND_PATH = Global.getApiConfig("wmarket.api.http.send");

	}
	
	public static class SysDF{
		/**超级管理员-用户ID*/
		public static final String DF_SUPER_USER = Global.getIdsConfig("ids.super.user");
		/**超级管理员-角色ID*/
		public static final String DF_SUPER_ROLE = Global.getIdsConfig("ids.super.role");
		/**超级管理员-公司ID*/
		public static final String DF_SUPER_COMPANY = Global.getIdsConfig("ids.super.company");
		/**超级管理员-综合部ID*/
		public static final String DF_SUPER_OFFICE = Global.getIdsConfig("ids.super.office");
		
		
		/**小贷租户角色*/
		public static final String DF_ADMIN_ROLE_XD = Global.getIdsConfig("ids.zadmin.role.xd");
		/**担保租户角色*/
		public static final String DF_ADMIN_ROLE_DB = Global.getIdsConfig("ids.zadmin.role.db");
		
		
		/**区域*/
		public static final String DF_AREA = Global.getIdsConfig("ids.default.area");
		
		
		/**接口默认密码=登录名+W_PSW_PFIX*/
		public static final String W_PSW_PFIX = Global.getApiConfig("api.password.pfix");
		
		/**机构类型-公司*/
		public static final String DF_SUPER_OFFICE_TYPEORG = "1";
		/**机构类型-部门*/
		public static final String DF_SUPER_OFFICE_TYPEDEPART = "2";
		/**机构等级-公司*/
		public static final String DF_SUPER_OFFICE_GRADEORG = "1";
		/**机构等级-部门*/
		public static final String DF_SUPER_OFFICE_GRADEDEPART = "2";
		/**用户类型-管理类型*/
		public static final String DF_ADMIN_USERTYPE_MANAGER = "1";
		/** 系统默认初始化机构 -部门*/
		public static final String SYS_OFFICE_COMMON = "sys_office_common";
	}

	/*固定模板*/
	public static class FormTplId{
		/**W端产品父模板ID */
		public static final String W_PRODUCT_FORM_ID = Global.getIdsConfig("ids.formtpl.wproduct");
		/*W端客户-个人客户父模板ID*/
		public static final String W_TEMPLOYEE_FORM_ID = Global.getIdsConfig("ids.formtpl.wemployee");
		/*W端客户-企业客户父模板ID*/
		public static final String W_TCOMPANY_FORM_ID = Global.getIdsConfig("ids.formtpl.wcompany");
		/*W端客户-业务申请父模板ID*/
		public static final String W_LOANCONTRACT_FORM_ID = Global.getIdsConfig("ids.formtpl.wloancontract");

		/*B端-个人客户父模板ID*/
		public static final String B_TEMPLOYEE_FORM_ID = Global.getIdsConfig("ids.formtpl.bemployee");
		/*B端-企业客户父模板ID*/
		public static final String B_TCOMPANY_FORM_ID = Global.getIdsConfig("ids.formtpl.bcompany");
		
		/*B端-质押模板*/
		public static final String B_CONTRACT_PLEDGE_ID = Global.getIdsConfig("ids.formtpl.bcontractpledge");
		/*B端-抵押模板*/
		public static final String B_CONTRACT_MORTGAGE_ID = Global.getIdsConfig("ids.formtpl.bcontractmortgage");
		/*B端-保证信息模板*/
		public static final String B_CONTRACT_GUARANTEE_ID = Global.getIdsConfig("ids.formtpl.bcontractguarantee");
		/*B端-业务咨询模板*/
		public static final String B_CONTRACT_ADVISORY_ID = Global.getIdsConfig("ids.formtpl.badvisory");

		/*WZH-产品固定模板*/
		public static final String WZH_PRODUCT_FORM_ID = Global.getIdsConfig("ids.formtpl.product");
		/*WZH-贷款业务申请模板*/
		public static final String WZH_XD_LOANCONTRACT_FORM_ID = Global.getIdsConfig("ids.formtpl.xd.loancontract");
		/*WZH-担保业务申请模板*/
		public static final String WZH_DB_LOANCONTRACT_FORM_ID = Global.getIdsConfig("ids.formtpl.db.loancontract");
		
	}
	

	/*提示消息*/
	public static class Msg{
		/** 新建用户 */
		public static final String MSG_ADD_USER = Global.getMessageConfig("message.smsregister");
		/** 修改密码 */
		public static final String MSG_UPDATE_PWS = Global.getMessageConfig("message.smsupdatekey");
		/** 提前还款提醒 */
		public static final String MSG_ALERT_AHEAD = Global.getMessageConfig("message.repaywarn");
		/** 当天还款提醒 */
		public static final String MSG_ALERT_NOWDATE = Global.getMessageConfig("message.repaywarn1");
		/** 放款提醒 */
		public static final String MSG_LEND_WARN = Global.getMessageConfig("message.lendwarn");
		/** 逾期提醒 */
		public static final String MSG_OVER_WARN = Global.getMessageConfig("message.overwarn");
		/** 律师函 */
		public static final String MSG_LETTER= Global.getMessageConfig("message.letter");
		/** 仲裁 */
		public static final String MSG_ZHONG_CAI = Global.getMessageConfig("message.zhongcai");
		
	}
	
	/**
	 * Sftp配置信息
	 * @author qiao
	 *
	 */
	public static class SftpPayeco {
		public static final String IP = Global.getApiConfig("wish.payeco.sftp.ip");
		public static final int PORT = Integer.parseInt(Global.getApiConfig("wish.payeco.sftp.port"));
		public static final String ACCOUNT = Global.getApiConfig("wish.payeco.sftp.account");
		public static final String PASSWORD = Global.getApiConfig("wish.payeco.sftp.password");
	}
	
	
	/**
	 * 贝通参数信息
	 * @author qiao
	 *
	 */
	public static class BetToneParam {
		public static final String SECRET_KEY = Global.getApiConfig("beitong.secretKey");
		public static final String GET_SIGN = Global.getApiConfig("beitong.getSign");
		public static final String RELEASE_LOAN = Global.getApiConfig("beitong.releaseLoan");
		public static final String REPAYMENT = Global.getApiConfig("beitong.repayment");
		public static final String QUERY_LOAN_INFO = Global.getApiConfig("beitong.queryLoanInfo");
		public static final String PROJECT_URL =  Global.getApiConfig("project.url");
	}

	public static class EmailParam{
		public static final String SEND_EMAIL = Global.getApiConfig("send.email");
		public static final String TARGET_EMAIL = Global.getApiConfig("target.email");
		public static final String EMAIL_HOST_TYPE = Global.getApiConfig("email.host.type");
		public static final String SEND_EMAIL_PASSWORD = Global.getApiConfig("send.email.password");
	}
	

	/**
	 * admin认证id
	 * @author lzj
	 *
	 */
	public static class adminId{
		public static final String ADMIN_AUTH_ID = Global.getIdsConfig("ids.admin.auth.id");
	 }
	
	
	/**
	 * wish 易联服务费比例
	 * @author lzj
	 *
	 */
	public static class wishFeeRate{
		public static final String 	WISH_FEE_RATE = Global.getIdsConfig("ids.wish.fee.rate");
	 }
	
	/**
	 * wish 创世月息比例
	 * @author lzj
	 *
	 */
	public static class wishMonthRate{
		public static final String 	WISH_MONTH_RATE = Global.getIdsConfig("ids.wish.month.rate");
	 }
	
	/**
	 * wish 贝通计算利息比例
	 * @author lzj
	 *
	 */
	public static class wishInterestRate{
		public static final String 	WISH_INTEREST_RATE = Global.getIdsConfig("ids.wish.interest.rate");
	 }
	
	
	/**
	 * wish相关url
	 * @author lzj
	 *
	 */
	public static class wishUrl{
		public static final String 	WISH_URL_IP = Global.getApiConfig("wish.url.ip");
		
		public static final String 	WISH_R_USERNAME = Global.getApiConfig("wish.url.username");
		public static final String 	WISH_R_PASSWORD = Global.getApiConfig("wish.url.password");
		public static final String 	WISH_R_DBANME = Global.getApiConfig("wish.url.dbname");
		public static final String 	WISH_R_HOST = Global.getApiConfig("wish.url.host");
		public static final String 	WISH_R_PORT = Global.getApiConfig("wish.url.port");
	 }
	
	/**
	 * 站内消息通知模板
	 * @author lzj
	 *
	 */
	public static class letterDForm{
		//W端新业务提醒机构
		public static final String LETTER_NEWLOAN = "0";
		public static final String LETTER_NEWLOAN_TITLE = Global.getLetterConfig("letter.newLoan.title");
		public static final String LETTER_NEWLOAN_CONTENT = Global.getLetterConfig("letter.newLoan.content");
		//理财满标通知卖家
		public static final String LETTER_FINACEFULL = "1";
		public static final String LETTER_FINACEFULL_TITLE = Global.getLetterConfig("letter.financeFull.title");
		public static final String LETTER_FINACEFULL_CONTENT = Global.getLetterConfig("letter.financeFull.content");
		//理财流标通知卖家
		public static final String LETTER_FAILINVEST="2";
		public static final String LETTER_FAILINVEST_TITILE = Global.getLetterConfig("letter.failInvest.title");
		public static final String LETTER_FAILINVEST_CONTENT = Global.getLetterConfig("letter.failInvest.content");
		//理财回款通知卖家
		public static final String LETTER_FINACEREAPY="3";
		public static final String LETTER_FINACEREAPY7_TITILE7= Global.getLetterConfig("letter.financeRepay7.title7");
		public static final String LETTER_FINACEREAPY7_CONTENT7 = Global.getLetterConfig("letter.financeRepay7.content7");
		
		public static final String LETTER_FINACEREAPY3_TITILE3= Global.getLetterConfig("letter.financeRepay3.title3");
		public static final String LETTER_FINACEREAPY3_CONTENT3 = Global.getLetterConfig("letter.financeRepay3.content3");
		
		public static final String LETTER_FINACEREAPY_TITILE= Global.getLetterConfig("letter.financeRepay.title");
		public static final String LETTER_FINACEREAPY_CONTENT = Global.getLetterConfig("letter.financeRepay.content");
		
		//#借款B端审核不通过
		public static final String LETTER_LOANAUDITFAIL="4";
		public static final String LETTER_LOANAUDITFAIL_TITILE= Global.getLetterConfig("letter.loanAuditFail.title");
		public static final String LETTER_LOANAUDITFAIL_CONTENT = Global.getLetterConfig("letter.loanAuditFail.content");
		
		//#借款B端审核通过准备签订合同
		public static final String LETTER_LOANAUDITSUCCESS="5";
		public static final String LETTER_LOANAUDITSUCCESS_TITILE= Global.getLetterConfig("letter.loanAuditSuccess.title");
		public static final String LETTER_LOANAUDITSUCCESS_CONTENT = Global.getLetterConfig("letter.loanAuditSuccess.content");
		
		//#借款B端放款成功
		public static final String LETTER_MAKELOANSUCCESS="6";
		public static final String LETTER_MAKELOANSUCCESS_TITILE= Global.getLetterConfig("letter.makeLoanSuccess.title");
		public static final String LETTER_MAKELOANSUCCESS_CONTENT = Global.getLetterConfig("letter.makeLoanSuccess.content");
		
		
		//#借款还款提醒
		public static final String LETTER_LOANREPAY="7";
		public static final String LETTER_LOANREPAY7_TITILE7= Global.getLetterConfig("letter.loanRepay7.title7");
		public static final String LETTER_LOANREPAY7_CONTENT7 = Global.getLetterConfig("letter.loanRepay7.content7");
		
		public static final String LETTER_LOANREPAY3_TITILE3= Global.getLetterConfig("letter.loanRepay3.title3");
		public static final String LETTER_LOANREPAY3_CONTENT3 = Global.getLetterConfig("letter.loanRepay3.content3");
		
		public static final String LETTER_LOANREPAY_TITILE= Global.getLetterConfig("letter.loanRepay.title");
		public static final String LETTER_LOANREPAY_CONTENT = Global.getLetterConfig("letter.loanRepay.content");
		
		//#逾期还款提醒
		public static final String LETTER_OVERREAPY="8";
		public static final String LETTER_OVERREAPY_TITILE= Global.getLetterConfig("letter.overReapy.title");
		public static final String LETTER_OVERREAPY_CONTENT = Global.getLetterConfig("letter.overReapy.content");
	}
	
	 /**
	* 消息业务节点类型
	* @author lzj
	*
*/
 public static final class MsgBusinessType{
	 /** 借款新业务提醒*/
	 public static final String  NEW_LOAN= "0";
	 public static final String  NEW_LOAN_TXT= "借款新业务提醒";
	 /** 理财满标提醒*/
	 public static final String  FINANCE_FULL = "1";
	 public static final String  FINANCE_FULL_TXT = "理财满标提醒";
	 /** 理财流标提醒*/
	 public static final String FAIL_INVEST = "2";
	 public static final String FAIL_INVEST_TXT = "理财流标提醒";
	 
	 /** 理财7天回款提醒*/
	 public static final String FINANCE_REPAY7 = "3";
	 public static final String FINANCE_REPAY7_TXT = "理财7天回款提醒";
	 /** 理财3天回款提醒*/
	 public static final String FINANCE_REPAY3 = "4";
	 public static final String FINANCE_REPAY3_TXT = "理财3天回款提醒";  
	 /** 理财1天回款提醒*/
	 public static final String FINANCE_REPAY1 = "5";
	 public static final String FINANCE_REPAY1_TXT = "理财1天回款提醒";  
	 /** 理财到期回款提醒*/
	 public static final String FINANCE_REPAY = "6";
	 public static final String FINANCE_REPAY_TXT = "理财到期回款提醒";  
	 
	 /** 借款B端审核不通过提醒*/
	 public static final String LOAN_AUDIT_FAIL = "7";
	 public static final String LOAN_AUDIT_FAIL_TXT = "借款B端审核不通过提醒";  
	 
	 /** 借款B端审核通过准备签约提醒*/
	 public static final String LOAN_AUDIT_SUCCESS = "8";
	 public static final String LOAN_AUDIT_SUCCESS_TXT = "借款B端审核通过准备签约提醒";
	 
	 /** 借款B端放款成功提醒*/
	 public static final String MAKE_LOAN_SUCCESS = "9";
	 public static final String MAKE_LOAN_SUCCESS_TXT = "借款B端放款成功提醒";
	 
	 /** 借款7天还款提醒*/
	 public static final String LOAN_REPAY7 = "10";
	 public static final String LOAN_REPAY7_TXT = "借款7天还款提醒";
	 /** 借款3天还款提醒*/
	 public static final String LOAN_REPAY3 = "11";
	 public static final String LOAN_REPAY3_TXT = "借款3天还款提醒";  
	 /** 借款1天还款提醒*/
	 public static final String LOAN_REPAY1 = "12";
	 public static final String LOAN_REPAY1_TXT = "借款1天还款提醒";  
	 /** 借款到期还款提醒*/
	 public static final String LOAN_REPAY = "13";
	 public static final String LOAN_REPAY_TXT = "借款到期还款提醒";  
	 
 }
	
	
	/**
	 * 营销短信
	 * @author lzj
	 * */
	public static class messageDForm{
		//W端新业务提醒机构---下单
		public static final String MESSAGE_NEWLOAN = "0";
		public static final String MESSAGE_NEWLOAN_CONTENT = Global.getMarketConfig("message.newLoan.content");
		//理财满标通知卖家
		public static final String MESSAGE_FINACEFULL = "1";
		public static final String MESSAGE_FINACEFULL_CONTENT = Global.getMarketConfig("message.financeFull.content");
		//理财流标通知卖家
		public static final String MESSAGE_FAILINVEST="2";
		public static final String MESSAGE_FAILINVEST_CONTENT = Global.getMarketConfig("message.failInvest.content");
		//理财回款通知卖家
		public static final String MESSAGE_FINACEREAPY="3";
		public static final String MESSAGE_FINACEREAPY7_CONTENT7 = Global.getMarketConfig("message.financeRepay7.content7");
		
		public static final String MESSAGE_FINACEREAPY3_CONTENT3 = Global.getMarketConfig("message.financeRepay3.content3");
		
		public static final String MESSAGE_FINACEREAPY_CONTENT = Global.getMarketConfig("message.financeRepay.content");
		
		//#借款B端审核不通过
		public static final String MESSAGE_LOANAUDITFAIL="4";
		public static final String MESSAGE_LOANAUDITFAIL_CONTENT = Global.getMarketConfig("message.loanAuditFail.content");
		
		//#借款B端审核通过准备签订合同
		public static final String MESSAGE_LOANAUDITSUCCESS="5";
		public static final String MESSAGE_LOANAUDITSUCCESS_CONTENT = Global.getMarketConfig("message.loanAuditSuccess.content");
		
		//#借款B端放款成功
		public static final String MESSAGE_MAKELOANSUCCESS="6";
		public static final String MESSAGE_MAKELOANSUCCESS_CONTENT = Global.getMarketConfig("message.makeLoanSuccess.content");
		
		
		//#借款还款提醒
		public static final String MESSAGE_LOANREPAY="7";
		public static final String MESSAGE_LOANREPAY7_CONTENT7 = Global.getMarketConfig("message.loanRepay7.content7");
		
		public static final String MESSAGE_LOANREPAY3_CONTENT3 = Global.getMarketConfig("message.loanRepay3.content3");
		
		public static final String MESSAGE = Global.getMarketConfig("message.loanRepay.content");
		
		//#逾期还款提醒
		public static final String MESSAGE_OVERREAPY="8";
		public static final String MESSAGE_OVERREAPY_CONTENT = Global.getMarketConfig("message.overReapy.content");
		
		
	}
	
	
	public static final String YES = "1";
	public static final String NO = "0";
	/** 新增*/
	public static final String NEW = "new";
	/** 有效的*/
	public static final String VALID = "valid";
	/** 无效的*/
	public static final String INVALID = "invalid";
	/** 挂起/暂停 */
	public static final String SUSPEND = "suspend";
	/** 成功 */
	public static final String SUCCESS = "success";
	/** 失败 */
	public static final String FAIL = "fail";
	
	public static final String fdfsStatus = Global.getApiConfig("wfw.api.http.fdfs.status");

	/**
	 * 业务合同类型
	 *
	 */
	public static final class LoanOrderType {
		/** 默认（B类型） */
		public static final String TYPE_B = "0";
		/** W类型 */
		public static final String TYPE_W = "1";
	}
	
	public static final class ProProductType {
		//贷款产品
		public static final String LOAN_1 = "1";
		//理财产品
		public static final String MANAGER_2 = "2";
	}
	/**
	 * 资金账户流水交易类型
	 * @author Chenh
	 *
	 */
	public static final class FlowType {
		//充值(入款)
		public static final String RECHARGE = "1";
		//提现(扣款)
		public static final String WITHDRAW= "2";
		//回收利息(入款)
		public static final String IN_INTEREST = "3";
		//回收本金(入款)
		public static final String IN_PRINCIPAL= "4";
		//回收本息(入款)
		public static final String IN_MONEY = "5";
		//投资(扣款)
		public static final String INVEST = "6";
		//还款(扣款)
		public static final String REPAYMENT = "7";
		//放款(扣款)
		public static final String LENDING = "8";
		//借款(入款)
		public static final String LOAN = "9";
		//提现手续费(扣款)
		public static final String WITHDRAW_FEE = "10";
		
	}
	/**
	 * 资金账户流水方式
	 * @author Chenh
	 *
	 */
	public static final class TradeWay {
		//线上
		public static final String ON_LINE = "1";
		//线下
		public static final String OFF_LINE= "2";
		
		
	}
	
	/**
	 * 附件类型
	 */
	public static final class FileType {
		/** 授信单据附件 */
		public static final String FILE_TYPE_1 = "1";

		/**
		 * 合同单据附件
		 */
		// 签订合同(贷款合同)
		public static final String FILE_TYPE_LOANCONTRACT_1_1 = "1_1";
		//
		public static final String FILE_TYPE_LOANCONTRACT_2_1 = "2_1";
		
		/** 授信申请：签订合同附件*/
		//授信申请附件    授信签订合同附件   业务办理提交申请    放款审批
		public static final String FILE_TYPE_CREDIT_SIGN = "credit_apply_sign";
		public static final String FILE_TYPE_CREDIT_ACTIVI = "credit_apply_activi";
		
		/** 展期合同附件 */
		public static final String FILE_TYPE_EXTEND = "extend";
		/** 展期:签订合同附件 */
		public static final String FILE_TYPE_EXTEND_SIGN = "extend_sign";
		
		/** 客户档案 */
		public static final String CUSTOMER_ARCHIVES = "customer_archives";
		
		/**意向调查 */
		public static final String CUSTOMER_INTENT = "customer_intent";
		
		/**理财附件#4498*/
		public static final String FILE_TYPE_FINANCIAL_PRODUCT = "financial_product";
	}

	/**
	 * 客户类型
	 */
	public static final class CustomerType {
		/** 企业 客户 */
		public static final String CUST_COMPANY  = "1";
		public static final String CUST_COMPANY_TXT  = "企业";
		/** 个人客户 */
		public static final String CUST_EMPLOYEE = "2";
		public static final String CUST_EMPLOYEE_TXT = "个人";
	}
	/**
	 * 客户状态 
	 */
	public static final class CustomerStatus {
		/** 潜在客户 */
		public static final String UNNORMAL  = "unnormal";
		/** 正式客户 */
		public static final String NORMAL  = "normal";
		/** 黑名 */
		public static final String BLACK  = "black";
		/** 加入黑名审核中 */
		public static final String BLACK_AUDIT = "black_audit";
		/** 解除黑名审核中 */
		public static final String REMOVE_BLACK_AUDIT = "remove_black_audit";
	}

	/**
	 * 押品类型
	 *
	 */
	public static final class CollateralType {
		/****** 抵押 *****/
		/** 商铺写字楼 */
		public static final int OFFICE_BUILDING 	= 1;
		/** 房子 */
		public static final int HOUSE				= 2;
		/** 工业用地 */
		public static final int INDUSTRIAL_LAND 	= 3;
		/** 商宅用地 */
		public static final int COMMERCIAL_LAND 	= 4;
		/** 公寓信息 */
		public static final int APARTMENT 			= 5;
		/** 独栋别墅 */
		public static final int SINGLE_VILLAS 		= 6;
		/** 住宅信息 */
		public static final int RESIDENCE 			= 7;
		/** 联排别墅 */
		public static final int ROW_VILLA 			= 8;
		/** 抵押车辆（车辆登记证） */
		public static final int MORT_CAR 			= 9;
		/** 机器 */
		public static final int MOR_MACHINE 		= 10;
		/** 林权 */
		public static final int MOR_LINQUAN 		= 11;
		/** 农业用地 */
		public static final int MOR_NONG 		= 12;

		/****** 质押 *****/
		/** 车辆 */
		public static final int CAR 				= 1;
		/** 存货 */
		public static final int STOCK 			= 2;
		/** 股权 */
		public static final int STOCK_RIGHT 	= 3;
		/** 机器 */
		public static final int MACHINE 			= 4;
		/** 无形权利 */
		public static final int INVISIBLE_POWER = 5;
		/** 用地 */
		public static final int UER_LAND 		= 6;
	}
	/**
	 * 业务合同状态
	 *
	 */
	public static final class LoanContractStatus {
        //字典表  type=loan_contract_status
		
		/** 中止  （放款前被停止叫中止，无后续操作） */
		public static final String TO_SUSPENSION 		= "0";
		/** 新增，还没提交申请） */
		public static final String TO_REVIEW 			= "1";
		/** 待审批*/	
		public static final String TO_APPROVE 			= "2";
		/** 待签订 */
		public static final String TO_SIGN 				= "3";
		/** 合同审核中 */
		public static final String CONTRACT_AUDIT 		= "contract_audit";
		/** 放款待审批 */
		public static final String TO_LOAN_APPROVAL 	= "4";
		/** 放款审批中 */
		public static final String IN_LOAN_APPROVAL 	= "in_loan_approval";
		/** 待放款 */
		public static final String TO_LOAN 				= "5";
		/** 未结清 */
		public static final String UN_CLEARED 			= "6";
		/** 已结清 */
		public static final String CLEARED 				= "7";
		/** 已逾期 */
		public static final String OVERDUE 				= "8";
		/** 已终止 */
		public static final String ENDED 				= "9";
		/** 贷前变更待审批 *//*
		public static final String DQBG 				= "10";*/
		/** 已核销 */
		public static final String CANCEL				= "10";
		
		/** 已展期 */
		public static final String EXTENDED			=	"extended";
		/** 展期：待审批  */
		public static final String ET_TO_APPROVE	=	"et_to_approve";
		/** 展期：待签订  */
		public static final String ET_TO_SIGN		=	"et_to_sign";
		/** 展期：审批不通过  */
		public static final String EXTEND_END		=	"extend_end";
		
		
		/** 担保专用  待收费  （担保签订合同之后就是待收费状态，）   担保公司的担保费用应当在贷款审批下来以后，放贷之前收取    */
		public static final String DB_TO_CHARGE		=	"db_to_charge";
		/** 担保专用  待放款      登记放款后进入保后待检查     */
		public static final String DB_TO_LOAN		=	"db_to_loan";
		/** 担保专用      放款后，进入保后待检查     */
		public static final String DB_TO_CHECK		=	"db_to_check";
		/** 担保专用      解除担保     */
		public static final String DB_TO_REMOVE	    =	"db_to_remove";
	}
	/**
	 * 平台订单转移状态
	 *
	 */
	public static final class MoveStatus {
		/** 待审核 */
		public static final String AUDIT_ING = "1";
		/**审核通过*/	
		public static final String AUDIT_SUCCESS= "2";
		/**审核不通过*/	
		public static final String AUDIT_FAIL= "3";
		
	}
	/**
	 * 五级状态（中文和数字两种，分别对应合同表和字典表）
	 * @author srf
	 *
	 */
	public static final class FiveLevelStatus{
		/** 1 正常 */
		public static final String NORMAL = "正常";
		public static final String NORMAL_N = "1";
		/** 2 关注 */
		public static final String CONCERN = "关注";
		public static final String CONCERN_N = "2";
		/** 3 次级 */
		public static final String SECONDERY = "次级";
		public static final String SECONDERY_N = "3";
		/** 4 可疑 */
		public static final String SUSPICIOUS = "可疑";
		public static final String SUSPICIOUS_N = "4";
		/** 5 损失 */
		public static final String LOSS = "损失";
		public static final String LOSS_N = "5";
		
		public static final String DEBT_STATUS = "1";
	}
	/**
	 * 押品类型
	 * @author lzj
	 */
	public static final class MortgageApplayType{
		 //字典表  type=mortgage_applay_type
		/** 抵押 */
		public static final String MORTGAGE	= "0";
		/** 质押*/
		public static final String PLEDGE	= "1";
	}
	/**
	 * 审批类型
	 * @author lzj
	 */
	public static final class MortgageAuditType{
		/** 借出*/
		public static final String BORROW_OUT	= "1";
		/** 归还 */
		public static final String TAKE_IN	= "0";
	}
	/**
	 * 押品借出合同状态
	 * @author lzj
	 */
	public static final class StrutsStatus{
		//字典表  type=ypjc_struts_status
		/** 新增(未借出) */
		public static final String BORROW_NO	= "1";
		/** 审批中*/
		public static final String BORROW_AUDIT	= "2";
		/** 已收押*/
		public static final String TAKE_INTO	= "3";
		/** 已借出*/
		public static final String BORROW_YES	= "4";
		/** 已归还*/
		public static final String RETURN_YES	= "5";
		/** 已解除*/
		public static final String REMOVE_YES	= "6";
	}
	/**
	 * 合同状态
	 * @author srf
	 */
	public static final class ContractStatus{
		/** 待审批 */
		public static final String PENDING	= "0";
		/** 审批不通过 */
		public static final String NOT_PASS	= "1";
		/** 审批通过 */
		public static final String APPROVAL	= "2";
	}
	
	/**
	 * 还款状态
	 * @author srf
	 *
	 */
	public static final class RepayStatus {
		/** 未还 */
		public static final String NO_PAID 		= "0";
		/** 未结清 */
		public static final String IN_PAYMENT 	= "1";
		/** 已还清 */
		public static final String PAID 			= "2";
		/** 需要还款：未还+未结清 */
		public static final String NEED_PAY 		= "3";
		/** 提前还款*/
		public static final String ADVANCE 		= "4";
	}
	/**
	 * 授信合同状态
	 */
	public static final class LoopLoanStatus{
		/**	待申请\评审（客户经理新增，还没提交申请）	 */
		public static final String TO_APPLY  		=	"0";
		/**	待审批-走审批制 	*/
		public static final String TO_APPROVE 	 	=	"1";
		/**	授信成功 	*/
		public static final String CREDIT_SUCCESS  	=	"2";
		/**	授信失败 	*/
		public static final String CREDIT_FAIL 		= 	"3";
		/** 授信结束	*/
		public static final String CREDIT_END  		=	"4";
	}
	/**
	 * 授信申请状态
	 */
	public static final class CreditApplyStatus{
		/**	临时	 */
		public static final String TEMP  		=	"tmp";
		/**	待申请\评审（客户经理新增，还没提交申请）	 */
		public static final String NEW  		=	"new";
		/**	待审批-走审批制 	*/
		public static final String TO_APPROVE 	 	=	"to_approve";
		/**	待签订 	*/
		public static final String TO_SIGN 	 	=	"to_sign";
		/**	授信成功 	*/
		public static final String CREDIT_SUCCESS  	=	"success";
		/**	授信失败 	*/
		public static final String CREDIT_FAIL 		= 	"fail";
	}
	
	/**
	 * 流程定义标识 
	 */
	public static final class ProcDefKey{
		/**	授信流程标识	*/
		public static final String LOOP_LOAN = "loop_loan";
		/**	贷款业务申请流程标识	*/
		public static final String LOAN_APPLY = "loan_apply";
		/**	放款申请流程标识	*/
		public static final String LEND_APPLY = "lend_apply";
		/**	展期申请流程标识	*/
		public static final String EXTEND_APPLY = "extend_apply";
		/**	加入黑名单流程标识	*/
		//public static final String BLACK_APPLY = "black_apply";
		public static final String BLACK_APPLY = "jrhmd";//tjhmd
		/**	解除黑名单流程标识	*/
		//public static final String BLACK_REMOVE = "black_remove";
		public static final String BLACK_REMOVE = "jchmd";
		
	}
	
	/**
	 * 业务合同状态
	 * @author zzm
	 */
	public static final class ExtendContractStatus {
		/** 待评审 */
		public static final String TO_REVIEW 		= "1";
		/** 待签到订 */	
		public static final String TO_SIGN 			= "2";
		/** 已签订 */
		public static final String SIGNED 			= "3";
		/** 未结清 */
		public static final String UN_CLEARED 		= "4";
		/** 已结清 */
		public static final String CLEARED 			= "5";
		/** 已撤回 */
		public static final String WITHDRAW 		= "6";
		/** 再展期 */
		public static final String RE_EXTEND 		= "7";
		/** 已逾期 */
		public static final String OVERDUE 			= "8";

	}
	
	/**
	 * 申请退款业务类型
	 * @author srf
	 * @date 20160406
	 */
	public static final class ReimbBusinessStatus{
		/** 常规贷款 */
		public static final String CONVENTIONAL		="0";
		/** 循环授信贷款 */
		public static final String CYCLINGCREDIT	= "1";
	}
	
	/**
	 * 申请退款状态
	 * @author srf
	 * @date 20160406
	 */
	public static final class ReimburseStatus{
		/** 1新增*/
		public static final String TO_REVIEW 	= "1";
		/** 2待审批 */
		public static final String TO_LOAN 		= "2";
		/** 3待放款*/
		public static final String REFUND 		= "3";
		/** 4已退费 */
		public static final String WITHDRAW		= "4";
		/** 5已撤回 */
		public static final String TO_RETURN		= "5";
	}
	
	/**
	 * 是否已读
	 * @author user
	 * @date 20160406
	 */
	public static final class ReimReadStatus{
		/** 未读 */
		public static final String NOT_READ 	= "0";
		/** 已读 */
		public static final String IS_READ 	= "1";
	}
	
	/**
	 * 页面左上角当前位置展示 ，例如：业务中心-我的业务-业务办理
	 * @author user
	 * @date 20160406
	 */
	public static final class LocationUrl{
		
		public static  String URL 	= "";
		
	}
	
   public static final class ProductName{
		
		public static  String PRODUCTNAME 	= "";
		
   }
   
   
   /**
	 *公司类型
	 *
	 */
	public static final class CompanyType {
		//担保
		public static final String DAN_BAO = "1";
		//贷款
		public static final String DAI_KUAN = "2";
		//担保
		public static final String TYPE_DB = "Db";
		//贷款
		public static final String TYPE_XD = "Xd";

	}
	   /**
     * @Description 资本信息利润类型
     * @author lzj
     * @date 2016-9-16
     */
    public static final class ProfitStatus{
    	/** 已分配*/
    	public static final String NO_ASSIGN = "1";
    	/** 未分配*/
    	public static final String YES_ASSIGN = "2";
    }
    /**
     * @Description 资本信息融资状态
     * @author lzj
     * @date 2016-9-16
     */
    public static final class FinanceStatus{
    	/** 增加*/
    	public static final String FINANCE_ADD = "1";
    	/** 归还*/
    	public static final String FINANCE_REMOVE = "2";
    }
   
   
   
   	/**
   	 * @Description [[_ProductStatus_]] 产品状态
   	 * @author zzm
   	 * @date 2016-6-1 下午5:15:46 
   	 */
	public static final class ProductStatus{
	   /** 新建*/
	   public static String NEW = "new";
	   /** 已发布*/
	   public static String PUBLISHED = "published";
	}
	
	/**
	 * @Description [[_ProductStatus_]] 提前还款申请状态
	 * @author zzm
	 * @date 2016-6-1 下午5:15:46 
	 */
	/*public static final class AdvanceStatus{
		*//** 待审批 *//*
		public static String TO_ADUIT = "toAudit";
		*//** 有效的（审核通过） *//*
		public static String VALID = "valid";
		*//** 无效的 （审核不通过）*//*
		public static String INVALID = "invalid";
	}*/
	/**
	 * @Description [[_ProductStatus_]] 提前还款申请状态
	 * @author lzj
	 * @date 2016-6-1 下午5:15:46 
	 *根据Adance中的注释更改过来的
	 */
	public static final class AdvanceStatus{
		/** 待审批 */
		public static String TO_ADUIT = "0";
		/** 无效的 （审核不通过）*/
		public static String INVALID = "1";
		/** 有效的（审核通过） */
		public static String VALID = "2";
	}
    /**
     * @Description [[_LetterTplType_]] 函件类型
     * @author zzm
     * @date 2016-6-13 上午10:11:39 
     */
    public static final class LetterTplType{
    	/** 催收函*/
    	public static final String HASTEN_REPAY_RECORD = "hastenrepayrecord";
    	/** 担保函*/
    	public static final String HASTEN_DANBAO_RECORD = "danbaorecord";

    }
	
    /**
     * @Description [[_TradingType_]] 交易类型
     * @author zzm
     * @date 2016-6-14 下午5:22:50 
     */
    public static final class TradingType{
    	/** 充值*/
    	public static final String RECHARGE = "recharge";
    	/** 提现*/
    	public static final String WITHDRAW = "withdraw";
    	/** 付费业务激活*/
    	public static final String FEE_BIZ_PAY = "feebizpay";
    }
    
    
    /**
     * @Description [[_付费业务类型_]] FeeBizCategory类
     * @author zzm
     * @date 2016-6-21 下午5:49:46 
     */
    public static final class FeeBizCategory{
    	/** 短信发送*/
    	public static final String MESSAGE = "message";
    	/** 风控模板*/
    	public static final String RISKTIP = "risktip";
    }
    
    
    /**
     * @Description [[_计数类型_]] CountCategory类
     * @author zzm
     * @date 2016-7-14 上午11:09:36 
     */
    public static final class CountCategory{
    	/** 贷款申请*/
    	public static final String LOAN_APPLY = "loan_apply";
    	/** 授信申请*/
    	public static final String CREDIT_APPLY = "credit_apply";
    }
	/**
	 * 理财资金流水交易类型
	 *
	 */
	public static final class AccountTradingType {
		/** 1放款*/
		public static final String GO_LOAN = "1";
		/** 2还款 */
		public static final String GO_REPAYMENT = "2";
		/** 3借款手续费*/
		public static final String LONA_FREE = "3";
		/** 4投资 */
		public static final String GO_INVEST = "4";
		/** 5投资回款 */
		public static final String RE_INTEREST = "5";
		/** 6充值 */
		public static final String PAY_IN = "6";
		/**7提现*/
		public static final String CASH_OUT = "7";
		/** 8债权转让 */
		public static final String GO_TRANSFER  = "8";
		/** 9保证金充值 */
		public static final String IN_MARGIN  = "9";
		/** 10保证金提取 */
		public static final String OUT_MARGIN  = "10";
	}
	/**
	 * 理财产品类型
	 *
	 */
	public static final class ifOnLine {
		/** 线上流水*/
		public static final String ON_LINE = "1";
		/** 线下流水*/
		public static final String OFF_LINE = "2";
	}
	/**
	 * 业务配置类型
	 */
	public enum TProductBizType {
		TPBIZ_SQ("1", "售前"),
		TPBIZ_SZ("2", "售中"),
		TPBIZ_SH("3", "售后");
		private String key;//类别
		private String name;//名称
		
		/** 构造方法 ***********************************************************************************/
		/**
		 * @Description [[_xxx_]]构造器
		 * @author Chenh
		 * @date 2015-1-15 下午3:39:14  
		 */
		private TProductBizType(String key, String name) {
			this.key = key;
			this.name = name;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	
		
	/**
	 * 自定义表单模块类型
	 */
	public enum FModel {
		M_TPL("tpl", "模板", "form_tpl", "", "", new FModel[]{}),
		
		M_PRODUCT("product", "产品", "form_Product", "t_product", "/product/tProduct/save", new FModel[]{M_TPL}),
		M_ADVISORY("advisory", "业务咨询", "form_Advisory", "t_advisory", "/advisory/tAdvisory/save", new FModel[]{M_TPL}),
		M_GUARANTEE_CONTRACT("guarantee_contract", "保证合同", "form_GuaranteeContract", "t_guarantee_contract", "/guarantee/tGuaranteeContract/save", new FModel[]{M_TPL}),
		M_MORTGAGE_CONTRACT("mortgage_contract", "抵押合同", "form_MortgaeContract", "t_mortgage_contract", "/mortgage/mortgageContract/save", new FModel[]{M_TPL}),
		M_PLEDGE_CONTRACT("pledge_contract", "质押合同", "form_PledgeContract", "t_pledge_contract", "/pledge/pledgeContract/save", new FModel[]{M_TPL}),
		M_LOOPLOAN("looploan", "授信", "form_Looploan", "t_looploan", "/looploan/tLoopLoan/save", new FModel[]{M_TPL}),
		M_MESSAGE("message", "消息管理", "form_Message", "t_message", "/product/tMessage/save", new FModel[]{M_TPL}),
		
		M_CUSTOMER_EMPLOYEE("employee", "个人客户", "form_Employee", "t_employee", "/employee/tEmployee/save", new FModel[]{M_TPL}),
		M_CUSTOMER_COMPANY("company", "企业客户", "form_Company", "t_company", "/company/tCompany/save", new FModel[]{M_TPL}),
		M_CUSTOMER_CREDIT("ba_customer_credit", "客户授信", "form_ba_customer_credit", "", "", new FModel[]{M_TPL}),
		M_CUSTOMER_BLACK("ba_customer_black", "加入黑名单", "form_ba_customer_black", "", "", new FModel[]{M_TPL}),
		M_CUSTOMER_UNBLACK("ba_customer_unblack", "解除黑名单", "form_ba_customer_black", "", "", new FModel[]{M_TPL}),
		
		M_BUSINESS_APPLICATION_DQDC("ba_dqdc", "立项调查", "form_ba_dqdc", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_YWSQ("ba_ywsq", "业务申请", "form_ba_ywsq", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_FXPG("ba_fxpg", "风险评估", "form_ba_fxpg", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_FWHGJC("ba_fwhgjc", "法务合规检查", "form_ba_fwhgjc", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_QDHT("ba_qdht", "签订合同", "form_ba_qdht", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_FKSQ("ba_fksq", "放款申请", "form_ba_fksq", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_DQBG("ba_dqbg", "贷前变更", "form_ba_dqbg", "t_loan_contract", "/contract/tLoanContract/tLoanContractChangeForm", new FModel[]{}),
		M_BUSINESS_APPLICATION_ZDSJBG("ba_zdsjbg", "重大事件报告", "form_ba_zdsjbg", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_ZZHT("ba_zzht", "中止合同", "form_ba_zzht", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_YPGL("ba_ypgl", "押品管理", "form_ba_ypgl", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_FK("ba_fk", "放款", "form_ba_fk", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_HTCK_YW("ba_htck_yw", "业务合同查看", "form_ba_htck", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_HTCK_DY("ba_htck_dy", "抵押合同查看", "form_ba_htck", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_HTCK_ZY("ba_htck_zy", "质押合同查看", "form_ba_htck", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_HTCK_BZ("ba_htck_bz", "保证合同查看", "form_ba_htck", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_SK("ba_sk", "收款", "form_ba_sk", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_DHJC("ba_dhjc", "贷后检查", "form_ba_dhjc", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_ZQSQ("ba_zqsq", "展期申请", "form_ba_zqsq", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_TQHK("ba_tqhk", "提前还款", "form_ba_tqhk", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_TFSQ("ba_tfsq", "退费申请", "form_ba_tfsq", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_WJFL("ba_wjfl", "五级分类", "form_ba_wjfl", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_YPJC("ba_wpjc", "押品借出", "form_ba_wpjc", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_YPBG("ba_ywbg", "业务变更", "form_ba_ywbg", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION_HZCL("ba_hzcl", "坏账处理", "form_ba_hzcl", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{}),
		M_BUSINESS_APPLICATION("business_application", "业务申请", "form_Business_Application", "t_loan_contract", "/contract/tLoanContract/save", new FModel[]{
			M_TPL
//			M_BUSINESS_APPLICATION_DQDC,
//			M_BUSINESS_APPLICATION_YWSQ,
//			M_BUSINESS_APPLICATION_FXPG,
//			M_BUSINESS_APPLICATION_FWHGJC,
//			M_BUSINESS_APPLICATION_QDHT,
//			M_BUSINESS_APPLICATION_FKSQ,
//			M_BUSINESS_APPLICATION_DQBG,
//			M_BUSINESS_APPLICATION_ZDSJBG,
//			M_BUSINESS_APPLICATION_ZZHT,
//			M_BUSINESS_APPLICATION_YPGL,
//			M_BUSINESS_APPLICATION_FK,
//			M_BUSINESS_APPLICATION_HTCK_BZ,
//			M_BUSINESS_APPLICATION_HTCK_DY,
//			M_BUSINESS_APPLICATION_HTCK_YW,
//			M_BUSINESS_APPLICATION_HTCK_ZY,
//			M_BUSINESS_APPLICATION_SK,
//			M_BUSINESS_APPLICATION_DHJC,
//			M_BUSINESS_APPLICATION_ZQSQ,
//			M_BUSINESS_APPLICATION_TQHK,
//			M_BUSINESS_APPLICATION_TFSQ,
//			M_BUSINESS_APPLICATION_WJFL,
//			M_BUSINESS_APPLICATION_YPJC,
//			M_BUSINESS_APPLICATION_YPBG,
//			M_BUSINESS_APPLICATION_HZCL
		});
		private String key;//类别
		private String name;//名称
		private String fname;//表单名
		private String tname;//表名
		private String action;//保存表单action
		private FModel[] modsub;//子模块
		/** 构造方法 ***********************************************************************************/
		/**
		 * @Description [[_xxx_]]构造器
		 * @author Chenh
		 * @date 2015-1-15 下午3:39:14  
		 */
		private FModel(String key, String name, String fname, String tname, String action) {
			this.key = key;
			this.name = name;
			this.fname = fname;
			this.tname = tname;
			this.action = action;
		}
		/**
		 * @Description [[_xxx_]]构造器
		 * @author Chenh
		 * @date 2015-1-15 下午3:39:14  
		 */
		private FModel(String key, String name, String fname, String tname, String action, FModel[] modsub) {
			this.key = key;
			this.name = name;
			this.fname = fname;
			this.tname = tname;
			this.action = action;
			this.modsub = modsub;
		}
		
		public static FModel getFModelByKey(String key) {
			FModel[] fmodels = FModel.values();
			for (FModel fModel : fmodels) {
				if((fModel.getKey()).equals(key)){
					return fModel;
				}
			}
			return null;
		}
		
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getTname() {
			return tname;
		}
		public void setTname(String tname) {
			this.tname = tname;
		}
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		public String getFname() {
			return fname;
		}
		public void setFname(String fname) {
			this.fname = fname;
		}

		public void setModsub(FModel[] modsub) {
			this.modsub = modsub;
		}
		public FModel[] getModsub() {
			return modsub;
		}
	}
	
	
	/**
	 *立项调查(提前还款)状态
	 *
	 */
	public static final class PreLoanInvesStatus {
             // 新建   审批中    审批通过， 审批不通过
		/** 新增，还没提交申请） */
		public static final String TO_REVIEW 			= "1";
		/** 审批中*/	
		public static final String TO_APPROVE 			= "2";
		/** 审批通过 */
		public static final String TO_AGREE				= "3";
		/** 审批不通过 */
		public static final String TO_DISAGREE 	        = "4";

	}
	
	
//	/**
//	 * 征信枚举
//	 */
//	public static enum CreditChecking {
//		C_PERSON_ALL(WindHttp.WT_PERSON, "", ""),
//		C_CORPORATION_ALL(WindHttp.WT_CORPORATION, "", "");
//		private String type;//类别
//		private String sub;//子分类
//		private String remark;//子分类
//		private CreditChecking(String type, String sub, String remark) {
//			this.type = type;
//			this.sub = sub;
//			this.remark = remark;
//		}
//		
//		public static CreditChecking[] init(String type, List<Map<String, String>> vals) {
//			for (Map<String, String> val : vals) {
//				Set<String> vkeys = val.keySet();
//				for(String key : vkeys) {
//					if(CreditChecking.getFModelByTypeAndSub(type, key) == null){
//						EnumUtils.addEnum(CreditChecking.class, "C_"+StringUtils.upperCase(type)+"_"+StringUtils.upperCase(key), new Class[]{String.class, String.class, String.class}, new Object[]{type, key, val.get(key)});
//					}
//				}
//			}
//			return CreditChecking.values();
//		}
//
//		public static CreditChecking[] initByVo(String type, WindVo vo) {
//			List<Map<String, String>> vals = new ArrayList<Map<String,String>>();
//			JSONObject voResult = vo.getResult();
//			Iterator<?> itkeys = vo.getResult().keys();
//			try {
//		        while(itkeys.hasNext()) {
//		            String key = (String) itkeys.next();
//		            String value = voResult.getString(key);//类型强转失败会异常
//		            
//		            Map<String, String> valMap = new HashMap<String, String>();
//		            valMap.put(key, value);
//		            vals.add(valMap);
//		        }
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			return init(WindHttp.WT_CORPORATION, vals);
//		}
//		
//		public static List<CreditChecking> getFModelByType(String type) {
//			List<CreditChecking>  creditCheckings = new ArrayList<CreditChecking>();
//			CreditChecking[] credits = CreditChecking.values();
//			for (CreditChecking credit : credits) {
//				if((credit.getType()).equals(type)){
//					creditCheckings.add(credit);
//				}
//			}
//			return creditCheckings;
//		}
//
//		public static CreditChecking getFModelByTypeAndSub(String type, String sub) {
//			CreditChecking[] credits = CreditChecking.values();
//			for (CreditChecking credit : credits) {
//				if(((credit.getType()).equals(type)) && ((credit.getSub()).equals(sub))){
//					return credit;
//				}
//			}
//			return null;
//		}
//
//		public String getType() {
//			return type;
//		}
//
//		public String getSub() {
//			return sub;
//		}
//
//		public void setType(String type) {
//			this.type = type;
//		}
//
//		public void setSub(String sub) {
//			this.sub = sub;
//		}
//
//		public String getRemark() {
//			return remark;
//		}
//
//		public void setRemark(String remark) {
//			this.remark = remark;
//		}
//	}
}

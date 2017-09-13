package com.wanfin.fpd.modules.wish.utils;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;

import com.jfinal.plugin.activerecord.Db;
import com.wanfin.fpd.common.config.Cons;

public class Rservice {
	
	
	public static final String username = Cons.wishUrl.WISH_R_USERNAME;
	public static final String password = Cons.wishUrl.WISH_R_PASSWORD;
	public static final String dbname = Cons.wishUrl.WISH_R_DBANME;
	public static final String host = Cons.wishUrl.WISH_R_HOST;
	public static final String port = Cons.wishUrl.WISH_R_PORT;
	
	
	/**
	 * 动态计算手续费
	 * 
	 * @param args
	 * @throws REXPMismatchException
	 */
	public static void main(String[] args) {

		//String user_id = "1000017815";
	/*	String amount = "1000";
		String loanContractId="37253cc1c4874111a2ee5d205186b454";
		String charge=blockOrder30_90(loanContractId,amount,"1");
		System.out.println(charge);*/
		//8,100,0,0,0
		//busiLength();
		/*System.out.println("main=======================================");
		String rr=riskControl30_90("1");
		System.out.println(rr);*/
		//busiLength();
		//fieldProcessingForOne("1000017218","1");
		RiskControlForOne30_90("1000017218","1");
	}
	/**
	 * 动态计算最多可提金额	
	 * 
	 * @param args
	 * @throws REXPMismatchException
	 */
	/*public static String getMaxLoan(String userId) {
		String forward = "";
		System.out.println("执行脚本");
		try {

			String query = Db.queryStr("select * from result_records where user_id = ? ", userId);
			// System.out.println("the sum="+sum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forward;
	}*/

	

	/**
	 * 申请借款锁定订单信息
	 * 
	 * @param args
	 * @throws REXPMismatchException
	 */

/*	public static String blockOrder(String loanContractId,String applicationMoney) {
		String forward="";
		System.out.println("执行脚本");
		RConnection connection = null;
		try { // 创建对象
			connection = new RConnection();
			String RfileName = "/operation.R";// /表示代码根目录
			String ss = Rservice.class.getResource(RfileName).getFile();
			System.out.println(ss);
			System.out.println(System.getProperty("os.name"));
			 if (!System.getProperty("os.name").equals("Linux")) {
				if (ss.startsWith("/")) {
					ss = ss.substring(1);
				}
			}
			System.out.println(ss);
			connection.eval("source('" + ss + "')");
			//forward=
			forward = connection.eval("operation(\""+ loanContractId + "\",\"" + applicationMoney +"\")").asString();
			REXP rResponseObject = connection.parseAndEval("try(eval(operation(\""+ loanContractId + "\",\"" + applicationMoney +"\")),silent=TRUE)");
			if (rResponseObject.inherits("try-error")) { 
			   forward=rResponseObject.asString();
			   System.out.println(rResponseObject.asString());
			}
			System.out.println("operation====================="+forward);
		} catch (Exception e) {
			e.printStackTrace();
		}
		connection.close();
		return forward;
	}*/
	
	
	public static String blockOrder30_90(String loanContractId,String applicationMoney,String status) {
		String forward="";
		System.out.println("执行脚本");
		RConnection connection = null;
		try { // 创建对象
			connection = new RConnection();
			String RfileName = "/operation30_90.R";// /表示代码根目录
			String ss = Rservice.class.getResource(RfileName).getFile();
			System.out.println(ss);
			System.out.println(System.getProperty("os.name"));
			 if (!System.getProperty("os.name").equals("Linux")) {
				if (ss.startsWith("/")) {
					ss = ss.substring(1);
				}
			}
			System.out.println(ss);
			connection.eval("source('" + ss + "')");
			
			forward = connection.eval("operation30_90(\""+ loanContractId + "\",\"" + applicationMoney +"\",\"" + status +"\",\"" + username +"\",\"" + password +"\",\"" + dbname +"\",\"" + host +"\"," + port +")").asString();
			/*REXP rResponseObject = connection.parseAndEval("try(eval(operation30_90(\""+ loanContractId + "\",\"" + applicationMoney +"\",\"" + status +"\")),silent=TRUE)");
			if (rResponseObject.inherits("try-error")) { 
			   forward=rResponseObject.asString();
			   System.out.println(rResponseObject.asString());
			}*/
			System.out.println("operation30_90====================="+forward);
		} catch (Exception e) {
			e.printStackTrace();
		}
		connection.close();
		return forward;
	}
	/**
	 * 定时执行风控脚本
	 * 
	 * @param args
	 * @throws REXPMismatchException
	 */

	public static String riskControl30_90(String status) {
		String forward="success";
		System.out.println("执行风控脚本:RiskControl30_90.R");
		RConnection connection = null;
		try { // 创建对象
			connection = new RConnection();

			String RfileName = "/RiskControl30_90.R";// /表示代码根目录
			String ss = Rservice.class.getResource(RfileName).getFile();
			System.out.println(ss);
			System.out.println(System.getProperty("os.name"));
			 if (!System.getProperty("os.name").equals("Linux")) {
				if (ss.startsWith("/")) {
					ss = ss.substring(1);
				}
			}
			System.out.println(ss);
			connection.eval("source('" + ss + "')");
			//forward=connection.eval("RiskControl(1)").asString();
			REXP rResponseObject = connection.parseAndEval("try(eval(RiskControl30_90(\"" + status +"\",\"" + username +"\",\"" + password +"\",\"" + dbname +"\",\"" + host +"\"," + port +")),silent=TRUE)");
			if (rResponseObject.inherits("try-error")) {
			   forward=rResponseObject.asString();
			}
			System.out.println("RiskControl30_90=====:"+forward);
			connection.close();
			connection = null; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return forward;
	}
	
	/**
	 * 定时计算经营时长
	 * 
	 * @param args
	 * @throws REXPMismatchException
	 */

	public static String busiLength(String status) {
		String forward="success";
		System.out.println("执行计算经营时长脚本");
		RConnection connection = null;
		try { // 创建对象
			connection = new RConnection();
			String RfileName = "/FieldProcessing.R";// /表示代码根目录
			String ss = Rservice.class.getResource(RfileName).getFile();
			System.out.println(ss);
			System.out.println(System.getProperty("os.name"));
			 if (!System.getProperty("os.name").equals("Linux")) {
				if (ss.startsWith("/")) {
					ss = ss.substring(1);
				}
			}
			System.out.println(ss);
			connection.eval("source('" + ss + "')");
			//forward=connection.eval("FieldProcessing(1)").asString();
			REXP rResponseObject = connection.parseAndEval("try(eval(FieldProcessing(\"" + status +"\",\"" + username +"\",\"" + password +"\",\"" + dbname +"\",\"" + host +"\"," + port +")),silent=TRUE)");
			if (rResponseObject.inherits("try-error")) { 
			   forward=rResponseObject.asString();
			}
			System.out.println("FieldProcessing=====:"+forward);
			connection.close();
			connection = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return forward;
	}
	
	
	
	
	/**
	 * 定时执行风控脚本
	 * 
	 * @param args
	 * @throws REXPMismatchException
	 */

/*	public static String riskControl(String status) {
		String forward="";
		System.out.println("执行风控脚本:RiskControl.R");
		RConnection connection = null;
		try { // 创建对象
			connection = new RConnection();
			String RfileName = "/RiskControl.R";// /表示代码根目录
			String ss = Rservice.class.getResource(RfileName).getFile();
			System.out.println(ss);
			System.out.println(System.getProperty("os.name"));
			 if (!System.getProperty("os.name").equals("Linux")) {
				if (ss.startsWith("/")) {
					ss = ss.substring(1);
				}
			}
			
			System.out.println(ss);
			connection.eval("source('" + ss + "')");
			//forward=connection.eval("RiskControl(1)").asString();
			REXP rResponseObject = connection.parseAndEval("try(eval(RiskControl(1)),silent=TRUE)");
			if (rResponseObject.inherits("try-error")) {
			   forward=rResponseObject.asString();
			   System.out.println(rResponseObject.asString() );
			}
			System.out.println("RiskControl====================="+forward);
			connection.close();
			connection = null; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return forward;
	}
	*/
	
	/**
	 * 定时计算经营时长-------个人
	 * 
	 * @param args
	 * @throws REXPMismatchException
	 */

	public static String fieldProcessingForOne(String userId,String status) {
		String forward="success";
		System.out.println("userId=======执行计算个人经营时长脚本");
		RConnection connection = null;
		try { // 创建对象
			connection = new RConnection();
			String RfileName = "/FieldProcessingForOne.R";// /表示代码根目录
			String ss = Rservice.class.getResource(RfileName).getFile();
			System.out.println(ss);
			System.out.println(System.getProperty("os.name"));
			 if (!System.getProperty("os.name").equals("Linux")) {
				if (ss.startsWith("/")) {
					ss = ss.substring(1);
				}
			}
			System.out.println(ss);
			connection.eval("source('" + ss + "')");
			//forward=connection.eval("FieldProcessingForOne(\""+ userId + "\",\"" + status +"\")").asString();
			REXP rResponseObject = connection.parseAndEval("try(eval(FieldProcessingForOne(\""+ userId + "\",\"" + status +"\",\"" + username +"\",\"" + password +"\",\"" + dbname +"\",\"" + host +"\"," + port +")),silent=TRUE)");
			if (rResponseObject.inherits("try-error")) { 
			   forward=rResponseObject.asString();
			}
			System.out.println("FieldProcessingForOne=====:"+forward);
			connection.close();
			connection = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return forward;
	}
	
	/**
	 * 定时计算经营时长-------个人
	 * 
	 * @param args
	 * @throws REXPMismatchException
	 */

	public static String RiskControlForOne30_90(String userId,String status) {
		String forward="success";
		System.out.println("userId=======执行个人风控脚本");
		RConnection connection = null;
		try { // 创建对象
			connection = new RConnection();
			String RfileName = "/RiskControlForOne30_90.R";// /表示代码根目录
			String ss = Rservice.class.getResource(RfileName).getFile();
			System.out.println(ss);
			System.out.println(System.getProperty("os.name"));
			 if (!System.getProperty("os.name").equals("Linux")) {
				if (ss.startsWith("/")) {
					ss = ss.substring(1);
				}
			}
			System.out.println(ss);
			connection.eval("source('" + ss + "')");
			//forward=connection.eval("FieldProcessingForOne(\""+ userId + "\",\"" + status +"\")").asString();
			REXP rResponseObject = connection.parseAndEval("try(eval(RiskControlForOne30_90(\""+ userId + "\",\"" + status +"\",\"" + username +"\",\"" + password +"\",\"" + dbname +"\",\"" + host +"\"," + port +")),silent=TRUE)");
			if (rResponseObject.inherits("try-error")) { 
			   forward=rResponseObject.asString();
			}
			System.out.println("RiskControlForOne30_90=====:"+forward);
			connection.close();
			connection = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return forward;
	}
	
}
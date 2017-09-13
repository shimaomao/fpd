package com.wanfin.fpd.core.billing;

import com.wanfin.fpd.common.config.Global;

public class BiSval {
	/**
	 * 预警提醒值
	 */
	public static final int WARING_NUMBER = 10;
	
	public final static class BiDicKey{
		public static final String BILING_ELEMENT_KEY = "biling_element_key";
		public static final String BILING_ELEMENT_TYPE = "biling_element_type";
	}

	/**
	 * 收费项唯一标示
	 * @author Chenh 
	 */
	public static class BiElementKey{
		/**短信**/
		public static final String BI_DX = Global.getIdsConfig("ids.billing.dx");

		/**平台**/
		public static final String BI_PT_ = Global.getIdsConfig("ids.billing.pt");
		/*平台-年费*/
		public static final String BI_PT_NF = Global.getIdsConfig("ids.billing.pt.nf");

		/**征信**/
		public static final String BI_ZX = Global.getIdsConfig("ids.billing.zx");
		/*征信-万众 */
		public static final String BI_ZX_WZ = Global.getIdsConfig("ids.billing.zx.wz");
		/*征信-万众 -企业*/
		public static final String BI_ZX_WZ_QY = Global.getIdsConfig("ids.billing.zx.wz.qy");
		/*征信-万众 -企业-公司控股信息*/
		public static final String BI_ZX_WZ_QY_GSKGXX = Global.getIdsConfig("ids.billing.zx.wz.qy.gskgxx");
		/*征信-万众 -企业-公司重要个人信息 */
		public static final String BI_ZX_WZ_QY_ZYGRXX = Global.getIdsConfig("ids.billing.zx.wz.qy.zygrxx");
		/*征信-万众 -企业-公司贷款信息 */
		public static final String BI_ZX_WZ_QY_DKXX = Global.getIdsConfig("ids.billing.zx.wz.qy.dkxx");
		/*征信-万众 -企业-公司公告信息 */
		public static final String BI_ZX_WZ_QY_GGXX = Global.getIdsConfig("ids.billing.zx.wz.qy.ggxx");
		/*征信-万众 -企业-公司附件信息  */
		public static final String BI_ZX_WZ_QY_FJXX = Global.getIdsConfig("ids.billing.zx.wz.qy.fjxx");
		/*征信-万众 -企业-公司个人控股信息  */
		public static final String BI_ZX_WZ_QY_GRKGXX = Global.getIdsConfig("ids.billing.zx.wz.qy.grkgxx");
		/*征信-万众 -企业-法院判决信息   */
		public static final String BI_ZX_WZ_QY_FYPJXX = Global.getIdsConfig("ids.billing.zx.wz.qy.fypjxx");
		/*征信-万众 -企业-公司执照信息    */
		public static final String BI_ZX_WZ_QY_ZZXX = Global.getIdsConfig("ids.billing.zx.wz.qy.zzxx");
		/*征信-万众 -企业-公司重要关联公司信息     */
		public static final String BI_ZX_WZ_QY_ZYGLGSXX = Global.getIdsConfig("ids.billing.zx.wz.qy.zyglgsxx");
		/*征信-万众 -企业-工商处罚信息     */
		public static final String BI_ZX_WZ_QY_GSCFXX = Global.getIdsConfig("ids.billing.zx.wz.qy.gscfxx");
		/*征信-万众 -企业-企业基本信息      */
		public static final String BI_ZX_WZ_QY_JBXX = Global.getIdsConfig("ids.billing.zx.wz.qy.jbxx");
		/*征信-万众 -企业-公司持股信息       */
		public static final String BI_ZX_WZ_QY_CGXX = Global.getIdsConfig("ids.billing.zx.wz.qy.cgxx");
		
		/*征信-万众 -个人*/
		public static final String BI_ZX_WZ_GR = Global.getIdsConfig("ids.billing.zx.wz.gr");
		/*征信-万众 -个人-个人贷款信息*/
		public static final String BI_ZX_WZ_GR_DKXX = Global.getIdsConfig("ids.billing.zx.wz.gr.dkxx");
		/*征信-万众 -个人-个人基本信息 */
		public static final String BI_ZX_WZ_GR_JBXX = Global.getIdsConfig("ids.billing.zx.wz.gr.jbxx");
		/*征信-万众 -个人-个人信用卡信息 */
		public static final String BI_ZX_WZ_GR_XYKXX = Global.getIdsConfig("ids.billing.zx.wz.gr.xykxx");
		/*征信-万众 -个人-个人持股信息  */
		public static final String BI_ZX_WZ_GR_CGXX = Global.getIdsConfig("ids.billing.zx.wz.gr.cgxx");
		/*征信-万众 -个人-关联用户信息  */
		public static final String BI_ZX_WZ_GR_GLYHXX = Global.getIdsConfig("ids.billing.zx.wz.gr.glyhxx");
		/*征信-万众 -个人-个人附件信息   */
		public static final String BI_ZX_WZ_GR_FJXX = Global.getIdsConfig("ids.billing.zx.wz.gr.fjxx");
		/*征信-万众 -个人-法院判决信息    */
		public static final String BI_ZX_WZ_GR_FYPJXX = Global.getIdsConfig("ids.billing.zx.wz.gr.fypjxx");
		/*征信-万众 -个人-用户证书信息     */
		public static final String BI_ZX_WZ_GR_YHZSXX = Global.getIdsConfig("ids.billing.zx.wz.gr.yhzsxx");
		
		/*征信-考拉   */
		public static final String BI_ZX_KL = Global.getIdsConfig("ids.billing.zx.kl");
		/*征信-考拉-企业*/
		public static final String BI_ZX_KL_QY = Global.getIdsConfig("ids.billing.zx.kl.qy");
		/*征信-考拉-企业 -企业工商照面报告 */
		public static final String BI_ZX_KL_QY_GSZMBG = Global.getIdsConfig("ids.billing.zx.kl.gszmbg");
		/*征信-考拉-企业 -企业投资信用报告*/
		public static final String BI_ZX_KL_QY_TZXYBG = Global.getIdsConfig("ids.billing.zx.kl.tzxybg");
		
		/*征信-考拉-个人*/
		public static final String BI_ZX_KL_GR = Global.getIdsConfig("ids.billing.zx.kl.gr");
		/*征信-考拉-个人-个人学历报告*/
		public static final String BI_ZX_KL_GR_XLBG = Global.getIdsConfig("ids.billing.zx.kl.gr.xlbg");
		/*征信-考拉-个人-个人职业简历*/  
		public static final String BI_ZX_KL_GR_ZYJL = Global.getIdsConfig("ids.billing.zx.kl.gr.zyjl");
		/*征信-考拉-个人- 职业资格报告*/ 
		public static final String BI_ZX_KL_GR_ZYZGBG = Global.getIdsConfig("ids.billing.zx.kl.gr.zyzgbg");
		/*征信-考拉-个人- 电信手机号状态核查*/ 
		public static final String BI_ZX_KL_GR_DXSJHZTHC = Global.getIdsConfig("ids.billing.zx.kl.gr.dxsjhzthc");
		/*征信-考拉-个人- 电信手机号在网时长查询 */ 
		public static final String BI_ZX_KL_GR_DXSJHZWSCCX = Global.getIdsConfig("ids.billing.zx.kl.gr.dxsjhzwsccx");
		/*征信-考拉-个人- 固定电话信息报告  */ 
		public static final String BI_ZX_KL_GR_GDDHXXBG = Global.getIdsConfig("ids.billing.zx.kl.gr.gddhxxbg");
		/*征信-考拉-个人- 房产价值评估   */ 
		public static final String BI_ZX_KL_GR_FCJZPG = Global.getIdsConfig("ids.billing.zx.kl.gr.fcjzpg");
		/*征信-考拉-个人- 个人工商信用报告   */ 
		public static final String BI_ZX_KL_GR_GSXYBG = Global.getIdsConfig("ids.billing.zx.kl.gr.gsxybg");
		/*征信-考拉-个人- 考拉个人金融交易信用报告   */ 
		public static final String BI_ZX_KL_GR_JRJYXYBG = Global.getIdsConfig("ids.billing.zx.kl.gr.jrjyxybg");
		/*征信-考拉-个人- 考拉个人信用分   */ 
		public static final String BI_ZX_KL_GR_KLGRXYF = Global.getIdsConfig("ids.billing.zx.kl.gr.klgrxyf");

		/*征信-考拉-个人- 机动车核验报告   */ 
		public static final String BI_ZX_KL_GR_JDCHYBG = Global.getIdsConfig("ids.billing.zx.kl.gr.jdchybg");
		/*征信-考拉-个人-驾驶员核验报告 */
		public static final String BI_ZX_KL_GR_JSYHYBG = Global.getIdsConfig("ids.billing.zx.kl.gr.jsyhybg");
		/*征信-考拉-个人-手机号核验-电信  */
		public static final String BI_ZX_KL_GR_SJHHYDX = Global.getIdsConfig("ids.billing.zx.kl.gr.sjhhydx");
		/*征信-考拉-个人-手机号核验-联通  */
		public static final String BI_ZX_KL_GR_SJHHYLT = Global.getIdsConfig("ids.billing.zx.kl.gr.sjhhylt");
		/*征信-考拉-个人-手机号核验-移动  */
		public static final String BI_ZX_KL_GR_SJHHYYD = Global.getIdsConfig("ids.billing.zx.kl.gr.sjhhyyd");
		/*征信-考拉-个人-多维身份核验报告  */
		public static final String BI_ZX_KL_GR_DWSFHY = Global.getIdsConfig("ids.billing.zx.kl.gr.dwsfhy");
		/*征信-考拉-个人-生物识别验证  */
		public static final String BI_ZX_KL_GR_SWSBYZ = Global.getIdsConfig("ids.billing.zx.kl.gr.swsbyz");
		/*征信-考拉-个人-人像识别验证   */
		public static final String BI_ZX_KL_GR_RXSBYZ = Global.getIdsConfig("ids.billing.zx.kl.gr.rxsbyz");
		
		
		/*征信-鹏元   */
		public static final String BI_ZX_PY = Global.getIdsConfig("ids.billing.zx.py");
		/*征信-鹏元-企业   */
		public static final String BI_ZX_PY_QY = Global.getIdsConfig("ids.billing.zx.py.qy");
		/*征信-鹏元-企业 -名称规范化*/
		public static final String BI_ZX_PY_QY_MCGFH = Global.getIdsConfig("ids.billing.zx.py.qy.mcgfh");
		/*征信-鹏元-企业 -企业电话反查 */
		public static final String BI_ZX_PY_QY_DHFC = Global.getIdsConfig("ids.billing.zx.py.qy.dhfc");
		/*征信-鹏元-企业 -商户经营分析  */
		public static final String BI_ZX_PY_QY_SHJYFX = Global.getIdsConfig("ids.billing.zx.py.qy.shjyfx");
		
		/*征信-鹏元-企业 - 行业景气指数  */
		public static final String BI_ZX_PY_QY_HYJQZS = Global.getIdsConfig("ids.billing.zx.py.qy.hyjqzs");
		/*征信-鹏元-企业 -企业经营分析  */
		public static final String BI_ZX_PY_QY_QYJYFX = Global.getIdsConfig("ids.billing.zx.py.qy.qyjyfx");
		/*征信-鹏元-企业 -企业经营指数  */
		public static final String BI_ZX_PY_QY_QYJYZS = Global.getIdsConfig("ids.billing.zx.py.qy.qyjyzs");
		/*征信-鹏元-企业 - 企业贷后监控 */
		public static final String BI_ZX_PY_QY_QYDHJK = Global.getIdsConfig("ids.billing.zx.py.qy.qydhjk");
		/*征信-鹏元-企业 -企业风险详细信息*/
		public static final String BI_ZX_PY_QY_QYFXXXXX = Global.getIdsConfig("ids.billing.zx.py.qy.qyfxxxxx");
		/*征信-鹏元-企业 - 企业风险概要信息 */
		public static final String BI_ZX_PY_QY_QYFXGYXX = Global.getIdsConfig("ids.billing.zx.py.qy.qyfxgyxx");
		/*征信-鹏元-企业 - 企业风险汇总信息  */
		public static final String BI_ZX_PY_QY_QYFXHZXX = Global.getIdsConfig("ids.billing.zx.py.qy.qyfxhzxx");
		/*征信-鹏元-企业 - 企业信用报告  */
		public static final String BI_ZX_PY_QY_QYXYBG = Global.getIdsConfig("ids.billing.zx.py.qy.qyxybg");
		/*征信-鹏元-个人  */
		public static final String BI_ZX_PY_GR = Global.getIdsConfig("ids.billing.zx.py.gr");
		
		/*征信-前海   */
		public static final String BI_ZX_QH = Global.getIdsConfig("ids.billing.zx.qh");
		/*征信-前海 -企业  */
		public static final String BI_ZX_QH_QY = Global.getIdsConfig("ids.billing.zx.qh.qy");
		/*征信-前海 -个人*/
		public static final String BI_ZX_QH_GR = Global.getIdsConfig("ids.billing.zx.qh.gr");
		/*征信-前海 -个人-好信法院通-基础班*/
		public static final String BI_ZX_QH_GR_HXFYTJCB = Global.getIdsConfig("ids.billing.zx.qh.gr.hxfytjcb");
		/*征信-前海 -个人-好信手机综合咨询*/
		public static final String BI_ZX_QH_GR_HXSJZHZX = Global.getIdsConfig("ids.billing.zx.qh.gr.hxsjzhzx");
		/*征信-前海 -个人- 好信银行卡咨询 */
		public static final String BI_ZX_QH_GR_HXYHKZX = Global.getIdsConfig("ids.billing.zx.qh.gr.hxyhkzx");
		/*征信-前海 -个人- 好信银行卡评分 */
		public static final String BI_ZX_QH_GR_HXYHKPF = Global.getIdsConfig("ids.billing.zx.qh.gr.hxyhkpf");
		/*征信-前海 -个人- 好信租车分 */
		public static final String BI_ZX_QH_GR_HXZCF = Global.getIdsConfig("ids.billing.zx.qh.gr.hxzcf");
		/*征信-前海 -个人-好信驾驶分 */
		public static final String BI_ZX_QH_GR_HXJSF = Global.getIdsConfig("ids.billing.zx.qh.gr.hxjsf");
		/*征信-前海 -个人- 好信信用轨迹 */
		public static final String BI_ZX_QH_GR_HXXYGJ = Global.getIdsConfig("ids.billing.zx.qh.gr.hxxygj");

		/*征信-前海 -个人- 好信一鉴通（房产验证） */
		public static final String BI_ZX_QH_GR_HXYJTFCYZ = Global.getIdsConfig("ids.billing.zx.qh.gr.hxyjtfcyz");
		/*征信-前海 -个人-  好信一鉴通（工作单位验证）  */
		public static final String BI_ZX_QH_GR_HXYJTGZDWYZ = Global.getIdsConfig("ids.billing.zx.qh.gr.hxyjtgzdwyz");
		/*征信-前海 -个人-  好信一鉴通（人脸识别）    */
		public static final String BI_ZX_QH_GR_HXYJTRLSB = Global.getIdsConfig("ids.billing.zx.qh.gr.hxyjtrlsb");
		/*征信-前海 -个人-  好信一鉴通（驾驶证状态验证）    */
		public static final String BI_ZX_QH_GR_HXYJTJSZZTYZ = Global.getIdsConfig("ids.billing.zx.qh.gr.hxyjtjszztyz");
		/*征信-前海 -个人- 好信一鉴通（车辆验证）     */
		public static final String BI_ZX_QH_GR_HXYJTCLYZ = Global.getIdsConfig("ids.billing.zx.qh.gr.hxyjtclyz");
		/*征信-前海 -个人- 好信一鉴通（关系人验证）     */
		public static final String BI_ZX_QH_GR_HXYJTGXRYZ = Global.getIdsConfig("ids.billing.zx.qh.gr.hxyjtgxryz");
		/*征信-前海 -个人- 好信一鉴通（移动手机验证）      */
		public static final String BI_ZX_QH_GR_HXYJTYDSJYZ = Global.getIdsConfig("ids.billing.zx.qh.gr.hxyjtydsjyz");
		/*征信-前海 -个人- 好信一鉴通（非移动手机验证）      */
		public static final String BI_ZX_QH_GR_HXYJTFYDSJYZ = Global.getIdsConfig("ids.billing.zx.qh.gr.hxyjtfydsjyz");
		/*征信-前海 -个人- 好信一鉴通（身份验证）      */
		public static final String BI_ZX_QH_GR_SFYZ = Global.getIdsConfig("ids.billing.zx.qh.gr.sfyz");
		/*征信-前海 -个人- 好信欺诈度提示      */
		public static final String BI_ZX_QH_GR_HXQZDTS = Global.getIdsConfig("ids.billing.zx.qh.gr.hxqzdts");
		/*征信-前海 -个人-好信地址通    */
		public static final String BI_ZX_QH_GR_HXDZT = Global.getIdsConfig("ids.billing.zx.qh.gr.hxdzt");
		/*征信-前海 -个人-好信常贷客-专业版     */
		public static final String BI_ZX_QH_GR_HXCDKZYB = Global.getIdsConfig("ids.billing.zx.qh.gr.hxcdkzyb");
		/*征信-前海 -个人- 好信度-基础版     */
		public static final String BI_ZX_QH_GR_HXCDKJCB = Global.getIdsConfig("ids.billing.zx.qh.gr.hxcdkjcb");
		/*征信-前海 -个人-好信风险度提示-专业版     */
		public static final String BI_ZX_QH_GR_HXFXDTSZYB = Global.getIdsConfig("ids.billing.zx.qh.gr.hxfxdtszyb");
		/*征信-前海 -个人-风险度提示2.0*/
		public static final String BI_ZX_QH_GR_FXDTS2 = Global.getIdsConfig("ids.billing.zx.qh.gr.fxdts2");
		/*征信-前海 -个人-好信度数据*/
		public static final String BI_ZX_QH_GR_HXDSJ = Global.getIdsConfig("ids.billing.zx.qh.gr.hxdsj");
		/*征信-前海 -个人-地址通数据*/
		public static final String BI_ZX_QH_GR_DZTSJ = Global.getIdsConfig("ids.billing.zx.qh.gr.dztsj");

		/*风控模型 */
		public static final String BI_FKMX_FKMX = Global.getIdsConfig("ids.billing.fkmx");
		/*风控模型-计算信息校验*/  
		public static final String BI_FKMX_JSXXJY = Global.getIdsConfig("ids.billing.fkmx.jsxxjy");
		/*风控模型-计算单个项目期望损失 */
		public static final String BI_FKMX_JSDGXMQWSS = Global.getIdsConfig("ids.billing.fkmx.jsdgxmqwss");
		/*风控模型-盈利分析  */
		public static final String BI_FKMX_YLFX = Global.getIdsConfig("ids.billing.fkmx.ylfx");
		/*风控模型-行业景气 指数 */
		public static final String BI_FKMX_HYJQZS = Global.getIdsConfig("ids.billing.fkmx.hyjqzs");
		/*风控模型-评级  */
		public static final String BI_FKMX_PJ = Global.getIdsConfig("ids.billing.fkmx.pj");

		/*风控模型-因子分解、标准化  */
		public static final String BI_FKMX_YZFJBZH = Global.getIdsConfig("ids.billing.fkmx.yzfjbzh");
		/*风控模型-计算组合项目损失概率分布  */
		public static final String BI_FKMX_JSZHXMSSGLFB = Global.getIdsConfig("ids.billing.fkmx.jszhxmssglfb");
		/*风控模型-压力测试  */
		public static final String BI_FKMX_YLCS = Global.getIdsConfig("ids.billing.fkmx.ylcs");
		/*风控模型-风险分布展望  */
		public static final String BI_FKMX_FXFBZW = Global.getIdsConfig("ids.billing.fkmx.fxfbzw");
		/*风控模型- 预期收入  */
		public static final String BI_FKMX_YQSR = Global.getIdsConfig("ids.billing.fkmx.yqsr");
		
		/*物流授信  */
		public static final String BI_WLSX = Global.getIdsConfig("ids.billing.wlsx");
		/*物流授信 -加权授信额度  */
		public static final String BI_WLSX_JQSXED = Global.getIdsConfig("ids.billing.wlsx.jqsxed");
		/*物流授信 -权重  */
		public static final String BI_WLSX_QZ = Global.getIdsConfig("ids.billing.wlsx.qz");
		/*物流授信 - 每公里授信额度 */
		public static final String BI_WLSX_MGLSXED = Global.getIdsConfig("ids.billing.wlsx.mglsxed");
		/*物流授信 - 里程预测 */
		public static final String BI_WLSX_LCYC = Global.getIdsConfig("ids.billing.wlsx.lcyc");
		

		/*专家模型   */
		public static final String BI_ZJMX = Global.getIdsConfig("ids.billing.zjmx");
		/*专家模型 - 加权信用分级 */
		public static final String BI_ZJMX_JQXYFJ = Global.getIdsConfig("ids.billing.wlsx.jqxyfj");
		/*专家模型 - 计数型指标分级 */
		public static final String BI_ZJMX_JSXZBFJ = Global.getIdsConfig("ids.billing.wlsx.jsxzbfj");
		/*专家模型 -计量型指标模糊分级 */
		public static final String BI_ZJMX_JLXZBMHFJ = Global.getIdsConfig("ids.billing.wlsx.jlxzbmhfj");

		
		/*金融景气指数模型  */
		public static final String BI_JRJQZSMX = Global.getIdsConfig("ids.billing.jrjqzsmx");
		/*金融景气指数模型  - 合成指数算法*/
		public static final String BI_JRJQZSMX_HCZSSF = Global.getIdsConfig("ids.billing.jrjqzsmx.hczssf");
		/*金融景气指数模型  - 领先指标同步指标选择 */
		public static final String BI_JRJQZSMX_LXZBTBZBXZ = Global.getIdsConfig("ids.billing.jrjqzsmx.lxzbtbzbxz");
		
		/*金融景气指数模型  -  季节性调整算法 */
		public static final String BI_JRJQZSMX_JJXTZSF = Global.getIdsConfig("ids.billing.jrjqzsmx.jjxtzsf");
	}
	
	public final static class BiElementType{
		/**
		 *  征信
		 */
		public static final Integer BI_EZC = 1;
		/**
		 * 短信
		 */
		public static final Integer BI_EDX = 2;
		/**
		 * 平台
		 */
		public static final Integer BI_EPT = 3;
		/**
		 * 风控
		 */
		public static final Integer BI_EFK = 4;
	}
	 
	public final static class BiType{
		/**
		 * 时间计费：1
		 * 数量计费：2
		 */
		public final static Integer TIME = 1;
		public final static Integer NUM = 2;
	}

	public final static class BiCollectStatus{
		/**
		 * 服务中：1
		 * 停止服务：2
		 * 暂停使用：3
		 */
		public final static Integer SERVER_ING = 1;
		public final static Integer SERVER_END = 2;
	}
}

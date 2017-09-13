/*
 *  Copyright 2014-2015 snakerflow.com
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.wanfin.fpd.app;




import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.wanfin.fpd.app.ScriptsPlugin;
import com.wanfin.fpd.modules.dfform.model.Field;
import com.wanfin.fpd.modules.dfform.model.Form;
import com.wanfin.fpd.modules.dfform.web.FormController;

/**
 * 搴旂敤鎬婚厤缃�
 * @author yuqs
 * @since 0.1
 */
public class AppConfig extends JFinalConfig {
	 @Override  
	    public void configConstant(Constants me) {  
		 loadPropertyFile("jeesite.properties");	 
	
			me.setDevMode(getPropertyToBoolean("devMode", false));
//		    me.setError404View("/common/404.jsp");
//		    me.setError500View("/common/500.jsp");
		    
		    me.setViewType(ViewType.JSP);
		    me.setBaseViewPath("WEB-INF/views/");  
	    }  
	  
	    @Override  
	    public void configRoute(Routes me) {  
	    	
	    	me.add("/a/dfform/form", FormController.class, "/modules/dfform");
//			me.add("/config/field", FieldController.class, "/config");
//	        me.add("/dfform/form", FormController.class);
	
	        
	    }  
	  
	    @Override  
	    public void configPlugin(Plugins me) { 
	    	C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbc.url"), getProperty("jdbc.username"), getProperty("jdbc.password").trim());
			me.add(c3p0Plugin);
			
			ScriptsPlugin scriptsPlugin = new ScriptsPlugin(c3p0Plugin);
			me.add(scriptsPlugin);
			
			// 閰嶇疆ActiveRecord鎻掍欢
			ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
			me.add(arp);
		
			
			arp.addMapping("df_form", Form.class);
			arp.addMapping("df_field", Field.class);
	    }  
	  
	    @Override  
	    public void configInterceptor(Interceptors me) {  
	    }  
	  
	    @Override  
	    public void configHandler(Handlers me) {  
	    }  
}

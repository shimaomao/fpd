/**  
 * @Project fpd 
 * @Title SwaggerConfig.java
 * @Package com.wanfin.fpd.core
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年6月13日 上午10:55:14 
 * @version V1.0   
 */ 
package com.wanfin.fpd.core.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

/**
 * @Description [[_xxx_]] SwaggerConfig类
 * @author Chenh
 * @date 2016年6月13日 上午10:55:14 
 */
@EnableWebMvc
@Configuration
@EnableSwagger
public class SwaggerConfig{
	@Autowired
	private SpringSwaggerConfig springSwaggerConfig;

    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig){
        this.springSwaggerConfig = springSwaggerConfig;
    }
    public SpringSwaggerConfig getSpringSwaggerConfig() {
		return springSwaggerConfig;
	}

	/**
     * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc
     * framework - allowing for multiple swagger groups i.e. same code base
     * multiple swagger resource listings.
     */
    @Bean
    public SwaggerSpringMvcPlugin customImplementation(){
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
        .apiInfo(apiInfo())
        .includePatterns(".*?")
        .swaggerGroup("api");
    }

    private ApiInfo apiInfo(){
        ApiInfo apiInfo = new ApiInfo(
                "B端Api平台!",
                "Welcome!",
                "/",
                "开发者邮箱",
                "Apache 2.0协议",
                "http://www.apache.org/licenses/LICENSE-2.0.html");
        return apiInfo;
    }
}

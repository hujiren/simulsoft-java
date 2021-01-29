package com.apl.stimulsoft;

import com.stimulsoft.web.servlet.StiWebResourceServlet;
import com.stimulsoft.webdesigner.servlet.StiWebDesignerActionServlet;
import com.stimulsoft.webviewer.servlet.StiWebViewerActionServlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
@SpringBootApplication(scanBasePackages = {
		"com.apl.stimulsoft"
//		"com.apl.jmreport",
//		"com.apl.lib", //APL基本工具类
//		"com.apl.tenant", //多租户
//		"com.apl.db.adb", // adb数据库操作助手
//		"com.apl.cache", // redis代理
},
		exclude= {DataSourceAutoConfiguration.class})
public class WebdesignerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebdesignerApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean stiWebResourceServlet(){
		ServletRegistrationBean bean = new ServletRegistrationBean(new StiWebResourceServlet());
		bean.addUrlMappings("/stimulsoft_web_resource/*");
		return bean;
	}

	@Bean
	public ServletRegistrationBean stiWebDesignerActionServlet(){
		ServletRegistrationBean bean = new ServletRegistrationBean(new StiWebDesignerActionServlet());
		bean.addUrlMappings("/stimulsoft_webdesigner_action");
		return bean;
	}

	@Bean
	public ServletRegistrationBean stiWebViewerActionServlet(){
		ServletRegistrationBean bean = new ServletRegistrationBean(new StiWebViewerActionServlet());
		bean.addUrlMappings("/stimulsoft_webviewer_action");
		return bean;
	}
}

package com.dubbo.druidconfig;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfig {


    /**
     * 注册StatViewServlet
     * @return
     */

    @Bean
    public ServletRegistrationBean  registrationBean() {
        //添加初始化参数
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        //白名单
        bean.addInitParameter("allow", "127.0.0.1");
        //黑名单
//        bean.addInitParameter("deny", "");
        bean.addInitParameter("loginUsername", "admin");
        bean.addInitParameter("loginPassword", "123456");
        bean.addInitParameter("resetEnable", "false");
        return bean;
    }

    /**
     * 注册WebStatFilter
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new WebStatFilter());

        //添加过滤规则
        bean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息
        bean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid-stats/*");
        return bean;
    }
}

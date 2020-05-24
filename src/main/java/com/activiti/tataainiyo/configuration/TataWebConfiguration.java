package com.activiti.tataainiyo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;
//@EnableWebMvc
//@Configuration
public class TataWebConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor()).addPathPatterns("/**");
    }


    @Bean("interceptor")
    public HandlerInterceptor interceptor(){
        return new HandlerInterceptor(){
            //业务处理前
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                System.out.println("=======start:");
                Enumeration<String> headerNames = request.getHeaderNames();
                while (headerNames.hasMoreElements()){
                    String s = headerNames.nextElement();
                    System.out.println( s+":"+request.getHeader(s));
                }
                return true;
            }
            //业务处理结束后
            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                System.out.println("=======post:");
                Collection<String> headerNames = response.getHeaderNames();
                headerNames.forEach(e->{
                    String header = response.getHeader(e);
                    System.out.println(e+":"+header);
                });
            }
            //DispatcherServlet处理完成后（用于释放资源）
            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
                System.out.println("=======after:");
                Collection<String> headerNames = response.getHeaderNames();
                headerNames.forEach(e->{
                    String header = response.getHeader(e);
                    System.out.println(e+":"+header);
                });
            }
        };
    }

}

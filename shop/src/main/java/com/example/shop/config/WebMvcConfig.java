package com.example.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
* 업로드 이미지 경로 설정
* */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // properties에 설정한 "uploadPath"프로퍼티 값 읽어옴
    @Value("${uploadPath}")
     String uploadPath;

    @Override
    // addResourceHandlers() 메소드를 통해 자신의 로컬 컴퓨터에 업로드한 파일을 찾을 위치를 설정
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")  ///'images'로 시작하는 url의 경우 설정한 폴더를 기준으로 파일을 읽어옴
                .addResourceLocations(uploadPath);  // 로컬에 저장된 파일을 읽어올 root 경로를 설정
    }
}

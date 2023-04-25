package com.capstone.project.config.swagger;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.swagger.annotations.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Configuration
public class SwaggerConfig  {

    //리소스 핸들러 설
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }

    // API마다 구분짓기 위한 설정.
//    @Bean
//    public Docket productApi() {
//        return getDocket("관리자", Predicates.or(
//                PathSelectors.regex("/admin.*")));
//    }

    @Bean
    public Docket api(TypeResolver typeResolver) {
        return new Docket(DocumentationType.OAS_30)
               // .additionalModels(typeResolver.resolve(MemberResponseDto.class))
                //.additionalModels(typeResolver.resolve(ErrorResponse.class))
                .securityContexts(Arrays.asList(securityContext())) // 추가
                .securitySchemes(Arrays.asList(apiKey())) // 추가
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.capstone.project.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .globalResponseMessage(RequestMethod.GET, getCustomizedResponseMessages())
                .globalResponseMessage(RequestMethod.POST, getCustomizedResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, getCustomizedResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, getCustomizedResponseMessages())
                .globalResponseMessage(RequestMethod.PATCH, getCustomizedResponseMessages());
    }

    // 추가
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    // 추가
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", (springfox.documentation.service.AuthorizationScope[]) authorizationScopes));
    }

    // 추가
    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Backend API")
                .description("Backend API 문서")
                .version("1.0")
                .build();
    }
//
//    private Object securityContext() {
//        return null;
//    }
//
//
//    @Bean
//    public Docket searchApi() {
//        return getDocket("배송기사", Predicates.or(
//                PathSelectors.regex("/delivery.*")));
//    }
//
//
//    @Bean
//    public Docket commonApi() {
//        return getDocket("사용자", Predicates.or(
//                PathSelectors.regex("/user.*")));
//    }
//
//    @Bean
//    public Docket allApi() {
//        return getDocket("전체", Predicates.or(
//                PathSelectors.regex("/*.*")));
//    }
//    //swagger 설정.
//    public Docket getDocket(String groupName, Predicate<String> predicate) {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName(groupName)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.capstone.project.controller"))
//                .paths(predicate)
//                .apis(RequestHandlerSelectors.any())
//                .build()
//                .globalResponseMessage(RequestMethod.GET, getCustomizedResponseMessages())
//                .globalResponseMessage(RequestMethod.POST, getCustomizedResponseMessages())
//                .globalResponseMessage(RequestMethod.DELETE, getCustomizedResponseMessages())
//                .globalResponseMessage(RequestMethod.PUT, getCustomizedResponseMessages())
//                .globalResponseMessage(RequestMethod.PATCH, getCustomizedResponseMessages());
//    }
//
    private List<ResponseMessage> getCustomizedResponseMessages() {
        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder().code(200).message("성공").build());
        responseMessages.add(new ResponseMessageBuilder().code(204).message("데이터 미존재").build());
        responseMessages.add(new ResponseMessageBuilder().code(400).message("입력값 오류").build());
        responseMessages.add(new ResponseMessageBuilder().code(401).message("미 로그인").build());
        responseMessages.add(new ResponseMessageBuilder().code(403).message("권한없음").build());
        responseMessages.add(new ResponseMessageBuilder().code(412).message("처리중 오류").build());
        responseMessages.add(new ResponseMessageBuilder().code(500).message("서버에러").build());
        return responseMessages;
    }
//
//    //swagger ui 설정.
//    @Bean
//    public UiConfiguration uiConfig() {
//        return UiConfigurationBuilder.builder()
//                .displayRequestDuration(true)
//                .validatorUrl("")
//                .build();
//    }

}

package hello.servlet;

import hello.servlet.web.springmvc.v1.SpringMemberFormControllerV1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ServletComponentScan    //서블릿 자동 등록
@SpringBootApplication
public class ServletApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServletApplication.class, args);

    }


	// application.properties에서 prefix와 suffix를 설정해주면 spring에서 자동으로 등록을 해준다.
//    @Bean
//    ViewResolver internalResourceViewResolver() {
//		return new InternalResourceViewResolver("/WEB-INF/views", ".jsp");
//	}


    //컴포넌트 스캔 없이 스프링 빈으로 직접 등록
//    @Bean
//    SpringMemberFormControllerV1 springMemberFormControllerV1() {
//        return new SpringMemberFormControllerV1();
//    }

}

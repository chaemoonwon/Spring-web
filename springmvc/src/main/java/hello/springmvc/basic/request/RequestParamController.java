package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Slf4j
//@Controller
@RestController
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {

        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    // 매개변수 파싱이 되지 않는 문제 발생
    /**
     * 문제 원인
     * 참고로 이 문제는 Build, Execution, Deployment Build Tools Gradle에서
     * Build and run using를 IntelliJ IDEA로 선택한 경우에만 발생한다.
     * Gradle로 선택한 경우에는 Gradle이 컴파일 시점에 해당 옵션을 자동으로 적용해준다.
     * 자바를 컴파일할 때 매개변수 이름을 읽을 수 있도록 남겨두어야 사용할 수 있다. 컴파일 시점에 -parameters 옵션
     * 을 사용하면 매개변수 이름을 사용할 수 있게 남겨둔다.
     * 스프링 부트 3.2 전까지는 바이트코드를 파싱해서 매개변수 이름을 추론하려고 시도했다. 하지만 스프링 부트 3.2 부터
     * 는 이런 시도를 하지 않는다
     */
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) {
        //int age != null => X
        //Integer age == null => O
        //Parameter 값으로 값이 없을 경우 빈 문자열로 값이 들어옴.(http://localhost:8080/request-param-required?&username)
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") Integer age) {

        //defaultValue는 required 값과 상관없이 값이 없을 경우에도 기본적으로 적용되어 있는 값이기 때문에
        //Parmeter값이 없어도 성립함.(빈 문자 포함)
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @RequestMapping("/request-param-map")
    public String requestParamMap(
            @RequestParam Map<String, Object> paramMap) {

        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }


    //  HTTP 요청 파라미터 - @ModelAttribute 적용 전

//    @ResponseBody
//    @RequestMapping("/model-attribute-v1")
//    public String modelAttributeV1(
//            @RequestParam String username,
//            @RequestParam Integer age
//    ){
//        HelloData data = new HelloData();
//        data.setUsername(username);
//        data.setAge(age);
//        log.info("username ={} ", data.getUsername());
//        log.info("age ={} ", data.getAge());
//
//        return "ok";
//    }

    //HTTP 요청 파라미터 - @ModelAttribute 적용 후
    //getter와 setter의 자동화
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(
           @ModelAttribute HelloData data
            ){
        log.info("username ={} ", data.getUsername());
        log.info("age ={} ", data.getAge());

        return "ok";
    }

    //@ModelAttribute도 생략가능
    //String , int , Integer 같은 단순 타입 = @RequestParam을 사용
    //나머지 @ModelAttribute
    //@ModelAttribute는 객체를 사용해서 값을 받을 수 있다는 점에서 사용성이 있음.
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData data
    ){
        log.info("username ={} ", data.getUsername());
        log.info("age ={} ", data.getAge());

        return "ok";
    }

}

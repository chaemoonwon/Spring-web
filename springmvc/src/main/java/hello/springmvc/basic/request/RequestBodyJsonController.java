package hello.springmvc.basic.request;


import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * {"username":"hello", "age":20}
 * content-type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream input = request.getInputStream();
        String messageBody = StreamUtils.copyToString(input, StandardCharsets.UTF_8);

        log.info("messageBody ={}", messageBody); //messageBody에 요청 받은 데이터 담기
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("helloData ={}", helloData);
        log.info("username ={}, age ={}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }


    /**
     * @RequestBody
     * HttpMessageConverter 사용 -> StringHttpMessageConverter 적용 => (content-Type에 맞춰서)문자를 객체로 변환
     *
     * @ResponseBody
     * - 모든 메서드에 @ResponseBody 적용
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        log.info("messageBody ={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("helloData ={}", helloData);
        log.info("username ={}, age ={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
     * @RequestBody
     * HttpMessageConverter 사용 -> StringHttpMessageConverter 적용 => (content-Type에 맞춰서)문자를 객체로 변환
     /*
     생략 불가능 => ModelAttribute가 적용되기 때문에 =>객체만 생성되고 요청 값이 전달 되지 않음
    HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type:
    application/json)
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(HelloData data) throws IOException {
        log.info("username ={}, age ={}", data.getUsername(), data.getAge());

        return "ok";
    }

    //requestBodyJsonV4 - HttpEntity
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> data) {
        HelloData body = data.getBody();
        log.info("username ={}, age ={}", body.getUsername(), body.getAge());

        return "ok";
    }


    /*
    * @ResponseBody
응답의 경우에도 @ResponseBody 를 사용하면 해당 객체를 HTTP 메시지 바디에 직접 넣어줄 수 있다.
물론 이 경우에도 HttpEntity 를 사용해도 된다.
* */

    // 객체가 요청할 때 Json으로 변환되어서, 응답할 때도 Json으로 응답이 됨.
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data){
        log.info("username ={}, age ={}", data.getUsername(), data.getAge());

        return data;
    }

}

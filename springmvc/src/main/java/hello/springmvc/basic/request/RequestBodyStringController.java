package hello.springmvc.basic.request;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    //Http 메세지 - 서블릿
    //HttpServletRequest request, HttpServletResponse response을 사용해서
    //inputStream을 받아서 messageBody에 결과 출력
    @PostMapping("/request-body-string-v1")
    public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        response.getWriter().write("ok");
    }

    //Http 메세지 - inputStream
/**
 * InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회
 * OutputStream(Writer): HTTP 응답 메시지 바디에 직접 결과 출력
 */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        responseWriter.write("ok");

    }

    /**
     * HttpEntity: HTTP header, body 정보를 편리하게 조회
     * - 메시지 바디 정보를 직접 조회
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     * - 요청 파라미터 전달과 관련이 없음(@RequestParam X, @ModelAttribute X)
     *
     * 응답에서도 HttpEntity 사용 가능
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */
//    @PostMapping("/request-body-string-v3")
//    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
//
//        String messageBody = httpEntity.getBody();
//        log.info("messageBody={}", messageBody);
//
//        return new HttpEntity<>("ok");
//
//    }

    /*
    *
HttpEntity 를 상속받은 다음 객체들도 같은 기능을 제공한다.
1. RequestEntity
HttpMethod, url 정보가 추가, 요청에서 사용
2. ResponseEntity
HTTP 상태 코드 설정 가능, 응답에서 사용
return new ResponseEntity<String>("Hello World", responseHeaders,
HttpStatus.CREATED)
* */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(RequestEntity<String> httpEntity){

        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);

        //ResponseEntity를 이용해 응답시 상태 코드(HttpStatus) 확인 가능.
        return new ResponseEntity<>("ok", HttpStatus.CREATED);

    }

    //@ResponseBody를 이용해 직접 messageBody 응답을 담아서 전달 할 수 있음.
    //@RequestBody를 Http messageBody 정보를 조회할 수 있음
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {

        log.info("messageBody={}", messageBody);

        return "ok";
    }




}

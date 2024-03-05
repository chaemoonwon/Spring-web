package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Slf4j
@RestController
public class RequestHeaderController {


    /**
     *
     * HttpServletRequest
     * HttpServletResponse
     * HttpMethod : HTTP 메서드를 조회한다. org.springframework.http.HttpMethod
     * Locale : Locale 정보를 조회한다.
     * @RequestHeader MultiValueMap<String, String> headerMap
     * 모든 HTTP 헤더를 MultiValueMap 형식(같은 키의 여러 개의 값)으로 조회한다.
     */
    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,
                          Locale locale,
                          @RequestParam MultiValueMap<String, String> headerMap,
                          @RequestHeader("host") String host,
                          @CookieValue(value = "myCookie", required = false) String cookie) {

        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);

        return "ok";
    }

}

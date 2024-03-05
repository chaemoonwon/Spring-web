package hello.springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller 는 반환 값이 String 이면 뷰 이름으로 인식된다. 그래서 뷰를 찾고 뷰가 랜더링 된다
//@RestController 는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력한다.
@RestController
public class LogTestController {
    //로그 선언
    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    private String logTest() {
        String name = "Spring";

        //요청 시 어떠한 레벨에서도 모두 출력이 되므로, 사용해서는 안 됨.
        System.out.println("name = " + name);

        //파라미터 X 출력하는 방식
        log.info("info log =" +name);
//        log.trace("trace log =", name); //출력 되지는 않지만, +연산이 일어남 => 불필요한 리소스를 사용하게 됨.



        // 로그 레벨 설정
        // LEVEL: TRACE > DEBUG > INFO > WARN > ERROR
        // 파라미터 O 출력하는 방식
        log.trace("trace log={}", name);
        log.debug("debug log={}", name);    //개발 서버에서 주로 사용

        // info 레벨로 요청 시 모두 출력
        log.info("Info log={}", name);      //운영 서버에서 주로 사용
        log.warn("warn log={}", name);
        log.error("error log={}", name);

        // 로그로 남길 때의 장점
        // System.out콘솔에만 출력하는 것이 아니라, 파일이나 네트워크 등, 로그를 별도의 위치에 남길 수 있다.
        // 특히 파일로 남길 때는 일별, 특정 용량에 따라 로그를 분할하는 것도 가능하다.

        return "ok";
    }

}

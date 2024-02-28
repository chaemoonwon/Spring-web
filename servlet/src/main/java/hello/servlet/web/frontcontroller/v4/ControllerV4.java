package hello.servlet.web.frontcontroller.v4;

import java.util.Map;

public interface ControllerV4 {

// /**을 입력하면 자동 입력됨.
    /**
     *
     * @param paramMap
     * @param model
     * @return
     */
    String process(Map<String, String> paramMap, Map<String, Object> model);
}

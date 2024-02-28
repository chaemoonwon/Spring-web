package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
* - Model 추가
* 서블릿 종속성 제거
* view 경로의 중복 제거
* */
@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerV3Map = new HashMap<>();

    public FrontControllerServletV3(){
        controllerV3Map.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerV3Map.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerV3Map.put("/front-controller/v3/members", new MemberListControllerV3());


    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service");

        String requestURI = request.getRequestURI();

        ControllerV3 controllerV3 = controllerV3Map.get(requestURI);
        if (controllerV3 == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //paramMap 값 가져오기
        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controllerV3.process(paramMap);

        String viewName = mv.getViewName(); //논리 이름 가져오기
        MyView view = viewResolver(viewName);

        //Model에 값을 넣어서 렌더링 => 화면에 결과를 응답
        view.render(mv.getModel(), request, response);
    }

    //논리 이름을 가져와서 Myview로 결과를 반환 => viewResolver
    //물리 이름과 논리 이름을 나눔.
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    // ParamMap 생성 => Model값 가져오기
    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap= new HashMap<>();

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}

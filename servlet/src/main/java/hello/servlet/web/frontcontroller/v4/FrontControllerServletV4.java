package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
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
@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerV4Map = new HashMap<>();

    public FrontControllerServletV4(){
        controllerV4Map.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerV4Map.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerV4Map.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service");

        String requestURI = request.getRequestURI();

        ControllerV4 controllerV4 = controllerV4Map.get(requestURI);
        if (controllerV4 == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }


        Map<String, String> paramMap = createParamMap(request); //paramMap 값 가져오기
        Map<String, Object> model = new HashMap<>();        //model 값 가져오기

        String viewName = controllerV4.process(paramMap, model);

        MyView view = viewResolver(viewName);
        //Model에 값을 넣어서 렌더링 => 화면에 결과를 응답
        view.render(model, request, response);
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

// package com.ynthm.springbootdemo.handler;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.web.servlet.error.ErrorAttributes;
// import org.springframework.boot.web.servlet.error.ErrorController;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.context.request.ServletWebRequest;
// import org.springframework.web.context.request.WebRequest;
//
// import javax.servlet.http.HttpServletRequest;
// import java.util.Map;
//
/// **
// * Author : Ynthm
// */
// @RestController
// public class BaseErrorRestController implements ErrorController {
//    private static final String PATH = "/error";
//
//    @Autowired
//    private ErrorAttributes errorAttributes;
//
//
//    @Override
//    public String getErrorPath() {
//        return PATH;
//    }
//
//    @RequestMapping
//    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
//        Map<String, Object> body = this.getErrorAttributes(request, true);
//        HttpStatus status = this.getStatus(request);
//        return new ResponseEntity(body, status);
//    }
//
//
//    protected Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean
// includeStackTrace) {
//        WebRequest webRequest = new ServletWebRequest(request);
//        return this.errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
//    }
//
//    protected HttpStatus getStatus(HttpServletRequest request) {
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        if (statusCode == null) {
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        } else {
//            try {
//                return HttpStatus.valueOf(statusCode);
//            } catch (Exception var4) {
//                return HttpStatus.INTERNAL_SERVER_ERROR;
//            }
//        }
//    }
// }

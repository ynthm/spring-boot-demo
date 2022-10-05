// package com.ynthm.demo.webflux;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.reactive.function.server.RequestPredicates;
// import org.springframework.web.reactive.function.server.RouterFunction;
// import org.springframework.web.reactive.function.server.RouterFunctions;
// import org.springframework.web.reactive.function.server.ServerResponse;
//
/// **
// * @author Ynthm Wang
// * @version 1.0
// */
// @Configuration
// public class RouterConfiguration {
//  @Bean
//  RouterFunction<ServerResponse> userRouterFunction(UserHandler userHandler) {
//    return RouterFunctions.nest(
//        RequestPredicates.path("/user"),
//        RouterFunctions.route(RequestPredicates.GET("/"), userHandler::getAllUsers)
//            .andRoute(RequestPredicates.POST("/"), userHandler::addUser)
//            .andRoute(RequestPredicates.DELETE("/{id}"), userHandler::deleteUser));
//  }
// }

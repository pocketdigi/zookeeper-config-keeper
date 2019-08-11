package com.pocketdigi.zookeeperconfig.server.interceptor;
import java.util.regex.Matcher;
import	java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pocketdigi.zookeeperconfig.server.model.ErrorEnum;
import com.pocketdigi.zookeeperconfig.server.util.JwtUtil;
import com.pocketdigi.zookeeperconfig.server.vo.ResultVO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author fhp
 * @date 2019-08-08
 */
@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static String USER_PREFIX="/user";
    private static String ERROR_PREFIX="/error";
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtUtil jwtUtil;

    Pattern tokenPattern = Pattern.compile("Bearer ([\\S]+$)");
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if(requestURI.startsWith(USER_PREFIX)||requestURI.startsWith(ERROR_PREFIX)) {
            return true;
        }
        //取token
        String token = getToken(request);
        if(StringUtils.isBlank(token)) {
            responseUnlogin(response);
            return false;
        }
        if(!jwtUtil.validateToken(token)) {
            responseUnlogin(response);
            return false;
        }
        return super.preHandle(request, response, handler);
    }

    private String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("authorization");
        if(StringUtils.isBlank(authorization)) {
            return null;
        }
        Matcher matcher = tokenPattern.matcher(authorization);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private void responseUnlogin(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        ResultVO<Object> objectResultVO = ResultVO.wrapError(ErrorEnum.USER_UN_LOGIN);
        try(PrintWriter out=response.getWriter()) {
            String json = objectMapper.writeValueAsString(objectResultVO);
            out.append(json);
        } catch (IOException e) {
            log.error("response error",e);
        }

    }
}

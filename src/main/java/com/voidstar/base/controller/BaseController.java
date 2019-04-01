package com.voidstar.base.controller;

import com.voidstar.base.entity.StarSession;
import com.voidstar.base.entity.User;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 邹强
 * @date 19-3-12
 */
public class BaseController {
    //每页条数
    protected final String baseRows = "20";
    protected final Logger log = Logger.getLogger(getClass());
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected ModelAndView model = new ModelAndView();

    protected StarSession getSession(HttpServletRequest request) {
        StarSession session = new StarSession();
        session.setDb("voidstar");
        return session;
    }

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        model.clear();
    }

    protected User getUser() {
        User user = (User) session.getAttribute("user");
        return user;
    }

    //返回code
    protected enum returnState {
        success, fail
    }

}

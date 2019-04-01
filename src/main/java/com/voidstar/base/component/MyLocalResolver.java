package com.voidstar.base.component;
import com.sun.istack.internal.Nullable;
//import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/*
* 可以再连接上携带区域信息
* */

public class MyLocalResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String l = request.getParameter("l");
        Locale local=Locale.getDefault();
        if(!StringUtils.isEmpty(l)){
            String[] split = l.split("_");
            local= new Locale(split[0],split[1]);

        }
        return local;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, @Nullable HttpServletResponse httpServletResponse, @Nullable Locale locale) {

    }
}

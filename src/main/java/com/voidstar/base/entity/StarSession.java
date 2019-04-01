package com.voidstar.base.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author 邹强
 * @date 19-2-27
 */
@Data
public class StarSession implements Serializable {
    private String user;
    private String db;
    private String token;
    private String sso;
    private boolean ssoLogin;
    private String ssoToken;
    private String ssoClient;
    private String ssoApp;
    private String locale;
    private boolean isSystemInstance;
    private String ipAddress;
    private String client;
    private String port;
    private Map<String, Object> params;
}

package com.utils;


import com.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class ShiroUtils {
    public static Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }
    public static void setAttribute(String k,String v){
        getSession().setAttribute(k,v);

    }
    public static Object getAttribute(String k){
        return getSession().getAttribute(k);
    }
    public static String getCaptcha(){
        return getAttribute("code")+"";
    }
    public static SysUser getCurrentUser(){
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }
    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }
    public static long  getUserId(){
        return  getCurrentUser().getUserId();
    }
}

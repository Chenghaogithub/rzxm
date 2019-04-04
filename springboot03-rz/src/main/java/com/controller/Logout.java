package com.controller;

import com.utils.R;
import com.utils.ShiroUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class Logout {
/*    @RequestMapping("/logout")
    public String logout(){
        ShiroUtils.getSubject().logout();
        return "redirect:/login.html";
    }*/

    @RequestMapping("/logout")
    @ResponseBody
    public R logout(){
        ShiroUtils.getSubject().logout();
        return  R.ok();
    }
}

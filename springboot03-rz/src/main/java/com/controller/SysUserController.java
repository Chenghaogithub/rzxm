package com.controller;

import com.dto.SysUserDTO;
import com.entity.SysUser;
import com.google.code.kaptcha.Producer;
import com.service.SysUserService;

import com.utils.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.lang.String;
import javax.imageio.ImageIO;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.List;

@RestController
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private Producer producer;

    @RequestMapping("/show")
    public List<SysUser> findAll(){
        return sysUserService.findAll();
    }
    @RequestMapping("sys/login")
    public R login(@RequestBody SysUserDTO sysUserDTO){
        String code = ShiroUtils.getCaptcha();
        System.out.println(code);
        String c = sysUserDTO.getCaptcha();
        if(code!=null&&!code.equalsIgnoreCase(c)){
            return R.error("验证码错误！");
        }
       /*  传统登陆return sysUserService.login(sysUserDTO);*/

        /*shiro认证*/
        String s = null;
        try {
            Subject subject = SecurityUtils.getSubject();
            String pwd = sysUserDTO.getPassword();
            System.out.println(pwd);
            Md5Hash  md5Hash = new Md5Hash(pwd,sysUserDTO.getUsername(),1024);
            pwd = md5Hash.toString();
            System.out.println(pwd);
            UsernamePasswordToken token = new UsernamePasswordToken(sysUserDTO.getUsername(),pwd);
            //判断用户是否勾选了remember me
            if(sysUserDTO.isRememberMe()){
                token.setRememberMe(true);
            }
            subject.login(token);
            System.out.println(subject.hasRole("管理员"));
            System.out.println(subject.isPermitted("sys:menu:save"));
            return R.ok();
        }catch (Exception e){
            s=e.getMessage();
        }
        return R.error(s);
    }
    @RequestMapping("/sys/user/list")
    public ResultData findUserByPage(Pager pager, String search, Sorter sorter){
        return sysUserService.findByPage(pager,search,sorter);
    }
    @RequestMapping("/captcha.jpg")
    public void kaptcha(HttpServletResponse response){
        try {
            String text = producer.createText();
            /*SecurityUtils.getSubject().getSession().setAttribute("code",text);*/
            ShiroUtils.setAttribute("code",text);
            BufferedImage image = producer.createImage(text);
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(image,"jpg",outputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping("/sys/user/info")
    public R userInfo(){
        SysUser user = ShiroUtils.getCurrentUser();
        System.out.println(user.toString());

        return R.ok().put("user",user);
    }
    @RequestMapping("sys/user/password")
    public R updatepwd(String password,String newPassword){

        System.out.println("密码"+password);
        System.out.println("新密码"+newPassword);
        SysUser user = ShiroUtils.getCurrentUser();
        System.out.println(user.getPassword());
        //对前台传来的密码进行加密处理
        Md5Hash  md5Hash = new Md5Hash(password,user.getUsername(),1024);
        String s = md5Hash.toString();
        System.out.println(s);
        //把加密后的密码与数据库进行比较
        if (s.equals(user.getPassword())){//比较一致
            //把新密码进行加密处理
            Md5Hash  newMd5Hash = new Md5Hash(newPassword,user.getUsername(),1024);
            String s1 = newMd5Hash.toString();
            user.setPassword(s1);
            //修改
            return sysUserService.updatepwd(user);
        }


        return R.error("您输入的密码不正确");
    }

}

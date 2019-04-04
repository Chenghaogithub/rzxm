package com.realm;

import com.entity.SysUser;
import com.service.SysMenuService;
import com.service.SysRoleService;
import com.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component(value = "userRealm")
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleService sysRoleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限-------------");
        //得到当前用户
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        //根据当前用户id查询角色名
        List<String> roles = sysRoleService.findRolesByUserId(user.getUserId());
        //再查询权限
        List<String> perms = sysMenuService.findPermsByUserId(user.getUserId());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.addRoles(roles);
        info.addStringPermissions(perms);
        System.out.println("----->授权over!");

        return info;
    }

    @Override//认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("------------认证");
        UsernamePasswordToken u = (UsernamePasswordToken) token;
        String uname = u.getUsername();
        String password = new String(u.getPassword());
        SysUser user = sysUserService.findByName(uname);
        if(user==null){
            throw new UnknownAccountException("账号未知！");
        }
        if(!password.equals(user.getPassword())){
            throw new IncorrectCredentialsException("密码错误！");

        }
        if(user.getStatus()==0){
            throw new LockedAccountException("账号被冻结！");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,password,this.getName());

        return info;
    }
}

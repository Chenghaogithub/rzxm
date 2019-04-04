package com.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.support.http.StatViewServlet;

import javax.jws.WebParam;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;


@WebServlet(name = "statViewServlet" ,urlPatterns = "/druid/*",
initParams = {
        @WebInitParam(name = "loginUsername" ,value = "aaa"),
        @WebInitParam(name = "loginPassword",value = "aaa"),
        @WebInitParam(name = "allow",value = "127.0.0.1")
}
)
public class DruidStaViewServlet extends StatViewServlet {

}

package com.test;

import org.apache.shiro.crypto.hash.Md5Hash;

public class Test01 {
    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("tom","tom",1024);
        System.out.println(md5Hash.toString());
    }
}

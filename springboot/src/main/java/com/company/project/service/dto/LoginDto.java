package com.company.project.service.dto;

import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

/**
 * Created by eayon on 2017-07-17.
 */
public class LoginDto implements Serializable {

    private String username;
    private String password;

    public LoginDto(){

    }

    public UsernamePasswordToken token(){
        return  new UsernamePasswordToken(username,password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

package com.green.hoteldog.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    public MyUserDetails getLoginUser(){
        try {
            return (MyUserDetails)SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
        }catch (Exception e){
            return null;
        }
    }
    public long getLoginUserPk(){
        MyUserDetails myUserDetails = getLoginUser();
        return myUserDetails == null ? 0 : myUserDetails.getMyPrincipal().getUserPk();
    }
}

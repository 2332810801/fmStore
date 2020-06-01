package com.fmjava.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fmjava.core.pojo.seller.Seller;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class UserDetailServiceImpl implements UserDetailsService {

  private SellerService sellerService;

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username ==null){
            return null;
        }

        //从数据库当中查询用户
        Seller seller = sellerService.findOne(username);
        if (seller != null){
            //判断当前用是否已经通过审核
            if("1".equals(seller.getStatus())){

                //定义权限
                ArrayList<GrantedAuthority> grantedList = new ArrayList<>();
                grantedList.add(new SimpleGrantedAuthority("ROLE_SELLER"));

               return  new User(username,seller.getPassword(),grantedList);
            }
        }
        return null;
    }
}

package com.ddot.service;

import com.ddot.pojo.User;
import com.ddot.utils.ServerResponse;

public interface UserService {
    //登录

    public ServerResponse loginLogin(String username,String password);

    public ServerResponse registerLogic(User user);
}

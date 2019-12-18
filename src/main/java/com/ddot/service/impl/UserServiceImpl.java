package com.ddot.service.impl;

import com.ddot.common.Const;
import com.ddot.common.ResponseCode;
import com.ddot.dao.UserMapper;
import com.ddot.pojo.User;
import com.ddot.service.UserService;
import com.ddot.utils.DateUtil;
import com.ddot.utils.MD5Utils;
import com.ddot.utils.ServerResponse;
import com.ddot.vo.UserVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public ServerResponse loginLogin(String username, String password) {

        //1 用户名和密码非空判断

        //StringUtils.isEmpty();
        //StringUtils.isBlank();
        if (StringUtils.isBlank(username)){  //null 长度为零 含有制表符 空格 都是为2空
            //要定义返回的错误码
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EMPTY.getCode(),ResponseCode.USERNAME_NOT_EMPTY.getMsg());

        }
        if (null == password || "".equals(password)){
            //要定义返回的错误码
            return ServerResponse.createServerResponseByFail(ResponseCode.PASSWORD_NOT_EMPTY.getCode(),ResponseCode.PASSWORD_NOT_EMPTY.getMsg());

        }

        //2用户名是否存在
        Integer count = userMapper.findByUsername(username);
        if (count == 0){
            //用户名不存在
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EXIT.getCode(), ResponseCode.USERNAME_NOT_EXIT.getMsg());

        }

        //3根据用户名和密码输入查询

        User user = userMapper.findByUsernameAndPassword(username,  MD5Utils.getMD5Code(password));
        //4返回结果

        if (null == user){
            //密码错误
            return ServerResponse.createServerResponseByFail(ResponseCode.PASSWORD_ERROR.getCode(), ResponseCode.PASSWORD_ERROR.getMsg());
        }

        //应该返回uservo对象

        return ServerResponse.createServerResponseBySuccess(convert(user));
    }

    private UserVO convert(User user){
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setUsername(user.getUsername());
        userVO.setEmail(user.getEmail());
        userVO.setPhone(user.getPhone());
        userVO.setCreateTime(DateUtil.date2String(user.getCreateTime()));
        userVO.setUpdateTime(DateUtil.date2String(user.getUpdateTime()));
        return userVO;
    }

    @Override
    public ServerResponse registerLogic(User user) {

        if (null == user){
            return ServerResponse.createServerResponseByFail(ResponseCode.PARAMTER_NOT_EMPTY.getCode(), ResponseCode.PARAMTER_NOT_EMPTY.getMsg());
        }
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        String phone = user.getPhone();
        String question = user.getQuestion();
        String answer = user.getAnswer();
        if (null == username || "".equals(username)){
            //要定义返回的错误码
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EMPTY.getCode(),ResponseCode.USERNAME_NOT_EMPTY.getMsg());

        }

        //step1 非空校验
        if (null == password || "".equals(password)){
            //要定义返回的错误码
            return ServerResponse.createServerResponseByFail(ResponseCode.PASSWORD_NOT_EMPTY.getCode(),ResponseCode.PASSWORD_NOT_EMPTY.getMsg());
        }
        if (null == email || "".equals(email)){
            //要定义返回的错误码
            return ServerResponse.createServerResponseByFail(ResponseCode.EMAIL_NOT_EMPTY.getCode(),ResponseCode.EMAIL_NOT_EMPTY.getMsg());
        }
        if (null == phone || "".equals(phone)){
            //要定义返回的错误码
            return ServerResponse.createServerResponseByFail(ResponseCode.PHONE_NOT_EMPTY.getCode(),ResponseCode.PHONE_NOT_EMPTY.getMsg());
        }
        if (null == question || "".equals(question)){
            //要定义返回的错误码
            return ServerResponse.createServerResponseByFail(ResponseCode.QUESTION_NOT_EMPTY.getCode(),ResponseCode.QUESTION_NOT_EMPTY.getMsg());
        }
        if (null == answer || "".equals(answer)){
            //要定义返回的错误码
            return ServerResponse.createServerResponseByFail(ResponseCode.ANSWER_NOT_EMPTY.getCode(),ResponseCode.ANSWER_NOT_EMPTY.getMsg());
        }

        //step2 判断用户名是否存在
        int user_count = userMapper.findByUsername(username);
        if (user_count > 0){
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_EXISTS.getCode(),ResponseCode.USERNAME_EXISTS.getMsg());
        }

        //判断邮箱是否存在
        int email_count = userMapper.findByEmail(email);
        if (email_count > 0){
            return ServerResponse.createServerResponseByFail(ResponseCode.EMAIL_EXISTS.getCode(),ResponseCode.EMAIL_EXISTS.getMsg());
        }

        //step3 进行注册
        //密码加密
        user.setPassword(MD5Utils.getMD5Code(user.getPassword()));

        //设置用户角色为普通用户
        user.setRole(Const.NORMAL_USER);

        Integer result = userMapper.insert(user);
        if (result == 0){   //数据库受影响行数 注册失败
            return ServerResponse.createServerResponseByFail(ResponseCode.REGISTER_FAIL.getCode(),ResponseCode.REGISTER_FAIL.getMsg());

        }

        return ServerResponse.createServerResponseBySuccess();

    }
}

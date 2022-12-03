package com.boot.javactrl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boot.javactrl.common.Constants;
import com.boot.javactrl.controller.dto.UserDTO;
import com.boot.javactrl.entity.User;
import com.boot.javactrl.exception.ServiceException;
import com.boot.javactrl.mapper.UserMapper;
import com.boot.javactrl.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.boot.javactrl.utils.TokenUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张预阅
 * @since 2022-10-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get();

    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        //业务异常
        if(one != null){
            BeanUtil.copyProperties(one, userDTO, true); //忽略大小写
            //设置Token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);
            return userDTO;
        }else{
            // 告诉外面这次结果失败了 自定义的
            throw new ServiceException(Constants.CODE_600, "用户名或者密码错误");
        }
    }

    @Override
    public User register(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one == null){
            one = new User();
            BeanUtil.copyProperties(userDTO, one, true);
            save(one); // 把 copy 完成之后把用户数据保存到数据库里面
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名已经存在");
        }
        return one;
    }

    private User getUserInfo(UserDTO userDTO){  //封装校验方法
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);  // 从数据库查询用户信息
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }
}

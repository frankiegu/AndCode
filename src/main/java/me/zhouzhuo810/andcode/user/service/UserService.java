package me.zhouzhuo810.andcode.user.service;

import me.zhouzhuo810.andcode.common.result.BaseResult;
import me.zhouzhuo810.andcode.common.service.BaseService;
import me.zhouzhuo810.andcode.user.entity.UserEntity;

/**
 * Created by zz on 2018/6/7.
 */
public interface UserService extends BaseService<UserEntity> {

    BaseResult userLogin(String phone, String password);

}

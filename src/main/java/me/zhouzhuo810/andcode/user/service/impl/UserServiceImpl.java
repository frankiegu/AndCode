package me.zhouzhuo810.andcode.user.service.impl;

import me.zhouzhuo810.andcode.common.dao.BaseDao;
import me.zhouzhuo810.andcode.common.entity.BaseEntity;
import me.zhouzhuo810.andcode.common.result.BaseResult;
import me.zhouzhuo810.andcode.common.service.impl.BaseServiceImpl;
import me.zhouzhuo810.andcode.common.utils.MapUtils;
import me.zhouzhuo810.andcode.user.dao.UserDao;
import me.zhouzhuo810.andcode.user.entity.UserEntity;
import me.zhouzhuo810.andcode.user.service.UserService;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * Created by zz on 2018/6/7.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserEntity> implements UserService {
    @Override
    @Resource(name = "userDaoImpl")
    public void setBaseDao(BaseDao<UserEntity> baseDao) {
        this.baseDao = baseDao;
    }

    public UserDao getDao() {
        return (UserDao) this.baseDao;
    }

    @Override
    public BaseResult userLogin(String phone, String password) {
        UserEntity userEntity = executeCriteriaForObject(new Criterion[]{
                Restrictions.eq("deleteFlag", BaseEntity.DELETE_FLAG_NO),
                Restrictions.eq("phone", phone),
                Restrictions.eq("password", password)
        });
        if (userEntity != null) {
            MapUtils mapUtils = new MapUtils();
            mapUtils.put("userId", userEntity.getId());
            mapUtils.put("name", userEntity.getName());
            mapUtils.put("phone", userEntity.getPhone());
            mapUtils.put("email", userEntity.getEmail());
            return new BaseResult(1, "用户名或密码错误！", mapUtils.build());
        }
        return new BaseResult(0, "登录成功！", new HashMap<String, String>());
    }

    @Override
    public BaseResult userRegister(String phone, String password, String name, String email) {
        UserEntity userEntity = new UserEntity();
        userEntity.setPhone(phone);
        userEntity.setPassword(password);
        userEntity.setEmail(email);
        userEntity.setName(name);
        try {
            save(userEntity);
            return new BaseResult(1, "注册成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BaseResult(0, "注册失败！");
    }
}

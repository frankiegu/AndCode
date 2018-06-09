package me.zhouzhuo810.andcode.user.action;

import me.zhouzhuo810.andcode.common.action.BaseController;
import me.zhouzhuo810.andcode.common.result.BaseResult;
import me.zhouzhuo810.andcode.common.service.BaseService;
import me.zhouzhuo810.andcode.user.entity.UserEntity;
import me.zhouzhuo810.andcode.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by zz on 2018/6/7.
 */
@Controller
@RequestMapping(value = "v1/user")
public class UserAction extends BaseController<UserEntity> {
    @Override
    @Resource(name = "userServiceImpl")
    public void setBaseService(BaseService<UserEntity> baseService) {
        this.baseService = baseService;
    }

    @Override
    public UserService getBaseService() {
        return (UserService) this.baseService;
    }

    @ResponseBody
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public BaseResult userLogin(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "password") String password
    ) {
        return getBaseService().userLogin(phone, password);
    }


    @ResponseBody
    @RequestMapping(value = "/userRegister", method = RequestMethod.POST)
    public BaseResult userRegister(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "email", required = false) String email
    ) {
        return getBaseService().userRegister(phone, password, name, email);
    }


}

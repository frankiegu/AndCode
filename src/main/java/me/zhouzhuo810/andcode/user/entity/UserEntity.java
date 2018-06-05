package me.zhouzhuo810.andcode.user.entity;

import me.zhouzhuo810.andcode.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by zz on 2018/5/29.
 */
@Entity(name = "user")
public class UserEntity extends BaseEntity {
    @Column(name = "phone")
    private String phone;
    @Column(name = "passowrd")
    private String passowrd;
    @Column(name = "email")
    private String email;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassowrd() {
        return passowrd;
    }

    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

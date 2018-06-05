package me.zhouzhuo810.andcode.project.entity;

import me.zhouzhuo810.andcode.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by zz on 2018/5/29.
 */
@Entity(name = "projectType")
public class ProjectTypeEntity extends BaseEntity {
    @Column(name = "TypeName")
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}

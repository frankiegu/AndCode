package me.zhouzhuo810.andcode.project.entity;

import me.zhouzhuo810.andcode.common.entity.BaseEntity;
import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 项目分类
 * Created by zz on 2018/5/29.
 */
@Entity(name = "projectTypeGroup")
public class ProjectTypeGroupEntity extends BaseEntity {
    @Column(name = "GroupName")
    private String groupName;
    @Column(name = "Pid")
    private String pid;
    @Formula("(SELECT count(*) FROM projectType p WHERE p.GroupId = ID AND p.DelFlag = 0)")
    private Integer typeCount;
    @Formula("(SELECT count(*) FROM projectTypeGroup p WHERE p.Pid = ID AND p.DelFlag = 0)")
    private Integer childCount;

    public Integer getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(Integer typeCount) {
        this.typeCount = typeCount;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }
}

package me.zhouzhuo810.andcode.project.entity;

import me.zhouzhuo810.andcode.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by zz on 2018/8/16.
 */
@Entity(name = "project_code")
public class ProjectCodeEntity extends BaseEntity{
    @Column(name = "Name")
    private String name;
    @Column(name = "Path")
    private String path;
    @Column(name = "ProjectId")
    private String projectId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}

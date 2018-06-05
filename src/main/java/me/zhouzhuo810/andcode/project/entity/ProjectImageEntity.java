package me.zhouzhuo810.andcode.project.entity;

import me.zhouzhuo810.andcode.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by zz on 2018/6/5.
 */
@Entity(name = "project_image")
public class ProjectImageEntity extends BaseEntity {
    @Column(name = "ImgPath")
    private String imgPath;
    @Column(name = "ImgSize")
    private String imgSize;
    @Column(name = "ProjectId")
    private String projectId;
    @Column(name = "Title")
    private String title;
    @Column(name = "Content")
    private String content;
    @Column(name = "Index")
    private Integer index; //排序用

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgSize() {
        return imgSize;
    }

    public void setImgSize(String imgSize) {
        this.imgSize = imgSize;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}

package me.zhouzhuo810.andcode.project.entity;

import me.zhouzhuo810.andcode.common.entity.BaseEntity;
import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by zz on 2018/5/29.
 */
@Entity(name = "project")
public class ProjectEntity extends BaseEntity {

    @Column(name = "ProjectName")
    private String projectName;
    @Column(name = "ProjectSize")
    private String projectSize;
    @Column(name = "ProjectTypeId")
    private String projectTypeId;
    @Column(name = "DevelopTools")
    private String developTools;
    @Column(name = "Price")
    private String price;
    @Column(name = "Note")
    private String note;
    @Formula("(SELECT t.TypeName FROM projectType t WHERE t.ID = projectTypeId)")
    private String fileTypeName;

    public String getFileTypeName() {
        return fileTypeName;
    }

    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectSize() {
        return projectSize;
    }

    public void setProjectSize(String projectSize) {
        this.projectSize = projectSize;
    }

    public String getProjectTypeId() {
        return projectTypeId;
    }

    public void setProjectTypeId(String projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    public String getDevelopTools() {
        return developTools;
    }

    public void setDevelopTools(String developTools) {
        this.developTools = developTools;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

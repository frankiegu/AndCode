package me.zhouzhuo810.andcode.project.service;

import me.zhouzhuo810.andcode.common.result.BaseResult;
import me.zhouzhuo810.andcode.common.service.BaseService;
import me.zhouzhuo810.andcode.project.entity.ProjectEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by zz on 2018/6/7.
 */
public interface ProjectService extends BaseService<ProjectEntity> {

    BaseResult addProject(String userId, String projectName, String projectSize, String projectTypeId,
                          String developTools, String price, MultipartFile apkPath, String note, String codePath);


    BaseResult deleteProject(String userId, String id);

    BaseResult updateProject(String userId, String projectId, String projectName,
                             String projectSize, String projectTypeId, String developTools,
                             String price, MultipartFile apkPath, String note, String codePath);

    BaseResult getAllProject();

    BaseResult getAllProjectByType(String typeId);
}

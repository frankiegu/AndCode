package me.zhouzhuo810.andcode.project.service;

import me.zhouzhuo810.andcode.common.result.BaseResult;
import me.zhouzhuo810.andcode.common.service.BaseService;
import me.zhouzhuo810.andcode.project.entity.ProjectImageEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by zz on 2018/6/7.
 */
public interface ProjectImgService extends BaseService<ProjectImageEntity> {

    BaseResult addProjectImg(String userId, String projectId, String title, String content, Integer index, MultipartFile imgFile);

    BaseResult updateProjectImg(String userId, String imgId, String projectId, String title, String content, Integer index, MultipartFile imgFile);

    BaseResult deleteProjectImg(String userId, String id);

    BaseResult getAllProjectImg(String projectId);
}

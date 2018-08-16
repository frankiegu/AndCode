package me.zhouzhuo810.andcode.project.service;

import me.zhouzhuo810.andcode.common.result.BaseResult;
import me.zhouzhuo810.andcode.common.service.BaseService;
import me.zhouzhuo810.andcode.project.entity.ProjectCodeEntity;
import me.zhouzhuo810.andcode.project.entity.ProjectEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by zz on 2018/6/7.
 */
public interface ProjectCodeService extends BaseService<ProjectCodeEntity> {

    BaseResult addProjectCode(String userId, String projectId, String name, String path);

    BaseResult deleteProjectCode(String userId, String id);

    BaseResult updateProjectCode(String userId, String codeId, String name, String path);

    BaseResult getAllCodeByProjectId(String userId, String projectId);

}

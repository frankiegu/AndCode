package me.zhouzhuo810.andcode.project.action;

import me.zhouzhuo810.andcode.common.action.BaseController;
import me.zhouzhuo810.andcode.common.result.BaseResult;
import me.zhouzhuo810.andcode.common.service.BaseService;
import me.zhouzhuo810.andcode.project.entity.ProjectEntity;
import me.zhouzhuo810.andcode.project.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * Created by zz on 2018/6/7.
 */
@Controller
@RequestMapping(value = "v1/project")
public class ProjectAction extends BaseController<ProjectEntity> {
    @Override
    @Resource(name = "projectServiceImpl")
    public void setBaseService(BaseService<ProjectEntity> baseService) {
        this.baseService = baseService;
    }

    @Override
    public ProjectService getBaseService() {
        return (ProjectService) baseService;
    }

    @ResponseBody
    @RequestMapping(value = "/addProject", method = RequestMethod.POST)
    public BaseResult addProject(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "projectName") String projectName,
            @RequestParam(value = "projectSize") String projectSize,
            @RequestParam(value = "projectTypeId") String projectTypeId,
            @RequestParam(value = "developTools", required = false) String developTools,
            @RequestParam(value = "price") String price,
            @RequestBody(required = false) MultipartFile apkFile,
            @RequestParam(value = "note", required = false) String note
    ) {

        return getBaseService().addProject(userId, projectName, projectSize,
                projectTypeId, developTools, price, apkFile, note);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
    public BaseResult deleteProject(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "id") String id
    ) {

        return getBaseService().deleteProject(userId, id);
    }

    @ResponseBody
    @RequestMapping(value = "/updateProject", method = RequestMethod.POST)
    public BaseResult updateProject(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "projectName", required = false) String projectName,
            @RequestParam(value = "projectSize", required = false) String projectSize,
            @RequestParam(value = "projectTypeId", required = false) String projectTypeId,
            @RequestParam(value = "developTools", required = false) String developTools,
            @RequestParam(value = "price", required = false) String price,
            @RequestBody(required = false) MultipartFile apkFile,
            @RequestParam(value = "note", required = false) String note
    ) {

        return getBaseService().updateProject(userId, projectId, projectName, projectSize,
                projectTypeId, developTools, price, apkFile, note);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllProjectByType", method = RequestMethod.GET)
    public BaseResult getAllProject(
            @RequestParam(value = "typeId", required = false) String typeId
    ) {
        return getBaseService().getAllProjectByType(typeId);
    }

}

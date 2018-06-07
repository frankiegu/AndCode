package me.zhouzhuo810.andcode.project.action;

import me.zhouzhuo810.andcode.common.action.BaseController;
import me.zhouzhuo810.andcode.common.result.BaseResult;
import me.zhouzhuo810.andcode.common.service.BaseService;
import me.zhouzhuo810.andcode.project.entity.ProjectTypeEntity;
import me.zhouzhuo810.andcode.project.service.ProjectTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by zz on 2018/6/7.
 */
@Controller
@RequestMapping(value = "v1/projectType")
public class ProjectTypeAction extends BaseController<ProjectTypeEntity> {

    @Override
    @Resource(name = "projectTypeServiceImpl")
    public void setBaseService(BaseService<ProjectTypeEntity> baseService) {
        this.baseService = baseService;
    }

    @Override
    public ProjectTypeService getBaseService() {
        return (ProjectTypeService) this.baseService;
    }

    @ResponseBody
    @RequestMapping(value = "/addProjectType", method = RequestMethod.POST)
    public BaseResult addProjectType(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "typeName") String typeName
    ) {
        return getBaseService().addProjectType(userId, typeName);
    }

    @ResponseBody
    @RequestMapping(value = "/updateProjectType", method = RequestMethod.POST)
    public BaseResult updateProjectType(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "typeId") String typeId,
            @RequestParam(value = "typeName") String typeName
    ) {

        return getBaseService().updateProjectType(userId, typeId, typeName);
    }


    @ResponseBody
    @RequestMapping(value = "/deleteProjectType", method = RequestMethod.POST)
    public BaseResult deleteProjectType(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "id") String id
    ) {
        return getBaseService().deleteProjectType(userId, id);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllProjectType", method = RequestMethod.GET)
    public BaseResult getAllProjectType(
    ) {
        return getBaseService().getAllProjectType();
    }


}

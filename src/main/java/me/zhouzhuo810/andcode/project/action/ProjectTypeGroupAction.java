package me.zhouzhuo810.andcode.project.action;

import me.zhouzhuo810.andcode.common.action.BaseController;
import me.zhouzhuo810.andcode.common.result.BaseResult;
import me.zhouzhuo810.andcode.common.service.BaseService;
import me.zhouzhuo810.andcode.project.entity.ProjectTypeGroupEntity;
import me.zhouzhuo810.andcode.project.service.ProjectTypeGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.persistence.Column;

/**
 * Created by zz on 2018/6/8.
 */
@Controller
@RequestMapping(value = "v1/projectTypeGroup")
public class ProjectTypeGroupAction extends BaseController<ProjectTypeGroupEntity> {
    @Override
    @Resource(name = "projectTypeGroupServiceImpl")
    public void setBaseService(BaseService<ProjectTypeGroupEntity> baseService) {
        this.baseService = baseService;
    }

    @Override
    public ProjectTypeGroupService getBaseService() {
        return (ProjectTypeGroupService) this.baseService;
    }

    @ResponseBody
    @RequestMapping(value = "/addProjectTypeGroup", method = RequestMethod.POST)
    public BaseResult addProjectTypeGroup(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "groupName") String groupName,
            @RequestParam(value = "pid", required = false) String pid
    ) {
        return getBaseService().addProjectTypeGroup(userId, groupName, pid);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteProjectTypeGroup", method = RequestMethod.POST)
    public BaseResult deleteProjectTypeGroup(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "id") String id
    ) {
        return getBaseService().deleteProjectTypeGroup(userId, id);
    }

    @ResponseBody
    @RequestMapping(value = "/updateProjectTypeGroup", method = RequestMethod.POST)
    public BaseResult updateProjectTypeGroup(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "groupId", required = false) String groupId,
            @RequestParam(value = "groupName", required = false) String groupName,
            @RequestParam(value = "pid", required = false) String pid
    ) {
        return getBaseService().updateProjectTypeGroup(userId, groupId, groupName, pid);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllProjectTypeGroup", method = RequestMethod.GET)
    public BaseResult getAllProjectTypeGroup(
            @RequestParam(value = "pid") String pid
    ) {
        return getBaseService().getAllProjectTypeGroup(pid);
    }


}

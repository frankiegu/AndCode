package me.zhouzhuo810.andcode.project.action;

import me.zhouzhuo810.andcode.common.action.BaseController;
import me.zhouzhuo810.andcode.common.result.BaseResult;
import me.zhouzhuo810.andcode.common.service.BaseService;
import me.zhouzhuo810.andcode.project.entity.ProjectCodeEntity;
import me.zhouzhuo810.andcode.project.service.ProjectCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by zz on 2018/8/16.
 */
@Controller
@RequestMapping(value = "v1/projectCode")
public class ProjectCodeAction extends BaseController<ProjectCodeEntity> {
    @Override
    @Resource(name = "projectCodeServiceImpl")
    public void setBaseService(BaseService<ProjectCodeEntity> baseService) {
        this.baseService = baseService;
    }

    @Override
    public ProjectCodeService getBaseService() {
        return (ProjectCodeService) baseService;
    }

    @ResponseBody
    @RequestMapping(value = "/addProjectCode", method = RequestMethod.POST)
    public BaseResult addProjectCode(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "path") String path
    ) {
        return getBaseService().addProjectCode(userId, projectId, name, path);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteProjectCode", method = RequestMethod.POST)
    public BaseResult deleteProjectCode(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "id") String id
    ) {
        return getBaseService().deleteProjectCode(userId, id);
    }

    @ResponseBody
    @RequestMapping(value = "/updateProjectCode", method = RequestMethod.POST)
    public BaseResult updateProjectCode(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "codeId") String codeId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "path", required = false) String path
    ) {
        return getBaseService().updateProjectCode(userId, codeId, name, path);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllCodeByProjectId", method = RequestMethod.GET)
    public BaseResult getAllCodeByProjectId(
        @RequestParam(value = "userId") String userId,
        @RequestParam(value = "projectId") String projectId
    ) {
        return getBaseService().getAllCodeByProjectId(userId, projectId);
    }
        

}

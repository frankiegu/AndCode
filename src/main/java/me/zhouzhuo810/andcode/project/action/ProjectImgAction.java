package me.zhouzhuo810.andcode.project.action;

import me.zhouzhuo810.andcode.common.action.BaseController;
import me.zhouzhuo810.andcode.common.result.BaseResult;
import me.zhouzhuo810.andcode.common.service.BaseService;
import me.zhouzhuo810.andcode.project.entity.ProjectImageEntity;
import me.zhouzhuo810.andcode.project.service.ProjectImgService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * Created by zz on 2018/6/7.
 */
@Controller
@RequestMapping(value = "v1/projectImg")
public class ProjectImgAction extends BaseController<ProjectImageEntity> {
    @Override
    @Resource(name = "projectImgServiceImpl")
    public void setBaseService(BaseService<ProjectImageEntity> baseService) {
        this.baseService = baseService;
    }

    @Override
    public ProjectImgService getBaseService() {
        return (ProjectImgService) this.baseService;
    }

    @ResponseBody
    @RequestMapping(value = "/addProjectImg", method = RequestMethod.POST)
    public BaseResult addProjectImg(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "content") String content,
            @RequestParam(value = "index") Integer index,
            @RequestBody(required = false) MultipartFile imgFile
    ) {
        return getBaseService().addProjectImg(userId, projectId, title, content, index, imgFile);
    }

    @ResponseBody
    @RequestMapping(value = "/updateProjectImg", method = RequestMethod.POST)
    public BaseResult updateProjectImg(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "imgId") String imgId,
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "index", required = false) Integer index,
            @RequestBody(required = false) MultipartFile imgFile
    ) {
        return getBaseService().updateProjectImg(userId, imgId, projectId, title, content, index, imgFile);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteProjectImg", method = RequestMethod.POST)
    public BaseResult deleteProjectImg(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "id") String id
    ) {
        return getBaseService().deleteProjectImg(userId, id);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllProjectImg", method = RequestMethod.GET)
    public BaseResult getAllProjectImg(
            @RequestParam(value = "projectId") String projectId
    ) {
        return getBaseService().getAllProjectImg(projectId);
    }


}

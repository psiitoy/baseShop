package com.rise.shop.web.controller.usage;

import com.rise.shop.domain.art.mongo.ImageInfo;
import com.rise.shop.domain.result.ActionJsonResult;
import com.rise.shop.service.art.ImageInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by wangdi on 15-1-23.
 */
@Controller
public class FileUploadController {

//    public static final String artist_ip = "http://123.57.67.81";
        public static final String artist_ip = "http://localhost";
    public static final String upload_path = "/opt/upload/img/";

    @Resource
    ImageInfoService imageInfoService;

    @RequestMapping(value = "/upload/{domain}/{filename}/{remark}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ActionJsonResult upload(@RequestParam MultipartFile[] myfiles, @PathVariable("domain") String domain, @PathVariable("filename") String filename, @PathVariable("remark") String remark, HttpServletRequest request) {
        ActionJsonResult actionJsonResult = new ActionJsonResult();
        if (myfiles == null || myfiles.length != 1) {
            actionJsonResult.setSuccess(false);
            actionJsonResult.setMsg("file num error");
            return actionJsonResult;
        }
        for (MultipartFile file : myfiles) {
            Long imageid = System.currentTimeMillis();
            String imgname = domain + filename + imageid;
            String[] filenamearr = file.getOriginalFilename().split("\\.");
            String fileName = imgname + "." + filenamearr[filenamearr.length - 1];
//        String fileName = new Date().getTime()+".jpg";
            File targetFile = new File(upload_path, fileName);
            System.out.println(targetFile.getPath());
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }

            //保存
            try {
                file.transferTo(targetFile);
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setId(imageid);
                imageInfo.setImageUrl(artist_ip + "/upload/img/" + fileName);
                imageInfo.setImageRemark(remark);
                imageInfoService.insert(imageInfo);
                actionJsonResult.setSuccess(true);
                actionJsonResult.setObj(imageInfo);
                actionJsonResult.setMsg("success");
                return actionJsonResult;
            } catch (Exception e) {
                e.printStackTrace();
                actionJsonResult.setSuccess(false);
                actionJsonResult.setMsg("fail");
                return actionJsonResult;
            }
        }
        actionJsonResult.setSuccess(false);
        actionJsonResult.setMsg("null file");
        return actionJsonResult;
    }

}

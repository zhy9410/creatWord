package com.creat.service.impl;

import com.creat.pojo.RespData;
import com.creat.service.WordService;
import com.creat.util.WordUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WordServiceImpl implements WordService {
    @Value("${tourInfoModel.pathName}")
    private String pathName;
    @Value("${tourInfoModel.fileName}")
    private String fileName;
    @Override
    public RespData createWord(HttpServletRequest req, HttpServletResponse resp) {
        resp.setCharacterEncoding("UTF-8");
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //处理数据获得map
        Map dataMap = processingTourInfoData();

        //设置下载文件的类型
        resp.setContentType("application/msword");
        //设置响应头信息
        try {
            resp.setHeader("Content-Disposition","attachment;filename=" + java.net.URLEncoder.encode("巡河记录表.doc", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            WordUtil.creatWord(pathName,fileName,resp.getOutputStream(),dataMap);
        } catch (IOException e) {
            return RespData.error("文件创建失败");
        }
        return RespData.success("文件下载成功");
    }
    //数据处理 模板没加非空校验 故数据处理中必须添加非空校验 模拟数据 省略 需要自己根据业务添加
    private Map processingTourInfoData(){
        Map map = new HashMap<>();
        List<Map> picList = new ArrayList<>();
        map.put("gmName", "河长名");
        map.put("objName", "河流");
        map.put("adName", "行政区名称");
        map.put("tourTime", "2020年10月12日");
        map.put("tourDuration", "2小时");
        map.put("question","问题1");
        //图片数据处理
       /* Map picMap = new HashMap<>();
        String filePath="";
        String pictureData = WordUtil.getImageStrFromHttp(filePath);
        picMap.put("picture", pictureData);
        picList.add(picMap);
        map.put("picList",picList);*/
        map.put("picList",null);
        return map;
    }

}

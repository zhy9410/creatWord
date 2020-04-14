package com.creat.comtroller;

import com.creat.pojo.RespData;
import com.creat.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CreatWordController {
    @Autowired
    private WordService wordService;
    @ResponseBody
    @RequestMapping("/downTourInfo")
    public RespData downTourInfo(HttpServletRequest req, HttpServletResponse resp) {

        try {
            RespData info = wordService.createWord(req, resp);
            return info;
        } catch (Exception e) {
            return RespData.error("服务器异常");
        }
    }
}

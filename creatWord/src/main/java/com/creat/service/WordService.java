package com.creat.service;

import com.creat.pojo.RespData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WordService {
    RespData createWord(HttpServletRequest req, HttpServletResponse resp);
}

package com.ns.controller;

import com.github.pagehelper.PageInfo;
import com.ns.entity.File;
import com.ns.entity.Notice;
import com.ns.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/file")
@Controller
public class FileController {
    @Autowired
    private IFileService iFileService;

    @RequestMapping("/queryFileList")
    @ResponseBody
    public PageInfo<Notice> queryFileList(HttpServletRequest request){
        PageInfo<Notice> pageInfo= new PageInfo<>();
        File file = new File();

        String pageNums = request.getParameter("pageNum");
        String pageSizes = request.getParameter("pageSize");

        Integer pageNum=0;
        Integer pageSize=10;
        if(pageNums != null && !"".equals(pageNums)){
            pageNum = Integer.parseInt(pageNums);
        }
        if(pageSizes != null && !"".equals(pageSizes)){
            pageSize = Integer.parseInt(pageSizes);
        }
        pageInfo = iFileService.queryFileList(file,pageNum,pageSize);
        return pageInfo;
    }
}

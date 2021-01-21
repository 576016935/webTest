package com.ns.service.impl;

import com.github.pagehelper.PageInfo;
import com.ns.dao.FileMapper;
import com.ns.entity.File;
import com.ns.entity.Notice;
import com.ns.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    private FileMapper fileMapper;
    @Override
    public PageInfo<Notice> queryFileList(File file, Integer pageNum, Integer pageSize) {
        fileMapper.insert(file);
        return null;
    }
}

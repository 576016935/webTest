package com.ns.service;

import com.github.pagehelper.PageInfo;
import com.ns.entity.File;
import com.ns.entity.Notice;

public interface IFileService {
    PageInfo<Notice> queryFileList(File file, Integer pageNum, Integer pageSize);
}

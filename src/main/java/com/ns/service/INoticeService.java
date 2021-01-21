package com.ns.service;

import com.github.pagehelper.PageInfo;
import com.ns.entity.Notice;

import java.util.List;
import java.util.Map;

public interface INoticeService {
    PageInfo<Notice> queryList(Notice notice, Integer pageNum, Integer pageSize) throws Exception;

    PageInfo<List<Map<String,Object>>> queryAll(Notice notice, Integer pageNum, Integer pageSize);
}

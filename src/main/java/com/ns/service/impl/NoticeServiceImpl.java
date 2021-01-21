package com.ns.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ns.dao.NoticeMapper;
import com.ns.entity.Notice;
import com.ns.service.INoticeService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class NoticeServiceImpl implements INoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private SolrClient solrClient;

    @Override
    @Transactional
    public PageInfo<Notice> queryList(Notice notice, Integer pageNum, Integer pageSize) throws Exception {
        /*try {
            notice.setProjectName("1");
            noticeMapper.insert(notice);
            Integer a = 1/0;
            notice.setProjectName("2");
            noticeMapper.insert(notice);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }*/
        //利用PageHelper分页查询 注意：这个一定要放查询语句的前一行,否则无法进行分页,因为它对紧随其后第一个sql语句有效
        //PageHelper.startPage(pageNum,pageSize);
        List<Notice> list = noticeMapper.queryList(notice);
        PageInfo<Notice> pageInfo = new PageInfo<Notice>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<List<Map<String, Object>>> queryAll(Notice notice, Integer pageNum, Integer pageSize) {
        //利用PageHelper分页查询 注意：这个一定要放查询语句的前一行,否则无法进行分页,因为它对紧随其后第一个sql语句有效
        PageHelper.startPage(pageNum,pageSize,"n.createtime desc");
        List<Map<String,Object>> mapList = noticeMapper.queryAll(notice);

        //获取新添加的信息
        SolrInputDocument solrInputFields = new SolrInputDocument();
        solrInputFields.addField("projectName",mapList.get(1).get("projectName"));
        solrInputFields.addField("createUser",mapList.get(1).get("createUser"));
        solrInputFields.addField("id",mapList.get(1).get("id"));
        solrInputFields.addField("createtime",mapList.get(1).get("createtime"));

        try {
            //将获取的信息存入索引库
            solrClient.add(solrInputFields);
            //提交
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        PageInfo<List<Map<String,Object>>> pageInfo = new PageInfo(mapList);
        return pageInfo;
    }
}

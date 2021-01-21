package com.ns.controller;

import com.github.pagehelper.PageInfo;
import com.ns.entity.Notice;
import com.ns.entity.SolrPage;
import com.ns.service.INoticeService;
import com.ns.util.IsLogin;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private INoticeService iNoticeService;
    @Autowired
    private SolrClient solrClient;

    /**
     * 单表mybatis插件分页方法测试
     * @param request
     * @return
     * @throws Exception
     */
    @IsLogin
    @RequestMapping("/queryList")
    @ResponseBody
    public PageInfo<Notice> queryList(HttpServletRequest request) throws Exception {
        PageInfo<Notice> pageInfo= new PageInfo<>();
        Notice notice = new Notice();
        String pageNums = request.getParameter("pageNum");//页码
        String pageSizes = request.getParameter("pageSize");//当页显示条数
        String projectName = request.getParameter("projectName");
        Integer pageNum=0;
        Integer pageSize=10;
        if(pageNums != null && !"".equals(pageNums)){
            pageNum = Integer.parseInt(pageNums);
        }
        if(pageSizes != null && !"".equals(pageSizes)){
            pageSize = Integer.parseInt(pageSizes);
        }
        //notice.setProjectName("1");
        pageInfo = iNoticeService.queryList(notice,pageNum,pageSize);
        return pageInfo;
    }

    /**
     *多表联合查询mybatis插件分页方法测试
     * @param request
     * @return
     * @throws Exception
     */
    @IsLogin
    @RequestMapping("/queryAll")
    @ResponseBody
    public PageInfo<List<Map<String,Object>>> queryAll(HttpServletRequest request) throws Exception {

        PageInfo<List<Map<String,Object>>> pageInfo= new PageInfo<>();
        Notice notice = new Notice();

        String pageNums = request.getParameter("pageNum");//页码
        String pageSizes = request.getParameter("pageSize");//当页显示条数


        Integer pageNum=0;
        Integer pageSize=10;
        if(pageNums != null && !"".equals(pageNums)){
            pageNum = Integer.parseInt(pageNums);
        }
        if(pageSizes != null && !"".equals(pageSizes)){
            pageSize = Integer.parseInt(pageSizes);
        }
        pageInfo = iNoticeService.queryAll(notice,pageNum,pageSize);
        return pageInfo;
    }

    @RequestMapping("/query")
    @ResponseBody
    public SolrPage<Notice> solrQuery(String keyword, SolrPage<Notice> solrPage, Model model){

        System.out.println("关键字：" + keyword);

        SolrQuery solrQuery = new SolrQuery();

        if(keyword == null || keyword.trim().equals("")){
            solrQuery.setQuery("*:*");
        } else {
            solrQuery.setQuery("projectName:" + keyword);
        }

        //设置搜索的高亮
        solrQuery.setHighlight(true);//表示开启高亮

        //设置高亮的前缀和后缀
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");

        //添加需要高亮的字段
        solrQuery.addHighlightField("projectName");

        //设置高亮的折叠
//        solrQuery.setHighlightSnippets(3);//摘要分成几部分
//        solrQuery.setHighlightFragsize(7);//每部分的长度

        //设置分页 limit ?,?
        solrQuery.setStart((solrPage.getPage() - 1) * solrPage.getPageSize());
        solrQuery.setRows(solrPage.getPageSize());


        QueryResponse query = null;
        List<Notice> list = new ArrayList<>();
        try {
            query = solrClient.query(solrQuery);

            //获得高亮的结果
            //返回值Map<String, Map<String, List<String>>>
            //{id:{fieldname:[高亮的内容,.....]}}
            Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();
            for(Map.Entry<String, Map<String, List<String>>> map1 : highlighting.entrySet()){
                System.out.println("key:" + map1.getKey());
                System.out.println("value:" + map1.getValue());
                System.out.println("--------------------------------");
            }


            //获得普通的搜索结果
            SolrDocumentList results = query.getResults();

            //处理总条数
            long pageSum = results.getNumFound();
            solrPage.setPageSum((int)pageSum);//设置总条数
            solrPage.setPageCount(solrPage.getPageSum() % solrPage.getPageSize() == 0 ?
                    solrPage.getPageSum() / solrPage.getPageSize() :
                    solrPage.getPageSum() / solrPage.getPageSize() + 1);//设置总页码

            for (SolrDocument solrDocument : results){
                Notice notice = new Notice();
                notice.setId(Integer.parseInt(solrDocument.getFieldValue("id") + ""));

                notice.setProjectName(solrDocument.getFieldValue("projectName") + "");
                notice.setCreateUser(solrDocument.getFieldValue("createUser")+"");

                //处理高亮的内容
                if(highlighting.containsKey(notice.getId() + "")){
                    //说明当前的商品有高亮的信息
                    List<String> gtitleHL = highlighting.get(notice.getId()+"").get("projectName");
                    if(gtitleHL != null){
                        notice.setProjectName(gtitleHL.get(0));
                    }
                }

                list.add(notice);
                System.out.println("notice的标题：" + notice.getProjectName());
            }

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //查询结果放入分页对象中
        solrPage.setDatas(list);

        model.addAttribute("page", solrPage);
        model.addAttribute("keyword", keyword);
        return solrPage;
        //return "redirect:../index.jsp";
    }

}

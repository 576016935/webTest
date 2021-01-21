package com.ns.entity;


import java.util.List;
import java.util.Objects;

public class SolrPage<T> {

    private Integer page = 1;//当前页
    private Integer pageSize = 8;//每页显示多少条
    private Integer pageCount;//总页码
    private Integer pageSum;//总条数
    private List<T> datas;//当前页的数据

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageSum() {
        return pageSum;
    }

    public void setPageSum(Integer pageSum) {
        this.pageSum = pageSum;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "SolrPage{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", pageCount=" + pageCount +
                ", pageSum=" + pageSum +
                ", datas=" + datas +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolrPage<?> solrPage = (SolrPage<?>) o;
        return Objects.equals(page, solrPage.page) &&
                Objects.equals(pageSize, solrPage.pageSize) &&
                Objects.equals(pageCount, solrPage.pageCount) &&
                Objects.equals(pageSum, solrPage.pageSum) &&
                Objects.equals(datas, solrPage.datas);
    }

    @Override
    public int hashCode() {

        return Objects.hash(page, pageSize, pageCount, pageSum, datas);
    }
}

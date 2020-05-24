package com.activiti.tataainiyo.base;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Query<T extends BaseDemo> {
    private String keyWords;
    private Long currentPage;
    private Long pageSize;
    private Long buf;
    private T demo;
    public void setCurrentPage(Long currentPage) {
        if(currentPage == null){
            this.currentPage = 1L;
        }else if(currentPage <= 0){
            this.currentPage = 1L;
        }else {
            this.currentPage = currentPage;
        }
        if(buf!=null){
           this.currentPage = (this.currentPage-1)*buf;
        }else {
            this.buf=this.currentPage;
        }
    }

    public void setPageSize(Long pageSize) {
        if(pageSize==null){
            this.pageSize = 10L;
        }else if(pageSize <= 0){
            this.pageSize = 10L;
        }else {
            this.pageSize = pageSize;
        }
        if(buf!=null){
            this.currentPage = (buf-1)*this.pageSize;
        }else {
            this.buf=this.pageSize;
        }
    }
}

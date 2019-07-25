package com.im.demo.notice.common.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;


@Log
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude="pageList")
public class PageMaker<T> {

    // Pagenation에 표시할 총 페이지
    public static final int DEFAULT_PAGE_SIZE = 10; //  PageVO.DEFAULT_SIZE;

    @JsonProperty("result")
    private Page<T> result;

    @JsonProperty("prev_page")
    private Pageable prevPage;
    @JsonProperty("next_page")
    private Pageable nextPage;

    @JsonProperty("current_page_num")
    private int currentPageNum = 0;
    @JsonProperty("total_page_num")
    private int totalPageNum = 0;
    @JsonProperty("default_page_size")
    private int defaultPageSize = 0;

    @JsonProperty("current_page")
    private Pageable currentPage;

    @JsonProperty("page_list")
    private List<Pageable> pageList;

    @JsonCreator
    public PageMaker(Page<T> result){
        this.result = result;
        this.currentPage = result.getPageable();
        this.currentPageNum = currentPage.getPageNumber() + 1;
        this.totalPageNum = result.getTotalPages();
        this.pageList = new ArrayList<>();
        this.defaultPageSize = DEFAULT_PAGE_SIZE;
        calculatePages();
    }

    private void calculatePages(){
        int tempEndNum = (int)(Math.ceil(this.currentPageNum/(float)DEFAULT_PAGE_SIZE) * DEFAULT_PAGE_SIZE);
        int startNum = tempEndNum - (DEFAULT_PAGE_SIZE - 1);
        Pageable startPage = this.currentPage;

        //move to start Pageble
        for(int index = startNum; index < this.currentPageNum; index++){
            startPage = startPage.previousOrFirst();
        }
        this.prevPage = startPage.getPageNumber() <= 0? null :startPage.previousOrFirst();

        if(this.totalPageNum < tempEndNum){
            tempEndNum = this.totalPageNum;
            this.nextPage = null;
        }

        for(int index = startNum; index <= tempEndNum; index++){
            pageList.add(startPage);
            startPage = startPage.next();
        }
        this.nextPage = startPage.getPageNumber() +1 < totalPageNum ? startPage: null;
    }
}
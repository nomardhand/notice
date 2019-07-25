package com.im.demo.notice.dto;


import com.im.demo.notice.common.dto.PageVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticePageVO extends PageVO {

    private Long id;

    private String title;
    private String content;
    private String writer;
    private String regdate;
    private String moddate;

    private String period;
    private String extra;

    public NoticePageVO() {
        super();
    }
}

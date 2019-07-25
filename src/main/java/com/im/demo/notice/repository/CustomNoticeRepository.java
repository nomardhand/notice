package com.im.demo.notice.repository;

import com.im.demo.notice.dto.NoticePageVO;
import com.im.demo.notice.model.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomNoticeRepository {
    Page<Notice> getNoticePageDsl(Pageable pageable, NoticePageVO noticePageVO);
}

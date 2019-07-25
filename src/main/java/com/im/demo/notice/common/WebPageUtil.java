package com.im.demo.notice.common;

import com.im.demo.notice.model.Notice;
import com.im.demo.notice.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WebPageUtil {
    @Autowired
    private NoticeRepository noticeRepository;

    /**
     * Notice 정보
     */
    public Notice getNotice(Long id) {
        return noticeRepository.findById(id).orElse(null);
    }
}

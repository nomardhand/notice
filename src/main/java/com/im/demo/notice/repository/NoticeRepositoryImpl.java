package com.im.demo.notice.repository;

import com.im.demo.notice.dto.NoticePageVO;
import com.im.demo.notice.model.Notice;
import com.im.demo.notice.model.QNotice;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.List;
import java.util.SimpleTimeZone;


public class NoticeRepositoryImpl extends QuerydslRepositorySupport implements CustomNoticeRepository {
    public NoticeRepositoryImpl() {
        super(Notice.class);
    }

    @Override
    public Page<Notice> getNoticePageDsl(Pageable pageable, NoticePageVO noticePageVO) {

        QNotice notice = QNotice.notice;

        JPQLQuery<Notice> jpqlQuery = from(notice);
        jpqlQuery.select(notice);

        // Where 절
        if (noticePageVO.getId() != null && noticePageVO.getId() > 0) {
            jpqlQuery.where(notice.id.eq(noticePageVO.getId()));
        }
        if (!StringUtils.isEmpty(noticePageVO.getTitle())) {
            jpqlQuery.where(notice.title.eq(noticePageVO.getTitle()));
        }
        if (!StringUtils.isEmpty(noticePageVO.getContent())) {
            jpqlQuery.where(notice.content.eq(noticePageVO.getContent()));
        }
        if (!StringUtils.isEmpty(noticePageVO.getWriter())) {
            jpqlQuery.where(notice.writer.eq(noticePageVO.getWriter()));
        }

        // keyword 검색
        if (!StringUtils.isEmpty(noticePageVO.getKeyword())) {
            if (noticePageVO.getKeytype().equals("title")) {
                jpqlQuery.where(notice.title.like("%" + noticePageVO.getKeyword() + "%"));
            }
            else if (noticePageVO.getKeytype().equals("content")) {
                jpqlQuery.where(notice.content.like("%" + noticePageVO.getKeyword() + "%"));
            }
            else if (noticePageVO.getKeytype().equals("writer")) {
                jpqlQuery.where(notice.writer.like("%" + noticePageVO.getKeyword() + "%"));
            }
        }

        // 기간 검색
        if (!StringUtils.isEmpty(noticePageVO.getPeriod())) {
            if (noticePageVO.getPeriod().equals("0")) { // 전체
                //조건 없음
            } else if (noticePageVO.getPeriod().equals("30")) { // 1달
                Calendar cal = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
                cal.add(Calendar.MONTH ,-1); // 한달 전 날짜 가져오기
                java.util.Date monthago = cal.getTime();
                // 날짜 보다 크거나 같음
                jpqlQuery.where(notice.regdate.goe(monthago));
            } else if (noticePageVO.getPeriod().equals("-1")) { // 특정 기간 범위 검색
                String range = noticePageVO.getRegdate(); // "2018:06:01-2018:12:25";

                // 한달 전 날짜 가져오기
                Calendar cal = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
                cal.add(Calendar.MONTH ,-1);
                java.util.Date from = cal.getTime();

                // 현재 날짜
                java.util.Date dateNow = Calendar.getInstance(new SimpleTimeZone(0, "GMT")).getTime();

                // 한달 전 날짜 보다 크거나 같음
                jpqlQuery.where(notice.regdate.between(from, dateNow));
            }
        }

        //jpqlQuery.groupBy(notice.id);
        jpqlQuery.orderBy(notice.id.desc());
        jpqlQuery.offset(pageable.getOffset());
        jpqlQuery.limit(pageable.getPageSize());

        List<Notice> list = jpqlQuery.fetch();
        long total = jpqlQuery.fetchCount();
        Page<Notice> notices = new PageImpl<>(list, pageable, total);

        return notices;
    }
}

package com.im.demo.notice.repository;

import com.im.demo.notice.model.Notice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NoticeRepository extends PagingAndSortingRepository<Notice, Long>, CustomNoticeRepository {

    // 최신 5회
    List<Notice> findTop5ByWriterOrderByRegdateDesc(final String writer);

    @Transactional // 데이터 변경시 필요함
    @Modifying
    Notice save(final Notice notice);

    @Transactional // 데이터 변경시 필요함
    @Modifying
    void delete(final Notice notice);
}

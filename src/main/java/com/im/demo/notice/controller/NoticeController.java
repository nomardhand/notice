package com.im.demo.notice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.im.demo.notice.common.dto.PageMaker;
import com.im.demo.notice.dto.NoticePageVO;
import com.im.demo.notice.model.Notice;
import com.im.demo.notice.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping(value = "/notice")
@Controller
public class NoticeController {
    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private int mCurPage = 1; // 현재 페이지 번호 저장


    /**
     * 목록
     */
    @RequestMapping(value = "/notice-list", method = RequestMethod.GET)
    public String noticePages(@ModelAttribute("p_page_vo") NoticePageVO noticePageVO, Model model) {

        PageMaker<Notice> pageMaker = null;
        try {
            pageMaker = getNoticePages(noticePageVO);
        } catch (Exception e) {
            return "/error-page";
        }

        // 현재 페이지 정보 설정
        mCurPage = noticePageVO.getPage();

        model.addAttribute("p_keytypes", getKeywordTypesForNotice()); // 키워드 타입
        model.addAttribute("p_page_maker", pageMaker);

        return "notice/notices";
    }

    /**
     * 키워드 타입
     */
    public List<String> getKeywordTypesForNotice() {
        List<String> keywordTypes = new ArrayList<>();
        keywordTypes.add("title");
        keywordTypes.add("content");
        keywordTypes.add("writer");
        return keywordTypes;
    }

    /**
     * 목록 가져오기
     */
    @Transactional
    public PageMaker<Notice> getNoticePages(NoticePageVO noticePageVO) {
        Pageable pageable = noticePageVO.makePageable(0, "id");
        Page<Notice> result = noticeRepository.getNoticePageDsl(pageable, noticePageVO);
        PageMaker<Notice> pageMaker = new PageMaker(result);
        return pageMaker;
    }

    /**
     * 등록
     */
    @RequestMapping(value = "/notice-save", method = RequestMethod.POST)
    public String noticeSave(@RequestParam(name = "id") Long id,
                             @RequestParam(name = "title") String title,
                             @RequestParam(name = "content") String content,
                             @RequestParam(name = "writer") String writer,
                             Model model,
                             RedirectAttributes rttr) {

        String msgContent = "등록되었습니다.";
        Notice notice = new Notice();

        if (id > 0) {
            notice = noticeRepository.findById(id).orElse(null);
            notice.setModdate(new Date());
            msgContent = "수정되었습니다.";
        }

        //notice.setId(1137L); // 자동 생성
        notice.setTitle(title);
        notice.setContent(content);
        notice.setWriter(writer);
        //notice.setRegdate(); // 자동 생성
        //notice.setModdate();

        try {
            noticeRepository.save(notice);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 현재 페이지로 설정
        NoticePageVO noticePageVO = new NoticePageVO();
        noticePageVO.setPage(mCurPage);

        // 리다이렉트 전달값
        rttr.addFlashAttribute("p_page_vo", noticePageVO);
        rttr.addFlashAttribute("p_msg_type", "ok");
        rttr.addFlashAttribute("p_msg_content", msgContent);

        return "redirect:notice-list";
    }

    /**
     * 삭제
     */
    @RequestMapping(value = "/notice-delete", method = RequestMethod.POST)
    public String noticeDelete(@RequestParam(name = "id") Long id, Model model, RedirectAttributes rttr) {

        noticeRepository.deleteById(id);

        // 현재 페이지로 설정
        NoticePageVO noticePageVO = new NoticePageVO();
        noticePageVO.setPage(mCurPage);

        rttr.addFlashAttribute("p_msg_type", "ok");
        rttr.addFlashAttribute("p_msg_content", "삭제되었습니다.");
        rttr.addFlashAttribute("p_page_vo", noticePageVO);

        return "redirect:notice-list";
    }
}

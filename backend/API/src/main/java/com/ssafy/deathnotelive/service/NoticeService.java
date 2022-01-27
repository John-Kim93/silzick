package com.ssafy.deathnotelive.service;

import com.ssafy.deathnotelive.dto.NoticeDto;
import com.ssafy.deathnotelive.entity.Notice;
import com.ssafy.deathnotelive.error.exception.NoticeNotFoundException;
import com.ssafy.deathnotelive.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    NoticeRepository noticeRepository;
    ModelMapper modelMapper;

    public List<NoticeDto.Notices> getAllNotice() {
        List<Notice> notices = noticeRepository.findAll();
        List<NoticeDto.Notices> list = notices
                .stream()
                .map(user -> modelMapper.map(user, NoticeDto.Notices.class))
                .collect(Collectors.toList());
        return list;
    }

    public NoticeDto.noticeDetail findNotice(Long noticeNo) {
        Notice notice = noticeRepository.findById(noticeNo).orElseThrow(() -> new NoticeNotFoundException("EORROR"));
        return NoticeDto.noticeDetail.builder()
                .noticeNo(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .hit(notice.getHit())
                .userId(notice.getUser().getUserId())
                .createAt(notice.getCreatedAt())
                .modifiedAt(notice.getModifiedAt())
                .build();
    }

    public void deleteNotice(Long noticeNo) {
        noticeRepository.findById(noticeNo).ifPresent(noticeRepository::delete);
    }

    public void modifyNotice(NoticeDto.NoticeModify noticeModify) {
        Long noticeNo = noticeModify.getNoticeNo();
        Notice notice = noticeRepository.findById(noticeNo).orElseThrow(() -> new NoticeNotFoundException("EORROR"));
        noticeRepository.save(Notice.builder()
                .content(noticeModify.getContent())
                .title(noticeModify.getTitle())
                .build());
    }
}

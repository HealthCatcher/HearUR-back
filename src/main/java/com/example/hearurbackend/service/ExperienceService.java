package com.example.hearurbackend.service;

import com.example.hearurbackend.dto.experience.ExperienceResponseDto;
import com.example.hearurbackend.dto.experience.NoticeRequestDto;
import com.example.hearurbackend.dto.post.PostRequestDto;
import com.example.hearurbackend.dto.post.PostResponseDto;
import com.example.hearurbackend.entity.community.Post;
import com.example.hearurbackend.entity.experience.ExperienceNotice;
import com.example.hearurbackend.entity.user.User;
import com.example.hearurbackend.repository.ExperienceNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExperienceService {
    private final ExperienceNoticeRepository experienceNoticeRepository;
    private final UserService userService;
//    public List<ExperienceResponseDto> getNoticeList() {
//        List<ExperienceNotice> noticeEntities = experienceNoticeRepository.findAll();
//        return noticeEntities.stream()
//                .map(notice -> {
//                    Optional<User> userOptional = userService.getUser(notice.getAuthor().getUsername());
//                    String authorNickname = userOptional.map(User::getNickname).orElse("Unknown Author");
//
//                    return NoticeRequestDto.builder()
//                            .no(notice.getNo())
//                            .category(notice.getCategory())
//                            .title(notice.getTitle())
//                            .author(authorNickname)
//                            .createDate(notice.getCreateDate())
//                            .views(notice.getViews())
//                            .likes(notice.getLikesCount())
//                            .build();
//                })
//                .collect(Collectors.toList());
//    }
//
//    public ExperienceResponseDto getNoticeDetail(Long noticeNo) {
//    }
//
//    public Post createNotice(NoticeRequestDto noticeRequestDto, String username) {
//    }
//
//    public void updateNotice(Long noticeNo, PostRequestDto postRequestDto, String username) {
//    }
//
//    public void deleteNotice(Long noticeNo, String username) {
//
//    }
}

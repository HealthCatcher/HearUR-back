package com.example.hearurbackend.controller;

import com.example.hearurbackend.dto.experience.NoticeRequestDto;
import com.example.hearurbackend.dto.experience.NoticeResponseDto;
import com.example.hearurbackend.dto.oauth.CustomOAuth2User;
import com.example.hearurbackend.dto.post.PostRequestDto;
import com.example.hearurbackend.entity.experience.Notice;
import com.example.hearurbackend.service.ExperienceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/experience")
public class ExperienceController {
    private final ExperienceService experienceService;

    @Operation(summary = "체험단 공고 목록 조회")
    @GetMapping("/notice")
    public ResponseEntity<List<NoticeResponseDto>> getNoticeList() {
        List<NoticeResponseDto> noticeList = experienceService.getNoticeList();
        return ResponseEntity.ok(noticeList);
    }

    @Operation(summary = "체험단 공고 상세 조회")
    @GetMapping("/notice/{noticeId}")
    public ResponseEntity<NoticeResponseDto> getNoticeDetail(
            @PathVariable UUID noticeId
    ) {
        NoticeResponseDto responseDTO = experienceService.getNoticeDetail(noticeId);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "체험단 공고 작성")
    @PostMapping("/notice")
    public ResponseEntity<Void> createPost(
            @AuthenticationPrincipal CustomOAuth2User auth,
            @RequestBody NoticeResponseDto noticeRequestDto
    ) {
        Notice newNotice = experienceService.createNotice(noticeRequestDto, auth.getUsername());
        String noticeId = newNotice.getId().toString();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/community/{noticeId}")
                .buildAndExpand(noticeId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/notice/{noticeId}")
    public ResponseEntity<Void> updatePost(
            @PathVariable UUID noticeId,
            @AuthenticationPrincipal CustomOAuth2User auth,
            @RequestBody NoticeRequestDto noticeRequestDto
    ) {
        experienceService.updateNotice(noticeId, noticeRequestDto, auth.getUsername());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/notice/{noticeId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable UUID noticeId,
            @AuthenticationPrincipal CustomOAuth2User auth
    ) {
        experienceService.deleteNotice(noticeId, auth.getUsername());
        return ResponseEntity.noContent().build();
    }
}

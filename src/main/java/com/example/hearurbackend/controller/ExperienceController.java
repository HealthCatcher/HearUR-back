package com.example.hearurbackend.controller;

import com.example.hearurbackend.dto.experience.ExperienceResponseDto;
import com.example.hearurbackend.dto.experience.NoticeRequestDto;
import com.example.hearurbackend.dto.oauth.CustomOAuth2User;
import com.example.hearurbackend.dto.post.PostRequestDto;
import com.example.hearurbackend.entity.community.Post;
import com.example.hearurbackend.service.ExperienceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/experience")
public class ExperienceController {
    private final ExperienceService experienceService;

    @Operation(summary = "체험단 공고 목록 조회")
    @GetMapping("/experience")
    public ResponseEntity<List<ExperienceResponseDto>> getNoticeList() {
        //List<ExperienceResponseDto> noticeList = experienceService.getNoticeList();
        //return ResponseEntity.ok(noticeList);
        return null;
    }

    @Operation(summary = "체험단 공고 상세 조회")
    @GetMapping("/experience/{noticeNo}")
    public ResponseEntity<ExperienceResponseDto> getNoticeDetail(
            @PathVariable Long noticeNo
    ) {
        //ExperienceResponseDto responseDTO = experienceService.getNoticeDetail(noticeNo);
        //return ResponseEntity.ok(responseDTO);
        return null;
    }

    @Operation(summary = "체험단 공고 작성")
    @PostMapping("/experience")
    public ResponseEntity<Void> createPost(
            @AuthenticationPrincipal CustomOAuth2User auth,
            @RequestBody NoticeRequestDto noticeRequestDto
    ) {
//        Post newPost = experienceService.createNotice(noticeRequestDto, auth.getUsername());
//        String postNo = newPost.getNo().toString();
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/community/{postNo]}")
//                .buildAndExpand(postNo)
//                .toUri();
//        return ResponseEntity.created(location).build();
        return null;
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/experience/{noticeNo}")
    public ResponseEntity<Void> updatePost(
            @PathVariable Long postNo,
            @AuthenticationPrincipal CustomOAuth2User auth,
            @RequestBody PostRequestDto postRequestDto
    ) {
//        experienceService.updateNotice(postNo, postRequestDto, auth.getUsername());
//        return ResponseEntity.noContent().build();
        return null;
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/experience/{noticeNo}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postNo,
            @AuthenticationPrincipal CustomOAuth2User auth
    ) {
//        experienceService.deleteNotice(postNo, auth.getUsername());
//        return ResponseEntity.noContent().build();
        return null;
    }
}

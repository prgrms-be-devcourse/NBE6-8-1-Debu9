package com.back.domain.member.member.dto;

import java.time.LocalDateTime;

record MemberDto(
        int id,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        String email
) {
    public MemberDto(com.back.domain.member.member.entity.Member member) {
        this(
                member.getId(),
                member.getCreateDate(),
                member.getModifyDate(),
                member.getEmail()
        );
    }
}

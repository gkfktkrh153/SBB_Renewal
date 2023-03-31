package com.sbb.member.service;

import com.sbb.member.domain.Member;
import com.sbb.member.dto.MemberReq;
import com.sbb.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(MemberReq memberReq)
    {
        Member member = Member.builder()
                        .username(memberReq.getUsername())
                        .email(memberReq.getEmail())
                        .build();
        member.setPassword(passwordEncoder.encode(memberReq.getPassword1()));
        memberRepository.save(member);
    }
}

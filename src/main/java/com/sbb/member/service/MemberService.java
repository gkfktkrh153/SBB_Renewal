package com.sbb.member.service;

import com.sbb.member.domain.Member;
import com.sbb.member.dto.MemberReq;
import com.sbb.member.repository.MemberRepository;
import com.sbb.question.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Member getUser(String username){
        Optional<Member> o_member =
                memberRepository.findByusername(username);
        if(o_member.isPresent()){
            return o_member.get();
        }
        else{
            throw new DataNotFoundException("User not fount");
        }
    }
}

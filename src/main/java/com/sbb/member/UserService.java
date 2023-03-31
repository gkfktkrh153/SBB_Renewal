package com.sbb.member;

import com.sbb.member.domain.Member;
import com.sbb.member.dto.MemberReq;
import com.sbb.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> o_user = memberRepository.findByusername(username);
        if (o_user.isEmpty())
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        Member member = o_user.get();
        List<GrantedAuthority> authorityList = new ArrayList<>();

        if("admin".equals(username)){
            authorityList.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        }
        else {
            authorityList.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        return new User(member.getUsername(), member.getPassword(), authorityList);
    }
}

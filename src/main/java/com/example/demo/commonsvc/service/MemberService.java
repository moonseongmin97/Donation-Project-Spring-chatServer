package com.example.demo.commonsvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.commonsvc.to.MemberEntity;
import com.example.demo.commonsvc.to.MemberRepository;


import java.util.List;
import java.util.Optional;
@Service
public class MemberService  {

    @Autowired
    private MemberRepository memberRepository;

    // 모든 회원 조회
    public List<MemberEntity> findAllMembers() {
    	System.out.println("두번쨰도 됨");
        return memberRepository.findAll();
    }

    // ID로 회원 조회
    public Optional<MemberEntity> findMemberById(Long id) {
        return memberRepository.findById(id);
    }

    // 이메일로 회원 조회
    public MemberEntity findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    // 회원 저장
    public void saveMember(MemberEntity member) {
        memberRepository.save(member);
    }
}

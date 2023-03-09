package com.example.deliveryagent.common.domain;

import com.example.deliveryagent.member.domain.Member;

public class MemberContext {

    private MemberContext(){}
    private static final ThreadLocal<Member> currentMember = new ThreadLocal<>();

    public static Member getCurrentMember() {
        return currentMember.get();
    }

    public static void setCurrentMember(Member member) {
        currentMember.set(member);
    }

    public static void clear() {
        currentMember.remove();
    }
}

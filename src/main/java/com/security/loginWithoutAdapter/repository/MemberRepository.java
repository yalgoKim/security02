package com.security.loginWithoutAdapter.repository;

import com.security.loginWithoutAdapter.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository <Member, String> {
}

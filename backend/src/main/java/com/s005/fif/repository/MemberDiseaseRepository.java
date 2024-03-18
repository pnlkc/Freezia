package com.s005.fif.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s005.fif.entity.Member;
import com.s005.fif.entity.MemberDiseaseRel;

public interface MemberDiseaseRepository extends JpaRepository<MemberDiseaseRel, Integer> {
	List<MemberDiseaseRel> findAllByMember(Member member);
}

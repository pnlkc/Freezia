package com.s005.fif.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s005.fif.entity.Disease;

public interface DiseaseRepository extends JpaRepository<Disease, Integer> {
}

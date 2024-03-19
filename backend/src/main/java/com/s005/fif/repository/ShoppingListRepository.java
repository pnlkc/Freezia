package com.s005.fif.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s005.fif.entity.Member;
import com.s005.fif.entity.ShoppingList;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Integer> {
	List<ShoppingList> findAllByMember(Member member);
}

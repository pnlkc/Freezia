package com.s005.fif.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.s005.fif.common.Constant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ShoppingList {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer shoppingListId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member member;

	@Column(nullable = false)
	@Size(max = Constant.COMMON_TITLE_LENGTH)
	private String name;

	@Column(nullable = false)
	@ColumnDefault("false")
	private Boolean checkYn;
}

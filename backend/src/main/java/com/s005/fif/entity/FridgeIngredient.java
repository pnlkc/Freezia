package com.s005.fif.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
public class FridgeIngredient {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer fridgeIngredientId;

	// TODO : 추가 시간 자동 입력 로직 추가
	@Column(nullable = false)
	private LocalDate insertionDate;

	@Column(nullable = false)
	@CreatedDate
	private LocalDate expirationDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fridge_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Fridge fridge;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ingredient_id", nullable = false)
	private Ingredient ingredient;

	@PrePersist
	public void onPrePersist() {
		this.insertionDate =LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	}
}

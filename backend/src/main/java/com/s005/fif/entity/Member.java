package com.s005.fif.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.s005.fif.common.Constant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Member {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memberId;

	@Column(nullable = false)
	@Size(max = Constant.MEMBER_NAME_LENGTH)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender gender;

	@Column(nullable = false)
	private LocalDate birth;

	@Column(nullable = false)
	private Boolean onboardYn;

	private Integer stress;

	private Integer bloodOxygen;

	private Integer sleep;

	@Size(max = Constant.PREFER_MENU_LIST_LENGTH)
	private String preferMenu;

	@Column(nullable = false)
	@Size(max = Constant.COMMON_URL_LENGTH)
	private String imgUrl;

	@Size(max = Constant.FCM_TOKEN_LENGTH)
	private String mobileToken;

	@Size(max = Constant.FCM_TOKEN_LENGTH)
	private String watchToken;

	@Size(max = Constant.COMMON_CONTENT_LENGTH)
	private String threadId;

	// TODO : createDatetime, updateDatetime 자동 적용 로직
	private LocalDateTime createDatetime;

	private LocalDateTime updateDatetime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fridge_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Fridge fridge;

	public void updateOnboarding(String preferMenu) {
		this.preferMenu = preferMenu;
		this.onboardYn = true;
	}

	public void updatePreference(String preferMenu) {
		this.preferMenu = preferMenu;
	}

	public void updateThreadId(String threadId) { this.threadId = threadId; }

	public void updateMobileToken(String mobileToken) {
		this.mobileToken = mobileToken;
	}

	public void updateWatchToken(String watchToken) {
		this.watchToken = watchToken;
	}

}

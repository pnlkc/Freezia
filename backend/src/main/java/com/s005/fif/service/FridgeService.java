package com.s005.fif.service;

import org.springframework.stereotype.Service;

import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.exception.ExceptionType;
import com.s005.fif.entity.Fridge;
import com.s005.fif.repository.FridgeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FridgeService {

	private final FridgeRepository fridgeRepository;

	public void registerFridgeToken(Integer fridgeId, String fridgeToken) {
		Fridge fridge = fridgeRepository.findById(fridgeId)
			.orElseThrow(() -> new CustomException(ExceptionType.FRIDGE_NOT_FOUND));
		fridge.setFridgeToken(fridgeToken);
		fridgeRepository.save(fridge);
	}
}

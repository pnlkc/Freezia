package com.s005.fif.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.s005.fif.common.response.Response;
import com.s005.fif.dto.FcmSendDto;
import com.s005.fif.service.FirebaseCloudMessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fcm")
public class FcmController {

	private final FirebaseCloudMessageService firebaseCloudMessageService;

	@PostMapping
	public Response sendMessage(@RequestBody FcmSendDto fcmSendDto) throws IOException {
		firebaseCloudMessageService.sendMessageTo(fcmSendDto);
		return new Response(Response.MESSAGE, "FCM 메시지를 전송했습니다.");
	}

}

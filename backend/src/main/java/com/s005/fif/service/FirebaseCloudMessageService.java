package com.s005.fif.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.auth.oauth2.GoogleCredentials;
import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.exception.ExceptionType;
import com.s005.fif.dto.fcm.FcmMessageDto;
import com.s005.fif.dto.fcm.FcmMessageNotificationDto;
import com.s005.fif.dto.fcm.FcmRecipeDto;
import com.s005.fif.dto.fcm.FcmRecipeSenderDto;
import com.s005.fif.dto.fcm.FcmSendDto;
import com.s005.fif.dto.fcm.FcmStepShiftingDto;
import com.s005.fif.dto.response.CautionIngredientResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirebaseCloudMessageService {

	private final String API_URL = "https://fcm.googleapis.com/v1/projects/fridgeisfree-6fca3/messages:send";  // 요청을 보낼 엔드포인트

	public void sendMessageTo(FcmSendDto fcmSendDto) {
		try {
			String message = makeMessage(fcmSendDto);
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + getAccessToken());

			HttpEntity entity = new HttpEntity<>(message, headers);

			ResponseEntity response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

			if(response.getStatusCode() != HttpStatus.OK) {
				log.error("FCM 전송 오류: fcm.googleapis.com HTTP 응답 오류");
				log.error(response.toString());
				throw new CustomException(ExceptionType.FCM_REQUEST_FAILED);
			}
		} catch (IOException e) {
			log.error("FCM 전송 오류: ", e);
			throw new CustomException(ExceptionType.FCM_REQUEST_FAILED);
		}
	}

	/**
	 * Firebase Admin SDK의 비공개 키를 참조하여 Bearer 토큰을 발급 받습니다.
	 *
	 * @return Bearer token
	 */
	private String getAccessToken() throws IOException {
		String firebaseConfigPath = "firebase/fridgeisfree-dev-firebase-key.json";

		GoogleCredentials googleCredentials = GoogleCredentials
			.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
			.createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

		googleCredentials.refreshIfExpired();
		return googleCredentials.getAccessToken().getTokenValue();
	}

	/**
	 * FCM 전송 정보를 기반으로 메시지를 구성합니다. (Object -> String)
	 *
	 * @param fcmSendDto FcmSendDto
	 * @return String
	 */
	private String makeMessage(FcmSendDto fcmSendDto) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();

		Map<String, String> map = new HashMap<>();
		map.put("json", om.registerModule(new JavaTimeModule()).writeValueAsString(fcmSendDto.getData()));

		Object data = fcmSendDto.getData();
		if (data instanceof FcmRecipeDto) {
			map.put("type", ((FcmRecipeDto)data).getType().toString());
		} else if (data instanceof FcmRecipeSenderDto) {
			map.put("type", ((FcmRecipeSenderDto)data).getType().toString());
		} else if (data instanceof FcmStepShiftingDto) {
			map.put("type", ((FcmStepShiftingDto)data).getType().toString());
		} else if (data instanceof CautionIngredientResponseDto) {
			map.put("type", ((CautionIngredientResponseDto)data).getType().toString());
		}

		if (
			data instanceof CautionIngredientResponseDto ||
				(data instanceof FcmRecipeDto && (((FcmRecipeDto)data).getType() == 2 || ((FcmRecipeDto)data).getType() == 3))
		) {
			FcmMessageNotificationDto fcmMessageNotificationDto = FcmMessageNotificationDto.builder()
				.message(FcmMessageNotificationDto.Message.builder()
					.token(fcmSendDto.getToken())
					.data(map)
					.build()).validateOnly(false).build();
			return om.writeValueAsString(fcmMessageNotificationDto);
		}

		FcmMessageDto fcmMessageDto = FcmMessageDto.builder()
			.message(FcmMessageDto.Message.builder()
				.token(fcmSendDto.getToken())
				.notification(FcmMessageDto.Notification.builder()
					.title(fcmSendDto.getTitle())
					.body(fcmSendDto.getBody())
					.image(null)
					.build()
				)
				.data(map)
				.build()).validateOnly(false).build();

		return om.writeValueAsString(fcmMessageDto);
	}
}

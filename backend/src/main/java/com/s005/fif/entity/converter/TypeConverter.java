package com.s005.fif.entity.converter;

import java.util.stream.Stream;

import com.s005.fif.entity.Type;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TypeConverter implements AttributeConverter<Type, Integer> {
	/**
	 * Type enum을 DB에 code로 넣기 위해 override합니다.
	 * @param type 요리 타입
	 * @return 요리 타입의 code
	 */
	@Override
	public Integer convertToDatabaseColumn(Type type) {
		if (type == null) {
			return null;
		}

		return type.getCode();
	}

	/**
	 * DB에서 code를 읽었을 때 이에 맞는 Type enum을 반환합니다.
	 * @param code DB에서 읽은 code
	 * @return 매칭되는 Type (enum)
	 */
	@Override
	public Type convertToEntityAttribute(Integer code) {
		if (code == null) {
			return null;
		}

		return Stream.of(Type.values())
			.filter(t -> t.getCode() == code)
			.findFirst()
			.orElse(null);
	}
}

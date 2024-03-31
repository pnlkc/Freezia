package com.s005.fif.dto.fcm;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DeviceType {
	NONE(-1, "NONE"),
	WEB(0, "web"),
	MOBILE(1, "mobile"),
	WATCH(2, "watch");

	DeviceType(int type, String name) {
		this.type = type;
		this.name = name;
	}

	int type;
	String name;

	@JsonCreator
	public static DeviceType from(int type) {
		for (DeviceType d : DeviceType.values()) {
			if (d.type == type)
				return d;
		}
		return NONE;
	}

	@JsonCreator
	public static DeviceType from(String name) {
		for (DeviceType d : DeviceType.values()) {
			if (d.name.equals(name))
				return d;
		}
		return NONE;
	}
}

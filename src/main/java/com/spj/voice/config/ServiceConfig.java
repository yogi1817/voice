package com.spj.voice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Yogesh SHarma
 *
 */
@Component
@Data
public class ServiceConfig {

	@Value("${voice.findmysalon.waittimeuser}")
	private String waittimeuser;

	@Value("${voice.findmysalon.checkin}")
	private String checkIn;
}
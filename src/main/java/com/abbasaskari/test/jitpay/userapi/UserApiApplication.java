package com.abbasaskari.test.jitpay.userapi;

import com.abbasaskari.test.jitpay.userapi.common.log.LogUtil;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 */
@SpringBootApplication
public class UserApiApplication {
	private static final Logger LOG = LogUtil.getDefaultLogger(UserApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(UserApiApplication.class, args);
		LOG.info("Application is ready!");
	}
}

package com.wallet.demo.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for the Dozer library.
 *
 * @author David Winn
 */
@Configuration
public class DozerConfig {

	@Bean
	public Mapper mapper() {
		return new DozerBeanMapper();
	}

}

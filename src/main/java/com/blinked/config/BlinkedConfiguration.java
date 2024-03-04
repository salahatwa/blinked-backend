package com.blinked.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.api.common.cache.AbstractStringCacheStore;
import com.api.common.cache.InMemoryCacheStore;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Blinked configuration.
 *
 * @author ssatwa
 */
@Slf4j
@EnableAsync
//@EnableScheduling
@Configuration(proxyBeanMethods = false)
//@EnableConfigurationProperties(HaloProperties.class)
//@EnableJpaRepositories(basePackages = "run.halo.app.repository", repositoryBaseClass = BaseRepositoryImpl.class)
public class BlinkedConfiguration {

	@Bean
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
		builder.failOnEmptyBeans(false);
		return builder.build();
	}

	@Bean
	@ConditionalOnMissingBean
	public AbstractStringCacheStore stringCacheStore() {
		AbstractStringCacheStore stringCacheStore = new InMemoryCacheStore();

		log.info("Genhub cache store load impl : [{}]", stringCacheStore.getClass());
		return stringCacheStore;

	}
}

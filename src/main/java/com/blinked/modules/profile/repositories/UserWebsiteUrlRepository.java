package com.blinked.modules.profile.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blinked.modules.profile.entities.UserWebsiteUrl;

public interface UserWebsiteUrlRepository extends JpaRepository<UserWebsiteUrl, Long>{

	@Query("select userWebsiteUrl from UserWebsiteUrl userWebsiteUrl where userWebsiteUrl.url=?1")
	UserWebsiteUrl getIdFromUrl(String url);
	
	@Query("select userWebsiteUrl from UserWebsiteUrl userWebsiteUrl where userWebsiteUrl.userId=?1")
	List<UserWebsiteUrl> getUserWebsiteUrl(Long userId);

}

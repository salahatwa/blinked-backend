package com.blinked.modules.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blinked.modules.core.model.entities.CommentBlackList;
import com.blinked.modules.core.repositories.base.BaseRepository;

/**
 * 
 * @date 2020/1/3
 */
public interface CommentBlackListRepository extends BaseRepository<CommentBlackList, Long> {

	/**
	 *
	 * @param ipAddress
	 * @return
	 */
	Optional<CommentBlackList> findByIpAddress(String ipAddress);

	/**
	 * Update Comment BlackList By IPAddress
	 *
	 * @param commentBlackList
	 * @return result
	 */
	@Modifying
	@Query("UPDATE CommentBlackList SET banTime=:#{#commentBlackList.banTime} WHERE ipAddress=:#{#commentBlackList.ipAddress}")
	int updateByIpAddress(@Param("commentBlackList") CommentBlackList commentBlackList);
}

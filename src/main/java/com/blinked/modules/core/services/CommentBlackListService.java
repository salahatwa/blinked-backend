package com.blinked.modules.core.services;

import com.blinked.modules.core.model.entities.CommentBlackList;
import com.blinked.modules.core.model.enums.CommentViolationTypeEnum;
import com.blinked.modules.core.services.base.CrudService;

/**
 * Comment BlackList Service
 *
 * @date 2020/1/3
 */
public interface CommentBlackListService extends CrudService<CommentBlackList, Long> {
	/**
	 *
	 * @return boolean
	 */
	CommentViolationTypeEnum commentsBanStatus(String ipAddress);
}

package test.modules.core.services.impl;
//package com.blinked.modules.core.services.impl;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.Optional;
//
//import org.joda.time.DateTimeUtils;
//import org.springframework.stereotype.Service;
//
//import com.blinked.modules.core.model.entities.CommentBlackList;
//import com.blinked.modules.core.model.enums.CommentViolationTypeEnum;
//import com.blinked.modules.core.model.properties.CommentProperties;
//import com.blinked.modules.core.repositories.CommentBlackListRepository;
//import com.blinked.modules.core.repositories.PostCommentRepository;
//import com.blinked.modules.core.services.CommentBlackListService;
//import com.blinked.modules.core.services.OptionService;
//import com.blinked.modules.core.services.base.AbstractCrudService;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * Comment BlackList Service Implements
// *
// * @date 2020/1/3
// */
//
//@Service
//@Slf4j
//public class CommentBlackListServiceImpl extends AbstractCrudService<CommentBlackList, Long>
//		implements CommentBlackListService {
//	private final CommentBlackListRepository commentBlackListRepository;
//	private final PostCommentRepository postCommentRepository;
//	private final OptionService optionService;
//
//	public CommentBlackListServiceImpl(CommentBlackListRepository commentBlackListRepository,
//			PostCommentRepository postCommentRepository, OptionService optionService) {
//		super(commentBlackListRepository);
//		this.commentBlackListRepository = commentBlackListRepository;
//		this.postCommentRepository = postCommentRepository;
//		this.optionService = optionService;
//	}
//
//	@Override
//	public CommentViolationTypeEnum commentsBanStatus(String ipAddress) {
//		/*
//		 * N=Configurable later 1. Get the number of comments; 2. Determine whether the
//		 * specified number of times is exceeded within N minutes, and after exceeding
//		 * the limit, you can comment again every N minutes; 3. If there are multiple
//		 * comments within N minutes, it can be identified as a malicious attacker; 4.
//		 * Ban the malicious attacker for N minutes;
//		 */
//		Optional<CommentBlackList> blackList = commentBlackListRepository.findByIpAddress(ipAddress);
//		LocalDateTime now = LocalDateTime.now();
//		Date endTime = new Date(DateTimeUtils.toEpochMilli(now));
//		Integer banTime = optionService.getByPropertyOrDefault(CommentProperties.COMMENT_BAN_TIME, Integer.class, 10);
//		Date startTime = new Date(DateTimeUtils.toEpochMilli(now.minusMinutes(banTime)));
//		Integer range = optionService.getByPropertyOrDefault(CommentProperties.COMMENT_RANGE, Integer.class, 30);
//		boolean isPresent = postCommentRepository.countByIpAndTime(ipAddress, startTime, endTime) >= range;
//		if (isPresent && blackList.isPresent()) {
//			update(now, blackList.get(), banTime);
//			return CommentViolationTypeEnum.FREQUENTLY;
//		} else if (isPresent) {
//			CommentBlackList commentBlackList = CommentBlackList.builder().banTime(getBanTime(now, banTime))
//					.ipAddress(ipAddress).build();
//			super.create(commentBlackList);
//			return CommentViolationTypeEnum.FREQUENTLY;
//		}
//		return CommentViolationTypeEnum.NORMAL;
//	}
//
//	private void update(LocalDateTime localDateTime, CommentBlackList blackList, Integer banTime) {
//		blackList.setBanTime(getBanTime(localDateTime, banTime));
//		int updateResult = commentBlackListRepository.updateByIpAddress(blackList);
//		Optional.of(updateResult).filter(result -> result <= 0).ifPresent(result -> log.error("更新评论封禁时间失败"));
//	}
//
//	private Date getBanTime(LocalDateTime localDateTime, Integer banTime) {
//		return new Date(DateTimeUtils.toEpochMilli(localDateTime.plusMinutes(banTime)));
//	}
//}

package com.blinked.modules.core.model.support;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * PostComment page implementation.
 *
 * @author ssatwa
 * @date 3/25/19
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CommentPage<T> extends PageImpl<T> {

	/**
	 * Total comment (Contains sub comments)
	 */
	private final long commentCount;

	public CommentPage(List<T> content, Pageable pageable, long topTotal, long commentCount) {
		super(content, pageable, topTotal);

		this.commentCount = commentCount;
	}

	public CommentPage(List<T> content, long commentCount) {
		super(content);

		this.commentCount = commentCount;
	}
}

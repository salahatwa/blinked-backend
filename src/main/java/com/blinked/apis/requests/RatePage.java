package com.blinked.apis.requests;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * PostComment page implementation.
 *
 * @author ssatwa
 * @date 3/25/19
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RatePage<T> extends PageImpl<T> {

	/**
	 * Total comment (Contains sub comments)
	 */
	private final long rateCount;

	public RatePage(List<T> content, Pageable pageable, long topTotal, long rateCount) {
		super(content, pageable, topTotal);

		this.rateCount = rateCount;
	}

	public RatePage(List<T> content, long rateCount) {
		super(content);

		this.rateCount = rateCount;
	}
}

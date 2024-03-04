package com.blinked.apis.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;

import com.api.common.model.ApiRs;

/**
 * Year archive vo.
 *
 * @author ssatwa
 * @date 4/2/19
 */
@Data
@ToString
@EqualsAndHashCode
public class ArchiveYearVO extends ApiRs{

	private Integer year;

	private List<ProductListVO> products;

	public static class ArchiveComparator implements Comparator<ArchiveYearVO> {

		@Override
		public int compare(ArchiveYearVO left, ArchiveYearVO right) {
			return right.getYear() - left.getYear();
		}
	}
}

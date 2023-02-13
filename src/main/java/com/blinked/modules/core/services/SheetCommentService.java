package com.blinked.modules.core.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.blinked.modules.core.model.entities.SheetComment;
import com.blinked.modules.core.model.vo.SheetCommentWithSheetVO;
import com.blinked.modules.core.services.base.BaseCommentService;

/**
 * Sheet comment service interface.
 *
 * @author ssatwa
 * @author ssatwa
 * @date 2019-04-24
 */
public interface SheetCommentService extends BaseCommentService<SheetComment> {

	/**
	 * Converts to with sheet vo
	 *
	 * @param comment comment
	 * @return a comment with sheet vo
	 */
	@NonNull
	SheetCommentWithSheetVO convertToWithSheetVo(@NonNull SheetComment comment);

	/**
	 * Converts to with sheet vo
	 *
	 * @param sheetComments sheet comments
	 * @return a sheet comments with sheet vo
	 */
	@NonNull
	List<SheetCommentWithSheetVO> convertToWithSheetVo(@Nullable List<SheetComment> sheetComments);

	/**
	 * Converts to with sheet vo
	 *
	 * @param sheetCommentPage sheet comments
	 * @return a page of sheet comments with sheet vo
	 */
	@NonNull
	Page<SheetCommentWithSheetVO> convertToWithSheetVo(@NonNull Page<SheetComment> sheetCommentPage);
}

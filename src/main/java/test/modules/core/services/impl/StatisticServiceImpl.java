package test.modules.core.services.impl;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import com.blinked.modules.core.model.dto.StatisticDTO;
import com.blinked.modules.core.model.dto.StatisticWithUserDTO;
import com.blinked.modules.core.model.dto.UserDTO;
import com.blinked.modules.core.model.enums.CommentStatus;
import com.blinked.modules.core.model.enums.PostStatus;
import com.blinked.modules.core.services.CategoryService;
import com.blinked.modules.core.services.OptionService;
import com.blinked.modules.core.services.PostCommentService;
import com.blinked.modules.core.services.PostService;
import com.blinked.modules.core.services.SheetCommentService;
import com.blinked.modules.core.services.SheetService;
import com.blinked.modules.core.services.StatisticService;
import com.blinked.modules.core.services.TagService;
import com.blinked.modules.core.services.UserService;
import com.blinked.modules.user.entities.User;

/**
 * Statistic service implementation.
 *
 * @author ssatwa
 * @date 2019-12-16
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    private final PostService postService;

    private final SheetService sheetService;


    private final PostCommentService postCommentService;

    private final SheetCommentService sheetCommentService;


    private final OptionService optionService;


    private final CategoryService categoryService;

    private final TagService tagService;

    private final UserService userService;

    public StatisticServiceImpl(PostService postService,
            SheetService sheetService,
            PostCommentService postCommentService,
            SheetCommentService sheetCommentService,
            OptionService optionService,
            CategoryService categoryService,
            TagService tagService,
            UserService userService) {
        this.postService = postService;
        this.sheetService = sheetService;
        this.postCommentService = postCommentService;
        this.sheetCommentService = sheetCommentService;
        this.optionService = optionService;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.userService = userService;
    }

    @Override
    public StatisticDTO getStatistic() {
        StatisticDTO statisticDTO = new StatisticDTO();
        statisticDTO.setPostCount(postService.countByStatus(PostStatus.PUBLISHED));

        // Handle comment count
        long postCommentCount = postCommentService.countByStatus(CommentStatus.PUBLISHED);
        long sheetCommentCount = sheetCommentService.countByStatus(CommentStatus.PUBLISHED);

        statisticDTO.setCommentCount(postCommentCount + sheetCommentCount );
        statisticDTO.setTagCount(tagService.count());
        statisticDTO.setCategoryCount(categoryService.count());

        long birthday = optionService.getBirthday();
        long days = (System.currentTimeMillis() - birthday) / (1000 * 24 * 3600);
        statisticDTO.setEstablishDays(days);
        statisticDTO.setBirthday(birthday);

        statisticDTO.setVisitCount(postService.countVisit() + sheetService.countVisit());
        statisticDTO.setLikeCount(postService.countLike() + sheetService.countLike());
        return statisticDTO;
    }

    @Override
    public StatisticWithUserDTO getStatisticWithUser() {

        StatisticDTO statisticDTO = getStatistic();

        StatisticWithUserDTO statisticWithUserDTO = new StatisticWithUserDTO();
        statisticWithUserDTO.convertFrom(statisticDTO);

        User user = userService.getCurrentUser().orElseThrow(() -> new ServiceException("未查询到博主信息"));
        statisticWithUserDTO.setUser(new UserDTO().convertFrom(user));

        return statisticWithUserDTO;
    }
}

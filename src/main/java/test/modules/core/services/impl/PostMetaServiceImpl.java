package test.modules.core.services.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import com.blinked.modules.core.exceptions.NotFoundException;
import com.blinked.modules.core.model.entities.PostMeta;
import com.blinked.modules.core.repositories.PostRepository;
import com.blinked.modules.core.repositories.base.BaseMetaRepository;
import com.blinked.modules.core.services.PostMetaService;

import lombok.extern.slf4j.Slf4j;

/**
 * Post meta service implementation class.
 *
 * @author ssatwa
 * @date 2019-08-04
 */
@Slf4j
@Service
public class PostMetaServiceImpl extends BaseMetaServiceImpl<PostMeta> implements PostMetaService {

	private final PostRepository postRepository;

	public PostMetaServiceImpl(BaseMetaRepository<PostMeta> baseMetaRepository, PostRepository postRepository) {
		super(baseMetaRepository);
		this.postRepository = postRepository;
	}

	@Override
	public void validateTarget(@NotNull Integer postId) {
		postRepository.findById(postId).orElseThrow(
				() -> new NotFoundException("Can't find information about this article").setErrorData(postId));
	}
}

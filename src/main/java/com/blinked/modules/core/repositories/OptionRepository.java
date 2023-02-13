package com.blinked.modules.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.blinked.modules.core.model.entities.Option;
import com.blinked.modules.core.repositories.base.BaseRepository;

/**
 * Option repository.
 *
 * @author ssatwa
 * @author ssatwa
 * @date 2019-03-20
 */
public interface OptionRepository extends BaseRepository<Option, Integer>, JpaSpecificationExecutor<Option> {

    /**
     * Query option by key
     *
     * @param key key
     * @return Option
     */
    Optional<Option> findByKey(String key);

    /**
     * Delete option by key
     *
     * @param key key
     */
    void deleteByKey(String key);
}

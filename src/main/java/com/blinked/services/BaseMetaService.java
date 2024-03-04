package com.blinked.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.lang.NonNull;

import com.api.common.repo.CrudService;
import com.blinked.apis.requests.BaseMetaParam;
import com.blinked.entities.BaseMeta;
import com.blinked.entities.dto.BaseMetaDTO;

/**
 * Base meta service interface.
 *
 * @author ssatwa
 * @date 2019-08-04
 */
public interface BaseMetaService<META extends BaseMeta> extends CrudService<META, Long> {

	/**
	 * Creates by product metas and product id.
	 *
	 * @param productId product id must not be null
	 * @param metas     metas must not be null
	 * @return a list of product meta
	 */
	List<META> createOrUpdateByProductId(@NonNull Integer productId, Set<META> metas);

	/**
	 * Remove product metas by product id.
	 *
	 * @param productId product id must not be null
	 * @return a list of product meta
	 */
	List<META> removeByProductId(@NonNull Integer productId);

	/**
	 * Lists product metas as map.
	 *
	 * @param productIds product ids must not be null
	 * @return a map of product meta
	 */
	Map<Integer, List<META>> listProductMetaAsMap(@NonNull Set<Integer> productIds);

	/**
	 * Lists metas by product id.
	 *
	 * @param productId product id must not be null
	 * @return a list of meta
	 */
	@NonNull
	List<META> listBy(@NonNull Integer productId);

	/**
	 * Creates a meta by meta.
	 *
	 * @param meta meta must not be null
	 * @return created meta
	 */
	@NonNull
	@Override
	META create(@NonNull META meta);

	/**
	 * Creates a meta by meta param.
	 *
	 * @param metaParam meta param must not be null
	 * @return created meta
	 */
	@NonNull
	META createBy(@NonNull BaseMetaParam<META> metaParam);

	/**
	 * Target validation.
	 *
	 * @param targetId target id must not be null (product id, sheet id)
	 */
	void validateTarget(@NonNull Integer targetId);

	/**
	 * Convert to map.
	 *
	 * @param metas a list of metas
	 * @return a map of metas
	 */
	Map<String, Object> convertToMap(List<META> metas);

	/**
	 * Convert ProductMeta to ProductMetaDTO.
	 *
	 * @param productMeta product meta must not be null
	 * @return product meta vo
	 */
	@NonNull
	BaseMetaDTO convertTo(@NonNull META productMeta);

	/**
	 * Convert list of ProductMeta to list of ProductMetaDTO.
	 *
	 * @param productMetaList product meta list must not be null
	 * @return a list of product meta dto
	 */
	@NonNull
	List<BaseMetaDTO> convertTo(@NonNull List<META> productMetaList);

}

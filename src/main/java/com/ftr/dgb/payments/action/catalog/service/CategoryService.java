package com.ftr.dgb.payments.action.catalog.service;

import com.ftr.dgb.payments.action.catalog.service.dto.CategoryDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.ftr.dgb.payments.action.catalog.domain.Category}.
 */
public interface CategoryService {

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save.
     * @return the persisted entity.
     */
    CategoryDTO save(CategoryDTO categoryDTO);

    /**
     * Get all the categories.
     *
     * @return the list of entities.
     */
    List<CategoryDTO> findAll();


    /**
     * Get the "id" category.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoryDTO> findOne(String id);

    /**
     * Delete the "id" category.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}

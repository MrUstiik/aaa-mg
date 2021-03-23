package com.ftr.dgb.payments.action.catalog.service;

import com.ftr.dgb.payments.action.catalog.service.dto.CategoryActionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.ftr.dgb.payments.action.catalog.domain.CategoryAction}.
 */
public interface CategoryActionService {

    /**
     * Save a categoryAction.
     *
     * @param categoryActionDTO the entity to save.
     * @return the persisted entity.
     */
    CategoryActionDTO save(CategoryActionDTO categoryActionDTO);

    /**
     * Get all the categoryActions.
     *
     * @return the list of entities.
     */
    List<CategoryActionDTO> findAll();


    /**
     * Get the "id" categoryAction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoryActionDTO> findOne(String id);

    /**
     * Delete the "id" categoryAction.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}

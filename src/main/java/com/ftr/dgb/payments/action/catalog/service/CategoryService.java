package com.ftr.dgb.payments.action.catalog.service;

import com.ftr.dgb.payments.action.catalog.service.dto.CategoryDto;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.ftr.dgb.payments.action.catalog.domain.Category}.
 */
public interface CategoryService {
    /**
     * Save a category.
     *
     * @param categoryDto the entity to save.
     * @return the persisted entity.
     */
    Mono<CategoryDto> save(CategoryDto categoryDto);

    /**
     * Get all the categories.
     *
     * @return the list of entities.
     */
    Flux<CategoryDto> findAll();

    /**
     * Returns the number of categories available.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" category.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<CategoryDto> findOne(String id);

    /**
     * Delete the "id" category.
     *
     * @param id the id of the entity.
     */
    Mono<Void> delete(String id);
}

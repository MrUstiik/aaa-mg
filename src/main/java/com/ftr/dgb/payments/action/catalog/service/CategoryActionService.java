package com.ftr.dgb.payments.action.catalog.service;

import com.ftr.dgb.payments.action.catalog.service.dto.CategoryActionDto;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.ftr.dgb.payments.action.catalog.domain.CategoryAction}.
 */
public interface CategoryActionService {
    /**
     * Save a categoryAction.
     *
     * @param categoryActionDto the entity to save.
     * @return the persisted entity.
     */
    Mono<CategoryActionDto> save(CategoryActionDto categoryActionDto);

    /**
     * Get all the categoryActions.
     *
     * @return the list of entities.
     */
    Flux<CategoryActionDto> findAll();

    /**
     * Returns the number of categoryActions available.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" categoryAction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<CategoryActionDto> findOne(String id);

    /**
     * Delete the "id" categoryAction.
     *
     * @param id the id of the entity.
     */
    Mono<Void> delete(String id);
}

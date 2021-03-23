package com.ftr.dgb.payments.action.catalog.web.rest;

import com.ftr.dgb.payments.action.catalog.service.CategoryService;
import com.ftr.dgb.payments.action.catalog.service.dto.CategoryDto;
import com.ftr.dgb.payments.action.catalog.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.reactive.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing {@link com.ftr.dgb.payments.action.catalog.domain.Category}.
 */
@RestController
@RequestMapping("/api")
public class CategoryResource {
    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    private static final String ENTITY_NAME = "actionCatalogServiceCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * {@code POST  /categories} : Create a new category.
     *
     * @param categoryDto the categoryDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryDto, or with status {@code 400 (Bad Request)} if the category has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categories")
    public Mono<ResponseEntity<CategoryDto>> createCategory(@Valid @RequestBody CategoryDto categoryDto) throws URISyntaxException {
        log.debug("REST request to save Category : {}", categoryDto);
        if (categoryDto.getId() != null) {
            throw new BadRequestAlertException("A new category cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return categoryService
            .save(categoryDto)
            .map(
                result -> {
                    try {
                        return ResponseEntity
                            .created(new URI("/api/categories/" + result.getId()))
                            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                            .body(result);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }
            );
    }

    /**
     * {@code PUT  /categories} : Updates an existing category.
     *
     * @param categoryDto the categoryDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryDto,
     * or with status {@code 400 (Bad Request)} if the categoryDto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categories")
    public Mono<ResponseEntity<CategoryDto>> updateCategory(@Valid @RequestBody CategoryDto categoryDto) throws URISyntaxException {
        log.debug("REST request to update Category : {}", categoryDto);
        if (categoryDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        return categoryService
            .save(categoryDto)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
            .map(
                result ->
                    ResponseEntity
                        .ok()
                        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result)
            );
    }

    /**
     * {@code GET  /categories} : get all the categories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping("/categories")
    public Mono<List<CategoryDto>> getAllCategories() {
        log.debug("REST request to get all Categories");
        return categoryService.findAll().collectList();
    }

    /**
     * {@code GET  /categories} : get all the categories as a stream.
     * @return the {@link Flux} of categories.
     */
    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<CategoryDto> getAllCategoriesAsStream() {
        log.debug("REST request to get all Categories as a stream");
        return categoryService.findAll();
    }

    /**
     * {@code GET  /categories/:id} : get the "id" category.
     *
     * @param id the id of the categoryDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categories/{id}")
    public Mono<ResponseEntity<CategoryDto>> getCategory(@PathVariable String id) {
        log.debug("REST request to get Category : {}", id);
        Mono<CategoryDto> categoryDto = categoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryDto);
    }

    /**
     * {@code DELETE  /categories/:id} : delete the "id" category.
     *
     * @param id the id of the categoryDto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categories/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteCategory(@PathVariable String id) {
        log.debug("REST request to delete Category : {}", id);
        return categoryService
            .delete(id)
            .map(
                result ->
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
            );
    }
}

package com.ftr.dgb.payments.action.catalog.web.rest;

import com.ftr.dgb.payments.action.catalog.service.CategoryActionService;
import com.ftr.dgb.payments.action.catalog.web.rest.errors.BadRequestAlertException;
import com.ftr.dgb.payments.action.catalog.service.dto.CategoryActionDto;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.reactive.ResponseUtil;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ftr.dgb.payments.action.catalog.domain.CategoryAction}.
 */
@RestController
@RequestMapping("/api")
public class CategoryActionResource {

    private final Logger log = LoggerFactory.getLogger(CategoryActionResource.class);

    private static final String ENTITY_NAME = "actionCatalogServiceCategoryAction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryActionService categoryActionService;

    public CategoryActionResource(CategoryActionService categoryActionService) {
        this.categoryActionService = categoryActionService;
    }

    /**
     * {@code POST  /category-actions} : Create a new categoryAction.
     *
     * @param categoryActionDto the categoryActionDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryActionDto, or with status {@code 400 (Bad Request)} if the categoryAction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-actions")
    public Mono<ResponseEntity<CategoryActionDto>> createCategoryAction(@Valid @RequestBody CategoryActionDto categoryActionDto) throws URISyntaxException {
        log.debug("REST request to save CategoryAction : {}", categoryActionDto);
        if (categoryActionDto.getId() != null) {
            throw new BadRequestAlertException("A new categoryAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return categoryActionService.save(categoryActionDto)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/category-actions/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /category-actions} : Updates an existing categoryAction.
     *
     * @param categoryActionDto the categoryActionDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryActionDto,
     * or with status {@code 400 (Bad Request)} if the categoryActionDto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryActionDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-actions")
    public Mono<ResponseEntity<CategoryActionDto>> updateCategoryAction(@Valid @RequestBody CategoryActionDto categoryActionDto) throws URISyntaxException {
        log.debug("REST request to update CategoryAction : {}", categoryActionDto);
        if (categoryActionDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        return categoryActionService.save(categoryActionDto)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
            .map(result -> ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId()))
                .body(result)
            );
    }

    /**
     * {@code GET  /category-actions} : get all the categoryActions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoryActions in body.
     */
    @GetMapping("/category-actions")
    public Mono<List<CategoryActionDto>> getAllCategoryActions() {
        log.debug("REST request to get all CategoryActions");
        return categoryActionService.findAll().collectList();
    }

    /**
     * {@code GET  /category-actions} : get all the categoryActions as a stream.
     * @return the {@link Flux} of categoryActions.
     */
    @GetMapping(value = "/category-actions", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<CategoryActionDto> getAllCategoryActionsAsStream() {
        log.debug("REST request to get all CategoryActions as a stream");
        return categoryActionService.findAll();
    }

    /**
     * {@code GET  /category-actions/:id} : get the "id" categoryAction.
     *
     * @param id the id of the categoryActionDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryActionDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-actions/{id}")
    public Mono<ResponseEntity<CategoryActionDto>> getCategoryAction(@PathVariable String id) {
        log.debug("REST request to get CategoryAction : {}", id);
        Mono<CategoryActionDto> categoryActionDto = categoryActionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryActionDto);
    }

    /**
     * {@code DELETE  /category-actions/:id} : delete the "id" categoryAction.
     *
     * @param id the id of the categoryActionDto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-actions/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteCategoryAction(@PathVariable String id) {
        log.debug("REST request to delete CategoryAction : {}", id);
        return categoryActionService.delete(id)            .map(result -> ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
        );
    }
}

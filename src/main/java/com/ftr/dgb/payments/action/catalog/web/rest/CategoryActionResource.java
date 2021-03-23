package com.ftr.dgb.payments.action.catalog.web.rest;

import com.ftr.dgb.payments.action.catalog.service.CategoryActionService;
import com.ftr.dgb.payments.action.catalog.web.rest.errors.BadRequestAlertException;
import com.ftr.dgb.payments.action.catalog.service.dto.CategoryActionDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @param categoryActionDTO the categoryActionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryActionDTO, or with status {@code 400 (Bad Request)} if the categoryAction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-actions")
    public ResponseEntity<CategoryActionDTO> createCategoryAction(@Valid @RequestBody CategoryActionDTO categoryActionDTO) throws URISyntaxException {
        log.debug("REST request to save CategoryAction : {}", categoryActionDTO);
        if (categoryActionDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoryAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryActionDTO result = categoryActionService.save(categoryActionDTO);
        return ResponseEntity.created(new URI("/api/category-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /category-actions} : Updates an existing categoryAction.
     *
     * @param categoryActionDTO the categoryActionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryActionDTO,
     * or with status {@code 400 (Bad Request)} if the categoryActionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryActionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-actions")
    public ResponseEntity<CategoryActionDTO> updateCategoryAction(@Valid @RequestBody CategoryActionDTO categoryActionDTO) throws URISyntaxException {
        log.debug("REST request to update CategoryAction : {}", categoryActionDTO);
        if (categoryActionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoryActionDTO result = categoryActionService.save(categoryActionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoryActionDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /category-actions} : get all the categoryActions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoryActions in body.
     */
    @GetMapping("/category-actions")
    public List<CategoryActionDTO> getAllCategoryActions() {
        log.debug("REST request to get all CategoryActions");
        return categoryActionService.findAll();
    }

    /**
     * {@code GET  /category-actions/:id} : get the "id" categoryAction.
     *
     * @param id the id of the categoryActionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryActionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-actions/{id}")
    public ResponseEntity<CategoryActionDTO> getCategoryAction(@PathVariable String id) {
        log.debug("REST request to get CategoryAction : {}", id);
        Optional<CategoryActionDTO> categoryActionDTO = categoryActionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryActionDTO);
    }

    /**
     * {@code DELETE  /category-actions/:id} : delete the "id" categoryAction.
     *
     * @param id the id of the categoryActionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-actions/{id}")
    public ResponseEntity<Void> deleteCategoryAction(@PathVariable String id) {
        log.debug("REST request to delete CategoryAction : {}", id);
        categoryActionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}

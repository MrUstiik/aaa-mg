package com.ftr.dgb.payments.action.catalog.service.impl;

import com.ftr.dgb.payments.action.catalog.domain.Category;
import com.ftr.dgb.payments.action.catalog.repository.CategoryRepository;
import com.ftr.dgb.payments.action.catalog.service.CategoryService;
import com.ftr.dgb.payments.action.catalog.service.dto.CategoryDto;
import com.ftr.dgb.payments.action.catalog.service.mapper.CategoryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Category}.
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Mono<CategoryDto> save(CategoryDto categoryDto) {
        log.debug("Request to save Category : {}", categoryDto);
        return categoryRepository.save(categoryMapper.toEntity(categoryDto)).map(categoryMapper::toDto);
    }

    @Override
    public Flux<CategoryDto> findAll() {
        log.debug("Request to get all Categories");
        return categoryRepository.findAll().map(categoryMapper::toDto);
    }

    public Mono<Long> countAll() {
        return categoryRepository.count();
    }

    @Override
    public Mono<CategoryDto> findOne(String id) {
        log.debug("Request to get Category : {}", id);
        return categoryRepository.findById(id).map(categoryMapper::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Category : {}", id);
        return categoryRepository.deleteById(id);
    }
}

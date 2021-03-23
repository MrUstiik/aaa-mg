package com.ftr.dgb.payments.action.catalog.service.impl;

import com.ftr.dgb.payments.action.catalog.domain.CategoryAction;
import com.ftr.dgb.payments.action.catalog.repository.CategoryActionRepository;
import com.ftr.dgb.payments.action.catalog.service.CategoryActionService;
import com.ftr.dgb.payments.action.catalog.service.dto.CategoryActionDto;
import com.ftr.dgb.payments.action.catalog.service.mapper.CategoryActionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link CategoryAction}.
 */
@Service
public class CategoryActionServiceImpl implements CategoryActionService {
    private final Logger log = LoggerFactory.getLogger(CategoryActionServiceImpl.class);

    private final CategoryActionRepository categoryActionRepository;

    private final CategoryActionMapper categoryActionMapper;

    public CategoryActionServiceImpl(CategoryActionRepository categoryActionRepository, CategoryActionMapper categoryActionMapper) {
        this.categoryActionRepository = categoryActionRepository;
        this.categoryActionMapper = categoryActionMapper;
    }

    @Override
    public Mono<CategoryActionDto> save(CategoryActionDto categoryActionDto) {
        log.debug("Request to save CategoryAction : {}", categoryActionDto);
        return categoryActionRepository.save(categoryActionMapper.toEntity(categoryActionDto)).map(categoryActionMapper::toDto);
    }

    @Override
    public Flux<CategoryActionDto> findAll() {
        log.debug("Request to get all CategoryActions");
        return categoryActionRepository.findAll().map(categoryActionMapper::toDto);
    }

    public Mono<Long> countAll() {
        return categoryActionRepository.count();
    }

    @Override
    public Mono<CategoryActionDto> findOne(String id) {
        log.debug("Request to get CategoryAction : {}", id);
        return categoryActionRepository.findById(id).map(categoryActionMapper::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        log.debug("Request to delete CategoryAction : {}", id);
        return categoryActionRepository.deleteById(id);
    }
}

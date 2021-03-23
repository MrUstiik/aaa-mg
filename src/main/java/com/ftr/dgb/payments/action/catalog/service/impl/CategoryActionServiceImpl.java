package com.ftr.dgb.payments.action.catalog.service.impl;

import com.ftr.dgb.payments.action.catalog.service.CategoryActionService;
import com.ftr.dgb.payments.action.catalog.domain.CategoryAction;
import com.ftr.dgb.payments.action.catalog.repository.CategoryActionRepository;
import com.ftr.dgb.payments.action.catalog.service.dto.CategoryActionDTO;
import com.ftr.dgb.payments.action.catalog.service.mapper.CategoryActionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public CategoryActionDTO save(CategoryActionDTO categoryActionDTO) {
        log.debug("Request to save CategoryAction : {}", categoryActionDTO);
        CategoryAction categoryAction = categoryActionMapper.toEntity(categoryActionDTO);
        categoryAction = categoryActionRepository.save(categoryAction);
        return categoryActionMapper.toDto(categoryAction);
    }

    @Override
    public List<CategoryActionDTO> findAll() {
        log.debug("Request to get all CategoryActions");
        return categoryActionRepository.findAll().stream()
            .map(categoryActionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    public Optional<CategoryActionDTO> findOne(String id) {
        log.debug("Request to get CategoryAction : {}", id);
        return categoryActionRepository.findById(id)
            .map(categoryActionMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete CategoryAction : {}", id);
        categoryActionRepository.deleteById(id);
    }
}

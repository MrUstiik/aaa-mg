package com.ftr.dgb.payments.action.catalog.service.impl;

import com.ftr.dgb.payments.action.catalog.service.CategoryService;
import com.ftr.dgb.payments.action.catalog.domain.Category;
import com.ftr.dgb.payments.action.catalog.repository.CategoryRepository;
import com.ftr.dgb.payments.action.catalog.service.dto.CategoryDTO;
import com.ftr.dgb.payments.action.catalog.service.mapper.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public CategoryDTO save(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDTO> findAll() {
        log.debug("Request to get all Categories");
        return categoryRepository.findAll().stream()
            .map(categoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    public Optional<CategoryDTO> findOne(String id) {
        log.debug("Request to get Category : {}", id);
        return categoryRepository.findById(id)
            .map(categoryMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.deleteById(id);
    }
}

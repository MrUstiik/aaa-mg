package com.ftr.dgb.payments.action.catalog.service.mapper;

import com.ftr.dgb.payments.action.catalog.domain.*;
import com.ftr.dgb.payments.action.catalog.service.dto.CategoryDto;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDto}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper<CategoryDto, Category> {
    default Category fromId(String id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}

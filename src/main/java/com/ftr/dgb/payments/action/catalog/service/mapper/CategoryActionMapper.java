package com.ftr.dgb.payments.action.catalog.service.mapper;


import com.ftr.dgb.payments.action.catalog.domain.*;
import com.ftr.dgb.payments.action.catalog.service.dto.CategoryActionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoryAction} and its DTO {@link CategoryActionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryActionMapper extends EntityMapper<CategoryActionDTO, CategoryAction> {



    default CategoryAction fromId(String id) {
        if (id == null) {
            return null;
        }
        CategoryAction categoryAction = new CategoryAction();
        categoryAction.setId(id);
        return categoryAction;
    }
}

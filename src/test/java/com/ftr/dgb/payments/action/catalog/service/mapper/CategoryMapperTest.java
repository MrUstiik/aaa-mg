package com.ftr.dgb.payments.action.catalog.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryMapperTest {
    private CategoryMapper categoryMapper;

    @BeforeEach
    public void setUp() {
        categoryMapper = new CategoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(categoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(categoryMapper.fromId(null)).isNull();
    }
}

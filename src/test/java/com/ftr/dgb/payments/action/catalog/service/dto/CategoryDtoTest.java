package com.ftr.dgb.payments.action.catalog.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ftr.dgb.payments.action.catalog.web.rest.TestUtil;

public class CategoryDtoTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryDto.class);
        CategoryDto categoryDto1 = new CategoryDto();
        categoryDto1.setId("id1");
        CategoryDto categoryDto2 = new CategoryDto();
        assertThat(categoryDto1).isNotEqualTo(categoryDto2);
        categoryDto2.setId(categoryDto1.getId());
        assertThat(categoryDto1).isEqualTo(categoryDto2);
        categoryDto2.setId("id2");
        assertThat(categoryDto1).isNotEqualTo(categoryDto2);
        categoryDto1.setId(null);
        assertThat(categoryDto1).isNotEqualTo(categoryDto2);
    }
}

package com.ftr.dgb.payments.action.catalog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ftr.dgb.payments.action.catalog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class CategoryActionDtoTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryActionDto.class);
        CategoryActionDto categoryActionDto1 = new CategoryActionDto();
        categoryActionDto1.setId("id1");
        CategoryActionDto categoryActionDto2 = new CategoryActionDto();
        assertThat(categoryActionDto1).isNotEqualTo(categoryActionDto2);
        categoryActionDto2.setId(categoryActionDto1.getId());
        assertThat(categoryActionDto1).isEqualTo(categoryActionDto2);
        categoryActionDto2.setId("id2");
        assertThat(categoryActionDto1).isNotEqualTo(categoryActionDto2);
        categoryActionDto1.setId(null);
        assertThat(categoryActionDto1).isNotEqualTo(categoryActionDto2);
    }
}

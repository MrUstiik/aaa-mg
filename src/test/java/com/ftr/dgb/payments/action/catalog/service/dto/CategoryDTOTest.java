package com.ftr.dgb.payments.action.catalog.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ftr.dgb.payments.action.catalog.web.rest.TestUtil;

public class CategoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryDTO.class);
        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setId("id1");
        CategoryDTO categoryDTO2 = new CategoryDTO();
        assertThat(categoryDTO1).isNotEqualTo(categoryDTO2);
        categoryDTO2.setId(categoryDTO1.getId());
        assertThat(categoryDTO1).isEqualTo(categoryDTO2);
        categoryDTO2.setId("id2");
        assertThat(categoryDTO1).isNotEqualTo(categoryDTO2);
        categoryDTO1.setId(null);
        assertThat(categoryDTO1).isNotEqualTo(categoryDTO2);
    }
}

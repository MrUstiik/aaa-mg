package com.ftr.dgb.payments.action.catalog.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ftr.dgb.payments.action.catalog.web.rest.TestUtil;

public class CategoryActionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryActionDTO.class);
        CategoryActionDTO categoryActionDTO1 = new CategoryActionDTO();
        categoryActionDTO1.setId("id1");
        CategoryActionDTO categoryActionDTO2 = new CategoryActionDTO();
        assertThat(categoryActionDTO1).isNotEqualTo(categoryActionDTO2);
        categoryActionDTO2.setId(categoryActionDTO1.getId());
        assertThat(categoryActionDTO1).isEqualTo(categoryActionDTO2);
        categoryActionDTO2.setId("id2");
        assertThat(categoryActionDTO1).isNotEqualTo(categoryActionDTO2);
        categoryActionDTO1.setId(null);
        assertThat(categoryActionDTO1).isNotEqualTo(categoryActionDTO2);
    }
}

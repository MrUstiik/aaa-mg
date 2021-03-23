package com.ftr.dgb.payments.action.catalog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ftr.dgb.payments.action.catalog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Category.class);
        Category category1 = new Category();
        category1.setId("id1");
        Category category2 = new Category();
        category2.setId(category1.getId());
        assertThat(category1).isEqualTo(category2);
        category2.setId("id2");
        assertThat(category1).isNotEqualTo(category2);
        category1.setId(null);
        assertThat(category1).isNotEqualTo(category2);
    }
}

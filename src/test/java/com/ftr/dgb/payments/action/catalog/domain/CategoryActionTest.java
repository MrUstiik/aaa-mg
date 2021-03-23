package com.ftr.dgb.payments.action.catalog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ftr.dgb.payments.action.catalog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class CategoryActionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryAction.class);
        CategoryAction categoryAction1 = new CategoryAction();
        categoryAction1.setId("id1");
        CategoryAction categoryAction2 = new CategoryAction();
        categoryAction2.setId(categoryAction1.getId());
        assertThat(categoryAction1).isEqualTo(categoryAction2);
        categoryAction2.setId("id2");
        assertThat(categoryAction1).isNotEqualTo(categoryAction2);
        categoryAction1.setId(null);
        assertThat(categoryAction1).isNotEqualTo(categoryAction2);
    }
}

package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class LinhvucTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Linhvuc.class);
        Linhvuc linhvuc1 = new Linhvuc();
        linhvuc1.setId(1L);
        Linhvuc linhvuc2 = new Linhvuc();
        linhvuc2.setId(linhvuc1.getId());
        assertThat(linhvuc1).isEqualTo(linhvuc2);
        linhvuc2.setId(2L);
        assertThat(linhvuc1).isNotEqualTo(linhvuc2);
        linhvuc1.setId(null);
        assertThat(linhvuc1).isNotEqualTo(linhvuc2);
    }
}

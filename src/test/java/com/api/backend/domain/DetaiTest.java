package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class DetaiTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Detai.class);
        Detai detai1 = new Detai();
        detai1.setId(1L);
        Detai detai2 = new Detai();
        detai2.setId(detai1.getId());
        assertThat(detai1).isEqualTo(detai2);
        detai2.setId(2L);
        assertThat(detai1).isNotEqualTo(detai2);
        detai1.setId(null);
        assertThat(detai1).isNotEqualTo(detai2);
    }
}

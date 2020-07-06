package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class DanhgiaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Danhgia.class);
        Danhgia danhgia1 = new Danhgia();
        danhgia1.setId(1L);
        Danhgia danhgia2 = new Danhgia();
        danhgia2.setId(danhgia1.getId());
        assertThat(danhgia1).isEqualTo(danhgia2);
        danhgia2.setId(2L);
        assertThat(danhgia1).isNotEqualTo(danhgia2);
        danhgia1.setId(null);
        assertThat(danhgia1).isNotEqualTo(danhgia2);
    }
}

package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class HoidongdanhgiaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hoidongdanhgia.class);
        Hoidongdanhgia hoidongdanhgia1 = new Hoidongdanhgia();
        hoidongdanhgia1.setId(1L);
        Hoidongdanhgia hoidongdanhgia2 = new Hoidongdanhgia();
        hoidongdanhgia2.setId(hoidongdanhgia1.getId());
        assertThat(hoidongdanhgia1).isEqualTo(hoidongdanhgia2);
        hoidongdanhgia2.setId(2L);
        assertThat(hoidongdanhgia1).isNotEqualTo(hoidongdanhgia2);
        hoidongdanhgia1.setId(null);
        assertThat(hoidongdanhgia1).isNotEqualTo(hoidongdanhgia2);
    }
}

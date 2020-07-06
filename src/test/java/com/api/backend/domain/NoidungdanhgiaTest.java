package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class NoidungdanhgiaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Noidungdanhgia.class);
        Noidungdanhgia noidungdanhgia1 = new Noidungdanhgia();
        noidungdanhgia1.setId(1L);
        Noidungdanhgia noidungdanhgia2 = new Noidungdanhgia();
        noidungdanhgia2.setId(noidungdanhgia1.getId());
        assertThat(noidungdanhgia1).isEqualTo(noidungdanhgia2);
        noidungdanhgia2.setId(2L);
        assertThat(noidungdanhgia1).isNotEqualTo(noidungdanhgia2);
        noidungdanhgia1.setId(null);
        assertThat(noidungdanhgia1).isNotEqualTo(noidungdanhgia2);
    }
}

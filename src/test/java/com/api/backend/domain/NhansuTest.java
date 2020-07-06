package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class NhansuTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nhansu.class);
        Nhansu nhansu1 = new Nhansu();
        nhansu1.setId(1L);
        Nhansu nhansu2 = new Nhansu();
        nhansu2.setId(nhansu1.getId());
        assertThat(nhansu1).isEqualTo(nhansu2);
        nhansu2.setId(2L);
        assertThat(nhansu1).isNotEqualTo(nhansu2);
        nhansu1.setId(null);
        assertThat(nhansu1).isNotEqualTo(nhansu2);
    }
}

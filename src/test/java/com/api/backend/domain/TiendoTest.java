package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class TiendoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tiendo.class);
        Tiendo tiendo1 = new Tiendo();
        tiendo1.setId(1L);
        Tiendo tiendo2 = new Tiendo();
        tiendo2.setId(tiendo1.getId());
        assertThat(tiendo1).isEqualTo(tiendo2);
        tiendo2.setId(2L);
        assertThat(tiendo1).isNotEqualTo(tiendo2);
        tiendo1.setId(null);
        assertThat(tiendo1).isNotEqualTo(tiendo2);
    }
}

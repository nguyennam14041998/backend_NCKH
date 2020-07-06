package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class DonviTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Donvi.class);
        Donvi donvi1 = new Donvi();
        donvi1.setId(1L);
        Donvi donvi2 = new Donvi();
        donvi2.setId(donvi1.getId());
        assertThat(donvi1).isEqualTo(donvi2);
        donvi2.setId(2L);
        assertThat(donvi1).isNotEqualTo(donvi2);
        donvi1.setId(null);
        assertThat(donvi1).isNotEqualTo(donvi2);
    }
}

package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class HochamTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hocham.class);
        Hocham hocham1 = new Hocham();
        hocham1.setId(1L);
        Hocham hocham2 = new Hocham();
        hocham2.setId(hocham1.getId());
        assertThat(hocham1).isEqualTo(hocham2);
        hocham2.setId(2L);
        assertThat(hocham1).isNotEqualTo(hocham2);
        hocham1.setId(null);
        assertThat(hocham1).isNotEqualTo(hocham2);
    }
}

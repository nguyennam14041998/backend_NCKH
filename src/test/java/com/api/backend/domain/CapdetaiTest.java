package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class CapdetaiTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Capdetai.class);
        Capdetai capdetai1 = new Capdetai();
        capdetai1.setId(1L);
        Capdetai capdetai2 = new Capdetai();
        capdetai2.setId(capdetai1.getId());
        assertThat(capdetai1).isEqualTo(capdetai2);
        capdetai2.setId(2L);
        assertThat(capdetai1).isNotEqualTo(capdetai2);
        capdetai1.setId(null);
        assertThat(capdetai1).isNotEqualTo(capdetai2);
    }
}

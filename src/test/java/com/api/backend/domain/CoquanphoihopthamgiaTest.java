package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class CoquanphoihopthamgiaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coquanphoihopthamgia.class);
        Coquanphoihopthamgia coquanphoihopthamgia1 = new Coquanphoihopthamgia();
        coquanphoihopthamgia1.setId(1L);
        Coquanphoihopthamgia coquanphoihopthamgia2 = new Coquanphoihopthamgia();
        coquanphoihopthamgia2.setId(coquanphoihopthamgia1.getId());
        assertThat(coquanphoihopthamgia1).isEqualTo(coquanphoihopthamgia2);
        coquanphoihopthamgia2.setId(2L);
        assertThat(coquanphoihopthamgia1).isNotEqualTo(coquanphoihopthamgia2);
        coquanphoihopthamgia1.setId(null);
        assertThat(coquanphoihopthamgia1).isNotEqualTo(coquanphoihopthamgia2);
    }
}

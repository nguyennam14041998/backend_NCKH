package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class CoquanphoihopTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coquanphoihop.class);
        Coquanphoihop coquanphoihop1 = new Coquanphoihop();
        coquanphoihop1.setId(1L);
        Coquanphoihop coquanphoihop2 = new Coquanphoihop();
        coquanphoihop2.setId(coquanphoihop1.getId());
        assertThat(coquanphoihop1).isEqualTo(coquanphoihop2);
        coquanphoihop2.setId(2L);
        assertThat(coquanphoihop1).isNotEqualTo(coquanphoihop2);
        coquanphoihop1.setId(null);
        assertThat(coquanphoihop1).isNotEqualTo(coquanphoihop2);
    }
}

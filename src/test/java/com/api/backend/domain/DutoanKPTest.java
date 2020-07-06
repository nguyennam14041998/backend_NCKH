package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class DutoanKPTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DutoanKP.class);
        DutoanKP dutoanKP1 = new DutoanKP();
        dutoanKP1.setId(1L);
        DutoanKP dutoanKP2 = new DutoanKP();
        dutoanKP2.setId(dutoanKP1.getId());
        assertThat(dutoanKP1).isEqualTo(dutoanKP2);
        dutoanKP2.setId(2L);
        assertThat(dutoanKP1).isNotEqualTo(dutoanKP2);
        dutoanKP1.setId(null);
        assertThat(dutoanKP1).isNotEqualTo(dutoanKP2);
    }
}

package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class DutoanKPCTTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DutoanKPCT.class);
        DutoanKPCT dutoanKPCT1 = new DutoanKPCT();
        dutoanKPCT1.setId(1L);
        DutoanKPCT dutoanKPCT2 = new DutoanKPCT();
        dutoanKPCT2.setId(dutoanKPCT1.getId());
        assertThat(dutoanKPCT1).isEqualTo(dutoanKPCT2);
        dutoanKPCT2.setId(2L);
        assertThat(dutoanKPCT1).isNotEqualTo(dutoanKPCT2);
        dutoanKPCT1.setId(null);
        assertThat(dutoanKPCT1).isNotEqualTo(dutoanKPCT2);
    }
}

package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class NoidungDTTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoidungDT.class);
        NoidungDT noidungDT1 = new NoidungDT();
        noidungDT1.setId(1L);
        NoidungDT noidungDT2 = new NoidungDT();
        noidungDT2.setId(noidungDT1.getId());
        assertThat(noidungDT1).isEqualTo(noidungDT2);
        noidungDT2.setId(2L);
        assertThat(noidungDT1).isNotEqualTo(noidungDT2);
        noidungDT1.setId(null);
        assertThat(noidungDT1).isNotEqualTo(noidungDT2);
    }
}

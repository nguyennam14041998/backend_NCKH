package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class DanhgiaCTTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DanhgiaCT.class);
        DanhgiaCT danhgiaCT1 = new DanhgiaCT();
        danhgiaCT1.setId(1L);
        DanhgiaCT danhgiaCT2 = new DanhgiaCT();
        danhgiaCT2.setId(danhgiaCT1.getId());
        assertThat(danhgiaCT1).isEqualTo(danhgiaCT2);
        danhgiaCT2.setId(2L);
        assertThat(danhgiaCT1).isNotEqualTo(danhgiaCT2);
        danhgiaCT1.setId(null);
        assertThat(danhgiaCT1).isNotEqualTo(danhgiaCT2);
    }
}

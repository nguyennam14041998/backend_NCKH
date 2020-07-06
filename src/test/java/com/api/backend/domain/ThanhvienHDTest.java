package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class ThanhvienHDTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThanhvienHD.class);
        ThanhvienHD thanhvienHD1 = new ThanhvienHD();
        thanhvienHD1.setId(1L);
        ThanhvienHD thanhvienHD2 = new ThanhvienHD();
        thanhvienHD2.setId(thanhvienHD1.getId());
        assertThat(thanhvienHD1).isEqualTo(thanhvienHD2);
        thanhvienHD2.setId(2L);
        assertThat(thanhvienHD1).isNotEqualTo(thanhvienHD2);
        thanhvienHD1.setId(null);
        assertThat(thanhvienHD1).isNotEqualTo(thanhvienHD2);
    }
}

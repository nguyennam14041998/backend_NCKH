package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class ThanhvienHDDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThanhvienHDDTO.class);
        ThanhvienHDDTO thanhvienHDDTO1 = new ThanhvienHDDTO();
        thanhvienHDDTO1.setId(1L);
        ThanhvienHDDTO thanhvienHDDTO2 = new ThanhvienHDDTO();
        assertThat(thanhvienHDDTO1).isNotEqualTo(thanhvienHDDTO2);
        thanhvienHDDTO2.setId(thanhvienHDDTO1.getId());
        assertThat(thanhvienHDDTO1).isEqualTo(thanhvienHDDTO2);
        thanhvienHDDTO2.setId(2L);
        assertThat(thanhvienHDDTO1).isNotEqualTo(thanhvienHDDTO2);
        thanhvienHDDTO1.setId(null);
        assertThat(thanhvienHDDTO1).isNotEqualTo(thanhvienHDDTO2);
    }
}

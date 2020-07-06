package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class DanhgiaCTDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DanhgiaCTDTO.class);
        DanhgiaCTDTO danhgiaCTDTO1 = new DanhgiaCTDTO();
        danhgiaCTDTO1.setId(1L);
        DanhgiaCTDTO danhgiaCTDTO2 = new DanhgiaCTDTO();
        assertThat(danhgiaCTDTO1).isNotEqualTo(danhgiaCTDTO2);
        danhgiaCTDTO2.setId(danhgiaCTDTO1.getId());
        assertThat(danhgiaCTDTO1).isEqualTo(danhgiaCTDTO2);
        danhgiaCTDTO2.setId(2L);
        assertThat(danhgiaCTDTO1).isNotEqualTo(danhgiaCTDTO2);
        danhgiaCTDTO1.setId(null);
        assertThat(danhgiaCTDTO1).isNotEqualTo(danhgiaCTDTO2);
    }
}

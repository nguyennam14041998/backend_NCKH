package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class DanhgiaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DanhgiaDTO.class);
        DanhgiaDTO danhgiaDTO1 = new DanhgiaDTO();
        danhgiaDTO1.setId(1L);
        DanhgiaDTO danhgiaDTO2 = new DanhgiaDTO();
        assertThat(danhgiaDTO1).isNotEqualTo(danhgiaDTO2);
        danhgiaDTO2.setId(danhgiaDTO1.getId());
        assertThat(danhgiaDTO1).isEqualTo(danhgiaDTO2);
        danhgiaDTO2.setId(2L);
        assertThat(danhgiaDTO1).isNotEqualTo(danhgiaDTO2);
        danhgiaDTO1.setId(null);
        assertThat(danhgiaDTO1).isNotEqualTo(danhgiaDTO2);
    }
}

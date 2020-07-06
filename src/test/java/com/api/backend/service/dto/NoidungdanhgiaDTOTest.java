package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class NoidungdanhgiaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoidungdanhgiaDTO.class);
        NoidungdanhgiaDTO noidungdanhgiaDTO1 = new NoidungdanhgiaDTO();
        noidungdanhgiaDTO1.setId(1L);
        NoidungdanhgiaDTO noidungdanhgiaDTO2 = new NoidungdanhgiaDTO();
        assertThat(noidungdanhgiaDTO1).isNotEqualTo(noidungdanhgiaDTO2);
        noidungdanhgiaDTO2.setId(noidungdanhgiaDTO1.getId());
        assertThat(noidungdanhgiaDTO1).isEqualTo(noidungdanhgiaDTO2);
        noidungdanhgiaDTO2.setId(2L);
        assertThat(noidungdanhgiaDTO1).isNotEqualTo(noidungdanhgiaDTO2);
        noidungdanhgiaDTO1.setId(null);
        assertThat(noidungdanhgiaDTO1).isNotEqualTo(noidungdanhgiaDTO2);
    }
}

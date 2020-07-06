package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class NhansuDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhansuDTO.class);
        NhansuDTO nhansuDTO1 = new NhansuDTO();
        nhansuDTO1.setId(1L);
        NhansuDTO nhansuDTO2 = new NhansuDTO();
        assertThat(nhansuDTO1).isNotEqualTo(nhansuDTO2);
        nhansuDTO2.setId(nhansuDTO1.getId());
        assertThat(nhansuDTO1).isEqualTo(nhansuDTO2);
        nhansuDTO2.setId(2L);
        assertThat(nhansuDTO1).isNotEqualTo(nhansuDTO2);
        nhansuDTO1.setId(null);
        assertThat(nhansuDTO1).isNotEqualTo(nhansuDTO2);
    }
}

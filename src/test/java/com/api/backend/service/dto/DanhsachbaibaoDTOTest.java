package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class DanhsachbaibaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DanhsachbaibaoDTO.class);
        DanhsachbaibaoDTO danhsachbaibaoDTO1 = new DanhsachbaibaoDTO();
        danhsachbaibaoDTO1.setId(1L);
        DanhsachbaibaoDTO danhsachbaibaoDTO2 = new DanhsachbaibaoDTO();
        assertThat(danhsachbaibaoDTO1).isNotEqualTo(danhsachbaibaoDTO2);
        danhsachbaibaoDTO2.setId(danhsachbaibaoDTO1.getId());
        assertThat(danhsachbaibaoDTO1).isEqualTo(danhsachbaibaoDTO2);
        danhsachbaibaoDTO2.setId(2L);
        assertThat(danhsachbaibaoDTO1).isNotEqualTo(danhsachbaibaoDTO2);
        danhsachbaibaoDTO1.setId(null);
        assertThat(danhsachbaibaoDTO1).isNotEqualTo(danhsachbaibaoDTO2);
    }
}

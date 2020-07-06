package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class HoidongdanhgiaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HoidongdanhgiaDTO.class);
        HoidongdanhgiaDTO hoidongdanhgiaDTO1 = new HoidongdanhgiaDTO();
        hoidongdanhgiaDTO1.setId(1L);
        HoidongdanhgiaDTO hoidongdanhgiaDTO2 = new HoidongdanhgiaDTO();
        assertThat(hoidongdanhgiaDTO1).isNotEqualTo(hoidongdanhgiaDTO2);
        hoidongdanhgiaDTO2.setId(hoidongdanhgiaDTO1.getId());
        assertThat(hoidongdanhgiaDTO1).isEqualTo(hoidongdanhgiaDTO2);
        hoidongdanhgiaDTO2.setId(2L);
        assertThat(hoidongdanhgiaDTO1).isNotEqualTo(hoidongdanhgiaDTO2);
        hoidongdanhgiaDTO1.setId(null);
        assertThat(hoidongdanhgiaDTO1).isNotEqualTo(hoidongdanhgiaDTO2);
    }
}

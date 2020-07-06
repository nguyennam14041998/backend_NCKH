package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class CoquanphoihopthamgiaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoquanphoihopthamgiaDTO.class);
        CoquanphoihopthamgiaDTO coquanphoihopthamgiaDTO1 = new CoquanphoihopthamgiaDTO();
        coquanphoihopthamgiaDTO1.setId(1L);
        CoquanphoihopthamgiaDTO coquanphoihopthamgiaDTO2 = new CoquanphoihopthamgiaDTO();
        assertThat(coquanphoihopthamgiaDTO1).isNotEqualTo(coquanphoihopthamgiaDTO2);
        coquanphoihopthamgiaDTO2.setId(coquanphoihopthamgiaDTO1.getId());
        assertThat(coquanphoihopthamgiaDTO1).isEqualTo(coquanphoihopthamgiaDTO2);
        coquanphoihopthamgiaDTO2.setId(2L);
        assertThat(coquanphoihopthamgiaDTO1).isNotEqualTo(coquanphoihopthamgiaDTO2);
        coquanphoihopthamgiaDTO1.setId(null);
        assertThat(coquanphoihopthamgiaDTO1).isNotEqualTo(coquanphoihopthamgiaDTO2);
    }
}

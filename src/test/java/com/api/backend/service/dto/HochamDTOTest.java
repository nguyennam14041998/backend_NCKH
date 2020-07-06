package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class HochamDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HochamDTO.class);
        HochamDTO hochamDTO1 = new HochamDTO();
        hochamDTO1.setId(1L);
        HochamDTO hochamDTO2 = new HochamDTO();
        assertThat(hochamDTO1).isNotEqualTo(hochamDTO2);
        hochamDTO2.setId(hochamDTO1.getId());
        assertThat(hochamDTO1).isEqualTo(hochamDTO2);
        hochamDTO2.setId(2L);
        assertThat(hochamDTO1).isNotEqualTo(hochamDTO2);
        hochamDTO1.setId(null);
        assertThat(hochamDTO1).isNotEqualTo(hochamDTO2);
    }
}

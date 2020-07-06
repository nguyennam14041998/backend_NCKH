package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class CapdetaiDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CapdetaiDTO.class);
        CapdetaiDTO capdetaiDTO1 = new CapdetaiDTO();
        capdetaiDTO1.setId(1L);
        CapdetaiDTO capdetaiDTO2 = new CapdetaiDTO();
        assertThat(capdetaiDTO1).isNotEqualTo(capdetaiDTO2);
        capdetaiDTO2.setId(capdetaiDTO1.getId());
        assertThat(capdetaiDTO1).isEqualTo(capdetaiDTO2);
        capdetaiDTO2.setId(2L);
        assertThat(capdetaiDTO1).isNotEqualTo(capdetaiDTO2);
        capdetaiDTO1.setId(null);
        assertThat(capdetaiDTO1).isNotEqualTo(capdetaiDTO2);
    }
}

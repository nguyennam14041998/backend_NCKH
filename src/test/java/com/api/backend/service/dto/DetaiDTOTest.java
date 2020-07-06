package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class DetaiDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetaiDTO.class);
        DetaiDTO detaiDTO1 = new DetaiDTO();
        detaiDTO1.setId(1L);
        DetaiDTO detaiDTO2 = new DetaiDTO();
        assertThat(detaiDTO1).isNotEqualTo(detaiDTO2);
        detaiDTO2.setId(detaiDTO1.getId());
        assertThat(detaiDTO1).isEqualTo(detaiDTO2);
        detaiDTO2.setId(2L);
        assertThat(detaiDTO1).isNotEqualTo(detaiDTO2);
        detaiDTO1.setId(null);
        assertThat(detaiDTO1).isNotEqualTo(detaiDTO2);
    }
}

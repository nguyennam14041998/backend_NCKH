package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class UpfileDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UpfileDTO.class);
        UpfileDTO upfileDTO1 = new UpfileDTO();
        upfileDTO1.setId(1L);
        UpfileDTO upfileDTO2 = new UpfileDTO();
        assertThat(upfileDTO1).isNotEqualTo(upfileDTO2);
        upfileDTO2.setId(upfileDTO1.getId());
        assertThat(upfileDTO1).isEqualTo(upfileDTO2);
        upfileDTO2.setId(2L);
        assertThat(upfileDTO1).isNotEqualTo(upfileDTO2);
        upfileDTO1.setId(null);
        assertThat(upfileDTO1).isNotEqualTo(upfileDTO2);
    }
}

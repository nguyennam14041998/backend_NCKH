package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class NguonkinhphiDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NguonkinhphiDTO.class);
        NguonkinhphiDTO nguonkinhphiDTO1 = new NguonkinhphiDTO();
        nguonkinhphiDTO1.setId(1L);
        NguonkinhphiDTO nguonkinhphiDTO2 = new NguonkinhphiDTO();
        assertThat(nguonkinhphiDTO1).isNotEqualTo(nguonkinhphiDTO2);
        nguonkinhphiDTO2.setId(nguonkinhphiDTO1.getId());
        assertThat(nguonkinhphiDTO1).isEqualTo(nguonkinhphiDTO2);
        nguonkinhphiDTO2.setId(2L);
        assertThat(nguonkinhphiDTO1).isNotEqualTo(nguonkinhphiDTO2);
        nguonkinhphiDTO1.setId(null);
        assertThat(nguonkinhphiDTO1).isNotEqualTo(nguonkinhphiDTO2);
    }
}

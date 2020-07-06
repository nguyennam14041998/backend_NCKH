package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class DonviDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonviDTO.class);
        DonviDTO donviDTO1 = new DonviDTO();
        donviDTO1.setId(1L);
        DonviDTO donviDTO2 = new DonviDTO();
        assertThat(donviDTO1).isNotEqualTo(donviDTO2);
        donviDTO2.setId(donviDTO1.getId());
        assertThat(donviDTO1).isEqualTo(donviDTO2);
        donviDTO2.setId(2L);
        assertThat(donviDTO1).isNotEqualTo(donviDTO2);
        donviDTO1.setId(null);
        assertThat(donviDTO1).isNotEqualTo(donviDTO2);
    }
}

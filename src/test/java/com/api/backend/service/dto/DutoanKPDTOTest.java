package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class DutoanKPDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DutoanKPDTO.class);
        DutoanKPDTO dutoanKPDTO1 = new DutoanKPDTO();
        dutoanKPDTO1.setId(1L);
        DutoanKPDTO dutoanKPDTO2 = new DutoanKPDTO();
        assertThat(dutoanKPDTO1).isNotEqualTo(dutoanKPDTO2);
        dutoanKPDTO2.setId(dutoanKPDTO1.getId());
        assertThat(dutoanKPDTO1).isEqualTo(dutoanKPDTO2);
        dutoanKPDTO2.setId(2L);
        assertThat(dutoanKPDTO1).isNotEqualTo(dutoanKPDTO2);
        dutoanKPDTO1.setId(null);
        assertThat(dutoanKPDTO1).isNotEqualTo(dutoanKPDTO2);
    }
}

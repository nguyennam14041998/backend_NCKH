package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class DutoanKPCTDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DutoanKPCTDTO.class);
        DutoanKPCTDTO dutoanKPCTDTO1 = new DutoanKPCTDTO();
        dutoanKPCTDTO1.setId(1L);
        DutoanKPCTDTO dutoanKPCTDTO2 = new DutoanKPCTDTO();
        assertThat(dutoanKPCTDTO1).isNotEqualTo(dutoanKPCTDTO2);
        dutoanKPCTDTO2.setId(dutoanKPCTDTO1.getId());
        assertThat(dutoanKPCTDTO1).isEqualTo(dutoanKPCTDTO2);
        dutoanKPCTDTO2.setId(2L);
        assertThat(dutoanKPCTDTO1).isNotEqualTo(dutoanKPCTDTO2);
        dutoanKPCTDTO1.setId(null);
        assertThat(dutoanKPCTDTO1).isNotEqualTo(dutoanKPCTDTO2);
    }
}

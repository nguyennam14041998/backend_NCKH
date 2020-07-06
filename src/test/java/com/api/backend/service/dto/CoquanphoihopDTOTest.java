package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class CoquanphoihopDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoquanphoihopDTO.class);
        CoquanphoihopDTO coquanphoihopDTO1 = new CoquanphoihopDTO();
        coquanphoihopDTO1.setId(1L);
        CoquanphoihopDTO coquanphoihopDTO2 = new CoquanphoihopDTO();
        assertThat(coquanphoihopDTO1).isNotEqualTo(coquanphoihopDTO2);
        coquanphoihopDTO2.setId(coquanphoihopDTO1.getId());
        assertThat(coquanphoihopDTO1).isEqualTo(coquanphoihopDTO2);
        coquanphoihopDTO2.setId(2L);
        assertThat(coquanphoihopDTO1).isNotEqualTo(coquanphoihopDTO2);
        coquanphoihopDTO1.setId(null);
        assertThat(coquanphoihopDTO1).isNotEqualTo(coquanphoihopDTO2);
    }
}

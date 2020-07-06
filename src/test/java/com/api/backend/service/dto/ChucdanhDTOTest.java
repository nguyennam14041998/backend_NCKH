package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class ChucdanhDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChucdanhDTO.class);
        ChucdanhDTO chucdanhDTO1 = new ChucdanhDTO();
        chucdanhDTO1.setId(1L);
        ChucdanhDTO chucdanhDTO2 = new ChucdanhDTO();
        assertThat(chucdanhDTO1).isNotEqualTo(chucdanhDTO2);
        chucdanhDTO2.setId(chucdanhDTO1.getId());
        assertThat(chucdanhDTO1).isEqualTo(chucdanhDTO2);
        chucdanhDTO2.setId(2L);
        assertThat(chucdanhDTO1).isNotEqualTo(chucdanhDTO2);
        chucdanhDTO1.setId(null);
        assertThat(chucdanhDTO1).isNotEqualTo(chucdanhDTO2);
    }
}

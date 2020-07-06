package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class LinhvucDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LinhvucDTO.class);
        LinhvucDTO linhvucDTO1 = new LinhvucDTO();
        linhvucDTO1.setId(1L);
        LinhvucDTO linhvucDTO2 = new LinhvucDTO();
        assertThat(linhvucDTO1).isNotEqualTo(linhvucDTO2);
        linhvucDTO2.setId(linhvucDTO1.getId());
        assertThat(linhvucDTO1).isEqualTo(linhvucDTO2);
        linhvucDTO2.setId(2L);
        assertThat(linhvucDTO1).isNotEqualTo(linhvucDTO2);
        linhvucDTO1.setId(null);
        assertThat(linhvucDTO1).isNotEqualTo(linhvucDTO2);
    }
}

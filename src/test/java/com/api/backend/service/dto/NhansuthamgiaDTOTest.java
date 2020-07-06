package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class NhansuthamgiaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhansuthamgiaDTO.class);
        NhansuthamgiaDTO nhansuthamgiaDTO1 = new NhansuthamgiaDTO();
        nhansuthamgiaDTO1.setId(1L);
        NhansuthamgiaDTO nhansuthamgiaDTO2 = new NhansuthamgiaDTO();
        assertThat(nhansuthamgiaDTO1).isNotEqualTo(nhansuthamgiaDTO2);
        nhansuthamgiaDTO2.setId(nhansuthamgiaDTO1.getId());
        assertThat(nhansuthamgiaDTO1).isEqualTo(nhansuthamgiaDTO2);
        nhansuthamgiaDTO2.setId(2L);
        assertThat(nhansuthamgiaDTO1).isNotEqualTo(nhansuthamgiaDTO2);
        nhansuthamgiaDTO1.setId(null);
        assertThat(nhansuthamgiaDTO1).isNotEqualTo(nhansuthamgiaDTO2);
    }
}

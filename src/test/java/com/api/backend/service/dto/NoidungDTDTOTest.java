package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class NoidungDTDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoidungDTDTO.class);
        NoidungDTDTO noidungDTDTO1 = new NoidungDTDTO();
        noidungDTDTO1.setId(1L);
        NoidungDTDTO noidungDTDTO2 = new NoidungDTDTO();
        assertThat(noidungDTDTO1).isNotEqualTo(noidungDTDTO2);
        noidungDTDTO2.setId(noidungDTDTO1.getId());
        assertThat(noidungDTDTO1).isEqualTo(noidungDTDTO2);
        noidungDTDTO2.setId(2L);
        assertThat(noidungDTDTO1).isNotEqualTo(noidungDTDTO2);
        noidungDTDTO1.setId(null);
        assertThat(noidungDTDTO1).isNotEqualTo(noidungDTDTO2);
    }
}

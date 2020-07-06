package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class ChunhiemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChunhiemDTO.class);
        ChunhiemDTO chunhiemDTO1 = new ChunhiemDTO();
        chunhiemDTO1.setId(1L);
        ChunhiemDTO chunhiemDTO2 = new ChunhiemDTO();
        assertThat(chunhiemDTO1).isNotEqualTo(chunhiemDTO2);
        chunhiemDTO2.setId(chunhiemDTO1.getId());
        assertThat(chunhiemDTO1).isEqualTo(chunhiemDTO2);
        chunhiemDTO2.setId(2L);
        assertThat(chunhiemDTO1).isNotEqualTo(chunhiemDTO2);
        chunhiemDTO1.setId(null);
        assertThat(chunhiemDTO1).isNotEqualTo(chunhiemDTO2);
    }
}

package com.api.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class TiendoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TiendoDTO.class);
        TiendoDTO tiendoDTO1 = new TiendoDTO();
        tiendoDTO1.setId(1L);
        TiendoDTO tiendoDTO2 = new TiendoDTO();
        assertThat(tiendoDTO1).isNotEqualTo(tiendoDTO2);
        tiendoDTO2.setId(tiendoDTO1.getId());
        assertThat(tiendoDTO1).isEqualTo(tiendoDTO2);
        tiendoDTO2.setId(2L);
        assertThat(tiendoDTO1).isNotEqualTo(tiendoDTO2);
        tiendoDTO1.setId(null);
        assertThat(tiendoDTO1).isNotEqualTo(tiendoDTO2);
    }
}

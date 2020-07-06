package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class DanhsachbaibaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Danhsachbaibao.class);
        Danhsachbaibao danhsachbaibao1 = new Danhsachbaibao();
        danhsachbaibao1.setId(1L);
        Danhsachbaibao danhsachbaibao2 = new Danhsachbaibao();
        danhsachbaibao2.setId(danhsachbaibao1.getId());
        assertThat(danhsachbaibao1).isEqualTo(danhsachbaibao2);
        danhsachbaibao2.setId(2L);
        assertThat(danhsachbaibao1).isNotEqualTo(danhsachbaibao2);
        danhsachbaibao1.setId(null);
        assertThat(danhsachbaibao1).isNotEqualTo(danhsachbaibao2);
    }
}

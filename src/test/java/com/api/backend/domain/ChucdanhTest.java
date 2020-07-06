package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class ChucdanhTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chucdanh.class);
        Chucdanh chucdanh1 = new Chucdanh();
        chucdanh1.setId(1L);
        Chucdanh chucdanh2 = new Chucdanh();
        chucdanh2.setId(chucdanh1.getId());
        assertThat(chucdanh1).isEqualTo(chucdanh2);
        chucdanh2.setId(2L);
        assertThat(chucdanh1).isNotEqualTo(chucdanh2);
        chucdanh1.setId(null);
        assertThat(chucdanh1).isNotEqualTo(chucdanh2);
    }
}

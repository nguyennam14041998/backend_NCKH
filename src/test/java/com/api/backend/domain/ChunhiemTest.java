package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class ChunhiemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chunhiem.class);
        Chunhiem chunhiem1 = new Chunhiem();
        chunhiem1.setId(1L);
        Chunhiem chunhiem2 = new Chunhiem();
        chunhiem2.setId(chunhiem1.getId());
        assertThat(chunhiem1).isEqualTo(chunhiem2);
        chunhiem2.setId(2L);
        assertThat(chunhiem1).isNotEqualTo(chunhiem2);
        chunhiem1.setId(null);
        assertThat(chunhiem1).isNotEqualTo(chunhiem2);
    }
}

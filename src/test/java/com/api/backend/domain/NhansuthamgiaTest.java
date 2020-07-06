package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class NhansuthamgiaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nhansuthamgia.class);
        Nhansuthamgia nhansuthamgia1 = new Nhansuthamgia();
        nhansuthamgia1.setId(1L);
        Nhansuthamgia nhansuthamgia2 = new Nhansuthamgia();
        nhansuthamgia2.setId(nhansuthamgia1.getId());
        assertThat(nhansuthamgia1).isEqualTo(nhansuthamgia2);
        nhansuthamgia2.setId(2L);
        assertThat(nhansuthamgia1).isNotEqualTo(nhansuthamgia2);
        nhansuthamgia1.setId(null);
        assertThat(nhansuthamgia1).isNotEqualTo(nhansuthamgia2);
    }
}

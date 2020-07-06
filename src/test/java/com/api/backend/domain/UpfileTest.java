package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class UpfileTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Upfile.class);
        Upfile upfile1 = new Upfile();
        upfile1.setId(1L);
        Upfile upfile2 = new Upfile();
        upfile2.setId(upfile1.getId());
        assertThat(upfile1).isEqualTo(upfile2);
        upfile2.setId(2L);
        assertThat(upfile1).isNotEqualTo(upfile2);
        upfile1.setId(null);
        assertThat(upfile1).isNotEqualTo(upfile2);
    }
}

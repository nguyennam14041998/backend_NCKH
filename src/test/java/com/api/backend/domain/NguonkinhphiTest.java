package com.api.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.api.backend.web.rest.TestUtil;

public class NguonkinhphiTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nguonkinhphi.class);
        Nguonkinhphi nguonkinhphi1 = new Nguonkinhphi();
        nguonkinhphi1.setId(1L);
        Nguonkinhphi nguonkinhphi2 = new Nguonkinhphi();
        nguonkinhphi2.setId(nguonkinhphi1.getId());
        assertThat(nguonkinhphi1).isEqualTo(nguonkinhphi2);
        nguonkinhphi2.setId(2L);
        assertThat(nguonkinhphi1).isNotEqualTo(nguonkinhphi2);
        nguonkinhphi1.setId(null);
        assertThat(nguonkinhphi1).isNotEqualTo(nguonkinhphi2);
    }
}

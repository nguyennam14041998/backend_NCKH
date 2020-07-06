package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class NguonkinhphiMapperTest {

    private NguonkinhphiMapper nguonkinhphiMapper;

    @BeforeEach
    public void setUp() {
        nguonkinhphiMapper = new NguonkinhphiMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(nguonkinhphiMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(nguonkinhphiMapper.fromId(null)).isNull();
    }
}

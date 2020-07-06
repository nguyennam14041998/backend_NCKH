package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CoquanphoihopMapperTest {

    private CoquanphoihopMapper coquanphoihopMapper;

    @BeforeEach
    public void setUp() {
        coquanphoihopMapper = new CoquanphoihopMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(coquanphoihopMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(coquanphoihopMapper.fromId(null)).isNull();
    }
}

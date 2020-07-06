package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CoquanphoihopthamgiaMapperTest {

    private CoquanphoihopthamgiaMapper coquanphoihopthamgiaMapper;

    @BeforeEach
    public void setUp() {
        coquanphoihopthamgiaMapper = new CoquanphoihopthamgiaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(coquanphoihopthamgiaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(coquanphoihopthamgiaMapper.fromId(null)).isNull();
    }
}

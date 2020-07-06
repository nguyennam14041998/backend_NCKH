package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CapdetaiMapperTest {

    private CapdetaiMapper capdetaiMapper;

    @BeforeEach
    public void setUp() {
        capdetaiMapper = new CapdetaiMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(capdetaiMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(capdetaiMapper.fromId(null)).isNull();
    }
}

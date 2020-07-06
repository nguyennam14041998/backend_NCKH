package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DetaiMapperTest {

    private DetaiMapper detaiMapper;

    @BeforeEach
    public void setUp() {
        detaiMapper = new DetaiMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(detaiMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(detaiMapper.fromId(null)).isNull();
    }
}

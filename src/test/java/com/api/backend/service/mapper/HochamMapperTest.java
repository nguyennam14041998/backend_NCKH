package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class HochamMapperTest {

    private HochamMapper hochamMapper;

    @BeforeEach
    public void setUp() {
        hochamMapper = new HochamMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(hochamMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(hochamMapper.fromId(null)).isNull();
    }
}

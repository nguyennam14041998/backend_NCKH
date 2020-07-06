package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ChunhiemMapperTest {

    private ChunhiemMapper chunhiemMapper;

    @BeforeEach
    public void setUp() {
        chunhiemMapper = new ChunhiemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(chunhiemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(chunhiemMapper.fromId(null)).isNull();
    }
}

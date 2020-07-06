package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class UpfileMapperTest {

    private UpfileMapper upfileMapper;

    @BeforeEach
    public void setUp() {
        upfileMapper = new UpfileMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(upfileMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(upfileMapper.fromId(null)).isNull();
    }
}

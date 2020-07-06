package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TiendoMapperTest {

    private TiendoMapper tiendoMapper;

    @BeforeEach
    public void setUp() {
        tiendoMapper = new TiendoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(tiendoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tiendoMapper.fromId(null)).isNull();
    }
}

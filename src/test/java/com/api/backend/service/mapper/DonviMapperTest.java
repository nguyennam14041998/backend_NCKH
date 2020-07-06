package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DonviMapperTest {

    private DonviMapper donviMapper;

    @BeforeEach
    public void setUp() {
        donviMapper = new DonviMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(donviMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(donviMapper.fromId(null)).isNull();
    }
}

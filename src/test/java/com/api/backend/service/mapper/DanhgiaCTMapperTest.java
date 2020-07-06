package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DanhgiaCTMapperTest {

    private DanhgiaCTMapper danhgiaCTMapper;

    @BeforeEach
    public void setUp() {
        danhgiaCTMapper = new DanhgiaCTMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(danhgiaCTMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(danhgiaCTMapper.fromId(null)).isNull();
    }
}

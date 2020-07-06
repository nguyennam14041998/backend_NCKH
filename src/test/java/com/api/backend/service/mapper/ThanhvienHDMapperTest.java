package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ThanhvienHDMapperTest {

    private ThanhvienHDMapper thanhvienHDMapper;

    @BeforeEach
    public void setUp() {
        thanhvienHDMapper = new ThanhvienHDMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(thanhvienHDMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(thanhvienHDMapper.fromId(null)).isNull();
    }
}

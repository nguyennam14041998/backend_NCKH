package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class NhansuMapperTest {

    private NhansuMapper nhansuMapper;

    @BeforeEach
    public void setUp() {
        nhansuMapper = new NhansuMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(nhansuMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(nhansuMapper.fromId(null)).isNull();
    }
}

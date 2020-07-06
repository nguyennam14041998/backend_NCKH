package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DanhgiaMapperTest {

    private DanhgiaMapper danhgiaMapper;

    @BeforeEach
    public void setUp() {
        danhgiaMapper = new DanhgiaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(danhgiaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(danhgiaMapper.fromId(null)).isNull();
    }
}

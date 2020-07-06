package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class HoidongdanhgiaMapperTest {

    private HoidongdanhgiaMapper hoidongdanhgiaMapper;

    @BeforeEach
    public void setUp() {
        hoidongdanhgiaMapper = new HoidongdanhgiaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(hoidongdanhgiaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(hoidongdanhgiaMapper.fromId(null)).isNull();
    }
}

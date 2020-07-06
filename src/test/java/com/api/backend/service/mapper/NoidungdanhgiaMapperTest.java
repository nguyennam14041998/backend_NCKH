package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class NoidungdanhgiaMapperTest {

    private NoidungdanhgiaMapper noidungdanhgiaMapper;

    @BeforeEach
    public void setUp() {
        noidungdanhgiaMapper = new NoidungdanhgiaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(noidungdanhgiaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(noidungdanhgiaMapper.fromId(null)).isNull();
    }
}

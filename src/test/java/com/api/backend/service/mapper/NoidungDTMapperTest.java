package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class NoidungDTMapperTest {

    private NoidungDTMapper noidungDTMapper;

    @BeforeEach
    public void setUp() {
        noidungDTMapper = new NoidungDTMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(noidungDTMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(noidungDTMapper.fromId(null)).isNull();
    }
}

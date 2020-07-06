package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ChucdanhMapperTest {

    private ChucdanhMapper chucdanhMapper;

    @BeforeEach
    public void setUp() {
        chucdanhMapper = new ChucdanhMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(chucdanhMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(chucdanhMapper.fromId(null)).isNull();
    }
}

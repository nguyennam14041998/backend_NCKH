package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DutoanKPMapperTest {

    private DutoanKPMapper dutoanKPMapper;

    @BeforeEach
    public void setUp() {
        dutoanKPMapper = new DutoanKPMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(dutoanKPMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dutoanKPMapper.fromId(null)).isNull();
    }
}

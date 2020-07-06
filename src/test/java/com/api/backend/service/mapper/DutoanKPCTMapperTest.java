package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DutoanKPCTMapperTest {

    private DutoanKPCTMapper dutoanKPCTMapper;

    @BeforeEach
    public void setUp() {
        dutoanKPCTMapper = new DutoanKPCTMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(dutoanKPCTMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dutoanKPCTMapper.fromId(null)).isNull();
    }
}

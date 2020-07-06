package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class NhansuthamgiaMapperTest {

    private NhansuthamgiaMapper nhansuthamgiaMapper;

    @BeforeEach
    public void setUp() {
        nhansuthamgiaMapper = new NhansuthamgiaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(nhansuthamgiaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(nhansuthamgiaMapper.fromId(null)).isNull();
    }
}

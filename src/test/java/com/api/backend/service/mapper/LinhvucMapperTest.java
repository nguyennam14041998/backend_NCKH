package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class LinhvucMapperTest {

    private LinhvucMapper linhvucMapper;

    @BeforeEach
    public void setUp() {
        linhvucMapper = new LinhvucMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(linhvucMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(linhvucMapper.fromId(null)).isNull();
    }
}

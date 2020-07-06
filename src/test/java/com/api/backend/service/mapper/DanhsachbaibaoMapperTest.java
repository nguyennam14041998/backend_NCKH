package com.api.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DanhsachbaibaoMapperTest {

    private DanhsachbaibaoMapper danhsachbaibaoMapper;

    @BeforeEach
    public void setUp() {
        danhsachbaibaoMapper = new DanhsachbaibaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(danhsachbaibaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(danhsachbaibaoMapper.fromId(null)).isNull();
    }
}

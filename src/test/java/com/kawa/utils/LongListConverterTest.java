package com.kawa.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LongListConverterTest {

    private LongListConverter longListConverterUnderTest;

    @BeforeEach
    void setUp() {
        longListConverterUnderTest = new LongListConverter();
    }

    @Test
    void testConvertToDatabaseColumn() {
        assertThat(longListConverterUnderTest.convertToDatabaseColumn(List.of(0L))).isEqualTo("0");
    }

    @Test
    void testConvertToEntityAttribute() {
        assertThat(longListConverterUnderTest.convertToEntityAttribute("0")).isEqualTo(List.of(0L));
        assertThat(longListConverterUnderTest.convertToEntityAttribute("")).isEqualTo(Collections.emptyList());
    }
}

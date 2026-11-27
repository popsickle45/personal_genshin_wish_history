package com.example.backend.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, String> {
    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public String convertToDatabaseColumn(LocalDateTime t) { return t == null ? null : t.format(F); }
    public LocalDateTime convertToEntityAttribute(String s) { return s == null ? null : LocalDateTime.parse(s, F); }
}
package com.example.backend.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalDate;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, String> {
    public String convertToDatabaseColumn(LocalDate d) { return d == null ? null : d.toString(); }
    public LocalDate convertToEntityAttribute(String s) { return s == null ? null : LocalDate.parse(s); }
}
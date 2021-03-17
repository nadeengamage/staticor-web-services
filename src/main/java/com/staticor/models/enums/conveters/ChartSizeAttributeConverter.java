package com.staticor.models.enums.conveters;

import com.staticor.models.enums.ChartSize;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ChartSizeAttributeConverter implements AttributeConverter<ChartSize, String> {

    @Override
    public String convertToDatabaseColumn(ChartSize size) {
        if (size == null)
            return null;

        switch (size) {
            case SMALL:
                return "col-md-4";
            case MEDIUM:
                return "col-md-6";
            case LARGE:
                return "col-md-12";
            default:
                throw new IllegalArgumentException(size + " not supported.");

        }
    }

    @Override
    public ChartSize convertToEntityAttribute(String size) {
        if (size == null)
            return null;

        switch (size) {
            case "col-md-4":
                return ChartSize.SMALL;
            case "col-md-6":
                return ChartSize.MEDIUM;
            case "col-md-12":
                return ChartSize.LARGE;
            default:
                throw new IllegalArgumentException(size + " not supported.");

        }
    }
}

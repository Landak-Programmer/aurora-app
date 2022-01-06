package com.mobile.auroraai.exception;

public class ValueNotExist extends RuntimeException {

    public ValueNotExist(final String value) {
        super(String.format("Value %s not exist!", value));
    }
}

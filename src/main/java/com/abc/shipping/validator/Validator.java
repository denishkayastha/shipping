package com.abc.shipping.validator;

import java.util.List;

@FunctionalInterface
public interface Validator<T> {
    boolean validate(List<T> Object);
}
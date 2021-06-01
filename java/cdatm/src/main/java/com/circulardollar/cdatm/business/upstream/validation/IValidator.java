package com.circulardollar.cdatm.business.upstream.validation;

import com.circulardollar.cdatm.business.downstream.model.error.IError;
import java.util.Optional;

@FunctionalInterface
public interface IValidator<T> {
  Optional<IError> validate(T target);
}

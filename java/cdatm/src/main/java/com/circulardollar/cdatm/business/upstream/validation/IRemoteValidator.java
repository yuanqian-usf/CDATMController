package com.circulardollar.cdatm.business.upstream.validation;

import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;
import java.util.Optional;

@FunctionalInterface
public interface IRemoteValidator<T> {
  Optional<IErrorRecord> validate(T target);
}

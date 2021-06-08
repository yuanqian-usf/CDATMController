package com.circulardollar.cdatm.validator.upstream;

import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecordV2;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILogoutRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecord;
import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.token.IRequestWithToken;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecord;
import java.util.Optional;

public interface IATMRemoteValidator {
  Optional<IErrorRecord> validate(Object remoteRecord);

  Optional<IErrorRecord> validateLoginRecord(ILoginRecord loginRecord);

  Optional<IErrorRecord> validateAuthRecord(IAuthRecord authRecord);

  Optional<IErrorRecord> validateAuthRecordV2(IAuthRecordV2 authRecordV2);

  Optional<IErrorRecord> validateRequestWithToken(IRequestWithToken requestWithToken);

  Optional<IErrorRecord> validateAccountsRecord(IAccountsRecord accountsRecord);

  Optional<IErrorRecord> validateDepositRecord(IDepositRecord depositRecord);

  Optional<IErrorRecord> validateWithdrawRecord(IWithdrawRecord withdrawRecord);

  Optional<IErrorRecord> validateLogoutRecord(ILogoutRecord logoutRecord);
}

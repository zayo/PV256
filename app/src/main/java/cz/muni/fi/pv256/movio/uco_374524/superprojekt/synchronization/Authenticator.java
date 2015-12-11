package cz.muni.fi.pv256.movio.uco_374524.superprojekt.synchronization;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by prasniatko on 11/12/15.
 */
public class Authenticator extends AbstractAccountAuthenticator {

  private static final String TAG = ".Authenticator";

  public Authenticator(Context context) {
    super(context);
  }

  @Override
  public Bundle editProperties(
    AccountAuthenticatorResponse r, String s) {
    throw new UnsupportedOperationException();
  }

  // Because we're not actually adding an account to the device, just return null.
  @Override
  public Bundle addAccount(
    AccountAuthenticatorResponse r,
    String s,
    String s2,
    String[] strings,
    Bundle bundle) throws NetworkErrorException {
    return null;
  }

  // Ignore attempts to confirm credentials
  @Override
  public Bundle confirmCredentials(
    AccountAuthenticatorResponse r,
    Account account,
    Bundle bundle) throws NetworkErrorException {
    return null;
  }

  // Getting an authentication token is not supported
  @Override
  public Bundle getAuthToken(
    AccountAuthenticatorResponse r,
    Account account,
    String s,
    Bundle bundle) throws NetworkErrorException {
    throw new UnsupportedOperationException();
  }

  // Getting a label for the auth token is not supported
  @Override
  public String getAuthTokenLabel(String s) {
    throw new UnsupportedOperationException();
  }

  // Updating user credentials is not supported
  @Override
  public Bundle updateCredentials(
    AccountAuthenticatorResponse r,
    Account account,
    String s, Bundle bundle) throws NetworkErrorException {
    throw new UnsupportedOperationException();
  }

  // Checking features for the account is not supported
  @Override
  public Bundle hasFeatures(
    AccountAuthenticatorResponse r,
    Account account, String[] strings) throws NetworkErrorException {
    throw new UnsupportedOperationException();
  }
}
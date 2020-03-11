package pl.ninebits.qa.eurocash.api.users;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VendorPortalUser {
  private String login;
  private String password;
  private VendorPortalUserRole role;
}

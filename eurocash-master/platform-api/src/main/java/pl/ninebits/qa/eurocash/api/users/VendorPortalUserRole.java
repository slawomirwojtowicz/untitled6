package pl.ninebits.qa.eurocash.api.users;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum VendorPortalUserRole {
  ADMIN("ADMIN"),
  VENDOR("SPRZEDAWCA"),
  TO_CONFIG("DOKONFIGU");

  private String roleName;

  static VendorPortalUserRole byRoleName(String roleName) {
    return Arrays.stream(values()).filter(r -> r.getRoleName().equalsIgnoreCase(roleName)).findFirst().get();
  }
}

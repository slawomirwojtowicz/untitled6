package pl.ninebits.qa.eurocash.api.users;

import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.jasypt.util.text.BasicTextEncryptor;
import pl.ninebits.qa.automated.tests.core.utils.ExcelDataLoader;
import pl.ninebits.qa.automated.tests.core.utils.RandomUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VendorPortalUserPool {
  private List<VendorPortalUser> users;

  public VendorPortalUserPool(String usersDataFile, BasicTextEncryptor decryptor) {
    try {
      users = loadUsers(usersDataFile, decryptor);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load users definition from " + usersDataFile, e);
    }
  }

  public VendorPortalUser borrowUser() {
    if (users.isEmpty()) {
      throw new IllegalStateException("All test users are in use! Check the number of tests that run in parallel.");
    }
    return users.remove(RandomUtils.randomInt(1, users.size()) - 1);
  }

  public VendorPortalUser borrowUser(VendorPortalUserRole role) {
    if (users.isEmpty()) {
      throw new IllegalStateException("All test users are in use! Check the number of tests that run in parallel.");
    }
    List<VendorPortalUser> usersWithRole = users.stream().filter(u -> role.equals(u.getRole())).collect(Collectors.toList());
    if (usersWithRole.isEmpty()) {
      throw new IllegalStateException("There is no user in active pool with role " + role.name());
    }
    VendorPortalUser user = usersWithRole.remove(RandomUtils.randomInt(1, usersWithRole.size()) - 1);
    users.remove(user);
    return user;
  }

  public void returnUser(VendorPortalUser user) {
    users.add(user);
  }

  private List<VendorPortalUser> loadUsers(String usersDataFile, BasicTextEncryptor decryptor) throws IOException {
    List<VendorPortalUser> users = new ArrayList<>();
    Object[][] data = ExcelDataLoader.loadData(usersDataFile, null, String.class, String.class, String.class);
    for (int i = 0; i < data.length; i++) {
      String login = (String) data[i][0];
      String password = (String) data[i][1];
      String roleName = (String) data[i][2];

      if (decryptor != null && PropertyValueEncryptionUtils.isEncryptedValue(password)) {
        password = PropertyValueEncryptionUtils.decrypt(password, decryptor);
      }
      users.add(new VendorPortalUser(login, password, VendorPortalUserRole.byRoleName(roleName)));
    }
    return users;
  }
}

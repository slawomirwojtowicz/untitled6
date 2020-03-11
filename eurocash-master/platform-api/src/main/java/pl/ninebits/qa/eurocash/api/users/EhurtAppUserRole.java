package pl.ninebits.qa.eurocash.api.users;

import java.util.Arrays;

public enum EhurtAppUserRole {
    ADMIN("ADMIN_FIRMA"),
    STORES_CHAIN_ADMIN("ADM_SIECI"),
    ADMIN_MR("ADM_MR"),
    CUSTOMER_SERVICE("BOK"),
    ECM("ECM"),
    SHOP("SKLEP"),
    MARKETPLACE("MARKETPLACE"); //todo: do dodagadania rola dost usera pod marketplace

    private String roleName;

    EhurtAppUserRole(String roleName) {
        this.roleName = roleName;
    }

    static EhurtAppUserRole byRoleName(String roleName) {
        return Arrays.stream(values()).filter(r -> r.getRoleName().equalsIgnoreCase(roleName)).findFirst().get();
    }

    public String getRoleName() {
        return roleName;
    }
}

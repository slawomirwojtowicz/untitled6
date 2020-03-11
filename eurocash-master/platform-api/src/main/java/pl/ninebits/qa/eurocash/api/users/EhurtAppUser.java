package pl.ninebits.qa.eurocash.api.users;

public class EhurtAppUser {
    private String login;
    private String password;
    private EhurtAppUserRole role;

    public EhurtAppUser(String login, String password, EhurtAppUserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public EhurtAppUserRole getRole() {
        return role;
    }
}

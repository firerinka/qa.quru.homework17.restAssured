package qa.quru.domain;

public class Registration {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public Registration setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Registration setPassword(String password) {
        this.password = password;
        return this;
    }
}

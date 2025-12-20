package faraz.wallet.dto.request;

public class AdminUpdateUserRequest {

    private String email;
    private String firstName;
    private String lastName;
    private Boolean enabled;
    private String role;

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getRole() {
        return role;
    }
}

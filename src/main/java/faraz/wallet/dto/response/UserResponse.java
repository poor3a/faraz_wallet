package faraz.wallet.dto.response;

public class UserResponse {

    private Long id;
    private String phoneNumber;
    private boolean enabled;
    private String role;

    public UserResponse(Long id, String phoneNumber, boolean enabled, String role) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.enabled = enabled;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getRole() {
        return role;
    }
}

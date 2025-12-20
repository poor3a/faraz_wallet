package faraz.wallet.controller.auth;

import faraz.wallet.dto.request.LoginWithOtpRequest;
import faraz.wallet.dto.request.LoginWithPasswordRequest;
import faraz.wallet.dto.request.RequestOtpRequest;
import faraz.wallet.dto.response.JwtResponse;
import faraz.wallet.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginWithPassword(
            @Valid @RequestBody LoginWithPasswordRequest request)
    {
        String token = authService.loginWithPassword(
                request.getPhoneNumber(),
                request.getPassword()
        );
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> requestOtp(
            @Valid @RequestBody RequestOtpRequest request
    ) {
        authService.requestOtp(request.getPhoneNumber());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login-otp")
    public ResponseEntity<JwtResponse> loginWithOtp(
            @Valid @RequestBody LoginWithOtpRequest request)
    {
        String token = authService.loginWithOtp(
                request.getPhoneNumber(),
                request.getCode()
        );
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String authorizationHeader)
    {
        String token = authorizationHeader.substring(7);
        authService.logout(token);
        return ResponseEntity.ok().build();
    }
}

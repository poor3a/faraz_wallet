package faraz.wallet.service.auth;

import faraz.wallet.entity.Otp;
import faraz.wallet.entity.Token;
import faraz.wallet.entity.User;
import faraz.wallet.exception.ApiException;
import faraz.wallet.repository.OtpRepository;
import faraz.wallet.repository.TokenRepository;
import faraz.wallet.repository.UserRepository;
import faraz.wallet.security.JwtTokenProvider;
import faraz.wallet.service.SystemLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final SystemLogService systemLogService;

    @Transactional
    public void login(String phoneNumber, String password) {

        systemLogService.log(
                "LOGIN_ATTEMPT",
                phoneNumber,
                "User attempted login"
        );

        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> {
                    systemLogService.log(
                            "LOGIN_FAILED",
                            phoneNumber,
                            "User not found"
                    );
                    return new ApiException(HttpStatus.NOT_FOUND, "User not found");
                });

        if (!user.isEnabled()) {
            systemLogService.log(
                    "LOGIN_FAILED",
                    phoneNumber,
                    "User disabled"
            );
            throw new ApiException(HttpStatus.FORBIDDEN, "User is disabled");
        }

        if (!user.getPassword().equals(password)) {
            systemLogService.log(
                    "LOGIN_FAILED",
                    phoneNumber,
                    "Invalid credentials"
            );
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        Otp otp = new Otp();
        otp.setUser(user);
        otp.setCode(generateOtp());
        otp.setUsed(false);
        otp.setCreatedAt(Instant.now());
        otp.setExpiresAt(Instant.now().plusSeconds(300));

        otpRepository.save(otp);

        systemLogService.log(
                "OTP_GENERATED",
                phoneNumber,
                "OTP generated and sent"
        );
    }

    @Transactional
    public String verifyOtp(String phoneNumber, String code) {

        systemLogService.log(
                "OTP_VERIFICATION_ATTEMPT",
                phoneNumber,
                "OTP verification attempt"
        );

        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() ->
                        new ApiException(HttpStatus.NOT_FOUND, "User not found")
                );

        Otp otp = otpRepository
                .findTopByUserAndCodeAndUsedFalseOrderByCreatedAtDesc(user, code)
                .orElseThrow(() -> {
                    systemLogService.log(
                            "OTP_FAILED",
                            phoneNumber,
                            "Invalid OTP"
                    );
                    return new ApiException(HttpStatus.BAD_REQUEST, "Invalid OTP");
                });

        if (otp.getExpiresAt().isBefore(Instant.now())) {
            systemLogService.log(
                    "OTP_FAILED",
                    phoneNumber,
                    "OTP expired"
            );
            throw new ApiException(HttpStatus.BAD_REQUEST, "OTP expired");
        }

        otp.setUsed(true);
        otpRepository.save(otp);
        System.out.println(otp);

        revokeAllUserTokens(user);

        String jwt = jwtTokenProvider.generateToken(user);

        Token token = new Token();
        token.setUser(user);
        token.setToken(jwt);
        token.setExpired(false);
        token.setRevoked(false);
        token.setCreatedAt(Instant.now());

        tokenRepository.save(token);

        systemLogService.log(
                "TOKEN_ISSUED",
                phoneNumber,
                "JWT issued"
        );

        return jwt;
    }

    @Transactional
    public void logout(String tokenValue) {

        Token token = tokenRepository.findByToken(tokenValue)
                .orElseThrow(() ->
                        new ApiException(HttpStatus.UNAUTHORIZED, "Invalid token")
                );

        token.setRevoked(true);
        tokenRepository.save(token);

        systemLogService.log(
                "LOGOUT",
                token.getUser().getPhoneNumber(),
                "User logged out"
        );
    }

    private void revokeAllUserTokens(User user) {

        tokenRepository.findAllByUserAndExpiredFalseAndRevokedFalse(user)
                .forEach(token -> {
                    token.setRevoked(true);
                    tokenRepository.save(token);
                });
    }

    private String generateOtp() {

        int otp = (int) (Math.random() * 9000) + 1000;
        return String.valueOf(otp);
    }
}

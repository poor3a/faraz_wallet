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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    private static final Logger LOGGER =
            LoggerFactory.getLogger(AuthService.class);


    @Transactional
    public String loginWithPassword(String phoneNumber, String password) {

        systemLogService.log(
                "LOGIN_PASSWORD_ATTEMPT",
                phoneNumber,
                "Password login attempt"
        );

        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> {
                    systemLogService.log(
                            "LOGIN_PASSWORD_FAILED",
                            phoneNumber,
                            "User not found"
                    );
                    return new ApiException(HttpStatus.NOT_FOUND, "User not found");
                });

        if (!user.isEnabled()) {
            systemLogService.log(
                    "LOGIN_PASSWORD_FAILED",
                    phoneNumber,
                    "User disabled"
            );
            throw new ApiException(HttpStatus.FORBIDDEN, "User is disabled");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            systemLogService.log(
                    "LOGIN_PASSWORD_FAILED",
                    phoneNumber,
                    "Invalid credentials"
            );
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        revokeAllUserTokens(user);

        String jwt = jwtTokenProvider.generateToken(user);
        saveToken(user, jwt);

        systemLogService.log(
                "LOGIN_PASSWORD_SUCCESS",
                phoneNumber,
                "JWT issued via password login"
        );

        return jwt;
    }


    @Transactional
    public void requestOtp(String phoneNumber) {

        systemLogService.log(
                "OTP_REQUEST",
                phoneNumber,
                "OTP request initiated"
        );

        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> {
                    systemLogService.log(
                            "OTP_REQUEST_FAILED",
                            phoneNumber,
                            "User not found"
                    );
                    return new ApiException(HttpStatus.NOT_FOUND, "User not found");
                });

        if (!user.isEnabled()) {
            systemLogService.log(
                    "OTP_REQUEST_FAILED",
                    phoneNumber,
                    "User disabled"
            );
            throw new ApiException(HttpStatus.FORBIDDEN, "User is disabled");
        }

        Otp otp = new Otp();
        otp.setUser(user);
        otp.setCode(generateOtp());
        otp.setUsed(false);
        otp.setCreatedAt(Instant.now());
        otp.setExpiresAt(Instant.now().plusSeconds(100));

        otpRepository.save(otp);

        systemLogService.log(
                "OTP_SENT",
                phoneNumber,
                "OTP generated and sent"
        );

        LOGGER.info("OTP for {} = {}", phoneNumber, otp.getCode());
    }


    @Transactional
    public String loginWithOtp(String phoneNumber, String code) {

        systemLogService.log(
                "LOGIN_OTP_ATTEMPT",
                phoneNumber,
                "OTP login attempt"
        );

        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() ->
                        new ApiException(HttpStatus.NOT_FOUND, "User not found")
                );

        Otp otp = otpRepository
                .findTopByUserAndCodeAndUsedFalseOrderByCreatedAtDesc(user, code)
                .orElseThrow(() -> {
                    systemLogService.log(
                            "LOGIN_OTP_FAILED",
                            phoneNumber,
                            "Invalid OTP"
                    );
                    return new ApiException(HttpStatus.BAD_REQUEST, "Invalid OTP");
                });

        if (otp.getExpiresAt().isBefore(Instant.now())) {
            systemLogService.log(
                    "LOGIN_OTP_FAILED",
                    phoneNumber,
                    "OTP expired"
            );
            throw new ApiException(HttpStatus.BAD_REQUEST, "OTP expired");
        }

        otp.setUsed(true);
        otpRepository.save(otp);

        revokeAllUserTokens(user);

        String jwt = jwtTokenProvider.generateToken(user);
        saveToken(user, jwt);

        systemLogService.log(
                "LOGIN_OTP_SUCCESS",
                phoneNumber,
                "JWT issued via OTP login"
        );

        return jwt;
    }


    @Transactional
    public void logout(String tokenValue) {

        Token token = tokenRepository
                .findByTokenAndRevokedFalseAndExpiredFalse(tokenValue)
                .orElseThrow(() ->
                        new ApiException(HttpStatus.UNAUTHORIZED, "Token already invalid")
                );

        token.setRevoked(true);
        tokenRepository.save(token);

        systemLogService.log(
                "LOGOUT",
                token.getUser() != null ? token.getUser().getPhoneNumber() : null,
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

    private void saveToken(User user, String jwt) {
        Token token = new Token();
        token.setUser(user);
        token.setToken(jwt);
        token.setExpired(false);
        token.setRevoked(false);
        token.setCreatedAt(Instant.now());

        tokenRepository.save(token);
    }

    private String generateOtp() {
        int otp = (int) (Math.random() * 9000) + 1000;
        return String.valueOf(otp);
    }
}

package faraz.wallet.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "otps")
@Data
@NoArgsConstructor
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant expiresAt;


    @Column(nullable = false)
    private boolean used;

}

package fr.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name="persistent_logins")
public class PersistentLogin {


    @Column(name = "username", length = 64, nullable = false)
    private String email;

    @Id
    @GeneratedValue
    @Column(name="series", length = 64)
    private String series;

    @Column(name="token", length = 64, nullable = false)
    private String token;

    @Column(name="last_used", nullable = false)
    private Instant lastUsed;

}

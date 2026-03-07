package electro_wallet.entity;

import electro_wallet.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String username;

        @Column(nullable = false, unique = true)
        private String email;

        @Column(nullable = false)
        private String password;

        @Column(name = "phone_number", nullable = false, unique = true)
        private String phoneNumber;

        @Enumerated(EnumType.STRING)
        private Role role;

        private boolean enabled = true;

        @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
        private Account account;
}

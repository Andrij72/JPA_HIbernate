package com.akul.model.basic;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "email")
@Embeddable
public class Credentials {
        @Column(name = "email")
        private String email;

        @Column(name = "password")
        private String password;

        @Column(name = "last_modified_password")
        private LocalDateTime lastModifiedPassword;
}

package dev.project1backendspring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
@RequiredArgsConstructor
@Table(name = "Users")
public class User {
    @Id @GeneratedValue
    private Long id;

    @NonNull
    private String userName;

    @NonNull
    private String password;
}

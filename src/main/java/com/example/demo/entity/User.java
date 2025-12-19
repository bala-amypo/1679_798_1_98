package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String fullName;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    private String role;

    @Size(max = 50)
    private String preferredLearningStyle;

    private LocalDateTime createdAt;

    

    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY)
    private List<Course> courses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Progress> progressList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Recommendation> recommendations;

   

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.role == null || this.role.isBlank()) {
            this.role = "LEARNER";
        }
    }
}

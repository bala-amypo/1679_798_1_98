package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
    name = "courses",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "instructor_id"})
    }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String title;

    @Size(max = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;

    @NotBlank
    @Size(max = 50)
    private String category;

    private LocalDateTime createdAt;

    /* ---------- Relationships ---------- */

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<MicroLesson> microLessons;

    /* ---------- Lifecycle ---------- */

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}

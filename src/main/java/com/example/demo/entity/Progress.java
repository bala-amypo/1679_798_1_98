// package com.example.demo.model;

// import jakarta.persistence.*;

// @Entity
// public class Progress {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private int percentage;

//     @ManyToOne
//     @JoinColumn(name = "micro_lesson_id")
//     private MicroLesson microLesson;

//     @ManyToOne
//     @JoinColumn(name = "user_id")
//     private User user; // Assuming you have a User entity

//     // Getters and setters
//     public Long getId() { return id; }
//     public void setId(Long id) { this.id = id; }

//     public int getPercentage() { return percentage; }
//     public void setPercentage(int percentage) { this.percentage = percentage; }

//     public MicroLesson getMicroLesson() { return microLesson; }
//     public void setMicroLesson(MicroLesson microLesson) { this.microLesson = microLesson; }

//     public User getUser() { return user; }
//     public void setUser(User user) { this.user = user; }
// }


package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "progress",
       uniqueConstraints = @UniqueConstraint(
           columnNames = {"user_id", "micro_lesson_id"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Progress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "micro_lesson_id", nullable = false)
    private MicroLesson microLesson;
    
    @NotBlank(message = "Status is required")
    @Size(max = 20)
    @Column(nullable = false)
    @Builder.Default
    private String status = "NOT_STARTED";
    
    @NotNull(message = "Progress percent is required")
    @Min(value = 0, message = "Progress percent cannot be less than 0")
    @Max(value = 100, message = "Progress percent cannot exceed 100")
    @Column(nullable = false)
    private Integer progressPercent;
    
    @Column(name = "last_accessed_at")
    private LocalDateTime lastAccessedAt;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal score;
    
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastAccessedAt = LocalDateTime.now();
        
        if ("COMPLETED".equals(this.status)) {
            this.progressPercent = 100;
            if (this.completedAt == null) {
                this.completedAt = LocalDateTime.now();
            }
        }
    }
}
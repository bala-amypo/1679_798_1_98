package com.example.demo.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recommendation {

    @Id @GeneratedValue
    private Long id;

    private String recommendedLessonIds;
    private String basisSnapshot;
    private BigDecimal confidenceScore;
    private LocalDateTime generatedAt;

    @PrePersist
    public void prePersist() {
        generatedAt = LocalDateTime.now();
    }
}

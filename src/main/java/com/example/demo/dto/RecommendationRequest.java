// package com.example.demo.dto;

// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
// public class RecommendationRequest {
//     private Long userId;
//     private Long microLessonId;
// }


package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequest {
    private String tags;
    private String difficulty;
    private String contentType;
    private Integer limit;
    private String preferredLearningStyle;
}
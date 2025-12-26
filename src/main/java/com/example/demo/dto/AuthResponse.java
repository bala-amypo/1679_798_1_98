// package com.example.demo.dto;

// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
// public class AuthResponse {

//     private String message;

//     public AuthResponse(String message) {
//         this.message = message;
//     }
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
public class AuthResponse {
    private String accessToken;
    private Long userId;
    private String email;
    private String role;
    private String message;
}
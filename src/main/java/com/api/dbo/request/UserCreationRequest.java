package com.api.dbo.request;

import java.time.LocalDate;

import com.api.validor.DobConstraint;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 5, message = "USERNAME_ERROR")
    String userName;
    @Size(min = 8, message = "PASSWORD_ERROR")
    String password;
    String firstName;
    String lastName;
    String email;
    String phone;
    @DobConstraint(min = 16, message = "DOB_INVALID")
    LocalDate birthDate;
}
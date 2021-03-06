package com.moviesandchill.usermanagementservice.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserDto {
    private String login;

    private String email;

    private LocalDate birthday;

    private String logoUrl;

    private String description;
}

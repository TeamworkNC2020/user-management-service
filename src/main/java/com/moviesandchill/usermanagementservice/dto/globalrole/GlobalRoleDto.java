package com.moviesandchill.usermanagementservice.dto.globalrole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GlobalRoleDto {
    private Long globalRoleId;
    private String name;
}

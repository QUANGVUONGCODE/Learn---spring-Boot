package com.api.dbo.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleReponse {
    String name;
    String description;
    Set<PermissionReponse> permissions;
}

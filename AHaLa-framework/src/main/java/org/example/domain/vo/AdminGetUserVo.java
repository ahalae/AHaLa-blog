package org.example.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.example.domain.entity.Role;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminGetUserVo {
    private List<String> roleIds;
    private List<Role> roles;
    private UserInfoVo user;
}

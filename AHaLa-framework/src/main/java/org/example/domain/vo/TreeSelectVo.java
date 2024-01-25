package org.example.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.example.domain.entity.Menu;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TreeSelectVo {
    //菜单ID
    private Long id;

    //菜单名称
    private String label;

    //父菜单ID
    private Long parentId;

    private List<TreeSelectVo> children;
}

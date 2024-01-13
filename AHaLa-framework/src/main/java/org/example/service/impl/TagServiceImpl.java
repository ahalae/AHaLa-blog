package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.domain.entity.Tag;
import org.example.mapper.TagMapper;
import org.example.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author AHaLa
 * @since 2024-01-13 16:30:15
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}

package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.domain.ResponseResult;
import org.example.domain.dto.TagListDto;
import org.example.domain.entity.Tag;
import org.example.domain.vo.PageVo;


/**
 * 标签(Tag)表服务接口
 *
 * @author AHaLa
 * @since 2024-01-13 16:30:14
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagListDto tagListDto);

    ResponseResult deleteTag(Integer id);

    ResponseResult updateTag(TagListDto tagListDto);

    ResponseResult getTag(Integer id);

    ResponseResult listAllTag();
}

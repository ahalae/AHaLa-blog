package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.constants.SystemConstants;
import org.example.domain.ResponseResult;
import org.example.domain.dto.TagListDto;
import org.example.domain.entity.Tag;
import org.example.domain.vo.PageVo;
import org.example.domain.vo.TagVo;
import org.example.enums.AppHttpCodeEnum;
import org.example.exception.SystemException;
import org.example.mapper.TagMapper;
import org.example.service.TagService;
import org.example.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author AHaLa
 * @since 2024-01-13 16:30:15
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());

        Page<Tag> page =new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        //封装数据
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(TagListDto tagListDto) {
        if(!StringUtils.hasText(tagListDto.getName())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Integer id) {
        getBaseMapper().deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateTag(TagListDto tagListDto) {
        Tag tag = getById(tagListDto.getId());
        tag.setName(tagListDto.getName());
        tag.setRemark(tagListDto.getRemark());
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTag(Integer id) {
        Tag tag = getById(id);
        return ResponseResult.okResult(tag);
    }

    @Override
    public ResponseResult listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(wrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }


}

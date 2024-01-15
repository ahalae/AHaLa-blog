package org.example.controller;

import org.example.domain.ResponseResult;
import org.example.domain.dto.TagListDto;
import org.example.domain.vo.PageVo;
import org.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }
    @PostMapping
    public ResponseResult addTag(@RequestBody TagListDto tagListDto){
        return tagService.addTag(tagListDto);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable("id") Integer id){
        return tagService.deleteTag(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getTag(@PathVariable("id") Integer id){
        return tagService.getTag(id);
    }
    @PutMapping
    public ResponseResult updateTag(@RequestBody TagListDto tagListDto){
        return tagService.updateTag(tagListDto);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }

}

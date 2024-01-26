package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.domain.ResponseResult;
import org.example.domain.entity.Link;
import org.example.domain.vo.LinkVo;
import org.example.domain.vo.PageVo;


/**
 * 友链(Link)表服务接口
 *
 * @author AHaLa
 * @since 2023-12-24 21:47:09
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult<PageVo> pageLinkList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addLink(LinkVo linkVo);

    ResponseResult getLink(Long id);

    ResponseResult updateLink(LinkVo linkVo);

    ResponseResult deleteLink(Long id);
}

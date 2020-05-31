package com.guomz.csleeve.api.v1;

import com.guomz.csleeve.model.Spu;
import com.guomz.csleeve.service.SpuService;
import com.guomz.csleeve.vo.PageDozer;
import com.guomz.csleeve.vo.SpuSimplifyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/v1/spu")
@Validated
public class SpuController {

    @Autowired
    private SpuService spuService;

    /**
     * 返回spu明细
     * @param id
     * @return
     */
    @GetMapping("/id/{id}/detail")
    public Spu getDetail(@PathVariable("id") @Positive Long id){
        return spuService.getDetail(id);
    }

    /**
     * 分页返回最新的spu
     * @param start
     * @param count
     * @return
     */
    @GetMapping("/latest")
    public PageDozer<Spu, SpuSimplifyVo> getLatestSpuByPage(@RequestParam(value = "start", defaultValue = "0") Integer start,
                                                            @RequestParam(value = "count", defaultValue = "10") Integer count){
        Page<Spu> page = spuService.getLatestSpuByPage(start, count);
        return new PageDozer<>(page, SpuSimplifyVo.class);
    }

    /**
     * 根据类别返回spu
     * @param categoryId
     * @param isRoot
     * @param start
     * @param count
     * @return
     */
    @GetMapping("/by/category/{id}")
    public PageDozer<Spu, SpuSimplifyVo> getSpuByCategory(@PathVariable("id") Long categoryId,
                                                          @RequestParam("is_root") Boolean isRoot,
                                                          @RequestParam(value = "start", defaultValue = "0") Integer start,
                                                          @RequestParam(value = "count", defaultValue = "10") Integer count){
        Page<Spu> page = spuService.getSpuByCategory(categoryId, isRoot, start, count);
        return new PageDozer<>(page, SpuSimplifyVo.class);
    }
}

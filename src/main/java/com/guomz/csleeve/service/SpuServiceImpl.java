package com.guomz.csleeve.service;

import com.guomz.csleeve.bo.PageBo;
import com.guomz.csleeve.exception.http.NotFoundException;
import com.guomz.csleeve.model.Spu;
import com.guomz.csleeve.repository.SpuRepository;
import com.guomz.csleeve.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SpuServiceImpl implements SpuService{

    @Autowired
    private SpuRepository spuRepository;

    /**
     * 返回spu细节
     * @param id
     * @return
     */
    //@Override
    public Spu getDetail(Long id) {
        Spu spu = spuRepository.findSpuById(id);
        if(spu == null){
            throw new NotFoundException(30002);
        }
        return spu;
    }

    /**
     * 分页返回最新商品
     * @param start
     * @param count
     * @return
     */
    //@Override
    public Page<Spu> getLatestSpuByPage(int start, int count) {
        PageBo pageBo = CommonUtil.convertStartToPage(start, count);
        Pageable pageable = PageRequest.of(pageBo.getPageNum(), pageBo.getCount(), Sort.by("createTime").descending());
        Page<Spu> page = spuRepository.findAll(pageable);
        return page;
    }

    /**
     * 按照分类返回对应商品
     * @param categoryId
     * @param isRoot
     * @param start
     * @param count
     * @return
     */
    //@Override
    public Page<Spu> getSpuByCategory(long categoryId, boolean isRoot, int start, int count) {
        PageBo pageBo = CommonUtil.convertStartToPage(start, count);
        Pageable pageable = PageRequest.of(pageBo.getPageNum(), pageBo.getCount(), Sort.by("createTime").descending());
        if(isRoot){
            return spuRepository.findSpuByRootCategoryId(categoryId, pageable);
        }
        else{
            return spuRepository.findSpuByCategoryId(categoryId, pageable);
        }
    }
}

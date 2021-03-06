package com.yww.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yww.educms.entity.CmsBanner;
import com.yww.educms.mapper.CmsBannerMapper;
import com.yww.educms.service.CmsBannerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author yww
 * @since 2021-02-10
 */
@Service
public class CmsBannerServiceImpl extends ServiceImpl<CmsBannerMapper, CmsBanner> implements CmsBannerService {

    /**
     * @Descriprtion 根据SORT查询三条Banner
     * @return
     */
    @Override
    @Cacheable(key="'selectIndexList'",value="banner")
    public List<CmsBanner> selectBanner() {
        // 根据sort进行升序排列，并取其中三条记录
        QueryWrapper<CmsBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        wrapper.last("limit 3");

        List<CmsBanner> list = baseMapper.selectList(wrapper);
        return list;
    }
}

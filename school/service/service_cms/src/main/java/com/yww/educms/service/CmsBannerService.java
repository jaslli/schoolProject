package com.yww.educms.service;

import com.yww.educms.entity.CmsBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author yww
 */
public interface CmsBannerService extends IService<CmsBanner> {

    /**
     * 查询三张Banner
     * @return Banner数据
     */
    List<CmsBanner> selectBanner();
}

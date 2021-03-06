package com.yww.educms.service;

import com.yww.educms.entity.CmsBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author yww
 * @since 2021-02-10
 */
public interface CmsBannerService extends IService<CmsBanner> {

    List<CmsBanner> selectBanner();
}

package org.jeecg.modules.ddfcagoods.service.impl;

import org.jeecg.modules.ddfcagoods.entity.DdfCaGoods;
import org.jeecg.modules.ddfcagoods.mapper.DdfCaGoodsMapper;
import org.jeecg.modules.ddfcagoods.service.IDdfCaGoodsService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Collection;

/**
 * @Description: 平台商品
 * @Author: jeecg-boot
 * @Date:   2023-07-15
 * @Version: V1.0
 */
@Service
public class DdfCaGoodsServiceImpl extends ServiceImpl<DdfCaGoodsMapper, DdfCaGoods> implements IDdfCaGoodsService {
    //重写saveBatch方法
    @Override
    public boolean saveBatch(Collection<DdfCaGoods> entityList) {
        // TODO Auto-generated method stub
        System.out.println("saveBatch方法被调用了");
        entityList.forEach(System.out::println);
        return super.saveBatch(entityList);
    }
}

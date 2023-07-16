package org.jeecg.modules.ddfprorder.service.impl;

import org.jeecg.modules.ddfprorder.entity.DdfPrOrderdetail;
import org.jeecg.modules.ddfprorder.mapper.DdfPrOrderdetailMapper;
import org.jeecg.modules.ddfprorder.service.IDdfPrOrderdetailService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 订单明细
 * @Author: jeecg-boot
 * @Date:   2023-07-16
 * @Version: V1.0
 */
@Service
public class DdfPrOrderdetailServiceImpl extends ServiceImpl<DdfPrOrderdetailMapper, DdfPrOrderdetail> implements IDdfPrOrderdetailService {
	
	@Autowired
	private DdfPrOrderdetailMapper ddfPrOrderdetailMapper;
	
	@Override
	public List<DdfPrOrderdetail> selectByMainId(String mainId) {
		return ddfPrOrderdetailMapper.selectByMainId(mainId);
	}
}

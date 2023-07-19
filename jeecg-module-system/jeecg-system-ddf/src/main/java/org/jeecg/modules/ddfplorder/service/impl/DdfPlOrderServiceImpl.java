package org.jeecg.modules.ddfplorder.service.impl;

import org.jeecg.modules.ddfplorder.entity.DdfPlOrder;
import org.jeecg.modules.ddfplorder.entity.DdfPlOrderdetail;
import org.jeecg.modules.ddfplorder.mapper.DdfPlOrderdetailMapper;
import org.jeecg.modules.ddfplorder.mapper.DdfPlOrderMapper;
import org.jeecg.modules.ddfplorder.service.IDdfPlOrderService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 计划订单
 * @Author: jeecg-boot
 * @Date:   2023-07-19
 * @Version: V1.0
 */
@Service
public class DdfPlOrderServiceImpl extends ServiceImpl<DdfPlOrderMapper, DdfPlOrder> implements IDdfPlOrderService {

	@Autowired
	private DdfPlOrderMapper ddfPlOrderMapper;
	@Autowired
	private DdfPlOrderdetailMapper ddfPlOrderdetailMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		ddfPlOrderdetailMapper.deleteByMainId(id);
		ddfPlOrderMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			ddfPlOrderdetailMapper.deleteByMainId(id.toString());
			ddfPlOrderMapper.deleteById(id);
		}
	}
	
}

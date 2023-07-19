package org.jeecg.modules.ddfinorder.service.impl;

import org.jeecg.modules.ddfinorder.entity.DdfInOrder;
import org.jeecg.modules.ddfinorder.entity.DdfInOrderdetail;
import org.jeecg.modules.ddfinorder.mapper.DdfInOrderdetailMapper;
import org.jeecg.modules.ddfinorder.mapper.DdfInOrderMapper;
import org.jeecg.modules.ddfinorder.service.IDdfInOrderService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 内部订单
 * @Author: jeecg-boot
 * @Date:   2023-07-18
 * @Version: V1.0
 */
@Service
public class DdfInOrderServiceImpl extends ServiceImpl<DdfInOrderMapper, DdfInOrder> implements IDdfInOrderService {

	@Autowired
	private DdfInOrderMapper ddfInOrderMapper;
	@Autowired
	private DdfInOrderdetailMapper ddfInOrderdetailMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(DdfInOrder ddfInOrder, List<DdfInOrderdetail> ddfInOrderdetailList) {
		ddfInOrderMapper.insert(ddfInOrder);
		if(ddfInOrderdetailList!=null && ddfInOrderdetailList.size()>0) {
			for(DdfInOrderdetail entity:ddfInOrderdetailList) {
				//外键设置
				entity.setOrderId(ddfInOrder.getId());
				ddfInOrderdetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(DdfInOrder ddfInOrder,List<DdfInOrderdetail> ddfInOrderdetailList) {
		ddfInOrderMapper.updateById(ddfInOrder);
		
		//1.先删除子表数据
		ddfInOrderdetailMapper.deleteByMainId(ddfInOrder.getId());
		
		//2.子表数据重新插入
		if(ddfInOrderdetailList!=null && ddfInOrderdetailList.size()>0) {
			for(DdfInOrderdetail entity:ddfInOrderdetailList) {
				//外键设置
				entity.setOrderId(ddfInOrder.getId());
				ddfInOrderdetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		ddfInOrderdetailMapper.deleteByMainId(id);
		ddfInOrderMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			ddfInOrderdetailMapper.deleteByMainId(id.toString());
			ddfInOrderMapper.deleteById(id);
		}
	}
	
}

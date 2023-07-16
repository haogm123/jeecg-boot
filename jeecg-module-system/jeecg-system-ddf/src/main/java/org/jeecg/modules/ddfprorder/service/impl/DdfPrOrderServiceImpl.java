package org.jeecg.modules.ddfprorder.service.impl;

import org.jeecg.modules.ddfprorder.entity.DdfPrOrder;
import org.jeecg.modules.ddfprorder.entity.DdfPrInvoice;
import org.jeecg.modules.ddfprorder.entity.DdfPrOrderdetail;
import org.jeecg.modules.ddfprorder.mapper.DdfPrInvoiceMapper;
import org.jeecg.modules.ddfprorder.mapper.DdfPrOrderdetailMapper;
import org.jeecg.modules.ddfprorder.mapper.DdfPrOrderMapper;
import org.jeecg.modules.ddfprorder.service.IDdfPrOrderService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 订单中心
 * @Author: jeecg-boot
 * @Date:   2023-07-16
 * @Version: V1.0
 */
@Service
public class DdfPrOrderServiceImpl extends ServiceImpl<DdfPrOrderMapper, DdfPrOrder> implements IDdfPrOrderService {

	@Autowired
	private DdfPrOrderMapper ddfPrOrderMapper;
	@Autowired
	private DdfPrInvoiceMapper ddfPrInvoiceMapper;
	@Autowired
	private DdfPrOrderdetailMapper ddfPrOrderdetailMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(DdfPrOrder ddfPrOrder, List<DdfPrInvoice> ddfPrInvoiceList,List<DdfPrOrderdetail> ddfPrOrderdetailList) {
		ddfPrOrderMapper.insert(ddfPrOrder);
		if(ddfPrInvoiceList!=null && ddfPrInvoiceList.size()>0) {
			for(DdfPrInvoice entity:ddfPrInvoiceList) {
				//外键设置
				entity.setOrderid(ddfPrOrder.getId());
				ddfPrInvoiceMapper.insert(entity);
			}
		}
		if(ddfPrOrderdetailList!=null && ddfPrOrderdetailList.size()>0) {
			for(DdfPrOrderdetail entity:ddfPrOrderdetailList) {
				//外键设置
				entity.setOrderId(ddfPrOrder.getId());
				ddfPrOrderdetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(DdfPrOrder ddfPrOrder,List<DdfPrInvoice> ddfPrInvoiceList,List<DdfPrOrderdetail> ddfPrOrderdetailList) {
		ddfPrOrderMapper.updateById(ddfPrOrder);
		
		//1.先删除子表数据
		ddfPrInvoiceMapper.deleteByMainId(ddfPrOrder.getId());
		ddfPrOrderdetailMapper.deleteByMainId(ddfPrOrder.getId());
		
		//2.子表数据重新插入
		if(ddfPrInvoiceList!=null && ddfPrInvoiceList.size()>0) {
			for(DdfPrInvoice entity:ddfPrInvoiceList) {
				//外键设置
				entity.setOrderid(ddfPrOrder.getId());
				ddfPrInvoiceMapper.insert(entity);
			}
		}
		if(ddfPrOrderdetailList!=null && ddfPrOrderdetailList.size()>0) {
			for(DdfPrOrderdetail entity:ddfPrOrderdetailList) {
				//外键设置
				entity.setOrderId(ddfPrOrder.getId());
				ddfPrOrderdetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		ddfPrInvoiceMapper.deleteByMainId(id);
		ddfPrOrderdetailMapper.deleteByMainId(id);
		ddfPrOrderMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			ddfPrInvoiceMapper.deleteByMainId(id.toString());
			ddfPrOrderdetailMapper.deleteByMainId(id.toString());
			ddfPrOrderMapper.deleteById(id);
		}
	}
	
}

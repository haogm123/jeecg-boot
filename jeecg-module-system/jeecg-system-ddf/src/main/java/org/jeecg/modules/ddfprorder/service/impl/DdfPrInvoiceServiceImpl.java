package org.jeecg.modules.ddfprorder.service.impl;

import org.jeecg.modules.ddfprorder.entity.DdfPrInvoice;
import org.jeecg.modules.ddfprorder.mapper.DdfPrInvoiceMapper;
import org.jeecg.modules.ddfprorder.service.IDdfPrInvoiceService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 订单发票
 * @Author: jeecg-boot
 * @Date:   2023-07-16
 * @Version: V1.0
 */
@Service
public class DdfPrInvoiceServiceImpl extends ServiceImpl<DdfPrInvoiceMapper, DdfPrInvoice> implements IDdfPrInvoiceService {
	
	@Autowired
	private DdfPrInvoiceMapper ddfPrInvoiceMapper;
	
	@Override
	public List<DdfPrInvoice> selectByMainId(String mainId) {
		return ddfPrInvoiceMapper.selectByMainId(mainId);
	}
}

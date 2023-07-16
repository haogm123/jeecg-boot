package org.jeecg.modules.ddfprorder.service;

import org.jeecg.modules.ddfprorder.entity.DdfPrInvoice;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 订单发票
 * @Author: jeecg-boot
 * @Date:   2023-07-16
 * @Version: V1.0
 */
public interface IDdfPrInvoiceService extends IService<DdfPrInvoice> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<DdfPrInvoice>
	 */
	public List<DdfPrInvoice> selectByMainId(String mainId);
}

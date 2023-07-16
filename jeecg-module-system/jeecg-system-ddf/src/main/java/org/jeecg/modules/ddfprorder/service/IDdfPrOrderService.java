package org.jeecg.modules.ddfprorder.service;

import org.jeecg.modules.ddfprorder.entity.DdfPrInvoice;
import org.jeecg.modules.ddfprorder.entity.DdfPrOrderdetail;
import org.jeecg.modules.ddfprorder.entity.DdfPrOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 订单中心
 * @Author: jeecg-boot
 * @Date:   2023-07-16
 * @Version: V1.0
 */
public interface IDdfPrOrderService extends IService<DdfPrOrder> {

	/**
	 * 添加一对多
	 *
	 * @param ddfPrOrder
	 * @param ddfPrInvoiceList
	 * @param ddfPrOrderdetailList
	 */
	public void saveMain(DdfPrOrder ddfPrOrder,List<DdfPrInvoice> ddfPrInvoiceList,List<DdfPrOrderdetail> ddfPrOrderdetailList) ;
	
	/**
	 * 修改一对多
	 *
	 * @param ddfPrOrder
	 * @param ddfPrInvoiceList
	 * @param ddfPrOrderdetailList
	 */
	public void updateMain(DdfPrOrder ddfPrOrder,List<DdfPrInvoice> ddfPrInvoiceList,List<DdfPrOrderdetail> ddfPrOrderdetailList);
	
	/**
	 * 删除一对多
	 *
	 * @param id
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 *
	 * @param idList
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}

package org.jeecg.modules.ddfinorder.service;

import org.jeecg.modules.ddfinorder.entity.DdfInOrderdetail;
import org.jeecg.modules.ddfinorder.entity.DdfInOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 内部订单
 * @Author: jeecg-boot
 * @Date:   2023-07-18
 * @Version: V1.0
 */
public interface IDdfInOrderService extends IService<DdfInOrder> {

	/**
	 * 添加一对多
	 *
	 * @param ddfInOrder
	 * @param ddfInOrderdetailList
	 */
	public void saveMain(DdfInOrder ddfInOrder,List<DdfInOrderdetail> ddfInOrderdetailList) ;
	
	/**
	 * 修改一对多
	 *
	 * @param ddfInOrder
	 * @param ddfInOrderdetailList
	 */
	public void updateMain(DdfInOrder ddfInOrder,List<DdfInOrderdetail> ddfInOrderdetailList);
	
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

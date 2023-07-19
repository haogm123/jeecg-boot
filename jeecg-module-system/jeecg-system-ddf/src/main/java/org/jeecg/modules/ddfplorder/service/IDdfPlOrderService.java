package org.jeecg.modules.ddfplorder.service;

import org.jeecg.modules.ddfplorder.entity.DdfPlOrderdetail;
import org.jeecg.modules.ddfplorder.entity.DdfPlOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 计划订单
 * @Author: jeecg-boot
 * @Date:   2023-07-19
 * @Version: V1.0
 */
public interface IDdfPlOrderService extends IService<DdfPlOrder> {

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

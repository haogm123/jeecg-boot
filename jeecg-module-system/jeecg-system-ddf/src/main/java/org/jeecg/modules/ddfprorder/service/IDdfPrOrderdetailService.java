package org.jeecg.modules.ddfprorder.service;

import org.jeecg.modules.ddfprorder.entity.DdfPrOrderdetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 订单明细
 * @Author: jeecg-boot
 * @Date:   2023-07-16
 * @Version: V1.0
 */
public interface IDdfPrOrderdetailService extends IService<DdfPrOrderdetail> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<DdfPrOrderdetail>
	 */
	public List<DdfPrOrderdetail> selectByMainId(String mainId);
}

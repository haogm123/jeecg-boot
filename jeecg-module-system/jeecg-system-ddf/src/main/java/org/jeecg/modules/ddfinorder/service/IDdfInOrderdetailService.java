package org.jeecg.modules.ddfinorder.service;

import org.jeecg.modules.ddfinorder.entity.DdfInOrderdetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 内部订单明细
 * @Author: jeecg-boot
 * @Date:   2023-07-18
 * @Version: V1.0
 */
public interface IDdfInOrderdetailService extends IService<DdfInOrderdetail> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<DdfInOrderdetail>
	 */
	public List<DdfInOrderdetail> selectByMainId(String mainId);
}

package org.jeecg.modules.ddfprorder.mapper;

import java.util.List;
import org.jeecg.modules.ddfprorder.entity.DdfPrInvoice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 订单发票
 * @Author: jeecg-boot
 * @Date:   2023-07-16
 * @Version: V1.0
 */
public interface DdfPrInvoiceMapper extends BaseMapper<DdfPrInvoice> {

	/**
	 * 通过主表id删除子表数据
	 *
	 * @param mainId 主表id
	 * @return boolean
	 */
	public boolean deleteByMainId(@Param("mainId") String mainId);

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId 主表id
   * @return List<DdfPrInvoice>
   */
	public List<DdfPrInvoice> selectByMainId(@Param("mainId") String mainId);
}

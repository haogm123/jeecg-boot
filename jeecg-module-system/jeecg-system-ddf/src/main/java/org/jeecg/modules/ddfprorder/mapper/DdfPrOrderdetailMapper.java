package org.jeecg.modules.ddfprorder.mapper;

import java.util.List;
import org.jeecg.modules.ddfprorder.entity.DdfPrOrderdetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 订单明细
 * @Author: jeecg-boot
 * @Date:   2023-07-16
 * @Version: V1.0
 */
public interface DdfPrOrderdetailMapper extends BaseMapper<DdfPrOrderdetail> {

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
   * @return List<DdfPrOrderdetail>
   */
	public List<DdfPrOrderdetail> selectByMainId(@Param("mainId") String mainId);
}

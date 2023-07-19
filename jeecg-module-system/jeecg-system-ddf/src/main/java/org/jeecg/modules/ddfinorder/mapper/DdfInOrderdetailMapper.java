package org.jeecg.modules.ddfinorder.mapper;

import java.util.List;
import org.jeecg.modules.ddfinorder.entity.DdfInOrderdetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 内部订单明细
 * @Author: jeecg-boot
 * @Date:   2023-07-18
 * @Version: V1.0
 */
public interface DdfInOrderdetailMapper extends BaseMapper<DdfInOrderdetail> {

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
   * @return List<DdfInOrderdetail>
   */
	public List<DdfInOrderdetail> selectByMainId(@Param("mainId") String mainId);
}

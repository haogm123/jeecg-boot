package org.jeecg.modules.ddfplorder.mapper;

import java.util.List;
import org.jeecg.modules.ddfplorder.entity.DdfPlOrderdetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 计划明细
 * @Author: jeecg-boot
 * @Date:   2023-07-19
 * @Version: V1.0
 */
public interface DdfPlOrderdetailMapper extends BaseMapper<DdfPlOrderdetail> {

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
    * @return List<DdfPlOrderdetail>
    */
	public List<DdfPlOrderdetail> selectByMainId(@Param("mainId") String mainId);

}

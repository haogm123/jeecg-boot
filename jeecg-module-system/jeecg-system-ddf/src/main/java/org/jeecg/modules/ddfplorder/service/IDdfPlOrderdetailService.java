package org.jeecg.modules.ddfplorder.service;

import org.jeecg.modules.ddfplorder.entity.DdfPlOrderdetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 计划明细
 * @Author: jeecg-boot
 * @Date:   2023-07-19
 * @Version: V1.0
 */
public interface IDdfPlOrderdetailService extends IService<DdfPlOrderdetail> {

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId
   * @return List<DdfPlOrderdetail>
   */
	public List<DdfPlOrderdetail> selectByMainId(String mainId);
}

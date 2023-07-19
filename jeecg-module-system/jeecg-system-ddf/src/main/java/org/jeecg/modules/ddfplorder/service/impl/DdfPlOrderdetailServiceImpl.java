package org.jeecg.modules.ddfplorder.service.impl;

import org.jeecg.modules.ddfplorder.entity.DdfPlOrderdetail;
import org.jeecg.modules.ddfplorder.mapper.DdfPlOrderdetailMapper;
import org.jeecg.modules.ddfplorder.service.IDdfPlOrderdetailService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 计划明细
 * @Author: jeecg-boot
 * @Date:   2023-07-19
 * @Version: V1.0
 */
@Service
public class DdfPlOrderdetailServiceImpl extends ServiceImpl<DdfPlOrderdetailMapper, DdfPlOrderdetail> implements IDdfPlOrderdetailService {
	
	@Autowired
	private DdfPlOrderdetailMapper ddfPlOrderdetailMapper;
	
	@Override
	public List<DdfPlOrderdetail> selectByMainId(String mainId) {
		return ddfPlOrderdetailMapper.selectByMainId(mainId);
	}
}

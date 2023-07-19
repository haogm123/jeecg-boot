package org.jeecg.modules.ddfinorder.service.impl;

import org.jeecg.modules.ddfinorder.entity.DdfInOrderdetail;
import org.jeecg.modules.ddfinorder.mapper.DdfInOrderdetailMapper;
import org.jeecg.modules.ddfinorder.service.IDdfInOrderdetailService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 内部订单明细
 * @Author: jeecg-boot
 * @Date:   2023-07-18
 * @Version: V1.0
 */
@Service
public class DdfInOrderdetailServiceImpl extends ServiceImpl<DdfInOrderdetailMapper, DdfInOrderdetail> implements IDdfInOrderdetailService {
	
	@Autowired
	private DdfInOrderdetailMapper ddfInOrderdetailMapper;
	
	@Override
	public List<DdfInOrderdetail> selectByMainId(String mainId) {
		return ddfInOrderdetailMapper.selectByMainId(mainId);
	}
}

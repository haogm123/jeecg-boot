package org.jeecg.modules.ddfprgoods.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.ddfcagoods.entity.DdfCaGoods;
import org.jeecg.modules.ddfcagoods.service.IDdfCaGoodsService;
import org.jeecg.modules.ddfprgoods.entity.DdfPrGoods;
import org.jeecg.modules.ddfprgoods.service.IDdfPrGoodsService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 商家商品
 * @Author: jeecg-boot
 * @Date:   2023-07-15
 * @Version: V1.0
 */
@Api(tags="商家商品")
@RestController
@RequestMapping("/ddf/prgoods")
@Slf4j
public class DdfPrGoodsController extends JeecgController<DdfPrGoods, IDdfPrGoodsService> {
	@Autowired
	private IDdfPrGoodsService ddfPrGoodsService;
	@Autowired
	private IDdfCaGoodsService iDdfCaGoodsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param ddfPrGoods
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "商家商品-分页列表查询")
	@ApiOperation(value="商家商品-分页列表查询", notes="商家商品-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<DdfPrGoods>> queryPageList(DdfPrGoods ddfPrGoods,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DdfPrGoods> queryWrapper = QueryGenerator.initQueryWrapper(ddfPrGoods, req.getParameterMap());
		Page<DdfPrGoods> page = new Page<DdfPrGoods>(pageNo, pageSize);
		IPage<DdfPrGoods> pageList = ddfPrGoodsService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param ddfPrGoods
	 * @return
	 */
	@AutoLog(value = "商家商品-添加")
	@ApiOperation(value="商家商品-添加", notes="商家商品-添加")
	@RequiresPermissions("ddfprgoods:ddf_pr_goods:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody DdfPrGoods ddfPrGoods) {
		ddfPrGoodsService.save(ddfPrGoods);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ddfPrGoods
	 * @return
	 */
	@AutoLog(value = "商家商品-编辑")
	@ApiOperation(value="商家商品-编辑", notes="商家商品-编辑")
	@RequiresPermissions("ddfprgoods:ddf_pr_goods:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody DdfPrGoods ddfPrGoods) {
		ddfPrGoodsService.updateById(ddfPrGoods);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商家商品-通过id删除")
	@ApiOperation(value="商家商品-通过id删除", notes="商家商品-通过id删除")
	@RequiresPermissions("ddfprgoods:ddf_pr_goods:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		ddfPrGoodsService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "商家商品-批量删除")
	@ApiOperation(value="商家商品-批量删除", notes="商家商品-批量删除")
	@RequiresPermissions("ddfprgoods:ddf_pr_goods:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ddfPrGoodsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "商家商品-通过id查询")
	@ApiOperation(value="商家商品-通过id查询", notes="商家商品-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<DdfPrGoods> queryById(@RequestParam(name="id",required=true) String id) {
		DdfPrGoods ddfPrGoods = ddfPrGoodsService.getById(id);
		if(ddfPrGoods==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ddfPrGoods);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ddfPrGoods
    */
    @RequiresPermissions("ddfprgoods:ddf_pr_goods:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DdfPrGoods ddfPrGoods) {
        return super.exportXls(request, ddfPrGoods, DdfPrGoods.class, "商家商品");
    }

    /**
      * 通过excel导入店铺商品数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("ddfprgoods:ddf_pr_goods:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result result= super.importExcel(request, response, DdfPrGoods.class);
		//查询DdfPrGoods表中的所有数据
		QueryWrapper<DdfPrGoods> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("create_by",sysUser.getUsername());
		queryWrapper.eq("status",0);
		List<DdfPrGoods> list = ddfPrGoodsService.list(queryWrapper);
		list.forEach(item->{
			//根据69码匹配平台商品
			QueryWrapper<DdfCaGoods> queryWrapper1 = new QueryWrapper<>();
			queryWrapper1.eq("bar_code",item.getBarcode());
			queryWrapper1.eq("status",1);
			DdfCaGoods ddfCaGoods =iDdfCaGoodsService.getOne(queryWrapper1);
			if (ddfCaGoods != null){
				item.setUpdateTime(new Date());
				item.setStatus(new Integer("3"));
				item.setExt1(ddfCaGoods.getCaName());
				item.setCaId(ddfCaGoods.getId());
			}else {
				item.setUpdateTime(new Date());
				item.setStatus(new Integer("5"));
				item.setExt1("未匹配到数据！");
			}
		});
		ddfPrGoodsService.saveOrUpdateBatch(list);
		return result;
    }

}

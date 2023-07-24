package org.jeecg.modules.ddfcagoods.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.ddfcagoods.entity.DdfCaGoods;
import org.jeecg.modules.ddfcagoods.entity.DdfCaGoodsiarvo;
import org.jeecg.modules.ddfcagoods.entity.DdfCaGoodsimagevo;
import org.jeecg.modules.ddfcagoods.entity.DdfCaGoodsvo;
import org.jeecg.modules.ddfcagoods.service.IDdfCaGoodsService;

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
import org.springframework.beans.BeanUtils;
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
 * @Description: 平台商品
 * @Author: jeecg-boot
 * @Date:   2023-07-15
 * @Version: V1.0
 */
@Api(tags="平台商品")
@RestController
@RequestMapping("/ddf/cagoods")
@Slf4j
public class DdfCaGoodsController extends JeecgController<DdfCaGoods, IDdfCaGoodsService> {
	@Autowired
	private IDdfCaGoodsService ddfCaGoodsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param ddfCaGoods
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "平台商品-分页列表查询")
	@ApiOperation(value="平台商品-分页列表查询", notes="平台商品-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<DdfCaGoods>> queryPageList(DdfCaGoods ddfCaGoods,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DdfCaGoods> queryWrapper = QueryGenerator.initQueryWrapper(ddfCaGoods, req.getParameterMap());
		Page<DdfCaGoods> page = new Page<DdfCaGoods>(pageNo, pageSize);
		IPage<DdfCaGoods> pageList = ddfCaGoodsService.page(page, queryWrapper);
		pageList.getRecords().forEach(item->{
			if (item.getImg() !=null){
				item.setImgarr(item.getImg().split(","));
			}
		});
		return Result.OK(pageList);
	}

	 @ApiOperation(value="平台商品-APP查询", notes="平台商品-APP查询")
	 @GetMapping(value = "/applist")
	 public List<DdfCaGoodsvo> applist() {
		 QueryWrapper<DdfCaGoods> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("status",1);
		 //限制条数
		 queryWrapper.last("limit 10");
		 List<DdfCaGoods> list = ddfCaGoodsService.list(queryWrapper);
		 List<DdfCaGoodsvo> listvo = list.stream().map(item -> {
			 DdfCaGoodsvo vo = new DdfCaGoodsvo();
			 //反射复制类
			 BeanUtils.copyProperties(item,vo);
			 vo.setName(item.getCaName());
			 vo.setShortDescription(item.getContent());
			 vo.setDescription(item.getContent());
			 vo.setPrice("18");
			 vo.setType("simple");

			 DdfCaGoodsiarvo iarvo = new DdfCaGoodsiarvo();
			 iarvo.setId(item.getId());
			 iarvo.setName("Color");
			 iarvo.setOptions("Red,Green,Blue".split(","));
			 iarvo.setVisible(true);
			 iarvo.setVariation(false);
			 vo.setAttributes(Arrays.asList(iarvo));

			 if (item.getImg() !=null){
				 List<DdfCaGoodsimagevo> imglist = Arrays.stream(item.getImg().split(",")).map(img -> {
					 DdfCaGoodsimagevo imagevo = new DdfCaGoodsimagevo();
					 imagevo.setSrc(img);
					 imagevo.setId(item.getId());
					 imagevo.setName(item.getCaName());
					 return imagevo;
				 }).collect(Collectors.toList());
				 vo.setImages(imglist);
			 }
			 return vo;
		 }).collect(Collectors.toList());
		 return listvo;
	 }
	/**
	 *   添加
	 *
	 * @param ddfCaGoods
	 * @return
	 */
	@AutoLog(value = "平台商品-添加")
	@ApiOperation(value="平台商品-添加", notes="平台商品-添加")
	@RequiresPermissions("ddfcagoods:ddf_ca_goods:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody DdfCaGoods ddfCaGoods) {
		ddfCaGoodsService.save(ddfCaGoods);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ddfCaGoods
	 * @return
	 */
	@AutoLog(value = "平台商品-编辑")
	@ApiOperation(value="平台商品-编辑", notes="平台商品-编辑")
	@RequiresPermissions("ddfcagoods:ddf_ca_goods:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody DdfCaGoods ddfCaGoods) {
		ddfCaGoodsService.updateById(ddfCaGoods);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "平台商品-通过id删除")
	@ApiOperation(value="平台商品-通过id删除", notes="平台商品-通过id删除")
	@RequiresPermissions("ddfcagoods:ddf_ca_goods:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		ddfCaGoodsService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "平台商品-批量删除")
	@ApiOperation(value="平台商品-批量删除", notes="平台商品-批量删除")
	@RequiresPermissions("ddfcagoods:ddf_ca_goods:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ddfCaGoodsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "平台商品-通过id查询")
	@ApiOperation(value="平台商品-通过id查询", notes="平台商品-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<DdfCaGoods> queryById(@RequestParam(name="id",required=true) String id) {
		DdfCaGoods ddfCaGoods = ddfCaGoodsService.getById(id);
		if(ddfCaGoods==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ddfCaGoods);
	}

	 @ApiOperation(value="app-通过id查询", notes="app-通过id查询")
	 @GetMapping(value = "/appqueryId")
	 public DdfCaGoodsvo appqueryId(@RequestParam(name="id",required=true) String id) {
		 DdfCaGoods item = ddfCaGoodsService.getById(id);
		 DdfCaGoodsvo vo = new DdfCaGoodsvo();
		 BeanUtils.copyProperties(item,vo);
		 vo.setName(item.getCaName());
		 vo.setShortDescription(item.getContent());
		 vo.setDescription(item.getContent());
		 vo.setPrice("18");
		 vo.setType("simple");

		 DdfCaGoodsiarvo iarvo = new DdfCaGoodsiarvo();
		 iarvo.setId(item.getId());
		 iarvo.setName("Color");
		 iarvo.setOptions("Red,Green,Blue".split(","));
		 iarvo.setVisible(true);
		 iarvo.setVariation(false);
		 vo.setAttributes(Arrays.asList(iarvo));

		 if (item.getImg() !=null){
			 List<DdfCaGoodsimagevo> imglist = Arrays.stream(item.getImg().split(",")).map(img -> {
				 DdfCaGoodsimagevo imagevo = new DdfCaGoodsimagevo();
				 imagevo.setSrc(img);
				 imagevo.setId(item.getId());
				 imagevo.setName(item.getCaName());
				 return imagevo;
			 }).collect(Collectors.toList());
			 vo.setImages(imglist);
		 }

		 return vo;

	 }

    /**
    * 导出excel
    *
    * @param request
    * @param ddfCaGoods
    */
    @RequiresPermissions("ddfcagoods:ddf_ca_goods:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DdfCaGoods ddfCaGoods) {
        return super.exportXls(request, ddfCaGoods, DdfCaGoods.class, "平台商品");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("ddfcagoods:ddf_ca_goods:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DdfCaGoods.class);
    }

}

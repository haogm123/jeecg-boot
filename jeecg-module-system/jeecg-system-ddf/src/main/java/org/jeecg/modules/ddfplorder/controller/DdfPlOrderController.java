package org.jeecg.modules.ddfplorder.controller;

import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.ddfcagoods.service.IDdfCaGoodsService;
import org.jeecg.modules.ddfprgoods.entity.DdfPrGoods;
import org.jeecg.modules.ddfprgoods.service.IDdfPrGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.ddfplorder.entity.DdfPlOrderdetail;
import org.jeecg.modules.ddfplorder.entity.DdfPlOrder;
import org.jeecg.modules.ddfplorder.service.IDdfPlOrderService;
import org.jeecg.modules.ddfplorder.service.IDdfPlOrderdetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 计划订单
 * @Author: jeecg-boot
 * @Date:   2023-07-19
 * @Version: V1.0
 */
@Api(tags="计划订单")
@RestController
@RequestMapping("/ddf/plorder")
@Slf4j
public class DdfPlOrderController extends JeecgController<DdfPlOrder, IDdfPlOrderService> {

	@Autowired
	private IDdfPlOrderService ddfPlOrderService;

	@Autowired
	private IDdfPlOrderdetailService ddfPlOrderdetailService;

	 @Autowired
	 private IDdfPrGoodsService ddfPrGoodsService;



	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
	 * @param ddfPlOrder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "计划订单-分页列表查询")
	@ApiOperation(value="计划订单-分页列表查询", notes="计划订单-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<DdfPlOrder>> queryPageList(DdfPlOrder ddfPlOrder,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DdfPlOrder> queryWrapper = QueryGenerator.initQueryWrapper(ddfPlOrder, req.getParameterMap());
		Page<DdfPlOrder> page = new Page<DdfPlOrder>(pageNo, pageSize);
		IPage<DdfPlOrder> pageList = ddfPlOrderService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
     *   添加
     * @param ddfPlOrder
     * @return
     */
    @AutoLog(value = "计划订单-添加")
    @ApiOperation(value="计划订单-添加", notes="计划订单-添加")
    @RequiresPermissions("ddfplorder:ddf_pl_order:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody DdfPlOrder ddfPlOrder) {
        ddfPlOrderService.save(ddfPlOrder);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     * @param ddfPlOrder
     * @return
     */
    @AutoLog(value = "计划订单-编辑")
    @ApiOperation(value="计划订单-编辑", notes="计划订单-编辑")
    @RequiresPermissions("ddfplorder:ddf_pl_order:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
    public Result<String> edit(@RequestBody DdfPlOrder ddfPlOrder) {
        ddfPlOrderService.updateById(ddfPlOrder);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @AutoLog(value = "计划订单-通过id删除")
    @ApiOperation(value="计划订单-通过id删除", notes="计划订单-通过id删除")
    @RequiresPermissions("ddfplorder:ddf_pl_order:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name="id",required=true) String id) {
        ddfPlOrderService.delMain(id);
        return Result.OK("删除成功!");
    }

	@AutoLog(value = "计划订单-通过id匹配")
    @ApiOperation(value="计划订单-通过id匹配", notes="计划订单-通过id匹配")
    @RequiresPermissions("ddfplorder:ddf_pl_order:match")
    @PutMapping (value = "/matchOrder")
    public Result<String> matchOrder(@RequestParam(name="id",required=true) String id) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		//根据订单查询订单明细
		QueryWrapper<DdfPlOrderdetail> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("pl_order_id",id);
		List<DdfPlOrderdetail> list = ddfPlOrderdetailService.list(queryWrapper);
		//根据订单明细匹配在营商品
		list.forEach(item->{
			QueryWrapper<DdfPrGoods> queryWrapper1 = new QueryWrapper<>();
			queryWrapper1.eq("ca_id",item.getCaGoodsid());
			queryWrapper1.eq("pr_id",sysUser.getId());
			queryWrapper1.eq("status",1);
			DdfPrGoods ddfPrGoods=ddfPrGoodsService.getOne(queryWrapper1);
			if (ddfPrGoods !=null){
				if (ddfPrGoods.getPrInventory()>=item.getNumber()){
					item.setPrId(sysUser.getId());
					item.setPrice(ddfPrGoods.getPrice());
					item.setId(null);
				}
			}else {
				list.remove(item);
			}
		});
		ddfPlOrderdetailService.saveBatch(list);
        return Result.OK("匹配完成!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @AutoLog(value = "计划订单-批量删除")
    @ApiOperation(value="计划订单-批量删除", notes="计划订单-批量删除")
    @RequiresPermissions("ddfplorder:ddf_pl_order:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.ddfPlOrderService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    @RequiresPermissions("ddfplorder:ddf_pl_order:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DdfPlOrder ddfPlOrder) {
        return super.exportXls(request, ddfPlOrder, DdfPlOrder.class, "计划订单");
    }

    /**
     * 导入
     * @return
     */
    @RequiresPermissions("ddfplorder:ddf_pl_order:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DdfPlOrder.class);
    }
	/*---------------------------------主表处理-end-------------------------------------*/
	

    /*--------------------------------子表处理-计划明细-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	//@AutoLog(value = "计划明细-通过主表ID查询")
	@ApiOperation(value="计划明细-通过主表ID查询", notes="计划明细-通过主表ID查询")
	@GetMapping(value = "/listDdfPlOrderdetailByMainId")
    public Result<IPage<DdfPlOrderdetail>> listDdfPlOrderdetailByMainId(DdfPlOrderdetail ddfPlOrderdetail,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<DdfPlOrderdetail> queryWrapper = QueryGenerator.initQueryWrapper(ddfPlOrderdetail, req.getParameterMap());
        Page<DdfPlOrderdetail> page = new Page<DdfPlOrderdetail>(pageNo, pageSize);
        IPage<DdfPlOrderdetail> pageList = ddfPlOrderdetailService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param ddfPlOrderdetail
	 * @return
	 */
	@AutoLog(value = "计划明细-添加")
	@ApiOperation(value="计划明细-添加", notes="计划明细-添加")
	@PostMapping(value = "/addDdfPlOrderdetail")
	public Result<String> addDdfPlOrderdetail(@RequestBody DdfPlOrderdetail ddfPlOrderdetail) {
		ddfPlOrderdetailService.save(ddfPlOrderdetail);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param ddfPlOrderdetail
	 * @return
	 */
	@AutoLog(value = "计划明细-编辑")
	@ApiOperation(value="计划明细-编辑", notes="计划明细-编辑")
	@RequestMapping(value = "/editDdfPlOrderdetail", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> editDdfPlOrderdetail(@RequestBody DdfPlOrderdetail ddfPlOrderdetail) {
		ddfPlOrderdetailService.updateById(ddfPlOrderdetail);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "计划明细-通过id删除")
	@ApiOperation(value="计划明细-通过id删除", notes="计划明细-通过id删除")
	@DeleteMapping(value = "/deleteDdfPlOrderdetail")
	public Result<String> deleteDdfPlOrderdetail(@RequestParam(name="id",required=true) String id) {
		ddfPlOrderdetailService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "计划明细-批量删除")
	@ApiOperation(value="计划明细-批量删除", notes="计划明细-批量删除")
	@DeleteMapping(value = "/deleteBatchDdfPlOrderdetail")
	public Result<String> deleteBatchDdfPlOrderdetail(@RequestParam(name="ids",required=true) String ids) {
	    this.ddfPlOrderdetailService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportDdfPlOrderdetail")
    public ModelAndView exportDdfPlOrderdetail(HttpServletRequest request, DdfPlOrderdetail ddfPlOrderdetail) {
		 // Step.1 组装查询条件
		 QueryWrapper<DdfPlOrderdetail> queryWrapper = QueryGenerator.initQueryWrapper(ddfPlOrderdetail, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<DdfPlOrderdetail> pageList = ddfPlOrderdetailService.list(queryWrapper);
		 List<DdfPlOrderdetail> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 //此处设置的filename无效,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.FILE_NAME, "计划明细");
		 mv.addObject(NormalExcelConstants.CLASS, DdfPlOrderdetail.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("计划明细报表", "导出人:" + sysUser.getRealname(), "计划明细"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importDdfPlOrderdetail/{mainId}")
    public Result<?> importDdfPlOrderdetail(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
       // 获取上传文件对象
			 MultipartFile file = entity.getValue();
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<DdfPlOrderdetail> list = ExcelImportUtil.importExcel(file.getInputStream(), DdfPlOrderdetail.class, params);
				 Iterator iterator = list.iterator();
				 while (iterator.hasNext()){
					 DdfPlOrderdetail ddfPlOrderdetail = (DdfPlOrderdetail) iterator.next();
					 ddfPlOrderdetail.setOrderId(mainId);
					 QueryWrapper<DdfPrGoods> queryWrapper = new QueryWrapper<>();
					 queryWrapper.eq("erp_id",ddfPlOrderdetail.getPrGoodsid());
					 queryWrapper.eq("status",3);
					 DdfPrGoods ddfPrGoods = ddfPrGoodsService.getOne(queryWrapper);
					 if (ddfPrGoods != null){
						 ddfPlOrderdetail.setCaGoodsid(ddfPrGoods.getCaId());
						 ddfPlOrderdetail.setPrId(sysUser.getId());
						 ddfPlOrderdetail.setPrice(ddfPrGoods.getPrice());
					 }else {
						 iterator.remove();
					 }
				 }
				 long start = System.currentTimeMillis();
				 ddfPlOrderdetailService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-计划明细-end----------------------------------------------*/




}

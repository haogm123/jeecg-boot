package org.jeecg.modules.ddfprorder.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.ddfprorder.entity.DdfPrInvoice;
import org.jeecg.modules.ddfprorder.entity.DdfPrOrderdetail;
import org.jeecg.modules.ddfprorder.entity.DdfPrOrder;
import org.jeecg.modules.ddfprorder.vo.DdfPrOrderPage;
import org.jeecg.modules.ddfprorder.service.IDdfPrOrderService;
import org.jeecg.modules.ddfprorder.service.IDdfPrInvoiceService;
import org.jeecg.modules.ddfprorder.service.IDdfPrOrderdetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 订单中心
 * @Author: jeecg-boot
 * @Date:   2023-07-16
 * @Version: V1.0
 */
@Api(tags="订单中心")
@RestController
@RequestMapping("/ddf/prorder")
@Slf4j
public class DdfPrOrderController {
	@Autowired
	private IDdfPrOrderService ddfPrOrderService;
	@Autowired
	private IDdfPrInvoiceService ddfPrInvoiceService;
	@Autowired
	private IDdfPrOrderdetailService ddfPrOrderdetailService;
	
	/**
	 * 分页列表查询
	 *
	 * @param ddfPrOrder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "订单中心-分页列表查询")
	@ApiOperation(value="订单中心-分页列表查询", notes="订单中心-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<DdfPrOrder>> queryPageList(DdfPrOrder ddfPrOrder,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DdfPrOrder> queryWrapper = QueryGenerator.initQueryWrapper(ddfPrOrder, req.getParameterMap());
		Page<DdfPrOrder> page = new Page<DdfPrOrder>(pageNo, pageSize);
		IPage<DdfPrOrder> pageList = ddfPrOrderService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param ddfPrOrderPage
	 * @return
	 */
	@AutoLog(value = "订单中心-添加")
	@ApiOperation(value="订单中心-添加", notes="订单中心-添加")
    @RequiresPermissions("ddfprorder:ddf_pr_order:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody DdfPrOrderPage ddfPrOrderPage) {
		DdfPrOrder ddfPrOrder = new DdfPrOrder();
		BeanUtils.copyProperties(ddfPrOrderPage, ddfPrOrder);
		ddfPrOrderService.saveMain(ddfPrOrder, ddfPrOrderPage.getDdfPrInvoiceList(),ddfPrOrderPage.getDdfPrOrderdetailList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ddfPrOrderPage
	 * @return
	 */
	@AutoLog(value = "订单中心-编辑")
	@ApiOperation(value="订单中心-编辑", notes="订单中心-编辑")
    @RequiresPermissions("ddfprorder:ddf_pr_order:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody DdfPrOrderPage ddfPrOrderPage) {
		DdfPrOrder ddfPrOrder = new DdfPrOrder();
		BeanUtils.copyProperties(ddfPrOrderPage, ddfPrOrder);
		DdfPrOrder ddfPrOrderEntity = ddfPrOrderService.getById(ddfPrOrder.getId());
		if(ddfPrOrderEntity==null) {
			return Result.error("未找到对应数据");
		}
		ddfPrOrderService.updateMain(ddfPrOrder, ddfPrOrderPage.getDdfPrInvoiceList(),ddfPrOrderPage.getDdfPrOrderdetailList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "订单中心-通过id删除")
	@ApiOperation(value="订单中心-通过id删除", notes="订单中心-通过id删除")
    @RequiresPermissions("ddfprorder:ddf_pr_order:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		ddfPrOrderService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "订单中心-批量删除")
	@ApiOperation(value="订单中心-批量删除", notes="订单中心-批量删除")
    @RequiresPermissions("ddfprorder:ddf_pr_order:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ddfPrOrderService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "订单中心-通过id查询")
	@ApiOperation(value="订单中心-通过id查询", notes="订单中心-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<DdfPrOrder> queryById(@RequestParam(name="id",required=true) String id) {
		DdfPrOrder ddfPrOrder = ddfPrOrderService.getById(id);
		if(ddfPrOrder==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ddfPrOrder);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "订单发票通过主表ID查询")
	@ApiOperation(value="订单发票主表ID查询", notes="订单发票-通主表ID查询")
	@GetMapping(value = "/queryDdfPrInvoiceByMainId")
	public Result<List<DdfPrInvoice>> queryDdfPrInvoiceListByMainId(@RequestParam(name="id",required=true) String id) {
		List<DdfPrInvoice> ddfPrInvoiceList = ddfPrInvoiceService.selectByMainId(id);
		return Result.OK(ddfPrInvoiceList);
	}
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "订单明细通过主表ID查询")
	@ApiOperation(value="订单明细主表ID查询", notes="订单明细-通主表ID查询")
	@GetMapping(value = "/queryDdfPrOrderdetailByMainId")
	public Result<List<DdfPrOrderdetail>> queryDdfPrOrderdetailListByMainId(@RequestParam(name="id",required=true) String id) {
		List<DdfPrOrderdetail> ddfPrOrderdetailList = ddfPrOrderdetailService.selectByMainId(id);
		return Result.OK(ddfPrOrderdetailList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ddfPrOrder
    */
    @RequiresPermissions("ddfprorder:ddf_pr_order:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DdfPrOrder ddfPrOrder) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<DdfPrOrder> queryWrapper = QueryGenerator.initQueryWrapper(ddfPrOrder, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //配置选中数据查询条件
       String selections = request.getParameter("selections");
       if(oConvertUtils.isNotEmpty(selections)) {
            List<String> selectionList = Arrays.asList(selections.split(","));
            queryWrapper.in("id",selectionList);
       }
       //Step.2 获取导出数据
       List<DdfPrOrder> ddfPrOrderList = ddfPrOrderService.list(queryWrapper);

      // Step.3 组装pageList
      List<DdfPrOrderPage> pageList = new ArrayList<DdfPrOrderPage>();
      for (DdfPrOrder main : ddfPrOrderList) {
          DdfPrOrderPage vo = new DdfPrOrderPage();
          BeanUtils.copyProperties(main, vo);
          List<DdfPrInvoice> ddfPrInvoiceList = ddfPrInvoiceService.selectByMainId(main.getId());
          vo.setDdfPrInvoiceList(ddfPrInvoiceList);
          List<DdfPrOrderdetail> ddfPrOrderdetailList = ddfPrOrderdetailService.selectByMainId(main.getId());
          vo.setDdfPrOrderdetailList(ddfPrOrderdetailList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "订单中心列表");
      mv.addObject(NormalExcelConstants.CLASS, DdfPrOrderPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("订单中心数据", "导出人:"+sysUser.getRealname(), "订单中心"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    /**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("ddfprorder:ddf_pr_order:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
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
              List<DdfPrOrderPage> list = ExcelImportUtil.importExcel(file.getInputStream(), DdfPrOrderPage.class, params);
              for (DdfPrOrderPage page : list) {
                  DdfPrOrder po = new DdfPrOrder();
                  BeanUtils.copyProperties(page, po);
                  ddfPrOrderService.saveMain(po, page.getDdfPrInvoiceList(),page.getDdfPrOrderdetailList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}

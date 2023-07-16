package org.jeecg.modules.ddfprinfor.controller;

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
import org.jeecg.modules.ddfprinfor.entity.DdfPrInfor;
import org.jeecg.modules.ddfprinfor.service.IDdfPrInforService;

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
 * @Description: 商家资料
 * @Author: jeecg-boot
 * @Date:   2023-07-16
 * @Version: V1.0
 */
@Api(tags="商家资料")
@RestController
@RequestMapping("/ddf/prinfor")
@Slf4j
public class DdfPrInforController extends JeecgController<DdfPrInfor, IDdfPrInforService> {
	@Autowired
	private IDdfPrInforService ddfPrInforService;
	
	/**
	 * 分页列表查询
	 *
	 * @param ddfPrInfor
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "商家资料-分页列表查询")
	@ApiOperation(value="商家资料-分页列表查询", notes="商家资料-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<DdfPrInfor>> queryPageList(DdfPrInfor ddfPrInfor,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DdfPrInfor> queryWrapper = QueryGenerator.initQueryWrapper(ddfPrInfor, req.getParameterMap());
		Page<DdfPrInfor> page = new Page<DdfPrInfor>(pageNo, pageSize);
		IPage<DdfPrInfor> pageList = ddfPrInforService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param ddfPrInfor
	 * @return
	 */
	@AutoLog(value = "商家资料-添加")
	@ApiOperation(value="商家资料-添加", notes="商家资料-添加")
	@RequiresPermissions("ddfprinfor:ddf_pr_infor:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody DdfPrInfor ddfPrInfor) {
		ddfPrInforService.save(ddfPrInfor);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ddfPrInfor
	 * @return
	 */
	@AutoLog(value = "商家资料-编辑")
	@ApiOperation(value="商家资料-编辑", notes="商家资料-编辑")
	@RequiresPermissions("ddfprinfor:ddf_pr_infor:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody DdfPrInfor ddfPrInfor) {
		ddfPrInforService.updateById(ddfPrInfor);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商家资料-通过id删除")
	@ApiOperation(value="商家资料-通过id删除", notes="商家资料-通过id删除")
	@RequiresPermissions("ddfprinfor:ddf_pr_infor:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		ddfPrInforService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "商家资料-批量删除")
	@ApiOperation(value="商家资料-批量删除", notes="商家资料-批量删除")
	@RequiresPermissions("ddfprinfor:ddf_pr_infor:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ddfPrInforService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "商家资料-通过id查询")
	@ApiOperation(value="商家资料-通过id查询", notes="商家资料-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<DdfPrInfor> queryById(@RequestParam(name="id",required=true) String id) {
		DdfPrInfor ddfPrInfor = ddfPrInforService.getById(id);
		if(ddfPrInfor==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ddfPrInfor);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ddfPrInfor
    */
    @RequiresPermissions("ddfprinfor:ddf_pr_infor:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DdfPrInfor ddfPrInfor) {
        return super.exportXls(request, ddfPrInfor, DdfPrInfor.class, "商家资料");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("ddfprinfor:ddf_pr_infor:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DdfPrInfor.class);
    }

}

package org.jeecg.modules.ddfcategories.controller;

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
import org.jeecg.common.system.vo.SelectTreeModel;
import org.jeecg.modules.ddfcategories.entity.DdfCategories;
import org.jeecg.modules.ddfcategories.entity.DdfCategoriesvo;
import org.jeecg.modules.ddfcategories.service.IDdfCategoriesService;

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
 * @Description: 商品类目
 * @Author: jeecg-boot
 * @Date:   2023-07-24
 * @Version: V1.0
 */
@Api(tags="商品类目")
@RestController
@RequestMapping("/ddf/categories")
@Slf4j
public class DdfCategoriesController extends JeecgController<DdfCategories, IDdfCategoriesService>{
	@Autowired
	private IDdfCategoriesService ddfCategoriesService;

	/**
	 * 分页列表查询
	 *
	 * @param ddfCategories
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "商品类目-分页列表查询")
	@ApiOperation(value="商品类目-分页列表查询", notes="商品类目-分页列表查询")
	@GetMapping(value = "/rootList")
	public Result<IPage<DdfCategories>> queryPageList(DdfCategories ddfCategories,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		String hasQuery = req.getParameter("hasQuery");
        if(hasQuery != null && "true".equals(hasQuery)){
            QueryWrapper<DdfCategories> queryWrapper =  QueryGenerator.initQueryWrapper(ddfCategories, req.getParameterMap());
            List<DdfCategories> list = ddfCategoriesService.queryTreeListNoPage(queryWrapper);
            IPage<DdfCategories> pageList = new Page<>(1, 10, list.size());
            pageList.setRecords(list);
            return Result.OK(pageList);
        }else{
            String parentId = ddfCategories.getPid();
            if (oConvertUtils.isEmpty(parentId)) {
                parentId = "0";
            }
            ddfCategories.setPid(null);
            QueryWrapper<DdfCategories> queryWrapper = QueryGenerator.initQueryWrapper(ddfCategories, req.getParameterMap());
            // 使用 eq 防止模糊查询
            queryWrapper.eq("pid", parentId);
            Page<DdfCategories> page = new Page<DdfCategories>(pageNo, pageSize);
            IPage<DdfCategories> pageList = ddfCategoriesService.page(page, queryWrapper);
            return Result.OK(pageList);
        }
	}


	 @ApiOperation(value="商品类目-APP获取", notes="商品类目-APP获取查询")
	 @GetMapping(value = "/appList")
	 public List<DdfCategoriesvo> appList() {
			QueryWrapper<DdfCategories> queryWrapper =  new QueryWrapper<>();
			queryWrapper.eq("status", 1);
			List<DdfCategories> list = ddfCategoriesService.queryTreeListNoPage(queryWrapper);
			List<DdfCategoriesvo> ddfCategoriesvos=list.stream().map(item -> {
				DdfCategoriesvo ddfCategoriesvo=new DdfCategoriesvo();
				BeanUtils.copyProperties(item,ddfCategoriesvo);
				ddfCategoriesvo.setImage(item.getSrc());
				return ddfCategoriesvo;
			}).collect(Collectors.toList());
			return ddfCategoriesvos;
	 }
	 /**
	  * 【vue3专用】加载节点的子数据
	  *
	  * @param pid
	  * @return
	  */
	 @RequestMapping(value = "/loadTreeChildren", method = RequestMethod.GET)
	 public Result<List<SelectTreeModel>> loadTreeChildren(@RequestParam(name = "pid") String pid) {
		 Result<List<SelectTreeModel>> result = new Result<>();
		 try {
			 List<SelectTreeModel> ls = ddfCategoriesService.queryListByPid(pid);
			 result.setResult(ls);
			 result.setSuccess(true);
		 } catch (Exception e) {
			 e.printStackTrace();
			 result.setMessage(e.getMessage());
			 result.setSuccess(false);
		 }
		 return result;
	 }

	 /**
	  * 【vue3专用】加载一级节点/如果是同步 则所有数据
	  *
	  * @param async
	  * @param pcode
	  * @return
	  */
	 @RequestMapping(value = "/loadTreeRoot", method = RequestMethod.GET)
	 public Result<List<SelectTreeModel>> loadTreeRoot(@RequestParam(name = "async") Boolean async, @RequestParam(name = "pcode") String pcode) {
		 Result<List<SelectTreeModel>> result = new Result<>();
		 try {
			 List<SelectTreeModel> ls = ddfCategoriesService.queryListByCode(pcode);
			 if (!async) {
				 loadAllChildren(ls);
			 }
			 result.setResult(ls);
			 result.setSuccess(true);
		 } catch (Exception e) {
			 e.printStackTrace();
			 result.setMessage(e.getMessage());
			 result.setSuccess(false);
		 }
		 return result;
	 }

	 /**
	  * 【vue3专用】递归求子节点 同步加载用到
	  *
	  * @param ls
	  */
	 private void loadAllChildren(List<SelectTreeModel> ls) {
		 for (SelectTreeModel tsm : ls) {
			 List<SelectTreeModel> temp = ddfCategoriesService.queryListByPid(tsm.getKey());
			 if (temp != null && temp.size() > 0) {
				 tsm.setChildren(temp);
				 loadAllChildren(temp);
			 }
		 }
	 }

	 /**
      * 获取子数据
      * @param ddfCategories
      * @param req
      * @return
      */
	//@AutoLog(value = "商品类目-获取子数据")
	@ApiOperation(value="商品类目-获取子数据", notes="商品类目-获取子数据")
	@GetMapping(value = "/childList")
	public Result<IPage<DdfCategories>> queryPageList(DdfCategories ddfCategories,HttpServletRequest req) {
		QueryWrapper<DdfCategories> queryWrapper = QueryGenerator.initQueryWrapper(ddfCategories, req.getParameterMap());
		List<DdfCategories> list = ddfCategoriesService.list(queryWrapper);
		IPage<DdfCategories> pageList = new Page<>(1, 10, list.size());
        pageList.setRecords(list);
		return Result.OK(pageList);
	}

    /**
      * 批量查询子节点
      * @param parentIds 父ID（多个采用半角逗号分割）
      * @return 返回 IPage
      * @param parentIds
      * @return
      */
	//@AutoLog(value = "商品类目-批量获取子数据")
    @ApiOperation(value="商品类目-批量获取子数据", notes="商品类目-批量获取子数据")
    @GetMapping("/getChildListBatch")
    public Result getChildListBatch(@RequestParam("parentIds") String parentIds) {
        try {
            QueryWrapper<DdfCategories> queryWrapper = new QueryWrapper<>();
            List<String> parentIdList = Arrays.asList(parentIds.split(","));
            queryWrapper.in("pid", parentIdList);
            List<DdfCategories> list = ddfCategoriesService.list(queryWrapper);
            IPage<DdfCategories> pageList = new Page<>(1, 10, list.size());
            pageList.setRecords(list);
            return Result.OK(pageList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error("批量查询子节点失败：" + e.getMessage());
        }
    }
	
	/**
	 *   添加
	 *
	 * @param ddfCategories
	 * @return
	 */
	@AutoLog(value = "商品类目-添加")
	@ApiOperation(value="商品类目-添加", notes="商品类目-添加")
    @RequiresPermissions("ddfcategories:ddf_categories:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody DdfCategories ddfCategories) {
		ddfCategoriesService.addDdfCategories(ddfCategories);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ddfCategories
	 * @return
	 */
	@AutoLog(value = "商品类目-编辑")
	@ApiOperation(value="商品类目-编辑", notes="商品类目-编辑")
    @RequiresPermissions("ddfcategories:ddf_categories:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody DdfCategories ddfCategories) {
		ddfCategoriesService.updateDdfCategories(ddfCategories);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品类目-通过id删除")
	@ApiOperation(value="商品类目-通过id删除", notes="商品类目-通过id删除")
    @RequiresPermissions("ddfcategories:ddf_categories:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		ddfCategoriesService.deleteDdfCategories(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "商品类目-批量删除")
	@ApiOperation(value="商品类目-批量删除", notes="商品类目-批量删除")
    @RequiresPermissions("ddfcategories:ddf_categories:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ddfCategoriesService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "商品类目-通过id查询")
	@ApiOperation(value="商品类目-通过id查询", notes="商品类目-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<DdfCategories> queryById(@RequestParam(name="id",required=true) String id) {
		DdfCategories ddfCategories = ddfCategoriesService.getById(id);
		if(ddfCategories==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ddfCategories);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ddfCategories
    */
    @RequiresPermissions("ddfcategories:ddf_categories:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DdfCategories ddfCategories) {
		return super.exportXls(request, ddfCategories, DdfCategories.class, "商品类目");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("ddfcategories:ddf_categories:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
		return super.importExcel(request, response, DdfCategories.class);
    }

}

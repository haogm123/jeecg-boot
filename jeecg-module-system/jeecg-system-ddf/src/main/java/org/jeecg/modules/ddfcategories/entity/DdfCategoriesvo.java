package org.jeecg.modules.ddfcategories.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 商品类目
 * @Author: jeecg-boot
 * @Date:   2023-07-24
 * @Version: V1.0
 */
@Data
@TableName("ddf_categories")
@ApiModel(value="ddf_categories对象", description="商品类目")
public class DdfCategoriesvo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**主键*/
    private String id;
    private String name;
    private String image;
    private String description;
    private Integer menuorder;
}

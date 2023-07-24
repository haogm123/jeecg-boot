package org.jeecg.modules.ddfcagoods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class DdfCaGoodsvo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
    @ApiModelProperty(value = "主键")
    private String id;
	/**平台名称*/
    @ApiModelProperty(value = "平台名称")
    private String name;
	/**厂家*/
    @ApiModelProperty(value = "厂家")
    private String cjGoods;
	/**规格*/
	@Excel(name = "规格", width = 15)
    @ApiModelProperty(value = "规格")
    private String ggGoods;
	/**批准文号*/
    @ApiModelProperty(value = "批准文号")
    private String pzGoods;
	/**条码*/
    @ApiModelProperty(value = "条码")
    private String barCode;
	/**描述*/
    @ApiModelProperty(value = "描述")
    private String content;
	/**状态*/
    @ApiModelProperty(value = "状态")
    private Integer status;
    /**状态*/
    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "次标题")
    private String shortDescription;

    @ApiModelProperty(value = "a描述")
    private String description;

    @ApiModelProperty(value = "图片集")
    private List<DdfCaGoodsimagevo> images;

    @ApiModelProperty(value = "属性集")
    private List<DdfCaGoodsiarvo> attributes;
}

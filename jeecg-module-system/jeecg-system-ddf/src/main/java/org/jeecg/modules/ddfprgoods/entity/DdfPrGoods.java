package org.jeecg.modules.ddfprgoods.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 商家商品
 * @Author: jeecg-boot
 * @Date:   2023-07-15
 * @Version: V1.0
 */
@Data
@TableName("ddf_pr_goods")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ddf_pr_goods对象", description="商家商品")
public class DdfPrGoods implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String prName;
	/**库存*/
	@Excel(name = "库存", width = 15)
    @ApiModelProperty(value = "库存")
    private Integer prInventory;
	/**参考价格*/
	@Excel(name = "参考价格", width = 15)
    @ApiModelProperty(value = "参考价格")
    private BigDecimal price;
	/**成本价*/
	@Excel(name = "成本价", width = 15)
    @ApiModelProperty(value = "成本价")
    private BigDecimal rePrice;
	/**促销价*/
	@Excel(name = "促销价", width = 15)
    @ApiModelProperty(value = "促销价")
    private BigDecimal saPrice;
	/**商家id*/
	@Excel(name = "商家id", width = 15)
    @ApiModelProperty(value = "商家id")
    private String prId;
	/**平台id*/
	@Excel(name = "平台id", width = 15)
    @ApiModelProperty(value = "平台id")
    private String caId;
	/**商家描述*/
	@Excel(name = "商家描述", width = 15)
    @ApiModelProperty(value = "商家描述")
    private String prDesc;
	/**商家自定义*/
	@Excel(name = "商家自定义", width = 15)
    @ApiModelProperty(value = "商家自定义")
    private String prCustom;
	/**平台标签*/
	@Excel(name = "平台标签", width = 15)
    @ApiModelProperty(value = "平台标签")
    private String caLabel;
    /**有效日期*/
    @Excel(name = "有效日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "有效日期")
    private java.util.Date endTime;
    /**同步编码*/
    @Excel(name = "同步编码", width = 15)
    @ApiModelProperty(value = "同步编码")
    private java.lang.String erpId;
    /**扩展字段1*/
    @Excel(name = "扩展字段1", width = 15)
    @ApiModelProperty(value = "扩展字段1")
    private java.lang.String ext1;
    /**扩展字段2*/
    @Excel(name = "扩展字段2", width = 15)
    @ApiModelProperty(value = "扩展字段2")
    private java.lang.String ext2;
    /**状态*/
    @Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.Integer status;
    /**扫描条码*/
    @Excel(name = "扫描条码", width = 15)
    @ApiModelProperty(value = "扫描条码")
    private java.lang.String barcode;
}

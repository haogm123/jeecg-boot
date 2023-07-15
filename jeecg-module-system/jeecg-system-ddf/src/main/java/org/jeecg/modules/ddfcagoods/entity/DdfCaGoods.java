package org.jeecg.modules.ddfcagoods.entity;

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
 * @Description: 平台商品
 * @Author: jeecg-boot
 * @Date:   2023-07-15
 * @Version: V1.0
 */
@Data
@TableName("ddf_ca_goods")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ddf_ca_goods对象", description="平台商品")
public class DdfCaGoods implements Serializable {
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
	/**平台名称*/
	@Excel(name = "平台名称", width = 15)
    @ApiModelProperty(value = "平台名称")
    private String caName;
	/**厂家*/
	@Excel(name = "厂家", width = 15)
    @ApiModelProperty(value = "厂家")
    private String cjGoods;
	/**规格*/
	@Excel(name = "规格", width = 15)
    @ApiModelProperty(value = "规格")
    private String ggGoods;
	/**批准文号*/
	@Excel(name = "批准文号", width = 15)
    @ApiModelProperty(value = "批准文号")
    private String pzGoods;
	/**条码*/
	@Excel(name = "条码", width = 15)
    @ApiModelProperty(value = "条码")
    private String barCode;
	/**图片*/
	@Excel(name = "图片", width = 15)
    @ApiModelProperty(value = "图片")
    private String img;
	/**描述*/
	@Excel(name = "描述", width = 15)
    @ApiModelProperty(value = "描述")
    private String content;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private Integer status;
	/**扩展字段1*/
	@Excel(name = "扩展字段1", width = 15)
    @ApiModelProperty(value = "扩展字段1")
    private String ext1;
	/**扩展字段2*/
	@Excel(name = "扩展字段2", width = 15)
    @ApiModelProperty(value = "扩展字段2")
    private String ext2;
	/**扩展字段3*/
	@Excel(name = "扩展字段3", width = 15)
    @ApiModelProperty(value = "扩展字段3")
    private String ext3;
	/**扩展字段4*/
	@Excel(name = "扩展字段4", width = 15)
    @ApiModelProperty(value = "扩展字段4")
    private String ext4;
}

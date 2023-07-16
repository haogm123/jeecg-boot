package org.jeecg.modules.ddfprorder.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 订单发票
 * @Author: jeecg-boot
 * @Date:   2023-07-16
 * @Version: V1.0
 */
@ApiModel(value="ddf_pr_invoice对象", description="订单发票")
@Data
@TableName("ddf_pr_invoice")
public class DdfPrInvoice implements Serializable {
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
	/**订单id*/
    @ApiModelProperty(value = "订单id")
    private String orderid;
	/**开票金额*/
	@Excel(name = "开票金额", width = 15)
    @ApiModelProperty(value = "开票金额")
    private java.math.BigDecimal amount;
	/**发票号*/
	@Excel(name = "发票号", width = 15)
    @ApiModelProperty(value = "发票号")
    private String invoiceCode;
	/**发票地址*/
	@Excel(name = "发票地址", width = 15)
    @ApiModelProperty(value = "发票地址")
    private String url;
	/**发票图片*/
	@Excel(name = "发票图片", width = 15)
    @ApiModelProperty(value = "发票图片")
    private String img;
	/**发票类型*/
	@Excel(name = "发票类型", width = 15)
    @ApiModelProperty(value = "发票类型")
    private String invoiceType;
	/**商家税号*/
	@Excel(name = "商家税号", width = 15)
    @ApiModelProperty(value = "商家税号")
    private String prCode;
	/**开票说明*/
	@Excel(name = "开票说明", width = 15)
    @ApiModelProperty(value = "开票说明")
    private String context;
	/**开票状态*/
	@Excel(name = "开票状态", width = 15)
    @ApiModelProperty(value = "开票状态")
    private Integer status;
	/**扩展字段1*/
	@Excel(name = "扩展字段1", width = 15)
    @ApiModelProperty(value = "扩展字段1")
    private String ext1;
	/**扩展字段2*/
	@Excel(name = "扩展字段2", width = 15)
    @ApiModelProperty(value = "扩展字段2")
    private String ext2;
}

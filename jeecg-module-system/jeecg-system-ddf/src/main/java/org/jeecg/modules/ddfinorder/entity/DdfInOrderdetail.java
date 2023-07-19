package org.jeecg.modules.ddfinorder.entity;

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
 * @Description: 内部订单明细
 * @Author: jeecg-boot
 * @Date:   2023-07-18
 * @Version: V1.0
 */
@ApiModel(value="ddf_in_orderdetail对象", description="内部订单明细")
@Data
@TableName("ddf_in_orderdetail")
public class DdfInOrderdetail implements Serializable {
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
    private String orderId;
	/**商家id*/
	@Excel(name = "商家id", width = 15)
    @ApiModelProperty(value = "商家id")
    private String prGoodsid;
	/**平台商品id*/
	@Excel(name = "平台商品id", width = 15)
    @ApiModelProperty(value = "平台商品id")
    private String caGoodsid;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private Integer number;
	/**单价*/
	@Excel(name = "单价", width = 15)
    @ApiModelProperty(value = "单价")
    private java.math.BigDecimal unitPrice;
	/**总价*/
	@Excel(name = "总价", width = 15)
    @ApiModelProperty(value = "总价")
    private java.math.BigDecimal price;
	/**商品名称*/
	@Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
    private String goodsname;
	/**扫描编码*/
	@Excel(name = "扫描编码", width = 15)
    @ApiModelProperty(value = "扫描编码")
    private String barCode;
	/**订单状态*/
	@Excel(name = "订单状态", width = 15)
    @ApiModelProperty(value = "订单状态")
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

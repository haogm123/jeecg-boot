package org.jeecg.modules.ddfplorder.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecgframework.poi.excel.annotation.Excel;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 计划订单
 * @Author: jeecg-boot
 * @Date:   2023-07-19
 * @Version: V1.0
 */
@Data
@TableName("ddf_pl_order")
@ApiModel(value="ddf_pl_order对象", description="计划订单")
public class DdfPlOrder implements Serializable {
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
	/**购买用户id*/
    @Excel(name = "购买用户id", width = 15)
    @ApiModelProperty(value = "购买用户id")
    private String payUserId;
	/**商家用户id*/
    @Excel(name = "商家用户id", width = 15)
    @ApiModelProperty(value = "商家用户id")
    private String prUserId;
	/**订单类型*/
    @Excel(name = "订单类型", width = 15)
    @ApiModelProperty(value = "订单类型")
    private String orderType;
	/**订单地址*/
    @Excel(name = "订单地址", width = 15)
    @ApiModelProperty(value = "订单地址")
    private String orderAddr;
	/**订单电话*/
    @Excel(name = "订单电话", width = 15)
    @ApiModelProperty(value = "订单电话")
    private String orderPhone;
	/**订单数量*/
    @Excel(name = "订单数量", width = 15)
    @ApiModelProperty(value = "订单数量")
    private Integer orderNumber;
	/**订单金额*/
    @Excel(name = "订单金额", width = 15)
    @ApiModelProperty(value = "订单金额")
    private java.math.BigDecimal orderPrice;
	/**订单状态*/
    @Excel(name = "订单状态", width = 15)
    @ApiModelProperty(value = "订单状态")
    private Integer plStatus;
	/**原订单id*/
    @Excel(name = "原订单id", width = 15)
    @ApiModelProperty(value = "原订单id")
    private String plPid;
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

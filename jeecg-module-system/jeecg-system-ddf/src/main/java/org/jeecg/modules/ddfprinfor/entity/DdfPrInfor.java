package org.jeecg.modules.ddfprinfor.entity;

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
 * @Description: 商家资料
 * @Author: jeecg-boot
 * @Date:   2023-07-16
 * @Version: V1.0
 */
@Data
@TableName("ddf_pr_infor")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ddf_pr_infor对象", description="商家资料")
public class DdfPrInfor implements Serializable {
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
	/**用户ID*/
	@Excel(name = "用户ID", width = 15)
    @ApiModelProperty(value = "用户ID")
    private String userId;
	/**营业执照名称*/
	@Excel(name = "营业执照名称", width = 15)
    @ApiModelProperty(value = "营业执照名称")
    private String prName;
	/**经营地址*/
	@Excel(name = "经营地址", width = 15)
    @ApiModelProperty(value = "经营地址")
    private String address;
	/**到期日期*/
	@Excel(name = "到期日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "到期日期")
    private Date endTime;
	/**注册日期*/
	@Excel(name = "注册日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "注册日期")
    private Date startTime;
	/**联系人*/
	@Excel(name = "联系人", width = 15)
    @ApiModelProperty(value = "联系人")
    private String contact;
	/**联系电话*/
	@Excel(name = "联系电话", width = 15)
    @ApiModelProperty(value = "联系电话")
    private String phone;
	/**座机*/
	@Excel(name = "座机", width = 15)
    @ApiModelProperty(value = "座机")
    private String tel;
	/**图片1*/
	@Excel(name = "图片1", width = 15)
    @ApiModelProperty(value = "图片1")
    private String img1;
	/**图片2*/
	@Excel(name = "图片2", width = 15)
    @ApiModelProperty(value = "图片2")
    private String img2;
	/**图片3*/
	@Excel(name = "图片3", width = 15)
    @ApiModelProperty(value = "图片3")
    private String img3;
	/**图片4*/
	@Excel(name = "图片4", width = 15)
    @ApiModelProperty(value = "图片4")
    private String img4;
	/**审核状态*/
	@Excel(name = "审核状态", width = 15)
    @ApiModelProperty(value = "审核状态")
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

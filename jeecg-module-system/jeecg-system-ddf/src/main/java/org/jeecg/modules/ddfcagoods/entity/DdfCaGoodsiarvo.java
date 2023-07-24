package org.jeecg.modules.ddfcagoods.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DdfCaGoodsiarvo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    private Integer position;

    private Boolean visible;

    private Boolean variation;

    private String[] options;
}

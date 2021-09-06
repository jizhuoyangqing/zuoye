package com.huiminpay.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "Merchant",description = "商铺实体类对象")
@TableName("merchant")  //实体类要映射那张表
public class Merchant implements Serializable {

    @ApiModelProperty("主键")
    @TableId("id")         //实体类要映射表中的主键，自动映射对了，写不写都行，不影响映射
    private Long id;      //@TableId(value = "id",type = IdType.UUID)对主键是string类型时，还可以设置uuid自动生成

    @ApiModelProperty("商户名称")
    @TableField("merchant_name")  //实体类要映射表中的字段
    private String merchantName;

    @ApiModelProperty("商户编号")
    @TableField("merchant_no")  //实体类要映射表中的字段
    private String merchantNo;

    @ApiModelProperty("商户地址")
    private String merchantAddress;

    @ApiModelProperty("商户类型")
    private String merchantType;

    @ApiModelProperty("营业执照（企业证明）")
    private String businessLicensesImg;

    @ApiModelProperty("法人身份证正面照片")
    private String idCardAfterImg;

    @ApiModelProperty("法人身份证反面照片")
    private String idCardFrontImg;

    @ApiModelProperty("联系人姓名")
    private String username;

    @ApiModelProperty("联系人手机号(关联统一账号)")
    private String mobile;

    @ApiModelProperty("联系人地址")
    private String contactsAddress;

    @ApiModelProperty("审核状态 0-未申请,1-已申请待审核,2-审核通过,3-审核拒绝")
    private String auditStatus;

    @ApiModelProperty("租户ID,关联统一用户")
    private Long tenantId;
}

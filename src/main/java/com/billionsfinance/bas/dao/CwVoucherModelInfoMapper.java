package com.billionsfinance.bas.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.billionsfinance.bas.entity.voucher.model.CwVoucherModelInfo;
import com.billionsfinance.bas.entity.voucher.model.CwVoucherModelInfoExample;
import com.billionsfinance.bas.entity.voucher.vo.VoucherResultVo;

public interface CwVoucherModelInfoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL_INFO
	 * @mbggenerated
	 */
	int countByExample(CwVoucherModelInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL_INFO
	 * @mbggenerated
	 */
	int deleteByExample(CwVoucherModelInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL_INFO
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(BigDecimal id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL_INFO
	 * @mbggenerated
	 */
	int insert(CwVoucherModelInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL_INFO
	 * @mbggenerated
	 */
	int insertSelective(CwVoucherModelInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL_INFO
	 * @mbggenerated
	 */
	List<CwVoucherModelInfo> selectByExample(CwVoucherModelInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL_INFO
	 * @mbggenerated
	 */
	CwVoucherModelInfo selectByPrimaryKey(BigDecimal id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL_INFO
	 * @mbggenerated
	 */
	int updateByExampleSelective(@Param("record") CwVoucherModelInfo record,
			@Param("example") CwVoucherModelInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL_INFO
	 * @mbggenerated
	 */
	int updateByExample(@Param("record") CwVoucherModelInfo record,
			@Param("example") CwVoucherModelInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL_INFO
	 * @mbggenerated
	 */
	int updateByPrimaryKeySelective(CwVoucherModelInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL_INFO
	 * @mbggenerated
	 */
	int updateByPrimaryKey(CwVoucherModelInfo record);

	/**统计实还**/
	VoucherResultVo FactSheet(CwVoucherModelInfo record);
	
	int insertRecord(CwVoucherModelInfo record);
}
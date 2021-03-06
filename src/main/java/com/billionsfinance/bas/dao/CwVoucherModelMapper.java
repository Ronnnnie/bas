package com.billionsfinance.bas.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.billionsfinance.bas.entity.voucher.model.CwVoucherModel;
import com.billionsfinance.bas.entity.voucher.model.CwVoucherModelExample;

public interface CwVoucherModelMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	int countByExample(CwVoucherModelExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	int deleteByExample(CwVoucherModelExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(BigDecimal id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	int insert(CwVoucherModel record);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	int insertSelective(CwVoucherModel record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	List<CwVoucherModel> selectByExample(CwVoucherModelExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	CwVoucherModel selectByPrimaryKey(BigDecimal id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	int updateByExampleSelective(@Param("record") CwVoucherModel record,
			@Param("example") CwVoucherModelExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	int updateByExample(@Param("record") CwVoucherModel record,
			@Param("example") CwVoucherModelExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	int updateByPrimaryKeySelective(CwVoucherModel record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	int updateByPrimaryKey(CwVoucherModel record);
	
	int insertRecord(CwVoucherModel record);
}
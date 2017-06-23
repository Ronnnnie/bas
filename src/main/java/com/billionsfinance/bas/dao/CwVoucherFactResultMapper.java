package com.billionsfinance.bas.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.billionsfinance.bas.entity.voucher.model.CwVoucherFactResult;
import com.billionsfinance.bas.entity.voucher.model.CwVoucherFactResultExample;
import com.billionsfinance.bas.entity.voucher.vo.SelectConditionVo;
import com.billionsfinance.bas.entity.voucher.vo.VoucherResultVo;

public interface CwVoucherFactResultMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_FACT_RESULT
     *
     * @mbggenerated
     */
    int countByExample(CwVoucherFactResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_FACT_RESULT
     *
     * @mbggenerated
     */
    int deleteByExample(CwVoucherFactResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_FACT_RESULT
     *
     * @mbggenerated
     */
    int insert(CwVoucherFactResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_FACT_RESULT
     *
     * @mbggenerated
     */
    int insertSelective(CwVoucherFactResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_FACT_RESULT
     *
     * @mbggenerated
     */
    List<CwVoucherFactResult> selectByExample(CwVoucherFactResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_FACT_RESULT
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") CwVoucherFactResult record, @Param("example") CwVoucherFactResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_FACT_RESULT
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") CwVoucherFactResult record, @Param("example") CwVoucherFactResultExample example);
    /**get all city**/
    List<VoucherResultVo> getCityName();
    /**count fact data**/
	VoucherResultVo getFactSheet(SelectConditionVo record);
	/**count fact data**/
	List<VoucherResultVo> getAccountDate();
}
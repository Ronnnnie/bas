package com.billionsfinance.bas.server.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.billionsfinance.bas.dao.CwVoucherFactResultMapper;
import com.billionsfinance.bas.dao.CwVoucherModelInfoMapper;
import com.billionsfinance.bas.dao.CwVoucherModelMapper;
import com.billionsfinance.bas.entity.voucher.model.CwVoucherModel;
import com.billionsfinance.bas.entity.voucher.model.CwVoucherModelExample;
import com.billionsfinance.bas.entity.voucher.model.CwVoucherModelInfo;
import com.billionsfinance.bas.entity.voucher.model.CwVoucherModelInfoExample;
import com.billionsfinance.bas.entity.voucher.vo.FactAccountVo;
import com.billionsfinance.bas.entity.voucher.vo.FactSumVo;
import com.billionsfinance.bas.entity.voucher.vo.SelectConditionVo;
import com.billionsfinance.bas.entity.voucher.vo.VoucherInfoVo;
import com.billionsfinance.bas.entity.voucher.vo.VoucherResultVo;
import com.billionsfinance.bas.server.ICwVoucherModelInfoServer;
import com.billionsfinance.bas.util.SingletonScrollBar;
import com.billionsfinance.bas.util.StringUtil;
import com.billionsfinance.bas.util.enums.AccountTypeEnum;

/**
 * @ClassName: CwVoucherModelInfoServerImpl.java
 * @Description: 模板实现类
 * @author lin.tang
 * @date 2017年5月31日 上午9:58:38
 * Copyright: Copyright (c) 2017-2050 Company:BQJR
 */
@Service("cwVoucherModelInfoServer")
public class CwVoucherModelInfoServerImpl implements ICwVoucherModelInfoServer{
	private static final Log LOGGER = LogFactory.getLog(CwVoucherModelInfoServerImpl.class);
	@Autowired
	private CwVoucherModelInfoMapper cwVoucherModelInfoMapper;
	@Autowired
	private CwVoucherFactResultMapper cwVoucherFactResultMapper;
	@Autowired
	private CwVoucherModelMapper cwVoucherModelMapper;
	
	@Override
	public List<VoucherInfoVo> calculationVoucher(String modelNo,SingletonScrollBar singletonScrollBar) throws Exception{
		DecimalFormat df= new DecimalFormat("######0.00");
		CwVoucherModel cwVoucherModel=null;
		CwVoucherModelExample exampleModel=new CwVoucherModelExample();
		exampleModel.createCriteria().andModelNoEqualTo(modelNo);
		List<CwVoucherModel> listModel=cwVoucherModelMapper.selectByExample(exampleModel);
		if (listModel!=null&&listModel.size()>0) {
			cwVoucherModel=listModel.get(0);
		}
		singletonScrollBar.setNode(cwVoucherModel.getModelName());
		//首先找出对应的模板组信息
		CwVoucherModelInfoExample example=new CwVoucherModelInfoExample();
		example.createCriteria().andModelNoEqualTo(modelNo);
		example.setOrderByClause("id asc");
		//获取记账日期
		List<VoucherResultVo> dueDates=cwVoucherFactResultMapper.getAccountDate();
		String accountDate="";
		if (dueDates.size()>0) {
			accountDate=calculateTime(dueDates.get(0).getDueDate());
		}
		List<CwVoucherModelInfo> list=cwVoucherModelInfoMapper.selectByExample(example);
		List<VoucherInfoVo> resutList=new ArrayList<VoucherInfoVo>();
		int count=0;//分录号
		if (list!=null) {
			singletonScrollBar.setTemp(list.size());
			for (CwVoucherModelInfo cwVoucherModelInfo : list) {
				count++;
				singletonScrollBar.setCount(count);
				singletonScrollBar.setPercentage(Double.valueOf(df.format(((double)count/(double)singletonScrollBar.getTemp())*100)));
				
				VoucherInfoVo vo=new VoucherInfoVo();
				vo.setBusinessAccounting1(cwVoucherModelInfo.getAccountingAux1());
				vo.setBusinessAccounting2(cwVoucherModelInfo.getAccountingAux2());
				vo.setBusinessAccounting3(cwVoucherModelInfo.getAccountingAux3());
				vo.setAccountDate(accountDate);
				vo.setBusinessDate(accountDate);
				vo.setAuxiliaryAccountingDate(accountDate);
				vo.setExpireDate(accountDate);
				vo.setEntryNo(count+"");
				vo.setAbstractInfo(cwVoucherModelInfo.getAccountingAbs());
				vo.setAuxiliaryAbstractInfo(cwVoucherModelInfo.getAccountingAbs());
				vo.setCompany(cwVoucherModelInfo.getAccountNum());
				vo.setSubjectInfo(cwVoucherModelInfo.getSubjectNum());
				//然后根据信息进行分组统计
				FactAccountVo conditions=getSqlConditionResult(cwVoucherModelInfo);
				singletonScrollBar.setCurrentNode(JSON.toJSONString(conditions));
				if (conditions.getCitys().size()>0) {
					for (String city : conditions.getCitys()) {
						VoucherInfoVo voc=new VoucherInfoVo();
						voc.setBusinessAccounting1(cwVoucherModelInfo.getAccountingAux1());
						voc.setBusinessAccounting2(cwVoucherModelInfo.getAccountingAux2());
						voc.setBusinessAccounting3(cwVoucherModelInfo.getAccountingAux3());
						voc.setAccountDate(accountDate);
						voc.setBusinessDate(accountDate);
						voc.setAuxiliaryAccountingDate(accountDate);
						voc.setExpireDate(accountDate);
						voc.setEntryNo(count+"");
						voc.setAbstractInfo(cwVoucherModelInfo.getAccountingAbs());
						voc.setAuxiliaryAbstractInfo(cwVoucherModelInfo.getAccountingAbs());
						voc.setCompany(cwVoucherModelInfo.getAccountNum());
						voc.setSubjectInfo(cwVoucherModelInfo.getSubjectNum());
						
						SelectConditionVo record=new SelectConditionVo();
						record.setCity(city);
						record.setSum(getSqlSumResult(cwVoucherModelInfo));
						record.setFactAccountVo(conditions);
						VoucherResultVo result=cwVoucherFactResultMapper.getFactSheet(record);
						LOGGER.info("count="+count+"城市结果："+JSON.toJSONString(result));
						voc.setTotalFee(result==null?"":result.getTotalfee()==null?"":result.getTotalfee()+"");
						if (!StringUtil.checkIsNull(cwVoucherModelInfo.getAccountDir())) {
							if (cwVoucherModelInfo.getAccountDir().equals("借方")) {
								voc.setDebtorMoney(result==null?"":result.getTotalfee()==null?"":result.getTotalfee()+"");
								voc.setDirection("1");
							}else {
								voc.setDirection("0");
								voc.setLenderMoney(result==null?"":result.getTotalfee()==null?"":result.getTotalfee()+"");
							}
						}
						resutList.add(voc);
					}
				}else {
					SelectConditionVo record=new SelectConditionVo();
					record.setSum(getSqlSumResult(cwVoucherModelInfo));
					record.setFactAccountVo(conditions);
					VoucherResultVo result=cwVoucherFactResultMapper.getFactSheet(record);
					
					vo.setTotalFee(result==null?"":result.getTotalfee()==null?"":result.getTotalfee()+"");
					if (!StringUtil.checkIsNull(cwVoucherModelInfo.getAccountDir())) {
						if (cwVoucherModelInfo.getAccountDir().equals("借方")) {
							vo.setDebtorMoney(result==null?"":result.getTotalfee()==null?"":result.getTotalfee()+"");
							vo.setDirection("1");
						}else {
							vo.setDirection("0");
							vo.setLenderMoney(result==null?"":result.getTotalfee()==null?"":result.getTotalfee()+"");
						}
					}
					resutList.add(vo);
				}
			}
		}
		
		//生成凭证所需bean类 返回
		return resutList;
	}
	private FactSumVo getSqlSumResult(CwVoucherModelInfo cwVoucherModelInfo){
//		StringBuffer sum=new StringBuffer("");
		FactSumVo factSumVo=new FactSumVo();
		if (StringUtil.checkIsNotNull(cwVoucherModelInfo.getCalculatedAmountField())) {//计算金额字段不为空
			String[] types=cwVoucherModelInfo.getCalculatedAmountField().split("\\+");
			for (String string : types) {
//				sum.append(AccountTypeEnum.getlikeValue(string)).append("+");
				if (StringUtil.checkIsNotNull(string)) {
					String result=AccountTypeEnum.getlikeValue(string);
					if (StringUtil.checkIsNotNull(result)) {
						switch (result) {
						case "PAYPRINCIPALAMT":
							factSumVo.setPAYPRINCIPALAMT(result);
							break;
						case "PAYINTEAMT":
							factSumVo.setPAYINTEAMT(result);
							break;
						case "A2":
							factSumVo.setA2(result);
							break;
						case "A7":
							factSumVo.setA7(result);
							break;
						case "A12":
							factSumVo.setA12(result);
							break;
						case "A18":
							factSumVo.setA18(result);
							break;
						case "A9":
							factSumVo.setA9(result);
							break;
						case "A10":
							factSumVo.setA10(result);
							break;
						case "A17":
							factSumVo.setA17(result);
							break;
						case "A19":
							factSumVo.setA19(result);
							break;
						default:
							factSumVo.setPAYPRINCIPALAMT("PAYPRINCIPALAMT");
							break;
						}
					}
				}
			}
//			sum.delete(sum.length()-1, sum.length());//去掉最后一个+号
		}
		return factSumVo;
	}
	private FactAccountVo getSqlConditionResult(CwVoucherModelInfo cwVoucherModelInfo){
		StringBuffer condition=new StringBuffer(" where 1=1 ");
		FactAccountVo factAccountVo=new FactAccountVo();
		if (StringUtil.checkIsNotNull(cwVoucherModelInfo.getBusinessmodel())) {//业务模式不为空
			condition.append(" and ").append(" BUSINESSMODEL='").append(cwVoucherModelInfo.getBusinessmodel()).append("'");
			factAccountVo.setBusinessmodel(cwVoucherModelInfo.getBusinessmodel());
		}
		if (StringUtil.checkIsNotNull(cwVoucherModelInfo.getCanceltype())) {//是否取消分期不为空
			if (cwVoucherModelInfo.getCanceltype().equals("空")) {
				condition.append(" and ").append(" CANCELTYPE is null");
			}else {
				condition.append(" and ").append(" CANCELTYPE=").append(cwVoucherModelInfo.getCanceltype());
			}
			factAccountVo.setCanceltype(cwVoucherModelInfo.getCanceltype());
		}
		if (StringUtil.checkIsNotNull(cwVoucherModelInfo.getAssetbelong())) {//资产所属方不为空
			condition.append(" and ").append(" ASSETBELONG='").append(cwVoucherModelInfo.getAssetbelong()).append("'");
			factAccountVo.setAssetbelong(cwVoucherModelInfo.getAssetbelong());
		}
		if (StringUtil.checkIsNotNull(cwVoucherModelInfo.getGuaranteeparty())) {//保证方不为空
			condition.append(" and ").append(" GUARANTEEPARTY='").append(cwVoucherModelInfo.getGuaranteeparty()).append("'");
			factAccountVo.setGuaranteeparty(cwVoucherModelInfo.getGuaranteeparty());
		}
		if (StringUtil.checkIsNotNull(cwVoucherModelInfo.getSponsor())) {//被担保方不为空
			condition.append(" and ").append(" Sponsor='").append(cwVoucherModelInfo.getSponsor()).append("'");
			factAccountVo.setSponsor(cwVoucherModelInfo.getSponsor());
		}
		if (StringUtil.checkIsNotNull(cwVoucherModelInfo.getSponsortype())) {//担保方式不为空
			if (cwVoucherModelInfo.getSponsortype().equals("空")) {
				condition.append(" and ").append(" Sponsortype is null");
			}else {
				condition.append(" and ").append(" Sponsortype='").append(cwVoucherModelInfo.getSponsortype()).append("'");
			}
			factAccountVo.setSponsortype(cwVoucherModelInfo.getSponsortype());
		}
		if (StringUtil.checkIsNotNull(cwVoucherModelInfo.getIsInstalmentsEnd())) {//是否当期到期不为空
			condition.append(" and ").append(" IS_INSTALMENTS_END='").append(cwVoucherModelInfo.getIsInstalmentsEnd()).append("'");
			factAccountVo.setIsinstalmentsend(cwVoucherModelInfo.getIsInstalmentsEnd());
		}
		if (StringUtil.checkIsNotNull(cwVoucherModelInfo.getBadDebtDate())) {//坏账核销日期不为空
			if (cwVoucherModelInfo.getBadDebtDate().equals("空")) {
				condition.append(" and ").append(" BXHXDATE is null");
			}else {
				condition.append(" and ").append(" BXHXDATE is not null");
			}
			factAccountVo.setBaddebtdate(cwVoucherModelInfo.getBadDebtDate());
		}
		
		List<VoucherResultVo> citys=new ArrayList<>();
		List<String> city=new ArrayList<>();
		if (StringUtil.checkIsNotNull(cwVoucherModelInfo.getCity())) {//市不为空
			citys=cwVoucherFactResultMapper.getCityName();
			for (int i = 0; i < citys.size(); i++) {
				city.add(citys.get(i).getCity());
			}
		}
		factAccountVo.setCitys(city);
		return factAccountVo;
	}
	private String calculateTime(String dueDate){
		if (StringUtil.checkIsNotNull(dueDate)) {
			try {
				SimpleDateFormat format=new SimpleDateFormat("yyyy/MM");
				Date lastDate=format.parse(dueDate);
				Calendar c=Calendar.getInstance();
				c.setTime(lastDate);
				int MaxDay=c.getActualMaximum(Calendar.DAY_OF_MONTH);
				c.set( c.get(Calendar.YEAR), c.get(Calendar.MONTH), MaxDay, 23, 59, 59);
				SimpleDateFormat last = new SimpleDateFormat("yyyy/MM/dd");
				return last.format(c.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}else {
			return null;
		}
	}
	public static void main(String[] args) {
		
		System.out.println(Math.round(100.99));
		DecimalFormat df= new DecimalFormat("######0.00");  
		System.out.println(df.format(((double)7/(double)11)*100));
		System.out.println((double)(10/100)+"%");
	}
}



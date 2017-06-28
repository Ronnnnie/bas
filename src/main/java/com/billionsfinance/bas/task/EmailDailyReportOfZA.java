package com.billionsfinance.bas.task;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

import javax.annotation.Resource;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.util.StringUtils;

import com.billionsfinance.bas.server.ITrustContributionServer;
import com.billionsfinance.bas.server.impl.TrustContributionServer;

public class EmailDailyReportOfZA {

	String partner;
	
	//private static final ITrustContributionServer trustContributionServer = new TrustContributionServer();

	// @Resource
	// private CarParkService carParkService;

	public EmailDailyReportOfZA() {
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	// private static final ITrustContributionServer trustContributionServer =
	// new TrustContributionServer();

	public void sendHtmlEmail() throws EmailException, MalformedURLException,
			UnsupportedEncodingException, ParseException {
		ITrustContributionServer trustContributionServer = new TrustContributionServer();
		HtmlEmail email = new HtmlEmail();
		email.setHostName("smtp.exmail.qq.com");

		// TODO 收件人：资产管理中心全体员工；ifdept@bqjr.cn;
		String itemCode = "v00003";
		List<String> emailList = new ArrayList<>();
		emailList = trustContributionServer.getEmailList(itemCode);
		if (emailList != null && emailList.size() != 0) {
			String[] toBeStored = emailList
					.toArray(new String[emailList.size()]);
			email.addTo(toBeStored);
		}

		// email.addTo(new String[]{"zhiming.wu@bqjr.cn","785685931@qq.com"});

		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("zhiming.wu@bqjr.cn",
				"Wzm910712"));
		email.setSSLOnConnect(true);
		email.setFrom("zhiming.wu@bqjr.cn");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date yestoday = getYestoday();
		email.setSubject("投融资部-每日中航百度报酬日报--" + format.format(yestoday));
		/*	String caption = new String(("中航百度报酬日报--").getBytes("GBK"),
				"ISO-8859-1") + format.format(yestoday);
		String td1 = new String(("昨日信托报酬").getBytes("GBK"), "ISO-8859-1");
		String td2 = new String(("累计信托报酬").getBytes("GBK"), "ISO-8859-1");
		String td3 = new String(("昨日保管费").getBytes("GBK"), "ISO-8859-1");
		String td4 = new String(("累计保管费").getBytes("GBK"), "ISO-8859-1");
		String td5 = new String(("昨日优先收益").getBytes("GBK"), "ISO-8859-1");
		String td6 = new String(("累计优先收益").getBytes("GBK"), "ISO-8859-1");
		String td7 = new String(("上月优先级本金").getBytes("GBK"), "ISO-8859-1");
		String td8 = new String(("上月劣后级本金").getBytes("GBK"), "ISO-8859-1");

		String td31 = new String(("上月差额补足金额").getBytes("GBK"), "ISO-8859-1");
		String td32 = new String(("上月保证金1").getBytes("GBK"), "ISO-8859-1");
		String td33 = new String(("上月客户退货款").getBytes("GBK"), "ISO-8859-1");
		String td34 = new String(("上月客户主动还款").getBytes("GBK"), "ISO-8859-1");
		String td35 = new String(("M3+率").getBytes("GBK"), "ISO-8859-1");
		String td36 = new String(("B3").getBytes("GBK"), "ISO-8859-1");
		String td37 = new String(("M2+率").getBytes("GBK"), "ISO-8859-1");
		String td38 = new String(("B2").getBytes("GBK"), "ISO-8859-1");

		// TODO 查询出各个数据
		double custodianFee = 0;
		double priorityEarnings = 0;
		double trustremuneration = 0;
		Map<String, Object> map = new HashMap<>();
		map = trustContributionServer.getDetailFromBenefitCheckDaily(partner);
		if (map != null) {
			custodianFee = Double.parseDouble(map.get("CUSTODIANFEE")
					.toString());
			priorityEarnings = Double.parseDouble(map.get("PRIORITYEARNINGS")
					.toString());
			trustremuneration = Double.parseDouble(map.get("TRUSTREMUNERATION")
					.toString());
		}

		// 累计信托报酬
		Map<String, Object> trustRemunerationMap = new HashMap<>();
		double sumOftrustRemuneration = 0;
		trustRemunerationMap = trustContributionServer
				.getSumOfBenefitCheckDaily(partner);
		if (trustRemunerationMap != null) {
			sumOftrustRemuneration = Double.parseDouble(trustRemunerationMap
					.get("TRUSTREMUNERATION").toString());
		}

		// 累计优先收益
		Map<String, Object> priorityEaringsMap = new HashMap<>();
		double sumOfpriorityEarings = 0;
		priorityEaringsMap = trustContributionServer
				.getSumOfPriorityEarings(partner);
		if (priorityEaringsMap != null) {
			sumOfpriorityEarings = Double.parseDouble(priorityEaringsMap.get(
					"PRIORITYEARINGS").toString());
		}

		// 累计保管费
		Map<String, Object> custodianFeeMap = new HashMap<>();
		double sumOfcustodianFee = 0;
		custodianFeeMap = trustContributionServer.getSumOfcustodianFee(partner);
		if (custodianFeeMap != null) {
			sumOfcustodianFee = Double.parseDouble(custodianFeeMap.get(
					"CUSTODIANFEE").toString());
		}

		Map<String, Object> benefitCheckMonthSummaryMap = new HashMap<>();

		double priorityPrincipal = 0;
		// 上月劣后级本金
		double inferiorPrincipal = 0;
		// 上月差额补足金额
		double differenceUpBalance = 0;
		// 上月保证金
		double serviceFee = 0;
		// 上月客户退货款
		double merchantsRetmoneyActual = 0;
		// 上月客户主动还款
		double customeDrivingRepayment = 0;

		benefitCheckMonthSummaryMap = trustContributionServer
				.getdetailFromMonthSummary(partner);
		if (benefitCheckMonthSummaryMap != null) {

			priorityPrincipal = Double.parseDouble(benefitCheckMonthSummaryMap
					.get("PRIORITYPRINCIPAL").toString());

			inferiorPrincipal = Double.parseDouble(benefitCheckMonthSummaryMap
					.get("INFERIORPRINCIPAL").toString());

			differenceUpBalance = Double
					.parseDouble(benefitCheckMonthSummaryMap.get(
							"DIFFERENCEUPBALANCE").toString());

			serviceFee = Double.parseDouble(benefitCheckMonthSummaryMap.get(
					"SERVICEFEE").toString());

			merchantsRetmoneyActual = Double
					.parseDouble(benefitCheckMonthSummaryMap.get(
							"MERCHANTSRETMONEYACTUAL").toString());

			customeDrivingRepayment = Double
					.parseDouble(benefitCheckMonthSummaryMap.get(
							"CUSTOMEDRIVINGREPAYMENT").toString());
		}

		// B3=B2
		String partnerCode = "";
		if (partner.equalsIgnoreCase("众安")) {
			partnerCode = "ZAYX";
		}// 改为map为添加条件，做一下为null 时候的判断
		//partnerCode = "HBYH";// TODO delete when upload,and sql update
		Map<String, Object> resultB3Map = new HashMap<>();

		Map<String, Object> resultB2Map = new HashMap<>();

		double B3Part1 = 0;
		double B3Part2 = 0;
		resultB3Map = trustContributionServer
				.getB3Part1FromPayDaily(partnerCode);

		if (resultB3Map != null) {
			B3Part1 = Double.parseDouble((resultB3Map.get("PAYPRINCIPALAMT")
					.toString()));
		}
		resultB2Map = trustContributionServer
				.getB3Part2FromPayDaily(partnerCode);
		if (resultB2Map != null) {
			B3Part2 = Double.parseDouble((resultB2Map.get("PAYPRINCIPALAMT")
					.toString()));
		}
		
		 * double B3Part1=
		 * trustContributionServer.getB3Part1FromPayDaily(partnerCode);
		 * 
		 * double B3Part2 =
		 * trustContributionServer.getB3Part2FromPayDaily(partnerCode);
		 

		BigDecimal d1 = new BigDecimal(String.valueOf(B3Part1));

		BigDecimal d2 = new BigDecimal(String.valueOf(B3Part2));

		double B3 = d1.add(d2).doubleValue();

		// A3
		int days = 90;
		Map<String, Object> A3Map = new HashMap<>();
		A3Map.put("partnerCode", partnerCode);
		A3Map.put("days", days);

		double fPayCapital = 0;
		double fUndueCapital = 0;
		// 获取大于 90 天的应付本金
		Map<String, Object> over90DaysCapitalMap1 = new HashMap<>();
		over90DaysCapitalMap1 = trustContributionServer
				.getOverDueDaysCapital1(A3Map);
		if (over90DaysCapitalMap1 != null) {
			fPayCapital = Double.parseDouble(over90DaysCapitalMap1.get(
					"PAYPRINCIPALAMT").toString());
		}

		Map<String, Object> over90DaysCapitalMap2 = new HashMap<>();
		over90DaysCapitalMap2 = trustContributionServer
				.getOverDueDaysCapital2(A3Map);
		if (over90DaysCapitalMap2 != null) {
			fUndueCapital = Double.parseDouble(over90DaysCapitalMap2.get(
					"PAYPRINCIPALAMT").toString());
		}

		BigDecimal d1Capital = new BigDecimal(String.valueOf(fPayCapital));
		BigDecimal d2Capital = new BigDecimal(String.valueOf(fUndueCapital));
		double allCapital = d1Capital.add(d2Capital).doubleValue();

		double A3 = 0;
		// 获取实付本金
		Map<String, Object> resultMap = new HashMap<>();
		resultMap = trustContributionServer.getA3Capital(A3Map);
		if (resultMap != null) {
			A3 = allCapital
					- Double.parseDouble((resultMap.get("PAYPRINCIPALAMT")
							.toString()));
		}

		Map<String, Object> A2Map = new HashMap<>();
		double A2 = 0;
		A2Map.put("partnerCode", partnerCode);
		int days2 = 60;
		A2Map.put("days", days2);

		// 获取逾期60天以上的应付本金
		double fPayCapita1 = 0;
		double fUndueCapita1 = 0;
		Map<String, Object> over60DaysCapitalMap1 = new HashMap<>();
		over60DaysCapitalMap1 = trustContributionServer
				.getOverDueDaysCapital1(A2Map);
		if (over60DaysCapitalMap1 != null) {
			fPayCapita1 = Double.parseDouble(over60DaysCapitalMap1.get(
					"PAYPRINCIPALAMT").toString());
		}

		Map<String, Object> over60DaysCapitalMap2 = new HashMap<>();
		over60DaysCapitalMap2 = trustContributionServer
				.getOverDueDaysCapital2(A2Map);
		if (over60DaysCapitalMap2 != null) {
			fUndueCapita1 = Double.parseDouble(over60DaysCapitalMap2.get(
					"PAYPRINCIPALAMT").toString());
		}

		BigDecimal d1Capital1 = new BigDecimal(String.valueOf(fPayCapita1));
		BigDecimal d2Capital1 = new BigDecimal(String.valueOf(fUndueCapita1));
		double allCapital1 = d1Capital1.add(d2Capital1).doubleValue();

		Map<String, Object> resultMap2 = new HashMap<>();
		resultMap2 = trustContributionServer.getA3Capital(A2Map);
		if (resultMap2 != null) {
			// TODO
			A2 = allCapital1
					- Double.parseDouble((resultMap2.get("PAYPRINCIPALAMT")
							.toString()));
		}

		// 获取格式化对象
		NumberFormat nt = NumberFormat.getPercentInstance();
		// 设置百分数精确度2即保留两位小数
		nt.setMinimumFractionDigits(2);
		// 最后格式化并输出

		// TODO
		
		 * String M3= nt.format(A3/B3); String M2 = nt.format(A2/B3);
		 
		String M3 = "0.00%";
		String M2 = "0.00%";
		if (B3 != 0) {
			M3 = nt.format(A3 / B3);
			M2 = nt.format(A2 / B3);
		}
		

		URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
		// String cid = email.embed(url, "Apache logo");
		
		String firstTable = "<div>"
				+ "<table width=\"1150\" border=\"1\" cellspacing=\"0\">"
				+ "<caption>"
				+ caption
				+ "</caption>"
				+ "<tr style=\"background-color:#f47920\">"
				+ "<td align=\"center\">"
				+ td1
				+ "</td>"
				+ "<td align=\"center\">"
				+ td2
				+ "</td>"
				+ "<td align=\"center\">"
				+ td3
				+ "</td>"
				+ "<td align=\"center\">"
				+ td4
				+ "</td>"
				+ "<td align=\"center\">"
				+ td5
				+ "</td>"
				+ "<td align=\"center\">"
				+ td6
				+ "</td>"
				+ "<td align=\"center\">"
				+ td7
				+ "</td>"
				+ "<td align=\"center\">"
				+ td8
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align=\"center\">"
				+ trustremuneration
				+ "</td>"
				+ "<td align=\"center\">"
				+ sumOftrustRemuneration
				+ "</td>"
				+ "<td align=\"center\">"
				+ custodianFee
				+ "</td>"
				+ "<td align=\"center\">"
				+ sumOfcustodianFee
				+ "</td>"
				+ "<td align=\"center\">"
				+ priorityEarnings
				+ "</td>"
				+ "<td align=\"center\">";*/
		StringBuilder a1RateBuilder = new StringBuilder();
		a1RateBuilder = this.getA1RateBuilder();
		
		StringBuilder a2RateBuilder = new StringBuilder();
		a2RateBuilder = this.getA2RateBuilder();
		String commonHead = "<html>" + "<head>" + " </head>" + "<body>";
		String htmlMsg = commonHead
				+ a1RateBuilder.toString()
				+"</body>" + "</html>";
		email.setHtmlMsg(htmlMsg);
		email.send();
	}

	private StringBuilder getA2RateBuilder() {
		// TODO 计算A2率逻辑
		
		
		return null;
	}

	private StringBuilder getA1RateBuilder() throws UnsupportedEncodingException, ParseException {
		// TODO Auto-generated method stub

		int colNum = 15;
		StringBuilder commonTr = new StringBuilder();
		StringBuilder commonTd  = new StringBuilder();
		
		List<String> thList = new ArrayList<>(colNum);
		//TODO thList add 1 2~15 期
		thList.add(new String(("1_期").getBytes("GBK"), "ISO-8859-1"));
		thList.add(new String(("2_期").getBytes("GBK"), "ISO-8859-1"));
		thList.add(new String(("3_期").getBytes("GBK"), "ISO-8859-1"));
		thList.add(new String(("4_期").getBytes("GBK"), "ISO-8859-1"));
		thList.add(new String(("5_期").getBytes("GBK"), "ISO-8859-1"));
		thList.add(new String(("6_期").getBytes("GBK"), "ISO-8859-1"));
		thList.add(new String(("7_期").getBytes("GBK"), "ISO-8859-1"));
		thList.add(new String(("8_期").getBytes("GBK"), "ISO-8859-1"));
		thList.add(new String(("9_期").getBytes("GBK"), "ISO-8859-1"));
		thList.add(new String(("10_期").getBytes("GBK"), "ISO-8859-1"));
		thList.add(new String(("11_期").getBytes("GBK"), "ISO-8859-1"));
		thList.add(new String(("12_期").getBytes("GBK"), "ISO-8859-1"));
		thList.add(new String(("13_期").getBytes("GBK"), "ISO-8859-1"));
		thList.add(new String(("14_期").getBytes("GBK"), "ISO-8859-1"));
		thList.add(new String(("15_期").getBytes("GBK"), "ISO-8859-1"));
		
		new String(("上月差额补足金额").getBytes("GBK"), "ISO-8859-1");
		
		//List<Map<String, Object>> tdList = new ArrayList<>();
		
		
		
		//tdList = trustContributionServer.getA1List(partnerCode);
			
		commonTr = this.getCommonTr(thList,colNum);
		
		commonTd = this.getCommonTd();
			
		//TODO add
		commonTr.append(commonTd).append("</table>").append("</div>");
		//commonTr.append("</table>").append("</div>");
		
		return commonTr;
	}

	private StringBuilder getCommonTd() throws ParseException {	
		ITrustContributionServer trustContributionServer = new TrustContributionServer();
		
		//TODO tdList 取数逻辑
	//	ITrustContributionServer trustContributionServer = new TrustContributionServer();
		//List<Double> tdList = new ArrayList<>(colNum);
		StringBuilder commonTd = new StringBuilder();
		commonTd.append("<tr>").append("\n");

		//TODO 计算当天的A1率,替换到第一个null值
	String partnerCode = "ZAYX";
	String startDate="2017-04";
	SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM");
	String currentDate = formatter.format(new Date());
	
	List<String> monthList	=getMonthBetween(startDate,currentDate);
	for (String loanDate : monthList) {
		
		List<Map<String, Object>> tdList = new ArrayList<>();
		Map<String, String>parmA1Map = new HashMap<>();
		parmA1Map.put("partnerCode", partnerCode);
		parmA1Map.put("loanDate", loanDate);

		tdList = trustContributionServer.getA1List(parmA1Map);
		

		
		int denominator;
		int denominatorOfPay;
		int denominatorUndue = 0;
	/*	List<String> parmDenominatorOfPayList = new ArrayList<>();
		parmDenominatorOfPayList.add("ZHBD");
		parmDenominatorOfPayList.add("2016-10");*/
		Map<String, String> parmDenominatorOfPayMap = new HashMap<>();
		parmDenominatorOfPayMap.put("partnerCode", "ZHBD");
		parmDenominatorOfPayMap.put("loanDate", loanDate);
		denominatorOfPay = trustContributionServer.getDenominatorOfPay(parmDenominatorOfPayMap);
		Map<String, String> parmDenominatorUndueMap =new HashMap<>();
		parmDenominatorUndueMap.put("partnerCode", "HBYH");
		parmDenominatorUndueMap.put("loanDate", loanDate);
		//TODO 测试不加，最好测试加
		denominatorUndue = trustContributionServer.getDenominatorOfUndue(parmDenominatorUndueMap);
		denominator = denominatorOfPay + denominatorUndue;
		
		//获取逾期90以内的贷款数量
		 int numerator;
		// String loanDate = "2015-01";
		 numerator = trustContributionServer.getNumeratorOfA1(loanDate);
		 double Ax = 0;
		 if (denominator != 0) {
			 DecimalFormat df = new DecimalFormat("0.0000");
			 Ax= Double.parseDouble(df.format((double)numerator/denominator));
		}
		 //判断当天是否是最后一天，是的话，update 进trustA1
		 
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		 
	        Calendar ca = Calendar.getInstance();    
	        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
	        String last = format.format(ca.getTime());	        
	        String current = format.format(new Date());
	        
			int mapSize = 	tdList.get(0).size();
			if (mapSize == 16) {
				mapSize = 15;
			}
			String colName = "A"+mapSize;
	        
	        if (!current.equalsIgnoreCase(last)) {
				//update trustA1  value= Ax
	        	Map<String, Object> axMap = new HashMap<>();
	        	axMap.put("col", colName);
	        	axMap.put("colValue", "'"+Ax+"'");
	        	axMap.put("partnerCode", "'"+partnerCode+"'");
	        	axMap.put("loanDate", "'"+loanDate+"'");
	        	trustContributionServer.addAxIntoTrustA1(axMap);
			}
	        
	        

		 		 
		 tdList.get(0).size();
		
		Object loadDate = 	tdList.get(0).get("LOADDATE");
		Object A1 = 	tdList.get(0).get("A1");
		Object A2 = 	tdList.get(0).get("A2");
		Object A3=	tdList.get(0).get("A3");
		Object A4 = 	tdList.get(0).get("A4");
		Object A5 = 	tdList.get(0).get("A5");
		Object A6 = 	tdList.get(0).get("A6");
		Object A7 = 	tdList.get(0).get("A7");
		Object A8 = 	tdList.get(0).get("A8");
		Object A9 = 	tdList.get(0).get("A9");
		Object A10 = 	tdList.get(0).get("A10");
		Object A11 = 	tdList.get(0).get("A11");
		Object A12 = 	tdList.get(0).get("A12");
		Object A13 = 	tdList.get(0).get("A13");
		Object A14 = 	tdList.get(0).get("A14");
		Object A15 = 	tdList.get(0).get("A15");

			
			List<Object> tdList2 = new ArrayList<>();
			tdList2.add(loadDate);
			tdList2.add(A1);
			tdList2.add(A2);
			tdList2.add(A3);
			tdList2.add(A4);
			tdList2.add(A5);
			tdList2.add(A6);
			tdList2.add(A7);
			tdList2.add(A8);
			tdList2.add(A9);
			tdList2.add(A10);
			tdList2.add(A11);
			tdList2.add(A12);
			tdList2.add(A13);
			tdList2.add(A14);
			tdList2.add(A15);
			for (int i = 1; i < tdList2.size(); i++) {
				if (tdList2.get(i) != null) {
					Object temp = tdList2.get(i);					
					if (Double.parseDouble(tdList2.get(i).toString()) != 0) {
						tdList2.remove(i);
						tdList2.add(i,per(temp));
					}else {
						tdList2.remove(i);
						tdList2.add(i,0);
					}					
					continue;
				}else {
					tdList2.remove(i);
					if (Ax != 0) {
						tdList2.add(i,per(Ax));
					}else {
						tdList2.add(i,Ax);
					}
					
					//break;
					for (int j = i+1; j < tdList2.size(); j++) {
							tdList2.remove(j);
							tdList2.add(j,"-");					
					}break;
				}				
			}
			for (Object value : tdList2 ) {
				commonTd.append("<td align=\"center\">").append("\n").append(value)
				.append("\n")
				.append("</td>");
			}
			
	        commonTd.append("\n").append("</tr>");
	}

		return commonTd;
	}

	private StringBuilder getCommonTr(List<String> thList,int colNum) throws UnsupportedEncodingException {
		StringBuilder commonTr = new StringBuilder();
	//	List<String> thList = new ArrayList<>(colNum);

		
		commonTr.append("")
		.append("<div>")
		.append("<table width=\"1150\" border=\"1\" cellspacing=\"0\">")
		.append("<caption>")
		.append(new String(("A1率").getBytes("GBK"), "ISO-8859-1"))
		.append("</caption>")
		.append("<tr>")
		.append("<td align=\"center\">")
		.append(new String(("放款月份").getBytes("GBK"), "ISO-8859-1"))
		.append("</td>");
		
		for (int i = 0; i < colNum; i++) {
			commonTr.append("<td align=\"center\">").append("\n").append(thList.get(i))
			.append("\n")
			.append("</td>");
		}
		return commonTr;
	}

	private Date getYestoday() {
		// TODO Auto-generated method stub
		Date dNow = new Date(); // 当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		dBefore = calendar.getTime(); // 得到前一天的时间
		return dBefore;
	}
	
	/**
	 * 获取两个月份之间所有月份
	 * @throws ParseException 
	 */
	private static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
	    ArrayList<String> result = new ArrayList<String>();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

	    Calendar min = Calendar.getInstance();
	    Calendar max = Calendar.getInstance();

	    min.setTime(sdf.parse(minDate));
	    min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

	    max.setTime(sdf.parse(maxDate));
	    max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

	    Calendar curr = min;
	    while (curr.before(max)) {
	     result.add(sdf.format(curr.getTime()));
	     curr.add(Calendar.MONTH, 1);
	    }

	    return result;
	  }
	
	/**
	 * 百分号显示
	 * @param a1
	 * @return
	 */
	private static String per(Object a1) {
		
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
        format.setMinimumFractionDigits(2);// 设置小数位
        return  format.format(a1);		
	}
	
	/**
	 * 获取当前月的上一个月 yyyy-mm
	 * @return
	 */
    public static String getLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, -1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
        String lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }
}

package com.billionsfinance.bas.entity.voucher.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CwVoucherModelExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	public CwVoucherModelExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		protected void addCriterionForJDBCDate(String condition, Date value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			addCriterion(condition, new java.sql.Date(value.getTime()),
					property);
		}

		protected void addCriterionForJDBCDate(String condition,
				List<Date> values, String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property
						+ " cannot be null or empty");
			}
			List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
			Iterator<Date> iter = values.iterator();
			while (iter.hasNext()) {
				dateList.add(new java.sql.Date(iter.next().getTime()));
			}
			addCriterion(condition, dateList, property);
		}

		protected void addCriterionForJDBCDate(String condition, Date value1,
				Date value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			addCriterion(condition, new java.sql.Date(value1.getTime()),
					new java.sql.Date(value2.getTime()), property);
		}

		public Criteria andIdIsNull() {
			addCriterion("ID is null");
			return (Criteria) this;
		}

		public Criteria andIdIsNotNull() {
			addCriterion("ID is not null");
			return (Criteria) this;
		}

		public Criteria andIdEqualTo(BigDecimal value) {
			addCriterion("ID =", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotEqualTo(BigDecimal value) {
			addCriterion("ID <>", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThan(BigDecimal value) {
			addCriterion("ID >", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("ID >=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThan(BigDecimal value) {
			addCriterion("ID <", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThanOrEqualTo(BigDecimal value) {
			addCriterion("ID <=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdIn(List<BigDecimal> values) {
			addCriterion("ID in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotIn(List<BigDecimal> values) {
			addCriterion("ID not in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("ID between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("ID not between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andModelNoIsNull() {
			addCriterion("MODEL_NO is null");
			return (Criteria) this;
		}

		public Criteria andModelNoIsNotNull() {
			addCriterion("MODEL_NO is not null");
			return (Criteria) this;
		}

		public Criteria andModelNoEqualTo(String value) {
			addCriterion("MODEL_NO =", value, "modelNo");
			return (Criteria) this;
		}

		public Criteria andModelNoNotEqualTo(String value) {
			addCriterion("MODEL_NO <>", value, "modelNo");
			return (Criteria) this;
		}

		public Criteria andModelNoGreaterThan(String value) {
			addCriterion("MODEL_NO >", value, "modelNo");
			return (Criteria) this;
		}

		public Criteria andModelNoGreaterThanOrEqualTo(String value) {
			addCriterion("MODEL_NO >=", value, "modelNo");
			return (Criteria) this;
		}

		public Criteria andModelNoLessThan(String value) {
			addCriterion("MODEL_NO <", value, "modelNo");
			return (Criteria) this;
		}

		public Criteria andModelNoLessThanOrEqualTo(String value) {
			addCriterion("MODEL_NO <=", value, "modelNo");
			return (Criteria) this;
		}

		public Criteria andModelNoLike(String value) {
			addCriterion("MODEL_NO like", value, "modelNo");
			return (Criteria) this;
		}

		public Criteria andModelNoNotLike(String value) {
			addCriterion("MODEL_NO not like", value, "modelNo");
			return (Criteria) this;
		}

		public Criteria andModelNoIn(List<String> values) {
			addCriterion("MODEL_NO in", values, "modelNo");
			return (Criteria) this;
		}

		public Criteria andModelNoNotIn(List<String> values) {
			addCriterion("MODEL_NO not in", values, "modelNo");
			return (Criteria) this;
		}

		public Criteria andModelNoBetween(String value1, String value2) {
			addCriterion("MODEL_NO between", value1, value2, "modelNo");
			return (Criteria) this;
		}

		public Criteria andModelNoNotBetween(String value1, String value2) {
			addCriterion("MODEL_NO not between", value1, value2, "modelNo");
			return (Criteria) this;
		}

		public Criteria andModelNameIsNull() {
			addCriterion("MODEL_NAME is null");
			return (Criteria) this;
		}

		public Criteria andModelNameIsNotNull() {
			addCriterion("MODEL_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andModelNameEqualTo(String value) {
			addCriterion("MODEL_NAME =", value, "modelName");
			return (Criteria) this;
		}

		public Criteria andModelNameNotEqualTo(String value) {
			addCriterion("MODEL_NAME <>", value, "modelName");
			return (Criteria) this;
		}

		public Criteria andModelNameGreaterThan(String value) {
			addCriterion("MODEL_NAME >", value, "modelName");
			return (Criteria) this;
		}

		public Criteria andModelNameGreaterThanOrEqualTo(String value) {
			addCriterion("MODEL_NAME >=", value, "modelName");
			return (Criteria) this;
		}

		public Criteria andModelNameLessThan(String value) {
			addCriterion("MODEL_NAME <", value, "modelName");
			return (Criteria) this;
		}

		public Criteria andModelNameLessThanOrEqualTo(String value) {
			addCriterion("MODEL_NAME <=", value, "modelName");
			return (Criteria) this;
		}

		public Criteria andModelNameLike(String value) {
			addCriterion("MODEL_NAME like", value, "modelName");
			return (Criteria) this;
		}

		public Criteria andModelNameNotLike(String value) {
			addCriterion("MODEL_NAME not like", value, "modelName");
			return (Criteria) this;
		}

		public Criteria andModelNameIn(List<String> values) {
			addCriterion("MODEL_NAME in", values, "modelName");
			return (Criteria) this;
		}

		public Criteria andModelNameNotIn(List<String> values) {
			addCriterion("MODEL_NAME not in", values, "modelName");
			return (Criteria) this;
		}

		public Criteria andModelNameBetween(String value1, String value2) {
			addCriterion("MODEL_NAME between", value1, value2, "modelName");
			return (Criteria) this;
		}

		public Criteria andModelNameNotBetween(String value1, String value2) {
			addCriterion("MODEL_NAME not between", value1, value2, "modelName");
			return (Criteria) this;
		}

		public Criteria andCreateTimeIsNull() {
			addCriterion("CREATE_TIME is null");
			return (Criteria) this;
		}

		public Criteria andCreateTimeIsNotNull() {
			addCriterion("CREATE_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andCreateTimeEqualTo(Date value) {
			addCriterionForJDBCDate("CREATE_TIME =", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotEqualTo(Date value) {
			addCriterionForJDBCDate("CREATE_TIME <>", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeGreaterThan(Date value) {
			addCriterionForJDBCDate("CREATE_TIME >", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("CREATE_TIME >=", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeLessThan(Date value) {
			addCriterionForJDBCDate("CREATE_TIME <", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("CREATE_TIME <=", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeIn(List<Date> values) {
			addCriterionForJDBCDate("CREATE_TIME in", values, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotIn(List<Date> values) {
			addCriterionForJDBCDate("CREATE_TIME not in", values, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("CREATE_TIME between", value1, value2,
					"createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("CREATE_TIME not between", value1, value2,
					"createTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeIsNull() {
			addCriterion("UPDATE_TIME is null");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeIsNotNull() {
			addCriterion("UPDATE_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeEqualTo(Date value) {
			addCriterionForJDBCDate("UPDATE_TIME =", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotEqualTo(Date value) {
			addCriterionForJDBCDate("UPDATE_TIME <>", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeGreaterThan(Date value) {
			addCriterionForJDBCDate("UPDATE_TIME >", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("UPDATE_TIME >=", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeLessThan(Date value) {
			addCriterionForJDBCDate("UPDATE_TIME <", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("UPDATE_TIME <=", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeIn(List<Date> values) {
			addCriterionForJDBCDate("UPDATE_TIME in", values, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotIn(List<Date> values) {
			addCriterionForJDBCDate("UPDATE_TIME not in", values, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("UPDATE_TIME between", value1, value2,
					"updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("UPDATE_TIME not between", value1, value2,
					"updateTime");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
	 * @mbggenerated
	 */
	public static class Criterion {
		private String condition;
		private Object value;
		private Object secondValue;
		private boolean noValue;
		private boolean singleValue;
		private boolean betweenValue;
		private boolean listValue;
		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue,
				String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SITBAS_XFD_TMK.CW_VOUCHER_MODEL
     *
     * @mbggenerated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
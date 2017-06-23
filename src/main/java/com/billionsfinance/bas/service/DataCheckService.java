package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.DataCheckDao;

public class DataCheckService {

	@Autowired
	private DataCheckDao dataCheckDao;
	
	/**
	 * 根据条件查询用户数量
	 * @param alsUser
	 * @return
	 */
	public List<Map<String,Object>> queryDataCheckTableMenuOne(Map<String,Object> map){
		return dataCheckDao.queryDataCheckTableMenuOne(map);
	}
	
	public List<Map<String,Object>> queryDataCheckTableMenuTwo(Map<String,Object> map){
		return dataCheckDao.queryDataCheckTableMenuTwo(map);
	}
	
	public List<Map<String,Object>> queryDataCheckTableMenuThree(Map<String,Object> map){
		return dataCheckDao.queryDataCheckTableMenuThree(map);
	}
	
	public List<Map<String,Object>> queryDataCheckTableMenuFour(Map<String,Object> map){
		return dataCheckDao.queryDataCheckTableMenuFour(map);
	}
	
	public List<Map<String,Object>> queryDataCheckTableMenuFive(Map<String,Object> map){
		return dataCheckDao.queryDataCheckTableMenuFive(map);
	}
	
	public List<Map<String,Object>> queryDataCheckTableMenuNine(Map<String,Object> map){
		return dataCheckDao.queryDataCheckTableMenuNine(map);
	}
	
	public List<Map<String,Object>> queryDataCheckTableMenuTwelve(Map<String,Object> map){
		return dataCheckDao.queryDataCheckTableMenuTwelve(map);
	}
	
	public Map<String,Object> queryDataCheckTableMenuOneCount() {
		// TODO Auto-generated method stub
		return dataCheckDao.queryDataCheckTableMenuOneCount();
	}
	
	public Map<String,Object> queryDataCheckTableMenuTwoCount() {
		// TODO Auto-generated method stub
		return dataCheckDao.queryDataCheckTableMenuTwoCount();
	}
	
	public Map<String,Object> queryDataCheckTableMenuThreeCount() {
		// TODO Auto-generated method stub
		return dataCheckDao.queryDataCheckTableMenuThreeCount();
	}
	
	public Map<String,Object> queryDataCheckTableMenuFourCount() {
		// TODO Auto-generated method stub
		return dataCheckDao.queryDataCheckTableMenuFourCount();
	}
	
	public Map<String,Object> queryDataCheckTableMenuFiveCount() {
		// TODO Auto-generated method stub
		return dataCheckDao.queryDataCheckTableMenuFiveCount();
	}
	
	public Map<String,Object> queryDataCheckTableMenuNineCount() {
		// TODO Auto-generated method stub
		return dataCheckDao.queryDataCheckTableMenuNineCount();
	}
	
	public Map<String,Object> queryDataCheckTableMenuTwelveCount() {
		// TODO Auto-generated method stub
		return dataCheckDao.queryDataCheckTableMenuTwelveCount();
	}
	
	public List<Map<String,Object>> queryDataCheckTableMenuOneFindAll(){
		return dataCheckDao.queryDataCheckTableMenuOneFindAll();
	}
	
	public List<Map<String,Object>> queryDataCheckTableMenuTwoFindAll(){
		return dataCheckDao.queryDataCheckTableMenuTwoFindAll();
	}
	
	public List<Map<String,Object>> queryDataCheckTableMenuThreeFindAll(){
		return dataCheckDao.queryDataCheckTableMenuThreeFindAll();
	}
	
	public List<Map<String,Object>> queryDataCheckTableMenuFourFindAll(){
		return dataCheckDao.queryDataCheckTableMenuFourFindAll();
	}
	
	public List<Map<String,Object>> queryDataCheckTableMenuFiveFindAll(){
		return dataCheckDao.queryDataCheckTableMenuFiveFindAll();
	}
	
	public List<Map<String,Object>> queryDataCheckTableMenuNineFindAll(){
		return dataCheckDao.queryDataCheckTableMenuNineFindAll();
	}
	
	public List<Map<String,Object>> queryDataCheckTableMenuTwelveFindAll(){
		return dataCheckDao.queryDataCheckTableMenuTwelveFindAll();
	}
	
	
}

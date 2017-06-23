package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.CheckListVO;
import com.billionsfinance.bas.entity.PageVO;

public interface ICheckListServer {

	public PageVO queryBusinessDetail(PageVO pageVo,CheckListVO vo);
	
	public void importContract(List<CheckListVO> list);
	
	public void updateContract(List<CheckListVO> list);
	
	/**
	 * 查询合同总数
	 * @param alsUser
	 * @return
	 */
	public Map<String,Object> queryContractCount(Map<String,Object> map);
	
	public List<CheckListVO> queryCheckListDataFindAll();
	
}

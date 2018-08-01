package cn.edu.jxau.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import cn.edu.jxau.common.entity.Production;
import cn.edu.jxau.common.entity.User;
import cn.edu.jxau.dao.mapper.ProductionMapper;
import cn.edu.jxau.dao.mapper.UserMapper;
import cn.edu.jxau.service.CompetitionService;
import cn.edu.jxau.web.exception.CustomException;

public class CompetitionServiceImpl implements CompetitionService{

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ProductionMapper productionMapper;
	
	/**
	 * 报名大赛
	 * 根据userId修改用户报名状态
	 */
	@Override
	public void signUp(User user) throws CustomException {
		userMapper.updateByPrimaryKeySelective(user);
	}

	/**
	 * 提交作品
	 */
	@Override
	public boolean uploadWorks(Production production) {
		int result = productionMapper.insertSelective(production);
		if(result == 1) {
			return true;
		}
		return false;
	}


	/**
	 * 更新作品 
	 */
	@Override
	public boolean updatadWorks(Production production) {
		int result = productionMapper.updateByUserIdSelective(production);
		if(result == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 查询作品详细信息
	 * @return
	 */
	@Override
	public Production queryProduction(Long production_id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 查询所有的作品信息
	 * @return
	 */
	@Override
	public List<Production> queryProductionList() {
		List<Production> productionList = productionMapper.selectAll();
		return productionList;
	}

}

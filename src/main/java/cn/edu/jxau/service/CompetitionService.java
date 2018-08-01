package cn.edu.jxau.service;

import java.util.List;

import cn.edu.jxau.common.entity.Production;
import cn.edu.jxau.common.entity.User;

public interface CompetitionService {

	/**
	 * 报名大赛
	 * 根据userId修改用户报名状态
	 * 2017年12月30日
	 * zclong
	 * @param userId
	 */
	void signUp(User user);
	
	/**
	 * 提交作品
	 * 2017年12月30日
	 * zclong
	 * @param production
	 */
	boolean uploadWorks(Production production);
	
	/**
	 * 更新作品
	 * @param production
	 * @return
	 */
	boolean updatadWorks(Production production);
	
	/**
	 * 查询作品详细信息
	 * @return
	 */
	Production queryProduction(Long production_id);
	
	/**
	 * 查询所有的作品信息
	 * @return
	 */
	List<Production> queryProductionList();
}

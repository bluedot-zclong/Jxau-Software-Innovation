package cn.edu.jxau.common.entity;

/**
 * 存储用户-作品信息
 * @author zclong
 *
 */
public class UserProduction extends BaseEntity{

	/**
	 * 实现序列化
	 */
	private static final long serialVersionUID = 1L;
	// 用户对象
	private User user;
	// 用户作品
	private Production production;
	// 作品数
	private Integer sum;
	
	
	public Integer getSum() {
		return sum;
	}
	public void setSum(Integer sum) {
		this.sum = sum;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Production getProduction() {
		return production;
	}
	public void setProduction(Production production) {
		this.production = production;
	}
	
}

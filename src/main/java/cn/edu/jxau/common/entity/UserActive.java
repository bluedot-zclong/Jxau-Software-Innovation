package cn.edu.jxau.common.entity;

import java.util.List;

/**
 * user的扩展类
 * @author zclong
 * 2017年12月21日
 */
public class UserActive extends BaseEntity{
	/**
	 * 实现序列化
	 */
	private static final long serialVersionUID = 50954961944726745L;
	private Long id;
	// 登录名
    private String loginName;
	// 用户对象
	private User user;
	// 用户有的功能权限
	private List<Function> functions;
	// 用户对应的组员
	private List<Member> members;
	// 某个组员的详细信息
	private Member member;
	// 用户提交作品次数
	private Integer submitCount;
	
	public Integer getSubmitCount() {
		return submitCount;
	}
	public void setSubmitCount(Integer submitCount) {
		this.submitCount = submitCount;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public List<Member> getMembers() {
		return members;
	}
	public void setMembers(List<Member> members) {
		this.members = members;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Function> getFunctions() {
		return functions;
	}
	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	//本函数输出将作为默认的<shiro:principal/>输出.
	@Override
	public String toString() {
		return loginName;
	}
	
}

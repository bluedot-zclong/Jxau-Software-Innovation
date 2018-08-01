package cn.edu.jxau.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.jxau.common.entity.Member;
import cn.edu.jxau.dao.mapper.MemberMapper;
import cn.edu.jxau.dao.mapper.MembersMapper;
import cn.edu.jxau.service.MemberService;
import cn.edu.jxau.web.exception.CustomException;
import cn.edu.jxau.web.util.CustomDateConverter;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private MembersMapper membersMapper;

	/**
	 * 添加队员信息
	 */
	@Override
	public boolean addMember(Member member) {
		// 添加创建时间
		Date gmtCreate = CustomDateConverter.getNowDate();
		Date gmtModified = CustomDateConverter.getNowDate();
		member.setGmtCreate(gmtCreate);
		member.setGmtModified(gmtModified);
		// 队员为普通学生,0:代表普通学生，1：代表指导老师
		if(member.getMemberRole() == null) {
			member.setMemberMajor(member.getMemberClasss().substring(0, 4));
			member.setMemberRole(0);
		}
		
		int result= memberMapper.insert(member);
		if(result != 1) {
			return false;
		}
		return true;
	}

	@Override
	public List<Member> queryMemberByUserId(Long userId) throws CustomException {
		List<Member> members = null;
		if (userId != null) {
			Member member = new Member();
			member.setUserId(userId);
			members = membersMapper.selectMember(member);
		}
		return members;
	}

	@Override
	public Member queryMember(Long memberId) throws CustomException {
		Member member = memberMapper.selectByPrimaryKey(memberId);
		// 处理异常
		if (member == null) {
			throw new CustomException("查询的用户" + memberId + "不存在");
		}
		return member;
	}

	/**
	 * 更新队员信息
	 */
	@Override
	@Transactional
	public boolean upateMember(Member member) {
		// 修改时间
        Date gmtModified = CustomDateConverter.getNowDate();
        member.setGmtModified(gmtModified);
        if(member.getMemberRole() == null) {
        	member.setMemberMajor(member.getMemberClasss().substring(0, 4));
        }
        
		int result = memberMapper.updateByPrimaryKeySelective(member);
		if(result != 1) {
			return false;
		}
		return true;
	}

	/**
	 * 删除队员
	 */
	@Override
	public boolean removeMember(Long memberId) {
		int result = memberMapper.deleteByPrimaryKey(memberId);
		if(result != 1) {
			return false;
		}
		return true;
	}

}

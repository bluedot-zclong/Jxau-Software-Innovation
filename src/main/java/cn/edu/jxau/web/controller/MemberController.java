package cn.edu.jxau.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.jxau.common.entity.Member;
import cn.edu.jxau.common.entity.UserActive;
import cn.edu.jxau.service.MemberService;
import cn.edu.jxau.web.core.BaseController;
import cn.edu.jxau.web.exception.CustomException;
import cn.edu.jxau.web.util.ValidUtils;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {

	@Autowired
	private MemberService memberService;

	/**
	 * 添加队员信息
	 * 
	 * @param model
	 * @param req
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping(value = "/addMember", method = RequestMethod.POST)
	public @ResponseBody Object addMember(@RequestBody Member member, HttpServletRequest req) {
		// 队员信息验证
		if (!ValidUtils.memberVerify(member)) {
			return renderError("信息不为空或者格式错误");
		}
		if(member.getMemberId() != null) {
			// 更新信息
			if(memberService.upateMember(member)) {
				// 更新session数据
	        	updateMemberList(req);
	        	return renderSuccess("更新信息成功");
	        }else {
	        	return renderSuccess("更新失败");
	        }
		}
		// 获取用户id（队长）
		Long userId = getUserId(req);
		member.setUserId(userId);

		// 添加用户信息
		if (memberService.addMember(member)) {
			// 更新session数据
			updateMemberList(req);
			return renderSuccess("添加成功");
		}
		return renderSuccess("添加失败");
	}


	/**
	 * 查找队员信息
	 * @param memberId
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping("/queryMember/{memberId}")
	public String queryMember(@PathVariable("memberId") Long memberId, Model model, HttpServletRequest req) {
		if (memberId != null) {
			Member member = memberService.queryMember(memberId);
			if (member == null || !getMember(req, memberId)) {
				return "redirect:/view/userInfo";
			}
			model.addAttribute("member", member);
		} else {
			return "redirect:/view/userInfo";
		}
		return "member/modifyMember";
	}

	/**
	 * 修改队员信息
	 * @param model
	 * @param req
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping(value = "/modifyMember", method = RequestMethod.POST)
	public @ResponseBody Object modifyMember(@RequestBody Member member, HttpServletRequest req) throws CustomException {
		Long userId = getUserId(req);
		if(userId == null) {
			return renderError("请先登录！");
		}
		if(memberService.upateMember(member)) {
			// 更新session数据
        	updateMemberList(req);
        	return renderSuccess("更新信息成功");
        }
        return renderError("更新信息失败!");
	}
	
	/**
	 * 删除队员
	 * @param memberId
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping("/removeMember")
	public @ResponseBody Object removeMember(Long memberId, HttpServletRequest req) {
		
		Member member = memberService.queryMember(memberId);
		
        if (member == null || !getMember(req, memberId)) {
            return renderError("该队员不存在!");
        }
        if (memberService.removeMember(member.getMemberId())) {
        	// 更新session数据
        	updateMemberList(req);
            return renderSuccess("删除成功");
        }
        return renderError("删除失败!");
	}

	/**
	 * 更新session中的成员信息(缓存？) 2017年12月28日 zclong
	 * 
	 * @param userId
	 * @throws CustomException
	 */
	public void updateMemberList(HttpServletRequest req) {
		UserActive userActive = getSessionUser(req);
		Long userId = userActive.getUser().getUserId();
		// 查询用户对应的组员信息，并添加到session中
		List<Member> members = memberService.queryMemberByUserId(userId);
		userActive.setMember(null);
		// 单独存储老师信息
		for (Member member : members) {
			if(member.getMemberRole() == 1) {
				userActive.setMember(member);
			}
		}
		userActive.setMembers(members);
		req.getSession().setAttribute("user_session", userActive);
	}

}

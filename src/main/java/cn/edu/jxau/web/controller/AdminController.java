package cn.edu.jxau.web.controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.jxau.common.entity.Production;
import cn.edu.jxau.common.entity.User;
import cn.edu.jxau.common.entity.UserProduction;
import cn.edu.jxau.service.CompetitionService;
import cn.edu.jxau.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private CompetitionService competitionService;
	@Autowired
	private UserService userService;
	
	/**
	 * 查询所有的作品信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("null")
	@GetMapping("/queryProductionList")
	public String queryProductionList(HttpServletRequest request, Model model) {
		List<Production> productions = competitionService.queryProductionList();
		List<UserProduction> userProductions = new LinkedList<UserProduction>();
		UserProduction userProduction = null;
		for (Iterator iterator = productions.iterator(); iterator.hasNext();) {
			Production production = (Production) iterator.next();
            //获取提交作品人信息
			User userInfo = userService.findUserById(production.getUserId());
            //封装进Data对象
			userProduction = new UserProduction();
			userProduction.setProduction(production);
			userProduction.setUser(userInfo);
			userProductions.add(userProduction);
        }
		model.addAttribute("sum", userProductions.size());
		model.addAttribute("userProductions", userProductions);
		return "admin/zdata-list";
	}
	
}

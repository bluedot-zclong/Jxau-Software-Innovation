package cn.edu.jxau.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.edu.jxau.common.entity.Member;
import cn.edu.jxau.common.entity.Production;
import cn.edu.jxau.common.entity.User;
import cn.edu.jxau.common.entity.UserActive;
import cn.edu.jxau.common.utils.FileUtil;
import cn.edu.jxau.service.CompetitionService;
import cn.edu.jxau.service.MemberService;
import cn.edu.jxau.service.UserService;
import cn.edu.jxau.web.core.BaseController;
import cn.edu.jxau.web.interceptor.Token;
import cn.edu.jxau.web.util.CustomDateConverter;

/**
 * 报名大赛，提交作品
 * 
 * @author zclong 2017年12月30日
 */
@Controller
@RequestMapping("/user")
public class CompetitionController extends BaseController {
	private final static String USER_SESSION = "user_session";
	@Autowired
	private CompetitionService competitionService;
	@Autowired
	private UserService userService;
	@Autowired
	private MemberService memberService;

	/**
	 * 报名大赛
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/signUp")
	public @ResponseBody Object signUp(HttpServletRequest req) {
		Long userId = getUserId(req);
		if (userId != null) {
			User userInfo = getSessionLoginUser(req);
			if (userInfo != null && userInfo.getEnrollType() == 1) {
				return renderError("您已经报名了！");
			}
			// 设置为已报名
			userInfo.setEnrollType(1);
			// 修改时间
			Date gmtModified = CustomDateConverter.getNowDate();
			userInfo.setGmtModified(gmtModified);
			if (userService.updateUser(userInfo)) {
				saveSessionUser(userService.findUserById(userInfo.getUserId()), req.getSession());
				return renderSuccess("报名成功！");
			}
		} else {
			return renderError("请先登录！");
		}
		return renderError("报名失败！");
	}

	@RequestMapping("/worksSubmit")
	@Token(save = true)
	public String worksSubmit() {
		return "works/worksSubmit";
	}

	/**
	 * 上传文件 2017年12月30日 zclong
	 * 
	 * @param model
	 * @param request
	 * @param worksType
	 * @param productionName
	 * @param fileData
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadWorks", method = RequestMethod.POST)
	@Token(remove=true)
	public String uploadWorks(RedirectAttributes  model, HttpServletRequest request, @RequestParam("productionType") String productionType,
			@RequestParam("productionName") String productionName,
			@RequestParam("productionContent") String productionContent,
			@RequestParam("fileData") MultipartFile fileData) throws Exception {
		
		Long userId = getUserId(request);
		if (userId == null) {
			return "redirect:/view/login";
		}
		
		// 相对路径,存数据库
		String relativePath = FileUtil.buildYearMonth();

		// 如果文件不为空，写入上传路径
		if (!fileData.isEmpty() && !productionName.isEmpty()) {

			// 判断是否已经更新
			User userInfo = getSessionLoginUser(request);
			if (userInfo != null && userInfo.getSubmitStatus() == 2) {
			    model.addFlashAttribute("msg", "不能再次提交，如有需要请联系管理员!");
				return "redirect:/view/worksSubmit";
			}
			
			// 重命名，保证文件名的唯一性
			String savename = FileUtil.getUUIDName(productionName);
			relativePath += savename;

			// 上传文件路径
			String path = request.getServletContext().getRealPath("/WEB-INF/fileData/");
			// 上传文件名
			File filePath = new File(path, relativePath);
			// 判断路径是否存在，如果不存在就创建一个
			if (!filePath.getParentFile().exists()) {
				filePath.getParentFile().mkdirs();
			}
			// 将上传文件保存到一个目标文件当中
			fileData.transferTo(new File(path + File.separator + relativePath));
			// 将路径存储到数据库中
			Production production = new Production();
			production.setUserId(getUserId(request));
			production.setProductionContent(productionContent);
			production.setProductionName(productionName);
			production.setProductionType(productionType);
			production.setProductionPath(relativePath);
			// 修改时间
			Date gmtModified = CustomDateConverter.getNowDate();
			production.setGmtCreate(gmtModified);
			production.setGmtModified(gmtModified);
			if(getSessionLoginUser(request).getSubmitStatus() == 1) {
				// 设置为已更新
				userInfo.setSubmitStatus(2);
				// 更新
				if(competitionService.updatadWorks(production)) {
					model.addFlashAttribute("msg", "更新作品成功!");
				}else {
					model.addFlashAttribute("msg", "提交作品失败，请重试!");
					return "redirect:/view/worksSubmit";
				}
			}else {
				// 设置为已报名
				userInfo.setSubmitStatus(1);
				// 添加
				if(competitionService.uploadWorks(production)) {
					model.addFlashAttribute("msg", "提交作品成功!");
				}else {
					model.addFlashAttribute("msg", "提交作品失败，请重试!");
					return "redirect:/view/worksSubmit";
				}
			}
			// 更新用户信息
			userInfo.setGmtModified(gmtModified);
			if (userService.updateUser(userInfo)) {
				saveSessionUser(userService.findUserById(userInfo.getUserId()), request.getSession());
			}
		} else {
			model.addFlashAttribute("msg", "提交作品失败，请重试!");
		}
		return "redirect:/view/worksSubmit";
	}
	
	/**
	 * 下载文件 2017年12月30日 zclong
	 * 
	 * @param request
	 * @param productionName
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadWorks")
	public ResponseEntity<byte[]> downloadWorks(HttpServletRequest request,
			@RequestParam("productionName") String productionName, Model model) throws Exception {
		// 下载文件路径
		String path = request.getServletContext().getRealPath("/WEB-INF/fileData/");
		File file = new File(path + File.separator + productionName);
		if(file.exists()){  
			HttpHeaders headers = new HttpHeaders();
			// 下载显示的文件名，解决中文名称乱码问题
			String downloadFielName = new String(productionName.getBytes("UTF-8"), "iso-8859-1");
			// 通知浏览器以attachment（下载方式）打开图片
			headers.setContentDispositionFormData("attachment", downloadFielName);
			// application/octet-stream ： 二进制流数据（最常见的文件下载）。
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		} else {
			response.setContentType("text/html;charset=utf-8");  
	        PrintWriter out = response.getWriter();  
	        out.println("文件不存在");
	        return null;
		}
	}

	/**
	 * 存储用户信息到session中
	 * 
	 * @param user
	 * @param session
	 */
	private void saveSessionUser(User user, HttpSession session) {
		UserActive userActive = new UserActive();
		Long userId = user.getUserId();
		List<Member> members = memberService.queryMemberByUserId(userId);
		userActive.setMember(null);
		// 单独存储老师信息
		for (Member member : members) {
			if (member.getMemberRole() == 1) {
				userActive.setMember(member);
			}
		}
		userActive.setUser(user);
		userActive.setMembers(members);
		userActive.setId(userId);
		userActive.setLoginName(user.getUserName());
		// 存储用户信息
		session.setAttribute(USER_SESSION, userActive);
	}
}

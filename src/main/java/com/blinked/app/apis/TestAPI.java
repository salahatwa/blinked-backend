package com.blinked.app.apis;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.blinked.model.FrontEndPersonalInformation;
import com.blinked.model.FrontEndUser;

@Controller
public class TestAPI {

	private String getSubdomain(HttpServletRequest req) {

//		String site = req.getServerName();
//
//		String domain = InternetDomainName.from(site).topPrivateDomain().toString();
//		String subDomain = site.replaceAll(domain, "");
//		subDomain = subDomain.substring(0, subDomain.length() - 1);

		String userId = req.getRequestURL().toString().split("[.]")[0].replace("http://", "");

		return userId;
	}

	@GetMapping("/")
	public String view(Model model, HttpServletRequest request, @RequestHeader("x-custom-referrer") String userId) {

		System.out.println("Before User Id:" + userId);
		if (Objects.isNull(userId))
			userId = getSubdomain(request);

		System.out.println("User Id:" + userId);
//		User user = userRepository.getUserIdFromuserUserUrl(url);
//		
//		user = templateService.setupUserAccordingToView(user);

		FrontEndUser fronEndUser = new FrontEndUser();
		fronEndUser.setName("Salah At");
		fronEndUser.setMail("ss@gmail.com");

		FrontEndPersonalInformation info = new FrontEndPersonalInformation();
		info.setName("HHHHHHHHHHH");
		info.setSummary("HSD SDSD SDH");
		info.setTitle("Senior dev");
		fronEndUser.setPersonalInformation(info);

		model.addAttribute("user", fronEndUser);

		return "template_folder/" + userId + "/index";
	}
}

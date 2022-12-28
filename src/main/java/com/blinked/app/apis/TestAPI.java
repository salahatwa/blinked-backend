package com.blinked.app.apis;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.blinked.modules.profile.dtos.Profile;
import com.blinked.modules.profile.entities.UserWebsiteUrl;
import com.blinked.modules.profile.repositories.UserRepository;
import com.blinked.modules.profile.repositories.UserWebsiteUrlRepository;
import com.blinked.modules.profile.services.ConvertToFrontEndUser;
import com.blinked.modules.user.entities.User;

@Controller
public class TestAPI {

	@Autowired
	UserWebsiteUrlRepository userWebsiteUrlRepository;
	
	@Autowired
	UserRepository userRepository;

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
	public String view(Model model, HttpServletRequest request,
			@RequestHeader(name = "x-custom-referrer", required = false) String siteId) throws SQLException, IOException {

		System.out.println("Before User Id:" + siteId);
		if (Objects.isNull(siteId))
			siteId = getSubdomain(request);

		System.out.println("Site Id:" + siteId);

		UserWebsiteUrl site = userWebsiteUrlRepository.getIdFromUrl(siteId);

		User user = userRepository.getReferenceById(site.getUserId());

		Profile profile=new ConvertToFrontEndUser().convertUserToProfile(user);
		//		user = templateService.setupUserAccordingToView(user);

//		FrontEndUser fronEndUser = new FrontEndUser();
//		fronEndUser.setName("Salah At");
//		fronEndUser.setMail("ss@gmail.com");
//
//		FrontEndPersonalInformation info = new FrontEndPersonalInformation();
//		info.setName("HHHHHHHHHHH");
//		info.setSummary("HSD SDSD SDH");
//		info.setTitle("Senior dev");
//		fronEndUser.setPersonalInformation(info);
//
		model.addAttribute("user", profile);

		return "template_folder/" + site.getTemplate().getLivePath() + "/index";
	}
}

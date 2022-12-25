package com.blinked.modules.profile.controllers;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.modules.profile.entities.OTP;
import com.blinked.modules.profile.repositories.OTPRepository;
import com.blinked.modules.profile.repositories.UserRepository;
import com.blinked.modules.profile.services.EmailVerificationService;
import com.blinked.modules.profile.services.OTPGenerator;
import com.blinked.modules.user.entities.User;

@RestController
public class SignupRestController {

	private EmailVerificationService emailVerificationService = new EmailVerificationService();
	
	private OTPGenerator otp = new OTPGenerator();
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OTPRepository oTPRepository;
	
	@PostMapping("/emailVerificationProces")
	public Boolean emailVerificationProces(@RequestBody String mail) {
		
		System.out.println(mail);
		
		OTP newOtp = otp.generateOTP(mail);
		
		System.out.println(newOtp.getOtp());
		
			try {
				emailVerificationService.sendmail(newOtp.getEmail(), javaMailSender, newOtp.getOtp());
			} catch (MailException | MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	
			oTPRepository.save(newOtp);
		
		return true;
	}
	
	@PostMapping("/checkIfMailExists")
	public Boolean checkIfMailExists(@RequestBody String mail) {
		
		System.out.println(mail);
	
		User user = userRepository.checkIfMailExists(mail);
		
		if(user!=null) {
			return true;
		}
		
		System.out.println(user);
		   
		return false;
	}
	
	@PostMapping("/verifyOtp")
	public OTP verifyOtp(@RequestBody String otpCode) {
		
		System.out.println(otpCode);
		OTP otp = oTPRepository.getOtp(otpCode);
		
		System.out.println(otp);
		
		System.out.println(oTPRepository.getOtp(otpCode).getEmail());
		
		if(otp!=null) {
			System.out.println(otp.getEmail());
			
			oTPRepository.deleteById(otp.getId());
			return otp;
		}
		
		return otp;
	}
}

package com.blinked.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blinked.model.OTP;

public interface OTPRepository extends JpaRepository<OTP, Long>{

	@Query("Select o from OTP o where o.otp=?1")
	OTP getOtp(String otpCode);

}

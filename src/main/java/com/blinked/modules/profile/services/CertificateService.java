package com.blinked.modules.profile.services;

import java.io.IOException;
import java.sql.SQLException;

import com.blinked.modules.profile.dtos.FrontEndCertificate;
import com.blinked.modules.profile.entities.Certification;

public class CertificateService {

	public Certification convertFrontEndCertificateToBackEndCertificate(FrontEndCertificate frontEndCertificate,
			Certification certification) throws IOException {

		certification.setId(frontEndCertificate.getId());
		certification.setTitle(frontEndCertificate.getTitle());
		certification.setInstituteName(frontEndCertificate.getInstituteName());
		certification.setMonth(frontEndCertificate.getMonth());
		certification.setYear(frontEndCertificate.getYear());
		certification.setAttachment(frontEndCertificate.getAttachment());
		certification.setTypeOfAttachment(frontEndCertificate.getTypeOfAttachment());
		certification.setUrl(frontEndCertificate.getUrl());
		// certification.setView( frontEndCertificate.getView() );

		return certification;
	}

	public FrontEndCertificate convertBackEndCertificateToFrontEndCertificate(Certification certification)
			throws IOException, SQLException {

		FrontEndCertificate frontEndCertificate = new FrontEndCertificate();

		frontEndCertificate.setId(certification.getId());
		frontEndCertificate.setTitle(certification.getTitle());
		frontEndCertificate.setInstituteName(certification.getInstituteName());
		frontEndCertificate.setMonth(certification.getMonth());
		frontEndCertificate.setYear(certification.getYear());
		frontEndCertificate.setAttachment(certification.getAttachment());
		frontEndCertificate.setTypeOfAttachment(certification.getTypeOfAttachment());
		frontEndCertificate.setUrl(certification.getUrl());
		frontEndCertificate.setView(certification.getView());

		return frontEndCertificate;
	}

}

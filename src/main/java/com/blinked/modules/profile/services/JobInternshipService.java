package com.blinked.modules.profile.services;

import java.io.IOException;
import java.sql.SQLException;

import com.blinked.modules.profile.dtos.FrontEndJobInternship;
import com.blinked.modules.profile.entities.JobInternship;

public class JobInternshipService {

	public JobInternship FrontEndJobInternshipToBackEndJobInternship(FrontEndJobInternship frontEndJobInternship,
			JobInternship jobInternship) throws IOException {

		jobInternship.setId(frontEndJobInternship.getId());
		jobInternship.setTitle(frontEndJobInternship.getTitle());
		jobInternship.setCompany(frontEndJobInternship.getCompany());
		jobInternship.setCountry(frontEndJobInternship.getCountry());
		jobInternship.setState(frontEndJobInternship.getState());
		jobInternship.setCity(frontEndJobInternship.getCity());
		jobInternship.setStartMonth(frontEndJobInternship.getStartMonth());
		jobInternship.setStartYear(frontEndJobInternship.getStartYear());
		jobInternship.setCurrentStatus(frontEndJobInternship.getCurrentStatus());
		jobInternship.setEndMonth(frontEndJobInternship.getEndMonth());
		jobInternship.setEndYear(frontEndJobInternship.getEndYear());
		jobInternship.setDescription(frontEndJobInternship.getDescription());
		jobInternship.setAttachment(frontEndJobInternship.getAttachment());
		jobInternship.setTypeOfAttachment(frontEndJobInternship.getTypeOfAttachment());
		// jobInternship.setView( frontEndJobInternship.getView());

		return jobInternship;

	}

	public FrontEndJobInternship BackEndJobInternshipToFrontEndJobInternship(JobInternship jobInternship)
			throws IOException, SQLException {

		FrontEndJobInternship frontEndobInternship = new FrontEndJobInternship();

		frontEndobInternship.setId(jobInternship.getId());
		frontEndobInternship.setTitle(jobInternship.getTitle());
		frontEndobInternship.setCompany(jobInternship.getCompany());
		frontEndobInternship.setCountry(jobInternship.getCountry());
		frontEndobInternship.setState(jobInternship.getState());
		frontEndobInternship.setCity(jobInternship.getCity());
		frontEndobInternship.setStartMonth(jobInternship.getStartMonth());
		frontEndobInternship.setStartYear(jobInternship.getStartYear());
		frontEndobInternship.setCurrentStatus(jobInternship.getCurrentStatus());
		frontEndobInternship.setEndMonth(jobInternship.getEndMonth());
		frontEndobInternship.setEndYear(jobInternship.getEndYear());
		frontEndobInternship.setDescription(jobInternship.getDescription());
		frontEndobInternship.setAttachment(jobInternship.getAttachment());
		frontEndobInternship.setTypeOfAttachment(jobInternship.getTypeOfAttachment());
		frontEndobInternship.setView(jobInternship.getView());

		return frontEndobInternship;

	}

}

package com.blinked.modules.profile.services;

import java.io.IOException;
import java.sql.SQLException;

import com.blinked.modules.profile.dtos.FrontEndCourse;
import com.blinked.modules.profile.entities.Course;

public class CourseService {

	public Course convertFrontEndCourseToBackEndCourse(FrontEndCourse frontEndCourse, Course course)
			throws IOException {

		course.setId(frontEndCourse.getId());
		course.setName(frontEndCourse.getName());
		course.setInstituteName(frontEndCourse.getInstituteName());
		course.setCompletionMonth(frontEndCourse.getCompletionMonth());
		course.setCompletionYear(frontEndCourse.getCompletionYear());
		course.setAttachment(frontEndCourse.getAttachment());
		course.setCurrentStatus(frontEndCourse.getCurrentStatus());
		course.setTypeOfAttachment(frontEndCourse.getTypeOfAttachment());
		course.setUrl(frontEndCourse.getUrl());
		// course.setView( frontEndCourse.getView() );

		return course;
	}

	public FrontEndCourse convertBackEndCourseToFrontEndCourse(Course course) throws IOException, SQLException {

		FrontEndCourse frontEndCourse = new FrontEndCourse();

		frontEndCourse.setId(course.getId());
		frontEndCourse.setName(course.getName());
		frontEndCourse.setInstituteName(course.getInstituteName());
		frontEndCourse.setCompletionMonth(course.getCompletionMonth());
		frontEndCourse.setCompletionYear(course.getCompletionYear());
		frontEndCourse.setAttachment(course.getAttachment());
		frontEndCourse.setCurrentStatus(course.getCurrentStatus());
		frontEndCourse.setTypeOfAttachment(course.getTypeOfAttachment());
		frontEndCourse.setUrl(course.getUrl());
		frontEndCourse.setView(course.getView());

		return frontEndCourse;
	}

}

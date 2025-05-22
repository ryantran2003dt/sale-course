package com.salecoursecms.service;

import com.salecoursecms.dto.reponse.BaseReponse;
import com.salecoursecms.dto.request.CreateCourseRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface CourseService {
    BaseReponse<?> createCourse(CreateCourseRequest req, HttpServletRequest httpServletRequest);
}

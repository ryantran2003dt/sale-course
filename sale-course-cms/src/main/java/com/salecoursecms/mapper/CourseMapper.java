package com.salecoursecms.mapper;

import com.salecoursecms.dto.reponse.CourseReponse;
import com.salecoursecms.dto.request.CreateCourseRequest;
import com.salecoursecms.entity.first.CourseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class CourseMapper {
    public CourseEntity toCreateCourse(CreateCourseRequest req) {
        CourseEntity course = new CourseEntity();
        course.setNameCourse(req.getNameCourse());
        course.setDescription(req.getDescription());
        course.setPrice(req.getPrice());
        course.setImage(req.getImage());
        course.setNumberOfSession(req.getNumberOfSession());
        course.setCreateDate(new Date());
        course.setCreateBy(req.getCreateBy());
        course.setTeacherId(req.getTeacherId());
        course.setStatus(req.getStatus());
        return course;
    }
    public CourseReponse toReponse(CourseEntity course) {
        CourseReponse courseReponse = new CourseReponse();
        courseReponse.setId(course.getId());
        courseReponse.setNameCourse(course.getNameCourse());
        courseReponse.setDescription(course.getDescription());
        courseReponse.setPrice(course.getPrice());
        courseReponse.setImage(course.getImage());
        courseReponse.setNumberOfSession(course.getNumberOfSession());
        courseReponse.setCreateDate(course.getCreateDate());
        courseReponse.setCreateBy(course.getCreateBy());
        courseReponse.setTeacherId(course.getTeacherId());
        courseReponse.setStatus(course.getStatus());
        return courseReponse;
    }
}

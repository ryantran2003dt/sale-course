package com.salecoursecms.controller;

import com.salecoursecms.constant.UrlConst;
import com.salecoursecms.dto.request.CreateCourseRequest;
import com.salecoursecms.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(UrlConst.COURSE)
public class CourseController {
    private final CourseService courseService;

    @PostMapping(UrlConst.CREATE)
    public ResponseEntity<?> createCourse(@RequestBody CreateCourseRequest req, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(courseService.createCourse(req, httpServletRequest));
    }
}

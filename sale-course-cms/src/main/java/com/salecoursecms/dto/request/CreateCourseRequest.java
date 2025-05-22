package com.salecoursecms.dto.request;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateCourseRequest {
    private String nameCourse;
    private String description;
    private float price;
    private String image;
    private int numberOfSession;
    private Long createBy;
    private Long teacherId;
    private int status = 0; // 0 init, 1 approve, 2 inject, 3 delete
}

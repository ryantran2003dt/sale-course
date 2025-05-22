package com.salecoursecms.service.impl;

import com.salecoursecms.config.security.service.JwtService;
import com.salecoursecms.constant.AppConst;
import com.salecoursecms.constant.MessageConst;
import com.salecoursecms.constant.VariableConst;
import com.salecoursecms.dto.reponse.BaseReponse;
import com.salecoursecms.dto.reponse.CourseReponse;
import com.salecoursecms.dto.request.CreateCourseRequest;
import com.salecoursecms.entity.first.CourseEntity;
import com.salecoursecms.exception.ResourceNotFoundException;
import com.salecoursecms.mapper.CourseMapper;
import com.salecoursecms.repository.first.CourseRepository;
import com.salecoursecms.repository.first.UserRepository;
import com.salecoursecms.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Override
    public BaseReponse<?> createCourse(CreateCourseRequest req, HttpServletRequest httpServletRequest) {
        try{
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            String accessToken = authorizationHeader.substring(7);
            Long createBy = jwtService.extractIdFromToken(accessToken);
            boolean existTeacherId = userRepository.existsById(req.getTeacherId());
            if(createBy == null || !existTeacherId) {
                throw new ResourceNotFoundException(MessageConst.ACCOUNT_NOT_FOUND);
            }else {
                req.setCreateBy(createBy);
            }
            CourseEntity courseEntity = courseMapper.toCreateCourse(req);
            courseRepository.save(courseEntity);
            CourseReponse reponse = courseMapper.toReponse(courseEntity);
            return new BaseReponse<>(AppConst.STATUS_SUCCESS,false,messageSource.getMessage(MessageConst.CREATE_SUCCESS, null,new Locale(VariableConst.LAN)),reponse);
        } catch (Exception e) {
            log.error("[ERRRO] "+e.getMessage());
            return new BaseReponse<>(AppConst.STATUS_FAIL,false,messageSource.getMessage(MessageConst.CREATE_FAIL, null,new Locale(VariableConst.LAN)),null);
        }

    }

}

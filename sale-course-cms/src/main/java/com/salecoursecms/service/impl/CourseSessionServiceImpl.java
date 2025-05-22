package com.salecoursecms.service.impl;

import com.salecoursecms.constant.MessageConst;
import com.salecoursecms.dto.reponse.CourseSessionReponse;
import com.salecoursecms.entity.first.CourseEntity;
import com.salecoursecms.entity.first.CourseSessionEntity;
import com.salecoursecms.exception.SystemErrorException;
import com.salecoursecms.mapper.CourseSessionMapper;
import com.salecoursecms.repository.first.CourseSessionRepository;
import com.salecoursecms.service.CourseSessionService;
import com.salecoursecms.utils.CourseScheduleUtils;
import com.salecoursecms.utils.VietnamHolidayUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class CourseSessionServiceImpl implements CourseSessionService {
    private final CourseSessionMapper courseSessionMapper;
    private final CourseSessionRepository courseSessionRepository;

    @Override
    public List<CourseSessionReponse> generatorCourseSession(CourseEntity courseEntity) {
        try{
            LocalDate current = courseEntity.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            List<CourseSessionEntity> courseSessionList = new ArrayList<>();

            String sessionType = courseEntity.getSessionType() == 1 ? "ONLINE" : "OFFLINE";

            List<String> days = CourseScheduleUtils.splitDays(courseEntity.getDaysOfWeek());

            List<DayOfWeek> studyDays = days.stream()
                    .map(day -> DayOfWeek.valueOf(day.toUpperCase()))
                    .collect(Collectors.toList());

            Set<LocalDate> holidays = VietnamHolidayUtils.getVietnamHolidays(current.getYear());

            int sessionCount = 0;
            while (sessionCount < courseEntity.getNumberOfSession()) {
                if (!studyDays.contains(current.getDayOfWeek()) || holidays.contains(current)) {
                    current = current.plusDays(1);
                    continue;
                }

                if (current.getMonthValue() == 1 && current.getDayOfMonth() == 1) {
                    holidays = VietnamHolidayUtils.getVietnamHolidays(current.getYear());
                }

                CourseSessionEntity session = new CourseSessionEntity();
                session.setSessionDate(java.sql.Date.valueOf(current));
                session.setCourseId(courseEntity.getId());
                session.setCreateDate(new Date());
                session.setStatus(1);

                session.setTitle("Session " + (sessionCount + 1));
                session.setDescription("");
                session.setLocation("");
                session.setSessionType(sessionType);

                courseSessionList.add(session);
                sessionCount++;

                current = current.plusDays(1);
            }

            courseSessionRepository.saveAll(courseSessionList);
            List<CourseSessionReponse> reponseList = courseSessionMapper.toReponseList(courseSessionList);
            return reponseList;
        }catch (Exception e){
            log.error("[ERROR] "+e.getMessage());
            throw new SystemErrorException(MessageConst.INTERNAL_SERVER_ERROR);
        }
    }
}

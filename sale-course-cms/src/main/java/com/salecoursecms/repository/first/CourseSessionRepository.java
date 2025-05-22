package com.salecoursecms.repository.first;

import com.salecoursecms.entity.first.CourseSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseSessionRepository extends JpaRepository<CourseSessionEntity, Long> {
    @Query(value = "DELETE FROM COURSE_SESSION WHERE COURSE_ID = :courseId", nativeQuery = true)
    void deleteCourseSessionByCourseId(@Param("courseId") Long courseId);

}

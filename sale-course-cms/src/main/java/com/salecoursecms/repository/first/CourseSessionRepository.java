package com.salecoursecms.repository.first;

import com.salecoursecms.entity.first.CourseSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseSessionRepository extends JpaRepository<CourseSessionEntity, Long> {
}

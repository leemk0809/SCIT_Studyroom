package net.scit.studyroom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.scit.studyroom.domain.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
}

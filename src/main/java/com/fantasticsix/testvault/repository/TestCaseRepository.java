package com.fantasticsix.testvault.repository;

import com.fantasticsix.testvault.dto.StatusDateCountDTO;
import com.fantasticsix.testvault.model.Attachment;
import com.fantasticsix.testvault.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.fantasticsix.testvault.model.Module;

import java.util.List;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    List<TestCase> getTestCasesByModule(Module module);
    @Query(value = "SELECT status, DATE_FORMAT(creation_date, '%Y-%m-%d') AS creationDate, COUNT(*) AS count " +
            "FROM test_case GROUP BY status, DATE_FORMAT(creation_date, '%Y-%m-%d')", nativeQuery = true)
    List<Object[]> countByStatusAndCreationDate();


    @Query("SELECT tc.priority, COUNT(tc) FROM TestCase tc GROUP BY tc.priority")
    List<Object[]> countByPriority();
}

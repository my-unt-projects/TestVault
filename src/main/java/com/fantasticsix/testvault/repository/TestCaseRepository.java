package com.fantasticsix.testvault.repository;

import com.fantasticsix.testvault.dto.StatusDateCountDTO;
import com.fantasticsix.testvault.model.Attachment;
import com.fantasticsix.testvault.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.fantasticsix.testvault.model.Module;

import java.util.Date;
import java.util.List;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long>, JpaSpecificationExecutor<TestCase> {
    List<TestCase> getTestCasesByModule(Module module);
    @Query(value = "SELECT status, TO_CHAR(creation_date, 'YYYY-MM-DD') AS creationDate, COUNT(*) AS count " +
            "FROM test_case GROUP BY status, TO_CHAR(creation_date, 'YYYY-MM-DD')", nativeQuery = true)
    List<Object[]> countByStatusAndCreationDate();


    @Query("SELECT tc.priority, COUNT(tc) FROM TestCase tc GROUP BY tc.priority")
    List<Object[]> countByPriority();

    @Query("SELECT p.projectName, COUNT(t) FROM TestCase t JOIN t.project p GROUP BY p.projectName")
    List<Object[]> countTestCasesByProject();

    Long countByCreationDate(Date creationDate);

    @Query("SELECT u.name, COUNT(t) FROM TestCase t JOIN t.assignedTo u GROUP BY u.name")
    List<Object[]> countTestCasesPerUser();

    List<TestCase> findByDueDateBetween(Date start, Date end);

    @Query("SELECT t.status, t.priority, COUNT(t) FROM TestCase t GROUP BY t.status, t.priority")
    List<Object[]> countByStatusAndPriority();
}

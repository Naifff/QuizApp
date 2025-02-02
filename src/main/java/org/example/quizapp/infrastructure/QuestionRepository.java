// Infrastructure Layer
package org.example.quizapp.infrastructure;

import org.example.quizapp.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;



@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	@Query("SELECT q FROM Question q LEFT JOIN FETCH q.options")
	List<Question> findAllWithOptions();
}


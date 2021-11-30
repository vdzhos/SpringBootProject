package com.sbproject.schedule;

import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.repositories.SubjectRepository;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
@DataJpaTest
public class JPAUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    public void should_find_no_subjects_if_repository_is_empty() {
        Iterable<Subject> subjects = subjectRepository.findAll();
        assertThat(subjects).isEmpty();
    }

    @Test
    public void should_store_a_subject() {
        Subject subject = subjectRepository.save(new Subject("Subject 1", 3));

        assertThat(subject).hasFieldOrPropertyWithValue("name", "Subject 1");
        assertThat(subject).hasFieldOrPropertyWithValue("quantOfGroups", 3);
    }

    @Test
    public void should_find_all_subjects() {
        Subject s1 = new Subject("Subject 1", 3);
        entityManager.persist(s1);

        Subject s2 = new Subject("Subject 2", 4);
        entityManager.persist(s2);

        Iterable<Subject> tutorials = subjectRepository.findAll();
        assertThat(tutorials).hasSize(2).contains(s1, s2);
    }

    @Test
    public void should_find_subject_by_id() {
        Subject s1 = new Subject("Subject 1", 3);
        entityManager.persist(s1);

        Subject s2 = new Subject("Subject 2", 4);
        entityManager.persist(s2);

        Subject foundSubject = subjectRepository.findById(s2.getId()).get();
        assertThat(foundSubject).isEqualTo(s2);
    }

    @Test
    public void should_delete_subject_by_id() {
        Subject s1 = new Subject("Subject 1", 3);
        entityManager.persist(s1);

        Subject s2 = new Subject("Subject 2", 4);
        entityManager.persist(s2);

        subjectRepository.deleteById(s2.getId());

        Iterable<Subject> subjects = subjectRepository.findAll();
        assertThat(subjects).hasSize(1).contains(s1);
    }

}

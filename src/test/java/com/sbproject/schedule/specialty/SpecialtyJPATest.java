package com.sbproject.schedule.specialty;


import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//@RunWith(SpringRunner.class)
@DataJpaTest
//@SpringBootTest
//@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class))
public class SpecialtyJPATest {


    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Test
    public void givenSpecialtyObject_saveInDB_thenSpecialtyObject() {
        Specialty s = new Specialty("New Specialty", 1);
        Specialty s1 = specialtyRepository.save(s);

        Assertions.assertEquals(s.getName(), s1.getName());
        Assertions.assertEquals(s.getYear(), s1.getYear());
    }

    @Test
    public void givenNameAndYear_findSpecialties_thenNotEmpty() {
        Specialty s = new Specialty("New Specialty", 1);
        specialtyRepository.save(s);
        Iterable<Specialty> ss = specialtyRepository.findByNameAndYear("New Specialty", 1);
        Assertions.assertTrue(ss.iterator().hasNext());
    }

    @Test
    public void givenIncorrectNameAndYear_findSpecialties_thenNotFound() {
        Specialty s = new Specialty("New Specialty", 2);
        specialtyRepository.save(s);
        Assertions.assertFalse(specialtyRepository.existsByNameAndYear("New Specialty", 1));
    }

    @Test
    public void givenCorrectNameAndYear_findSpecialties_thenFound() {
        Specialty s = new Specialty("New Specialty", 2);
        specialtyRepository.save(s);
        Assertions.assertTrue(specialtyRepository.existsByNameAndYear("New Specialty", 2));
    }


//    @Test(expected = SpecialtyIllegalArgumentException.class)
//    public void givenIncorrectSpecialtyObject_saveInDB_thenException() {
//        Specialty s = new Specialty("New Specialty",10);
//        specialtyRepository.save(s);
//    }


//    @Autowired
//    private SpecialtyService specialtyService;
//
//    @Test
//    public void givenSpecialtyObject_saveInDB_thenSpecialtyObject() {
//        Specialty s = new Specialty("New Specialty",1);
//        Specialty s1 = specialtyService.addSpecialty(s.getName(),s.getYear());
//
//        Assertions.assertEquals(s.getName(),s1.getName());
//        Assertions.assertEquals(s.getYear(),s1.getYear());
//    }
//
//    @Test(expected = SpecialtyIllegalArgumentException.class)
//    public void givenIncorrectSpecialtyObject_saveInDB_thenException() {
//        Specialty s = new Specialty("New Specialty",10);
//        specialtyService.addSpecialty(s.getName(),s.getYear());
//    }


}

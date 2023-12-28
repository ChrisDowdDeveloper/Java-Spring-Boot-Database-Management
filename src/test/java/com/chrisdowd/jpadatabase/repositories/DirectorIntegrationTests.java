package com.chrisdowd.jpadatabase.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import com.chrisdowd.jpadatabase.TestDataUtil;
import com.chrisdowd.jpadatabase.domain.Director;
import com.chrisdowd.jpadatabase.domain.Movie;

import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class DirectorIntegrationTests {
     
    private DirectorRepository underTest;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    public void DirectorDaoImplIntegrationTests(DirectorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatDirectorCanBeCreatedAndRecalled() {
        Director director = TestDataUtil.createTestDirectorA();
        underTest.save(director);
        Optional<Director> result = underTest.findById(director.getId());
        assertTrue(result.isPresent());
        assertEquals(director, result.get());
    }

    @Test
    public void testThatMultipleDirectorsCanBeCreatedAndRecalled() {
        Director directorA = TestDataUtil.createTestDirectorA();
        underTest.save(directorA);


        Director directorB = TestDataUtil.createTestDirectorB();
        underTest.save(directorB);


        Director directorC = TestDataUtil.createTestDirectorC();
        underTest.save(directorC);

    
        Iterable<Director> resultIterable = underTest.findAll();
        List<Director> resultList = new ArrayList<>();
        resultIterable.forEach(resultList::add);
    
        assertEquals(3, resultList.size(), "The number of retrieved directors should be 3.");
    
        List<Director> expectedDirectors = Arrays.asList(directorA, directorB, directorC);

        assertEquals(expectedDirectors, resultList, "The retrieved directors list should match the expected list.");
    }
    

    @Test
    public void testThatUpdateChangesDirectors() {
        Director directorA = TestDataUtil.createTestDirectorA();
        directorA.setName("UPDATED");
        underTest.save(directorA);
        Optional<Director> result = underTest.findById(directorA.getId());
        assertTrue(result.isPresent());
        assertEquals(directorA, result.get());
    }

    @Test
    public void testThatDeleteRemovesDirector() {
        Director directorA = TestDataUtil.createTestDirectorA();
        underTest.save(directorA);
        Movie movieA = TestDataUtil.createTestMovieA(directorA);
        movieRepository.save(movieA);

        List<Movie> movies = movieRepository.findByDirectorId(directorA.getId());
        movieRepository.deleteAll(movies);

        underTest.deleteById(directorA.getId());
        Optional<Director> result = underTest.findById(directorA.getId());
        assertTrue(result.isEmpty());
    }

    @Test
    public void testThatGetDirectorsWithAgeLessThan() {
        Director directorA = TestDataUtil.createTestDirectorA();
        underTest.save(directorA);
        Director directorB = TestDataUtil.createTestDirectorB();
        underTest.save(directorB);
        Director directorC = TestDataUtil.createTestDirectorC();
        underTest.save(directorC);

        Iterable<Director> result = underTest.ageLessThan(50);
        List<Director> expected = new ArrayList<>();
        expected = Arrays.asList(directorB, directorC);
        assertEquals(expected, result);
    }

    @Test
    public void testThatGetDirectorsWithAgeGreaterThan() {
        Director directorA = TestDataUtil.createTestDirectorA();
        underTest.save(directorA);
        Director directorB = TestDataUtil.createTestDirectorB();
        underTest.save(directorB);
        Director directorC = TestDataUtil.createTestDirectorC();
        underTest.save(directorC);

        Iterable<Director> result = underTest.findDirectorsWithAgeGreaterThan(50);
        List<Director> expected = new ArrayList<>();
        expected = Arrays.asList(directorA);
        assertEquals(expected, result);
    }
}
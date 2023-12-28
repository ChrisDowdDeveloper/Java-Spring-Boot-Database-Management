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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.chrisdowd.jpadatabase.TestDataUtil;
import com.chrisdowd.jpadatabase.domain.Director;
import com.chrisdowd.jpadatabase.domain.Movie;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MovieIntegrationTests {

    private MovieRepository underTest;

    @Autowired
    public void MovieDaoImplIntegrationTests(MovieRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatMovieCanBeCreatedAndRecalled() {
        Director director = TestDataUtil.createTestDirectorA();
        Movie movie = TestDataUtil.createTestMovieA(director);
        underTest.save(movie);
        Optional<Movie> result = underTest.findById(movie.getIsan());
        assertTrue(result.isPresent());
        assertEquals(movie, result.get());
    }

    @Test
    public void testThatManyMoviesCanBeCreatedAndRecalled() {

        Director directorA = TestDataUtil.createTestDirectorA();
        Movie movieA = TestDataUtil.createTestMovieA(directorA);
        underTest.save(movieA);

        Director directorB = TestDataUtil.createTestDirectorB();
        Movie movieB = TestDataUtil.createTestMovieB(directorB);
        underTest.save(movieB);

        Director directorC = TestDataUtil.createTestDirectorC();
        Movie movieC = TestDataUtil.createTestMovieC(directorC);
        underTest.save(movieC);

        Iterable<Movie> result = underTest.findAll();
        List<Movie> resultList = new ArrayList<>();
        result.forEach(resultList::add);

        assertEquals(3, resultList.size());
        List<Movie> expectedMovies = Arrays.asList(movieA, movieB, movieC);
        assertEquals(expectedMovies, resultList);
    }

    @Test
    public void testThatMovieCanBeUpdated() {
        Director directorA = TestDataUtil.createTestDirectorA();
        Movie movieA = TestDataUtil.createTestMovieA(directorA);
        underTest.save(movieA);

        movieA.setTitle("UPDATED");
        underTest.save(movieA);
        Optional<Movie> result = underTest.findById(movieA.getIsan());
        assertTrue(result.isPresent());
        assertEquals(movieA, result.get());
    }

    @Test
    public void testThatMovieCanBeDeleted() {
        Director directorA = TestDataUtil.createTestDirectorA();
        Movie movieA = TestDataUtil.createTestMovieA(directorA);
        underTest.save(movieA);
        underTest.deleteById(movieA.getIsan());
        Optional<Movie> result = underTest.findById(movieA.getIsan());
        assertTrue(result.isEmpty());
    }
}

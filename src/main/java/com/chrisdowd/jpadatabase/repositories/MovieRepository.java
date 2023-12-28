package com.chrisdowd.jpadatabase.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chrisdowd.jpadatabase.domain.Movie;

@Repository
public interface MovieRepository extends CrudRepository<Movie, String>{
    List<Movie> findByDirectorId(Long directorId);
}

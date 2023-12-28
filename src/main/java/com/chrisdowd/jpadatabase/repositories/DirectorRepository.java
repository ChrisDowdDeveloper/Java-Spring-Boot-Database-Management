package com.chrisdowd.jpadatabase.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chrisdowd.jpadatabase.domain.Director;

@Repository
public interface DirectorRepository extends CrudRepository<Director, Long>{

    Iterable<Director> ageLessThan(int age);

    @Query("SELECT d FROM Director d where d.age > ?1")
    Iterable<Director> findDirectorsWithAgeGreaterThan(int age);
    
}

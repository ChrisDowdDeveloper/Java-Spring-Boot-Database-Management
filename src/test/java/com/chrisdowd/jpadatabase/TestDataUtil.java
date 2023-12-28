package com.chrisdowd.jpadatabase;

import com.chrisdowd.jpadatabase.domain.Director;
import com.chrisdowd.jpadatabase.domain.Movie;

public final class TestDataUtil {
    
    private TestDataUtil(){
    }

    public static Director createTestDirectorA() {
        return Director.builder()
            .name("John Smith")
            .age(60)
            .build();
    }

    public static Director createTestDirectorB() {
        return Director.builder()
            .name("Lisa Johnson")
            .age(45)
            .build();
    }

    public static Director createTestDirectorC() {
        return Director.builder()
            .name("Michael Brown")
            .age(24)
            .build();
    }

    public static Movie createTestMovieA(final Director director) {
        return Movie.builder()
            .isan("0000-0000-0000-1-0000-0000-1")
            .title("The Mystery of Shadows")
            .director(director) // Use Director object
            .build();
    }

    public static Movie createTestMovieB(final Director director) {
        return Movie.builder()
            .isan("0000-0000-0000-4-0000-0000-4")
            .title("The Enchanted Garden")
            .director(director) // Use Director object
            .build();
    }

    public static Movie createTestMovieC(final Director director) {
        return Movie.builder()
            .isan("0000-0000-0000-7-0000-0000-7")
            .title("In Pursuit of Dreams")
            .director(director) // Use Director object
            .build();
    }
}

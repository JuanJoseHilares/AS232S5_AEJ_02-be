package pe.edu.vallegrande.myproject.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pe.edu.vallegrande.myproject.model.Movie;
import reactor.core.publisher.Mono;

public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {

    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    Mono<Movie> findByName(String name);
}

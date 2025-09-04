package pe.edu.vallegrande.myproject.rest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.vallegrande.myproject.model.Movie;
import pe.edu.vallegrande.myproject.model.MovieFullDTO;
import pe.edu.vallegrande.myproject.model.DisneyApiResponse;
import pe.edu.vallegrande.myproject.service.MovieService;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/api/RapidAPI")
public class RestMovie {

    private final MovieService movieService;
    private final WebClient webClient;

    public RestMovie(MovieService movieService,
                     @Qualifier("disneyWebClient") WebClient rapidApiWebClient) {
        this.movieService = movieService;
        this.webClient = rapidApiWebClient;
    }

    @GetMapping("/Disney/GetAll")
    public Flux<Movie> getAllMovies() {
        return movieService.findAll();
    }

    @GetMapping("/Disney/search/{name}")
    public Mono<Map<String, String>> getMovieByName(@PathVariable String name) {
        return movieService.findByName(name)
                .map(movie -> Map.of(
                        "id", movie.getId(),
                        "name", movie.getName()
                ))
                .switchIfEmpty(Mono.just(Map.of("message", "No se encontró la película en MongoDB")));
    }

    // Guardar nueva película
    @PostMapping("/Disney/save")
    public Mono<Movie> createMovie(@RequestBody Movie movie) {
        return movieService.save(movie);
    }

    @GetMapping("/Disney/searchFull/{name}")
    public Mono<Object> getMovieFullByName(@PathVariable String name) {
        return movieService.findByName(name)
                .flatMap(movie -> getMoviesFromAPI(movie.getName())
                        .map(apiData -> {
                            MovieFullDTO dto = new MovieFullDTO();
                            dto.setName(apiData.getName());
                            dto.setDescription(apiData.getDescription());
                            dto.setRelease_year(apiData.getRelease_year());
                            dto.setRuntime(apiData.getRuntime());
                            dto.setGenres(apiData.getGenres());
                            dto.setProduction_countries(apiData.getProduction_countries());
                            return dto;
                        })
                        .collectList()
                        .cast(Object.class)
                )
                .switchIfEmpty(Mono.just(Map.of("message", "No se encontró la película en MongoDB")));
    }

    private Flux<MovieFullDTO> getMoviesFromAPI(String name) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v1/disney-plus-top")
                        .queryParam("name", name)
                        .build())
                .retrieve()
                .bodyToMono(DisneyApiResponse.class)
                .flatMapMany(resp -> {
                    if (resp.getItems() != null && !resp.getItems().isEmpty()) {
                        return Flux.fromIterable(resp.getItems())
                                .map(item -> {
                                    MovieFullDTO dto = new MovieFullDTO();
                                    dto.setName(item.getTitle());
                                    dto.setDescription(item.getDescription());
                                    dto.setRelease_year(item.getRelease_year());
                                    dto.setRuntime(item.getRuntime());
                                    dto.setGenres(item.getGenres().toArray(new String[0]));
                                    dto.setProduction_countries(item.getProduction_countries().toArray(new String[0]));
                                    return dto;
                                });
                    }
                    return Flux.empty();
                })
                .onErrorResume(e -> Flux.empty());
    }
}

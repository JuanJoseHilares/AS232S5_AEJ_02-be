package pe.edu.vallegrande.myproject.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.myproject.model.Netflix;
import pe.edu.vallegrande.myproject.service.NetflixLanguageService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/api/netflix/languages")
public class RestNetflixLanguage {

    private final NetflixLanguageService languageService;
    private final WebClient netflixWebClient;

    public RestNetflixLanguage(NetflixLanguageService languageService,
                               WebClient netflixWebClient) {
        this.languageService = languageService;
        this.netflixWebClient = netflixWebClient;
    }

    @GetMapping("/db/GetAll")
    public Flux<Netflix> getAllFromDb() {
        return languageService.findAll();
    }


    
    @GetMapping("/db/{code}")
    public Mono<Object> getByCode(@PathVariable String code) {
        return languageService.findByCode(code)
                .cast(Object.class)
                .switchIfEmpty(Mono.just(Map.of("message", "No se encontró el idioma en MongoDB")));
    }
    // 4. 🔹 Eliminar idioma por código en MongoDB
    @DeleteMapping("/db/delete/{code}")
    public Mono<Map<String, String>> deleteLanguage(@PathVariable String code) {
        return languageService.findByCode(code)
                .flatMap(language -> languageService.deleteById(language.getCode())
                        .then(Mono.just(Map.of("message", "Idioma eliminado con éxito"))))
                .switchIfEmpty(Mono.just(Map.of("message", "No se encontró el idioma en MongoDB")));
    }


    // 5. 🔹 Obtener idiomas directamente del API
    @GetMapping("/api")
    public Flux<Netflix> getFromApi() {
        return netflixWebClient.get()
                .uri("/languages")
                .retrieve()
                .bodyToFlux(Netflix.class);
    }

    @PostMapping("/db/save")
    public Mono<Netflix> saveFromApi(@RequestParam String code,
                                     @RequestParam String name) {
        Netflix language = new Netflix(code, name);
        return languageService.save(language);
    }
}

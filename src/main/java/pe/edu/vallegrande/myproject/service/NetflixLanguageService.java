package pe.edu.vallegrande.myproject.service;

import pe.edu.vallegrande.myproject.model.Netflix;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NetflixLanguageService {
    Flux<Netflix> findAll();
    Mono<Netflix> save(Netflix language);
    Mono<Netflix> findByCode(String code);
    Mono<Void> deleteById(String id);
}

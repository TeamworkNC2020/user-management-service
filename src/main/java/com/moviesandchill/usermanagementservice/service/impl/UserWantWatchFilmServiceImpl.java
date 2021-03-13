package com.moviesandchill.usermanagementservice.service.impl;

import com.moviesandchill.usermanagementservice.dto.film.FilmDto;
import com.moviesandchill.usermanagementservice.entity.Film;
import com.moviesandchill.usermanagementservice.entity.User;
import com.moviesandchill.usermanagementservice.exception.film.FilmNotFoundException;
import com.moviesandchill.usermanagementservice.exception.user.UserNotFoundException;
import com.moviesandchill.usermanagementservice.mapper.FilmMapper;
import com.moviesandchill.usermanagementservice.repository.FilmRepository;
import com.moviesandchill.usermanagementservice.repository.UserRepository;
import com.moviesandchill.usermanagementservice.service.UserWantWatchFilmService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserWantWatchFilmServiceImpl implements UserWantWatchFilmService {

    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;

    public UserWantWatchFilmServiceImpl(UserRepository userRepository, FilmRepository filmRepository, FilmMapper filmMapper) {
        this.userRepository = userRepository;
        this.filmRepository = filmRepository;
        this.filmMapper = filmMapper;
    }

    @Override
    public List<FilmDto> getAllWantWatchFilms(long userId) throws UserNotFoundException {
        User user = findUserById(userId);
        var films = new ArrayList<>(user.getWantWatchFilms());
        return filmMapper.mapToDto(films);
    }

    @Override
    public void addWantWatchFilm(long userId, long filmId) throws UserNotFoundException {
        User user = findUserById(userId);
        Film film = filmMapper.mapToEntity(filmId);
        film = filmRepository.save(film);
        user.getWantWatchFilms().add(film);
    }

    @Override
    public void deleteAllWantWatchFilms(long userId) throws UserNotFoundException {
        User user = findUserById(userId);
        user.getWantWatchFilms().clear();
    }

    @Override
    public void deleteWantWatchFilm(long userId, long filmId) throws UserNotFoundException, FilmNotFoundException {
        User user = findUserById(userId);
        Film film = findFilmById(filmId);

        var films = user.getWantWatchFilms();

        if (!films.contains(film)) {
            throw new FilmNotFoundException(filmId);
        }
        user.getWantWatchFilms().remove(film);
    }

    private User findUserById(long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    private Film findFilmById(long filmId) throws FilmNotFoundException {
        return filmRepository.findById(filmId).orElseThrow(FilmNotFoundException::new);
    }
}

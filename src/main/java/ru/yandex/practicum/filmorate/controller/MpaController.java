package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.MpaService;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
public class MpaController {

    @Autowired
    MpaService mpaService;

    @GetMapping("{id}")
    public MpaRating findById(@NotNull @PathVariable Integer id) {
        return mpaService.findById(id);
    }

    @GetMapping()
    public List<MpaRating> findAll() {
        return mpaService.findAll();
    }

}

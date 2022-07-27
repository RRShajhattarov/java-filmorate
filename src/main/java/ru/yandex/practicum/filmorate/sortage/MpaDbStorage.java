package ru.yandex.practicum.filmorate.sortage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;

@Slf4j
@Service
public class MpaDbStorage {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public MpaRating findById(int id) {
        List<MpaRating> mpaList = jdbcTemplate.query("select mpa_id, mpa_rating from mpa_rating where mpa_id = ?",
                (rs, rowNum) -> {
                    MpaRating mpa = new MpaRating();
                    mpa.setId(rs.getInt("MPA_ID"));
                    mpa.setName(rs.getString("MPA_RATING"));
                    return mpa;
                }, id);
        return mpaList.size() != 1 ? new MpaRating() : mpaList.get(0);
    }

    public List<MpaRating> findAll() {
        List<MpaRating> mpaRatingList = jdbcTemplate.query("select mpa_id, mpa_rating from mpa_rating",
                (rs, rowNum) -> {
                    MpaRating mpa = new MpaRating();
                    mpa.setId(rs.getInt("MPA_ID"));
                    mpa.setName(rs.getString("MPA_RATING"));
                    return mpa;
                });
        return mpaRatingList;
    }
}

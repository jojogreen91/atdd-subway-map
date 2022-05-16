package wooteco.subway.repository.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import wooteco.subway.repository.entity.StationEntity;

@Repository
public class JdbcStationDao implements StationDao {

    private static final RowMapper<StationEntity> ROW_MAPPER = (resultSet, rowNum) -> new StationEntity(
            resultSet.getLong("id"),
            resultSet.getString("name")
    );

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcStationDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public StationEntity save(final StationEntity stationEntity) {
        final String sql = "insert into STATION (name) values (:name)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final SqlParameterSource source = new BeanPropertySqlParameterSource(stationEntity);
        jdbcTemplate.update(sql, source, keyHolder);
        final long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return stationEntity.fillId(id);
    }

    @Override
    public List<StationEntity> findAll() {
        final String sql = "select id, name from STATION";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    @Override
    public StationEntity findById(final Long id) {
        final String sql = "select id, name from STATION where id = :id";
        final Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        final SqlParameterSource source = new MapSqlParameterSource(params);
        return jdbcTemplate.queryForObject(sql, source, ROW_MAPPER);
    }

    @Override
    public void deleteById(final Long id) {
        final String sql = "delete from STATION where id = :id";
        final Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        final SqlParameterSource source = new MapSqlParameterSource(params);
        jdbcTemplate.update(sql, source);
    }
}

package io.pivotal.pal.tracker;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public TimeEntry create(TimeEntry entry) {
//        String sql = "insert into time_entries(project_id, user_id, date, hours) values(?,?,?,?)";
//
//        int newId = jdbcTemplate.update(sql, entry.getProjectId(), entry.getUserId(), entry.getDate(), entry.getHours());
//        entry.setId(new Long(newId));
//
//
//        return entry;

        final PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
                final PreparedStatement ps = connection.prepareStatement("insert into time_entries(project_id, user_id, date, hours) values(?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                //ps.setString(1, name);
                ps.setLong(1, entry.getProjectId());
                ps.setLong(2, entry.getUserId());
                ps.setDate(3, Date.valueOf(entry.getDate()));
                ps.setInt(4, entry.getHours());

                return ps;
            }
        };

        // The newly generated key will be saved in this object
        final KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(psc, holder);

        final long newEntryId = holder.getKey().longValue();

        entry.setId(newEntryId);
        return entry;


    }

    @Override
    public TimeEntry find(Long id) {
        String sql = "select id, project_id, user_id, date, hours from time_entries where id=?";

        try {
            TimeEntry entry = jdbcTemplate.queryForObject(sql, new RowMapper<TimeEntry>() {
                public TimeEntry mapRow(ResultSet rs,
                                        int rowNum)
                        throws SQLException {


                    LocalDate localDate = rs.getDate("date").toLocalDate();
                    TimeEntry entry = new TimeEntry(rs.getLong("id"),
                            rs.getLong("project_id"),
                            rs.getLong("user_id"),
                            localDate,
                            rs.getInt("hours"));


                    return entry;


                }


            }, id);

            return entry;
        } catch( IncorrectResultSizeDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<TimeEntry> list() {
        String sql = "select id, project_id, user_id, date, hours from time_entries";

        List<TimeEntry> lstResults = jdbcTemplate.query(sql, new RowMapper<TimeEntry>() {
            public TimeEntry mapRow(ResultSet rs,
                                    int rowNum)
                    throws SQLException {


                LocalDate localDate = rs.getDate("date").toLocalDate();
                TimeEntry entry = new TimeEntry(rs.getLong("id"),
                        rs.getLong("project_id"),
                        rs.getLong("user_id"),
                        localDate,
                        rs.getInt("hours"));


                return entry;


            }


        });

        return lstResults;
    }

    @Override
    public TimeEntry delete(Long id) {
       String sql = "delete from time_entries where id=?";
       int updated = jdbcTemplate.update(sql, id);

       return null;
    }

    @Override
    public TimeEntry update(Long id, TimeEntry entry) {
        String sql = "update time_entries set project_id=?, user_id=?, date=?, hours=? where id=?";

        int updated = jdbcTemplate.update(sql, entry.getProjectId(), entry.getUserId(), Date.valueOf(entry.getDate()), entry.getHours(), id);

        entry.setId(id);

        return entry;

    }
}

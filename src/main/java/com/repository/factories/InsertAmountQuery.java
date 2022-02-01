package com.repository.factories;

import com.main.model.request.CreateTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Map;

@Component
public class InsertAmountQuery {





    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCall;

    // init SimpleJdbcCall
    @PostConstruct
    void init() {
        // o_name and O_NAME, same
        jdbcTemplate.setResultsMapCaseInsensitive(true);

        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("usp_Insert_Amount")
                .returningResultSet("result", new BeanPropertyRowMapper<>(String.class))
        ;
    }

    public String InsertAmount(CreateTransaction transaction) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("userid", transaction.getUserId());
        namedParameters.addValue("datecreated", new Date(System.currentTimeMillis()));
        namedParameters.addValue("amount", transaction.getAmount());

        try {
            Map out = simpleJdbcCall.execute(namedParameters);

                if (out != null) {
                    var value =   out.get("Id").toString();
                    return value;
                }


        } catch (Exception e) {
            // ORA-01403: no data found, or any java.sql.SQLException
            System.err.println(e.getMessage());
            return  "null";
        }
        return "null";
    }




}

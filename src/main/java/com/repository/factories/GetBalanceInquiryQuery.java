package com.repository.factories;

import com.main.model.User;
import com.main.model.response.TransactionBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;



@Component
public class GetBalanceInquiryQuery {

    @Autowired
    private DataSource dataSource;

    private SimpleJdbcCall jdbcCall;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public GetBalanceInquiryQuery(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public TransactionBalance GetBalance(int  userId) {

        try {

            SqlParameterSource param = new MapSqlParameterSource()
                    .addValue("userId", userId);

            this.jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("usp_Get_Total_Amount_By_User").returningResultSet("user", BeanPropertyRowMapper.newInstance(TransactionBalance.class));

            var result   = (List<TransactionBalance>)this.jdbcCall.execute(param).get("user");

            if(result == null || result.size() <= 0) return  null;

            return result.get(0);

        } catch (Exception e) {
            System.err.println(e);
            throw (e);
        }
    }

}






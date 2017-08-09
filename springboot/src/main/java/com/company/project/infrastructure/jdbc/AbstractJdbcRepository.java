package com.company.project.infrastructure.jdbc;

import com.company.project.authz.domain.shared.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by eayon on 2017-07-19.
 */
public abstract class AbstractJdbcRepository implements Repository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;
}

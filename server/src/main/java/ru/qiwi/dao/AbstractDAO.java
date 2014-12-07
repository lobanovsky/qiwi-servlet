package ru.qiwi.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.activation.DataSource;

public class AbstractDAO extends JdbcDaoSupport {

    private DataSource dataSource;


}

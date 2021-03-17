package com.staticor.services;

import com.staticor.models.connections.Connection;
import org.springframework.stereotype.Service;

import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class ConnectionService {

    public java.sql.Connection getConnection(String url, Connection connection) throws SQLException {
        java.sql.Connection con = DriverManager.getConnection(url, connection.getUsername(), connection.getPassword());
        return con;
    }
}
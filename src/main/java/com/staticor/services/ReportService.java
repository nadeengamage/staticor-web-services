package com.staticor.services;

import com.staticor.models.collections.Collection;
import com.staticor.models.connections.Connection;
import com.staticor.models.dtos.ReportEditor;
import com.staticor.repositories.CollectionRepository;
import com.staticor.repositories.ReportRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

@Service
public class ReportService extends ServiceResponse {

    private final static Logger LOGGER = LogManager.getLogger(ReportService.class);

    private final String[] words = {"ALTER", "TRUNCATE", "COMMENT", "RENAME", "INSERT", "UPDATE", "DELETE", "GRANT", "REVOKE", "COMMIT", "ROLLBACK", "SAVEPOINT", "SET", "TRANSACTION"};

    private final ReportRepository repository;

    private final CollectionRepository collectionRepository;

    private final ConnectionService connectionService;

    public ReportService(ReportRepository repository, CollectionRepository collectionRepository, ConnectionService connectionService) {
        this.repository = repository;
        this.collectionRepository = collectionRepository;
        this.connectionService = connectionService;
    }

    public ServiceResponse executeSqlQuery(ReportEditor editor) {

        List<String> columns = new ArrayList<>();
        List<Map<String, Object>> rows = new ArrayList<>();

        Optional<Collection> collection = collectionRepository.findById(editor.getCollectionId());
        collection.orElseThrow(RuntimeException::new);

        try {

            // check sql query
            if (containsWords(editor.getSql().toUpperCase())) {
                return success(false).code(400).message("You haven't authorization to execute this given SQL query");
            }

            Connection connection = collection.get().getConnection();

            String url = String.format("jdbc:mysql://%s:%s/%s?createIfNotExists=true&useSSL=false", connection.getHost(), connection.getPort(), connection.getDatabaseName());

            java.sql.Connection con = connectionService.getConnection(url, connection);
            PreparedStatement ps = con.prepareStatement(editor.getSql());
            ResultSet rs = ps.executeQuery();

            // get columns
            getColumns(rs, columns);

            // get values
            getRows(rs, columns, rows);

            rs.close();
            ps.close();
            con.close();

            return success(true).code(200).result(new ReportEditor(columns, rows));
        } catch (SQLException e) {
            LOGGER.error("SQL editor error " + e.getSQLState());
            return success(false).code(500).message("Invalid SQL - " + e.getSQLState()).errors(e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.error("SQL editor error " + e.getMessage());
            return success(false).code(500).errors(e.getLocalizedMessage());
        }
    }

    private void getColumns(ResultSet rs, List<String> columns) throws SQLException {
        if (rs != null) {
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                columns.add(rsmd.getColumnName(i));
            }
        }
    }

    private void getRows(ResultSet rs, List<String> columns, List<Map<String, Object>> rows) throws SQLException {
        if (rs != null) {
            if (rs.isBeforeFirst()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columns.size(); i++) {
                        String colName = rsmd.getColumnName(i);
                        Object colVal = rs.getObject(i);
                        row.put(colName, colVal);
                    }
                    rows.add(row);
                }
            }
        }
    }

    public boolean containsWords(String inputString) {
        boolean found = false;
        for (String word : words) {
            if (inputString.contains(word)) {
                found = true;
                break;
            }
        }
        return found;
    }
}
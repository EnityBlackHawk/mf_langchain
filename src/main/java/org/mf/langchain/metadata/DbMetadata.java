package org.mf.langchain.metadata;

import lombok.Getter;
import org.mf.langchain.util.SqlDataType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DbMetadata {

    private final Connection _connection;
    private ArrayList<Table> tables = new ArrayList<>();
    private DatabaseMetaData _metadata;

    public DbMetadata(Connection connection) {
        _connection = connection;
    }

    public DbMetadata(String connectionString, String username, String password) throws SQLException {
        Connection tmp_connection;
        try {
            tmp_connection = DriverManager.getConnection(connectionString, username, password);
        } catch (SQLException e) {
            tmp_connection = null;
        }
        _connection = tmp_connection;

        if(_connection == null) return;

        _metadata = _connection.getMetaData();

        ResultSet tbs = _metadata.getTables(null, null, null, new String[] {"TABLE"});

        while(tbs.next()) {
            ArrayList<Column> columnArrayList = new ArrayList<>();
            String tb_name = tbs.getString("TABLE_NAME");
            ResultSet cls = _metadata.getColumns(null, null, tb_name, null);
            while (cls.next())
            {
                String columnName = cls.getString("COLUMN_NAME");
                String datatype = cls.getString("DATA_TYPE");

                columnArrayList.add(
                        new Column(columnName, SqlDataType.getByValue(Integer.parseInt(datatype)))
                );

            }
            tables.add(
                    new Table(
                            tb_name,
                            columnArrayList
                    )
            );
        }


    }

    public boolean isConnected() {
        try {
            return _connection != null && _connection.isClosed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Table> getTables() {
        return tables;
    }
}

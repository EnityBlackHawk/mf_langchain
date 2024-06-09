package org.mf.langchain.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class QueryResult {

    public ArrayList<String> columns = new ArrayList<>();
    public ArrayList<ArrayList<String>> rows = new ArrayList<>();

    public QueryResult(ResultSet resultSet) throws SQLException {
        for(int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
            columns.add(resultSet.getMetaData().getColumnName(i + 1));
        }
        while(resultSet.next()) {
            ArrayList<String> row = new ArrayList<>();
            for(int i = 0; i < columns.size(); i++) {
                row.add(resultSet.getString(i + 1));
            }
            if(row.size() != rows.size())
                throw new IllegalArgumentException("Row length does not match column length");
            rows.add(row);
        }
    }

    public QueryResult addRow(String... row) {
        if(row.length != columns.size())
            throw new IllegalArgumentException("Row length does not match column length");
        ArrayList<String> r = new ArrayList<>();
        Collections.addAll(r, row);
        rows.add(r);
        return this;
    }


}

package org.mf.langchain.util

import java.sql.ResultSet
import java.util.*

class QueryResult {
    private val columns = mutableListOf<String>()
    private val rows = mutableListOf<List<String>>()

    constructor(resultSet: ResultSet) {
        for (i in 0 until resultSet.metaData.columnCount) {
            columns.add(resultSet.metaData.getColumnName(i + 1))
        }
        while (resultSet.next()) {
            val row = ArrayList<String>()
            for (i in columns.indices) {
                row.add(resultSet.getString(i + 1))
            }
            require(row.size == columns.size) { "Row length does not match column length" }
            rows.add(row)
        }
    }

    constructor(map : Map<String, Int>, vararg columnNames : String) {
        this.columns.addAll(columnNames)
        for((k, v) in map) {
            addRow(k, v.toString())
        }
    }

    fun addRow(vararg row: String): QueryResult {
        require(row.size == columns.size) { "Row length does not match column length" }
        val r = mutableListOf<String>()
        r.addAll(row.asList())
        rows.add(r)
        return this
    }

    fun asString() : String{
        val sb = StringBuilder();
        var formatSting = ""
        for(i in 0 until  columns.size) {
            formatSting += ("%${
                (rows.map { it[i] }.maxByOrNull { it: String -> it.length }?.length ?: 0).coerceAtLeast(
                    columns[i].length
                )
            }s" + if (i + 1 == columns.size) "" else " | ")
        }
        formatSting += "\n"

        sb.append(String.format(formatSting, *columns.toTypedArray()))
        for (i in 0 until rows.size) {
            sb.append(String.format(formatSting, *rows[i].toTypedArray()))
        }

        return sb.toString()
    }

    override fun toString() : String {
        return asString()
    }
}

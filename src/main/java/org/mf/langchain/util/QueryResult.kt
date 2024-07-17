package org.mf.langchain.util

import org.springframework.data.annotation.Id
import java.sql.ResultSet
import java.util.*
import java.lang.Integer

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

    fun <T> getAllFromColumn(columnName: String, outputClass : Class<T>): List<T> {
        val columnIndex = columns.indexOf(columnName)
        require(columnIndex != -1) { "Column $columnName not found" }
        val strings = rows.map { it[columnIndex] }
        return when(outputClass) {
            Integer::class.java -> strings.map { it.toInt() } as List<T>
            Long::class.java -> strings.map { it.toLong() } as List<T>
            Float::class.java -> strings.map { it.toFloat() } as List<T>
            Double::class.java -> strings.map { it.toDouble() } as List<T>
            else -> strings as List<T>
        }
    }

    fun asString() : String{
        val sb = StringBuilder();
        var formatSting = ""
        for(i in 0 until  columns.size) {
            formatSting += ("%${
                (rows.map { it[i] }.maxByOrNull { it: String? -> it?.length ?: 0  }?.length ?: 0).coerceAtLeast(
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

    fun <T> asObject(clazz : Class<T>) : List<T> {
        val res = mutableListOf<T>()
        for (row in rows) {
            val obj = clazz.getDeclaredConstructor().newInstance()
            for (i in 0 until columns.size) {
                val field = clazz.getDeclaredField(columns[i].snakeToCamelCase())
                field.isAccessible = true
                if(row[i] == null || row[i].isEmpty()) continue
                when (field.type.name) {
                    "int" -> field.set(obj, row[i].toInt())
                    "long" -> field.set(obj, row[i].toLong())
                    "float" -> field.set(obj, row[i].toFloat())
                    "double" -> field.set(obj, row[i].toDouble())
                    "java.lang.String" -> field.set(obj, row[i])
                    "java.sql.Date" -> field.set(obj, java.sql.Date.valueOf(row[i]))
                    "java.util.Date" -> field.set(obj, Date(java.sql.Date.valueOf(row[i]).time))
                    "java.sql.Timestamp" -> field.set(obj, java.sql.Timestamp.valueOf(row[i]))
                    "java.sql.Time" -> field.set(obj, java.sql.Time.valueOf(row[i]))
                    else -> {
                        val c = field.type
                        val o = c.getDeclaredConstructor().newInstance()
                        val ann = c.declaredFields.map { it.annotations }
                        val fId = c.declaredFields.first { it.isAnnotationPresent(Id::class.java) }
                        fId.isAccessible = true
                        fId.set(o, row[i])
                        field.set(obj, o)
                    }
                }
            }
            res.add(obj)
        }
        return res;
    }

    fun String.snakeToCamelCase(): String {
        val pattern = "_[a-z]".toRegex()
        return replace(pattern) { it.value.last().uppercase() }
    }

    override fun toString() : String {
        return asString()
    }
}

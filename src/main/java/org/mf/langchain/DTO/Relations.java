package org.mf.langchain.DTO;

public class Relations{
        public String table_source;
        public String table_target;
        public String cardinality;

        @Override
        public String toString() {
            return table_source + " " + cardinality + " " + table_target;
        }

}

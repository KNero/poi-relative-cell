package team.nero.poi.relative.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConverter {
    private String userId;
    private String password;
    private String url;

    private QueryToRelativeBook queryToRelativeBook = new QueryToRelativeBook();

    private DbConverter() {
    }

    public static DbConverterBuilder builder() {
        return new DbConverterBuilder();
    }

    public void start(File targetDir) throws SQLException, IOException {
        try (Connection con = DriverManager.getConnection(url, userId, password)) {
            queryToRelativeBook.convert(con).toFile(targetDir);
        }
    }

    public static class DbConverterBuilder {
        private DbConverter dbConverter = new DbConverter();
        private String driverClass;

        /**
         * db driver class name
         */
        public DbConverterBuilder setDbDriverClass(String driverClass) {
            this.driverClass = driverClass;
            return this;
        }

        /**
         * db password
         */
        public DbConverterBuilder setDbUserPassword(String password) {
            dbConverter.password = password;
            return this;
        }

        /**
         * db url
         */
        public DbConverterBuilder setDbUrl(String url) {
            dbConverter.url = url;
            return this;
        }

        /**
         * db user id
         */
        public DbConverterBuilder setDbUserId(String userId) {
            dbConverter.userId = userId;
            return this;
        }

        public DbConverterBuilder setSelectQuery(String selectQuery) {
            dbConverter.queryToRelativeBook.setSelectQuery(selectQuery);
            return this;
        }

        /**
         * for paging
         * @param pageOffsetIndex 페이지 처리 쿼리에서 offset 을 세팅하기 위한 ? 위치
         * @param pageSizeIndex 페이지 처리 쿼리에서 limit 을 세팅하기 위한 ? 위치
         * @param pageSize 한 페이지 크기 (limit 값)
         */
        public DbConverterBuilder setPageSize(int pageOffsetIndex, int pageSizeIndex, int pageSize) {
            dbConverter.queryToRelativeBook.setPageOffsetIndex(pageOffsetIndex);
            dbConverter.queryToRelativeBook.setPageSizeIndex(pageSizeIndex);
            dbConverter.queryToRelativeBook.setPageSize(pageSize);
            return this;
        }

        public DbConverterBuilder setFileName(String fileName) {
            dbConverter.queryToRelativeBook.setFileName(fileName);
            return this;
        }

        public DbConverter build() {
            if (driverClass == null) {
                throw new NullPointerException("driverClass is null.");
            } else if (dbConverter.userId == null) {
                throw new NullPointerException("userId is null.");
            } else if (dbConverter.password == null) {
                throw new NullPointerException("password is null.");
            } else if (dbConverter.url == null) {
                throw new NullPointerException("url is null.");
            } else if (dbConverter.queryToRelativeBook.getSelectQuery() == null) {
                throw new NullPointerException("selectQuery is null.");
            } else if (dbConverter.queryToRelativeBook.getFileName() == null) {
                throw new NullPointerException("fileName is null");
            }

            try {
                Class.forName(driverClass);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            return dbConverter;
        }
    }
}

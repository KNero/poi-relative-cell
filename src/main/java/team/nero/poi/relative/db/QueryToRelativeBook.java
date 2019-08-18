package team.nero.poi.relative.db;

import team.nero.poi.relative.Direction;
import team.nero.poi.relative.RelativeBook;
import team.nero.poi.relative.RelativeCell;
import team.nero.poi.relative.RelativeSheet;

import java.sql.*;

class QueryToRelativeBook {
    private String fileName;

    private String selectQuery;
    private int pageOffsetIndex;
    private int pageSizeIndex;
    private int pageSize;

    void setSelectQuery(String selectQuery) {
        this.selectQuery = selectQuery;
    }

    String getSelectQuery() {
        return selectQuery;
    }

    void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    void setPageOffsetIndex(int pageOffsetIndex) {
        this.pageOffsetIndex = pageOffsetIndex;
    }

    void setPageSizeIndex(int pageSizeIndex) {
        this.pageSizeIndex = pageSizeIndex;
    }

    void setFileName(String fileName) {
        this.fileName = fileName;
    }

    String getFileName() {
        return fileName;
    }

    /**
     * 1. 자동으로 다음 시트로 이동 시키기
     */
    RelativeBook convert(Connection con) throws SQLException {
        RelativeBook relativeBook = new RelativeBook(fileName);
        RelativeSheet sheet = relativeBook.getSheet("db data");

        boolean isCreateHeader = false;
        int rowCount = 1;

        for (int pageIndex = 0; ; ++pageIndex) {
            int startRowCount = rowCount;

            try (PreparedStatement statement = con.prepareStatement(selectQuery)) {
                if (pageSizeIndex > 0) {
                    statement.setInt(pageSizeIndex, pageSize);
                }

                if (pageOffsetIndex > 0) {
                    statement.setInt(pageOffsetIndex, pageIndex * pageSize);
                }

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        if (!isCreateHeader) {
                            createHeader(sheet, resultSet.getMetaData(), rowCount);

                            isCreateHeader = true;
                            ++rowCount;
                        }

                        createDataRow(sheet, resultSet, rowCount);

                        ++rowCount;
                    }
                }
            }

            if (startRowCount == rowCount || pageSizeIndex == 0) { // 추가된 로우가 없거나 페이지 설정이 없을 경우
                break;
            }
        }

        return relativeBook;
    }

    private void createHeader(RelativeSheet sheet, ResultSetMetaData metaData, int rowCount) throws SQLException {
        RelativeCell cell = sheet.getPivotCell("A", rowCount);

        for (int i = 1; i <= metaData.getColumnCount(); ++i) {
            cell.setValue(metaData.getColumnName(i));
            cell = cell.next(Direction.RIGHT);
        }
    }

    private void createDataRow(RelativeSheet sheet, ResultSet resultSet, int rowCount) throws SQLException {
        RelativeCell cell = sheet.getPivotCell("A", rowCount);
        ResultSetMetaData metaData = resultSet.getMetaData();

        for (int i = 1; i <= metaData.getColumnCount(); ++i) {
            Object value = resultSet.getObject(i);
            if (value != null) {
                cell.setValue(value.toString());
            }

            cell = cell.next(Direction.RIGHT);
        }
    }
}

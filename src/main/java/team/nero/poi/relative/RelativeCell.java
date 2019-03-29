package team.nero.poi.relative;

import org.apache.poi.ss.usermodel.CellStyle;

public interface RelativeCell {
    void setValue(String value);

    /**
     * create next cell by direction.
     * @param direction {@link team.nero.poi.relative.Direction}
     * @return next new RelativeCell.
     */
    RelativeCell next(Direction direction);

    /**
     * create next cell by direction.
     * @param direction {@link team.nero.poi.relative.Direction}
     * @param distance next cell distance. (direction * distance)
     * @return
     */
    RelativeCell next(Direction direction, int distance);

    /**
     * @return this cell's style.
     */
    CellStyle getStyle();

    /**
     *  create new style and set style to this relative cell.
     * @return new style. {@link org.apache.poi.ss.usermodel.CellStyle}
     */
    CellStyle createNewStyleAndGet();

    /**
     * if style name's style is exists return cell style.
     * And set style to this relative cell
     * @return new style or recycle style {@link org.apache.poi.ss.usermodel.CellStyle}
     */
    CellStyle setStyleAndGet(String styleName);
}

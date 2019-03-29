# poi-relative-cell
Improved use to poi

## create excel file
```java
RelativeBook book = new RelativeBook("fileName"); //create xls
RelativeBook book = new RelativeBook("fileName", true); //create xlsx
```

## create sheet
```java
RelativeSheet sheet = book.getSheet("sheetName");
```

### create first cell
```java
RelativeCell cell = sheet.getPivotCell("B", 3); //created to B-3 cell
cell.setValue("1000");
```

#### get cell style
```java
CellStyle style = cell1.createNewStyleAndGet(); // create new cell and set style

// get current style. if not call createNewStyleAndGet or setStyleAndGet return null.
CellStyle style = cell1.getStyle(); 
```

### create next cell
```java
cell1 = cell.next(Direction.LEFT);
```
```Direction``` is next cell's direction. 

![](https://github.com/KNero/poi-relative-cell/blob/master/guide.png)

value 1000 is pivot.

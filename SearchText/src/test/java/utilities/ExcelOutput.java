package utilities;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExcelOutput {
	
	public void setData(String name, String price, String rating, String browser) throws IOException{
		String pathString = ".\\src\\test\\resources\\OutPutData.xlsx";
		String sheetName = "Sheet1";
		ExcelUtility utility = new ExcelUtility(pathString);
		int totalrows=utility.getRowCount(sheetName);	
		int insertIndexRow=totalrows+1;
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		utility.setCellData(sheetName, insertIndexRow, 0, name);
		utility.setCellData(sheetName, insertIndexRow, 1, price);
		utility.setCellData(sheetName, insertIndexRow, 2, rating);
		utility.setCellData(sheetName, insertIndexRow, 3, browser);
		utility.setCellData(sheetName, insertIndexRow, 4, timestamp);
	}
}

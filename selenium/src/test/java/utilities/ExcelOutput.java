package utilities;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelOutput {
	
	public void setData(List<String> data) throws IOException{
		String pathString = ".\\testData\\Book1.xlsx";
		String sheetName = "Sheet1";
		ExcelUtility utility = new ExcelUtility(pathString);
		int totalrows=utility.getRowCount(sheetName);	
		int insertIndexRow=totalrows+1;
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		utility.setCellData(sheetName, insertIndexRow, 0, data.get(0));
		utility.setCellData(sheetName, insertIndexRow, 1, data.get(1));
		utility.setCellData(sheetName, insertIndexRow, 2, timestamp);
		
	}
}

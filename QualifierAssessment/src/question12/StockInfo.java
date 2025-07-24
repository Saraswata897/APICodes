package question12;

import java.util.HashSet;
import java.util.Set;

public class StockInfo {
	private Set<String> stockInfo = new HashSet<String>();
	public void addClothStockDetails(String stockDetails) {
		stockInfo.add(stockDetails);
		
	}
	public Set<String> filterClothItemsWithinStockRange(int minimumQuantity, int maximumQuantity) {
		Set<String> resultSet = new HashSet<String>();
		for (String string : stockInfo) {
			String stockName=string.split(":")[0];
			int quantity=Integer.parseInt(string.split(":")[1]);
			if (quantity>=minimumQuantity && quantity<=maximumQuantity) {
				resultSet.add(stockName);
			}
		}
		return resultSet;
	}
}

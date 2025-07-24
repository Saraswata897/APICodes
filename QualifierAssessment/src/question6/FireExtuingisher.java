package question6;

import java.util.ArrayList;
import java.util.List;

public class FireExtuingisher {
	private List<String> product= new ArrayList<String>();
	public void addExtinguisherDetails(String extinguisherDetails) {
		product.add(extinguisherDetails);
	}
	public List<String> findExtinguisherProductRange(double minimumPrice,double maximumPrice){
		List<String> resultList = new ArrayList<String>();
		for (String string : product) {
			String[] parts=string.split(":");
			String productName=parts[0];
			double price=Double.parseDouble(parts[2]);
			if (price>=minimumPrice && price<=maximumPrice) {
				resultList.add(productName);
			}
		}
		return resultList;
	}
}

package question4;

import java.util.ArrayList;
import java.util.List;

public class HotelManager {
	private List<String> hotelList=new ArrayList<String>();
	public void addHotelDetails(String hotelDetails) {
		hotelList.add(hotelDetails);
	}
	public List<String> minRatings(double minRating) {
		List<String> hotelNameList = new ArrayList<String>();
		for (String s  : hotelList) {
			String[] details = s.split(":");
			String hotelName = details[0];
			double rating = Double.parseDouble(details[1]);
			if(rating>=minRating) {
				hotelNameList.add(hotelName);
			}
		}
		return hotelNameList;
	}
}

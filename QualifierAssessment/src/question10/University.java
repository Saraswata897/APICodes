package question10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class University {
	private Map<String, Double> uniMap = new HashMap<>();
	public void addUniversityDetails(String universityName,double totalStudyExpense) {
		uniMap.put(universityName, totalStudyExpense);
	}
	public List<String> filterBudgetUniversities(double budgetAmount){
		List<String> budgetList = new ArrayList<String>();
		for(Map.Entry<String, Double> entry : uniMap.entrySet()) {
			String uniName=entry.getKey();
			double expense=entry.getValue();
			if (expense<=budgetAmount) {
				budgetList.add(uniName);
			}
		}
		return budgetList;
	}
}

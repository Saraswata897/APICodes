package question13;

import java.util.Set;
import java.util.TreeSet;

public class PetInfo {
	private Set<String> petSet=new TreeSet<String>();
	
	public Set<String> getPetSet() {
		return petSet;
	}
	public void addPetDetails(String petDetails) {
		petSet.add(petDetails);
	}
	public Set<String> filterPetsByAgeAndBreed(int ageInMonth, String breed) {
		Set<String> resultSet=new TreeSet<String>();
		for (String string : petSet) {
			String[] parts=string.split(":");
			String name=parts[0];
			String breedName=parts[1];
			int age = Integer.parseInt(parts[2]);
			if(age<=ageInMonth && breedName.equalsIgnoreCase(breed)) {
				resultSet.add(name);
			}
			
		}
		return resultSet;
		
	}
}

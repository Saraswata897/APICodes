package question5;

public class AppInfo {
	private String appId;
	private String developer;
	private int sizeInMB;
	private boolean isFree;
	public AppInfo(String appId, String developer, int sizeInMB, boolean isFree) {
		this.appId = appId;
		this.developer = developer;
		this.sizeInMB = sizeInMB;
		this.isFree = isFree;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getDeveloper() {
		return developer;
	}
	public void setDeveloper(String developer) {
		this.developer = developer;
	}
	public int getSizeInMB() {
		return sizeInMB;
	}
	public void setSizeInMB(int sizeInMB) {
		this.sizeInMB = sizeInMB;
	}
	public boolean getIsFree() {
		return isFree;
	}
	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}
	public static AppInfo verifyAppDetails(String appDetails) throws InvalidAppInfoException{
		String[] parts = appDetails.split(":");
		if(parts.length!=4) {
//			System.out.println("A");
			throw new InvalidAppInfoException("Invalid App Details");
		}
		String appid=parts[0].trim();
		String developer=parts[1].trim();
		String sizeStr=parts[2].trim();
		String isFreeString=parts[3].trim();
		if(!(appid.matches("APP\\d{3}"))) {
//			System.out.println("B");
			throw new InvalidAppInfoException("Invalid App Details");
		}
		if(!(developer.matches("[a-zA-Z]+"))) {
//			System.out.println("C");
			throw new InvalidAppInfoException("Invalid App Details");
		}
 		int size;
 		try {
			size=Integer.parseInt(sizeStr);
//			System.out.println(size);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			throw new InvalidAppInfoException("Invalid App Details");
		}
 		if(size<10 || size>1000) {
// 			System.out.println("D");
 			throw new InvalidAppInfoException("Invalid App Details");
 		}
 		if(!(isFreeString.equals("true")) && !(isFreeString.equals("false"))) {
// 			System.out.println("E");
 			throw new InvalidAppInfoException("Invalid App Details");
 		}
 		boolean isFree=Boolean.parseBoolean(isFreeString);
		return new AppInfo(appid, developer, size, isFree) ;
		
	}
	
	
}

package FlightPriceCompare.FlightPriceCompare;

import java.util.HashMap;

public class Calendar {
	
	public static String getMonthYear(String travelDate)
	{
		HashMap<String,String> h= new HashMap<String, String>();
		h.put("01","January" );
		h.put("02","February" );
		h.put("03","March" );
		h.put("04","April" );
		h.put("05","May" );
		h.put("06","June" );
		h.put("07","July" );
		h.put("08","August" );
		h.put("09","September" );
		h.put("10","October" );
		h.put("11","November" );
		h.put("12","December" );
		
		String monthNum=travelDate.split("/")[1];
		String year=travelDate.split("/")[0];
		String monthText=h.get(monthNum);
		
		return monthText+" "+year;
	}
	
	public static String getDate(String travelDate) 
	{
		return travelDate.split("/")[2];
		
	}

}

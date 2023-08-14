package FlightPriceCompare.FlightPriceCompare;

import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

public class CsvData {
	
	static CSVWriter writer;
	public static void heading() throws IOException {
	    writer=new CSVWriter(new FileWriter("Files\\Data.csv"));
		String[] headings= {"Flight Operator","Flight Number ","Price on Cleartrip"};//"Price on Paytm"
		writer.writeNext(headings);
	}
	public static void data(String flightOp, String flightNum, String clearTripPrice, String paytmPrice ) throws IOException 
	{
	String[] data= {flightOp,flightNum,clearTripPrice,paytmPrice};
	writer.writeNext(data);
	writer.flush();
	}
}

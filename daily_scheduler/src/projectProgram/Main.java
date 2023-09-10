package projectProgram;

import java.util.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import java.text.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Main {

	public static void main(String[] args) throws IOException {
		List<String> fileLines = new ArrayList<String>();
		List<ISCData> events = new ArrayList<ISCData>();
		List<ISCData> eventsToday = new ArrayList<ISCData>();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String icalLink = "https://portalapi2.uwaterloo.ca/v2/calendar/feed/asangle/03LxPGxsHzxS_2C5ESrfNVNjvXQcwyuooGa4cnKJawREQ5KaE9MP_ROiezsxUsIYmR6D-f_Q4VOCBQ6Flgeorw/portal.ics";
		URL url = new URL(icalLink);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){			
			String line;
			while ((line = reader.readLine()) != null) {
		        fileLines.add(line);
		    }
		}
		
		int i = 0;
		while(!(fileLines.get(i).equals("END:VCALENDAR"))) {
			List<String> event = new ArrayList<String>();

			if(fileLines.get(i).equals("BEGIN:VEVENT")) {
				i++;
				while(!(fileLines.get(i).equals("END:VEVENT"))) {
					if(fileLines.get(i).substring(0, Math.min(fileLines.get(i).length(), 11)).equals("DESCRIPTION")) {
						StringBuilder sb = new StringBuilder();
						while(!fileLines.get(i).substring(0, Math.min(fileLines.get(i).length(), 5)).equals("DTEND")) {
							sb.append(fileLines.get(i).stripLeading());
							i++;
						}
						event.add(sb.toString());
						event.add(fileLines.get(i));
					} else {
						event.add(fileLines.get(i));
					}
					i++;
				}
			}
			
			if(event.size() > 0) {
				events.add(dataParser(event));
			}
			System.out.println(event);
			i++;
		}
		
		for(int j = 0; j < events.size(); j++) {
			String[] time = events.get(j).getStartTime().split(":");
			String[] splitTime = time[1].split("T");
			if(Integer.valueOf(splitTime[0]).equals(Integer.valueOf(dateFormat.format(date)))){
				eventsToday.add(events.get(j));
			}
		}
		
		System.out.println(eventsToday);
	}
	
	//Creates new ISCData objects
	public static ISCData dataParser(List<String> event) {
		if(event.size() == 9) {
			ISCData obj = new ISCData(event.get(0), event.get(1), event.get(2), event.get(3), 
					event.get(4), event.get(5), event.get(6), event.get(7), event.get(8));
			return obj;
		} else {
			ISCData obj = new ISCData(event.get(0), event.get(1), event.get(2), event.get(3), 
					event.get(4), event.get(5), event.get(6), event.get(7), event.get(8), event.get(9));
			return obj;
		}
	}
	
	public static String dataURL(String URL) {
		return "hi";
	}
}

class ISCData {
	private String origin;
	private String created;
	private String description;
	private String endTime;
	private String currentTime;
	private String startTime;
	private String location = "None";
	private String sequence;
	private String summary;
	private String UID;
	
	public ISCData (String origin, String created, String description, String endTime, String currentTime,
			String startTime, String location, String sequence, String summary, String UID) {
		this.origin = origin;
		this.created = created;
		this.description = description;
		this.endTime = endTime;
		this.currentTime = currentTime;
		this.startTime = startTime;
		this.location = location;
		this.sequence = sequence;
		this.summary = summary;
		this.UID = UID;
	}
	
	//Overloaded (no location)
	public ISCData (String origin, String created, String description, String endTime, String currentTime,
			String startTime, String sequence, String summary, String UID) {
		this.origin = origin;
		this.created = created;
		this.description = description;
		this.endTime = endTime;
		this.currentTime = currentTime;
		this.startTime = startTime;
		this.sequence = sequence;
		this.summary = summary;
		this.UID = UID;
	}
	
	public String getStartTime() {
		return this.startTime;
	}
	
	public String toString() {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("[yyyyMMddHHmmss]")
																	.appendPattern("[yyyyMMdd]")
																	.toFormatter();
		String[] timeStart = this.startTime.split(":");
		String[] splitTimeStart = timeStart[1].split("T");
		String[] timeEnd = this.endTime.split(":");
		String[] splitTimeEnd = timeEnd[1].split("T");
		LocalDateTime ldtStart;
		LocalDateTime ldtEnd;
		
		if(String.valueOf(Integer.valueOf(splitTimeStart[1]) - 40000).length() == 6) {
			ldtStart = LocalDateTime.parse(splitTimeStart[0] + "" + String.valueOf(Integer.valueOf(splitTimeStart[1]) - 40000), formatter);
		} else if (String.valueOf(Integer.valueOf(splitTimeEnd[1]) - 40000).length() == 0) {
			if(location.equals("None")) {
				return "Event from " + this.origin.substring(6) + " calendar:\r\n"
						+ this.description.substring(12) + ": " + this.summary.substring(8) + "\r\n"
						+ "Time: " + LocalDateTime.parse(splitTimeStart[0], formatter).format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
						+ " - " + LocalDateTime.parse(splitTimeEnd[0], formatter).format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
			}
			
			return "Event from " + this.origin.substring(6) + " calendar:\r\n"
			+ this.description.substring(12) + ": " + this.summary.substring(8) + "\r\n"
			+ "Time: " + LocalDateTime.parse(splitTimeStart[0], formatter).format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
			+ " - " + LocalDateTime.parse(splitTimeEnd[0], formatter).format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")) + "\r\n"
			+ "Location: " + this.location.substring(9);
		} else {
			ldtStart = LocalDateTime.parse(splitTimeStart[0] + "0" + String.valueOf(Integer.valueOf(splitTimeStart[1]) - 40000), formatter);
		}
		
		if(String.valueOf(Integer.valueOf(splitTimeEnd[1]) - 40000).length() == 6) {
			ldtEnd = LocalDateTime.parse(splitTimeEnd[0] + "" + String.valueOf(Integer.valueOf(splitTimeEnd[1]) - 40000), formatter);
		} else {
			ldtEnd = LocalDateTime.parse(splitTimeEnd[0] + "0" + String.valueOf(Integer.valueOf(splitTimeEnd[1]) - 40000), formatter);
		}
		
		
		if(this.location.equals("None")) {
			return "Event from " + this.origin.substring(6) + " calendar:\r\n"
					+ this.description.substring(12) + ": " + this.summary.substring(8) + "\r\n"
					+ "Time: " + ldtStart.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy  HH:mm"))
					+ " - " + ldtEnd.format(DateTimeFormatter.ofPattern("HH:mm"));
		}
		
		return "Event from " + this.origin.substring(6) + " calendar:\r\n"
				+ this.description.substring(12) + ": " + this.summary.substring(8) + "\r\n"
				+ "Time: " + ldtStart.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy  HH:mm"))
				+ " - " + ldtEnd.format(DateTimeFormatter.ofPattern("HH:mm")) + "\r\n"
				+ "Location: " + this.location.substring(9);
	}
	
}
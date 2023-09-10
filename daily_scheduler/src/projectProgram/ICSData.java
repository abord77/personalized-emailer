package projectProgram;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class ICSData {
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
	
	public ICSData (String origin, String created, String description, String endTime, String currentTime,
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
	public ICSData (String origin, String created, String description, String endTime, String currentTime,
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
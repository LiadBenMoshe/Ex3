package gameClient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;

import utils.Point3D;

public class KML_Logger2 {
	
	public static void make(String name) {
		
		create(name);
		write(name);
	}
	
	public static void create(String namefile) {
		 try {
		      File myObj = new File(namefile);
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		 
		
	}
	@SuppressWarnings("deprecation")
	public static void write(String filename) {
		try {
		      FileWriter myWriter = new FileWriter(filename);
		      myWriter.write(make_start(filename));
		      myWriter.write(make_style_id("p0"));
		      
		      myWriter.write(KML_write_Placemark(Time.UTC(2001,11,3,12, 33, 31),new Point3D(12.21,11.1),"p0"));
		      myWriter.write("  </Document>\r\n"+"</kml>");
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	


	public static String KML_write_Placemark(long time,Point3D p,String icon) {
		
		String kmlelement ="    <Placemark>\n" +
                "     <TimeStamp>\n" + 
                "      <when>"+time+"</when>\n" + 
                "      </TimeStamp>\n" +
                "      <styleUrl>#hiker-icon</styleUrl>\n" +
                "      <Point>\n" +
                "       <coordinates>"+p.x()+","+p.y()+","+p.z()+ "</coordinates>\n" +
                "      </Point>\n" +
                "      </Placemark>\n";
		return kmlelement;

	}
	public static String make_style_id(String icon) {
		String styleId="    <Style id="+icon+">\r\n" + 
				"     <IconStyle>\r\n" + 
				"      <Icon>\r\n" + 
				"       <href>data\\"+icon+".png</href>\r\n" + 
				"      </Icon>\r\n" + 
				"      <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" + 
				"     </IconStyle>\r\n" + 
				"    </Style>\n";
		
		
		return styleId;
	}
	public static String make_start(String namefile) {
		String startKML="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" + 
				"  <Document>\r\n" + 
				"    <name>"+namefile+"</name>\n";
		return startKML;
	}
	
	
	

}

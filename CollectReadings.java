package com.systemmoniproj;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CollectReadings {
	public static void main(String[] args) {

		try {

			while (true) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sysmonitor", "root",
						"password");

				File fileObject = new File("E:");

				LocalDateTime createdDateTime = LocalDateTime.now();
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

				double totalSpace = (double) fileObject.getTotalSpace() / 1073741824;
				double freeSpace = (double) fileObject.getFreeSpace() / 1073741824;
				double usedSpace = totalSpace - freeSpace;

				System.out.println("Total Space: " + totalSpace);
				System.out.println("Free Space: " + freeSpace);
				System.out.println("Used Space: " + usedSpace);
				System.out.println("Created Date Time: " + dtf.format(createdDateTime));

				String q = "insert into readings values(?,?,?,?)";
				PreparedStatement pstmt = con.prepareStatement(q);
				pstmt.setDouble(1, totalSpace);
				pstmt.setDouble(2, freeSpace);
				pstmt.setDouble(3, usedSpace);
				pstmt.setString(4, dtf.format(createdDateTime));

				int res = pstmt.executeUpdate();
				System.out.println("Inserted Record: " + res);

				con.close();
				pstmt.close();
				
				Thread.sleep(5000);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

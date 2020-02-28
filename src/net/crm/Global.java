package net.crm;

import java.util.Date;

public class Global {


	public static String DB_Name = "crm";
	public static String DB_Server = "localhost";
	public static String DB_User = "root";
	public static String DB_Password = "";
	
	public static void LogServiceErrors(String class_name, String method_name, String error_desc) {
		String err_detail = " ";
		err_detail = err_detail + "\n" + "Error Detail";
		err_detail = err_detail + "\n" + "------------";
		err_detail = err_detail + "\n" + "Class name  : " + class_name;
		err_detail = err_detail + "\n" + "Method name : " + method_name;
		err_detail = err_detail + "\n" + "Description : " + error_desc;
		err_detail = err_detail + "\n" + "Time        : " + new Date();
		err_detail = err_detail + "\n" + "----------------------------------------------------------------------";

		System.out.println(err_detail);
	}
}

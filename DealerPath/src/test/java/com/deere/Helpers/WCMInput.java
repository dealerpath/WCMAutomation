package com.deere.Helpers;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WCMInput extends BaseClass {
	public static XSSFWorkbook book;
	private static XSSFSheet dataWCMInputSheet;
	private static XSSFSheet dataInputValueSheet;
	static LinkedHashMap<String, HashMap<String, Object>> WCMInputValues;
	static LinkedHashMap<String, String> InputValues;

	public static void readWCMContentData() throws Exception {
		try {
			ArrayList<String> ListAttributeName = new ArrayList<String>();
			FileInputStream inputStream = new FileInputStream(new File(strWCMInput));
			book = new XSSFWorkbook(inputStream);

			dataWCMInputSheet = book.getSheet("WCMInputValues");
			dataInputValueSheet = book.getSheet("InputValues");

			int colon_count = dataWCMInputSheet.getRow(0).getLastCellNum();
			String AttributeName = "";

			for (int j = 0; j < colon_count; j++) {
				AttributeName = dataWCMInputSheet.getRow(0).getCell(j).getStringCellValue().trim();
				ListAttributeName.add(AttributeName);
			}
			WCMInputValues = readWCMInputValues();
			InputValues = readInputValues();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static LinkedHashMap<String, HashMap<String, Object>> RowValuedata = new LinkedHashMap<String, HashMap<String, Object>>();

	public static LinkedHashMap<String, HashMap<String, Object>> readWCMInputValues() throws Exception {
		try {
			XSSFCell cell;

			int totalRows = dataWCMInputSheet.getLastRowNum();
			int startRow = 0;
			int totalCol = 0;
			int flagRegion = 0;
			int flagLibrary = 0;
			int flagDepartments = 0;
			int flagSubDepartments = 0;
			int flagLastSavedDate = 0;
			int flagMultilingual = 0;
			int flagNumberOfRowstofetch = 0;
			int flagComments = 0;
			String regionValue = "";
			String libraryValue = "";
			String departmentValue = "";
			int subDepartmentsValue=0;
			String multiLinguals = "";
			String publishedDateValue = "";
			int NumberOfRowstofetch = 0;
			String Comments = "";
			List<LinkedHashMap> WCMRowDataList = null;
			ArrayList<String> WCMHeaderList = new ArrayList<String>();

			for (int i = 0; i < totalRows; i++) {
				String cellvalue = dataWCMInputSheet.getRow(i).getCell(0).getStringCellValue().toString().trim();
				if (cellvalue.equalsIgnoreCase("Region")) {
					startRow = i;
					totalCol = dataWCMInputSheet.getRow(startRow).getLastCellNum();

					for (int j = 0; j < totalCol; j++) {
						String WCMHeaderName = dataWCMInputSheet.getRow(startRow).getCell(j).getStringCellValue().trim()
								.toString();
						WCMHeaderList.add(WCMHeaderName);
						switch (WCMHeaderName) {
						case "Region":
							flagRegion = j;
							break;
						case "Library":
							flagLibrary = j;
							break;
						case "Departments":
							flagDepartments = j;
							break;
						case "SubDepartments":
							flagSubDepartments = j;
							break;
						case "Multilingual":
							flagMultilingual = j;
							break;
						case "Last Saved Date":
							flagLastSavedDate = j;
							break;	
						case "Number Of Rows to fetch":
							flagNumberOfRowstofetch = j;
							break;
						}
					}
				} else {
					break;
				}
			}

			System.out.println(WCMHeaderList);

			WCMRowDataList = new ArrayList<LinkedHashMap>();

			for (int i = 2; i <4; i++) {

				regionValue = dataWCMInputSheet.getRow(i).getCell(flagRegion).getStringCellValue().trim();
				libraryValue = dataWCMInputSheet.getRow(i).getCell(flagLibrary).getStringCellValue().trim();
				departmentValue = dataWCMInputSheet.getRow(i).getCell(flagDepartments).getStringCellValue().trim();
				if(!(flagSubDepartments==0)) {
				subDepartmentsValue = (int) dataWCMInputSheet.getRow(i).getCell(flagSubDepartments).getNumericCellValue();
				}
				multiLinguals=dataWCMInputSheet.getRow(i).getCell(flagMultilingual).getStringCellValue().trim();
				publishedDateValue = dataWCMInputSheet.getRow(i).getCell(flagLastSavedDate).getStringCellValue().trim();
				if(!(flagNumberOfRowstofetch==0))
				{
				NumberOfRowstofetch = (int) dataWCMInputSheet.getRow(i).getCell(flagNumberOfRowstofetch).getNumericCellValue();
				}
				// Changed this line
				HashMap<String, Object> map = new HashMap<String, Object>();
				if (!regionValue.contains("scenario")) {
					map.put("Region", regionValue);
					map.put("Library", libraryValue);
					map.put("Departments", departmentValue);
					map.put("SubDepartments", subDepartmentsValue);
					map.put("Multilingual", multiLinguals);
					map.put("Last Saved Date", publishedDateValue);
					map.put("Number Of Rows to fetch", NumberOfRowstofetch);

					String key = regionValue + "_" + departmentValue;
					RowValuedata.put(key, map);

				}

			}
		
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		return RowValuedata;

	}

	static LinkedHashMap<String, String> inputValueData = new LinkedHashMap<String, String>();

	public static LinkedHashMap<String, String> readInputValues() throws Exception {
		try {
			int totalRows = dataInputValueSheet.getLastRowNum();
			String Value = "";
			for (int i = 0; i <= totalRows; i++) {
				String cellvalue = dataInputValueSheet.getRow(i).getCell(0).getStringCellValue().toString().trim();
				switch (cellvalue) {
				case "Browser":
					Value = dataInputValueSheet.getRow(i).getCell(1).getStringCellValue().toString().trim();
					inputValueData.put("Browser", Value);
					break;
				case "URL":
					Value = dataInputValueSheet.getRow(i).getCell(1).getStringCellValue().toString().trim();
					inputValueData.put("URL", Value);
					break;
				case "Username":
					Value = dataInputValueSheet.getRow(i).getCell(1).getStringCellValue().toString().trim();
					inputValueData.put("Username", Value);
					break;
				case "Password":
					Value = dataInputValueSheet.getRow(i).getCell(1).getStringCellValue().toString().trim();
					inputValueData.put("Password", Value);
					break;
				case "FileName":
					Value = dataInputValueSheet.getRow(i).getCell(1).getStringCellValue().toString().trim();
					inputValueData.put("FileName", Value);
					break;	
					
				}
			}
			System.out.println("*********Data From Input Value Sheet : *****************" + inputValueData);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		return inputValueData;
	}
}

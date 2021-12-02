package com.sbproject.schedule.xlsx;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sbproject.schedule.models.Lesson;

import java.time.DayOfWeek;

public class ScheduleDownloader {

	private String title;
	
	private XSSFWorkbook workbook;
	
	private static final short DEFAULT_ROW_HEIGHT = 400;
	
	private static final int DEFAULT_COLUMN_WIDTH = 3000;
	
	private static final int COLUMN_COUNT = 7;
	
	
	public ScheduleDownloader(String title)
	{
		this.title = title;
		this.workbook = new XSSFWorkbook();
	}
	
	
	public void downloadSchedule (Iterable<Lesson> schedule, BufferedOutputStream ostream) throws IOException
	{
		 XSSFSheet sheet = workbook.createSheet(title);
		 createHeaderRow(sheet);
		 fillSchedule(reformatSchedule((List<Lesson>)schedule), sheet);
		 this.workbook.write(ostream);
	}
	
	private Map<DayOfWeek, List<Lesson>> reformatSchedule(List<Lesson> list)
	{
		Map<DayOfWeek, List<Lesson>> map = new TreeMap<DayOfWeek, List<Lesson>>();
		for(DayOfWeek dow : DayOfWeek.values())
		{
			map.put(dow, list
					.stream()
					.filter(s -> s.getDayOfWeek().equals(dow))
					.collect(Collectors.toList()));
		}
		return map;
	}
	
	private void createHeaderRow(XSSFSheet sheet)
	{
		for(int i = 0; i <= COLUMN_COUNT; i++)
			sheet.setColumnWidth(i, DEFAULT_COLUMN_WIDTH);
		XSSFCellStyle headerStyle = workbook.createCellStyle();
		setDefaultStyleParams(headerStyle);
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.DARK_BLUE.index);
		headerStyle.setFillForegroundColor(IndexedColors.AQUA.index);
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setFont(headerFont);
		sheet.setDefaultRowHeight(DEFAULT_ROW_HEIGHT);
		Row headerRow = sheet.createRow(0);
		Cell[] hcells = new Cell[COLUMN_COUNT];
		for(int i = 0; i < hcells.length; i++) 
		{
			hcells[i] = headerRow.createCell(i);
			String value = "";
			switch(i)
			{
			case 0:
				value = "Day";
				break;
			case 1:
				value = "Time";
				break;
			case 2:
				value = "Subject";
				break;
			case 3:
				value = "Teacher";
				break;
			case 4:
				value = "Group";
				break;
			case 5:
				value = "Weeks";
				break;
			default:
				value = "Room";
			}
			hcells[i].setCellValue(value);
			hcells[i].setCellStyle(headerStyle);
		}
		
	}
	
	private void fillSchedule(Map<DayOfWeek, List<Lesson>> schedule, XSSFSheet sheet)
	{
		
		XSSFCellStyle defaultStyle = workbook.createCellStyle();
		defaultStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	 	setDefaultStyleParams(defaultStyle);
	 	int rowCount = 1;
	 	for(DayOfWeek day : schedule.keySet())
	 	{
	 		int rowBegin = rowCount;
	 		for(Lesson lesson : schedule.get(day))
	 		{
	 			 Row row = sheet.createRow(rowCount++);
	 			 int colCnt = 0;
	 			 Cell cell1 = row.createCell(colCnt);
				 cell1.setCellStyle(defaultStyle);
				 for(Object str : lesson.getColumnArray()) {
					 Cell cell = row.createCell(++colCnt);
					 cell.setCellStyle(defaultStyle);
					 if(str instanceof String) 
					 {
						 int width = ((String)str).length() * 400;
		                	if(sheet.getColumnWidth(colCnt) < width)
		                		sheet.setColumnWidth(colCnt, ((String)str).length() * 400);
						 cell.setCellValue((String)str);
					 } 
					 else if(str instanceof Integer)
						 cell.setCellValue((Integer)str);
				 }
	 		}
	 		if(rowCount != rowBegin)
	 		{
				Cell cell = sheet.getRow(rowBegin).getCell(0);
				cell.setCellValue(day.name());	
				if(rowCount - rowBegin > 1)
					sheet.addMergedRegion(new CellRangeAddress(rowBegin, rowCount - 1, 0, 0)); 
			}  
	 		else 
	 		{
				Row emptyRow = sheet.createRow(rowCount);
				Cell dayCell = emptyRow.createCell(0);
				dayCell.setCellValue(day.name());
				dayCell.setCellStyle(defaultStyle);
				for(int i = 1; i <= 6; i++)
					emptyRow.createCell(i).setCellStyle(defaultStyle);
				rowCount++;
			}
	 	}
	}
	
	private void setDefaultStyleParams(XSSFCellStyle style)
	{
		style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setWrapText(true);
	}
	
}

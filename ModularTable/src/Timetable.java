import java.awt.Desktop;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import java.awt.Color;
/**
 *
 * @author Sinny
 */
public class Timetable {
    public static ArrayList<ArrayList<String>> timeSlots = new ArrayList<ArrayList<String>>();// a array of strings to timeslots in every sheet
    public static ArrayList<ArrayList<String>> venues = new ArrayList<ArrayList<String>>();// a list of venues, didn't keep array because venues may change.
    public static TreeSet<String> Teachers = new TreeSet<String>();
    public static TreeSet<String> Sections = new TreeSet<String>();
    public static TreeSet<String> Courses = new TreeSet<String>();
    public static ArrayList<HashMap<slotKey,Slot>> days = new ArrayList<HashMap<slotKey, Slot>>();
    private final String[] dayDict = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    private final Color[] ClashColors = {new Color(169, 50, 38), new Color(146, 43, 33), new Color(176, 58, 46),new Color(148, 49, 38)};
    Random rnd = new Random();
    
    public Timetable(FileInputStream file)
    {
        try
        {
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            readTimetable(workbook);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static TreeSet<String> getTeachers()
    {
        return Teachers;
    }
    public static TreeSet<String> getCourses()
    {
        return Courses;
    }
    public static TreeSet<String> getSections()
    {
        return Sections;
    }
//    public static int[][][] freeTeachers(HashSet<String> teachers)
//    {
//        int count[][];
//        for(int i=0;i<days.size();i++)
//        {
//            for(int j=0;j<venues.size();j++)
//            {
//                for(int k=0;k<timeSlots.size();k++)
//                {
//                    Slot slot = days.get(i).get(new slotKey(venues.get(i).get(j),timeSlots.get(i).get(k)));
//                    if(teachers.contains(slot.getTeacher()))
//                        count++;
//                }
//            }
//        }
//        
//    }
    public void writeCoursesTimetable(String filename, HashSet<String> parameters)
    {
        XSSFWorkbook timetable = new XSSFWorkbook();
        CreationHelper helper = timetable.getCreationHelper();
        XSSFCellStyle normal = timetable.createCellStyle();
        XSSFCellStyle highlighted = timetable.createCellStyle();
        XSSFCellStyle clash = timetable.createCellStyle();
        normal.setBorderBottom(BorderStyle.THIN);
        normal.setBorderLeft(BorderStyle.THIN);
        normal.setBorderRight(BorderStyle.THIN);
        normal.setBorderTop(BorderStyle.THIN);
        normal.setAlignment(HorizontalAlignment.CENTER);
        normal.setWrapText(true);
        highlighted.cloneStyleFrom(normal);
        clash.cloneStyleFrom(normal);
        clash.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont fontNormal = timetable.createFont();
        XSSFFont fontHighlighted = timetable.createFont();
        fontNormal.setFontName("Calibri");
        fontNormal.setFontHeight(11);
        fontHighlighted.setFontName("Calibri");
        fontHighlighted.setFontHeight(11);
        fontHighlighted.setBold(true);
        fontHighlighted.setColor(IndexedColors.BLUE_GREY.getIndex());
        normal.setFont(fontNormal);
        highlighted.setFont(fontHighlighted);
        for(int i=0;i<days.size();i++) {
            XSSFSheet day = timetable.createSheet(dayDict[i]);
            ArrayList<ArrayList<CellList>> Counter = new ArrayList<>(timeSlots.size());
            Row row = day.createRow(0);
            for (int j = 0; j < timeSlots.get(i).size(); j++) {
                Counter.add(new ArrayList<>());
                row.createCell(j + 1).setCellValue(helper.createRichTextString(timeSlots.get(i).get(j)));
            }
            for (int j = 1; j <= venues.get(i).size(); j++) {
                
                row = day.createRow(j);
                row.createCell(0).setCellValue(helper.createRichTextString(venues.get(i).get(j - 1)));
                for (int k = 0; k < timeSlots.get(i).size(); k++) {
                    Slot slot = days.get(i).get(new slotKey(venues.get(i).get(j - 1), timeSlots.get(i).get(k)));
                    row.createCell(k + 1);
                    if (parameters.contains(slot.getCourse() + " " + slot.getclass())) {
                        Counter.get(k).add(new CellList(i,j, k+1));
                        row.getCell(k + 1).setCellValue(helper.createRichTextString(slot.values()));
                        row.getCell(k + 1).setCellStyle(normal);
                    } 
                    else if(slot.isEmpty() && !(i==4 && k==4))
                    {
//                        row.getCell(k+1).setCellValue(helper.createRichTextString("Unoccupied"));
                        row.getCell(k+1).setCellStyle(highlighted);
                    }
                    else 
                    {
                        row.getCell(k + 1).setCellValue(helper.createRichTextString("\n\n"));
                        row.getCell(k + 1).setCellStyle(normal);
                    }
                    
                }
                for(int k=0;k<timeSlots.get(i).size();k++)
                {
                    day.autoSizeColumn(k);
                }
            }
            for(int m=0;m<Counter.size();m++)
            {
                
                if(Counter.get(m).size() > 1)
                {
  
                    int rndColor = rnd.nextInt(ClashColors.length);
                    System.out.println("COLOR: " + rndColor);
                    clash.setFillForegroundColor(new XSSFColor(ClashColors[rndColor],new DefaultIndexedColorMap()));
                    for(CellList cell : Counter.get(m))
                    {
                        timetable.getSheetAt(cell.getSheet()).getRow(cell.getRow()).getCell(cell.getCol()).setCellStyle(clash);
                    }
                }
            }
        }
        
        try(OutputStream file = new FileOutputStream(filename))
        {
            timetable.write(file);
            Desktop desktop = Desktop.getDesktop();
            desktop.open(new File(filename));
            
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
    }
    public void writeTeachersTimetable(String filename, HashSet<String> parameters)
    {
        XSSFWorkbook timetable = new XSSFWorkbook();
        CreationHelper helper = timetable.getCreationHelper();
        XSSFCellStyle normal = timetable.createCellStyle();
        XSSFCellStyle highlighted = timetable.createCellStyle();
        XSSFCellStyle clash = timetable.createCellStyle();
        normal.setBorderBottom(BorderStyle.THIN);
        normal.setBorderLeft(BorderStyle.THIN);
        normal.setBorderRight(BorderStyle.THIN);
        normal.setBorderTop(BorderStyle.THIN);
        normal.setAlignment(HorizontalAlignment.CENTER);
        normal.setWrapText(true);
        highlighted.cloneStyleFrom(normal);
        
        clash.cloneStyleFrom(normal);

        clash.setFillForegroundColor(IndexedColors.RED.getIndex());
        clash.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont fontNormal = timetable.createFont();
        XSSFFont fontHighlighted = timetable.createFont();
        fontNormal.setFontName("Calibri");
        fontNormal.setFontHeight(11);
        fontHighlighted.setFontName("Calibri");
        fontHighlighted.setFontHeight(11);
        fontHighlighted.setBold(true);
        fontHighlighted.setColor(IndexedColors.BLUE_GREY.getIndex());

        normal.setFont(fontNormal);
        highlighted.setFont(fontHighlighted);
        for(int i=0;i<days.size();i++) {
            XSSFSheet day = timetable.createSheet(dayDict[i]);
            ArrayList<ArrayList<CellList>> Counter = new ArrayList<>(timeSlots.size());
            Row row = day.createRow(0);
            for (int j = 0; j < timeSlots.get(i).size(); j++) {
                Counter.add(new ArrayList<>());
                row.createCell(j + 1).setCellValue(helper.createRichTextString(timeSlots.get(i).get(j)));
            }
            for (int j = 1; j <= venues.get(i).size(); j++) {
                row = day.createRow(j);
                row.createCell(0).setCellValue(helper.createRichTextString(venues.get(i).get(j - 1)));
                for (int k = 0; k < timeSlots.get(i).size(); k++) {
                    Slot slot = days.get(i).get(new slotKey(venues.get(i).get(j - 1), timeSlots.get(i).get(k)));
                    row.createCell(k + 1);
                    System.out.println(slot.values() + "\n\n");
                    if (parameters.contains(slot.getTeacher())) {
                        System.out.println("here with " + venues.get(i).get(j-1) + " " + timeSlots.get(i).get(k));
                        Counter.get(k).add(new CellList(i,j, k+1));
                        row.getCell(k + 1).setCellValue(helper.createRichTextString(slot.values()));
                        row.getCell(k + 1).setCellStyle(normal);
                    } 
                    else if(slot.isEmpty() && !(i==4 && k==4))
                    {
//                        row.getCell(k+1).setCellValue(helper.createRichTextString("Unoccupied"));
                        row.getCell(k+1).setCellStyle(highlighted);
                    }
                    else 
                    {
                        row.getCell(k + 1).setCellValue(helper.createRichTextString("\n\n"));
                        row.getCell(k + 1).setCellStyle(normal);
                    }
                }
                for(int k=0;k<=timeSlots.get(i).size();k++)
                {
                    day.autoSizeColumn(k);
                }
            }
            for(int m=0;m<Counter.size();m++)
            {
                
                if(Counter.get(m).size() > 1)
                {
                    int rndColor = rnd.nextInt(ClashColors.length);
                    clash.setFillForegroundColor(new XSSFColor(ClashColors[rndColor],new DefaultIndexedColorMap()));
                    for(CellList cell : Counter.get(m))
                        timetable.getSheetAt(cell.getSheet()).getRow(cell.getRow()).getCell(cell.getCol()).setCellStyle(clash);
                }
            }
        }
        try(OutputStream file = new FileOutputStream(filename))
        {
            timetable.write(file);
            Desktop desktop = Desktop.getDesktop();
            desktop.open(new File(filename));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        



    } 
    public void writeSectionsTimetable(String filename, HashSet<String> parameters)
    {
        XSSFWorkbook timetable = new XSSFWorkbook();
        CreationHelper helper = timetable.getCreationHelper();
        XSSFCellStyle normal = timetable.createCellStyle();
        XSSFCellStyle highlighted = timetable.createCellStyle();
        XSSFCellStyle clash = timetable.createCellStyle();
        normal.setBorderBottom(BorderStyle.THIN);
        normal.setBorderLeft(BorderStyle.THIN);
        normal.setBorderRight(BorderStyle.THIN);
        normal.setBorderTop(BorderStyle.THIN);
        normal.setAlignment(HorizontalAlignment.CENTER);
        normal.setWrapText(true);
        highlighted.cloneStyleFrom(normal);
        clash.cloneStyleFrom(normal); 
        
        clash.setFillForegroundColor(IndexedColors.RED.getIndex());
        clash.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont fontNormal = timetable.createFont();
        XSSFFont fontHighlighted = timetable.createFont();
        fontNormal.setFontName("Calibri");
        fontNormal.setFontHeight(11);
        fontHighlighted.setFontName("Calibri");
        fontHighlighted.setFontHeight(11);
        fontHighlighted.setBold(true);
        fontHighlighted.setColor(IndexedColors.BLUE_GREY.getIndex());

        normal.setFont(fontNormal);
        highlighted.setFont(fontHighlighted);
        for(int i=0;i<days.size();i++) {
            XSSFSheet day = timetable.createSheet(dayDict[i]);
            ArrayList<ArrayList<CellList>> Counter = new ArrayList<>();
            Row row = day.createRow(0);
            for (int j = 0; j < timeSlots.get(i).size(); j++) {
                Counter.add(new ArrayList<>());
                row.createCell(j + 1).setCellValue(helper.createRichTextString(timeSlots.get(i).get(j)));
            }
            for (int j = 1; j <= venues.get(i).size(); j++) {
                row = day.createRow(j);
                row.createCell(0).setCellValue(helper.createRichTextString(venues.get(i).get(j - 1)));
                for (int k = 0; k < timeSlots.get(i).size(); k++) {
                    Slot slot = days.get(i).get(new slotKey(venues.get(i).get(j - 1), timeSlots.get(i).get(k)));
                    row.createCell(k + 1);
                    System.out.println(slot.getclass() + " ");
                    if (parameters.contains(slot.getclass())) {
                        Counter.get(k).add(new CellList(i,j, k+1));
                        row.getCell(k + 1).setCellValue(helper.createRichTextString(slot.values()));
                        row.getCell(k + 1).setCellStyle(normal);
                    } 
                    else if(slot.isEmpty() && !(i==4 && k==4))
                    {
                        row.getCell(k+1).setCellStyle(highlighted);
                    }
                    else 
                    {
                        row.getCell(k + 1).setCellValue(helper.createRichTextString("\n\n"));
                        row.getCell(k + 1).setCellStyle(normal);
                    }
                }
                for(int k=0;k<=timeSlots.get(i).size();k++)
                {
                    day.autoSizeColumn(k);
                }
            }
            for(int m=0;m<Counter.size();m++)
            {
                
                if(Counter.get(m).size() > 1)
                {
                    int rndColor = rnd.nextInt(ClashColors.length);
                    clash.setFillForegroundColor(new XSSFColor(ClashColors[rndColor],new DefaultIndexedColorMap()));
                    for(CellList cell : Counter.get(m))
                        timetable.getSheetAt(cell.getSheet()).getRow(cell.getRow()).getCell(cell.getCol()).setCellStyle(clash);
                }
            }
        }
        try(OutputStream file = new FileOutputStream(filename))
        {
            timetable.write(file);
            Desktop desktop = Desktop.getDesktop();
            desktop.open(new File(filename));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }



    } 
    private static void readTimetable(XSSFWorkbook workbook){
            Iterator<Sheet> sheeter = workbook.iterator();
            int timeslots = 0;
            while (sheeter.hasNext())
            {
                timeslots=0;
                XSSFSheet sheet = (XSSFSheet)sheeter.next();
                //reading the timeslot values
                XSSFRow timerow = sheet.getRow(3);
                ArrayList<String> timeslot = new ArrayList<String>();
                ArrayList<String> venue = new ArrayList<String>();
                HashMap<slotKey, Slot> slotMap=new HashMap<slotKey, Slot>();
                for (int j = 2; ;  j++)
                {
                    if(timerow.getCell(j) == null || timerow.getCell(j).getCellType() == CellType.BLANK)
                        break;
                    timeslots++;
                    XSSFCell cell = timerow.getCell(j);
                    System.out.println(cell.getStringCellValue());
                    timeslot.add(cell.getStringCellValue());
                }
                //reading the names of venues
                for (int j = 4;; j++) {
                    XSSFRow venuerow = sheet.getRow(j);
                    if(venuerow == null)
                        break;
                    XSSFCell cell = venuerow.getCell(1);
                    if (cell == null || cell.getCellType() == CellType.BLANK) {
                        continue;
                    }
                    System.out.println(cell.getStringCellValue());
                    venue.add(cell.getStringCellValue());
                    for(int k=0;k<timeslots;k++)
                    {
                        slotKey key = new slotKey(cell.getStringCellValue(), timeslot.get(k));
                        XSSFCell slotdata = venuerow.getCell(k+2);
                        if(slotdata == null || slotdata.getCellType() == CellType.BLANK || slotdata.getStringCellValue().equals("Jumma Prayers"))
                        {
                            Slot slot = new Slot();
                            slotMap.put(key, slot);
                            System.out.println("empty");
                        }
                        else
                        {   
                            System.out.println(slotdata.getStringCellValue());
                            String[] slotValues = (slotdata.getStringCellValue()).split("\n");
                            Slot slot = new Slot(slotValues[0], slotValues[1], slotValues[2]);
                            Courses.add(slotValues[0]+" "+slotValues[2]);
                            Teachers.add(slotValues[1]);
                            Sections.add(slotValues[2]);
                            slotMap.put(key, slot);
                            
                        }
                        
                        
                        
                        
                    }
                }
                timeSlots.add(timeslot);
                venues.add(venue);
                days.add(slotMap);

            }
    }
    
}

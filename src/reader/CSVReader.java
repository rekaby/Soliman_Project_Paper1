package reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.naming.PartialResultException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;

import data_transformation.PostRecord;



public class CSVReader  {

	BufferedReader reader;
	private static CSVReader instance = null;
	public static CSVReader getInstance() {
	      if(instance == null) {
	         instance = new CSVReader();
	      }
	      return instance;
	   }
	
	public void ReadInputData()
	{
		Map<Long, PostRecord> dataMap= new HashMap<Long, PostRecord>();
		try {
			XSSFWorkbook xlsxBook = new XSSFWorkbook(new FileInputStream("C:\\Rekaby\\rekaby Sources\\PHD\\Hamburg SDS\\Research\\My Research\\Soliman Integration 1\\Input\\ManualPosts.xlsx"));
			XSSFSheet  sheet = xlsxBook.getSheetAt(0);
		    
		    int rows; // No of rows
		    rows = sheet.getPhysicalNumberOfRows();

		    for(int r = 0; r < rows; r++) {
		    	PostRecord element=CSVReader.getInstance().nextInstance(sheet, r);
		    	
				if(element!=null)
				{
				
					//if(element.getAnswer().contains("\n")||element.getAnswer().contains("\r"))
					//{
						element.setAnswer(element.getAnswer().replaceAll("\n", " "));
						element.setAnswer(element.getAnswer().replaceAll("\r", " "));
						element.setAnswer(element.getAnswer().replaceAll("\'", ""));
						element.setAnswer(element.getAnswer().replaceAll("\"", ""));
						//System.out.println("Answer has \\n");
					
					//}
					//if(element.getTitle().contains("\n")||element.getTitle().contains("\r"))
					//{
						element.setTitle(element.getTitle().replaceAll("\n", " "));
						element.setTitle(element.getTitle().replaceAll("\r", " "));
						element.setTitle(element.getTitle().replaceAll("\'", ""));
						element.setTitle(element.getTitle().replaceAll("\"", ""));
						//System.out.println("Title has \\n");
					//}
					//if(element.getQuestion().contains("\n")||element.getQuestion().contains("\r"))
					//{
						element.setQuestion(element.getQuestion().replaceAll("\n", " "));
						element.setQuestion(element.getQuestion().replaceAll("\r", " "));
						element.setQuestion(element.getQuestion().replaceAll("\'", ""));
						element.setQuestion(element.getQuestion().replaceAll("\"", ""));
						//System.out.println("Question has \\n");
					//}
					if(element.getAnswer().contains("\n")||element.getAnswer().contains("\r"))
					{
						throw new Exception();
					
					}
					if(element.getTitle().contains("\n")||element.getTitle().contains("\r"))
					{
					//	element.getTitle().replace("\n", " ");
						throw new Exception();
					}
					if(element.getQuestion().contains("\n")||element.getQuestion().contains("\r"))
					{
						//element.getQuestion().replace("\n", " ");
						throw new Exception();
					}
					//TODO
				}
				if(dataMap.containsKey(element.getPostId()))
				{
					 String answer=dataMap.get(element.getPostId()).getAnswer();
					dataMap.get(element.getPostId()).setAnswer(answer+" "+element.getAnswer());
				}
				else
				{
					dataMap.put(element.getPostId(), element);
				}
				
		    }
		    
			
			for (int i = 0; i < dataMap.keySet().size(); i++) {
				long id=(Long)dataMap.keySet().toArray()[i];
				PostRecord element=dataMap.get(id);
				System.out.println(element.getPostId()+",'"+element.getTitle()+"','"+element.getQuestion()+"','"+element.getAnswer()+"',"+element.getClassLabel());
			}
			
			
			CSVReader.getInstance().close();
		} catch (Exception e) {
			System.out.println("Reading input data is failed:");
			e.printStackTrace();
		}
		
	}
	
	public boolean startReading(String file) throws IOException {
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
		return true;
	}
	public void close() throws IOException 
	{
		if (reader != null) 
			reader.close(); 
	}
	public PostRecord nextInstance(XSSFSheet sheet, int r) throws IOException {
		try {
		    
			XSSFRow row;
		    XSSFCell cell;
		    long postId;
		    String postTitle;
		    String postQuestion;
		    String postAnswer;
		    long postLabel;
		    
		    
		        row = sheet.getRow(r);
		        if(row != null) {
		           /* for(int c = 0; c < 5; c++) {
		                cell = row.getCell((short)c);
		                if(cell != null) {
		                	System.out.println(cell.getStringCellValue());
		                    // Your code here
		                }
		            }*/
		        	postId=Long.parseLong(row.getCell(0).getRawValue());
		        	postTitle=row.getCell(1).getStringCellValue();
		        	postQuestion=row.getCell(2).getStringCellValue();
		        	postAnswer=row.getCell(3).getStringCellValue();
		        	postLabel=Long.parseLong(row.getCell(4).getRawValue());
		        	
		    	    return new PostRecord(postId,postTitle,postQuestion,postAnswer ,postLabel);
		    	
		        }
		    
		} catch(Exception ioe) {
		    ioe.printStackTrace();
		}
		return new PostRecord();
	}
	public PostRecord nextInstanceOld() throws IOException {
		
	    String[] lstLines =new String[5] ;//= new ArrayList<String[]>();

	    String line = reader.readLine();
	    if (line != null && !line.equals("") && !line.startsWith("*")) 
	    {
	    	lstLines=line.split("\t");
	    	System.out.println(lstLines.length);
	    }
	    
	    if (lstLines.length== 0) return null;
	    
	    int length =lstLines.length;
	    System.out.println("obj:"+Integer.parseInt(lstLines[0]));
	    System.out.println("obj:"+lstLines[1]);
	    System.out.println("obj:"+lstLines[2]);
	    System.out.println("obj:"+lstLines[3]);
	    System.out.println("obj:"+Integer.parseInt(lstLines[4]));
	    
	    return new PostRecord(Integer.parseInt(lstLines[0]),lstLines[1],lstLines[2],lstLines[3],
	    		Integer.parseInt(lstLines[4]));
	}


}

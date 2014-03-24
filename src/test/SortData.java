package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

public class SortData {
	//static String word="flu";
	static String regex="([^\\t]+)\\t([^\\t]+)\\t([^\\t]+)\\t[^\\t]+\\s[^\\t]+\\t[^\\t]+";// 
    
	public static Vector<String> record;
	public static SortedMap<String,Vector<String>> map= new TreeMap<String,Vector<String>>();
	
	public static void main(String[] args) throws Exception { 
		//FirstSort();
        Sort();
    }
	
	public static void Sort(){
		try { 
//			RandomAccessFile rf = new RandomAccessFile("E://sort_log.20080601.decode.filter", "r");
			FileInputStream fis=new FileInputStream("E://trysort_log.20080601.decode.filter");      //文件字节流
	    	InputStreamReader isr=new InputStreamReader(fis,"UTF-8");//字节流和字符流的桥梁，可以指定指定字符格式
	    	BufferedReader br=new BufferedReader(isr);
//			long start = rf.getFilePointer();

	    	String line = ""; 
	    	int i;
	    	String ID = "";
//	    	int i=0;
//            while ((line = br.readLine()) != null) {
//            	record[i]=line;
//            	i++;
//            }
	    	for(i=0;((line=br.readLine())!=null)&&(i<1700000);i++);
	    	System.out.print(i);
	    	for(i=1700000;((line=br.readLine())!=null)&&(i<2100000);i++)
	    	{
//	    	for(start = rf.getFilePointer();start<100;start=rf.getFilePointer()){
//	    		System.out.print(start); 
	    		//line=br.readLine();
	    		System.out.println(line); 
	    		ID=line.split("\\t")[0];
	    		if(map.containsKey(ID)){
	    			record = new Vector<String>();
	    			record=map.get(ID);
	    			record.add(line);
	    			map.put(ID, record);
	    		}
	    		else{
	    			record = new Vector<String>();
	    			record.add(line);
	    			map.put(ID, record);
	    		}
	    	}
	    	System.out.print(i);
//	    	System.out.println("\n第一个元素内容的key："+map.firstKey());
//	        System.out.print("对应的值是\n");
//	        
//	        record=map.get(map.firstKey());
//	        for(i=0;i<record.size();i++)
//	        {
//	        	System.out.println(record.get(i));
//	        }
//	        System.out.println("\n最后一个元素内容的key："+map.lastKey());
//	        System.out.print("对应的值是\n");
//	
//	    	record=map.get(map.lastKey());
//	        for(i=0;i<record.size();i++)
//	        {
//	        	System.out.println(record.get(i));
//	        }
	    	Set set=map.entrySet();
	    	Iterator it=set.iterator();
	    	File csv2 = new File("E://6trySequencedsort_log.20080601.decode.filter"); // CSV�ļ� 
	    	BufferedWriter bw = new BufferedWriter(new FileWriter(csv2,true)); 
	    	
	    	while(it.hasNext())
	    	{
	    		Map.Entry mapentry =(Map.Entry)it.next();
	    		record=(Vector)mapentry.getValue();
	    		for(i=0;i<record.size();i++)
	    	    {
//	    			System.out.println(record.get(i));
//	    			try { 
                        //bw.newLine(); 
                        bw.write(record.get(i)); 
                        bw.newLine(); 
                        //bw.write(URLDecoder.decode(line, "UTF-8"));
                        
//                    } catch (FileNotFoundException e) { 
//                        e.printStackTrace(); 
//                    } catch (IOException e) { 
//                        e.printStackTrace(); 
//                    } 
	    	    }
	    		bw.newLine(); 
	    		//bw.write("\n");
	    	}
	    	bw.close(); 
		}catch (FileNotFoundException e) { 
            // ����File�������ʱ���쳣 
            e.printStackTrace(); 
        } catch (IOException e) { 
            // ����BufferedReader����ر�ʱ���쳣 
            e.printStackTrace(); 
        }
	}
//	public class InsertSort {
//		　　public static void main(String[] args) {
//		　　insertSort(new int[]{8,2,4,9,3,6,7,10});
//		　　}

//		public void dumpArray(String[] a) {
//		　for (int index = 0; index < a.length; index++) {
//		　　System.out.print(a[index] + " ");
//		　}
//		　　System.out.println("");
//		　　}
		public static void insertSort(Vector<String> a) {
//		　　// 输出原始数组
//		　　dumpArray(a);
			for (int index = 1; index < a.size(); index++) {
				int subIndex = index;
				String currentData = a.get(index); // 等待插入的数据
				System.out.print(currentData.split("\\t")[0]);
				System.out.print(Integer.parseInt(a.get(subIndex - 1).split("\\t")[0]));
				
				while ((subIndex > 0) && (Integer.parseInt(a.get(subIndex - 1).split("\\t")[0])> Integer.parseInt(currentData.split("\\t")[0]))) {
					a.add(subIndex, a.get(subIndex - 1)) ;
					subIndex--;
				}
				a.add(subIndex, currentData); // 插入到合适的位置
			// 每次排序后也输出
	//		dumpArray(a);
			}
		}
	
	
    public static void FirstSort(){
    	try { 
        	FileInputStream fis=new FileInputStream("E://SogouQ//access_log22.20080601.decode.filter");      //文件字节流
        	InputStreamReader isr=new InputStreamReader(fis,"UTF-8");//字节流和字符流的桥梁，可以指定指定字符格式
        	BufferedReader br=new BufferedReader(isr);
        	//File csv = new File("E://SogouQ//access_log.20080601.decode.filter"); // CSV�ļ�
            //BufferedReader br = new BufferedReader(new FileReader(csv));
            // ��ȡֱ�����һ�� 
            String line = ""; 
            while ((line = br.readLine()) != null) { 

                PatternCompiler compiler = new Perl5Compiler();                       // �����ʽ�Ƚ��������                  
                PatternMatcher matcher = new Perl5Matcher();                       // ʵ���С��Сд���е������ʽģ��                
        		org.apache.oro.text.regex.Pattern pattern = compiler.compile(regex);
        		
//                Pattern pattern = Pattern.compile(regex);
//                Matcher matcher = pattern.matcher(line);
//                MatchResult result=matcher.toMatchResult();
                if (matcher.contains(line,pattern)) {
                	 try { 
                		 System.out.println(line);
                		 //File csv2 = new File("E://sortall_log.20080601.decode.filter");
                         File csv2 = new File("E://trysort_log.20080601.decode.filter"); // CSV�ļ� 
                         // ׷��ģʽ 
                         BufferedWriter bw = new BufferedWriter(new FileWriter(csv2,true)); 
                         // ����һ����� 
                         bw.newLine(); 
                         org.apache.oro.text.regex.MatchResult result=matcher.getMatch();
                         System.out.println(result.group(2)+"\t"+result.group(1)+"\t"+result.group(3));
                         //System.out.println(result.group(1)+"\t"+result.group(2)+"\t"+result.group(3));
                         bw.write(result.group(2).replaceAll("\\s*", "")+"\t"+result.group(1).replaceAll("\\s*", "")+"\t"+result.group(3).replaceAll("\\s*", "")); 
                         //bw.write(URLDecoder.decode(line, "UTF-8"));
                         bw.close(); 
                     } catch (FileNotFoundException e) { 
                         e.printStackTrace(); 
                     } catch (IOException e) { 
                         e.printStackTrace(); 
                     } 
                } 
          }     
            br.close();
        } catch (FileNotFoundException e) { 
            // ����File�������ʱ���쳣 
            e.printStackTrace(); 
        } catch (IOException e) { 
            // ����BufferedReader����ر�ʱ���쳣 
            e.printStackTrace(); 
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
}



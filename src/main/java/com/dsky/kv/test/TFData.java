package com.dsky.kv.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

public class TFData {
	public static void main(String[] args) {
		String filepath = System.getProperty("user.dir");     
        filepath +="\\movie_lines_selected.txt";
        System.out.println(filepath);  
       // /*  
        try   
        {  
            File file = new File(filepath);           
            if(!file.exists())  
            {   //如果不存在data.txt文件则创建  
                file.createNewFile();  
                System.out.println("movie_lines_selected.txt创建完成");               
            }  
            FileWriter fw = new FileWriter(file);       //创建文件写入  
            BufferedWriter bw = new BufferedWriter(fw);  
              
            //产生随机数据，写入文件  
            Random random = new Random();  
            for(int i=0;i<150000;i++)  //生成10000对问答
            {
            	int lineLenA = (int)Math.floor((random.nextDouble()*25.0));//每行的最大长度25
            	if(lineLenA ==0 )
            		lineLenA =1;
            	String strA="";
            	for(int j=0;j<lineLenA;j++) {
                    int randint =(int)Math.floor((random.nextDouble()*10000.0));   //产生0-10000之间随机数   
                    
                    if(lineLenA -j >1) {
                    	strA += String.valueOf(randint)+" ";
                    	
                    }else {
                    	strA += String.valueOf(randint);
                    }
            	}
                bw.write(strA);
                bw.newLine();
       //新的一行
               int lineLenB = (int)Math.floor((random.nextDouble()*25.0));//每行的最大长度25
               if(lineLenB == 0)
            	   lineLenB = 1;
               String strB="";
               for(int k=0;k<lineLenB;k++) {
                    int randintB =(int)Math.floor((random.nextDouble()*10000.0));   //产生0-10000之间随机数          
                    if(lineLenB -k >1) {
                        strB += String.valueOf(randintB)+" ";
                    }else {
                        strB += String.valueOf(randintB);
                    }
               }
               bw.write(strB);
               bw.newLine();
            		
           }

  
            bw.close();  
            fw.close();  
              
        }   
        catch (Exception e)   
        {  
            e.printStackTrace();  
        } 
        //*/ 
	}

}

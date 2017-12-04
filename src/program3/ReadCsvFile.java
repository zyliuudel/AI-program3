package program3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import program3.Node;

public class ReadCsvFile {
	public static void main(String[] args) { 
        try { 
        	BufferedReader getcolnum = new BufferedReader(new FileReader("\\D:\\lzy\\lzy\\CIS\\681\\program3\\balloon\\adult-stretch.data"));
        	String oneline[] = getcolnum.readLine().split(",");
        	int colnum = oneline.length;
        	int featurenum = colnum-1;
        	getcolnum.close();
        	
        	BufferedReader getlinenum = new BufferedReader(new FileReader("\\D:\\lzy\\lzy\\CIS\\681\\program3\\balloon\\adult-stretch.data"));
        	String getlines = null;
        	int linenum = 0;
        	while((getlines=getlinenum.readLine())!=null)
        	{            
        		linenum++;
            } 
        	getlinenum.close();
        	
        	System.out.println(linenum);
            System.out.println(colnum);
            System.out.println(featurenum);
        	
            BufferedReader reader = new BufferedReader(new FileReader("\\D:\\lzy\\lzy\\CIS\\681\\program3\\balloon\\adult-stretch.data"));
            String line = null;
            
            // Get all data from csv file, stored as a 2D array of doubles 
            double[][] data = new double[linenum][colnum]; 
            
            int cur_line = 0;
            while((line=reader.readLine())!=null){ 
                String item[] = line.split(",");
                for (int i =0; i<item.length;i++)
                {
                	String newitem = item[i].replaceAll("\\s","");
                	data[cur_line][i] = Double.parseDouble(newitem);
                }
            	cur_line++;
            } 
            
            // An arraylist for all lines of data
            ArrayList<double []> datas = new ArrayList<double []>();            
            for (int i =0; i<linenum;i++)
            {
            	double[] onelinedata = new double[colnum];
            	for (int j = 0; j<colnum; j++)
            	{
            		onelinedata[j] = data[i][j];
            	}
            	datas.add(onelinedata);
            }
            
            // An arraylist for all coloums of features
            ArrayList<double []> features = new ArrayList<double []>();
            for (int i =0; i<colnum;i++)
            {
            	double[] onecoldata = new double[linenum];
            	for(int j = 0; j<linenum; j++)
            	{
            		onecoldata[j] = data[j][i];
            	}
            	features.add(onecoldata);
            }
            
           Node test = new Node();
           test.data = datas;
           test.depth = 0;
           Node.split(test,featurenum);
           
           Node left = test.leftchild;
           Node right = test.rightchild;
           
           ArrayList<double[]>ldata = left.data;
           ArrayList<double[]>rdata = right.data;
           
           for(int i=0;i<ldata.size();i++)
           {	
        	   for(int j=0;j <colnum; j++)
        	   {
        		   System.out.print(ldata.get(i)[j]+",");
        	   }
        	   System.out.println();
           }
           System.out.println(ldata.size());
           System.out.println(rdata.size());
           System.out.println(test.maxgainsplitIndex);
           System.out.println(test.maxgainsplitvalue);
           
           
            
            
            
            
            
            // Get all possible split points of a feature in a arraylist, and put arraylists of different features in a large arraylist
            /*ArrayList<ArrayList<Double>> featureSplitPoints = new ArrayList<ArrayList<Double>>();
            for (int i = 0; i<features.size(); i++)
            {	
            	ArrayList<Double> onecol= new ArrayList<Double>();
            	for(int j = 0; j<linenum; j++)
            	{
            		if(onecol.contains(((double[])features.get(i))[j]) != true)
            		{
            			onecol.add(((double[])features.get(i))[j]);
            		}
            	}
            	featureSplitPoints.add(onecol);
            }
            
             //Test for all split points in a feature
            for(int i = 0; i<((ArrayList<Double>)(featureSplitPoints.get(20))).size(); i++ )
        	{
            	System.out.println( (double)(((ArrayList<Double>)(featureSplitPoints.get(20))).get(i)) );
        	}
            System.out.println(((ArrayList<Double>)(featureSplitPoints.get(20))).size());
        	
            
            /* Test for a column of feature
            for(int j = 0; j<linenum; j++ )
        	{
        		System.out.print(((double[])onefeature.get(0))[j]+",");
        	}
            */
                      
            /* Test a line of data
            for(int j = 0; j<colnum; j++ )
        	{
        		System.out.print(((double[])onedata.get(0))[j]+",");
        	}*/
            
            
            /* Test all data           
            for (int i = 0; i<linenum;i++)
            {
            	for(int j = 0; j<colnum;j++)
            	{
            		System.out.println(data[i][j]);
            	}
            }*/
            
            
            
            reader.close();           
         
            
            
            
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
}

package program3;

import java.util.ArrayList;

public class Node {

	public ArrayList<double []> data;
	public Node parent;
	public Node leftchild;
	public Node rightchild;
	public int depth;
	public int maxgainsplitIndex;
	public double maxgainsplitvalue;
	
	
	/*public Node(ArrayList<double []> ndata, Node nparent, Node nleftchild, Node nrightchild, int ndepth)
	{
		this.data = ndata;
		this.parent = nparent;
		this.leftchild = nleftchild;
		this.rightchild = nrightchild;
		this.depth = ndepth;	
	}*/	
	
    public static ArrayList<Node> split(Node current, int featurenum)
    {
    	ArrayList<double []> data = current.data;
    	
    	
    	
    	ArrayList<Double> classification = new ArrayList<Double>();
    	
    	// If this node only contains one or less data, do not split.
    	if(data.size() <= 1)
    	{
    		current.leftchild = null;
    		current.rightchild = null;
    		return null;    		
    	}
    	
    	else 
    	{
    		// Get the last bit of each data, which is the class it belongs to, and check if they all belongs to the same class.
    		for(int i = 0; i<data.size();i++)
    		{
    			//The index of the last bit: since total columns = featurenum+1, the last bit's index will be featurenum, and it is the class it belongs to
    			double lastbit = data.get(i)[featurenum];
    			if(classification.contains(lastbit) == false)
    			{
    				classification.add(lastbit);
    			}
    		}
    		
    		// If all data belongs to the same class, do not split
    		if (classification.size()<=1)
    		{
    			current.leftchild = null;
        		current.rightchild = null;
        		return null;    
    		}
    		
    		else
    		{
    			ArrayList<ArrayList<Double>> splitpoints = getAllSplitPoint(current,featurenum);
    			double [] infogain_splitpoint = new double[splitpoints.size()];
    			int[] maxinfosplitIndex = new int[2];
    			double mininfogain = Double.MAX_VALUE;
    			
    			// i:the ith feature; j: the jth splitpoint in ith feature; k:the kth data in the dataset
    			for (int i = 0; i<featurenum; i++)
    			{
    				ArrayList<Double> onefeaturesplitpoint = splitpoints.get(i);
    				
    				for(int j =0;j<onefeaturesplitpoint.size();j++)
    				{
    					// For data k; 
    					//p1: k.feature[i]<splitpoints[j] && k.class = 0
    					//n1: k.feature[i]<splitpoints[j] && k.class = 1
    					//p2: k.feature[i]>=splitpoints[j] && k.class = 0
    					//n2: k.feature[i]>=splitpoints[j] && k.class = 1
    					
    					double p1 = 0;
    					double n1 = 0;
    					double p2 = 0;
    					double n2 = 0;
    					
    					for(int k = 0; k < data.size();k++)
    					{
    						if(data.get(k)[i]<onefeaturesplitpoint.get(j))
    						{
    							if(data.get(k)[featurenum]==8) {p1++;}
    							else {n1++;}
    						}
    						else
    						{
    							if(data.get(k)[featurenum]==9) {p2++;}
    							else {n2++;}
    						}
    					}
    					double infogain = getinfogain(p1,n1,p2,n2);
    					if(infogain<mininfogain)
    					{
    						mininfogain = infogain;
    						// i: feature i at ith column
    						maxinfosplitIndex[0] = i;
    						// j: split point j of feature i which has maximum infogain
    						maxinfosplitIndex[1] = j;
    					}
    				}
    			}
    			
    			Node leftchild = new Node();
    			Node rightchild = new Node();
    			
    			ArrayList<double[]> leftchilddata = new ArrayList<double[]>();
    			ArrayList<double[]> rightchilddata = new ArrayList<double[]>();
    			
    			for(int i = 0; i<data.size();i++)
    			{
    				if(data.get(i)[maxinfosplitIndex[0]] < maxinfosplitIndex[1])
    				{
    					leftchilddata.add(data.get(i));
    				}
    				else
    				{
    					rightchilddata.add(data.get(i));
    				}
    			}
    			
    			leftchild.data = leftchilddata;
    			rightchild.data = rightchilddata;
    			
    			leftchild.depth = current.depth +1;
    			rightchild.depth = current.depth +1;
    			
    			leftchild.parent = current;
    			rightchild.parent = current;
    			current.leftchild = leftchild;
    			current.rightchild = rightchild;
    			current.maxgainsplitIndex = maxinfosplitIndex[0];
    			current.maxgainsplitvalue = maxinfosplitIndex[1];
    			
    			ArrayList<Node> childrenlist = new ArrayList<Node>();
    			childrenlist.add(leftchild);
    			childrenlist.add(rightchild);
    			
    			return childrenlist;    			
    		}
    	}
    }
    
    public static double getinfogain(double p1, double n1, double p2, double n2) 
    {
    	double infogain1 = 0;
	 	double infogain2 = 0;
	 	
	 	if(p1 == 0 || n1 ==0)
	 	{
	 		infogain1 = 0;
	 	}
	 	else
	 	{
	 		infogain1 = ((p1+n1)/(p1+n1+p2+n2))*(-(p1/(p1+n1))*(Math.log(p1/(p1+n1))/(Math.log(2)))-(n1/(p1+n1))*(Math.log(n1/(p1+n1))/(Math.log(2))));
	 	}
	 	
	 	if (p2 == 0 || n2 ==0)
	 	{
	 		infogain2 = 0;
	 	}
	 	else
	 	{
	 		infogain2 = ((p2+n2)/(p1+n1+p2+n2))*(-(p2/(p2+n2))*(Math.log(p2/(p2+n2))/(Math.log(2)))-(n2/(p2+n2))*(Math.log(n2/(p2+n2))/(Math.log(2))));
	 	}

    	double infogain = infogain1 + infogain2;
		return infogain;
	}

	//Get all split points in the data of a node
    public static ArrayList<ArrayList<Double>> getAllSplitPoint(Node node, int featurenum)
    {
    	ArrayList<double []> data = node.data;
    	ArrayList<ArrayList<Double>> splitpoints = new ArrayList<ArrayList<Double>>();
    	
    	if (data.size() == 0)
    	{
    		return null;
    	}
    	
    	else
    	{
    		for (int j=0; j< featurenum; j++)
    		{
    			ArrayList<Double> onefeature= new ArrayList<Double>();
    			for(int i =0; i<data.size();i++)
    			{   				
    				if(onefeature.contains(data.get(i)[j]) != true)
    				{
    					onefeature.add(data.get(i)[j]);    					
    				}
    			}
    			splitpoints.add(onefeature);
    		}
    		return splitpoints;
    	}
    }
}

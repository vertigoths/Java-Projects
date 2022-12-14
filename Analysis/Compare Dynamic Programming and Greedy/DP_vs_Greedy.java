import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Musahan_Sever_2017510114
{
	public static void main(String[] args) throws FileNotFoundException 
	{
		HashMap<String,Integer> lions = new HashMap<>();
		
		HashMap<String,LinkedList<String>> kids = new HashMap<>();	//kid -> parent(s)
		HashMap<String,LinkedList<String>> parents = new HashMap<>();	//parent -> kid(s)
		HashMap<String,LinkedList<String>> siblings = new HashMap<>();	//person -> sibling(s)
		
		readHuntingAbilities(lions);
		readLionsHierarchy(siblings,kids);
	
		for(String key : kids.keySet()) //fill the parents map
		{
			for(String parent : kids.get(key)) 
			{
				LinkedList<String> currentKids = parents.get(parent);
				if(currentKids == null) 
				{
					currentKids = new LinkedList<>();
				}
				
				currentKids.add(key);
				parents.put(parent, currentKids);
			}
		}
		
		System.out.println("Greedy: " + greedy(lions,kids,parents));
		readHuntingAbilities(lions);	//since lions map is cleared, it's need to be filled again
		System.out.println("--------------");
		System.out.println("DP: " + dp(lions,kids,parents));
	}
	
	public static void readHuntingAbilities(HashMap<String,Integer> map) throws FileNotFoundException
	{
		Scanner file=new Scanner(new File("C:\\Users\\zombi\\Desktop\\CME-2204 Algorithm Analysis\\HW2\\hunting_abilities.txt"),"UTF-8");
		file.nextLine();
		while(file.hasNext()) 
		{
			String[] line=file.nextLine().split("\\s+");
			map.put(line[0], Integer.parseInt(line[1]));
		}
		file.close();
	}
	
	public static void readLionsHierarchy(HashMap<String,LinkedList<String>> siblings, HashMap<String,LinkedList<String>> kids) throws FileNotFoundException
	{
		Scanner file=new Scanner(new File("C:\\Users\\zombi\\Desktop\\CME-2204 Algorithm Analysis\\HW2\\lions_hierarchy.txt"),"UTF-8");
		file.nextLine();
		while(file.hasNext()) 
		{
			String[] line=file.nextLine().split("\\s+");
			
			// line[0] either can be parent or sibling, line[1] either can be kid or sibling
			
			if(line[2].equals("Left-Child")) 
			{	
				LinkedList<String> currentParents = kids.get(line[1]);	// Get parents of kid (Assuming there can be more than one parent)

				if(currentParents == null)	//if this this kid doesn't have parents yet, create list.
				{
					currentParents = new LinkedList<String>();
				}
				
				currentParents.add(line[0]);	//add parent
					
				kids.put(line[1], currentParents);	//Change the content of kids map
			}
			
			else 
			{
				LinkedList<String> currentSiblings = siblings.get(line[0]);	//Get siblings of line[0] 
				
				if(currentSiblings == null) //if siblings doesn't exist, create list
				{
					currentSiblings = new LinkedList<String>();
				}
				
				LinkedList<String> siblingsOfSecond = siblings.get(line[1]);	//Get  siblings of second
				
				if(siblingsOfSecond == null) //again
				{
					siblingsOfSecond = new LinkedList<String>();
				}
				
				for(String sibling : currentSiblings) 	//For each sibling in siblings of first
				{
					siblingsOfSecond.add(sibling);	//add sibling to line[1]
						
					siblings.get(sibling).add(line[1]);	//add line[1] to siblings of line[0]
				}
				
				// There may be second for which will iterate over siblings of second
				
				LinkedList<String> parentsOfSecond = kids.get(line[1]);	//Get parents of second
				
				if(parentsOfSecond == null) 
				{
					parentsOfSecond = new LinkedList<String>();
				}
				
				if(kids.get(line[0]) != null)	//if line[0] has parents, so it means line[1].parent = line[0].parent
				{
					for(String parent : kids.get(line[0])) 
					{
						parentsOfSecond.add(parent);	//Add parent
						
						//There can be check if second line has parent but first doesn't
					}
				}

				currentSiblings.add(line[1]);	//add sibling to line[0]
				siblingsOfSecond.add(line[0]);	//add sibling to line[1]
				
				siblings.put(line[0], currentSiblings);	//Change the contents of siblings map
				
				siblings.put(line[1], siblingsOfSecond);	//Change the contents of siblings map
				
				kids.put(line[1], parentsOfSecond);	//Change the contents of kids map
			}
		}
		file.close();
	}

	public static int greedy(HashMap<String,Integer> map, HashMap<String,LinkedList<String>> kids, HashMap<String,LinkedList<String>> parents) 
	{
		String currentMaxLion = "";	//will store sequential strongest lion
		int currentMaxAbility = 0;	//ability of sequential strongest lion
		int sum = 0;	//total max ability will be achieved
		
		while(map.size() != 0) 	//do until there is no lion left
		{	
			for(String key : map.keySet()) //find current strongest lion in map
			{
				if(map.get(key) > currentMaxAbility) 
				{
					currentMaxAbility = map.get(key);
					currentMaxLion = key;
				}
			}
			
			if(kids.get(currentMaxLion) != null) 
			{
				for(String parent : kids.get(currentMaxLion)) //remove parents of strongest lion
				{
					map.remove(parent);
				}
			}
			
			if(parents.get(currentMaxLion) != null) 
			{
				for(String kid : parents.get(currentMaxLion)) //remove kids of strongest lion
				{
					map.remove(kid);
				}
			}
			
			map.remove(currentMaxLion);	//remove strongest lion from map
			
			sum += currentMaxAbility;	//add ability
			
			System.out.println(currentMaxAbility + " " + currentMaxLion);
			
			currentMaxAbility = 0;
		}
		
		return sum;
	}

	public static String getRoot(HashMap<String,Integer> map, HashMap<String,LinkedList<String>> kids) 
	{
		//Returns what is in the root
		for(String key : map.keySet()) 
		{
			if(kids.get(key) == null) 
			{
				return key;
			}
		}
		
		return null;
	}

	public static LinkedList<String> getWhatsInDeep(HashMap<String,LinkedList<String>> parents, HashMap<String,Integer> map)
	{
		//Returns what is in the last level
		LinkedList<String> list = new LinkedList<>();
		for(String key : map.keySet()) 
		{
			if(parents.get(key) == null) 
			{
				list.add(key);
			}
		}
		
		return list;
	}

	public static int dp(HashMap<String,Integer> map, HashMap<String,LinkedList<String>> kids, HashMap<String,LinkedList<String>> parents) 
	{
		LinkedList<String> leafs = getWhatsInDeep(parents,map);	//lions without kids, we start there
		HashMap<String,Integer> currentDP = new HashMap<>();	//storage for previous states
		String root = getRoot(map,kids);	//root lion, assuming only one
		
		HashMap<String,Character> parentOrChild = new HashMap<>(); //P or C map
		
		for(String lion : leafs) 
		{
			currentDP.put(lion,map.get(lion));	//write down the leaf
			parentOrChild.put(lion,'P');
		}
		
		while(currentDP.get(root) == null)  //if we assign root to map, it means algorithm finished.
		{
			LinkedList<String> temp = new LinkedList<>(); 
			
			for(String lion : leafs) //leafs means lastly visited lions
			{
				String parent = kids.get(lion).get(0);	//get parent of lastly visited lion	
				
				if(isAllKidsVisited(parent,currentDP,parents)) 	//if all kids isn't visited move next because we know that there will be another chance to visit
				{
					int sumOfGrandKidsPlusCurrent = map.get(parent) + getSumOfGrandKids(parent,currentDP,parents);	//sum of lion and it's grands
					
					int sumOfKids = getSumOfKids(parent,currentDP,parents);	//sum of kids
					
					if(sumOfGrandKidsPlusCurrent >= sumOfKids) 
					{
						parentOrChild.put(parent, 'P');
					}
					
					else 
					{
						parentOrChild.put(parent, 'C');
					}
					
					currentDP.put(parent, Math.max(sumOfGrandKidsPlusCurrent, sumOfKids));	//as can be seen here algorithm depends or either choose sumOfGrandsPlusLion or sumOfKids
					
					temp.add(parent);	//since we need to visit this parent add to list which will be leafs later
				}
			}
			
			leafs = temp;
		}
		
		for(String lion : parentOrChild.keySet()) 
		{
			char ch = parentOrChild.get(lion);
			
			if(ch == 'P' && parents.get(lion) != null) //if it's P and not leaf node then it's chosen
			{
				System.out.println(map.get(lion) + " " + lion);
			}
			
			else if(ch == 'P' && parents.get(lion) == null && parentOrChild.get(kids.get(lion).get(0)) == 'C') //if it's P, leaf node and parents is C, it's chosen
			{
				System.out.println(map.get(lion) + " " + lion);
			}
		}
		
		return currentDP.get(root);	  //return root of map
	}

	public static boolean isAllKidsVisited(String parent, HashMap<String,Integer> currentDP, HashMap<String,LinkedList<String>> parents) 
	{
		for(String kid : parents.get(parent)) 
		{
			if(currentDP.get(kid) == null) 
			{
				return false;
			}
		}
		
		return true;
	}

	public static int getSumOfGrandKids(String lion, HashMap<String,Integer> currentDP, HashMap<String,LinkedList<String>> parents) 
	{
		int sum = 0;
		
		for(String kid : parents.get(lion)) 
		{
			if(parents.get(kid) != null) 
			{
				for(String grandKid: parents.get(kid)) 
				{
					sum += currentDP.get(grandKid);
				}
			}
		}
		
		return sum;
	}

	public static int getSumOfKids(String lion, HashMap<String,Integer> currentDP, HashMap<String,LinkedList<String>> parents) 
	{
		int sum = 0;
		
		for(String kid : parents.get(lion)) 
		{
			sum += currentDP.get(kid);
		}
		
		return sum;
	}
}

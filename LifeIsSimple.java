import java.util.*;
import java.io.*;

/*	This program simulates a colony of cells lifecycle.  It takes a txt file with a colony, a 6x7 grid of dashes(-) and X's
	where an X is a cell and a dash is an empty space. The second argument is an integer of the number of turns you want to see performed.*/

public class LifeIsSimple
{
	public static void main(String [] args)
	{
		if(args.length < 2)
		{
			System.out.println("Please enter a file name and an integer.");
			System.exit(0);
		}
		int turns = Integer.parseInt(args[1]);
		String[][] colony = new String[5][7];
		
		//First, read the file and set up the 2D array of the colony
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
			String line;
			for(int i = 0; i < colony.length; i++)
			{
				line = reader.readLine();
				colony[i] = line.split(" ");
			}
			reader.close();
		}
		catch(Exception e)
		{
			System.err.format("Exception occured while reading " + args[0]);
		}
		displayColony(colony);
		
		//Loop through the colony for each turn
		for(int i = 0; i < turns; i++)
		{
			System.out.println("Turn - " + (i+1));
			nextTurn(colony);
			displayColony(colony);
		}
	}
	
	//Function to handle each turn.
	public static void nextTurn(String [][] a)
	{
		int neighbors;
		
		//Determine the number of neighbors each cell has and mark where new cells will be born(B), and where old cells will die(D)
		//A neighbor = A living cell directly next to a space.
		for(int i = 0; i < a.length; i++)
		{
			for(int j = 0; j < a[i].length; j++)
			{
				neighbors = 0;
				int y = j, x = i;
				if((x-1) >= 0 && (y-1) >= 0 && (a[x-1][y-1].equals("X") || a[x-1][y-1].equals("D")))
					neighbors = neighbors + 1;
				if((y-1) >= 0 && (a[x][y-1].equals("X") || a[x][y-1].equals("D")))
					neighbors = neighbors + 1;
				if((x+1) < a.length && (y+1) < a[i].length && (a[x+1][y+1].equals("X") || a[x+1][y+1].equals("D")))
					neighbors = neighbors + 1;
				if((x+1) < a.length && (a[x+1][y].equals("X") || a[x+1][y].equals("D")))
					neighbors++;
				if((x+1) < a.length && (y-1) >= 0 && (a[x+1][y-1].equals("X") || a[x+1][y-1].equals("D")))
					neighbors = neighbors + 1;
				if((y+1) < a[i].length && (a[x][y+1].equals("X") || a[x][y+1].equals("D")))
					neighbors = neighbors + 1;
				if((x-1) >= 0 && (y+1) < a[i].length && (a[x-1][y+1].equals("X") || a[x-1][y+1].equals("D")))
					neighbors = neighbors + 1;
				if((x-1) >= 0 && (a[x-1][y].equals("X") || a[x-1][y].equals("D")))
					neighbors = neighbors + 1;
				
				if((neighbors > 3 || neighbors < 2) && a[i][j].equals("X")) //A cell dies if it has more than three or less than 2 neighbors
					a[i][j] = "D";
				else if(neighbors == 3 && a[i][j].equals("-")) //A new cell is born if an empty space has exactly three neighbors
					a[i][j] = "B";
			}
		}
		
		//Loop through once more and mark newly born cells as an X and remove dead cells
		for(int i = 0; i < a.length; i++)
		{
			for(int j = 0; j < a[i].length; j++)
			{
				if(a[i][j].equals("D"))
					a[i][j] = "-";
				else if(a[i][j].equals("B"))
					a[i][j] = "X";
			}
		}
	}
	
	//Function to display the colony in it's current state
	public static void displayColony(String [][] a)
	{
		for(int i = 0; i < a.length; i++)
		{
			for(int j = 0; j < a[i].length; j++)
				System.out.print(a[i][j] + " ");
			System.out.println();
		}
	}
}
import java.util.Scanner;

/**
   This tests the Runway class and simulates a one runway airport. 
   @author Jeremy Hill
   @version 12.0.2
 */

public class Airport
{
	static int landTime;          //holds the time it takes to land
	static int takeoffTime;	      //holds the time it takes to takeoff
	static int totalTime;		  //holds the total time of the simulation

	//holds the time a plane can be in the landingQueue without crashing
	static int crashTime;		  
	//holds the probability that a plane will be arriving for a given minute
	static double arrivalProb;
	//holds the probability that a plane will be departing for a given minute
	static double departureProb;

    //holds the total time planes spent in the landingQueue
    static int timeInLandingQueue = 0;
    //holds the total time planes spent in the takeoffQueue
    static int timeInTakeoffQueue = 0;

    static int planesLanded = 0;     //holds the number of planes that landed
    static int planesDepart = 0;     //holds the number of planes that departed
    static int planesCrashed = 0;    //holds the number of planes that crashed


    /** 
     * The main method simply call two methods and prints the results of the
     * simulation to the terminal. First it calls runStartUpCode which asks
     * the user to enter the data for the simulation. It then calls the 
     * runSimulation methods which runs the actual simulation. When the 
     * simulation is over the main method prints out its results. 
     * @param args Accepts a string array as an argument
     */
   @SuppressWarnings("unchecked")
   public static void main(String[] args)
   {
   		runStartUpCode();
	    
        runSimulation();
	    
        System.out.println("The number of planes that took off in the " + 
        				   "simulated time: " + planesDepart);
        System.out.println("The number of planes that landed in the " + 
        				   "simulated time: " + planesLanded);
        System.out.println("The number of planes that crashed because they "+ 
        				   "ran out of fuel before they could land: " + 
        				   planesCrashed);
     	System.out.println("The average time that a plane spent in the " + 
     					   "takeoff queue: " + 
     					   (planesDepart == 0 ? 0.0 : timeInTakeoffQueue /
     					   (double)planesDepart));
        System.out.println("The average time that a plane spent in the " + 
        				   "landing queue: " + 
        				   (planesLanded == 0 ? 0.0 : timeInLandingQueue /
        				   (double)planesLanded));
    }

    /** 
     * The runSimulation method is what actually runs the airport simulation. 
     * There is a large for loop that loop for the entire duration that the 
     * user selects. The for loop has two inner loops for whenever a plane is
     * leaving either one of the queues. The inner loop runs for the duration 
     * of the dequeue and then adds that time to the main loop iterator so 
     * the time for dequeue is accounted for. 
     * Precondition: landTime is an integer > 0, takeOffTime is an integer > 0,
     * arrivalProb is a     double between 0 and 1, departureProb is a double 
     * between 0 and 1, crashTime is     an integer > 0, totalTime is an 
     * integer > 0.
     * Postcondition: The method will run a one runway airport simulation. 
     */
    public static void runSimulation()
    {
        @SuppressWarnings("unchecked")
        LinkedQueue<Plane> landingQueue = new LinkedQueue();
        @SuppressWarnings("unchecked")
        LinkedQueue<Plane> takeoffQueue = new LinkedQueue();

        Runway run = new Runway();  //creates a Runway object
        
        for(int clock = 1; clock < totalTime; clock++)
        {
            //checks if a plane should be added to landingQueue
            if(run.isPlaneComingIntoQueue(arrivalProb))
            {
                Plane temp = new Plane(clock);
                landingQueue.enqueue(temp);
            }

            //checks if a plane should be added to takeoffQueue
            if(run.isPlaneComingIntoQueue(departureProb))
            {
                Plane temp = new Plane(clock);
                takeoffQueue.enqueue(temp);
            }

            //checks if a plane has crashed, if so increment planesCrashed
            while(!landingQueue.isEmpty() && 
                  run.isPlaneCrashed(landingQueue.getFront().getTime(), clock, crashTime))
            {
                landingQueue.dequeue();
                planesCrashed++;
            }

            //checks if landingQueue is not empty, if so remove the top plane
            if(!landingQueue.isEmpty())
            {
                timeInLandingQueue += (clock - landingQueue.getFront().getTime());
                landingQueue.dequeue();
                planesLanded++;             //increment planesLanded
                int i;                      //holds temp clock
                    
                    //checks for additional planes to be added while dequeueing
                    for(i = clock; i < landTime + clock && i < totalTime; i++)
                    {
                        //checks if a plane should be added to landingQueue
                        if(run.isPlaneComingIntoQueue(arrivalProb))
                        {
                            Plane tempL = new Plane(i);
                            landingQueue.enqueue(tempL);
                        }

                        //checks if a plane should be added to takeoffQueue
                        if(run.isPlaneComingIntoQueue(departureProb))
                        {
                            Plane tempL = new Plane(i);
                            takeoffQueue.enqueue(tempL);
                        }
                    }

                clock += landTime;              //synchronize clock
            }
            //if landingQueue is empty, checks if takeoffQueue is not empty
            else if (!takeoffQueue.isEmpty()) 
            {
                timeInTakeoffQueue += (clock - takeoffQueue.getFront().getTime());
                takeoffQueue.dequeue();
                planesDepart++;                 //increment planesDepart
                int i;                          //holds temp clock

                    //checks for additional planes to be added while dequeueing
                    for(i = clock; i < takeoffTime + clock && i < totalTime;
                        i++)
                    {
                        //checks if a plane should be added to landingQueue
                        if(run.isPlaneComingIntoQueue(arrivalProb))
                        {
                            Plane tempT = new Plane(i);
                            landingQueue.enqueue(tempT);
                        }

                        //checks if a plane should be added to takeoffQueue
                        if(run.isPlaneComingIntoQueue(departureProb))
                        {
                            Plane tempT = new Plane(i);
                            takeoffQueue.enqueue(tempT);
                        }
                    }
                clock += takeoffTime;           //synchronize clock
            } 
        }
    }


    /**
	 * runStartUpCode simply asks the user to input data for the simulation 
	 * to utilize. It then calls the isValid method to validate the user's 
	 * input.
	 */
    public static void runStartUpCode()
    {
    	Scanner in = new Scanner(System.in);
	    System.out.print("The amount of time needed for one plane " + 
	    				 "to land (in minutes): ");
	    landTime = in.nextInt();
	    System.out.print("The amount of time needed for one plane " + 
	    				 "to take off (in minutes): ");
	    takeoffTime = in.nextInt();
	    System.out.println("The probability of an arrival of a plane " + 
	    				   "into the landing queue ");
	    System.out.print("(this will be a decimal number between 0 & 1): ");
	    arrivalProb = in.nextDouble();
	    System.out.println("The probability of an arrival of a plane into " + 
	    				   "the takeoff queue ");
	    System.out.print("(this will be a decimal number between 0 & 1): ");
	    departureProb = in.nextDouble();
	    System.out.println("The maximum amount of time that a plane can " +
	    				   "stay in the landing queue ");
	    System.out.print("without running out of fuel and crashing " +
	    				 "(in minutes): ");
	    crashTime = in.nextInt();
	    System.out.print("The total length of time to be simulated " +
	    				 "(in minutes): ");
	    totalTime = in.nextInt();
	    System.out.println("");

        isValid();
    }


    /**
	 * isValid checks to see that all of the user's inputs met the proper
	 * preconditions. If so the method does nothing. However, if the input
	 * is invalid, then a new call to runStartUpCode is made so the user
	 * can enter valid data.
	 */
    public static void isValid()
    {
    	//checks that land time is greater than 0 and less than total time
    	if(landTime > 0 &&  landTime < totalTime)
    	{
    		//checks that takeoff time is greater than 0 and less than total
    		if(takeoffTime > 0  && takeoffTime < totalTime)
    		{
    			//checks that the arrival proabaility is between 0 and 1
    			if(arrivalProb > 0.0 && arrivalProb < 1.0)
    			{
    				//checks that the departure proabaility is between 0 and 1
    				if(departureProb > 0.0 && departureProb < 1.0)
    				{
    					//checks that crash time is greater than 0 and less 
    					// than total time
    					if(crashTime > 0 && crashTime < totalTime)
    					{
    						System.out.println("entries = valid");
    					}
    					else
    					{
    						System.out.println("Invalid crash time " +
    										   "(Positive integer)");
    						runStartUpCode();
    					}
    				}
    				else
    				{
    					System.out.println("Invalid departure prob " +
    									   "(Positive decimal)");
    					runStartUpCode();
    				}
    			}
    			else
    			{
    				System.out.println("Invalid arrival prob " +
    								   "(Positive decimal)");
    				runStartUpCode();
    			}
    		}
    		else
    		{
    			System.out.println("Invalid takeoff time " +
    							   "(Positive integer)");
    			runStartUpCode();
    		}
    	}
    	else
    	{
    		System.out.println("Invalid land time or total time " +
    						   "(Positive integer)");
    		runStartUpCode();
    	}
    }
}
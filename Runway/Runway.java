import java.util.*;
/**
   This class implements a linked queue of Plane objects using nodes. 
   @author Jeremy Hill
   @version 12.0.2
 */

public class Runway
{
	/**
	 * isPlaneComingIntoQueue returns true if the random number generated is
	 * less that the probability the user enter. Otherwise return false.
	 * Precondition: Probability is between 0 and 1.
     * Postcondition: If probability >= the randomly generated number, then 
     * the return value is true; otherwise the return value is false
	 * @param probability The probability a plane will be added to a queue.
	 * @return True if a plane should be added to the queue.
	 */
	public boolean isPlaneComingIntoQueue(double probability)
    {
    	if(Math.random() <= probability)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
  

  	/**
	 * isPlaneCrashed returns true if the plane leaving the landingQueue was 
	 * in the queue longer than the crash time limit given by the user. 
	 * Otherwise return false.
	 * Precondition: landingQueue is not empty.
	 * Postcondition: If the current time minus the time the plane was added 
	 * to the queue is greater then the time limit for crashing, then the 
	 * method will return true, otherwise it will return false.
	 * @param sin The time the plane was added to the queue.
	 * @param out The time the plane left the queue.
	 * @param limit The crash time limit provided by the user.
	 * @return True if a plane crashed.
	 */
    public boolean isPlaneCrashed(int in, int out, int limit)
    {
        if(out - in > limit)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
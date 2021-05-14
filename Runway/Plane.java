/**
   This class creates a plane object.
   @author Jeremy Hill
   @version 12.0.2
 */

public class Plane
{
	private int timeAdded;  //holds the time the plane was added to the queue

	/**
	 * Constructor to create a plane with a data field represeting the time
	 * it was added to the queue. 
	 * @param time The time the plane was added to the queue.
	 */
	public Plane(int time)
	{
		timeAdded = time;
	}


	/**
	 * Getter method that returns the time the plane was added to the queue.
	 * @return The time the plane was added to the queue.
	 */
	public int getTime()
	{
		return timeAdded;
	}
}
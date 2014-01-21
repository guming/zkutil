package org.jinn.zookeeper.election;


public interface LeaderSelector {
	
	public void takeLeaderShip();
	
	public void requeue(boolean reCreate);
	
	public boolean isLeader();
	
//	public void registeWatcher();
}

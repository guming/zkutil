package org.jinn.zookeeper.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;

public class LockHolder {
	
	private Logger logger=Logger.getLogger(LockHolder.class);
	private AtomicReference<Thread> threadhold=new AtomicReference<Thread>();
	private volatile String lockNode;
	private final AtomicInteger holdCount = new AtomicInteger(0);
	  
	public void setThreadHolad(String lockNode){
//		while(true){
//			if(holdCount.get()>0){
//				try {
//					Thread.sleep(1000);
//					System.out.println("testtk");
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}else{
				synchronized(this){
					threadhold.set(Thread.currentThread());
					holdCount.set(1);
					this.lockNode=lockNode;
//					break;
				}
//			}
//		}
	}
	public boolean increment(){
		if(Thread.currentThread().equals(threadhold.get())){
			holdCount.incrementAndGet();
			return true;
		}else{
			return false;
		}
	}
	public boolean decrement(){
		if(Thread.currentThread().equals(threadhold.get())){
			holdCount.decrementAndGet();
			return true;
		}else{
			return false;
		}
	}
	public String getLockNode(){
		if(Thread.currentThread().equals(threadhold.get())){
			return lockNode;
		}else{
			return null;
		}
	}
	public void clear(){
		synchronized(this){
			if(Thread.currentThread().equals(threadhold.get())){
				threadhold.set(null);
		        holdCount.set(0);
		        System.out.println("clear success");
			}
		}
	}
}

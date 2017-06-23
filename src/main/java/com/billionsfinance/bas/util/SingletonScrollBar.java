package com.billionsfinance.bas.util;

/**
 * @ClassName: Singleton.java
 * @Description: 实时滚动条
 * @author lin.tang
 * @date 2017年6月5日 上午11:40:14 Copyright: Copyright (c) 2017-2050 Company:BQJR
 */
public class SingletonScrollBar {
	private static SingletonScrollBar instance = null;

	private double percentage;

	private String node;
	
	private String currentNode;

	private int temp;

	private int count;
	
	private boolean flag;
	
	private int tasks;
	
	private int currentTask;
	
	public  static SingletonScrollBar getInstance() {
	      if(instance==null){
	      instance=new SingletonScrollBar();
	      instance.setPercentage(0.00);
	      instance.setNode("");
	      instance.setTemp(0);
	      instance.setCount(0);
	      instance.setTasks(0);
	      instance.setCurrentNode("");
	      instance.setCurrentTask(0);
	      instance.setFlag(false);
	      }
	      return instance;
	  }


	public String getCurrentNode() {
		return currentNode;
	}


	public void setCurrentNode(String currentNode) {
		this.currentNode = currentNode;
	}


	public int getCurrentTask() {
		return currentTask;
	}


	public void setCurrentTask(int currentTask) {
		this.currentTask = currentTask;
	}


	public int getTasks() {
		return tasks;
	}


	public void setTasks(int tasks) {
		this.tasks = tasks;
	}


	public boolean isFlag() {
		return flag;
	}


	public void setFlag(boolean flag) {
		this.flag = flag;
	}


	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}


	public double getPercentage() {
		return percentage;
	}


	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	
}

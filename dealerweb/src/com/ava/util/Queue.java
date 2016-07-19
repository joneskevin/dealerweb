package com.ava.util;

public class Queue<E> extends java.util.Vector<E> {
	
	private int queneSize = 100;
	
	private boolean sizeIsLimited = true;
	
	public Queue() {
		super();
	}
	
	public Queue(int queneSize) {
		super();
		this.queneSize = queneSize;
	}

	public synchronized void insert(E x) {
		super.addElement(x);
		if (sizeIsLimited) {
			while (this.size() > this.queneSize) {
				//System.out.println("============> the Queue is MaxSize limited, delete the first object");
				delete();
			}
		}		
	}

	/**	删除队列的一个元素，即删除第一个	*/
	public synchronized Object delete() {

		if (this.isEmpty()){
			return null;
		}

		Object x = super.elementAt(0);

		super.removeElementAt(0);

		return x;

	}

	public synchronized Object get() {

		if (this.isEmpty()){
			return null;
		}

		return super.elementAt(0);

	}

	public boolean isEmpty() {

		return super.isEmpty();

	}

	public synchronized void clear() {

		super.removeAllElements();

	}

	public int getQueneSize() {
		return queneSize;
	}

	public void setQueneSize(int queneSize) {
		this.queneSize = queneSize;
	}

	public boolean isSizeIsLimited() {
		return sizeIsLimited;
	}

	public void setSizeIsLimited(boolean sizeIsLimited) {
		this.sizeIsLimited = sizeIsLimited;
	}


}

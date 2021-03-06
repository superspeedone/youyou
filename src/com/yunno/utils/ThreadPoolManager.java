package com.yunno.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池，管理线程的工具类
 * 
 */

public class ThreadPoolManager {
	private ExecutorService service;

	private ThreadPoolManager() {
		int num = Runtime.getRuntime().availableProcessors();
		service = Executors.newFixedThreadPool(num * 4);
	}

	private static final ThreadPoolManager manager = new ThreadPoolManager();

	public static ThreadPoolManager getInstance() {
		return manager;
	}

	public void addTask(Runnable runnable) {
		service.execute(runnable);
		//service.submit(runnable);
	}
}

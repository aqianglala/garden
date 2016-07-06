package com.softgarden.garden.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

/**
 * 线程管理器
 * 
 * @author Kevin
 * @date 2015-9-30
 */
public class ThreadManager {

	private static ThreadPool mThreadPool;

	/**
	 * 获取单例的线程池对象
	 * 
	 * @return
	 */
	public static ThreadPool getThreadPool() {
		if (mThreadPool == null) {
			synchronized (ThreadManager.class) {
				if (mThreadPool == null) {
					// cpu个数
					int cpuNum = Runtime.getRuntime().availableProcessors();
					// int count = cpuNum * 2 + 1;
					int count = 8;

					System.out.println("cpu个数:" + cpuNum);
					mThreadPool = new ThreadPool(count, count, 0L);
				}
			}
		}

		return mThreadPool;
	}

	public static class ThreadPool {

		private int corePoolSize;// 核心线程数
		private int maximumPoolSize;// 最大线程数
		private long keepAliveTime;// 保持活跃时间(休息时间)

		private ThreadPoolExecutor executor;

		private ThreadPool(int corePoolSize, int maximumPoolSize,
				long keepAliveTime) {
			this.corePoolSize = corePoolSize;
			this.maximumPoolSize = maximumPoolSize;
			this.keepAliveTime = keepAliveTime;
		}

		public void execute(Runnable r) {
			// 线程池几个参数的理解:
			// 比如去火车站买票, 有10个售票窗口, 但只有5个窗口对外开放. 那么对外开放的5个窗口称为核心线程数,
			// 而最大线程数是10个窗口.
			// 如果5个窗口都被占用, 那么后来的人就必须在后面排队, 但后来售票厅人越来越多, 已经人满为患, 就类似于线程队列已满.
			// 这时候火车站站长下令, 把剩下的5个窗口也打开, 也就是目前已经有10个窗口同时运行. 后来又来了一批人,
			// 10个窗口也处理不过来了, 而且售票厅人已经满了, 这时候站长就下令封锁入口,不允许其他人再进来, 这就是线程异常处理策略.
			// 而线程存活时间指的是, 允许售票员休息的最长时间, 以此限制售票员偷懒的行为.
			if (executor == null) {
				// 参1:核心线程数;参2:最大线程数;参3:保持活跃时间(休息时间);参4:活跃时间单位;参5:线程队列;参6:线程工厂;参7:异常处理策略
				executor = new ThreadPoolExecutor(corePoolSize,
						maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
						new LinkedBlockingDeque<Runnable>(),
						Executors.defaultThreadFactory(), new AbortPolicy());
			}

			executor.execute(r);// 将当前Runnable对象放在线程池中
		}

		// 移除任务
		public void cancel(Runnable r) {
			if (executor != null) {
				executor.getQueue().remove(r);// 从下载队列中移除下载任务
			}
		}
	}
}

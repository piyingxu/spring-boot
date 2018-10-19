package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
// @EnableDiscoveryClient
@EnableSwagger2
@EnableFeignClients
@EnableCircuitBreaker
// /@EnableHystrixDashboard
@EnableAsync
// 支持多个包扫描，以逗号隔开
@MapperScan({ "com.mapper" })
public class ConsumerApplication {

    /**
     * 自定义异步线程池
     *
     * @return
     * @throws InterruptedException
     */
    /*
     * @Bean public AsyncTaskExecutor taskExecutor() {
     * 1）当池子大小小于corePoolSize就新建线程，并处理请求
     * 2）当池子大小等于corePoolSize，把请求放入workQueue中，池子里的空闲线程就去从workQueue中取任务并处理
     * 3）当workQueue放不下新入的任务时
     * ，新建线程入池，并处理请求，如果池子大小撑到了maximumPoolSize就用RejectedExecutionHandler来做拒绝处理
     * 4）另外，当池子的线程数大于corePoolSize的时候，多余的线程会等待keepAliveTime长的时间，如果无请求可处理就自行销毁
     * 其会优先创建 CorePoolSiz 线程， 当继续增加线程时，先放入Queue中，当 CorePoolSiz 和 Queue
     * 都满的时候，就增加创建新线程，当线程达到MaxPoolSize的时候，就会抛出错 误
     * org.springframework.core.task.TaskRejectedException
     * 另外MaxPoolSize的设定如果比系统支持的线程数还要大时，会抛出java.lang.OutOfMemoryError: unable to
     * create new native thread 异常。
     *
     * log.info("===========开始初始化自定义线程==========="); ThreadPoolTaskExecutor
     * executor = new ThreadPoolTaskExecutor();
     * executor.setThreadNamePrefix("Anno-Executor"); //线程的名称的前缀
     * executor.setMaxPoolSize(10); //线程池的大小 executor.setCorePoolSize(5);
     * //核心线程数 executor.setQueueCapacity(25); //队列长度 任务队列容量（阻塞队列）
     * executor.setKeepAliveSeconds(120); //线程保活时间（单位秒）
     *
     * 设置拒绝策略 executor.setRejectedExecutionHandler(new
     * ThreadPoolExecutor.AbortPolicy());
     * executor.setRejectedExecutionHandler(new
     * ThreadPoolExecutor.DiscardPolicy());
     * executor.setRejectedExecutionHandler(new
     * ThreadPoolExecutor.DiscardOldestPolicy());
     * executor.setRejectedExecutionHandler(new
     * ThreadPoolExecutor.CallerRunsPolicy()); 使用预定义的异常处理类，预定义的有5种策略：
     * 1、AbortPolicy 默认策略。使用该策略时，如果线程池队列满了丢掉这个任务并且抛出RejectedExecutionException异常
     * 2、DiscardPolicy 如果线程池队列满了，会直接丢掉这个任务并且不会有任何异常。 3、DiscardOldestPolicy
     * 丢弃最老的。也就是说如果队列满了，会将最早进入队列的任务删掉腾出空间，再尝试加入队列。因为队列是队尾进，队头出，所以队头元素是最老的，
     * 因此每次都是移除对头元素后再尝试入队。 4、 CallerRunsPolicy
     * 如果添加到线程池失败，那么主线程会自己去执行该任务，不会等待线程池中的线程去执行。就像是个急脾气的人，我等不到别人来做这件事就干脆自己干。
     * 5、预定义 executor.setRejectedExecutionHandler(new RejectedExecutionHandler()
     * {
     *
     * @Override public void rejectedExecution(Runnable r, ThreadPoolExecutor
     * executor) { log.info("===========线程池任务已满，开始执行失败策略==========="); } });
     *
     * executor.initialize(); return executor; }
     */

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ConsumerApplication.class, args);
        System.out.println("consumer is start===");
    }

}

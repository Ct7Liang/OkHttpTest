package ct7liang.android.apporitation;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018-05-24.
 */

public class ThreadPoolUtils {

    public static ThreadPoolExecutor pool;

    public static void create(){

        /**
         * SynchronousQueue<Runnable> synchronousQueue = new SynchronousQueue<>();
         * 队列接收到任务后 会直接将任务交给线程 该任务队列不会保存任务
         * 当前线程数量 < corePoolSize
         * 创建核心线程 执行任务
         * 当前线程数量 > corePoolSize
         * 创建非核心线程 执行任务
         * 当总线程数量 = maximumPoolSize 并且所有线程在工作的时候, 此时一旦有任务会报错
         * 解决: maximumPoolSize = Integer.MAX_VALUE
         */

        /**
         * LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>();
         * 队列接收到任务后
         * 当前线程数量 < corePoolSize
         * 创建核心线程 执行任务
         * 当前线程数量 = corePoolSize
         * 任务停留在队列中等待
         * 此任务队列下maximumPoolSize参数无效, 因为不会去创建非核心线程
         */

        /**
         * ArrayBlockingQueue<Runnable> arrayBlockingQueue = new ArrayBlockingQueue<>(3); 设置队列的最大长度
         * 队列接收到任务后
         * 当前线程数量 < corePoolSize
         * 创建核心线程 执行任务
         * 当前线程数量 = corePoolSize
         * 任务停留在队列中 等待核心线程执行
         * 如果队列已满 新建非核心线程执行任务
         * 如果总线程数量达到maximumPoolSize, 不会再开启新的费非核心线程, 否则会出错
         */

        SynchronousQueue<Runnable> synchronousQueue = new SynchronousQueue<>();
        LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>();
        ArrayBlockingQueue<Runnable> arrayBlockingQueue = new ArrayBlockingQueue<>(3);


        pool = new ThreadPoolExecutor(
                1, /*int corePoolSize 核心线程数量*/
                2, /*int maximumPoolSize 总线程数量最大值 = 核心线程数量+非核心线程数量*/
                1, /*线程空闲时存活时长, 默认作用于非核心线程, 当allowCoreThreadTimeOut设置为true时, 也可以作用于核心线程*/
                TimeUnit.MINUTES, /*线程空闲时长单位*/
                arrayBlockingQueue /*任务队列 SynchronousQueue LinkedBlockingQueue ArrayBlockingQueue DelayQueue*/
        );

    }

    public static void createA(){
        ExecutorService pool = Executors.newCachedThreadPool();
    }



    /**
     * 桃花飞绿水
     * 一庭芳草围新绿
     * 有情芍药含春泪
     * 野竹上表霄
     * 十亩藤花落古香
     * 无力蔷薇卧晓枝
     * 我愿暂求造化力
     * 减却牡丹妖艳色
     * 花非花 梦非梦
     * 花如梦 梦似花
     * 梦里有花 花开如梦
     * 心非心 镜非镜
     * 心如镜 镜似心
     * 镜中有心 心如明镜
     */
}

### Join
thread.Join把指定的线程加入到当前线程，可以将两个交替执行的线程合并为顺序执行的线程。比如在线程B中调用了线程A的Join()方法，直到线程A执行完毕后，才会继续执行线程B。
```
t.join();      //使调用线程 t 在此之前执行完毕。
t.join(1000);  //等待 t 线程，等待时间是1000毫秒 
```
我们来看看jdk8中的join源码：
```
    /**
     * Waits at most {@code millis} milliseconds for this thread to
     * die. A timeout of {@code 0} means to wait forever.
     *
     * <p> This implementation uses a loop of {@code this.wait} calls
     * conditioned on {@code this.isAlive}. As a thread terminates the
     * {@code this.notifyAll} method is invoked. It is recommended that
     * applications not use {@code wait}, {@code notify}, or
     * {@code notifyAll} on {@code Thread} instances.
     *
     * @param  millis
     *         the time to wait in milliseconds
     *
     * @throws  IllegalArgumentException
     *          if the value of {@code millis} is negative
     *
     * @throws  InterruptedException
     *          if any thread has interrupted the current thread. The
     *          <i>interrupted status</i> of the current thread is
     *          cleared when this exception is thrown.
     */
    public final synchronized void join(long millis)
    throws InterruptedException {
        long base = System.currentTimeMillis();
        long now = 0;

        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (millis == 0) {
            while (isAlive()) {
                wait(0);
            }
        } else {
            while (isAlive()) {
                long delay = millis - now;
                if (delay <= 0) {
                    break;
                }
                wait(delay);
                now = System.currentTimeMillis() - base;
            }
        }
    }
```
从代码上看，如果线程被生成了，但还未被起动，调用它的 join() 方法是没有作用的，将直接继续向下执行。可以看出，Join方法实现是通过wait（小提示：Object 提供的方法）。 当main线程调用t.join时候，main线程会获得线程对象t的锁（wait 意味着拿到该对象的锁),调用该对象的wait(等待时间)，直到该对象唤醒main线程 ，比如退出后。这就意味着main 线程调用t.join时，必须能够拿到线程t对象的锁。**最直白的理解就是插队，前面也说了：比如在线程B中调用了线程A的Join()方法，直到线程A执行完毕后，才会继续执行线程B。不就是A插队了嘛**

### 代码演示

1. 未使用join
```
/**
 * Created by grace on 2018/9/9.
 */
public class Demo1 {
    public static void main(String[] args)  {
        System.out.println(Thread.currentThread().getName() + "主线程开始运行");

        Thread threadA = new JoinThread("A");
        threadA.start();

        Thread threadB = new JoinThread("B");
        threadB.start();

        System.out.println(Thread.currentThread().getName() + "主线程结束运行");
    }
}

class JoinThread extends Thread{
    private String name;
      public JoinThread(String name){
          super(name);
          this.name=name;
      }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " 线程运行开始!");
        for (int i = 0; i <5 ; i++) {
            System.out.println(Thread.currentThread().getName()+"线程运行");
        }
        System.out.println(Thread.currentThread().getName() + " 线程运行结束!");
    }
}

结果：
main主线程开始运行
main主线程结束运行
A 线程运行开始!
B 线程运行开始!
B线程运行
B线程运行
B线程运行
B线程运行
B线程运行
A线程运行
A线程运行
A线程运行
A线程运行
A线程运行
B 线程运行结束!
A 线程运行结束!
```
**main主线程结束运**有可能最后一句输出吗？答案是有可能的，但是可能性很小，跟机器也有很大的关系。</br>
解释：当主线程 main方法执行`System.out.println(Thread.currentThread().getName() + "主线程开始运行");`这条语句时，两外两个线程还没有真正开始运行，或许正在为它分配资源准备运行。因为为线程分配资源需要时间，而main方法执行完t.start()方法后继续往下执行`System.out.println(Thread.currentThread().getName() + "主线程结束运行");`,这个时候for循环可能还没开始呢！

2. 使用join
```
/**
 * Created by grace on 2018/9/9.
 */
public class Demo1 {
    public static void main(String[] args)  {
        System.out.println(Thread.currentThread().getName() + "主线程开始运行");

        Thread threadA = new JoinThread("A");
        threadA.start();

        try {
            threadA.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread threadB = new JoinThread("B");
        threadB.start();

        try {
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "主线程结束运行");
    }
}

class JoinThread extends Thread{
    private String name;
      public JoinThread(String name){
          super(name);
          this.name=name;
      }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " 线程运行开始!");
        for (int i = 0; i <5 ; i++) {
            System.out.println(Thread.currentThread().getName()+"线程运行");
        }
        System.out.println(Thread.currentThread().getName() + " 线程运行结束!");
    }
}

结果：
main主线程开始运行
A 线程运行开始!
A线程运行
A线程运行
A线程运行
A线程运行
A线程运行
A 线程运行结束!
B 线程运行开始!
B线程运行
B线程运行
B线程运行
B线程运行
B线程运行
B 线程运行结束!
main主线程结束运行

这就是join的效果，A B线程进行了插队，必须等待A B执行完main才能执行。
```

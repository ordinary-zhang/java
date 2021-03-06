### 1.概念
Java内存模型规定所有的变量都是存在主存当中，每个线程都有自己的工作内存，工作内存保存着该线程使用到的变量到主存副本拷贝，线程对变量的所有操作都必须在工作内存中进行，而不能直接对主存进行操作。并且每个线程不能访问其他线程的工作内存。

### 2.原子性、可见性、有序性
在并发编程中，我们通常会遇到以下三个问题：原子性问题，可见性问题，有序性问题。</br>
原子性：即一个操作或者多个操作 要么全部执行并且执行的过程不会被任何因素打断，要么就都不执行。</br>
可见性: 是指当多个线程访问同一个变量时，一个线程修改了这个变量的值，其他线程能够立即看得到修改的值。</br>
有序性：即程序执行的顺序按照代码的先后顺序执行。</br>

### 3. volatile变量
关键字volatile可以说时java虚拟机提供的最轻量级的同步机制，java内存模型中专门定义了一些特殊的访问规则。一个变量定义为volatile修饰之后它具备两种特性：
1. 保证此变量对所有线程的可见性，保证了一个线程修改了某个共享变量的值，这新值对其他线程来说是立即可见的。
2. 禁止进行指令重排序。

那么在线程1修改stop值时（当然这里包括2个操作，修改线程1工作内存中的值，然后将修改后的值写入内存），会使得线程2的工作内存中缓存变量stop无效，然后线程2读取时，发现自己的缓存行无效，它会等待缓存行对应的主存地址被更新之后，然后去对应的主存读取最新的值。但是它并不是原子操作，只是保证可见性。所以在不符合以下两种规则的运算场景中，我们任然要通过锁（使用synchronized或者java.util.concurrent中的原子类）来保证原子性。
1. 运算结果并不依赖变量的当前值，或者能够保证只有一个线程修改变量的值。
2. 变量不需要与其他的状态变量共同参与不变约束。

观察加入volatile关键字和没有加入volatile关键字时所生成的汇编代码发现，加入volatile关键字时，会多出一个lock前缀指令；lock前缀指令实际上相当于一个内存屏障（也成内存栅栏），内存屏障会提供3个功能：
1. 它确保指令重排序时不会把其后面的指令排到内存屏障之前的位置，也不会把前面的指令排到内存屏障的后面；
2. 它会强制将对缓存的修改操作立即写入主存；
3. 如果是写操作，它会导致其他CPU中对应的缓存行无效。

### 4.先行发生原则（happens-before）
如果java内存模型中所有的有序性都紧紧依靠volatile和synchronized来完成，那么有些操作会变得很繁琐，但是我们在编写代码的时候并没感觉到这一点，这是因为java语言中有一个先行发生原则。这个原则很重要，他是判断数据是否存在竞争，线程是否安全的主要依据。

**什么是先行发生原则？** </br>
先行发生原则是指：如果说操作A先行发生于操作B，也就是发生在操作B之前，操作A产生的影响能被操作B观察到。

举例说明：
```
//以下操作在线程A中执行
i = 1;
//以下操作在线程B中执行
j = i;
//以下操作在线程C中执行
i = 2;
```
假设线程A中的操作i=1先行发生于线程B的操作j=i,那么可以确定在线程B的操作执行之后，j一定等于1。因为：根据先行发生原则，i=1的结果可以被B观察到； 
现在保持A先行发生于B，线程C出现在A与B之间，但是线程C与B没有先行发生关系。那么j会等于多少呢？答案至不确定。因为线程C对变量i的影响可能会被B观察到，也可能不会。因为两者之间没有先行发生关系；B可能存在读取过期的数据不具备多线程安全。

java内存模型中的”天然“先行关系
1. 程序次序规则 
一个线程内，按照程序代码的顺序，书写在前面的操作先行发生于(逻辑上)书写在后面的操作。 
2. 管程锁定规则 
一个unlock操作先行发生于后面对同一个锁的lock操作。后面指时间上的先后顺序。 
3. volatile变量规则 
对一个volatile变量的写操作先行发生于后面对这个变量的读操作。这里的后面指时间上的先后顺序。 
4. 传递性 
如果操作A先行发生于操作B，操作B先行发生于操作C，那么，操作A也就先行发生于操作C。 
5. 线程启动规则 
Thread对象的start方法先行发生于此线程的每个动作； 
6. 线程终止规则 
线程中的所有操作都先行发生于对此线程的终止检测； 
7. 线程中断规则 
对线程的interrupt（）方法的调用先行发生于被中断线程的代码检测到中断时间的发生； 
8. 对象终结规则 
一个对象的初始化完成先行发生于它的finalize方法的开始；

**分析**
先行发生原则（happens-before）不是描述实际操作的先后顺序，它是用来描述可见性的一种规则； </br>
对上述中原则的理解： </br>
对2）管程锁定规则的理解：如果线程1解锁了a，接着线程2锁定了a，那么，线程1解锁a之前的写操作都对线程2可见（线程1和线程2可以是同一个线程）。 </br>
对3）volatile变量规则的理解：如果线程1写入了volatile变量v（这里和后续的“变量”都指的是对象的字段、类字段和数组元素），接着线程2读取了v，那么，线程1写入v及之前的写操作都对线程2可见（线程1和线程2可以是同一个线程）。</br>

**总结**
先行发生原则的作用：判断内存可见性与重排序是否造成并发问题。

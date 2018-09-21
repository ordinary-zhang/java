### "=="和equals方法究竟有什么区别？

1. ==操作符专门用来比较两个变量的值是否相等，也就是用于比较变量所对应的内存中所存储的数值是否相同，要比较两个基本类型的数据或两个引用变量是否相等，只能用==操作符。

2. 如果一个变量指向的数据是对象类型的，那么，这时候涉及了两块内存，对象本身占用一块内存（堆内存），变量也占用一块内存（栈），例如Objet obj = newObject();变量obj是一个内存，new Object()是另一个内存，此时，变量obj所对应的内存中存储的数值就是对象占用的那块内存的首地址。对于指向对象类型的变量，如果要比较两个变量是否指向同一个对象，即要看这两个变量所对应的内存中的数值是否相等，这时候就需要用==操作符进行比较。

3. equals方法是用于比较两个独立对象的内容是否相同，就好比去比较两个人的长相是否相同，它比较的两个对象是独立的。例如，对于下面的代码：
```
String a=new String("foo");

String b=new String("foo");
```

两条new语句创建了两个对象，然后用a/b这两个变量分别指向了其中一个对象，这是两个不同的对象，它们的首地址是不同的，即a和b中存储的数值是不相同的，所以，表达式a==b将返回false，而这两个对象中的内容是相同的，所以，表达式a.equals(b)将返回true。

在实际开发中，我们经常要比较传递进行来的字符串内容是否等，例如，String input = …;input.equals(“quit”)，许多人稍不注意就使用==进行比较了，这是错误的。记住，字符串的比较基本上都是使用equals方法。

如果一个类没有自己定义equals方法，那么它将继承Object类的equals方法，Object类的equals方法的实现代码如下：
```
boolean equals(Object o){

return this==o;

}
```
4. 这说明，如果一个类没有自己定义equals方法，它默认的equals方法（从Object类继承的）就是使用==操作符，也是在比较两个变量指向的对象是否是同一对象，这时候使用equals和使用==会得到同样的结果，如果比较的是两个独立的对象则总返回false。如果你编写的类希望能够比较该类创建的两个实例对象的内容是否相同，那么你必须覆盖equals方法，由你自己写代码来决定在什么情况即可认为两个对象的内容是相同的。

5. **那么为什么在实际中大家都说equals是比较值是否相等的呢？** 这就是体现了java继承之后方法重写的概念了。前面说过Object是所有类的父类，那么子类就可以重写equals方法（父类中private方法不可重写），我们看下String中的重写equals方法：
```
 public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof String) {
            String anotherString = (String)anObject;
            int n = value.length;
            if (n == anotherString.value.length) {
                char v1[] = value;
                char v2[] = anotherString.value;
                int i = 0;
                while (n-- != 0) {
                    if (v1[i] != v2[i])
                        return false;
                    i++;
                }
                return true;
            }
        }
        return false;
    }

```
在String的equals中除了内存地址相同返回为true还存在值的比较，类似的还有int，double等等。

-----------------------------------------------------------------

简单的说，==比较两个人是否究竟是真正同一个人，equals一般用来比较两个人在逻辑上是否相等（比如规定两人成年之后身高相同就算两人相同等等），想怎么定义就怎么定义，如果不覆盖equals方法的话，默认仍然是比较两人是否同一个人（废话，两个人都还处于胚胎状态，都没有具体特征，怎么可能在逻辑上比较）。

**总结来说：**
1. 对于==，如果作用于基本数据类型的变量，则直接比较其存储的 “值”是否相等；

如果作用于引用类型的变量，则比较的是所指向的对象的地址

2. 对于equals方法，注意：equals方法不能作用于基本数据类型的变量

如果没有对equals方法进行重写，则比较的是引用类型的变量所指向的对象的地址；

诸如String、Date等类对equals方法进行了重写的话，比较的是所指向的对象的内容。

参考博客：
https://blog.csdn.net/u012403290/article/details/64141900?utm_source=copy </br>
https://www.cnblogs.com/findumars/p/3746878.html

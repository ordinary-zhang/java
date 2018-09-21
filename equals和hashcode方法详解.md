### 1.概述
1. equal()方法是object提供的方法，用来判断两个对象的值是否相等。里面默认是==运算符比较两个对象，用户可重写
2. hashcode方法也是object提供的方法，默认返回的是对象在内存中的地址转换成的一个int值，也可重写

object对象中的 public boolean equals(Object obj)，对于任何非空引用值 x 和 y，当且仅当 x 和 y 引用同一个对象时，此方法才返回 true；

**注意** ：当此方法被重写时，通常有必要重写 hashCode 方法，以维护 hashCode 方法的常规协定，该协定声明相等对象必须具有相等的哈希码。如下：</br>
(1)当obj1.equals(obj2)为true时，obj1.hashCode() == obj2.hashCode()必须为true </br>
(2)当obj1.hashCode() == obj2.hashCode()为false时，obj1.equals(obj2)必须为false </br>
(3)当obj1.hashCode() == obj2.hashCode()为true时，obj1.equals(obj2)不一定为true </br>

**如果不重写equals，那么比较的将是对象的引用是否指向同一块内存地址，重写之后目的是为了比较两个对象的value值是否相等。特别指出利用equals比较八大包装对象
（如int，float等）和String类（因为该类已重写了equals和hashcode方法）对象时，默认比较的是值，在比较其它自定义对象时都是比较的引用地址**

hashcode是用于散列数据的快速存取，如利用HashSet/HashMap/Hashtable类来存储数据时，都是根据存储对象的hashcode值来进行判断是否相同的。
### 2.为什么重写equels方法要重写hashcode方法
这样如果我们对一个对象重写了euqals，意思是只要对象的成员变量值都相等那么euqals就等于true，但不重写hashcode，那么我们再new一个新的对象，
当原对象.equals（新对象）等于true时，两者的hashcode却是不一样的，由此将产生了理解的不一致，如在存储散列集合时（如Set类），将会存储了两个值一样的对象，
导致混淆，因此，就也需要重写hashcode()

### 3.例子
我们知道hashset的特性：HashSet实现了Set接口，它不允许集合中出现重复元素。
```
import java.util.Collection;
import java.util.HashSet;

1. 情况1
/**
 * Created by grace on 2018/9/21.
 */
public class Demo {
    public static void main(String[] args) {
        Name n1 = new Name("01");
        Name n2 = new Name("01");

        Collection c = new HashSet();
        c.add(n1);
        c.add(n2);
        System.out.println("------------");
        System.out.println(n1.equals(n2));
        System.out.println("------------");
        System.out.println(n1.hashCode());
        System.out.println(n2.hashCode());
        System.out.println("c:"+c);
        System.out.println("size:"+c.size());
    }


}

class Name {
    private String id;
    public Name(String id) {
        this.id = id;
    }

    public String toString(){
        return this.id;
    }
//    public boolean equals(Object obj) {
//        if (obj instanceof Name) {
//            Name name = (Name) obj;
//            System.out.println("equal:"+ name.id);
//            return (id.equals(name.id));
//        }
//        return super.equals(obj);
//    }
//
//    public int hashCode() {
//        Name name = (Name) this;
//        System.out.println("Hash:" + name.id);
//        return id.hashCode();
//
//    }
}

结果：------------
false
------------
460141958
1163157884
c:[01, 01]
size:2

```
很正常你没有重写equals方法，两个对象肯定不一样，n1,n2都放入集合中

2. 情况2
**只写equals方法**
```
------------
equal:01
true
------------
460141958
1163157884
c:[01, 01]
size:2

从结果看明显矛盾啊，我equals相等了hashcode不相等，明显违背了前面说的三条原则。
```
**总结** 所以重写equals方法时要重写hashcode方法。

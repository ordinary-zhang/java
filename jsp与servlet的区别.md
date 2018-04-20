 Servlet与JSP区别
 
 jsp:JSP全称Java Server Pages，是一种动态网页开发技术。它使用JSP标签在HTML网页中插入Java代码。标签通常以<%开头以%>结束。 
 
 事实上，servlet就是一个Java接口
 servlet接口定义的是一套处理网络请求的规范，所有实现servlet的类，都需要实现它那五个方法，其中最主要的是两个生命周期方法 init()和destroy()，
 还有一个处理请求的service()，也就是说，所有实现servlet接口的类，或者说，所有想要处理网络请求的类，都需要回答这三个问题：
你初始化时要做什么
你接受到请求时要做什么
你销毁时要做什么



 jsp和servlet的区别和联系：
1.jsp经编译后就变成了Servlet.
(JSP的本质就是Servlet，JVM只能识别java的类，不能识别JSP的代码,Web容器将JSP的代码编译成JVM能够识别的java类)
2.jsp更擅长表现于页面显示,servlet更擅长于逻辑控制.
3.Servlet中没有内置对象，Jsp中的内置对象都是必须通过HttpServletRequest对象，HttpServletResponse对象以及HttpServlet对象得到.
Jsp是Servlet的一种简化，使用Jsp只需要完成程序员需要输出到客户端的内容，Jsp中的Java脚本如何镶嵌到一个类中，由Jsp容器完成。
而Servlet则是个完整的Java类，这个类的Service方法用于生成对客户端的响应。
4.Servlet在Java代码中通过HttpServletResponse对象动态输出HTML内容
JSP在静态HTML内容中嵌入Java代码，Java代码被动态执行后生成HTML内容



2、各自的特点
JSP侧重于视图，Servlet主要用于控制逻辑
Servlet更多的是类似于一个Controller，用来做控制。
Servlet能够很好地组织业务逻辑代码，但是在Java源文件中通过字符串拼接的方式生成动态HTML内容会导致代码维护困难、可读性差
JSP虽然规避了Servlet在生成HTML内容方面的劣势，但是在HTML中混入大量、复杂的业务逻辑同样也是不可取的


3、通过MVC双剑合璧
既然JSP和Servlet都有自身的适用环境，那么能否扬长避短，让它们发挥各自的优势呢？答案是肯定的——MVC(Model-View-Controller)模式非常适合解决这一问题。
MVC模式（Model-View-Controller）是软件工程中的一种软件架构模式，把软件系统分为三个基本部分：模型（Model）、视图（View）和控制器（Controller）：
Controller——负责转发请求，对请求进行处理
View——负责界面显示
Model——业务功能编写（例如算法实现）、数据库设计以及数据存取操作实现

jsp 九大内置对象和其作用详解 

   JSP中一共预先定义了9个这样的对象，分别为：request、response、session、application、out、pagecontext、config、page、exception
1、request对象
request 对象是 javax.servlet.httpServletRequest类型的对象。 
该对象代表了客户端的请求信息，主要用于接受通过HTTP协议传送到服务器的数据。（包括头信息、系统信息、请求方式以及请求参数等）
。request对象的作用域为一次请求。
2、response对象
response 代表的是对客户端的响应，主要是将JSP容器处理过的对象传回到客户端。response对象也具有作用域，它只在JSP页面内有效。
3、session对象
session 对象是由服务器自动创建的与用户请求相关的对象。
服务器为每个用户都生成一个session对象，用于保存该用户的信息，跟踪用户的操作状态。
session对象内部使用Map类来保存数据，因此保存数据的格式为 “Key/value”。
 session对象的value可以使复杂的对象类型，而不仅仅局限于字符串类型。
4、application对象
 application 对象可将信息保存在服务器中，直到服务器关闭，否则application对象中保存的信息会在整个应用中都有效。
 与session对象相比，application对象生命周期更长，类似于系统的“全局变量”。
5、out 对象
out 对象用于在Web浏览器内输出信息，并且管理应用服务器上的输出缓冲区。
在使用 out 对象输出数据时，可以对数据缓冲区进行操作，及时清除缓冲区中的残余数据，为其他的输出让出缓冲空间。
待数据输出完毕后，要及时关闭输出流。
6、pageContext 对象
pageContext 对象的作用是取得任何范围的参数，通过它可以获取 JSP页面的out、request、reponse、session、application 等对象。
pageContext对象的创建和初始化都是由容器来完成的，在JSP页面中可以直接使用 pageContext对象。
7、config 对象
config 对象的主要作用是取得服务器的配置信息。通过 pageConext对象的 getServletConfig() 方法可以获取一个config对象。
当一个Servlet 初始化时，容器把某些信息通过 config对象传递给这个 Servlet。
 开发者可以在web.xml 文件中为应用程序环境中的Servlet程序和JSP页面提供初始化参数。
8、page 对象
page 对象代表JSP本身，只有在JSP页面内才是合法的。 
page隐含对象本质上包含当前 Servlet接口引用的变量，类似于Java编程中的 this 指针。
9、exception 对象
exception 对象的作用是显示异常信息，只有在包含 isErrorPage="true" 的页面中才可以被使用，
在一般的JSP页面中使用该对象将无法编译JSP文件。excepation对象和Java的所有对象一样，都具有系统提供的继承结构。
exception 对象几乎定义了所有异常情况。在Java程序中，可以使用try/catch关键字来处理异常情况； 
如果在JSP页面中出现没有捕获到的异常，就会生成 exception 对象，并把 exception 对象传送到在page指令中设定的错误页面中
，然后在错误页面中处理相应的 exception 对象。



servlet生命周期
a：如果在web.xml中servlet配置loadon-start-up,那么服务启动时，就会创建servlet对象；如果不配置，就会在
第一次请求时创建，servlet对象一旦创建就会驻存在内存中，等待客户端发送请求。
servlet对象一旦创建就会调用init方法进行初始化。
b：如果客户端(浏览器)发送请求，那么servlet对象就会调用service()方法，并且会创建两个对象，一个是封装请求数据
的httpServletRequest对象，一个是封装响应数据的HttpServletResponse对象，并且把这两个对象作为参数传递给
service方法，service方法根据请求方式调用doget或者dopost方法。
c：服务器关闭或者web应用卸载时，servlet对象就会被销毁，那么会调用destroy方法。


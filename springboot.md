# Annotation(標註、註解)

* 基本上可以把註解想像成是賦予一個新的功能，不同的註解所提供的功能不一樣，並且他們的使用方法也會有點不太一樣

* E.g.: 
	```
	@SpringBootApplication

	用法: 加在 class 上面
	用途: 表示這一個 Main 的 class，是這個 Spring Boot 程式的啟動入口
	```
# Inversion of Control(IoC)
	
* 將 object 的控制權交給了外部的 Spring 容器來管理

* Control (控制)，就是對於 object 的控制權

* 所有的 object 就都是由外部的 Spring 容器來進行管理，因此當 Teacher 想要去使用印表機時，就只要跟 Spring 容器去借就好了，Teacher 就不需要自己再去「控制」這個印表機的生命週期

# Dependency Injection(DI)

* 被依賴物件並不是在依賴物件的程式中使用new產生，而是從外部「注入(inject)」至依賴物件

	```
	public class ShoppingCart {
    
		private PostShipping postShipping;
		
		// 由建構式從外部注入被依賴的物件至依賴的程式中 (constructor injection)
		public ShoppingCart(PostShipping postShipping) { 
			this.postShipping = postShipping;
		}
		
		private void checkout() {
			postShipping.shipOrder(order);
		}

		// 由Setter從外部注入被依賴的物件至依賴的程式中 (setter injection)
		public void setPostShipping(PostShipping postShipping) { 
			this.postShipping = postShipping;
		}

	}
	```

	* 為什麼使用DI
		* 「依賴注入是為了解決物件間高耦合的問題」

		* 高耦合(High coupling)是指當一個被依賴對象被修改時會連帶影響依賴對象也得修改

# Dependency Inversion Principle(DIP)
	
* 透過介面(interface)與依賴注入的方式來降低依賴間的耦合，稱為DIP
	```
	/** 運送介面 */
	public interface Shipping {
		
		public void shipOrder(Order order);

	}

	/** 郵局貨運 */
	public class PostShipping implements Shipping {

		@Override
		public void shipOrder(Order order) { ... }

	}

	/** 新竹貨運 */
	public class HctShipping implements Shipping {

		@Override
		public void shipOrder(Order order) { ... }

	}
	```
	
	```
	public class ShoppingCart {
    
		private Shipping shipping; // <-- 改為Shipping介面，此為利用物建導向的多型特性。
		
		public ShoppingCart(Shipping shipping) {
			this.shipping = shipping; // 被依賴物件由外部注入，達到業務邏輯與被依賴物件的生成邏輯分開
		}
		
		private void checkout(Order order) {
			shipping.shipOrder(order);
		}

	}
	```

# Bean

* 使用了 IoC，將 object 的控制權交給 Spring 容器來管理之後，往後的所有 object 就都是活在 Spring 容器裡面，由 Spring 容器來管理這個 object 的生命週期

*  **「由 Spring 容器來管理的 object，我們賦予他們一個新的名字，就叫做 Bean」**，所以 Bean 其實就只是一個 object 而已

# 注入 Bean 的方法：@Autowired

* 使用 @Autowired 去注入一個 Bean 進來時，我們自己本身也得變成是由 Spring 容器所管理的 Bean 才可以，這樣子 Spring 容器才有辦法透過 DI (依賴注入)，將我們想要的 Bean 給注入進來
* 想要使用 @Autowired 去注入一個 Bean 時，必須滿足：

	* 必須要確保 **「自己也是一個 Bean」** （即是有在 class 上面加上 @Component）
	* 並且 @Autowired 是透過 **「變數的類型」** 來注入 Bean 的（所以只要 Spring 容器中沒有那個類型的 Bean，就會注入失敗）

* Example:
	* 我想注入Printer在UserController，就要再把Printer變成@Component，這樣就會變成由 Spring 容器所管理的Bean
```
package com.hello.entity;

import org.springframework.stereotype.Component;

@Component
public class BrotherPrinter implements Printer{

    @Override
    public String print() {
        return "Brother Printer";
    }

    @Override
    public String print(String message) {
        return message;
    }
}
```

```
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private Printer printer;

    @GetMapping("/name")
    public String getName(){
        return "hello, World! " + printer.print();
    }

    @GetMapping("/name/{message}")
    public String getNameWithMessage(@PathVariable("message") String message){
        return "hello, World! " + printer.print(message);
    }

    @GetMapping("/queryUserName/{id}")
    public String queryUserName(@PathVariable("id") String id){
        return userService.queryUserName(id);
    }
}
```

# 指定注入的 Bean 的名字：@Qualifier

* 設在 Spring 容器中，同時有兩個一樣類型的 Bean 存在，那麼在這個情況下，Spring Boot 會如何運作呢？
	* 答案是：Spring Boot 會出現錯誤並且運行失敗，因為 hpPrinter 和 canonPrinter 同時都可以向上轉型成 Printer 類型，所以 Spring Boot 不知道該注入哪一個 Bean，因此錯誤原因就會是「同時有多個同樣類型的 Bean 存在，因此無法選擇要注入哪一個」
	* 「MyController required a single bean, but 2 were found」，這一行的訊息就是表示「MyController 想要注入一個 Bean，但是發現 Spring 容器中存在 2 個同樣類型的 Bean，因此注入失敗」

* @Qualifier 的用途，是去指定要注入的 Bean 的「名字」是什麼，進而解決同時有兩個同樣類型的 Bean 存在的問題

* 想要使用 @Qualifier 去指定注入一個 Bean 時，必須滿足：

	* 必須搭配 **@Autowired** 一起使用
	* 指定的是 **「Bean 的名字」**
		* 使用 @Component 去創建 Bean 時，這些 Bean 的名字，就會是 **「Class 名的第一個字母轉成小寫」**
		* Example:
			* HpPrinter class 所生成的 Bean，名字就會叫做 hpPrinter
			* CanonPrinter 所生成的 Bean，名字就會叫做 canonPrinter


# Spring AOP 的定義

* AOP 的全稱是 **Aspect-Oriented Programming**，中文是翻譯為 **「切面導向程式設計」** 或是 **「剖面導向程式設計」**
* AOP 的概念，就是 **「透過切面，統一的去處理方法之間的共同邏輯」**
* [教學網站](https://ithelp.ithome.com.tw/articles/10327252)

# 在 pom.xml 載入 Spring AOP

* pom.xml 這個檔案裡面新增以下的程式，將 Spring AOP 的功能給載入進來
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

* 加完上述的程式之後，在 pom.xml 的右上角會出現一個 M 符號，點擊這個 M 符號，就可以更新這個 Spring Boot 程式，把 Spring AOP 的功能給載入進來

# 製造切面的方法：@Aspect

* 只有 Bean 才可以變成一個切面
  * 就是在使用 @Aspect 時，也要同時使用 @Component，將該 class 變成 Bean 的同時，切面的設定才會生效

* 在切入點方法「執行前」執行切面：@Before
  * 只要我們在這個 before() 方法的上面，去加上一個 @Before，
  * 並且在後面指定想要的切入點，
  * 就可以在這個切入點的方法 「執行前」，去執行這個 MyAspect class 中的 before() 方法了
  ```
	@Aspect
	@Component
	public class MyAspect {

		@Before("execution(* com.hello.entity.HpPrinter.*(..))")
		public void before() {
			System.out.println("I'm before");
		}


		@After("execution(* com.hello.entity.HpPrinter.*(..))")
		public void after() {
			System.out.println("I'm after");
		}

		@Around("execution(* com.hello.entity.HpPrinter.*(..))")
		public Object around(ProceedingJoinPoint pjp) throws Throwable {
			System.out.println("I'm around before");

			// 執行切入點的方法，obj 為切入點方法執行的結果
			Object obj = pjp.proceed();

			System.out.println("I'm around after");
			return obj;
		}
	}
  ```

* 如何解讀 AOP 程式？
  * 步驟一：先閱讀 @Before 小括號中的程式
    * 小括號中的程式，稱為「切入點 (Pointcut)」，即是去指定哪個方法要被切
  * 步驟二：查看前面的註解是什麼
    * 這邊所加上的，就是 @Before
    * 指定要在小括號中的切入點 「執行前」，去執行下面的 before() 方法
    * 因此在步驟二這裡，就是去確認「切面方法執行的時機點」
  * 步驟三：要執行的切面方法
    * 確認好「切入點」和「時機點」之後，最後就是在下面的 before() 方法中，去撰寫切面的程式

* 在 Spring AOP 裡面，有三種時機點可以選擇：
  * @Before：在方法「執行前」執行切面
  * @After：在方法「執行後」執行切面
  * @Around：在方法「執行前」和「執行後」，執行切面

* Spring AOP 以前最常被用在以下三個地方：
  * 權限驗證
  * 統一的 Exception 處理
  * log 記錄

# 什麼是 Spring MVC？

* 就是讓我們「在 Spring Boot 中實作前後端之間的溝通」

# 什麼是 Http 協議？

* 負責去規定資料的傳輸格式，讓前端和後端能夠有效進行資料溝通
* 在 Http 協議中，可以分為「Request (請求)」和「Response (回應)」兩部分
  * 一個 Http request 加上一個 Http response，就可以組合成一次完整的 Http 溝通

# Http Request 的格式規範

* 分成四個部分，分別是：
  * http method
    * Http 請求所使用的「請求方法」
      * 常用的有 GET、POST、PUT、DELETE..等等
  * url
    * 網站的網址
  * request header 
    * 放一些請求時的通用資訊
  * request body
    * 放請求的參數
    * 只有在使用 POST 或是 PUT 這類的請求方法時，才可以去使用 request body 去傳遞參數

# Http Response 的格式規範

* 分成三個部分，分別是：
  * http status code
    * 「Http 狀態碼」
    * 用途是表達「這一次 Http 請求的結果為何」
  * response header
    * Response header 和上面的 Request header 類似，都是放一些通用的資訊
  * response body
    * 後端要傳給前端的數據
    * 請求是成功的話，前端就會去取得這個 Response body 中的數據，並且將這些數據結合前端的版面設計，最後就可以呈現完整的網頁給使用者看了

# Url 的格式

* 使用的協議
  * 在 url 的最前面，會顯示這個 url 所使用的是什麼協議
  * 像是這邊使用的就是 http 協議
* 域名
  * 協議後面會有一個 :// 做分隔，後面接著的就是這個 url 的域名
  * 以這個例子來說的話，這個 url 的域名就是「localhost」
* Port
  * 不一定會出現，是一個可選項
  * 域名後面有加上 :，並且在 : 後面有加上一個數字的話，那這個數字就是 url 所使用的 port
* url 路徑
  *  port 後面，會去加上一個斜線（或是沒有 port 的話，就是在域名後面的斜線）
  *  斜線之後的所有東西，就是這個 **url 的路徑**
  *  像是這個例子中，url 路徑的值就是 **/test**

* Url 路徑對應：@RequestMapping
  *  http://localhost:8080/test ，對這個 url 來說，他的 url 路徑就是最後面的 **/test**
  *  @RequestMapping，並且在後面的小括號中指定 url 路徑的值 /test，這樣就可以成功將 url 路徑 /test 給對應到下面的 test 方法上
     *  @RequestMapping 是一個通用的注解，可以用來映射各種類型的 HTTP 請求（GET、POST、PUT、DELETE 等）。

     ```java
	 @RequestMapping(value = "/test", method = RequestMethod.GET)
	 public String test() {
    	return "test";
	 }
	 ```

	 * 使用 @RequestMapping 的注意事項
    	 * 該 class 上面一定要上加 @Controller 或是 @RestController，否則 @RequestMapping 不會生效

# 什麼是 Json？

* 是一種格式，用途是更簡單、更直覺的去呈現數據，讓我們易於讀寫
* Example:
  * 我的學號是 123，名字是 Judy
  
  ```json
  {
      "id": 123,
      "name": "Judy"
  }
  ```

## Json 的格式

* 由一組大括號 {} 來表示一個 object

```json
{
   //....
}
```

* Key 和 Value 的概念
  * 在 Json 中的所有 key，**都必須要加上雙引號 " "** 來框住才可以
  * key 為 id，並且 value 為 123

  ```json
  {
      "id": 123,
      "name": "Judy"
  }
  ```

## Json 所支援的值

* 整數
  * "id": 123
* 浮點數
  * "score": 1.111
* 字串
  * "name": "Hello"
* Boolean
  * "option": true
* List
  * "list": [1, 2, 3]

## 如何將返回值轉換成是 Json 格式？

* 兩個步驟
  * 在 class 上面加上 @RestController
  * 將該方法的返回值，改成是一個「Java 中的物件」

  ```java
    @RequestMapping("/user")
    public Student user(){
        Student student = new Student();
        student.setName("Scoot");
        return student;
    }
  ```

# @Controller 和 @RestController 的差別在哪裡？

* 共同點：
  * 都可以將 class 變成 Bean、也都可以將裡面的 @RequestMapping 生效
* 差別：
  * @Controller：將方法的返回值自動轉換成 前端模板的名字
  * @RestController：將方法的返回值自動轉換成 Json 格式

# GET 的用法和特性

* 當你使用 GET 方法時，你所傳遞的參數就會被別人所看見
* 前端只能夠將參數放在 url 的最後面，透過這個格式將參數傳遞給後端
  *  url 的最後面，寫上 id=123&name=Judy 時
     *  要傳遞 「id 為 123，並且 name 為 Judy」的參數資訊給後端，
     *  這種寫在 url 最後面的參數一個名字，叫做「query parameter」

# POST 的用法和特性

* 當你使用 POST 方法時，你所傳遞的參數就可以隱藏起來，不被別人看見
* 使用 POST 請求時，Http 協議就規定「前端要將參數放在 request body 中做傳遞」
  * 由於 request body 會被封裝起來，因此參數不會洩漏
  * 放在 request body 中的參數格式，是以 Json 格式來撰寫的

# 在 Spring Boot 中接住參數的四個註解

* @RequestParam
  * 接住放在 Url 後面的參數
  * 注意事項
    * 參數名字須一致
      * Spring Boot 中的變數的「名字」，必須要和 url 中的參數的「名字」一樣才可以
    * 參數類型需一致
  * Example:
    * http://localhost:8080/name?date=0823
  ```java
    @RequestMapping("/name")
    public String getName(@RequestParam String date){
        return "hello, World! " + printer.print() + " " + date;
    }
  ```

* @RequestBody
  * 接住放在 request body 中的參數
  * 創建一個和這個 Json 格式「一一對應」的 Java class 出來
  
  ```json
  {
    "id": "112598019",
    "name": "Scott"
  }
  ```

  ```java
  public class Student {
    
    private Integer id;
    private String name;

    // getter 和 setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
  }
  ```

  * 注意事項
    * Json 格式轉換要小心
      * 和 Json 格式一一對應的 Java class
  
* @RequestHeader
  * 接住放在 request header 中的參數
  * 前端也是可以在 request header 中傳遞參數給後端的（通常前端是會將「權限認證」或是「通用資訊」，放在 request header 中傳遞）

  ```java
    @RequestMapping("/header")
    public String header(@RequestHeader String info){
        return info;
    }
  ```
* @PathVariable
  * 接住放在 url 路徑中的值
  * 注意事項
    * 「url 路徑」和「參數」的名字要一致
    * 參數類型需一致

    ```java
      @GetMapping("/name/{message}")
      public String getNameWithMessage(@PathVariable("message") String message){
          return "hello, World! " + printer.print(message);
      }
    ```
    
  * 為什麼我們需要 @PathVariable？
    * 假設我們要傳遞 id 為 123 的參數給前端的話，其實完全可以使用 **@RequestParam** 來傳遞
    * 為什麼要大費周章把 123 的值給塞到 **url 路徑** 裡面，然後再透過 **@PathVariable** 來取得
    * 就是 **「為了支援 RESTful API 的設計風格」**
  

# 什麼是 API？

* 用特定的格式，去表示某個功能到底要怎麼使用
* API 的目的，就是「用工程師看得懂的方式，去說明某個方法要如何使用」
* Example:
  * 把「取得商品列表」的 API 給定義好了，
  * 像是我們會去說明
    * 要使用 GET 方法來請求、
    * 並且請求參數 (query parameter) 要帶上什麼、
    * 以及可能的 Http response 回覆會是什麼
    * ![alt text](img/API.png)

# [什麼是 RESTful API？](https://ithelp.ithome.com.tw/articles/10335071)

* 符合 REST 風格的 API
* RESTful API 的設計
  * 使用 Http method 表示動作
    * POST、GET、PUT、DELETE 分別去對應到資料庫的 Create、Read、Update、Delete 操作
    ![alt text](img/http_method_action.png)
  * 使用 url 路徑來描述資源之間的階層關係
    * Example:
      * 有一個 API GET /users
        * 根據我們剛剛所介紹的 REST 風格，知道 GET 是去對應到資料庫的「Read 查詢」操作，
        * 因此這個 API GET /users 的含義，就是去「取得所有的 user」
      * 另一個 API GET /users/123
        * 這個 API 的含義，就是去「取得在所有的 user 裡面，user id 為 123 的那個 user」
      ![alt text](img/restful_url.png)
  * 使用 Json 或是 Xml 回傳
    * REST 風格會要求後端程式所回傳的 response body，必須要是 Json 或是 Xml 的格式
    * 其實就是在 class 上面加上 @RestController，這樣就可以正確的返回 Json 格式了
   
    ![alt text](img/restful_return_json.png)
* **RESTful API 的目的，是為了「簡化工程師之間的溝通成本」，並不是設計 API 的標準規範**

# 在 Spring Boot 中設計和實作 RESTful API

```java
@RestController
public class StudentController {

    @PostMapping("/students")
    public String create(@RequestBody Student student){
        return "執行資料庫的Create操作";
    }

    @GetMapping("/students/{id}")
    public String read(@PathVariable String id){
        return "執行資料庫的Read操作";
    }

    @PutMapping("/students/{id}")
    public String update(@PathVariable String id,
                         @RequestBody Student student){
        return "執行資料庫的Update操作";
    }

    @DeleteMapping("/students/{id}")
    public String delete(@PathVariable String id){
        return "執行資料庫的Delete操作";
    }
}
```

# 什麼是 Http status code（Http 狀態碼）？

* 屬於 Http response 的一部分
* 表示這次 Http request 的結果為何

![alt text](img/http_response.png)

* Http status code 中的分類
  * 1xx : 資訊
    * 取得資訊
  * 2xx : 成功
    *  200 OK
       *  這一次的 http 請求成功了
    *  201 Created
       *  http 請求成功且表示「有一個新的資源成功被創建了
    *  202 Accepted
       *  這一次的請求已經被接受了，但是尚未處理完成

    ![alt text](img/success_200.png)

  * 3xx : 重新導向
    * 301 Moved Permanently
      * 這個 url 永久性的搬家了
        * 通常後端在回傳 301 的時候，同時也會將新的 url 放在 response header 裡面，告訴前端要去哪裡找新家，所以前端就可以改成去請求這個新的 url
    * 302 Found
      * 這個 url 暫時性的搬家
        * 通常後端在回傳 302 的時候，也是會將新的 url 放在 response header 裡面，告訴前端要去哪裡找新家，所以前端就可以改成去請求這個新的 url
    * 301 和 302 差在哪裡？
      * 就只是這個 url 是「永久性的搬家」還是「暫時性的搬家」而已
      * 對於後端這邊來說，在設計上都是要將新的 url 放在 response header 裡面，告訴前端新的 url 在哪裡

    ![alt text](img/redirect_300.png)

  * 4xx : 前端請求錯誤
    * 400 Bad Request
      * 前端的請求參數有錯誤
    * 401 Unauthorized
      * 沒有通過身份驗證
      * Example:
        * 帳密輸入錯誤
    * 403 Forbidden
      * 這一次的請求被後端拒絕」，
      * 這個拒絕通常是因為 **權限不足** 導致的
      * Example:
        * 這個會員他沒有權限，去執行這個功能
    * 404 Not Found
      * 這個網頁不存在
      * 通常就是由於 url 輸入錯誤、或是 url 失效（該資源被移走）所導致的

    ![alt text](img/frondend_error_400.png)

  * 5xx : 後端處理有問題
    * 500 Internal Server Error
      * 後端在處理這次請求的時候發生了錯誤
      * 可能是因為後端程式出了 bug、或是其他的原因造成的
    * 503 Service Unavailable
      * 臨時維護或者流量太大，所以後端目前沒有辦法處理請求
    * 504 Gateway Timeout
      * 這一次的請求超時了
      *  http 請求花了太長的時間都還沒有完成，所以就直接強制結束

    ![alt text](img/backend_error_500.png)

# 什麼是 Spring JDBC？

* 能夠在 Spring Boot 中執行 sql 語法，進而去操作資料庫

## Spring JDBC 根據 sql 分成兩大類：update 和 query

* 在 update 系列的方法中，可以去執行 
  * INSERT、
  * UPDATE、
  * DELETE 這三種 sql 語法
* 而在 query 系列的方法中，只能執行 
  * SELECT 這一種 sql 語法

## update() 的基本用法

* 分成 4 個步驟
  * 注入一個 NamedParameterJdbcTemplate
    * 在你的 Bean 裡面，先去注入
    
    ```java
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    ```

    * NamedParameterJdbcTemplate
      * 是 Spring JDBC 自動幫我們生成的 Bean，
      * 負責去處理和資料庫溝通的所有事項

  * 撰寫 sql 語法
    * 注意database要對，table和column也要對
  ```java
  String sql = "INSERT INTO student(id, name) VALUES (3, 'John')";
  ```

  *  新增一個 Map<String, object> 的 map 變數
     *  放 sql 語法裡面的變數的值
     *  想要「動態的決定」當前 sql 語法中的值的話，那就需要依靠 map 
        *  修改 sql 語法、添加 map 變數中的值
           *  sql 語法：將「3 和 John」的地方，改成「:studentId 和 :studentName」
              *  :studentId
              *  就表示我們指定這是一個 sql 中的變數，名字叫做 studentId
              *  :studentName
              *  就表示我們指定這又是另一個 sql 中的變數，名字則是叫做 studentName
           *  map 變數：在 map 變數中 put 兩組 key-value 的值進去

           ```java
               @RequestMapping("/students/insert/map")
              public String insertMap(@RequestBody Student student){
                  String sql = "INSERT INTO user(id, user_name) VALUES (:studentId, :studentName)";
                  Map<String, Object> map = new HashMap<>();
                  map.put("studentId", student.getId());
                  map.put("studentName", student.getName());
                  namedParameterJdbcTemplate.update(sql, map);
                  return "執行 INSERT SQL";
              } 
            ```
  
  ```java
  Map<String, Object> map = new HashMap<>();
  ```

  *  使用 update() 方法
     *  使用 namedParameterJdbcTemplate 的 update() 方法，
     *  並且把上面所新增的 sql 和 map 這兩個變數，依照順序的給傳進去
  
  ```java
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @RequestMapping("/students/insert")
    public String insert(){
        String sql = "INSERT INTO user(id, user_name) VALUES (3, 'John')";
        Map<String, Object> map = new HashMap<>();
        namedParameterJdbcTemplate.update(sql, map);
        return "執行 INSERT SQL";
    }
  ```

Quer## query() 的用法

* 前兩個參數和 update() 方法一樣，都是先放入「要執行的 sql 語法」，接著是放入「動態決定 sql 變數的 map」
* 特別的地方，就在於他的第三個參數 RowMapper
  * 將資料庫查詢出來的數據，轉換成是 Java object
  * 像是我們可以創建一個新的 StudentRowMapper class，然後讓他去 implements RowMapper 這個 interface，實作如下的程式：

  ```java

  package com.hello.mapper;

  import com.hello.entity.Student;
  import org.springframework.jdbc.core.RowMapper;


  import java.sql.ResultSet;
  import java.sql.SQLException;

  public class StudentRowMapper implements RowMapper<Student> {
      @Override
      public Student mapRow(ResultSet rs, int rowNum) throws SQLException{
          Student student = new Student();
          student.setId(rs.getInt("id"));
          student.setName(rs.getString("user_name"));
          return student;
      }

  }
  ```

  ```java
    @RequestMapping("/students/query/{id}")
    public List<Student> query(@PathVariable String id){
        String sql = "SELECT id, user_name from user where id= :studentId";
        Map<String, Object> map = new HashMap<>();
        RowMapper<Student> rowMapper = new StudentRowMapper();
        map.put("studentId", id);
        return namedParameterJdbcTemplate.query(sql, map, rowMapper);
    }
  ```

![alt text](img/query_rowmapper.png)



## 在 Spring Boot 中操作資料庫

* 常見的操作資料庫的工具有:
  * 在 Spring Boot 中執行 sql 語法，去操作資料庫
    * 直接在 Spring Boot 中去執行原始的 sql 語法，然後透過這些 sql 語法去存取資料庫的數據這樣
    * Spring JDBC
    * MyBatis
  * 使用 ORM(Object Relational Mapping) 的概念，去操作資料庫
    * 使用這類的工具，基本上就很少寫 sql 語法了，而是會套用另一種新的概念（即是 ORM），去存取資料庫的數據
    * Spring Data JPA
    * Hibernate

## 什麼是 CRUD？

* Create（新增）
* Read（查詢）
* Update（修改）
* Delete（刪除）

![alt text](img/CRUD.png)


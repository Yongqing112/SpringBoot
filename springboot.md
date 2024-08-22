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
# Install Environment

## Step 1: Spring Boot

* Intellij New Project
	* Build System: **Maven**

* pom.xml
	* add dependency
		```
		<dependencies>
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-web</artifactId>
				<version>3.3.1</version>
			</dependency>

			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-test</artifactId>
				<version>3.3.1</version>
			</dependency>
		</dependencies>
		```

	* add parent
		* parent can avoid duplicated settings.
		```
		<parent>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-parent</artifactId>
			<version>3.3.1</version>
		</parent>

		<dependencies>
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-web</artifactId>
			</dependency>

			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-test</artifactId>
			</dependency>
		</dependencies>
		```

* Reload All Maven Project
	* Click *m*
	* Button is on the toolbar.

* Install Plugin
	* Spring Boot Assistant

* add src/main/resources/application.yml
	* Set configure
	```
	server:
	port: 8080

	spring:
	application:
		name: helloWorld
	```

* Run Spring Boot
	* add src/main/java/com/hello/Main.java
	```
	package com.hello;

	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;

	@SpringBootApplication
	public class Main {

		public static void main(String[] args) {
			SpringApplication.run(Main.class);
		}
	}
	```

	* add src/main/java/com/hello/controller/UserController.java
	```
	package com.hello.controller;

	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.RestController;

	@RestController
	public class UserController {

		@GetMapping("/name")
		public String getName(){
			return "hello, World!";
		}
	}
	```

	* Click **Run 'Main.main()'**

	* Open browser and input URL http://localhost:8080/name

	* You can see "hello, World!"

* Finish
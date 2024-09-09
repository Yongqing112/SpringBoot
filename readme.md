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

## Step 2: Connect to Database

* [安裝教學網站](https://onway2017.wordpress.com/2022/06/27/sql-%E5%A6%82%E4%BD%95%E4%B9%BE%E6%B7%A8%E7%9A%84%E5%8D%B8%E8%BC%89%E9%87%8D%E8%A3%9Dmysql/)
* add dependency in pom.xml
	```
	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
		<version>8.0.33</version>
	</dependency>

	<dependency>
		<groupId>org.mybatis.spring.boot</groupId>
		<artifactId>mybatis-spring-boot-starter</artifactId>
		<version>3.0.3</version>
	</dependency>
	```

* add src/main/resources/mapper/uUser/UserMapper.xml
	```
	<?xml version="1.0" encoding="UTF-8" ?>
	<!DOCTYPE mapper
			PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
			"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	<mapper namespace="com.hello.mapper.UserMapper">
	<!--    id is UserMapper.queryUserName()    -->
	<!--    #{id} is UserMapper.queryUserName() parameter "String id"    -->
		<select id="queryUserName" resultType="java.lang.String" parameterType="java.lang.String">
			select user_name from uUser where id=#{id}
		</select>
	</mapper>
	```

* add src/main/java/com/hello/mapper/UserMapper.java
	```
	package com.hello.mapper;

	public interface UserMapper {

		String queryUserName(String id);
	}
	```

* Install Plugin 
	* MybatisX
		* the plugin can help to link XML and Java files to each other
		* Example: You can see the code in xml file
		```
		<mapper namespace="com.hello.mapper.UserMapper">
		```
		* and then you can see an icon beside the line on which you click the icon can jump to UserMapper.java and vice versa

* add src/main/java/com/hello/service/impl/UserServiceImpl.java
	```
	package com.hello.service.impl;

	import com.hello.mapper.UserMapper;
	import com.hello.service.UserService;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

	@Service
	public class UserServiceImpl implements UserService {

		@Autowired
		private UserMapper userMapper;

		@Override
		public String queryUserName(String id) {
			return userMapper.queryUserName(id);
		}
	}
	```

*	add new API in UserController
	```
		@Autowired
		private UserService userService;

		@GetMapping("/queryUserName/{id}")
		public String queryUserName(@PathVariable("id") String id){
			return userService.queryUserName(id);
		}
	```

* add database information in application.yml
	```
	  datasource:
	#    hello is database name
		url: jdbc:mysql://localhost:3306/hello?uUser=root
		username: root
		password: springboot

	mybatis:
	#  Before executing, you need to scan XML files otherwise, they appear as errors when invoke
	mapper-locations:
		- classpath:/mapper/**/*.xml
	```

* add Mapper Scan in Main file
	```
	@MapperScan("com.hello.mapper")
	```

* Run and open browser with http://localhost:8080/queryUserName/1
	* id = 1 is test

* Finish

## Step 3: Log

* add src/main/resources/logback.xml
	* copy github code
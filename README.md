# Practice-Repository

Here's a **Java Selenium & REST Assured Cheat Sheet** organized in an Excel-like table format. This combines key concepts, methods, and workflows for both web automation (Selenium) and API testing (REST Assured):

---

### **Java Selenium Cheat Sheet**

| **Category**          | **Key Concepts/Methods**                                                                 | **Example/Notes**                                                                 |
|-----------------------|------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| **Setup**             | `WebDriver` initialization                                                              | `WebDriver driver = new ChromeDriver();`<br>`System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");` |
| **Navigation**        | `get()`, `navigate().to()`, `navigate().back()`, `navigate().forward()`, `refresh()`     | `driver.get("https://example.com");`<br>`driver.navigate().back();`               |
| **Locators**          | `By.id()`, `By.name()`, `By.xpath()`, `By.cssSelector()`, `By.className()`, `By.tagName()` | `driver.findElement(By.id("username"));`<br>`driver.findElements(By.xpath("//div[@class='item']"));` |
| **Element Interaction**| `click()`, `sendKeys()`, `clear()`, `submit()`, `getText()`, `getAttribute()`, `isDisplayed()` | `element.sendKeys("text");`<br>`element.getAttribute("href");`                    |
| **Waits**             | Implicit Wait: `driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);`<br>Explicit Wait: `WebDriverWait` + `ExpectedConditions` | `WebDriverWait wait = new WebDriverWait(driver, 10);`<br>`wait.until(ExpectedConditions.visibilityOf(element));` |
| **Dropdowns**         | `Select` class: `selectByValue()`, `selectByIndex()`, `selectByVisibleText()`            | `Select dropdown = new Select(driver.findElement(By.id("dropdown")));`<br>`dropdown.selectByIndex(1);` |
| **Frames/Alerts**     | Switch to frame: `driver.switchTo().frame("frameName");`<br>Handle alerts: `Alert alert = driver.switchTo().alert();` | `alert.accept();`<br>`alert.dismiss();`                                           |
| **Cookies**           | `manage().addCookie()`, `getCookieNamed()`, `deleteAllCookies()`                         | `driver.manage().addCookie(new Cookie("name", "value"));`                         |
| **Screenshot**        | `TakesScreenshot` interface: `getScreenshotAs()`                                         | `File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);`      |
| **Advanced**          | Actions class (`dragAndDrop()`, `doubleClick()`), JavaScriptExecutor                     | `Actions actions = new Actions(driver);`<br>`actions.dragAndDrop(source, target).perform();` |

---

### **REST Assured Cheat Sheet**

| **Category**          | **Key Concepts/Methods**                                                                 | **Example/Notes**                                                                 |
|-----------------------|------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| **Setup**             | Maven Dependency:                                                                        | ```xml<br><dependency><br>  <groupId>io.rest-assured</groupId><br>  <artifactId>rest-assured</artifactId><br>  <version>5.3.0</version><br></dependency>``` |
| **Request Basics**    | `given()`, `when()`, `then()`                                                            | ```java<br>given().param("key", "value")<br>.when().get("/endpoint")<br>.then().statusCode(200);``` |
| **HTTP Methods**      | `get()`, `post()`, `put()`, `delete()`, `patch()`                                        | `when().post("/users");`<br>`when().put("/users/1");`                            |
| **Headers/Body**      | `header()`, `contentType()`, `body()`                                                    | `given().header("Authorization", "Bearer token")`<br>`.body(jsonPayload).when().post(...);` |
| **Path/Query Params** | `pathParam()`, `queryParam()`                                                            | `given().pathParam("id", 123).when().get("/users/{id}");`                        |
| **JSON Validation**   | `body("key", equalTo(value))`, `body("list.size()", equalTo(3))`                         | `.then().body("data.name", equalTo("John"))`<br>`.body("users[0].id", equalTo(1));` |
| **Schema Validation** | `matchesJsonSchemaInClasspath()`                                                         | `.then().body(matchesJsonSchemaInClasspath("schema.json"));`                     |
| **Authentication**    | `auth().basic()`, `auth().oauth2()`, `auth().preemptive()`                               | `given().auth().basic("user", "pass")`<br>`given().auth().oauth2("token")`       |
| **Response Extraction**| `extract().response()`, `jsonPath()`, `xmlPath()`                                        | `Response response = get("/users").then().extract().response();`<br>`String name = response.jsonPath().get("data.name");` |
| **Logging**           | `log().all()`, `log().body()`, `log().headers()`                                         | `given().log().all().when().get(...)`<br>`then().log().body();`                  |
| **Advanced**          | Multipart requests, SSL configuration, Filters                                           | `given().multiPart("file", new File("test.txt"))`<br>`given().relaxedHTTPSValidation();` |

---

### **Key Notes**  
**Selenium Best Practices**:  
1. Use explicit waits over implicit waits.  
2. Avoid `Thread.sleep()`; prefer `ExpectedConditions`.  
3. Use `Page Object Model (POM)` for maintainability.  
4. Close sessions with `driver.quit()`.  

**REST Assured Best Practices**:  
1. Reuse specifications with `RequestSpecBuilder`/`ResponseSpecBuilder`.  
2. Validate status codes, headers, and schemas.  
3. Use JSON/XML path assertions for complex validations.  
4. Extract reusable values from responses (e.g., tokens).  

---

### **Common Imports**  
```java
// Selenium  
import org.openqa.selenium.*;  
import org.openqa.selenium.support.ui.*;  

// REST Assured  
import io.restassured.RestAssured.*;  
import io.restassured.matcher.RestAssuredMatchers.*;  
import io.restassured.path.json.JsonPath;  
import static org.hamcrest.Matchers.*;  
```

---

### **Example Workflows**  
**Selenium**: Login Test  
```java  
driver.get("https://example.com/login");  
driver.findElement(By.id("username")).sendKeys("user");  
driver.findElement(By.id("password")).sendKeys("pass");  
driver.findElement(By.id("submit")).click();  
WebDriverWait wait = new WebDriverWait(driver, 10);  
wait.until(ExpectedConditions.titleContains("Dashboard"));  
```

**REST Assured**: API Test  
```java  
given()  
  .baseUri("https://api.example.com")  
  .header("Content-Type", "application/json")  
  .body("{ \"name\": \"John\" }")  
.when()  
  .post("/users")  
.then()  
  .statusCode(201)  
  .body("id", notNullValue())  
  .body("name", equalTo("John"));  
```

---


**comprehensive interview-focused cheat sheet** that covers **Java**, **Selenium**, and **REST Assured**, including **scenarios**, **best practices**, **common questions**, and **edge cases**. I’ll break this into sections for clarity.

---

### **1. Core Java for Interviews**  
**Key Topics & Scenarios**  
| **Topic**               | **Common Questions/Scenarios**                                                                 | **Sample Answers/Code**                                                                 |
|-------------------------|------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------|
| **OOP Concepts**        | Explain abstraction vs. encapsulation, polymorphism (overloading/overriding), inheritance vs. composition. | *"Abstraction hides complexity (e.g., interfaces), encapsulation bundles data and methods (e.g., private fields). Composition is preferred over inheritance for flexibility."* |
| **Exception Handling**  | Difference between `throw` and `throws`, checked vs. unchecked exceptions, custom exceptions. | ```java try { ... } catch (IOException e) { ... } finally { ... } ```                  |
| **Collections**         | When to use `ArrayList` vs. `LinkedList`, how `HashMap` works internally (hashing, buckets).  | *"HashMap uses hashCode() and equals() for key lookups. Load factor triggers rehashing."* |
| **Multithreading**      | `Runnable` vs. `Thread`, `synchronized` keyword, `volatile`, thread pools, deadlock scenarios. | ```java ExecutorService executor = Executors.newFixedThreadPool(5); ```                |
| **JDBC**                | Connection pooling, difference between `Statement` and `PreparedStatement`.                   | *"PreparedStatement prevents SQL injection and is precompiled."*                       |
| **Design Patterns**     | Singleton (thread-safe), Factory, Builder.                                                   | ```java // Thread-safe Singleton public class Singleton { private static volatile Singleton instance; ... } ``` |

---

### **2. Selenium Interview Scenarios**  
**Common Questions & Solutions**  
| **Scenario**                    | **What Interviewers Ask**                                                                 | **How to Answer/Code**                                                                 |
|---------------------------------|------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------|
| **Dynamic Elements**            | How to handle elements with changing IDs/XPaths?                                         | Use relative XPath/CSS with attributes: `//div[contains(@class, 'button')]` or `driver.findElement(By.xpath("//*[text()='Submit']"))`. |
| **Waits**                       | Explain implicit vs. explicit vs. fluent waits.                                          | *"Explicit waits wait for specific conditions (e.g., element clickable). Avoid Thread.sleep()."*<br>`WebDriverWait wait = new WebDriverWait(driver, 10); wait.until(ExpectedConditions.visibilityOf(element));` |
| **Dropdowns**                   | How to select a value from a non-`<select>` dropdown?                                    | Use `Actions` class or JavaScript: <br>`actions.click(dropdownElement).sendKeys("Option1").perform();` |
| **iFrames**                     | How to switch to an iframe and interact with elements?                                   | `driver.switchTo().frame("frameName");`<br>After interaction: `driver.switchTo().defaultContent();` |
| **Alerts/Popups**               | Handle JavaScript alerts.                                                                | `Alert alert = driver.switchTo().alert(); alert.accept();`                            |
| **File Upload**                 | Upload a file using Selenium.                                                            | `driver.findElement(By.id("fileInput")).sendKeys("C:/path/to/file.txt");`             |
| **Cross-Browser Testing**       | How to run tests on Chrome, Firefox, etc.?                                               | Use WebDriverManager: <br>`WebDriverManager.chromedriver().setup();`<br>`WebDriver driver = new ChromeDriver();` |
| **Page Object Model (POM)**     | What is POM? How does it improve maintainability?                                        | *"POM separates page logic from tests. Each page is a class with elements and actions."* |
| **Headless Browsers**           | Run tests in headless mode.                                                              | ```java ChromeOptions options = new ChromeOptions(); options.addArguments("--headless"); ``` |

---

### **3. REST Assured Interview Scenarios**  
**Key Topics & Solutions**  
| **Scenario**                    | **What Interviewers Ask**                                                                 | **How to Answer/Code**                                                                 |
|---------------------------------|------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------|
| **Authentication**              | How to test APIs with OAuth2, JWT, or Basic Auth?                                        | ```java given().auth().oauth2("token")... ```<br>For Basic Auth: `given().auth().preemptive().basic("user", "pass")...` |
| **Response Validation**         | Validate JSON array size, nested objects, or status codes.                              | ```java .then().body("data.size()", equalTo(5)) .body("user.name", equalTo("John")) ``` |
| **Schema Validation**           | Validate JSON schema compliance.                                                        | ```java .then().body(matchesJsonSchemaInClasspath("schema.json")); ```                |
| **Query/Path Params**           | Pass dynamic path parameters or query strings.                                           | ```java given().pathParam("id", 123).queryParam("status", "active")... ```            |
| **Request Logging**             | Log request/response details for debugging.                                              | `given().log().all().when().get(...).then().log().body();`                            |
| **Multipart File Upload**       | Upload a file via API.                                                                   | ```java given().multiPart("file", new File("test.txt"))... ```                        |
| **Extracting Values**           | Extract a value from response for subsequent requests.                                   | ```java String token = given().when().post("/login").jsonPath().getString("token"); ``` |
| **SSL Certificates**            | Handle HTTPS/SSL errors.                                                                 | `given().relaxedHTTPSValidation();`                                                  |

---

### **4. Behavioral & Problem-Solving Scenarios**  
| **Scenario**                    | **Interviewer’s Goal**                                                                  | **How to Respond**                                                                     |
|---------------------------------|-----------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------|
| **Debugging Failing Tests**     | Assess problem-solving skills.                                                          | *"I check logs, add breakpoints, isolate the issue (e.g., network, element locators, timing), and use tools like Postman for API debugging."* |
| **Test Framework Design**       | How would you structure a scalable automation framework?                               | *"I’d use POM for Selenium, REST Assured for APIs, TestNG/JUnit for execution, Maven/Gradle for builds, and integrate CI/CD pipelines."* |
| **Handling Flaky Tests**         | What causes flaky tests, and how do you fix them?                                       | *"Flakiness comes from poor waits, dynamic elements, or environment issues. I use explicit waits, retry mechanisms, and stable locators."* |
| **Team Collaboration**          | How do you share automation results with non-technical stakeholders?                    | *"I use dashboards (Extent Reports, Allure), Jira integration, and highlight key metrics like pass/fail rates."* |

---

### **5. Coding Challenges**  
**Java/Selenium**  
1. **Reverse a String**  
   ```java
   public String reverse(String str) {
     return new StringBuilder(str).reverse().toString();
   }
   ```
2. **Find Element in Rotated Sorted Array** (Algorithm question).  
3. **Write a Singleton Class** (thread-safe).  

**REST Assured**  
1. **Validate a Complex JSON Response**  
   ```java
   .then().body("data.findAll { it.age > 30 }.size()", equalTo(2));
   ```
2. **Chain Requests** (e.g., get a token from one API and use it in another).  

---

### **6. Tools & Best Practices**  
- **Selenium**: Use `WebDriverManager` for driver setup, `TestNG`/`JUnit` for test execution, `Allure` for reporting.  
- **REST Assured**: Reuse specs with `RequestSpecBuilder`, validate schemas, and use `JsonPath`/`XmlPath` for extraction.  
- **General**: Version control (Git), CI/CD (Jenkins, GitLab CI), and containerization (Docker).  

---

### **7. Common Interview Questions**  
1. **Java**:  
   - *What is the difference between `==` and `.equals()`?*  
   - *How does garbage collection work in Java?*  
2. **Selenium**:  
   - *How do you handle stale elements?* (Re-locate the element or use explicit waits.)  
   - *What is the difference between `findElement` and `findElements`?*  
3. **REST Assured**:  
   - *How do you test a POST API with a payload?*  
   - *What is the difference between `@Test` and `@BeforeMethod` in TestNG?*  

---

### **Final Tips**  
1. **Mocking**: Be prepared to discuss tools like Mockito for Java or WireMock for API mocking.  
2. **Performance**: Mention tools like JMeter or Gatling if asked about performance testing.  
3. **Stay Calm**: For coding challenges, talk through your thought process. Interviewers care about how you approach problems.  

          **GOOD LUCK**
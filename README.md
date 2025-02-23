# Practice-Repository
Created this repository to add my work in it.


(Due to technical issues, the search service is temporarily unavailable.)

Got it! Let’s create a **comprehensive interview-focused cheat sheet** that covers **Java**, **Selenium**, and **REST Assured**, including **scenarios**, **best practices**, **common questions**, and **edge cases**. I’ll break this into sections for clarity.

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

Let me know if you want to deep-dive into any specific area! 🚀
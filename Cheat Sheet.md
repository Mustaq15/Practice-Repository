***INTERVIEW PREPARATION***

---

## **Handling Multiple Windows in Selenium (Java)**
### **1. Get the Current Window Handle**
```java
String parentWindow = driver.getWindowHandle();
```
- Stores the main window’s handle for reference.

### **2. Get All Window Handles**
```java
Set<String> allWindows = driver.getWindowHandles();
```
- Returns a set of all open window handles.

### **3. Switch to a New Window**
```java
for (String windowHandle : allWindows) {
    if (!windowHandle.equals(parentWindow)) {
        driver.switchTo().window(windowHandle);
        break;
    }
}
```
- Iterates through all window handles and switches to the new one.

### **4. Perform Actions in the New Window**
```java
driver.findElement(By.id("elementID")).click();
```
- Interact with elements in the new window.

### **5. Close the New Window & Switch Back**
```java
driver.close(); // Closes the current window
driver.switchTo().window(parentWindow); // Switch back to the main window
```
- Always switch back to the parent window after closing the new one.

---

## **Alternative: Using `ArrayList` for Tab Handling**
```java
ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());

driver.switchTo().window(tabs.get(1)); // Switch to the second tab
// Perform actions on the second tab
driver.close(); // Close the second tab

driver.switchTo().window(tabs.get(0)); // Switch back to the first tab
```
- Uses an `ArrayList` to index and manage window handles.

---

## **Using a Method for Dynamic Window Handling**
```java
public static void switchToWindow(String title) {
    for (String handle : driver.getWindowHandles()) {
        driver.switchTo().window(handle);
        if (driver.getTitle().equals(title)) {
            return;
        }
    }
}
```
- Switches to a window based on the title.


---

# **Switching to a Child Frame in Selenium (Java)**  

## **1. Switch to a Frame by Index**  
```java
driver.switchTo().frame(0); // Switch to the first frame (index starts from 0)
```
- Useful when there are multiple frames, but not recommended if the structure changes dynamically.

---

## **2. Switch to a Frame by Name or ID**  
```java
driver.switchTo().frame("frameName"); // Switch using frame's name
driver.switchTo().frame("frameID");   // Switch using frame's ID
```
- Works if the frame has a unique `name` or `id` attribute.

---

## **3. Switch to a Frame by WebElement**  
```java
WebElement frameElement = driver.findElement(By.xpath("//iframe[@id='frameID']"));
driver.switchTo().frame(frameElement);
```
- Useful when the frame lacks an ID or name but can be located using XPath or CSS.

---

## **4. Switch Back to the Main Page (Parent Frame)**  
```java
driver.switchTo().parentFrame(); // Switch to the immediate parent frame
```
- Use this when inside a nested frame and you need to move one level up.

---

## **5. Switch Back to the Default Content (Main Page)**  
```java
driver.switchTo().defaultContent(); // Switch to the main HTML document
```
- Use this when you want to exit all frames and return to the main page.

---

## **6. Handling Nested Frames (Switch from Parent to Child Frame)**  
```java
driver.switchTo().frame("parentFrame"); // Switch to parent frame
driver.switchTo().frame("childFrame");  // Switch to child frame inside parent
```
- This approach works when dealing with nested frames.

---
### **Why Use `@FindBy` Annotation in Selenium?**  

In Selenium, we can locate web elements using **`driver.findElement(By.locator())`**, but using **`@FindBy` annotation** (provided by **Page Factory**) has several advantages.  

---

## **Advantages of Using `@FindBy` Annotation**  

### ✅ **1. Improves Code Readability & Maintainability**  
- Separates locators from test logic, making tests more readable.  
- Changes in locators require updates only in one place.  

#### **Example (Without `@FindBy`)**  
```java
WebElement loginButton = driver.findElement(By.id("login"));
loginButton.click();
```

#### **Example (With `@FindBy`)**
```java
@FindBy(id = "login")
WebElement loginButton;

public void clickLogin() {
    loginButton.click();
}
```
- Now, the locator is **defined only once** and can be used multiple times.

---

### ✅ **2. Enhances Code Reusability**  
- Once initialized, elements can be used across different test methods.  

---

### ✅ **3. Improves Performance with Lazy Initialization**  
- Elements are only **fetched when needed**, reducing unnecessary DOM searches.  

```java
PageFactory.initElements(driver, this);
```
- `PageFactory` initializes elements **only when interacting with them**, optimizing performance.

---

### ✅ **4. Supports Multiple Locators (`@FindBys`, `@FindAll`)**  
- Helps in scenarios where a **single locator is not enough**.  

#### **Using `@FindBys` (AND Condition)**
```java
@FindBys({
    @FindBy(tagName = "input"),
    @FindBy(className = "login-field")
})
WebElement loginField;
```
- Finds an element that **matches both conditions**.

#### **Using `@FindAll` (OR Condition)**
```java
@FindAll({
    @FindBy(id = "username"),
    @FindBy(name = "user")
})
WebElement username;
```
- Finds an element that **matches any condition**.

---

### ✅ **5. Reduces `StaleElementReferenceException`**  
- Elements are **re-fetched automatically** when needed.

---

## **How to Use `@FindBy` Annotation?**  

### **1. Import Required Packages**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
```

### **2. Define Page Objects with `@FindBy`**
```java
public class LoginPage {
    
    WebDriver driver;

    @FindBy(id = "username")
    WebElement usernameField;

    @FindBy(name = "password")
    WebElement passwordField;

    @FindBy(xpath = "//button[text()='Login']")
    WebElement loginButton;

    // Constructor to initialize elements
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Method to perform login
    public void login(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }
}
```
- This makes the test script **clean and reusable**.  

---

## **When to Use `@FindBy` vs. `findElement`?**
| **Scenario**                | **Use `@FindBy`** | **Use `findElement`** |
|-----------------------------|------------------|------------------|
| Page Object Model (POM)     | ✅ Yes           | ❌ No           |
| Single-time element usage   | ❌ No           | ✅ Yes          |
| Dynamic elements            | ❌ No           | ✅ Yes          |
| Better performance & reusability | ✅ Yes | ❌ No |

---

## **Conclusion**  
Using `@FindBy` with Page Factory helps in **better maintainability, readability, and performance**. If you’re following the **Page Object Model (POM)**, `@FindBy` is highly recommended! 🚀  

---
### **Real-World Implementation of `@FindBy` in Selenium using Page Factory**  

Let's consider a **real-world login scenario** where we automate the login process of a web application using `@FindBy`.  

---

## **Scenario**  
- Navigate to the login page.  
- Enter **username** and **password**.  
- Click the **Login** button.  
- Verify if the login was successful.  

---

## **1️⃣ Create a Base Test Class (for Setup & Teardown)**  
This class initializes the WebDriver and manages browser setup.  

```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    public void setUp() {
        // Initialize WebDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Open the login page
        driver.get("https://example.com/login");
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Close the browser
        }
    }
}
```

---

## **2️⃣ Create the `LoginPage` Class (Using `@FindBy`)**  
This class represents the login page using Page Object Model (POM).  

```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    WebDriver driver;

    // Locating elements using @FindBy
    @FindBy(id = "username")
    WebElement usernameField;

    @FindBy(name = "password")
    WebElement passwordField;

    @FindBy(xpath = "//button[text()='Login']")
    WebElement loginButton;

    @FindBy(css = ".error-message")
    WebElement errorMessage;

    // Constructor to initialize elements
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Action methods
    public void enterUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }
}
```

---

## **3️⃣ Create a Login Test Class**
This class contains test cases for login functionality.  

```java
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    LoginPage loginPage;

    @BeforeMethod
    public void setupTest() {
        setUp();
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testValidLogin() {
        loginPage.enterUsername("testuser");
        loginPage.enterPassword("password123");
        loginPage.clickLogin();

        // Verify login success (example: checking the URL)
        Assert.assertEquals(driver.getCurrentUrl(), "https://example.com/dashboard");
    }

    @Test
    public void testInvalidLogin() {
        loginPage.enterUsername("invalidUser");
        loginPage.enterPassword("wrongPassword");
        loginPage.clickLogin();

        // Verify error message
        Assert.assertEquals(loginPage.getErrorMessage(), "Invalid username or password.");
    }

    @AfterMethod
    public void tearDownTest() {
        tearDown();
    }
}
```

---

## **🔹 Why is This a Real-World Implementation?**
✔ **Uses Page Object Model (POM)** for maintainability.  
✔ **Encapsulates element locators** in `@FindBy` for better readability.  
✔ **Implements reusable methods** for login functionality.  
✔ **Automates real login scenarios** (valid & invalid credentials).  
✔ **Uses TestNG for structured test execution.**  

---

# **TestNG: A Powerful Testing Framework for Java**  

## **What is TestNG?**  
TestNG (**Test Next Generation**) is a **testing framework** in Java inspired by JUnit and NUnit. It provides advanced features like **parallel execution, grouping, data-driven testing, and detailed reporting**, making it ideal for Selenium test automation.  

---

## **🔹 Key Features of TestNG**
- ✅ **Annotations (@Test, @BeforeMethod, @AfterMethod, etc.)**  
- ✅ **Parallel Execution of Tests**  
- ✅ **Dependency-Based Testing (`dependsOnMethods`)**  
- ✅ **Grouping of Test Cases (`groups`)**  
- ✅ **Data-Driven Testing (`@DataProvider`)**  
- ✅ **Flexible Test Configuration (`testng.xml`)**  
- ✅ **Built-in Reporting**  

---

# **🔹 TestNG Annotations & Example**
TestNG uses **annotations** to define the order and behavior of test methods.

### **Example: Basic TestNG Class**
```java
import org.testng.annotations.*;

public class TestNGExample {

    @BeforeClass
    public void setup() {
        System.out.println("Setup: Open Browser");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("BeforeMethod: Navigate to Page");
    }

    @Test
    public void testLogin() {
        System.out.println("Test: Login Test Executed");
    }

    @Test
    public void testSearch() {
        System.out.println("Test: Search Functionality");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("AfterMethod: Logout");
    }

    @AfterClass
    public void tearDown() {
        System.out.println("Teardown: Close Browser");
    }
}
```
### **Execution Order**
```
Setup: Open Browser
BeforeMethod: Navigate to Page
Test: Login Test Executed
AfterMethod: Logout
BeforeMethod: Navigate to Page
Test: Search Functionality
AfterMethod: Logout
Teardown: Close Browser
```

---

# **🔹 TestNG Annotations**
| **Annotation**    | **Description** |
|------------------|----------------|
| `@BeforeSuite`   | Runs **before all test cases** in the suite. |
| `@BeforeTest`    | Runs **before any test** in the `<test>` tag in `testng.xml`. |
| `@BeforeClass`   | Runs **before the first method of the class**. |
| `@BeforeMethod`  | Runs **before every test method**. |
| `@Test`          | Marks a method as a **test case**. |
| `@AfterMethod`   | Runs **after each test method**. |
| `@AfterClass`    | Runs **after all test methods** in the class. |
| `@AfterTest`     | Runs **after all tests** in the `<test>` tag. |
| `@AfterSuite`    | Runs **after all tests in the suite**. |

---

# **🔹 Advantages of TestNG**
## ✅ **1. Parallel Execution of Tests**
- Run multiple tests **simultaneously**, reducing execution time.
```xml
<suite name="Parallel Test Suite" parallel="methods" thread-count="2">
```

## ✅ **2. Data-Driven Testing (`@DataProvider`)**
- Test the same scenario with **multiple datasets**.
```java
@DataProvider(name = "loginData")
public Object[][] getData() {
    return new Object[][] {
        {"user1", "pass1"},
        {"user2", "pass2"}
    };
}

@Test(dataProvider = "loginData")
public void testLogin(String username, String password) {
    System.out.println("Logging in with: " + username + " / " + password);
}
```

## ✅ **3. Test Case Grouping (`groups`)**
- Run specific **groups of tests**.
```java
@Test(groups = "smoke")
public void smokeTest() {
    System.out.println("Running Smoke Test");
}
```

## ✅ **4. Test Case Dependency (`dependsOnMethods`)**
- Make test cases **dependent** on others.
```java
@Test
public void login() {
    System.out.println("Login Test");
}

@Test(dependsOnMethods = "login")
public void search() {
    System.out.println("Search Test after Login");
}
```

## ✅ **5. Retry Failed Tests Automatically (`IRetryAnalyzer`)**
- Re-run failed tests without manual intervention.

## ✅ **6. Generate Detailed Reports**
- **HTML reports** automatically generated after execution.

---

# **🔹 Running Tests using `testng.xml`**
TestNG allows execution via an **XML configuration file**.

### **Example: `testng.xml`**
```xml
<suite name="My Test Suite">
    <test name="Login Tests">
        <classes>
            <class name="com.tests.LoginTest"/>
        </classes>
    </test>
</suite>
```
Run using:
```
mvn test -DsuiteXmlFile=testng.xml
```

---

# **🔹 Conclusion**
TestNG is a powerful testing framework that improves Selenium automation with **structured execution, better reporting, parallel testing, and data-driven testing**. 🚀  

---
## **🔹 What is a Test Case?**  

A **Test Case** is a set of actions, conditions, and expected results used to verify whether a software application is working as intended. It helps testers **validate functionality**, find defects, and ensure quality.  

---

## **🔹 Components of a Test Case**
A well-written test case should include the following details:

| **Test Case Component** | **Description** |
|------------------------|----------------|
| **Test Case ID** | Unique identifier (e.g., `TC_Login_001`). |
| **Test Case Name** | Short, descriptive name (e.g., "Verify Login with Valid Credentials"). |
| **Test Objective** | The purpose of the test (e.g., "To check if a user can log in with valid credentials"). |
| **Preconditions** | Conditions that must be met before test execution (e.g., "User must be registered"). |
| **Test Steps** | A step-by-step process to execute the test. |
| **Test Data** | Input values required for testing (e.g., Username: `testuser`, Password: `pass123`). |
| **Expected Result** | The expected system behavior after execution. |
| **Actual Result** | The observed system behavior (filled after execution). |
| **Status** | Pass/Fail based on actual vs. expected results. |
| **Remarks/Comments** | Any additional notes or defects logged. |

---

## **🔹 Example of a Manual Test Case**  

| **Test Case ID**  | **TC_Login_001**  |
|------------------|----------------|
| **Test Case Name**  | Verify login with valid credentials |
| **Test Objective** | Ensure users can log in successfully with correct credentials. |
| **Preconditions**  | 1. The user must be registered.<br>2. The application must be open on the login page. |
| **Test Steps** | 1. Enter "testuser" in the Username field.<br>2. Enter "pass123" in the Password field.<br>3. Click on the "Login" button. |
| **Test Data**  | Username: `testuser`, Password: `pass123` |
| **Expected Result**  | User should be successfully logged in and redirected to the dashboard. |
| **Actual Result**  | (To be filled after execution) |
| **Status**  | (Pass/Fail) |
| **Remarks**  | (If failed, mention the issue) |

---

## **🔹 Types of Test Cases**
### ✅ **1. Functional Test Cases**  
- Verify if the application functions as expected (e.g., Login, Signup, Search).  

### ✅ **2. Non-Functional Test Cases**  
- Test performance, security, usability, etc. (e.g., Load Testing).  

### ✅ **3. Regression Test Cases**  
- Ensure new changes do not break existing functionality.  

### ✅ **4. Negative Test Cases**  
- Check system behavior with invalid inputs (e.g., Login with incorrect password).  

### ✅ **5. Boundary Test Cases**  
- Validate system behavior at input limits (e.g., min/max character validation).  

---

## **🔹 Difference Between Test Case and Test Scenario**
| **Aspect**        | **Test Case** | **Test Scenario** |
|------------------|-------------|---------------|
| **Definition**   | A detailed document with steps, inputs, and expected results. | A high-level test condition that covers multiple test cases. |
| **Example**      | "Verify login with valid credentials." | "Verify login functionality." |
| **Detail Level** | Very detailed | High-level overview |
| **Usage**        | Used by testers for step-by-step execution. | Helps in planning and coverage. |

---

## **🔹 Why Are Test Cases Important?**
✔ Ensure **consistent** testing and coverage.  
✔ Help in **reproducibility** of defects.  
✔ Serve as **documentation** for future testing.  
✔ Useful for **automation** in frameworks like Selenium & TestNG.  

---
## **🔹 Defining Severity and Priority in Software Testing**  

In software testing, **Severity** and **Priority** help classify and manage defects based on their impact and urgency.  

---

## **🔹 What is Severity?**
- **Definition**: **Severity** defines the **impact** of a defect on the application’s functionality.  
- **Decided by**: **Testers** (QA Team).  
- **Types of Severity**:  

| **Severity Level** | **Description** | **Example** |
|------------------|----------------|------------|
| **Critical** 🚨 | A **major failure** that **blocks** testing. No workaround available. | App crashes on login. |
| **High** 🔴 | A major function is **broken**, but some parts may still work. | Checkout button not working. |
| **Medium** 🟡 | A minor function is not working but **doesn’t block** the user. | UI alignment issue in a dropdown. |
| **Low** 🟢 | Cosmetic issue, spelling mistakes, or minor UI glitches. | Misspelled label on the home page. |

---

## **🔹 What is Priority?**
- **Definition**: **Priority** defines **how soon** a defect should be fixed based on business needs.  
- **Decided by**: **Developers & Business Team (Product Owner, Stakeholders).**  
- **Types of Priority**:  

| **Priority Level** | **Description** | **Example** |
|------------------|----------------|------------|
| **High** 🔴 | Must be fixed **immediately** as it blocks business operations. | Payment gateway failure. |
| **Medium** 🟡 | Important but **can wait** until the next release. | "Forgot Password" link not working. |
| **Low** 🟢 | Fix when time permits; not urgent. | Minor UI misalignment in footer. |

---

## **🔹 Difference Between Severity & Priority**
| **Aspect**       | **Severity** | **Priority** |
|-----------------|------------|-----------|
| **Definition**  | Impact of a defect on the system. | Urgency of fixing the defect. |
| **Who Decides?** | Testers (QA Team). | Business Team or Developers. |
| **Example** | App crash (High Severity, High Priority). | Typo in the footer (Low Severity, Low Priority). |

---

## **🔹 Real-World Examples of Severity vs. Priority**
| **Scenario** | **Severity** | **Priority** | **Reason** |
|-------------|------------|------------|------------|
| Payment gateway is broken. | **Critical** | **High** | Users can't make payments. Must be fixed ASAP. |
| Login page takes 10 seconds to load. | **High** | **Medium** | Impacts UX but doesn’t block users. |
| "Contact Us" form submission fails. | **Medium** | **Medium** | Non-critical but needs attention. |
| Misspelled company name in the footer. | **Low** | **Low** | Cosmetic issue, fix when possible. |

---

## **🔹 How to Set Severity & Priority in Bug Tracking Tools?**
In **Jira**, while logging a bug, you typically set:  
- **Severity**: Choose from **Blocker, Critical, Major, Minor, Trivial**.  
- **Priority**: Choose from **Highest, High, Medium, Low, Lowest**.  

---

## **🔹 Conclusion**
✔ **Severity = Impact on functionality** (Tester's perspective).  
✔ **Priority = Fixing urgency** (Business perspective).  
✔ Both help developers prioritize defect resolution.  

---
## **🔹 How to Log a Defect in Jira with Severity & Priority?** 🚀  

### **Step 1: Open Jira and Create a New Issue**  
1️⃣ Log in to **Jira** and navigate to your project.  
2️⃣ Click on **"Create"** (Top Menu).  
3️⃣ Select **Issue Type** → **Bug**.  

---

### **Step 2: Fill in the Bug Details**  

| **Field**          | **Example Entry** |
|--------------------|------------------|
| **Summary**       | "Payment Gateway Crashes on Checkout" |
| **Description**   | Steps to reproduce, expected & actual results. |
| **Severity**      | **Critical** (Application crash, no workaround) |
| **Priority**      | **High** (Business-critical issue, must be fixed ASAP) |
| **Reporter**      | Your Name |
| **Assignee**      | Developer responsible for fixing |
| **Environment**   | Browser (Chrome 120), OS (Windows 11) |
| **Attachments**   | Screenshots, Logs, Video, Console Errors |

---

### **Step 3: Example Jira Bug Report**  


**Summary**: Payment Gateway Crashes on Checkout  
**Environment**: Chrome 120, Windows 11  
**Steps to Reproduce**:  
1. Navigate to `https://example.com/cart`  
2. Add an item and proceed to checkout  
3. Enter valid payment details and click "Pay Now"  
4. Observe the application crash  

**Expected Result**: Payment should be processed successfully.  
**Actual Result**: Application crashes with a "500 Server Error".  

**Severity**: Critical (Blocks all users)  
**Priority**: High (Business-impacting issue)  

**Attachments**: (Add error screenshots, logs, console output)


---

### **Step 4: Submit & Track Bug**  
✅ Click **"Create"** and assign it to a developer.  
✅ Track progress under **"Bug Status"** (To Do → In Progress → Done).  
✅ Verify once fixed & update the status to **"Closed"**.  

---

### **🔹 Severity vs. Priority Examples in Jira**
| **Bug Description** | **Severity** | **Priority** |
|---------------------|------------|------------|
| Checkout button not working | High | High |
| "Contact Us" form not submitting | Medium | Medium |
| Logo misalignment on the homepage | Low | Low |
| Login page takes 10 seconds to load | High | Medium |

---

### **📌 Why is Logging Bugs in Jira Important?**
✔ Helps track and manage defects efficiently.  
✔ Prioritizes critical issues for quicker resolution.  
✔ Provides a **history of defects**, making regression testing easier.  

---
## **🔹 Automating Jira Issue Creation Using Selenium and Jira REST API** 🚀
Integrating Selenium test automation with Jira enables automatic logging of defects directly into Jira whenever a test fail. This seamless integration enhances the efficiency of the testing process by reducing manual intervention. Below is a step-by-step guide to achieve this integratio:

---

### **1. Prerequisites**

- **Jira Account** Ensure you have access to a Jira instance with the necessary permissions to create issue
- **Selenium Setup** A working Selenium environment for test automatio
- **Programming Language** This guide uses Java, but the concept can be adapted to other language

---

### **2. Generate Jira API Token**
To authenticate with the Jira REST API, you'll need an API toke

1. **Log in** to your Jira account.
2 Navigate to **Account Settings** > **Security** > **API Token*
3 Click on **"Create API token"**, provide a label, and **generate*
4 **Copy** and **store** the token securely; you'll need it for authenticatio

---

### **3. Add Jira REST API Client Dependency**
**In your Selenium project, include the Jira REST API client library to facilitate interactions with Jira. For Maven users, add the following dependency to your `pom.xml**
```xml
<dependency>
   <groupId>net.rcarz</groupId>
   <artifactId>jira-client</artifactId>
   <version>0.5</version>
</dependency>
```
**This library simplifies the process of making REST API calls to Jir.**

---

### **4. Implement Jira Integration in Selenium Tests**
**Within your Selenium test framework, create a utility class to handle Jira issue creation. Below is a sample implementatio**
```java
import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.Issue;

public class JiraIntegration {

    private static final String JIRA_URL = "https://your-domain.atlassian.net";
    private static final String JIRA_USERNAME = "your-email@example.com";
    private static final String JIRA_API_TOKEN = "your_api_token";
    private static final String JIRA_PROJECT_KEY = "PROJ";

    private JiraClient jira;

    public JiraIntegration() {
        BasicCredentials creds = new BasicCredentials(JIRA_USERNAME, JIRA_API_TOKEN);
        jira = new JiraClient(JIRA_URL, creds);
    }

    public void createJiraIssue(String summary, String description) {
        try {
            Issue.FluentCreate newIssue = jira.createIssue(JIRA_PROJECT_KEY, "Bug")
                                              .field("summary", summary)
                                              .field("description", description);
            Issue issue = newIssue.execute();
            System.out.println("Jira Issue Created: " + issue.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
**This utility uses the Jira REST API to create a new issue in the specified projec.**

---

### **5. Integrate with Selenium Test Cases**
**In your Selenium test cases, utilize the `JiraIntegration` class to log defects automatically upon test failure**
```java
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class SampleTest {

    private JiraIntegration jira = new JiraIntegration();

    @Test
    public void testExample() {
        try {
            // Selenium test steps
            // ...

            // Example assertion
            assertTrue("Verify page title", driver.getTitle().equals("Expected Title"));

        } catch (AssertionError e) {
            // Capture screenshot or any other necessary details
            String screenshotPath = captureScreenshot();

            // Create Jira issue
            String summary = "Test Failure: " + e.getMessage();
            String description = "Test failed at step XYZ. Screenshot: " + screenshotPath;
            jira.createJiraIssue(summary, description);

            // Re-throw to mark the test as failed
            throw e;
        }
    }

    @After
    public void tearDown() {
        // Cleanup code
        driver.quit();
    }

    private String captureScreenshot() {
        // Implement screenshot capture and return the file path
        return "/path/to/screenshot.png";
    }
}
```
**In this example, when an assertion fails, a Jira issue is automatically created with relevant detail.**

---

### **6. Verify Issue Creation in Jira**
**After executing the test, log in to your Jira dashboard to verify that the issue has been created successfully with the appropriate detail**

---

**References:**

- [Jira REST API Example - Create Issue](https://developer.atlassian.com/server/jira/platform/jira-rest-api-example-create-issue-7897248/)
- [How to integrate Jira with Selenium](https://www.browserstack.com/guide/how-to-integrate-jira-with-selenium)
- [Selenium-Jira-Integration GitHub Repository](https://github.com/iniyavans/Selenium-Jira-Integration)
*By following these steps, you can streamline your testing process, ensuring that defects are promptly and accurately reported in Jira, thereby enhancing overall productivity and collaboration within your development tea.* 

---
### **Example of High Priority & Low Severity Bug**  

**🔹 Definition:**  
- **High Priority** → Needs immediate attention because it affects business or user experience.  
- **Low Severity** → The issue does not impact core functionality or cause a system failure.  

---
## **🔹 XPath vs. CSS Selector in Selenium**  

XPath and CSS Selectors are both used to locate web elements in Selenium. However, they have key differences in **performance, syntax, and flexibility**.  

---

## **🔹 Quick Comparison Table**  

| **Feature**             | **XPath** ✅ | **CSS Selector** ✅ |
|-------------------------|-------------|-----------------|
| **Definition**          | XML path language used to navigate and locate elements in an XML/HTML document. | Uses CSS rules to select elements based on attributes, classes, and hierarchy. |
| **Performance** 🚀       | **Slower** (Traverses the entire DOM). | **Faster** (Direct access to elements). |
| **Syntax Complexity**   | **More complex**, allows advanced queries. | **Simpler & more readable**. |
| **Supports Parent Traversing?** | ✅ Yes (can move both forward & backward in DOM). | ❌ No (Only moves forward). |
| **Supports Text Matching?** | ✅ Yes (`contains()`, `text()`). | ❌ No (Does not support `text()` function). |
| **Supports Indexing?** | ✅ Yes (e.g., `(//div[@class='example'])[1]`). | ✅ Yes (`div.example:nth-of-type(1)`). |
| **Best for Dynamic Elements?** | ✅ Yes, more flexible (can use `contains()`, `starts-with()`). | ❌ Less flexible, requires exact match. |
| **Browser Compatibility** | ❌ Not fully supported in older versions of IE. | ✅ Works well in all modern browsers. |

---

## **🔹 Examples: XPath vs. CSS Selector**

### **✅ 1. Locate an Element by ID**
🔹 **XPath:**  
```xpath
//*[@id='login-button']
```
🔹 **CSS Selector:**  
```css
#login-button
```

---

### **✅ 2. Locate an Element by Class**
🔹 **XPath:**  
```xpath
//*[@class='submit-btn']
```
🔹 **CSS Selector:**  
```css
.submit-btn
```

---

### **✅ 3. Locate an Element by Tag & Attribute**
🔹 **XPath:**  
```xpath
//input[@type='text']
```
🔹 **CSS Selector:**  
```css
input[type='text']
```

---

### **✅ 4. Locate a Child Element**
🔹 **XPath:**  
```xpath
//div[@class='container']/button
```
🔹 **CSS Selector:**  
```css
.container > button
```

---

### **✅ 5. Locate Using `contains()` (Supported in XPath Only)**
🔹 **XPath:**  
```xpath
//button[contains(text(),'Submit')]
```
🔹 **CSS Selector:**  
❌ Not supported (must use exact text match).

---

### **✅ 6. Locate Using Index**
🔹 **XPath:**  
```xpath
(//input[@type='text'])[2]
```
🔹 **CSS Selector:**  
```css
input[type='text']:nth-of-type(2)
```

---

## **🔹 When to Use XPath vs. CSS Selector?**
| **Use Case** | **Recommended Selector** |
|-------------|---------------------|
| **Performance & Speed Matters** | ✅ **CSS Selector** (faster in browsers) |
| **Need to Traverse Backwards in DOM** | ✅ **XPath** |
| **Locating Dynamic Elements** | ✅ **XPath** (supports `contains()`, `starts-with()`) |
| **Simple, Readable Selectors** | ✅ **CSS Selector** |

---

## **🔹 Conclusion**
✔ **Use CSS Selector for faster and cleaner queries** when possible.  
✔ **Use XPath when you need advanced text matching or parent traversing**.  

---


### **📌 Real-World Example:**  

#### **Scenario:**  
The **company logo** on the homepage is **not loading** or **displaying incorrectly**.  

#### **Details:**  
- **Priority: High** 🚀 (First impressions matter; branding issues can affect user trust).  
- **Severity: Low** 🟢 (Does not impact the application's core functionality).  

#### **Impact:**  
- It affects **brand image and credibility**, especially if customers see a broken logo.  
- However, users **can still navigate and use all features** without any issue.  

#### **Expected Fix Timeline:**  
- Should be fixed **ASAP**, as it's a **visual and branding issue**.  

---

### **Other Examples of High Priority & Low Severity Bugs:**
| **Scenario** | **Priority** | **Severity** | **Reason** |
|-------------|-------------|-------------|------------|
| The company name is misspelled in the main navigation bar. | High | Low | Branding issue, but does not impact functionality. |
| A promotional banner on the homepage is broken. | High | Low | Business impact, but not affecting app functions. |
| A key contact number in the **"Support"** section is incorrect. | High | Low | Customers may not be able to reach support, but the app works fine. |

---

## **🔹 XPath vs. CSS Selector in Selenium**  

XPath and CSS Selectors are both used to locate web elements in Selenium. However, they have key differences in **performance, syntax, and flexibility**.  

---

## **🔹 Quick Comparison Table**  

| **Feature**             | **XPath** ✅ | **CSS Selector** ✅ |
|-------------------------|-------------|-----------------|
| **Definition**          | XML path language used to navigate and locate elements in an XML/HTML document. | Uses CSS rules to select elements based on attributes, classes, and hierarchy. |
| **Performance** 🚀       | **Slower** (Traverses the entire DOM). | **Faster** (Direct access to elements). |
| **Syntax Complexity**   | **More complex**, allows advanced queries. | **Simpler & more readable**. |
| **Supports Parent Traversing?** | ✅ Yes (can move both forward & backward in DOM). | ❌ No (Only moves forward). |
| **Supports Text Matching?** | ✅ Yes (`contains()`, `text()`). | ❌ No (Does not support `text()` function). |
| **Supports Indexing?** | ✅ Yes (e.g., `(//div[@class='example'])[1]`). | ✅ Yes (`div.example:nth-of-type(1)`). |
| **Best for Dynamic Elements?** | ✅ Yes, more flexible (can use `contains()`, `starts-with()`). | ❌ Less flexible, requires exact match. |
| **Browser Compatibility** | ❌ Not fully supported in older versions of IE. | ✅ Works well in all modern browsers. |

---

## **🔹 Examples: XPath vs. CSS Selector**

### **✅ 1. Locate an Element by ID**
🔹 **XPath:**  
```xpath
//*[@id='login-button']
```
🔹 **CSS Selector:**  
```css
#login-button
```

---

### **✅ 2. Locate an Element by Class**
🔹 **XPath:**  
```xpath
//*[@class='submit-btn']
```
🔹 **CSS Selector:**  
```css
.submit-btn
```

---

### **✅ 3. Locate an Element by Tag & Attribute**
🔹 **XPath:**  
```xpath
//input[@type='text']
```
🔹 **CSS Selector:**  
```css
input[type='text']
```

---

### **✅ 4. Locate a Child Element**
🔹 **XPath:**  
```xpath
//div[@class='container']/button
```
🔹 **CSS Selector:**  
```css
.container > button
```

---

### **✅ 5. Locate Using `contains()` (Supported in XPath Only)**
🔹 **XPath:**  
```xpath
//button[contains(text(),'Submit')]
```
🔹 **CSS Selector:**  
❌ Not supported (must use exact text match).

---

### **✅ 6. Locate Using Index**
🔹 **XPath:**  
```xpath
(//input[@type='text'])[2]
```
🔹 **CSS Selector:**  
```css
input[type='text']:nth-of-type(2)
```

---

## **🔹 When to Use XPath vs. CSS Selector?**
| **Use Case** | **Recommended Selector** |
|-------------|---------------------|
| **Performance & Speed Matters** | ✅ **CSS Selector** (faster in browsers) |
| **Need to Traverse Backwards in DOM** | ✅ **XPath** |
| **Locating Dynamic Elements** | ✅ **XPath** (supports `contains()`, `starts-with()`) |
| **Simple, Readable Selectors** | ✅ **CSS Selector** |

---

## **🔹 Conclusion**
✔ **Use CSS Selector for faster and cleaner queries** when possible.  
✔ **Use XPath when you need advanced text matching or parent traversing**.  

---
## **🔹 Different Parameters in Testing** 🚀  

**Testing parameters** define different aspects of a test case to ensure comprehensive test coverage. These parameters help in planning, executing, and managing tests effectively.  
---
 🔹 1. Functional Parameters
 🔹 2. Non-Functional Parameters
 🔹 3. Test Execution Parameters
 🔹 4. Defect Parameters
 🔹 5. Automation Parameters
 🔹 6. API Testing Parameters
 🔹 7. Database Testing Parameters

---

## **🔹 1. Functional Parameters** 🎯  
These parameters focus on the behavior of the application.  

| **Parameter** | **Description** | **Example** |
|--------------|----------------|-------------|
| **Input Data** | The test data required for execution. | Username & Password for login. |
| **Expected Output** | The expected result based on input. | Successful login message. |
| **Test Steps** | The sequence of actions performed in a test case. | Click "Login" after entering credentials. |
| **Business Rules** | Conditions that must be met for test validation. | Discount applied only if order > $100. |

---

## **🔹 2. Non-Functional Parameters** ⚙️  
These parameters check system performance, security, and usability.  

| **Parameter** | **Description** | **Example** |
|--------------|----------------|-------------|
| **Performance** | Checks speed, responsiveness, and stability. | Page should load within 2 seconds. |
| **Security** | Ensures data protection and access control. | Password should be encrypted. |
| **Usability** | Measures ease of use and user experience. | Buttons should be clearly labeled. |
| **Compatibility** | Tests across different browsers, OS, and devices. | Site should work on Chrome, Edge, and Firefox. |

---

## **🔹 3. Test Execution Parameters** 🏃‍♂️  
Define how tests are run and managed.  

| **Parameter** | **Description** | **Example** |
|--------------|----------------|-------------|
| **Test Environment** | The setup where tests are executed. | QA, UAT, Production. |
| **Test Data Source** | The location of input data. | Database, Excel, API. |
| **Test Execution Mode** | Defines how tests are run. | Manual, Automated, Parallel. |
| **Test Duration** | Time taken to execute a test case. | Login test should complete in 5 sec. |

---

## **🔹 4. Defect Parameters** 🐞  
Used to track and manage defects in a system.  

| **Parameter** | **Description** | **Example** |
|--------------|----------------|-------------|
| **Severity** | Impact of the defect on functionality. | High (Login not working). |
| **Priority** | Urgency of fixing the defect. | Low (UI misalignment). |
| **Reproducibility** | Whether the issue occurs consistently. | Always, Sometimes, Rare. |
| **Defect Status** | The current state of the defect. | New, Assigned, Fixed, Closed. |

---

## **🔹 5. Automation Parameters** 🤖  
For automated test execution.  

| **Parameter** | **Description** | **Example** |
|--------------|----------------|-------------|
| **Test Script Name** | The name of the automated test script. | `LoginTest.java` |
| **Browser/Platform** | Defines where automation is executed. | Chrome, Windows 11 |
| **Test Data Management** | Input data handling in automation. | CSV, JSON, Excel. |
| **Reporting & Logging** | Capturing test execution details. | Extent Reports, Allure. |

---

## **🔹 6. API Testing Parameters** 🌐  
For validating API responses and behavior.  

| **Parameter** | **Description** | **Example** |
|--------------|----------------|-------------|
| **Endpoint URL** | The API address to test. | `https://api.example.com/login` |
| **Request Method** | Type of API request. | GET, POST, PUT, DELETE |
| **Headers** | Extra data sent with the request. | Authorization: Bearer Token |
| **Response Code** | Expected HTTP status code. | 200 OK, 401 Unauthorized |
| **Response Time** | The time taken to get a response. | Should be < 2 sec. |

---

## **🔹 7. Database Testing Parameters** 🗄️  
For validating backend database operations.  

| **Parameter** | **Description** | **Example** |
|--------------|----------------|-------------|
| **Database Type** | The database used in the application. | Oracle, MySQL, PostgreSQL |
| **Query Execution** | SQL queries used for testing. | `SELECT * FROM users WHERE id=1;` |
| **Data Integrity** | Ensuring data consistency. | Order total should match item prices. |
| **ACID Compliance** | Checking atomicity, consistency, isolation, durability. | Data rollback on transaction failure. |

---

## **🔹 Conclusion**
✔ **Testing parameters ensure a structured and efficient testing process.**  
✔ **They help cover all aspects: functional, non-functional, automation, API, and database testing.**  
✔ **Choosing the right parameters improves test effectiveness and defect tracking.**  

---

# Top API Authentication Methods

| Authentication Protocol | Use Cases                              | Advantages                                      | When to Use                                      |
|-------------------------|----------------------------------------|------------------------------------------------|------------------------------------------------|
| OAuth                   | Securely authorize third-party access | Delegates authorization; no credentials shared | When integrating with third-party apps         |
| Bearer Token            | Stateless, time-bound access tokens   | Simplicity; good for single-use, short-lived   | For quick, stateless API access                |
| API Key                 | Static, long-lived secret keys        | Simplicity; for trusted parties                | Internal API access or trusted partners        |
| Basic Auth              | Username and password combo           | Simplicity; avoid for sensitive data access    | When user experience is not a priority        |
| JWT (JSON Web Token)    | Self-contained access tokens         | Scalability; holds user data; good for microservices | For scalable, stateless, secure access |

---
## **🔹 Different Types of Authentication in Software Testing** 🔐  

Authentication is the process of verifying a user's identity before granting access to an application or system. There are several types of authentication methods used based on security requirements.  

---

## **1️⃣ Basic Authentication (Username & Password)**
📌 **How it Works?**  
- The user enters a **username** and **password**.  
- Credentials are sent in the request **header (Base64 encoded)**.  

📌 **Example (REST API Request with Basic Auth in Postman)**  
```
Authorization: Basic Base64(username:password)
```
✅ **Pros:** Simple & easy to implement.  
❌ **Cons:** Less secure (credentials can be easily exposed if not encrypted).  

---

## **2️⃣ Token-Based Authentication (JWT, OAuth, API Keys)**
📌 **How it Works?**  
- The user logs in and gets a **token** (JWT, OAuth token, or API key).  
- The token is passed in subsequent requests for authentication.  

📌 **Example (JWT Authentication in REST API with Selenium & Rest Assured)**  
```java
RequestSpecification request = RestAssured.given();
request.header("Authorization", "Bearer your-jwt-token");
Response response = request.get("https://api.example.com/user");
```
✅ **Pros:** More secure, no need to send credentials with every request.  
❌ **Cons:** Token expiration handling is required.  

---

## **3️⃣ OAuth 2.0 Authentication (Used in Google, Facebook, GitHub)**
📌 **How it Works?**  
- User **logs in via a third-party provider** (Google, Facebook).  
- A temporary **OAuth token** is issued to access APIs.  

📌 **Example Flow:**  
1. User clicks "Login with Google".  
2. Google verifies and sends an **Access Token**.  
3. The application uses the token to access Google APIs.  

✅ **Pros:** Secure, no need to store passwords.  
❌ **Cons:** Requires third-party integration.  

---

## **4️⃣ Multi-Factor Authentication (MFA)**
📌 **How it Works?**  
- Requires **two or more authentication factors** (e.g., Password + OTP).  
- Used in **banking apps, enterprise security**.  

📌 **Example (Steps in Gmail Login with MFA):**  
1. Enter **username & password**.  
2. Receive an **OTP on phone/email**.  
3. Enter the OTP to log in.  

✅ **Pros:** Strong security.  
❌ **Cons:** Requires extra steps for users.  

---

## **5️⃣ Biometric Authentication (Fingerprint, Face ID, Retina Scan)**
📌 **How it Works?**  
- Uses **biometric data** (fingerprint, facial recognition) for login.  
- Used in **mobile banking, smartphones (Face ID, Touch ID)**.  

📌 **Example:**  
- Unlocking an iPhone with **Face ID**.  
- Logging into a banking app with a **fingerprint scanner**.  

✅ **Pros:** Convenient & secure.  
❌ **Cons:** Needs specialized hardware (fingerprint sensor, camera).  

---

## **6️⃣ SSO (Single Sign-On) Authentication**
📌 **How it Works?**  
- **One login** gives access to multiple applications.  
- Example: Logging into **Gmail also logs you into Google Drive, YouTube, and Google Docs**.  

📌 **Example (SSO with OAuth 2.0 & SAML):**  
- **Login via Google** allows access to multiple apps using the same credentials.  

✅ **Pros:** Reduces password fatigue.  
❌ **Cons:** If SSO is compromised, all linked accounts are vulnerable.  

---

## **7️⃣ LDAP Authentication (Lightweight Directory Access Protocol)**
📌 **How it Works?**  
- Used for authentication in **enterprise environments** (Active Directory, OpenLDAP).  
- **Example:** Logging into a **corporate system using company credentials**.  

✅ **Pros:** Centralized authentication for enterprises.  
❌ **Cons:** Complex setup, requires directory services.  

---

## **🔹 Summary Table: Types of Authentication**
| **Authentication Type** | **Example Use Case** | **Pros** | **Cons** |
|----------------------|----------------|---------|---------|
| **Basic Authentication** | REST APIs with username/password | Easy to implement | Less secure |
| **JWT Authentication** | Securing APIs with tokens | No credentials stored in requests | Requires token handling |
| **OAuth 2.0** | Google/Facebook login | Secure, widely used | Third-party dependency |
| **Multi-Factor Authentication (MFA)** | Online banking | Strong security | Extra steps for users |
| **Biometric Authentication** | Mobile banking, Face ID | Convenient, fast | Requires hardware |
| **Single Sign-On (SSO)** | Google Workspace (Gmail, Drive, YouTube) | One login for multiple apps | If hacked, all accounts at risk |
| **LDAP Authentication** | Corporate employee logins | Centralized access control | Requires directory services |

---

### **🔹 Which Authentication Should You Use?**
✅ **For REST APIs?** → **JWT or OAuth 2.0**  
✅ **For Web Applications?** → **SSO or Multi-Factor Authentication**  
✅ **For Enterprise Apps?** → **LDAP Authentication**  
✅ **For High-Security Apps?** → **Biometric Authentication**  

---



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
## **🔹 Running Test Cases in Parallel Execution (TestNG & Cucumber)** 🚀  

Parallel test execution allows multiple tests to run simultaneously, reducing execution time. Below are the different ways to achieve parallel execution in **TestNG** and **Cucumber with TestNG**.  

---

## **📌 1. Parallel Execution in TestNG**  

### **👉 Method 1: Parallel Execution via `testng.xml`**  
TestNG provides an inbuilt feature to run test cases in parallel by modifying the **`testng.xml`** file.  

### **🔹 Step 1: Create TestNG Test Classes**  
📌 Create two test classes **`Test1.java`** and **`Test2.java`**.

#### **📝 Test1.java**
```java
import org.testng.annotations.Test;

public class Test1 {
    @Test
    public void testMethod1() {
        System.out.println("Executing Test 1 - " + Thread.currentThread().getId());
    }
}
```

#### **📝 Test2.java**
```java
import org.testng.annotations.Test;

public class Test2 {
    @Test
    public void testMethod2() {
        System.out.println("Executing Test 2 - " + Thread.currentThread().getId());
    }
}
```

---

### **🔹 Step 2: Configure `testng.xml` for Parallel Execution**
Modify the **`testng.xml`** file to run tests in **parallel mode**.

```xml
<suite name="Parallel Execution Suite" parallel="classes" thread-count="2">
    <test name="Parallel Tests">
        <classes>
            <class name="Test1"/>
            <class name="Test2"/>
        </classes>
    </test>
</suite>
```

📌 **Explanation**:  
✔ `parallel="classes"` → Runs test classes in parallel.  
✔ `thread-count="2"` → Runs 2 test cases at the same time.  

---

### **🔹 Step 3: Run the `testng.xml`**
Run the tests using the command:  
```sh
mvn test -DsuiteXmlFile=testng.xml
```

✅ **Expected Output:**  
```
Executing Test 1 - 12  (Thread ID: 12)
Executing Test 2 - 13  (Thread ID: 13)
```
**(Both tests run simultaneously on different threads.)**  

---

## **📌 2. Parallel Execution in TestNG using `@DataProvider`**
Another way to run tests in parallel is by setting `parallel=true` in **`@DataProvider`**.

```java
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ParallelDataProviderTest {

    @DataProvider(name = "data", parallel = true)
    public Object[][] testData() {
        return new Object[][]{
                {"Data1"},
                {"Data2"}
        };
    }

    @Test(dataProvider = "data")
    public void testMethod(String data) {
        System.out.println("Executing test with data: " + data + " - Thread: " + Thread.currentThread().getId());
    }
}
```

✅ **This will execute tests in parallel using multiple data sets.**  

---

## **📌 3. Parallel Execution in Cucumber with TestNG**
If you're using **Cucumber with TestNG**, follow these steps.

### **🔹 Step 1: Configure Parallel Execution in `testng.xml`**
```xml
<suite name="Cucumber Parallel Execution" parallel="tests" thread-count="2">
    <test name="Cucumber Test Execution">
        <classes>
            <class name="runner.TestRunner"/>
        </classes>
    </test>
</suite>
```

---

### **🔹 Step 2: Modify the `TestRunner.java`**
Enable **parallel execution of Cucumber scenarios** using the `dataproviderthreadcount`.

```java
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = "stepDefinitions",
    plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
```

✅ This enables **parallel execution of Cucumber scenarios**.  

---

## **📌 4. Parallel Execution in Maven using `maven-surefire-plugin`**
You can also control parallel execution via Maven by configuring **`maven-surefire-plugin`** in `pom.xml`.

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.0.0-M7</version>
    <configuration>
        <parallel>methods</parallel>
        <threadCount>2</threadCount>
    </configuration>
</plugin>
```

✅ This will run **TestNG methods in parallel** when running `mvn test`.  

---

## **🔹 Summary Table: Parallel Execution Methods**
| **Method** | **Approach** | **Best For** |
|------------|-------------|--------------|
| **TestNG `testng.xml`** | `parallel="classes"` | Running multiple test classes in parallel |
| **TestNG `@DataProvider`** | `parallel=true` | Parallel execution with different data sets |
| **Cucumber `@DataProvider`** | `DataProvider(parallel = true)` | Running multiple Cucumber scenarios in parallel |
| **Maven `maven-surefire-plugin`** | `parallel=methods` | Running TestNG tests in parallel using Maven |

---

## **✅ Which One Should You Use?**
✔ **Using TestNG?** → Use `parallel="classes"` in `testng.xml`.  
✔ **Using Cucumber?** → Enable `DataProvider(parallel=true)`.  
✔ **Using Maven?** → Configure `maven-surefire-plugin`.  

---

## **🔹 Full CI/CD Setup with Jenkins for Parallel Test Execution** 🚀  

I'll guide you through setting up **Jenkins for CI/CD** to run your **Selenium, TestNG, and Cucumber tests in parallel**. The setup includes:  
✅ **Jenkins Installation & Plugins**  
✅ **Jenkins Job for Running Tests**  
✅ **Parallel Execution Setup**  
✅ **Integrating with GitHub & Maven**  
✅ **Jenkins Reporting (Allure, Extent Reports)**  

---

## **📌 1. Install Jenkins & Required Plugins**
If Jenkins is not installed, follow these steps:  

### **🔹 Step 1: Install Jenkins (Linux/Windows)**
Run the following command to install Jenkins on **Ubuntu**:  
```sh
sudo apt update && sudo apt install jenkins -y
```
For **Windows**, download it from [Jenkins Official Website](https://www.jenkins.io/download/).  

### **🔹 Step 2: Install Required Jenkins Plugins**
Go to **Jenkins Dashboard → Manage Jenkins → Manage Plugins → Available Plugins**, then install:  
✔ **Maven Integration Plugin**  
✔ **TestNG Results Plugin**  
✔ **Allure Plugin** (For Reports)  
✔ **Git Plugin** (For Source Code Management)  

---

## **📌 2. Configure Jenkins Job for Test Execution**
### **🔹 Step 1: Create a Jenkins Job**
1️⃣ Go to **Jenkins Dashboard** → Click on **New Item**  
2️⃣ Select **Freestyle Project** → Click **OK**  
3️⃣ Under **Source Code Management**, select **Git** and enter your repository URL.  

---

### **🔹 Step 2: Add Build Steps (Maven)**
1️⃣ Go to **Build → Add Build Step** → Select **Invoke Top-Level Maven Targets**  
2️⃣ Enter **Maven Goals** for parallel execution:  
```sh
clean test -DsuiteXmlFile=testng.xml
```
3️⃣ Click **Save** and **Apply**.  

---

## **📌 3. Setup Parallel Execution in Jenkins**
### **👉 Method 1: TestNG Parallel Execution (`testng.xml`)**
Modify your **`testng.xml`** to enable parallel execution:

```xml
<suite name="Parallel Suite" parallel="methods" thread-count="2">
    <test name="Parallel Test Execution">
        <classes>
            <class name="tests.Test1"/>
            <class name="tests.Test2"/>
        </classes>
    </test>
</suite>
```
📌 This will execute test cases in **two threads simultaneously**.

---

### **👉 Method 2: Maven Parallel Execution (`pom.xml`)**
Modify the **`maven-surefire-plugin`** to run tests in parallel:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.0.0-M7</version>
    <configuration>
        <parallel>methods</parallel>
        <threadCount>2</threadCount>
    </configuration>
</plugin>
```

📌 Running `mvn test` will execute TestNG tests in parallel.

---

## **📌 4. Run Jenkins Job**
1️⃣ **Go to Jenkins Dashboard → Select your Job**  
2️⃣ Click **Build Now**  
3️⃣ Check **Console Output** for test execution logs.  

✅ **If successful, you will see test execution happening in parallel.**  

---

## **📌 5. Generate and View Reports in Jenkins**
### **🔹 Step 1: Add Allure Reporting in `pom.xml`**
```xml
<plugin>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-maven</artifactId>
    <version>2.10.0</version>
    <executions>
        <execution>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### **🔹 Step 2: Configure Jenkins for Allure Reports**
1️⃣ Install **Allure Plugin** in Jenkins  
2️⃣ In Jenkins Job → **Post-Build Actions** → Select **Allure Report**  
3️⃣ Enter **Report Path**: `target/allure-results`  
4️⃣ Click **Save and Apply**  

After running the job, **Allure Reports** will be available in **Jenkins Dashboard**.

---

## **📌 6. Run Tests on Jenkins Using GitHub Webhook**
1️⃣ **Go to GitHub Repository → Settings → Webhooks**  
2️⃣ Add Webhook: `http://your-jenkins-url/github-webhook/`  
3️⃣ Select **Just the Push Event** and Save  
4️⃣ Now, whenever you push code, Jenkins will automatically trigger tests!  

---

## **🎯 Final CI/CD Pipeline Flow**
✅ Developer **pushes code** to GitHub  
✅ Jenkins **fetches the latest code**  
✅ **Parallel execution** of TestNG tests  
✅ **Test reports** (Allure, Extent) generated  
✅ Test results **published in Jenkins**  

---

# **🔹 Defect Life Cycle (Bug Life Cycle) in Software Testing** 🚀  

## **📌 What is Defect Life Cycle?**  
The **Defect Life Cycle** (or **Bug Life Cycle**) is the process that a defect goes through from its **identification to closure**. It ensures that all defects are tracked, fixed, and verified properly before release.  

---

## **📌 Defect Life Cycle Stages**
Below are the different stages in the defect life cycle:  

### **🔹 1. New** 🆕  
- When a tester finds a defect, it is reported as **"New"** in the defect tracking tool (e.g., JIRA, Bugzilla).  

### **🔹 2. Assigned** 🎯  
- The defect is assigned to the **developer/team lead** for fixing.  
- The status changes to **"Assigned"**.  

### **🔹 3. Open** 🔍  
- The developer starts analyzing the defect.  
- If valid, it is fixed and moved to the **"Fixed"** state.  
- If **invalid**, it may be marked as **Rejected / Not a Bug**.  

### **🔹 4. Rejected** ❌ (Optional)  
- If the defect is **not valid**, the developer rejects it.  
- Possible reasons:  
  ✅ Works as Expected  
  ✅ Duplicate Defect  
  ✅ Environment Issue  

### **🔹 5. Deferred** 🕒 (Optional)  
- If a defect is **not critical** or can be fixed in the next release, it is **Deferred**.  

### **🔹 6. Fixed** 🛠  
- The developer fixes the defect and marks it as **"Fixed"**.  

### **🔹 7. Retest** 🔄  
- The tester verifies the fix in a new build.  
- If working fine, it moves to **"Verified"**.  
- If not, it moves back to **"Reopened"**.  

### **🔹 8. Reopened** 🔁  
- If the defect is **not fixed properly**, it is **Reopened** and sent back to the developer.  

### **🔹 9. Verified** ✅  
- If the tester confirms the fix, it is **Verified**.  

### **🔹 10. Closed** 🚀  
- If the defect is **fixed, verified, and working fine**, it is marked as **"Closed"**.  

---

## **📌 Defect Life Cycle Flow Diagram**  
```
New → Assigned → Open → Fixed → Retest → Verified → Closed
                          ↳ (Reopened) → Fixed
                ↳ (Rejected / Deferred)
```

---

## **📌 Example of Defect Life Cycle in JIRA**  
🔹 **Scenario:** Login button not working  
1️⃣ Tester reports a defect as **"New"** in JIRA.  
2️⃣ Project Manager **assigns** the defect to the developer.  
3️⃣ Developer checks and marks it as **"Open"**.  
4️⃣ If the bug is valid, the developer fixes it and sets it to **"Fixed"**.  
5️⃣ Tester **retests** the defect in a new build.  
6️⃣ If the issue is fixed, the status changes to **"Verified"** → **"Closed"**.  
7️⃣ If the issue is still present, the tester marks it as **"Reopened"**.  

---

## **📌 Key Points About Defect Life Cycle**  
✔ Defect tracking tools used: **JIRA, Bugzilla, HP ALM, etc.**  
✔ Status flow depends on **company policy** and **project requirements**.  
✔ High-priority defects are **fixed immediately**, while low-priority ones may be **Deferred**.  

---

## **🔹 Selenium WebDriver - Overview & Explanation** 🚀  

### **📌 What is Selenium WebDriver?**  
Selenium WebDriver is an **automation testing tool** that allows you to control web browsers **programmatically** using different programming languages like **Java, Python, C#, etc.**.  

It is a part of the **Selenium suite** and helps testers **execute test cases on real browsers** (like Chrome, Firefox, Edge) without requiring a manual UI interaction.  

---

## **📌 Key Features of Selenium WebDriver**  
✔ **Cross-Browser Testing** – Supports Chrome, Firefox, Edge, Safari, etc.  
✔ **Programming Language Support** – Java, Python, C#, JavaScript, etc.  
✔ **Headless Testing** – Run tests without opening a UI browser.  
✔ **Integration with Frameworks** – TestNG, JUnit, Cucumber, etc.  
✔ **Parallel Execution** – Run multiple tests at the same time.  

---

## **📌 Architecture of Selenium WebDriver**  
Selenium WebDriver interacts with web browsers using the **Browser Driver**, which acts as a bridge between test scripts and the browser.  

### **🔹 Selenium WebDriver Components:**
1️⃣ **Test Script** – Your test code written in Java, Python, etc.  
2️⃣ **WebDriver API** – Commands that communicate with the browser.  
3️⃣ **Browser Driver** – ChromeDriver, GeckoDriver (Firefox), EdgeDriver, etc.  
4️⃣ **Browser** – Chrome, Firefox, Edge, etc.  

🖥 **Flow:**  
```
Test Script → WebDriver API → Browser Driver → Browser → Web Page Interaction
```

---

## **📌 Example: Basic Selenium WebDriver Script in Java**  

```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {
    public static void main(String[] args) {
        // Set up ChromeDriver
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        
        // Initialize WebDriver
        WebDriver driver = new ChromeDriver();
        
        // Open a website
        driver.get("https://www.google.com");
        
        // Print title
        System.out.println("Page Title: " + driver.getTitle());
        
        // Close the browser
        driver.quit();
    }
}
```
✅ This script launches Chrome, opens **Google**, prints the title, and closes the browser.

---

## **📌 Selenium WebDriver Commands**
### **🔹 1. Browser Commands**
```java
driver.get("https://example.com");  // Open a website
driver.getTitle();                  // Get page title
driver.getCurrentUrl();              // Get current URL
driver.quit();                       // Close browser completely
driver.close();                       // Close current tab
```

### **🔹 2. Element Interaction**
```java
driver.findElement(By.id("username")).sendKeys("TestUser"); // Enter text
driver.findElement(By.name("password")).sendKeys("password");
driver.findElement(By.xpath("//button[@id='login']")).click(); // Click a button
```

### **🔹 3. Handling Alerts, Frames, Windows**
```java
driver.switchTo().alert().accept(); // Handle alert pop-up
driver.switchTo().frame("frameName"); // Switch to an iframe
driver.switchTo().window("windowHandle"); // Switch between windows/tabs
```

---

## **📌 Advantages of Selenium WebDriver**  
✔ **Free & Open Source** – No licensing cost  
✔ **Supports Multiple Browsers & Platforms**  
✔ **Easy Integration with CI/CD Tools (Jenkins, GitHub, etc.)**  
✔ **Flexible – Supports Web UI Automation & Headless Execution**  

---
## **🔹 Is WebDriver an Interface?** ✅  

Yes, **WebDriver is an interface** in Selenium.  

---

## **📌 WebDriver as an Interface in Selenium**
In **Selenium WebDriver**, `WebDriver` is an interface that provides a blueprint for browser automation. It is implemented by **browser-specific classes** like `ChromeDriver`, `FirefoxDriver`, `EdgeDriver`, etc.  

### **🔹 WebDriver Interface Definition (Selenium)**
```java
public interface WebDriver {
    void get(String url);
    String getTitle();
    String getCurrentUrl();
    void close();
    void quit();
    // Other abstract methods...
}
```
📌 **As an interface, WebDriver cannot be instantiated directly.** Instead, we create an object of a browser driver class that implements `WebDriver`.  

---

## **📌 Example: WebDriver Interface Implementation**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverExample {
    public static void main(String[] args) {
        // WebDriver is an interface; ChromeDriver implements it.
        WebDriver driver = new ChromeDriver();

        // Using WebDriver methods
        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());

        driver.quit();
    }
}
```
✅ Here, `ChromeDriver` implements the `WebDriver` interface.  

---

## **📌 Browser Drivers That Implement WebDriver**
| Browser Driver Class  | Implements WebDriver? |
|----------------------|--------------------|
| `ChromeDriver`      | ✅ Yes |
| `FirefoxDriver`     | ✅ Yes |
| `EdgeDriver`        | ✅ Yes |
| `SafariDriver`      | ✅ Yes |
| `RemoteWebDriver`   | ✅ Yes |

📌 **`RemoteWebDriver`** is a common implementation that allows running tests on **Selenium Grid**.

---

## **📌 Key Benefits of Using WebDriver as an Interface**
✔ **Flexibility** – Easily switch between browser drivers (`ChromeDriver`, `FirefoxDriver`, etc.).  
✔ **Loose Coupling** – No dependency on specific browsers, ensuring maintainability.  
✔ **Cross-Browser Testing** – Run tests across different browsers with minimal changes.  

---

# **🔹 Selenium – Overview & Components** 🚀  

## **📌 What is Selenium?**  
Selenium is a **popular open-source automation testing tool** for **web applications**. It allows testers and developers to automate web browser interactions across different browsers and platforms using multiple programming languages like **Java, Python, C#, etc.**  

📌 **Key Features:**  
✔ **Cross-Browser Support** – Chrome, Firefox, Edge, Safari, etc.  
✔ **Multi-Language Support** – Java, Python, C#, JavaScript, etc.  
✔ **Parallel Execution** – Faster test execution using Selenium Grid  
✔ **Integration with CI/CD Tools** – Jenkins, GitHub, Docker, etc.  

---

## **📌 Selenium Components**  
Selenium is not a single tool but a suite of **four major components**:  

| **Component**        | **Description** |
|----------------------|----------------|
| **Selenium WebDriver** | Automates web browser interactions. |
| **Selenium IDE** | A record-and-playback tool for creating test cases. |
| **Selenium Grid** | Runs tests in parallel on multiple machines. |
| **Selenium RC (Deprecated)** | Older version, replaced by WebDriver. |

---

## **📌 1. Selenium WebDriver (Most Used)**
🔹 **What is it?**  
Selenium WebDriver is an **API that automates web browsers** by sending commands to interact with elements like buttons, text fields, dropdowns, etc.  

🔹 **Example in Java**  
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());
        driver.quit();
    }
}
```
✅ Supports multiple browsers like **ChromeDriver, FirefoxDriver, EdgeDriver, etc.**  

---

## **📌 2. Selenium IDE (Integrated Development Environment)**
🔹 **What is it?**  
- A **record-and-playback tool** available as a **Chrome & Firefox extension**.  
- Best for **beginners & quick automation** but **not suitable for complex test cases**.  

🔹 **Features:**  
✔ No programming skills required  
✔ Can export test cases to Selenium WebDriver scripts  
✔ Supports basic debugging  

---

## **📌 3. Selenium Grid (For Parallel Execution)**
🔹 **What is it?**  
Selenium Grid allows executing **multiple test cases on different machines & browsers in parallel**, reducing execution time.  

🔹 **Key Features:**  
✔ Runs tests on **remote machines**  
✔ Supports **cross-browser testing**  
✔ Works with **Selenium WebDriver**  

🔹 **Example:** Running tests on multiple browsers using **Hub-Node architecture**.  

---

## **📌 4. Selenium RC (Deprecated)**
🔹 **What is it?**  
- Older version of Selenium before WebDriver.  
- Required **Selenium Server** to interact with browsers.  
- **Replaced by Selenium WebDriver** for better performance.  

---

## **📌 Selenium Suite Comparison**  

| **Feature** | **Selenium WebDriver** | **Selenium IDE** | **Selenium Grid** |
|------------|-----------------|------------|------------|
| Programming Knowledge Required | ✅ Yes | ❌ No | ✅ Yes |
| Cross-Browser Testing | ✅ Yes | ❌ No | ✅ Yes |
| Parallel Execution | ❌ No (alone) | ❌ No | ✅ Yes |
| Remote Execution | ✅ Yes | ❌ No | ✅ Yes |
| Best Use Case | Full automation | Quick testing | Distributed execution |

---

 
### **🔹 Selenium RC vs. Selenium WebDriver - Key Differences**  

| Feature | **Selenium RC (Deprecated)** ❌ | **Selenium WebDriver (Current)** ✅ |
|---------|--------------------------------|----------------------------------|
| **Architecture** | Requires **Selenium Server** to run tests. | Directly interacts with browsers (No server needed). |
| **Execution Speed** | Slower due to server communication. | Faster since it directly controls the browser. |
| **Browser Support** | Supports older browsers but outdated. | Supports modern browsers (Chrome, Firefox, Edge, etc.). |
| **Element Handling** | Uses JavaScript injection (complex). | Uses native browser automation (simpler & reliable). |
| **Alert, Frames, Popups Handling** | Requires workarounds. | Direct support for alerts, popups, frames, etc. |
| **Headless Execution** | ❌ No support. | ✅ Supports headless mode. |
| **Mobile Testing** | ❌ Not supported. | ✅ Works with **Appium** for mobile automation. |
| **Parallel Execution** | Limited support. | Works with **Selenium Grid** for parallel execution. |
| **Deprecation** | **No longer used.** | Actively maintained & preferred. |

📌 **Conclusion:** **Selenium WebDriver** is **faster, more reliable, and supports modern automation**. Selenium RC is **obsolete** and no longer used.  

✅ **For interviews, just remember:**  
- **RC is slow (needs a server); WebDriver is fast (direct control).**  
- **WebDriver supports alerts, popups, headless mode, and mobile automation, unlike RC.**  
- **RC is outdated and replaced by WebDriver.** 🚀
---
# **🔹 Difference Between Selenium RC and Selenium WebDriver** 🚀 

Selenium **RC (Remote Control)** and **WebDriver** are both used for web automation, but WebDriver is the improved version that replaced RC due to its limitations.  

---

## **📌 Key Differences Between Selenium RC and WebDriver**  

| Feature | **Selenium RC (Remote Control)** ❌ *(Deprecated)* | **Selenium WebDriver** ✅ *(Current & Recommended)* |
|---------|--------------------------------------------------|----------------------------------------------|
| **Architecture** | Uses a **Selenium Server** to interact with the browser. | Directly interacts with the browser without an intermediate server. |
| **Execution Speed** | Slower due to server communication overhead. | Faster since it directly communicates with the browser. |
| **Browser Support** | Supports older browsers but with limitations. | Supports modern browsers (Chrome, Firefox, Edge, etc.). |
| **Installation** | Requires **Selenium Server** to be running before execution. | No server required—only browser drivers (e.g., ChromeDriver). |
| **API Simplicity** | Uses complex **JavaScript injection** for execution. | Uses native **browser automation APIs** (simpler & more reliable). |
| **Handling Alerts, Popups, Frames** | Requires complex workarounds. | Supports direct handling of alerts, frames, and popups. |
| **Headless Browser Support** | No support for headless execution. | Supports **headless testing** in Chrome, Firefox, etc. |
| **Mobile Automation Support** | No mobile support. | Supports mobile testing with **Appium**. |
| **Parallel Execution** | Limited parallel execution. | Works with **Selenium Grid** for parallel execution. |
| **Flexibility** | Requires **Selenium Core** to interact with elements. | More flexible and powerful element interaction. |
| **Deprecation** | **Officially deprecated & no longer used.** | Actively maintained and used in **modern automation frameworks**. |

---

## **📌 Example Comparison**  

### **🔹 Selenium RC Example (Old Approach)**
```java
import com.thoughtworks.selenium.DefaultSelenium;

public class SeleniumRCTest {
    public static void main(String[] args) {
        DefaultSelenium selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://www.google.com");
        selenium.start();
        selenium.open("/");
        System.out.println("Title: " + selenium.getTitle());
        selenium.stop();
    }
}
```
✅ **Disadvantages:**  
- Needs **Selenium Server** running on port `4444`.  
- **Slow execution** due to extra communication.  

---

### **🔹 Selenium WebDriver Example (Modern Approach)**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumWebDriverTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());
        driver.quit();
    }
}
```
✅ **Advantages:**  
- **No server required** → Direct browser interaction.  
- **Faster execution** and **better element handling**.  

---

## **📌 Why Selenium WebDriver is Preferred?**
✔ **Faster execution** → No intermediate Selenium server.  
✔ **Better browser compatibility** → Works with **modern browsers**.  
✔ **More powerful API** → Supports alerts, frames, popups, and JavaScript execution.  
✔ **Supports Mobile Testing** → Works with **Appium** for Android & iOS testing.  
✔ **Headless Browser Support** → Works in **Chrome, Firefox headless mode**.  

---
## **🔹 Different Types of Locators in Selenium** 🚀  

Locators in Selenium are used to find and interact with **web elements** (buttons, text fields, links, etc.) on a webpage.  

### **📌 1. ID Locator** (Fastest & Most Preferred ✅)  
- Finds elements using their **unique `id` attribute**.  
- **Syntax:**  
  ```java
  driver.findElement(By.id("username")).sendKeys("TestUser");
  ```
- **Example:**  
  ```html
  <input id="username" type="text">
  ```

---

### **📌 2. Name Locator**  
- Locates elements using the `name` attribute.  
- **Syntax:**  
  ```java
  driver.findElement(By.name("password")).sendKeys("Secret123");
  ```
- **Example:**  
  ```html
  <input name="password" type="password">
  ```

---

### **📌 3. Class Name Locator**  
- Finds elements using the **CSS class name** (not unique).  
- **Syntax:**  
  ```java
  driver.findElement(By.className("login-btn")).click();
  ```
- **Example:**  
  ```html
  <button class="login-btn">Login</button>
  ```

---

### **📌 4. Tag Name Locator**  
- Locates elements using HTML **tag names** (e.g., `input`, `button`, `a`).  
- **Syntax:**  
  ```java
  driver.findElement(By.tagName("h1")).getText();
  ```
- **Example:**  
  ```html
  <h1>Welcome</h1>
  ```

---

### **📌 5. Link Text Locator**  
- Finds links (`<a>` elements) using the **exact link text**.  
- **Syntax:**  
  ```java
  driver.findElement(By.linkText("Click Here")).click();
  ```
- **Example:**  
  ```html
  <a href="https://example.com">Click Here</a>
  ```

---

### **📌 6. Partial Link Text Locator**  
- Finds links using a **partial match** of the link text.  
- **Syntax:**  
  ```java
  driver.findElement(By.partialLinkText("Click")).click();
  ```
- **Example:**  
  ```html
  <a href="https://example.com">Click Here</a>
  ```

---

### **📌 7. XPath Locator** (Powerful but slower than CSS Selector)  
- Used to locate elements using **XPath expressions**.  
- **Syntax:**  
  ```java
  driver.findElement(By.xpath("//input[@id='email']")).sendKeys("test@example.com");
  ```
- **Types of XPath:**  
  ✅ **Absolute XPath** (Not recommended ❌):  
  ```java
  driver.findElement(By.xpath("/html/body/div/input")).click();
  ```
  ✅ **Relative XPath** (Recommended ✅):  
  ```java
  driver.findElement(By.xpath("//input[@id='email']")).click();
  ```

---

### **📌 8. CSS Selector** (Faster than XPath ✅)  
- Finds elements using **CSS selectors**.  
- **Syntax:**  
  ```java
  driver.findElement(By.cssSelector("input#email")).sendKeys("test@example.com");
  ```
- **Examples:**  
  ```css
  tagname[attribute='value']     → input[id='email']
  tagname.classname              → button.login-btn
  tagname#id                     → input#email
  ```

---

## **📌 Summary Table: Selenium Locators**  

| **Locator**        | **Syntax Example** | **Speed & Performance** |
|-------------------|----------------|------------------|
| **ID** ✅ | `By.id("username")` | **Fastest** ✅ |
| **Name** | `By.name("password")` | Fast |
| **Class Name** | `By.className("login-btn")` | Fast |
| **Tag Name** | `By.tagName("h1")` | Medium |
| **Link Text** | `By.linkText("Click Here")` | Medium |
| **Partial Link Text** | `By.partialLinkText("Click")` | Medium |
| **XPath** | `By.xpath("//input[@id='email']")` | **Slower** ❌ |
| **CSS Selector** ✅ | `By.cssSelector("input#email")` | **Faster than XPath** ✅ |

---

## **🎯 Best Practices for Locators**  
✔ **Prefer `ID` and `Name` locators** (Fastest & reliable).  
✔ Use **CSS Selectors instead of XPath** (Better performance).  
✔ **Avoid Absolute XPath** (Breaks easily with UI changes).  
✔ Use **Relative XPath & CSS Selectors** for dynamic elements.  

---
## **🔹 What is XPath in Selenium?** 🚀  

XPath (**XML Path Language**) is a **locator technique** in Selenium used to find elements in an **HTML document**. It allows testers to **navigate through the HTML structure** using nodes and attributes.  

---

## **📌 How Does XPath Work in Selenium?**
XPath works by identifying elements based on their **HTML structure and attributes**. It allows for complex element identification, even when no `id` or `name` attributes are available.  

🔹 **Example:** Consider this HTML code:
```html
<input type="text" id="username" name="user" class="input-field">
```
Using XPath, you can locate this element as follows:  
```java
driver.findElement(By.xpath("//input[@id='username']")).sendKeys("TestUser");
```
✅ This finds the `<input>` element where **`id="username"`**.

---

## **📌 Types of XPath in Selenium**  

### **1️⃣ Absolute XPath (Not Recommended ❌)**
- Directly navigates the full path from the root element (`html`).
- **Breaks easily** if the structure changes.

🔹 **Example:**  
```java
driver.findElement(By.xpath("/html/body/div/input")).sendKeys("TestUser");
```
❌ **Problem:** If any node changes, the XPath **fails**.

---

### **2️⃣ Relative XPath (Recommended ✅)**
- Starts directly from the desired element (no need to define full path).
- **More flexible** and less likely to break.

🔹 **Example:**  
```java
driver.findElement(By.xpath("//input[@id='username']")).sendKeys("TestUser");
```
✅ **Advantages:**  
✔ Shorter and easier to maintain  
✔ Works even if the page structure changes  

---

## **📌 XPath Functions in Selenium**  

### **1️⃣ Basic XPath Syntax**
| **XPath Expression** | **Description** | **Example** |
|---------------------|----------------|------------|
| `//tagname` | Selects all elements with that tag. | `//input` (Selects all `<input>` elements) |
| `//tagname[@attribute='value']` | Selects element with specific attribute value. | `//input[@id='username']` |
| `//tagname[text()='text']` | Finds elements by text content. | `//button[text()='Login']` |

---

### **2️⃣ Advanced XPath Functions**
| **XPath Function** | **Description** | **Example** |
|------------------|----------------|------------|
| `contains()` | Finds partial text or attribute value. | `//input[contains(@placeholder, 'Enter')]` |
| `starts-with()` | Finds elements where attribute starts with a value. | `//input[starts-with(@name, 'user')]` |
| `ends-with()` | Finds elements where attribute ends with a value. | `//input[ends-with(@name, 'email')]` (Not supported in all browsers) |
| `normalize-space()` | Removes extra spaces from text. | `//label[normalize-space(text())='Username']` |

---

### **3️⃣ XPath Axes (Parent, Child, Sibling, Ancestor, Descendant)**
XPath **axes** help navigate between related elements.

| **XPath Axis** | **Description** | **Example** |
|--------------|----------------|------------|
| `parent::` | Selects the parent element. | `//button[@id='login']/parent::div` |
| `child::` | Selects direct child elements. | `//div[@class='container']/child::input` |
| `ancestor::` | Selects all ancestors (grandparents, etc.). | `//input[@id='search']/ancestor::form` |
| `descendant::` | Selects all nested elements. | `//div[@id='menu']/descendant::a` |
| `following-sibling::` | Selects the next sibling element. | `//h2[@id='title']/following-sibling::p` |
| `preceding-sibling::` | Selects the previous sibling element. | `//td[text()='John']/preceding-sibling::td` |

---

## **📌 Example: Using XPath in Selenium (Java)**
```java
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class XPathExample {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");

        // Find element using XPath
        WebElement username = driver.findElement(By.xpath("//input[@id='username']"));
        username.sendKeys("TestUser");

        // Click login button
        driver.findElement(By.xpath("//button[text()='Login']")).click();

        driver.quit();
    }
}
```
✅ **This script:**  
✔ Opens a webpage  
✔ Finds elements using **XPath**  
✔ Sends input & clicks a button  

---

## **📌 When to Use XPath in Selenium?**
✅ **Use XPath when:**  
- Elements **don’t have unique IDs or names**.  
- You need to locate **dynamic elements** (e.g., changing attributes).  
- You need to **traverse the DOM** (parent, child, sibling elements).  

❌ **Avoid XPath if:**  
- The page structure changes frequently → Use **CSS Selectors** instead.  
- Performance is critical → **CSS Selectors are faster**.  

---

## **📌 XPath vs. CSS Selector – Which One is Better?**  

| Feature | **XPath** | **CSS Selector** |
|---------|----------|-----------------|
| **Performance** | Slower ❌ | Faster ✅ |
| **Readability** | Complex ❌ | Simple ✅ |
| **Parent Traversal** | ✅ Yes | ❌ No |
| **Text Matching** | ✅ Yes (`text()`, `contains()`) | ❌ No |

📌 **Use CSS Selectors for speed** and **XPath for flexibility**.  

---
# **🔹 Difference Between Absolute XPath and Relative XPath** 🚀  

XPath is used to locate elements in Selenium, and it comes in two types:  

| **Feature**        | **Absolute XPath ❌ (Not Recommended)** | **Relative XPath ✅ (Recommended)** |
|--------------------|--------------------------------|--------------------------------|
| **Definition**     | Starts from the **root element (`/html`)** and follows a direct path. | Starts **from any element** in the DOM, making it more flexible. |
| **Syntax**         | Begins with a **single slash (`/`)**. | Begins with a **double slash (`//`)**. |
| **Example**        | ```/html/body/div/input``` | ```//input[@id='username']``` |
| **Dependency on Structure** | **Highly dependent** on DOM structure (breaks easily). | **Less dependent**, works even if the structure changes. |
| **Readability**    | Hard to read and maintain. | Shorter and **easier to understand**. |
| **Performance**    | Slower (traverses full DOM). | Faster (directly locates elements). |
| **Use Case**       | Only when no other locators work. | **Preferred** for stable and dynamic elements. |

---

## **📌 Examples**
### **🔹 1. Absolute XPath (Not Recommended ❌)**
```java
driver.findElement(By.xpath("/html/body/div[1]/div[2]/form/input")).sendKeys("TestUser");
```
📌 **Problem:** If any element in the path changes, this XPath **breaks**.

---

### **🔹 2. Relative XPath (Recommended ✅)**
```java
driver.findElement(By.xpath("//input[@id='username']")).sendKeys("TestUser");
```
✅ Works even if other elements in the DOM change.  

---

## **📌 When to Use?**
✔ **Use Relative XPath** → More stable, flexible, and reliable.  
❌ **Avoid Absolute XPath** → Breaks easily with UI changes.  

---

# **🔹 Difference Between `findElement()` and `findElements()` in Selenium** 🚀  

Both `findElement()` and `findElements()` are used to locate web elements in Selenium, but they have key differences.

---

## **📌 Key Differences**
| Feature | **`findElement()`** | **`findElements()`** |
|---------|----------------|------------------|
| **Return Type** | Returns a **single WebElement**. | Returns a **List of WebElements**. |
| **Use Case** | Used when you need **only one element**. | Used when you need **multiple elements** (like a list of links, buttons, etc.). |
| **Behavior if Element Not Found** | Throws **`NoSuchElementException`**. | Returns an **empty list (`size() == 0`)** (No Exception). |
| **Example Use** | Find **one login button**. | Find **all links on a page**. |

---

## **📌 Example Usage in Java**
### **🔹 `findElement()` – Finds the First Matching Element**
```java
WebElement loginBtn = driver.findElement(By.id("login"));
loginBtn.click(); // Clicks the login button
```
✅ **If multiple elements match, it selects the **first** one.  
❌ **Throws `NoSuchElementException` if the element is not found.**

---

### **🔹 `findElements()` – Finds Multiple Elements**
```java
List<WebElement> links = driver.findElements(By.tagName("a"));
System.out.println("Total links: " + links.size());
```
✅ **Returns a list of matching elements**.  
❌ **If no elements are found, it returns an empty list (no exception).**

---

## **📌 When to Use?**
✔ **Use `findElement()`** → When locating a **single, unique element** (e.g., Login Button, Search Box).  
✔ **Use `findElements()`** → When dealing with **multiple elements** (e.g., All links, Multiple checkboxes).  

---

# **🔹 Handling Pop-ups and Alerts in Selenium** 🚀  

In Selenium, pop-ups and alerts can be handled using **`Alert` interface, Window Handles, and JavaScript Executor**.  

---

## **📌 1. Handling JavaScript Alerts (Simple, Confirmation, Prompt)**
### **🔹 Step 1: Switch to Alert**
Selenium provides the **`Alert` interface** to handle JavaScript pop-ups.

```java
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AlertHandling {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com/alert");

        // Switch to Alert
        Alert alert = driver.switchTo().alert();

        // Accept Alert (OK Button)
        alert.accept();
        
        driver.quit();
    }
}
```
✅ **`accept()`** – Clicks the **OK** button.  

---

## **📌 2. Handling Confirmation Alert (OK & Cancel)**
```java
Alert confirmAlert = driver.switchTo().alert();

// Click Cancel Button
confirmAlert.dismiss();
```
✅ **`dismiss()`** – Clicks the **Cancel** button.  

---

## **📌 3. Handling Prompt Alert (Input Box in Alert)**
```java
Alert promptAlert = driver.switchTo().alert();

// Enter text into the prompt box
promptAlert.sendKeys("Test Input");

// Click OK
promptAlert.accept();
```
✅ **`sendKeys("text")`** – Inputs text into the alert box.  

---

## **📌 4. Handling Browser Pop-ups (Authentication Pop-ups)**
If a pop-up requires a **username & password**, use this method:

```java
String url = "https://username:password@yourwebsite.com";
driver.get(url);
```
✅ Works for **basic authentication pop-ups**.

---

## **📌 5. Handling Window Pop-ups (File Upload)**
Selenium cannot handle OS pop-ups directly. Use **Robot Class**:

```java
import java.awt.*;
import java.awt.event.KeyEvent;

Robot robot = new Robot();
robot.keyPress(KeyEvent.VK_ENTER);
robot.keyRelease(KeyEvent.VK_ENTER);
```
✅ Used for handling **file upload pop-ups**.

---

## **📌 6. Handling Multiple Windows (Child Window Pop-ups)**
```java
String mainWindow = driver.getWindowHandle();

// Switch to new window
for (String windowHandle : driver.getWindowHandles()) {
    if (!windowHandle.equals(mainWindow)) {
        driver.switchTo().window(windowHandle);
        break;
    }
}

// Close child window & switch back
driver.close();
driver.switchTo().window(mainWindow);
```
✅ **Used for pop-ups that open in a new tab/window**.

---

## **📌 Summary Table**
| **Pop-up Type** | **Handling Method** |
|--------------|-----------------|
| **JavaScript Alert (OK Button)** | `alert.accept();` |
| **Confirmation Alert (OK/Cancel)** | `alert.dismiss();` |
| **Prompt Alert (Input Box)** | `alert.sendKeys("Text");` |
| **Authentication Pop-up** | `driver.get("https://username:password@url");` |
| **File Upload Pop-up** | Use **Robot Class** |
| **New Window Pop-up** | Use **Window Handles** |

---

# **🔹 Handling Multiple Browser Windows or Tabs in Selenium** 🚀  

When a test scenario involves multiple browser windows or tabs, Selenium provides **Window Handles** to switch between them.

---

## **📌 1. Get the Current Window Handle**
```java
String mainWindow = driver.getWindowHandle();
```
✅ **Stores the main window handle** to switch back later.

---

## **📌 2. Get All Open Window Handles**
```java
Set<String> allWindows = driver.getWindowHandles();
```
✅ **Returns a set of all open windows/tabs.**

---

## **📌 3. Switch to a New Window**
```java
for (String windowHandle : allWindows) {
    if (!windowHandle.equals(mainWindow)) {
        driver.switchTo().window(windowHandle);
        break;
    }
}
```
✅ **Switches control to a newly opened window.**

---

## **📌 4. Perform Actions in the New Window**
```java
driver.findElement(By.id("submit")).click();
```
✅ Interacts with elements in the new window.

---

## **📌 5. Close the Child Window & Switch Back**
```java
driver.close();  // Close the current window
driver.switchTo().window(mainWindow);  // Switch back to main window
```
✅ Always **switch back to the main window** after closing the child window.

---

## **📌 6. Using `ArrayList` for Easier Window Switching**
```java
ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());

// Switch to second tab
driver.switchTo().window(tabs.get(1));

// Close second tab and switch back
driver.close();
driver.switchTo().window(tabs.get(0));
```
✅ **Indexes window handles**, making switching easier.

---

## **📌 Summary Table**
| **Action** | **Selenium Command** |
|-----------|---------------------|
| **Get main window handle** | `driver.getWindowHandle();` |
| **Get all window handles** | `driver.getWindowHandles();` |
| **Switch to new window** | `driver.switchTo().window(handle);` |
| **Close current window** | `driver.close();` |
| **Switch back to main window** | `driver.switchTo().window(mainWindow);` |

---

# **🔹 Implicit, Explicit, and Fluent Waits in Selenium – Differences & Usage** 🚀  

Selenium provides different types of **waits** to handle dynamic elements and improve test stability.  

---

## **📌 1. Implicit Wait** ⏳ *(Applied Globally)*
- Tells Selenium to **wait for a specified time** before throwing `NoSuchElementException`.  
- Applies **globally** to all elements in the script.  

### **🔹 Syntax:**
```java
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
```
✅ **Good for**: General element loading issues.  
❌ **Not ideal for**: Specific conditions like element visibility or clickability.  

---

## **📌 2. Explicit Wait** ⏳ *(Applied to Specific Elements)*
- Waits **until a certain condition is met** (e.g., element is clickable, visible, etc.).  
- Uses **`WebDriverWait` and `ExpectedConditions`**.  

### **🔹 Syntax:**
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));
element.click();
```
✅ **Good for**: Waiting for specific elements.  
❌ **Not ideal for**: Applying globally to all elements.  

---

## **📌 3. Fluent Wait** ⏳ *(Advanced Explicit Wait)*
- Similar to **Explicit Wait**, but allows:  
  ✅ Custom polling frequency (e.g., check every 500ms).  
  ✅ Handling exceptions like `NoSuchElementException`.  

### **🔹 Syntax:**
```java
Wait<WebDriver> fluentWait = new FluentWait<>(driver)
        .withTimeout(Duration.ofSeconds(20)) // Max wait time
        .pollingEvery(Duration.ofMillis(500)) // Check every 500ms
        .ignoring(NoSuchElementException.class); // Ignore errors

WebElement element = fluentWait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
element.click();
```
✅ **Good for**: Dynamic elements that load unpredictably.  
❌ **Not ideal for**: Simple wait scenarios (Explicit Wait is enough).  

---

## **📌 Differences Between Implicit, Explicit, and Fluent Wait**
| **Feature**  | **Implicit Wait** | **Explicit Wait** | **Fluent Wait** |
|-------------|----------------|----------------|--------------|
| **Scope** | Applies **globally** to all elements. | Applies **only to a specific element**. | Applies **only to a specific element**. |
| **Condition-Based?** | ❌ No, waits for a fixed time. | ✅ Yes, waits for a condition (e.g., visibility, clickability). | ✅ Yes, waits with **custom polling intervals**. |
| **Best Use Case** | Elements taking time to load globally. | Waiting for **specific conditions** like visibility or clickability. | Handling **dynamic elements** with unpredictable load times. |
| **Polling Frequency** | **N/A** (Checks once every fixed interval). | **Fixed polling (500ms by default)**. | **Custom polling (e.g., check every 300ms, 500ms, etc.)**. |

---

## **🎯 Best Practices**
✔ **Use Implicit Wait** for **global element loading** issues.  
✔ **Use Explicit Wait** for **specific conditions** (visibility, clickability).  
✔ **Use Fluent Wait** for **highly dynamic elements** with unpredictable behavior.  

---

# **🔹 Handling Frames and iFrames in Selenium WebDriver** 🚀  

In Selenium, **frames (iframe)** are HTML elements that load another document inside the main page. To interact with elements inside a frame, Selenium must **switch to the frame first** before performing actions.  

---

## **📌 1. Switch to a Frame by Index**
```java
driver.switchTo().frame(0);  // Switch to the first frame (index starts from 0)
```
✅ **Use Case:** When there are **multiple frames** but no unique `id` or `name`.  
❌ **Issue:** Not reliable if the frame order changes.  

---

## **📌 2. Switch to a Frame by Name or ID**
```java
driver.switchTo().frame("frameName"); // Switch using frame name
driver.switchTo().frame("frameID");   // Switch using frame ID
```
✅ **Use Case:** When the frame has a unique `id` or `name`.  

---

## **📌 3. Switch to a Frame by WebElement**
```java
WebElement frameElement = driver.findElement(By.xpath("//iframe[@id='frameID']"));
driver.switchTo().frame(frameElement);
```
✅ **Use Case:** When the frame doesn’t have a unique `id` or `name`.  

---

## **📌 4. Switch Back to the Parent Frame**
```java
driver.switchTo().parentFrame(); // Moves back to the immediate parent frame
```
✅ **Use Case:** When inside a nested frame and need to move **one level up**.  

---

## **📌 5. Switch Back to the Main Page**
```java
driver.switchTo().defaultContent(); // Switches to the main HTML document
```
✅ **Use Case:** When deep inside nested frames and need to return to the **main page**.  

---

## **📌 6. Handling Nested Frames (Parent → Child Frame)**
```java
driver.switchTo().frame("parentFrame"); // Switch to parent frame
driver.switchTo().frame("childFrame");  // Switch to child frame inside parent
```
✅ **Use Case:** When dealing with **nested frames** (iframe inside another iframe).  

---

## **📌 Full Example: Handling Frames in Selenium**
```java
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FrameHandling {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");

        // Switch to the frame
        driver.switchTo().frame("frameID");

        // Perform actions inside the frame
        driver.findElement(By.id("button")).click();

        // Switch back to the main page
        driver.switchTo().defaultContent();

        driver.quit();
    }
}
```

---

## **📌 Summary Table**
| **Action** | **Selenium Command** |
|-----------|---------------------|
| **Switch to frame by index** | `driver.switchTo().frame(0);` |
| **Switch to frame by name/id** | `driver.switchTo().frame("frameID");` |
| **Switch to frame by WebElement** | `driver.switchTo().frame(frameElement);` |
| **Switch to parent frame** | `driver.switchTo().parentFrame();` |
| **Switch to main page** | `driver.switchTo().defaultContent();` |

---

# **🔹 Performing Mouse and Keyboard Actions in Selenium** 🚀  

Selenium provides the **Actions class** to simulate **mouse and keyboard interactions** like hovering, right-clicking, dragging, and pressing keys.

---

## **📌 1. Using Actions Class in Selenium**
To perform **mouse and keyboard actions**, import and create an `Actions` object:  
```java
import org.openqa.selenium.interactions.Actions;
Actions actions = new Actions(driver);
```

---

## **📌 2. Mouse Actions in Selenium**
### **🔹 a) Hover Over an Element (Mouse Hover)**
```java
WebElement element = driver.findElement(By.id("menu"));
actions.moveToElement(element).perform();  // Hover over the element
```
✅ **Use Case:** Display dropdown menus on hover.

---

### **🔹 b) Right-Click (Context Click)**
```java
WebElement element = driver.findElement(By.id("context-menu"));
actions.contextClick(element).perform();  // Right-click on the element
```
✅ **Use Case:** Open **context menus** on right-click.

---

### **🔹 c) Double Click**
```java
WebElement element = driver.findElement(By.id("double-click"));
actions.doubleClick(element).perform();  // Double-click on the element
```
✅ **Use Case:** Select text or activate special UI elements.

---

### **🔹 d) Drag and Drop**
```java
WebElement source = driver.findElement(By.id("drag"));
WebElement target = driver.findElement(By.id("drop"));
actions.dragAndDrop(source, target).perform();
```
✅ **Use Case:** Simulating **dragging elements** in web applications.

---

### **🔹 e) Click and Hold (Drag without Releasing)**
```java
WebElement element = driver.findElement(By.id("drag"));
actions.clickAndHold(element).moveByOffset(50, 100).release().perform();
```
✅ **Use Case:** Dragging an item **without an explicit target**.

---

## **📌 3. Keyboard Actions in Selenium**
### **🔹 a) Press and Release Keys**
```java
actions.sendKeys(Keys.ENTER).perform();  // Press Enter key
```
✅ **Use Case:** Simulate pressing **Enter, Tab, Backspace, etc.**.

---

### **🔹 b) Press a Key While Holding Another (Shift + Letter)**
```java
WebElement inputField = driver.findElement(By.id("textbox"));
actions.keyDown(Keys.SHIFT).sendKeys("hello").keyUp(Keys.SHIFT).perform();
```
✅ **Use Case:** Type text in **uppercase** (SHIFT + Letters).

---

### **🔹 c) Keyboard Shortcuts (Ctrl + A, Ctrl + C, Ctrl + V)**
```java
actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();  // Select All
actions.keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL).perform();  // Copy
actions.keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).perform();  // Paste
```
✅ **Use Case:** Simulate **copy-pasting or selecting text**.

---

## **📌 4. Full Example: Mouse & Keyboard Actions**
```java
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.chrome.ChromeDriver;

public class MouseKeyboardActions {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");

        Actions actions = new Actions(driver);

        // Hover over an element
        WebElement menu = driver.findElement(By.id("menu"));
        actions.moveToElement(menu).perform();

        // Right-click on an element
        WebElement rightClickElement = driver.findElement(By.id("context-menu"));
        actions.contextClick(rightClickElement).perform();

        // Double-click an element
        WebElement doubleClickElement = driver.findElement(By.id("double-click"));
        actions.doubleClick(doubleClickElement).perform();

        // Drag and Drop
        WebElement drag = driver.findElement(By.id("drag"));
        WebElement drop = driver.findElement(By.id("drop"));
        actions.dragAndDrop(drag, drop).perform();

        // Pressing Enter key
        actions.sendKeys(Keys.ENTER).perform();

        driver.quit();
    }
}
```
✅ This script demonstrates **mouse hover, right-click, double-click, drag-and-drop, and keyboard shortcuts**.

---

## **📌 Summary Table**
| **Action** | **Selenium Command** |
|-----------|---------------------|
| **Mouse Hover** | `actions.moveToElement(element).perform();` |
| **Right-Click** | `actions.contextClick(element).perform();` |
| **Double-Click** | `actions.doubleClick(element).perform();` |
| **Drag and Drop** | `actions.dragAndDrop(source, target).perform();` |
| **Press Enter** | `actions.sendKeys(Keys.ENTER).perform();` |
| **Ctrl + A (Select All)** | `actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();` |

---

# **🔹 Page Object Model (POM) in Selenium** 🚀  

## **📌 What is Page Object Model (POM)?**  
Page Object Model (POM) is a **design pattern** used in Selenium automation that **separates test scripts from UI locators and interactions** by creating a separate class for each web page.  

🔹 **In Simple Terms:**  
- Each **web page** in the application has a **corresponding Java class**.  
- This class contains **locators (elements)** and **methods (actions)** related to that page.  
- It improves **code reusability, maintainability, and readability**.  

---

## **📌 Why Use POM? (Advantages)**
✅ **Improves Code Maintainability** – UI locators are centralized, so changes in the UI require updates only in one place.  
✅ **Enhances Reusability** – Methods can be reused in multiple test cases.  
✅ **Increases Readability** – Clean and structured test scripts.  
✅ **Reduces Code Duplication** – Locators are not repeated across multiple test cases.  

---

## **📌 How POM Works?**
1️⃣ **Create a Separate Class for Each Page**  
2️⃣ **Define Web Elements using `@FindBy` (Page Factory)**  
3️⃣ **Create Methods for Page Actions**  
4️⃣ **Use the Page Class in Test Scripts**  

---

## **📌 Example: Implementing POM in Selenium**  

### **🔹 1. Page Class (`LoginPage.java`)**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    WebDriver driver;

    // Define locators using @FindBy
    @FindBy(id = "username")
    WebElement usernameField;

    @FindBy(name = "password")
    WebElement passwordField;

    @FindBy(xpath = "//button[@id='login']")
    WebElement loginButton;

    // Constructor to initialize elements
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Page actions (Reusable methods)
    public void enterUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }
}
```
✅ **This class represents the Login Page with elements and actions.**  

---

### **🔹 2. Test Script (`LoginTest.java`)**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
    WebDriver driver;
    LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://example.com/login");
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testValidLogin() {
        loginPage.enterUsername("testuser");
        loginPage.enterPassword("password123");
        loginPage.clickLogin();
        
        // Validate login success
        Assert.assertEquals(driver.getCurrentUrl(), "https://example.com/dashboard");
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
```
✅ **This test script:**  
✔ Uses the **LoginPage class**  
✔ Calls its methods instead of writing duplicate Selenium code  

---

## **📌 POM with Page Factory vs. Without Page Factory**
| **Feature** | **POM with Page Factory (`@FindBy`)** | **POM Without Page Factory (`driver.findElement`)** |
|------------|----------------------------------|----------------------------------|
| **Code Readability** | ✅ Clean & organized | ❌ More redundant code |
| **Performance** | ✅ Faster (elements initialized once) | ❌ Slower (elements located every time) |
| **Ease of Maintenance** | ✅ Easy (Update only in page class) | ❌ Hard (Changes in multiple places) |
| **Best For** | Large projects | Small test cases |

---

## **📌 Summary**
✔ **Page Object Model (POM)** improves **test structure, readability, and maintainability**.  
✔ **Each page has a corresponding Java class** with locators and actions.  
✔ **Page Factory (`@FindBy`) is recommended** for better performance.  
✔ **Test scripts only call page methods**, making them cleaner and reusable.  

---

# **🔹 Page Factory in Selenium WebDriver** 🚀  

## **📌 What is Page Factory?**  
Page Factory is a **built-in class in Selenium** that helps **implement the Page Object Model (POM)** efficiently. It initializes **web elements only when they are used**, making test execution **faster and more optimized**.  

✅ **It uses `@FindBy` annotations** to locate elements, instead of `driver.findElement()`.  

---

## **📌 Why Use Page Factory? (Advantages)**
✔ **Improves Test Execution Speed** – Elements are initialized only when needed.  
✔ **Simplifies Code** – Reduces `findElement()` calls.  
✔ **Increases Readability** – Uses `@FindBy` annotations for cleaner syntax.  
✔ **Enhances Maintainability** – Elements are declared in a structured way.  

---

## **📌 How to Use Page Factory in Selenium?**
### **🔹 1. Define a Page Class (`LoginPage.java`)**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    WebDriver driver;

    // Using @FindBy to locate elements
    @FindBy(id = "username")
    WebElement usernameField;

    @FindBy(name = "password")
    WebElement passwordField;

    @FindBy(xpath = "//button[@id='login']")
    WebElement loginButton;

    // Constructor to initialize elements
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public void enterUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }
}
```
✅ **This initializes all elements using `PageFactory.initElements()`**.  

---

### **🔹 2. Use Page Factory in Test Script (`LoginTest.java`)**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
    WebDriver driver;
    LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://example.com/login");
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testValidLogin() {
        loginPage.enterUsername("testuser");
        loginPage.enterPassword("password123");
        loginPage.clickLogin();
        
        // Validate login success
        Assert.assertEquals(driver.getCurrentUrl(), "https://example.com/dashboard");
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
```
✅ **This test script interacts with elements using Page Factory methods.**  

---

## **📌 Page Factory vs. Traditional POM (`findElement()`)**
| **Feature** | **Page Factory (`@FindBy`)** | **Traditional POM (`findElement`)** |
|------------|---------------------------|--------------------------------|
| **Code Readability** | ✅ Cleaner & structured | ❌ Repetitive code |
| **Performance** | ✅ Faster (Lazy Initialization) | ❌ Slower (Elements searched every time) |
| **Ease of Maintenance** | ✅ Easy (Update in one place) | ❌ Hard (Changes affect multiple test cases) |
| **Element Initialization** | ✅ Done only when needed | ❌ Done every time before interaction |
| **Best For** | Large-scale test automation | Small test cases |

---

## **📌 When to Use Page Factory?**
✔ **Use Page Factory** when working with **large test suites** for better performance.  
✔ **Use Traditional `findElement()`** for **small test cases** with fewer elements.  

---

# **🔹 Handling Dynamic Elements in Selenium WebDriver** 🚀  

Dynamic elements are **web elements whose attributes (ID, Class, XPath, etc.) change on every page load or user interaction**. To handle them, Selenium provides multiple strategies.  

---

## **📌 1. Use Dynamic XPath with `contains()` or `starts-with()`**
When elements have changing attributes, use XPath functions:  

✅ **Using `contains()` (Partial Attribute Matching)**  
```java
driver.findElement(By.xpath("//input[contains(@id, 'dynamic_')]")).sendKeys("TestUser");
```
✅ **Using `starts-with()` (Attribute Starts With)**  
```java
driver.findElement(By.xpath("//button[starts-with(@id, 'submit_')]")).click();
```
✅ **Using `text()` (Match Visible Text)**  
```java
driver.findElement(By.xpath("//a[text()='Click Here']")).click();
```
✔ **Best For:** Handling elements with changing IDs or classes.  

---

## **📌 2. Use CSS Selectors for Stable Locators**
CSS Selectors can handle dynamic elements **more efficiently** than XPath.  

✅ **Match Partial Attribute (`*=` Contains, `^=` Starts With, `$=` Ends With)**  
```java
driver.findElement(By.cssSelector("input[id*='dynamic_']")).sendKeys("TestUser"); // Contains
driver.findElement(By.cssSelector("button[id^='submit_']")).click(); // Starts with
driver.findElement(By.cssSelector("div[class$='container']")).click(); // Ends with
```
✔ **Best For:** Faster execution and simpler syntax.  

---

## **📌 3. Implement Explicit Wait (Wait Until Element Appears)**
Dynamic elements often take time to load. Use **Explicit Wait** to wait for them.  

```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dynamicElement")));
element.click();
```
✔ **Best For:** Waiting for elements to become **visible or clickable**.  

---

## **📌 4. Use JavaScript Executor (For Hidden or Slow-Loading Elements)**
If Selenium **fails to detect elements**, force interaction using JavaScript.  

```java
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("arguments[0].click();", driver.findElement(By.id("dynamicElement")));
```
✔ **Best For:** Handling elements that are **not detected by Selenium WebDriver**.  

---

## **📌 5. Use `FluentWait` for Polling-Based Element Detection**
If an element loads **intermittently**, `FluentWait` retries **at intervals** until it appears.  

```java
Wait<WebDriver> fluentWait = new FluentWait<>(driver)
        .withTimeout(Duration.ofSeconds(20))  // Max wait time
        .pollingEvery(Duration.ofMillis(500)) // Check every 500ms
        .ignoring(NoSuchElementException.class);

WebElement element = fluentWait.until(ExpectedConditions.elementToBeClickable(By.id("dynamicElement")));
element.click();
```
✔ **Best For:** Elements appearing at **random intervals**.  

---

## **📌 6. Refresh Page and Retry (For AJAX-Based Elements)**
If elements **change after an AJAX call**, refreshing may help.  

```java
driver.navigate().refresh();
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("dynamicElement")));
element.click();
```
✔ **Best For:** Elements that appear **after an AJAX refresh**.  

---

## **📌 7. Handle `StaleElementReferenceException` (If Element Becomes Stale)**
If an element **disappears and reappears**, re-locate it using a **loop**.  

```java
for (int i = 0; i < 3; i++) {
    try {
        driver.findElement(By.id("dynamicElement")).click();
        break;
    } catch (StaleElementReferenceException e) {
        System.out.println("Element became stale, retrying...");
    }
}
```
✔ **Best For:** Handling elements that **reload dynamically**.  

---

## **📌 Summary Table**
| **Strategy** | **When to Use?** | **Example** |
|------------|----------------|------------|
| **Dynamic XPath (`contains()`, `starts-with()`)** | When attributes change dynamically | `//input[contains(@id, 'dynamic_')]` |
| **CSS Selectors (`*=`, `^=`, `$=`)** | For faster execution | `input[id*='dynamic_']` |
| **Explicit Wait (`WebDriverWait`)** | When elements take time to load | `wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dynamic")))` |
| **JavaScript Executor** | If Selenium fails to detect an element | `js.executeScript("arguments[0].click();", element);` |
| **Fluent Wait** | When elements appear at **random intervals** | `fluentWait.until(ExpectedConditions.elementToBeClickable(element));` |
| **Refresh and Retry** | If elements appear after AJAX calls | `driver.navigate().refresh();` |
| **Handle Stale Element** | If elements become **stale** (reloads) | `try { element.click(); } catch (StaleElementReferenceException e) { retry(); }` |

---

# **🔹 Handling Dropdowns in Selenium WebDriver** 🚀  

In Selenium, dropdowns can be handled using the **`Select` class** or by interacting directly with elements (e.g., `click()`, JavaScript).  

---

## **📌 1. Handling Dropdowns Using the `Select` Class**
Selenium provides the `Select` class to handle **`<select>` dropdowns**.  

### **🔹 Step 1: Import `Select` Class**
```java
import org.openqa.selenium.support.ui.Select;
```

### **🔹 Step 2: Locate the Dropdown Element**
```java
WebElement dropdown = driver.findElement(By.id("dropdownId"));
Select select = new Select(dropdown);
```

### **🔹 Step 3: Select Options**
```java
select.selectByVisibleText("Option 1");  // Select by visible text
select.selectByValue("value1");         // Select by value attribute
select.selectByIndex(2);                // Select by index (0-based)
```

✅ **Best For:** Standard `<select>` dropdowns.  

---

## **📌 2. Get All Dropdown Options**
```java
List<WebElement> options = select.getOptions();
for (WebElement option : options) {
    System.out.println(option.getText());
}
```
✅ **Best For:** Fetching all available options in a dropdown.  

---

## **📌 3. Deselect Options (For Multi-Select Dropdowns)**
```java
select.deselectByIndex(1);   // Deselect option by index
select.deselectByValue("v2"); // Deselect option by value
select.deselectAll();        // Deselect all selected options
```
✅ **Best For:** Multi-select dropdowns (`<select multiple>`).  

---

## **📌 4. Handling Dropdowns Without the `Select` Class**
For **custom dropdowns (non-`<select>` elements)**, use `click()`.  

```java
driver.findElement(By.id("dropdownButton")).click();  // Click dropdown
driver.findElement(By.xpath("//li[text()='Option 2']")).click(); // Select option
```
✅ **Best For:** Dropdowns made with `<div>`, `<ul>`, etc.  

---

## **📌 5. Handling Dropdowns Using JavaScript Executor**
If Selenium **fails to interact**, use JavaScript:  

```java
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("document.getElementById('dropdownId').value='OptionValue';");
```
✅ **Best For:** Hidden dropdowns or when `click()` doesn’t work.  

---

## **📌 Summary Table**
| **Method** | **Best For** | **Example Code** |
|-----------|------------|----------------|
| **`Select` Class** | Standard `<select>` dropdowns | `select.selectByVisibleText("Option");` |
| **Click & XPath** | Custom dropdowns (`<div>`, `<ul>`) | `driver.findElement(By.xpath("//li[text()='Option']")).click();` |
| **JavaScript Executor** | Hidden or unclickable dropdowns | `js.executeScript("document.getElementById('id').value='Option'");` |

---

# **🔹 Different Types of Exceptions in Selenium WebDriver & How to Handle Them** 🚀  

Exceptions in Selenium occur when WebDriver fails to perform an action due to various reasons like **missing elements, timeouts, stale references, or browser issues**.  

---

## **📌 1. Common Selenium Exceptions & How to Handle Them**
| **Exception Name** | **Cause** | **Handling Method** |
|-------------------|-----------|---------------------|
| **`NoSuchElementException`** | Element not found on the page. | Use **Explicit Wait** before interacting with the element. |
| **`TimeoutException`** | The page or element did not load in the expected time. | Increase **wait time** using `WebDriverWait`. |
| **`StaleElementReferenceException`** | Element is no longer attached to the DOM. | Re-locate the element before interaction. |
| **`ElementNotVisibleException`** | Element exists but is not visible. | Use `ExpectedConditions.visibilityOfElementLocated()`. |
| **`ElementClickInterceptedException`** | Another element is blocking the target element. | Use JavaScript Click `executeScript()`. |
| **`NoSuchWindowException`** | Trying to switch to a window that doesn’t exist. | Validate window handles before switching. |
| **`NoSuchFrameException`** | Trying to switch to a non-existent frame. | Use `driver.switchTo().defaultContent();` before switching. |
| **`InvalidSelectorException`** | Incorrect XPath or CSS Selector syntax. | Verify the selector using DevTools (F12). |
| **`SessionNotCreatedException`** | Browser version mismatch with WebDriver. | Update WebDriver to the latest version. |

---

## **📌 2. How to Handle Selenium Exceptions**
### **🔹 Using Try-Catch Block**
```java
try {
    driver.findElement(By.id("login")).click();
} catch (NoSuchElementException e) {
    System.out.println("Element not found! Retrying...");
}
```
✅ **Best For:** Handling specific exceptions gracefully.

---

### **🔹 Using `WebDriverWait` (Explicit Wait)**
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));
element.click();
```
✅ **Best For:** Handling **`NoSuchElementException` & `ElementNotVisibleException`**.

---

### **🔹 Handling `StaleElementReferenceException` (Re-locate Element)**
```java
for (int i = 0; i < 3; i++) {
    try {
        driver.findElement(By.id("button")).click();
        break;
    } catch (StaleElementReferenceException e) {
        System.out.println("Element is stale, retrying...");
    }
}
```
✅ **Best For:** Handling stale elements that **reload dynamically**.

---

### **🔹 Handling `ElementClickInterceptedException` (JavaScript Click)**
```java
JavascriptExecutor js = (JavascriptExecutor) driver;
WebElement element = driver.findElement(By.id("submit"));
js.executeScript("arguments[0].click();", element);
```
✅ **Best For:** Clicking **elements blocked by overlays or pop-ups**.

---

## **📌 3. Summary Table: Exception Handling Methods**
| **Exception Type** | **Handling Method** |
|-------------------|-------------------|
| `NoSuchElementException` | Use **Explicit Wait** |
| `TimeoutException` | Increase **wait time** |
| `StaleElementReferenceException` | Re-locate element before interaction |
| `ElementClickInterceptedException` | Use **JavaScript Click** |
| `NoSuchFrameException` | Use `defaultContent()` before switching |
| `InvalidSelectorException` | Verify XPath/CSS Selector in DevTools |

---

# **🔹 Capturing Screenshots in Selenium WebDriver** 📸🚀  

Selenium provides the **`TakesScreenshot` interface** to capture screenshots during test execution. Screenshots are useful for **debugging failed tests, logging defects, and reporting results**.

---

## **📌 1. Capture Full-Page Screenshot**
### **🔹 Steps:**
1️⃣ **Cast WebDriver to `TakesScreenshot`**  
2️⃣ **Call `getScreenshotAs(OutputType.FILE)`**  
3️⃣ **Save the screenshot to a desired location**  

### **🔹 Example: Save Screenshot as a File**
```java
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class ScreenshotExample {
    public static void main(String[] args) throws IOException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");

        // Take Screenshot
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        
        // Save Screenshot to Local Folder
        FileUtils.copyFile(srcFile, new File("screenshots/screenshot.png"));

        driver.quit();
    }
}
```
✅ **Saves a screenshot as `screenshot.png` in the `screenshots/` folder.**  

---

## **📌 2. Capture Screenshot & Store as Byte Array**
Useful for **embedding screenshots in reports** like **Extent Reports or Allure**.  

```java
byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
```
✅ **Best for**: Adding screenshots directly to reports.

---

## **📌 3. Capture Screenshot of a Specific Web Element**
```java
WebElement element = driver.findElement(By.id("loginButton"));

// Take Element Screenshot
File srcFile = element.getScreenshotAs(OutputType.FILE);

// Save Screenshot
FileUtils.copyFile(srcFile, new File("screenshots/element.png"));
```
✅ **Best for**: Capturing **only a button, text field, or section** of the page.  

---

## **📌 4. Capture Screenshot on Test Failure (TestNG)**
Automatically take a screenshot **when a test fails**.  

```java
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;

public class ScreenshotOnFailure {
    WebDriver driver = new ChromeDriver();

    @Test
    public void testExample() {
        driver.get("https://example.com");
        driver.findElement(By.id("wrongID")).click();  // This will fail
    }

    @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) throws IOException {
        if (ITestResult.FAILURE == result.getStatus()) {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File("screenshots/" + result.getName() + ".png"));
        }
        driver.quit();
    }
}
```
✅ **Best for**: Debugging failed tests in **TestNG Framework**.  

---

## **📌 5. Capture Full Page Screenshot (Using AShot Library)**
Selenium **does not capture full-page screenshots** by default. Use **AShot** for this.

### **🔹 Add Maven Dependency**
```xml
<dependency>
    <groupId>ru.yandex.qatools.ashot</groupId>
    <artifactId>ashot</artifactId>
    <version>1.5.4</version>
</dependency>
```

### **🔹 Capture Full Page Screenshot**
```java
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FullPageScreenshot {
    public static void main(String[] args) throws IOException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");

        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        BufferedImage image = screenshot.getImage();

        ImageIO.write(image, "PNG", new File("screenshots/fullpage.png"));

        driver.quit();
    }
}
```
✅ **Best for**: Capturing **full-page screenshots**, including scrollable content.

---

## **📌 Summary Table**
| **Method** | **Best For** | **Example** |
|------------|------------|-------------|
| **Full Page Screenshot** | Entire webpage | `((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)` |
| **Element Screenshot** | Specific web elements | `element.getScreenshotAs(OutputType.FILE)` |
| **Byte Array Screenshot** | Embedding in reports | `getScreenshotAs(OutputType.BYTES)` |
| **Screenshot on Failure** | Debugging TestNG failures | `ITestResult.FAILURE` |
| **Full Page (Ashot Library)** | Capturing scrollable pages | `new AShot().shootingStrategy(...).takeScreenshot(driver)` |

---

# **🔹 Handling File Uploads & Downloads in Selenium WebDriver** 🚀  

Selenium does not interact with **native OS pop-ups** like file upload/download dialogs. However, we can handle these using **SendKeys, Robot Class, AutoIt, and Chrome Options**.

---

# **📌 1. Handling File Uploads in Selenium** 📤  
**✅ Method 1: Using `sendKeys()` (Simplest & Recommended)**
- If the file upload button is an `<input type="file">`, use `sendKeys()` to provide the file path.

```java
WebElement uploadElement = driver.findElement(By.id("fileUpload"));
uploadElement.sendKeys("C:\\Users\\YourPC\\Documents\\file.pdf");  // Absolute path
```
✅ **Best For:** Websites using standard **HTML file input** elements.  

---

**✅ Method 2: Using Robot Class (For Native Dialogs)**
- If `sendKeys()` doesn’t work (e.g., non-HTML buttons triggering file upload dialogs), use the **Robot Class**.

```java
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class FileUploadRobot {
    public static void uploadFile(String filePath) throws AWTException {
        Robot robot = new Robot();

        // Copy file path to clipboard
        StringSelection selection = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

        // Paste (Ctrl+V) and Press Enter
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }
}
```
✅ **Best For:** **Non-HTML upload buttons**, or when `sendKeys()` doesn’t work.

---

**✅ Method 3: Using AutoIt (For Windows-Based File Uploads)**
- AutoIt is a Windows automation tool to handle OS-based file upload pop-ups.

1. **Create an AutoIt Script (`uploadFile.au3`)**  
```autoit
ControlFocus("Open","","Edit1")
ControlSetText("Open","","Edit1","C:\Users\YourPC\Documents\file.pdf")
ControlClick("Open","","Button1")
```
2. **Compile and Call from Selenium**  
```java
Runtime.getRuntime().exec("C:\\path\\to\\uploadFile.exe");
```
✅ **Best For:** Windows **native file upload dialogs**.

---

# **📌 2. Handling File Downloads in Selenium** 📥  

**✅ Method 1: Set Chrome Preferences for Automatic Downloads**
- **Disable the download pop-up** and set the **default download folder**.

```java
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;

public class ChromeDownloadSetup {
    public static ChromeOptions setDownloadPreferences() {
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("download.default_directory", "C:\\Users\\YourPC\\Downloads");
        chromePrefs.put("download.prompt_for_download", false);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);

        return options;
    }
}
```
✅ **Best For:** Automating **headless file downloads**.

---

**✅ Method 2: Handle Download Pop-ups Using `Robot Class`**
- If a download pop-up appears, use **Robot Class** to press `Enter`.

```java
Robot robot = new Robot();
robot.keyPress(KeyEvent.VK_ENTER);
robot.keyRelease(KeyEvent.VK_ENTER);
```
✅ **Best For:** Handling OS-based **Save As** dialogs.

---

**✅ Method 3: Verify File Download Using Java**
- **Check if the file exists in the download folder**.

```java
import java.io.File;

public class VerifyDownload {
    public static boolean isFileDownloaded(String downloadPath, String fileName) {
        File file = new File(downloadPath + fileName);
        return file.exists();
    }
}
```
✅ **Best For:** Validating file download in automation tests.

---

# **📌 Summary Table**
| **Scenario** | **Best Method** | **Example** |
|-------------|----------------|-------------|
| **File Upload (Standard)** | `sendKeys()` | `uploadElement.sendKeys("filePath");` |
| **File Upload (Non-HTML Dialogs)** | `Robot Class` | `Robot.keyPress(KeyEvent.VK_V);` |
| **File Upload (Windows Pop-ups)** | `AutoIt` | `Runtime.getRuntime().exec("upload.exe");` |
| **File Download (No Pop-ups)** | Chrome Preferences | `options.setExperimentalOption("prefs", chromePrefs);` |
| **File Download (OS Save Dialogs)** | `Robot Class` | `Robot.keyPress(KeyEvent.VK_ENTER);` |
| **Verify File Download** | Java File Handling | `new File(downloadPath + fileName).exists();` |

---

# **🔹 Selenium Grid – Parallel Test Execution Across Multiple Machines & Browsers** 🚀  

## **📌 What is Selenium Grid?**  
Selenium Grid is a tool that allows **parallel execution of test cases on multiple machines, browsers, and operating systems simultaneously**. It follows a **Hub-Node architecture**, enabling **distributed execution** to speed up testing.  

---

## **📌 How Does Selenium Grid Work?**
### **🔹 Hub-Node Architecture**
1️⃣ **Hub** – The central server that controls test execution.  
2️⃣ **Nodes** – The machines where tests run (can be different browsers/OS).  
3️⃣ **Test Scripts** – Sent to the Hub, which assigns them to available Nodes.  

✅ **Example Setup:**  
```
Hub (Main Machine) → Sends Tests → Nodes (Chrome, Firefox, Edge on different OS)
```

---

## **📌 1. Setting Up Selenium Grid (Step-by-Step)**
### **🔹 Step 1: Start the Hub**
```sh
java -jar selenium-server-4.0.0.jar hub
```
✅ **Starts the central server** that distributes test cases.

---

### **🔹 Step 2: Register Nodes**
#### **Register a Chrome Node**
```sh
java -jar selenium-server-4.0.0.jar node --port 5555 --browser "chrome"
```
✅ **Registers a Chrome browser as a test execution node.**

#### **Register a Firefox Node**
```sh
java -jar selenium-server-4.0.0.jar node --port 5556 --browser "firefox"
```
✅ **Registers a Firefox browser as a test execution node.**

---

### **🔹 Step 3: Run Tests on Selenium Grid**
Modify the WebDriver setup in your Selenium script to **connect to the Hub** instead of a local browser.

```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;

public class SeleniumGridTest {
    public static void main(String[] args) throws MalformedURLException {
        // Set Hub URL
        String hubURL = "http://localhost:4444/wd/hub";

        // Define browser capability
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");

        // Create Remote WebDriver instance
        WebDriver driver = new RemoteWebDriver(new URL(hubURL), capabilities);

        // Execute test
        driver.get("https://example.com");
        System.out.println("Title: " + driver.getTitle());

        driver.quit();
    }
}
```
✅ **Runs test cases on a remote machine via Selenium Grid.**

---

## **📌 2. Advantages of Selenium Grid**
✔ **Parallel Execution** – Runs tests on multiple machines at once.  
✔ **Cross-Browser Testing** – Test across **Chrome, Firefox, Edge, Safari, etc.**  
✔ **Cross-Platform Testing** – Test on **Windows, Mac, Linux** remotely.  
✔ **Speeds Up Testing** – Reduces execution time with distributed tests.  
✔ **CI/CD Integration** – Works with **Jenkins, Docker, Kubernetes**.  

---

## **📌 3. Selenium Grid vs. Local WebDriver**
| **Feature** | **Selenium Grid** | **Local WebDriver** |
|------------|----------------|------------------|
| **Execution Speed** | ✅ Faster (Parallel execution) | ❌ Slower (One test at a time) |
| **Cross-Browser Support** | ✅ Yes (Multiple browsers at once) | ❌ Limited (One browser at a time) |
| **Cross-Platform Support** | ✅ Yes (Windows, Mac, Linux) | ❌ No |
| **Remote Execution** | ✅ Yes (Tests run on remote machines) | ❌ No (Tests run locally) |
| **Setup Complexity** | ❌ Requires configuration | ✅ Simple |

---

## **📌 4. Advanced Selenium Grid Setup (Docker + Selenium Grid)**
For **cloud-based execution**, use **Docker Selenium Grid**:
```sh
docker run -d -p 4444:4444 --name selenium-grid selenium/standalone-chrome
```
✅ **Best for CI/CD pipelines and DevOps integration.**  

---

# **🔹 Running Tests in Parallel Using Selenium Grid** 🚀  

## **📌 What is Parallel Execution in Selenium Grid?**  
Parallel execution in Selenium Grid allows **running multiple test cases across different browsers, machines, and operating systems simultaneously**, reducing test execution time.  

---

## **📌 1. Selenium Grid Architecture for Parallel Execution**
✅ **Hub:** Acts as a **central server** that distributes test cases.  
✅ **Nodes:** Machines with **different browsers/OS** where tests execute.  
✅ **Test Scripts:** Connect to the **Hub** and execute on available **Nodes**.  

---

## **📌 2. Setting Up Selenium Grid for Parallel Execution**
### **🔹 Step 1: Start Selenium Grid Hub**
```sh
java -jar selenium-server-4.0.0.jar hub
```
✅ **This starts the Hub at** `http://localhost:4444`.  

---

### **🔹 Step 2: Register Multiple Nodes**
#### **Register a Chrome Node**
```sh
java -jar selenium-server-4.0.0.jar node --port 5555 --browser "chrome"
```
#### **Register a Firefox Node**
```sh
java -jar selenium-server-4.0.0.jar node --port 5556 --browser "firefox"
```
✅ **Multiple nodes enable parallel execution** across browsers.

---

## **📌 3. Configuring Parallel Execution in TestNG**
### **🔹 Step 3: Modify `testng.xml` to Enable Parallel Execution**
```xml
<suite name="Parallel Execution Suite" parallel="tests" thread-count="2">
    <test name="Chrome Test">
        <parameter name="browser" value="chrome"/>
        <classes>
            <class name="tests.ParallelTest"/>
        </classes>
    </test>

    <test name="Firefox Test">
        <parameter name="browser" value="firefox"/>
        <classes>
            <class name="tests.ParallelTest"/>
        </classes>
    </test>
</suite>
```
✅ **`parallel="tests"`** runs test cases on **different browsers in parallel**.  
✅ **`thread-count="2"`** sets the number of parallel threads.  

---

## **📌 4. Implement Parallel Execution in Test Script (`ParallelTest.java`)**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.net.URL;

public class ParallelTest {
    WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setup(String browser) throws MalformedURLException {
        String hubURL = "http://localhost:4444/wd/hub";
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (browser.equalsIgnoreCase("chrome")) {
            capabilities.setBrowserName("chrome");
        } else if (browser.equalsIgnoreCase("firefox")) {
            capabilities.setBrowserName("firefox");
        }

        driver = new RemoteWebDriver(new URL(hubURL), capabilities);
    }

    @Test
    public void testExample() {
        driver.get("https://example.com");
        System.out.println("Title: " + driver.getTitle() + " - Thread: " + Thread.currentThread().getId());
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
```
✅ **Runs tests on multiple browsers simultaneously using TestNG parameters.**  

---

## **📌 5. Running Tests in Parallel Using Maven (Surefire Plugin)**
Modify `pom.xml` to **enable parallel execution** in Maven:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.0.0-M7</version>
    <configuration>
        <parallel>methods</parallel>
        <threadCount>2</threadCount>
    </configuration>
</plugin>
```
✅ **This allows running multiple tests in parallel when executing `mvn test`.**  

---

## **📌 6. Running Parallel Tests with Selenium Grid + Docker**
For **containerized parallel execution**, use **Docker Selenium Grid**:

```sh
docker run -d -p 4444:4444 --name selenium-hub selenium/hub
docker run -d --link selenium-hub -p 5555:5555 selenium/node-chrome
docker run -d --link selenium-hub -p 5556:5556 selenium/node-firefox
```
✅ **Executes tests in parallel using Docker containers**.  

---

## **📌 Summary Table: Parallel Execution in Selenium Grid**
| **Method** | **Best For** | **Configuration** |
|------------|-------------|------------------|
| **TestNG `parallel="tests"`** | Running tests in parallel | Modify `testng.xml` |
| **Maven Surefire Plugin** | Running multiple test methods | Modify `pom.xml` |
| **Selenium Grid Nodes** | Running tests on multiple machines | Start Hub & Nodes |
| **Docker Selenium Grid** | Running tests in isolated environments | Use `docker-compose.yml` |

---

# **🔹 Difference Between `close()` and `quit()` in Selenium WebDriver** 🚀  

| **Method** | **What It Does?** | **When to Use?** |
|------------|------------------|------------------|
| **`driver.close()`** | Closes the **current active window/tab** only. | When you want to **close a specific tab** but keep the browser session running. |
| **`driver.quit()`** | Closes **all browser windows/tabs** and **ends the WebDriver session**. | When you want to **terminate the entire browser session**. |

---

## **📌 Example: `close()` vs `quit()`**
### **🔹 Using `close()` (Closes One Tab)**
```java
WebDriver driver = new ChromeDriver();
driver.get("https://example.com");

// Opens a new tab
driver.switchTo().newWindow(WindowType.TAB);
driver.get("https://google.com");

driver.close();  // Closes only the current tab (Google)
```
✅ **If multiple tabs are open, `close()` closes only the active tab.**  

---

### **🔹 Using `quit()` (Closes All Tabs & Browser)**
```java
WebDriver driver = new ChromeDriver();
driver.get("https://example.com");

// Opens a new tab
driver.switchTo().newWindow(WindowType.TAB);
driver.get("https://google.com");

driver.quit();  // Closes all tabs & quits the browser
```
✅ **Closes all browser windows & terminates WebDriver session.**  

---

## **📌 Key Differences**
| **Feature** | **`close()`** | **`quit()`** |
|------------|-------------|-------------|
| **Closes Single Tab?** | ✅ Yes | ✅ Yes (if only one tab) |
| **Closes All Tabs?** | ❌ No | ✅ Yes |
| **Ends WebDriver Session?** | ❌ No | ✅ Yes |
| **Best For** | Closing **only one tab** | Closing **entire browser** |

---

# **🔹 Performing Drag-and-Drop in Selenium WebDriver** 🚀  

Selenium provides the **`Actions` class** to simulate **drag-and-drop** operations for web elements.

---

## **📌 1. Using `dragAndDrop()` Method (Recommended ✅)**
The easiest way to perform **drag-and-drop** is using `dragAndDrop(source, target)`.  

### **🔹 Example: Drag and Drop an Element**
```java
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class DragAndDropExample {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://jqueryui.com/droppable/");

        // Switch to iframe (if the elements are inside one)
        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));

        // Locate source (draggable element) and target (drop location)
        WebElement source = driver.findElement(By.id("draggable"));
        WebElement target = driver.findElement(By.id("droppable"));

        // Perform drag and drop
        Actions actions = new Actions(driver);
        actions.dragAndDrop(source, target).perform();

        driver.quit();
    }
}
```
✅ **Best for:** Simple drag-and-drop operations.  

---

## **📌 2. Using `clickAndHold()` and `moveToElement()` (For Complex Dragging)**
If `dragAndDrop()` does not work, manually **click, move, and release** the element.  

### **🔹 Example: Manual Drag and Drop**
```java
Actions actions = new Actions(driver);
actions.clickAndHold(source)  // Click and hold the element
       .moveToElement(target) // Move to target
       .release()             // Release the element
       .build().perform();    // Execute action
```
✅ **Best for:** Dragging elements that require **custom movements**.  

---

## **📌 3. Using `moveByOffset()` (For Precise Dragging)**
If the drop area **does not have a fixed target element**, use **pixel coordinates**.

### **🔹 Example: Drag by Offset**
```java
actions.clickAndHold(source).moveByOffset(100, 50).release().perform();
```
✅ **Best for:** Dragging elements to **dynamic positions**.  

---

## **📌 4. Handling Drag and Drop Using JavaScript Executor (For Unresponsive Elements)**
If standard Selenium methods **fail**, execute JavaScript directly.

### **🔹 Example: JavaScript Drag-and-Drop**
```java
JavascriptExecutor js = (JavascriptExecutor) driver;
String script = "var src = arguments[0], tgt = arguments[1];"
              + "var evt = document.createEvent('HTMLEvents');"
              + "evt.initEvent('dragstart', true, true); src.dispatchEvent(evt);"
              + "evt.initEvent('drop', true, true); tgt.dispatchEvent(evt);";
js.executeScript(script, source, target);
```
✅ **Best for:** Pages with **custom JavaScript drag-and-drop implementations**.  

---

## **📌 Summary Table: Drag-and-Drop Methods**
| **Method** | **Best For** | **Example** |
|------------|------------|------------|
| **`dragAndDrop()`** | Standard drag-and-drop elements | `actions.dragAndDrop(source, target).perform();` |
| **`clickAndHold() + moveToElement()`** | Elements needing manual movement | `actions.clickAndHold(source).moveToElement(target).release().perform();` |
| **`moveByOffset(x, y)`** | Dragging to a dynamic location | `actions.clickAndHold(source).moveByOffset(100, 50).release().perform();` |
| **JavaScript Executor** | Handling JavaScript-based drag-drop | `js.executeScript(dragScript, source, target);` |

---

# **🔹 Role of `DesiredCapabilities` in Selenium WebDriver** 🚀  

## **📌 What is `DesiredCapabilities`?**  
`DesiredCapabilities` is a class in Selenium **(Deprecated in Selenium 4)** that was used to **set browser properties, enable features, and configure WebDriver sessions** for **remote execution**, especially with **Selenium Grid**.  

📌 **In Selenium 4, `DesiredCapabilities` is replaced by `Options` classes** like `ChromeOptions`, `FirefoxOptions`, and `EdgeOptions`.

---

## **📌 1. How `DesiredCapabilities` Works (Selenium 3)**
### **🔹 Example: Setting Browser Preferences in Selenium 3**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DesiredCapabilitiesExample {
    public static void main(String[] args) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");  // Set browser
        capabilities.setCapability("acceptInsecureCerts", true); // Handle SSL certificates

        WebDriver driver = new ChromeDriver(capabilities);
        driver.get("https://example.com");
    }
}
```
✅ **Best For (Selenium 3):** Setting browser properties, enabling SSL certificates, and configuring remote execution.

---

## **📌 2. `DesiredCapabilities` Replaced by `Options` in Selenium 4**
### **🔹 Example: Using `ChromeOptions` Instead of `DesiredCapabilities`**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeOptionsExample {
    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true); // Handle SSL certificates
        options.addArguments("--headless"); // Run browser in headless mode

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://example.com");
    }
}
```
✅ **Selenium 4 uses browser-specific `Options` classes instead of `DesiredCapabilities`.**

---

## **📌 3. Using `DesiredCapabilities` with Selenium Grid**
Even though `DesiredCapabilities` is deprecated, it is **still used in Selenium Grid** for remote execution.

### **🔹 Example: Running Tests on Selenium Grid**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;

public class SeleniumGridTest {
    public static void main(String[] args) throws MalformedURLException {
        String hubURL = "http://localhost:4444/wd/hub";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");

        WebDriver driver = new RemoteWebDriver(new URL(hubURL), capabilities);
        driver.get("https://example.com");

        System.out.println("Title: " + driver.getTitle());
        driver.quit();
    }
}
```
✅ **Best For:** Running tests on **Selenium Grid (Hub & Nodes).**

---

## **📌 4. Alternative: `Options` with Selenium Grid (Selenium 4)**
```java
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

ChromeOptions options = new ChromeOptions();
options.setCapability("browserName", "chrome");

WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"), options);
```
✅ **Replaces `DesiredCapabilities` in Selenium 4.**

---

## **📌 Summary Table**
| **Feature** | **`DesiredCapabilities` (Deprecated)** | **`Options` Classes (Selenium 4)** |
|------------|--------------------------------|--------------------------------|
| **Setting Browser Properties** | ✅ Yes | ✅ Yes |
| **Handling SSL Certificates** | ✅ Yes | ✅ Yes |
| **Running Tests on Selenium Grid** | ✅ Yes (Still used) | ✅ Yes (`ChromeOptions`, etc.) |
| **Remote Execution** | ✅ Yes | ✅ Yes (`setCapability()`) |
| **Usage in Selenium 4** | ❌ Deprecated | ✅ Recommended |

---

# **🔹 Executing JavaScript in Selenium WebDriver** 🚀  

Selenium WebDriver allows executing **JavaScript code** using **`JavascriptExecutor`** to perform advanced actions like handling hidden elements, scrolling, clicking, or modifying values.

---

## **📌 1. What is `JavascriptExecutor`?**
`JavascriptExecutor` is an interface in Selenium that lets you execute JavaScript commands in the browser.

✅ **Use Cases:**  
✔ Click elements that Selenium can’t interact with.  
✔ Scroll the page dynamically.  
✔ Handle hidden elements.  
✔ Retrieve values from the browser console.  

---

## **📌 2. How to Use `JavascriptExecutor` in Selenium?**
To execute JavaScript, **cast WebDriver to `JavascriptExecutor`**:
```java
JavascriptExecutor js = (JavascriptExecutor) driver;
```

### **🔹 Example: Execute Simple JavaScript Command**
```java
js.executeScript("alert('Hello from Selenium!');");
```
✅ **Opens an alert box in the browser.**

---

## **📌 3. Scrolling Using JavaScript**
### **🔹 Scroll Down by Pixels**
```java
js.executeScript("window.scrollBy(0,500);");  // Scroll down 500px
```
✅ **Useful for:** Handling elements below the viewport.

### **🔹 Scroll to Bottom of Page**
```java
js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
```
✅ **Useful for:** Infinite scrolling pages.

### **🔹 Scroll to a Specific Element**
```java
WebElement element = driver.findElement(By.id("footer"));
js.executeScript("arguments[0].scrollIntoView(true);", element);
```
✅ **Best for:** Navigating to an element that’s not visible.

---

## **📌 4. Clicking Hidden Elements**
```java
WebElement hiddenButton = driver.findElement(By.id("hiddenBtn"));
js.executeScript("arguments[0].click();", hiddenButton);
```
✅ **Best for:** Clicking buttons that are blocked by overlays.

---

## **📌 5. Entering Text in a Disabled Input Field**
```java
WebElement inputField = driver.findElement(By.id("username"));
js.executeScript("arguments[0].value='SeleniumUser';", inputField);
```
✅ **Best for:** Filling forms when `sendKeys()` fails.

---

## **📌 6. Retrieving Browser and Element Information**
```java
String pageTitle = (String) js.executeScript("return document.title;");
System.out.println("Page Title: " + pageTitle);
```
✅ **Best for:** Fetching dynamic page data.

---

## **📌 7. Highlighting an Element (For Debugging)**
```java
WebElement element = driver.findElement(By.id("searchBox"));
js.executeScript("arguments[0].style.border='3px solid red';", element);
```
✅ **Best for:** Debugging element identification.

---

## **📌 Summary Table**
| **JavaScript Action** | **Selenium Code** |
|----------------|----------------|
| **Scroll Down** | `js.executeScript("window.scrollBy(0,500);");` |
| **Scroll to Bottom** | `js.executeScript("window.scrollTo(0, document.body.scrollHeight);");` |
| **Scroll to Element** | `js.executeScript("arguments[0].scrollIntoView(true);", element);` |
| **Click Hidden Element** | `js.executeScript("arguments[0].click();", element);` |
| **Set Value in Disabled Field** | `js.executeScript("arguments[0].value='Selenium';", element);` |
| **Get Page Title** | `js.executeScript("return document.title;");` |
| **Highlight Element** | `js.executeScript("arguments[0].style.border='3px solid red';", element);` |

---

# **🔹 Handling AJAX Elements in Selenium WebDriver** 🚀  

AJAX (Asynchronous JavaScript and XML) updates web elements dynamically **without refreshing the page**. Selenium **might fail** to locate elements immediately, leading to exceptions like:  
❌ `NoSuchElementException`  
❌ `StaleElementReferenceException`  
❌ `TimeoutException`  

To handle AJAX elements, we use **Explicit Waits, JavaScript Executor, and Fluent Waits**.

---

## **📌 1. Use Explicit Wait (`WebDriverWait`)** ✅ (Recommended)
Since AJAX elements take time to load, use **`WebDriverWait` with `ExpectedConditions`** to wait dynamically.

### **🔹 Example: Wait Until Element is Visible**
```java
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ajaxElement")));
element.click();
```
✅ **Best For:** Waiting until an **AJAX-loaded element is visible.**  

---

## **📌 2. Use `FluentWait` (Advanced Handling)**
If AJAX elements load **intermittently**, `FluentWait` retries **at intervals** until the element appears.

### **🔹 Example: Use Fluent Wait**
```java
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;
import java.util.NoSuchElementException;
import org.openqa.selenium.support.ui.Wait;

Wait<WebDriver> fluentWait = new FluentWait<>(driver)
        .withTimeout(Duration.ofSeconds(20))  // Max wait time
        .pollingEvery(Duration.ofMillis(500)) // Check every 500ms
        .ignoring(NoSuchElementException.class);

WebElement element = fluentWait.until(ExpectedConditions.elementToBeClickable(By.id("ajaxButton")));
element.click();
```
✅ **Best For:** Handling **slow-loading AJAX elements.**  

---

## **📌 3. Use JavaScript Executor to Detect AJAX Completion**
AJAX requests can be detected by checking **JavaScript Ready State**.

### **🔹 Example: Wait Until AJAX Completes**
```java
import org.openqa.selenium.JavascriptExecutor;

JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("return jQuery.active == 0;");
```
✅ **Best For:** Pages using **jQuery AJAX calls**.

---

## **📌 4. Handle `StaleElementReferenceException` (Element Reloading)**
If an AJAX element **reloads dynamically**, re-locate it before performing actions.

### **🔹 Example: Retrying on Stale Element**
```java
for (int i = 0; i < 3; i++) {
    try {
        driver.findElement(By.id("ajaxElement")).click();
        break;
    } catch (StaleElementReferenceException e) {
        System.out.println("Element is stale, retrying...");
    }
}
```
✅ **Best For:** Handling **dynamic elements that refresh on interaction.**  

---

## **📌 5. Wait for Element Text to Change**
If an AJAX request updates an element’s **text dynamically**, wait for the text change.

### **🔹 Example: Wait Until Text Appears**
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("status"), "Loaded"));
```
✅ **Best For:** Waiting for **status messages after AJAX updates.**  

---

## **📌 Summary Table: Handling AJAX Elements**
| **Method** | **Best For** | **Example** |
|------------|-------------|-------------|
| **Explicit Wait (`WebDriverWait`)** ✅ | Waiting for element visibility | `ExpectedConditions.visibilityOfElementLocated()` |
| **Fluent Wait** ✅ | Handling unpredictable AJAX delays | `.pollingEvery(Duration.ofMillis(500))` |
| **JavaScript Executor** | Checking AJAX completion (`jQuery.active`) | `js.executeScript("return jQuery.active == 0;")` |
| **Retry on StaleElementException** | Handling reloading elements | `try { element.click(); } catch (StaleElementReferenceException e) { retry(); }` |
| **Wait for Text Change** | Waiting for dynamic content updates | `ExpectedConditions.textToBePresentInElementLocated()` |

---

# **🔹 Headless Browsers in Selenium** 🚀  

## **📌 What is a Headless Browser?**  
A **headless browser** is a browser that runs **without a GUI (Graphical User Interface)**. It executes web page interactions in the background, making tests **faster and more efficient**.  

✅ **Benefits of Headless Testing:**  
✔ Faster execution (No UI rendering).  
✔ Useful for CI/CD pipelines.  
✔ Runs on **servers, Docker, Jenkins, etc.**  
✔ Saves resources (CPU & memory).  

---

## **📌 1. Using Headless Chrome with Selenium**
### **🔹 Enable Headless Mode in Chrome**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class HeadlessChromeTest {
    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");  // Enable headless mode
        options.addArguments("--disable-gpu");  // Recommended for stability

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://example.com");

        System.out.println("Page Title: " + driver.getTitle());
        driver.quit();
    }
}
```
✅ **Best For:** Fast execution of tests **without opening the browser window**.  

---

## **📌 2. Using Headless Firefox with Selenium**
### **🔹 Enable Headless Mode in Firefox**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class HeadlessFirefoxTest {
    public static void main(String[] args) {
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);  // Enable headless mode

        WebDriver driver = new FirefoxDriver(options);
        driver.get("https://example.com");

        System.out.println("Page Title: " + driver.getTitle());
        driver.quit();
    }
}
```
✅ **Best For:** Running Selenium tests in **headless Firefox mode**.  

---

## **📌 3. Using Headless Edge with Selenium**
### **🔹 Enable Headless Mode in Edge**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class HeadlessEdgeTest {
    public static void main(String[] args) {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless");

        WebDriver driver = new EdgeDriver(options);
        driver.get("https://example.com");

        System.out.println("Page Title: " + driver.getTitle());
        driver.quit();
    }
}
```
✅ **Best For:** Running tests in **Microsoft Edge** without UI.  

---

## **📌 4. Running Headless Selenium Tests in CI/CD (Jenkins, Docker)**
### **🔹 Run Tests in Jenkins (Headless Mode)**
Modify your **TestNG/Maven test script** to include **headless options**.  

```sh
mvn test -Dheadless=true
```
✅ **Best For:** Running Selenium tests in **Jenkins Pipelines**.  

---

## **📌 5. Headless Browsers vs. Regular Browsers**
| **Feature** | **Headless Browser** | **Regular Browser** |
|------------|-----------------|----------------|
| **Execution Speed** | ✅ Faster | ❌ Slower (UI rendering) |
| **Memory Usage** | ✅ Low | ❌ High |
| **Supports Visual Debugging** | ❌ No | ✅ Yes |
| **Best For** | CI/CD, API testing, Automation | Manual Testing, UI Verification |

---

## **📌 Summary**
✔ **Headless Browsers** run Selenium tests **without a UI**, making them **faster & lightweight**.  
✔ Supported in **Chrome, Firefox, Edge**, and works well with **Jenkins & Docker**.  
✔ Best suited for **CI/CD pipelines, server environments, and parallel testing**.  

---

# **🔹 Integrating Selenium with TestNG & JUnit** 🚀  

Selenium works with testing frameworks like **TestNG and JUnit** to manage test execution, assertions, parallel execution, and reporting.

---

# **📌 1. Integrating Selenium with TestNG (Recommended ✅)**  

### **🔹 Step 1: Add TestNG Dependency (Maven)**
```xml
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.8.0</version>
    <scope>test</scope>
</dependency>
```
✅ **Best for:** Parallel execution, annotations, and reporting.

---

### **🔹 Step 2: Create a Selenium Test with TestNG**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGExample {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://example.com");
    }

    @Test
    public void verifyTitle() {
        String title = driver.getTitle();
        Assert.assertEquals(title, "Example Domain");
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
```
✅ **Uses TestNG Annotations:**  
✔ `@BeforeMethod` → Runs before each test.  
✔ `@Test` → Marks the test case.  
✔ `@AfterMethod` → Runs after each test.  

---

### **🔹 Step 3: Execute TestNG Tests with `testng.xml`**
Create **`testng.xml`** for parallel execution.
```xml
<suite name="Test Suite" parallel="methods" thread-count="2">
    <test name="Selenium Test">
        <classes>
            <class name="TestNGExample"/>
        </classes>
    </test>
</suite>
```
Run with:  
```sh
mvn test
```
✅ **Best for:** Running tests in **parallel**.

---

# **📌 2. Integrating Selenium with JUnit**  

### **🔹 Step 1: Add JUnit Dependency (Maven)**
```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>
```
✅ **Best for:** Simple unit testing and older projects.

---

### **🔹 Step 2: Create a Selenium Test with JUnit**
```java
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class JUnitExample {
    WebDriver driver;

    @Before
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://example.com");
    }

    @Test
    public void verifyTitle() {
        Assert.assertEquals("Example Domain", driver.getTitle());
    }

    @After
    public void teardown() {
        driver.quit();
    }
}
```
✅ **Uses JUnit Annotations:**  
✔ `@Before` → Runs before each test.  
✔ `@Test` → Marks the test case.  
✔ `@After` → Runs after each test.  

---

# **📌 TestNG vs. JUnit – Which One to Use?**
| Feature | **TestNG** ✅ (Recommended) | **JUnit** |
|------------|----------------|--------|
| **Parallel Execution** | ✅ Yes | ❌ No (JUnit 4), ✅ Yes (JUnit 5) |
| **Annotations** | `@BeforeMethod`, `@AfterMethod`, `@Test` | `@Before`, `@After`, `@Test` |
| **Dependency Handling** | ✅ Better with Maven | ✅ Good |
| **Report Generation** | ✅ Built-in HTML reports | ❌ Requires external plugins |
| **Best For** | Complex projects with parallel execution | Simple unit testing |

✅ **Use TestNG** for better **test management, reporting, and parallel execution**.  
✅ **Use JUnit** if working on **legacy projects** or **JUnit-based frameworks**.  

---

# **📌 3. Running Selenium Tests in CI/CD Pipelines**
✅ **TestNG & JUnit tests integrate easily with:**  
- **Jenkins** (`mvn test`)  
- **GitHub Actions**  
- **Docker + Selenium Grid**  

---

# **🔹 Running Selenium Tests on Different Browsers (Cross-Browser Testing) 🚀**  

Cross-browser testing ensures that web applications work **consistently across multiple browsers** like **Chrome, Firefox, Edge, and Safari**.  

---

## **📌 1. Using WebDriver for Different Browsers**  
You can specify different browser drivers in Selenium:  

### **🔹 Chrome**
```java
WebDriver driver = new ChromeDriver();
```
### **🔹 Firefox**
```java
WebDriver driver = new FirefoxDriver();
```
### **🔹 Edge**
```java
WebDriver driver = new EdgeDriver();
```
✅ **Best for:** Running tests on **one browser at a time**.

---

## **📌 2. Running Cross-Browser Tests Using TestNG (`@Parameters`)**
You can configure **multiple browsers** in `testng.xml` and pass them to the test script.

### **🔹 Step 1: Create a TestNG Test (`CrossBrowserTest.java`)**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CrossBrowserTest {
    WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setup(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        }
        driver.get("https://example.com");
    }

    @Test
    public void testTitle() {
        System.out.println("Page Title: " + driver.getTitle());
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
```

---

### **🔹 Step 2: Define Browsers in `testng.xml`**
```xml
<suite name="CrossBrowserSuite" parallel="tests" thread-count="3">
    <test name="Chrome Test">
        <parameter name="browser" value="chrome"/>
        <classes>
            <class name="CrossBrowserTest"/>
        </classes>
    </test>

    <test name="Firefox Test">
        <parameter name="browser" value="firefox"/>
        <classes>
            <class name="CrossBrowserTest"/>
        </classes>
    </test>

    <test name="Edge Test">
        <parameter name="browser" value="edge"/>
        <classes>
            <class name="CrossBrowserTest"/>
        </classes>
    </test>
</suite>
```
✅ **Runs tests on multiple browsers in parallel** using **TestNG parameters**.  
Run with:  
```sh
mvn test
```

---

## **📌 3. Running Cross-Browser Tests with Selenium Grid (Distributed Execution)**
Selenium Grid allows running tests on **multiple machines and browsers simultaneously**.

### **🔹 Step 1: Start Selenium Grid Hub**
```sh
java -jar selenium-server-4.0.0.jar hub
```

### **🔹 Step 2: Register Nodes**
```sh
java -jar selenium-server-4.0.0.jar node --port 5555 --browser "chrome"
java -jar selenium-server-4.0.0.jar node --port 5556 --browser "firefox"
```

### **🔹 Step 3: Modify Test Script to Use Selenium Grid**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;

public class GridTest {
    public static void main(String[] args) throws MalformedURLException {
        String hubURL = "http://localhost:4444/wd/hub";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");

        WebDriver driver = new RemoteWebDriver(new URL(hubURL), capabilities);
        driver.get("https://example.com");
        System.out.println("Title: " + driver.getTitle());

        driver.quit();
    }
}
```
✅ **Best for:** Running tests on **multiple machines** (Windows, Mac, Linux).

---

## **📌 4. Running Cross-Browser Tests in Headless Mode (CI/CD, Jenkins)**
Use **headless browsers** for faster execution in CI/CD environments.  

### **🔹 Headless Chrome**
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless");
WebDriver driver = new ChromeDriver(options);
```
### **🔹 Headless Firefox**
```java
FirefoxOptions options = new FirefoxOptions();
options.setHeadless(true);
WebDriver driver = new FirefoxDriver(options);
```
✅ **Best for:** Running tests in **Jenkins, Docker, and CI/CD pipelines**.

---

## **📌 5. Running Cross-Browser Tests with Maven Surefire Plugin**
Modify **`pom.xml`** to run tests in parallel.
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.0.0-M7</version>
    <configuration>
        <parallel>methods</parallel>
        <threadCount>3</threadCount>
    </configuration>
</plugin>
```
Run with:  
```sh
mvn test
```
✅ **Best for:** Running **parallel tests** via Maven.

---

## **📌 Summary Table: Cross-Browser Testing Methods**
| **Method** | **Best For** | **Configuration Needed?** |
|------------|------------|--------------------------|
| **Using Different WebDrivers** | Simple local execution | No |
| **TestNG `@Parameters`** ✅ | Multi-browser execution | Yes (`testng.xml`) |
| **Selenium Grid** ✅ | Distributed execution | Yes (Hub & Nodes) |
| **Headless Browsers** | CI/CD, Jenkins | Yes (`ChromeOptions`) |
| **Maven Surefire Plugin** | Parallel execution | Yes (`pom.xml`) |

---

# **🔹 Managing Cookies in Selenium WebDriver** 🍪🚀  

Selenium allows handling **cookies** to manage **user sessions, authentication, and preferences** during automation testing.  

---

## **📌 1. What are Cookies in Selenium?**
Cookies store **session information** in the browser. Selenium can **add, delete, retrieve, and manage cookies** using the `driver.manage().getCookies()` API.

✅ **Use Cases:**  
✔ Handling **login sessions** without re-entering credentials.  
✔ Testing **remember me functionality**.  
✔ Managing **user preferences** across tests.  

---

## **📌 2. Retrieve All Cookies**
```java
Set<Cookie> cookies = driver.manage().getCookies();
for (Cookie cookie : cookies) {
    System.out.println(cookie.getName() + " : " + cookie.getValue());
}
```
✅ **Best for:** Fetching all cookies from the browser.

---

## **📌 3. Retrieve a Specific Cookie**
```java
Cookie sessionCookie = driver.manage().getCookieNamed("sessionID");
System.out.println("Session Cookie: " + sessionCookie.getValue());
```
✅ **Best for:** Extracting a **specific cookie’s value**.

---

## **📌 4. Add a New Cookie**
```java
Cookie newCookie = new Cookie("user", "testUser123");
driver.manage().addCookie(newCookie);
```
✅ **Best for:** Injecting session cookies to **avoid login screens**.

---

## **📌 5. Delete a Specific Cookie**
```java
driver.manage().deleteCookieNamed("sessionID");
```
✅ **Best for:** Removing **specific cookies** for session cleanup.

---

## **📌 6. Delete All Cookies**
```java
driver.manage().deleteAllCookies();
```
✅ **Best for:** Clearing all cookies **before a new test session**.

---

## **📌 7. Save & Reuse Cookies (Login Persistence)**
### **🔹 Step 1: Save Cookies After Login**
```java
Set<Cookie> cookies = driver.manage().getCookies();
for (Cookie cookie : cookies) {
    System.out.println("Saved Cookie: " + cookie.getName() + " = " + cookie.getValue());
}
```
### **🔹 Step 2: Load Cookies in a New Session**
```java
for (Cookie cookie : cookies) {
    driver.manage().addCookie(cookie);
}
driver.navigate().refresh();  // Refresh page to apply cookies
```
✅ **Best for:** Automating tests that require **session reuse**.

---

## **📌 Summary Table**
| **Action** | **Selenium Code** |
|------------|-----------------|
| **Get All Cookies** | `driver.manage().getCookies();` |
| **Get a Specific Cookie** | `driver.manage().getCookieNamed("sessionID");` |
| **Add a New Cookie** | `driver.manage().addCookie(new Cookie("key", "value"));` |
| **Delete a Cookie** | `driver.manage().deleteCookieNamed("sessionID");` |
| **Delete All Cookies** | `driver.manage().deleteAllCookies();` |
| **Save & Reuse Cookies** | Save & reapply cookies in new sessions |

---

## **📌 8. Handling Cookies in Headless Browsers**
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless");
WebDriver driver = new ChromeDriver(options);
```
✅ **Best for:** Running tests in **Jenkins & CI/CD pipelines**.  

---

# **🔹 Handling Timeouts in Selenium WebDriver** ⏳🚀  

Timeouts in Selenium help manage **page loads, element wait times, and script execution** to prevent failures due to delays.  

---

## **📌 1. Types of Timeouts in Selenium**
| **Timeout Type** | **Description** | **Best Use Case** |
|----------------|----------------|----------------|
| **Implicit Wait** | Waits **globally** for elements to appear before throwing an exception. | For elements that take time to load. |
| **Explicit Wait** ✅ | Waits **until a condition is met** (e.g., element is clickable). | For dynamic elements that load unpredictably. |
| **Fluent Wait** | Like **Explicit Wait**, but with **custom polling frequency**. | For handling intermittent element loads. |
| **Page Load Timeout** | Waits for the entire page to load before throwing an exception. | Prevents long page load times from blocking tests. |
| **Script Timeout** | Waits for JavaScript execution to complete. | When running JavaScript-heavy web apps. |

---

## **📌 2. Setting Up Different Timeouts in Selenium**
### **🔹 (A) Implicit Wait (Global Timeout for Finding Elements)**
```java
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
```
✅ **Best For:** Waiting for elements **globally** across all `findElement()` calls.  
❌ **Not Recommended:** **Cannot handle dynamic conditions** like visibility or clickability.

---

### **🔹 (B) Explicit Wait (Waits Until Condition is Met)**
Use **`WebDriverWait`** for specific conditions like element visibility.

```java
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dynamicElement")));
element.click();
```
✅ **Best For:** Handling **AJAX-based** dynamic elements.  

---

### **🔹 (C) Fluent Wait (Advanced Handling with Custom Polling)**
Waits for an element while **checking at regular intervals**.

```java
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;

FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
        .withTimeout(Duration.ofSeconds(20))  // Max wait time
        .pollingEvery(Duration.ofMillis(500)) // Check every 500ms
        .ignoring(NoSuchElementException.class);

WebElement element = fluentWait.until(ExpectedConditions.elementToBeClickable(By.id("ajaxButton")));
element.click();
```
✅ **Best For:** Handling **intermittently loading** elements.  

---

### **🔹 (D) Page Load Timeout (Wait Until Page is Fully Loaded)**
```java
driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
```
✅ **Best For:** Preventing **long page load delays**.  

---

### **🔹 (E) Script Timeout (Wait for JavaScript Execution to Finish)**
```java
driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(20));
```
✅ **Best For:** Waiting for JavaScript-heavy applications.  

---

## **📌 3. Handling Timeout Exceptions**
| **Exception** | **Reason** | **Solution** |
|--------------|-----------|-------------|
| `NoSuchElementException` | Element not found in time | Use **Explicit Wait** |
| `TimeoutException` | Page/element took too long to load | Increase **timeout duration** |
| `StaleElementReferenceException` | Element reloaded before action | Use **Fluent Wait** |
| `ElementClickInterceptedException` | Element is blocked by overlay | Use **JavaScript Click** |

---

## **📌 Summary Table: Timeout Handling**
| **Timeout Type** | **Selenium Code** | **Use Case** |
|----------------|----------------|----------------|
| **Implicit Wait** | `driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));` | Apply **globally** for element finding. |
| **Explicit Wait** | `wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn")));` | Wait for **specific conditions**. |
| **Fluent Wait** | `fluentWait.until(ExpectedConditions.elementToBeClickable(By.id("btn")));` | **Advanced handling** for unpredictable delays. |
| **Page Load Timeout** | `driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));` | Prevent long **page load waits**. |
| **Script Timeout** | `driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(20));` | Handle **JavaScript execution delays**. |

---

# **🔹 TestNG in Selenium – A Complete Guide 🚀**  

## **📌 What is TestNG?**  
TestNG (**Test Next Generation**) is a **powerful testing framework** in Java that enhances Selenium by providing:  
✅ **Annotations (@Test, @BeforeMethod, etc.)** for structured test execution.  
✅ **Assertions** for validation (`Assert.assertEquals()`).  
✅ **Parallel execution** for faster test runs.  
✅ **Data-driven testing** using `@DataProvider`.  
✅ **Test reports** (HTML/XML).  

---

## **📌 1. Install TestNG in Selenium (Maven)**
Add the following **TestNG dependency** in `pom.xml`:
```xml
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.8.0</version>
    <scope>test</scope>
</dependency>
```
✅ **Best for:** Managing dependencies in **Maven projects**.  

---

## **📌 2. Writing a Selenium Test with TestNG**
### **🔹 Example: Basic Selenium Test using TestNG**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGExample {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://example.com");
    }

    @Test
    public void testTitle() {
        String title = driver.getTitle();
        Assert.assertEquals(title, "Example Domain");
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
```
✅ **TestNG Annotations:**  
✔ `@BeforeMethod` → Runs **before each test**.  
✔ `@Test` → Marks the test method.  
✔ `@AfterMethod` → Runs **after each test** (closes browser).  

---

## **📌 3. Running Tests with `testng.xml`**
Create a **`testng.xml`** file to manage test execution.
```xml
<suite name="Test Suite">
    <test name="Selenium Test">
        <classes>
            <class name="TestNGExample"/>
        </classes>
    </test>
</suite>
```
✅ Run tests with:  
```sh
mvn test
```
✅ **Best for:** Running multiple tests **via XML configuration**.

---

## **📌 4. Parallel Execution in TestNG**
Enable **parallel execution** to run tests **faster**.

### **🔹 Modify `testng.xml` for Parallel Execution**
```xml
<suite name="Parallel Suite" parallel="methods" thread-count="2">
    <test name="Parallel Test">
        <classes>
            <class name="TestNGExample"/>
        </classes>
    </test>
</suite>
```
✅ **Best for:** Running multiple tests **simultaneously**.

---

## **📌 5. Data-Driven Testing using `@DataProvider`**
Run tests **with multiple data sets**.

### **🔹 Example: Login Test with Multiple Users**
```java
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataDrivenTest {
    @DataProvider(name = "loginData")
    public Object[][] getData() {
        return new Object[][]{
                {"user1", "password1"},
                {"user2", "password2"}
        };
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password) {
        System.out.println("Logging in with: " + username + " | " + password);
    }
}
```
✅ **Best for:** Testing **multiple data sets** in a single test.

---

## **📌 6. Generating TestNG Reports**
TestNG automatically generates **detailed HTML reports** after execution.  
📌 **Path:** `test-output/index.html`  

✅ **Best for:** **Analyzing test results**.

---

## **📌 7. TestNG vs JUnit – Why TestNG?**
| **Feature** | **TestNG** ✅ | **JUnit** |
|------------|-------------|-----------|
| **Annotations** | `@Test, @BeforeMethod, @AfterMethod` | `@Test, @Before, @After` |
| **Parallel Execution** | ✅ Yes | ❌ No (JUnit 4), ✅ Yes (JUnit 5) |
| **Data-Driven Testing** | ✅ `@DataProvider` | ❌ Needs external tools |
| **Built-in Reporting** | ✅ Yes | ❌ No |
| **Dependency Handling** | ✅ Yes (`dependsOnMethods`) | ❌ No |

✅ **Use TestNG** for advanced **test execution, reporting, and parallel testing**.

---

## **📌 Summary Table**
| **Feature** | **Selenium Code** |
|------------|-----------------|
| **Basic TestNG Test** | `@Test public void testMethod() {}` |
| **Parallel Execution** | `parallel="methods" thread-count="2"` in `testng.xml` |
| **Data-Driven Testing** | `@DataProvider(name = "data")` |
| **Assertions** | `Assert.assertEquals(actual, expected);` |
| **Reporting** | Auto-generated at `test-output/index.html` |

---

# **🔹 Data-Driven Testing in Selenium using TestNG** 🚀  

Data-driven testing allows **running a test multiple times with different data sets** to validate various inputs and outputs.

✅ **Why Use Data-Driven Testing?**  
✔ Avoids **hardcoding test data**.  
✔ Improves **test coverage**.  
✔ Reduces **manual effort** in writing repetitive tests.  

---

## **📌 1. Using `@DataProvider` (TestNG Built-in Method)**
### **🔹 Step 1: Create a Data-Driven Test with `@DataProvider`**
```java
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataDrivenTest {
    @DataProvider(name = "loginData")
    public Object[][] getData() {
        return new Object[][]{
                {"user1", "password1"},
                {"user2", "password2"},
                {"user3", "password3"}
        };
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password) {
        System.out.println("Logging in with: " + username + " | " + password);
    }
}
```
✅ **Test runs 3 times**, each with different credentials.  
✅ **Best for:** Small test data sets **within the test class**.

---

## **📌 2. Fetch Test Data from an Excel File (`Apache POI`)**
If test data is large, store it in **Excel** and fetch dynamically.

### **🔹 Step 1: Add Apache POI Dependency in `pom.xml`**
```xml
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.2.3</version>
</dependency>
```

### **🔹 Step 2: Create Excel Utility to Read Data**
```java
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {
    public static Object[][] getExcelData(String filePath, String sheetName) throws IOException {
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheet(sheetName);
        int rows = sheet.getPhysicalNumberOfRows();
        int cols = sheet.getRow(0).getPhysicalNumberOfCells();

        Object[][] data = new Object[rows - 1][cols];
        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i - 1][j] = sheet.getRow(i).getCell(j).toString();
            }
        }
        workbook.close();
        return data;
    }
}
```

### **🔹 Step 3: Integrate Excel with `@DataProvider`**
```java
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;

public class ExcelDataDrivenTest {
    @DataProvider(name = "excelData")
    public Object[][] getData() throws IOException {
        return ExcelUtils.getExcelData("testdata.xlsx", "LoginData");
    }

    @Test(dataProvider = "excelData")
    public void loginTest(String username, String password) {
        System.out.println("Logging in with: " + username + " | " + password);
    }
}
```
✅ **Best for:** Large **test datasets stored in Excel**.

---

## **📌 3. Fetch Test Data from a CSV File**
### **🔹 Step 1: Create CSV Utility**
```java
import java.io.*;
import java.util.*;

public class CSVUtils {
    public static List<String[]> getCSVData(String filePath) throws IOException {
        List<String[]> data = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            data.add(line.split(","));
        }
        br.close();
        return data;
    }
}
```

### **🔹 Step 2: Integrate CSV with `@DataProvider`**
```java
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.List;

public class CSVDataDrivenTest {
    @DataProvider(name = "csvData")
    public Object[][] getData() throws IOException {
        List<String[]> data = CSVUtils.getCSVData("testdata.csv");
        return data.toArray(new Object[0][]);
    }

    @Test(dataProvider = "csvData")
    public void loginTest(String username, String password) {
        System.out.println("Logging in with: " + username + " | " + password);
    }
}
```
✅ **Best for:** Simple **comma-separated test data**.

---

## **📌 4. Fetch Test Data from a JSON File**
### **🔹 Step 1: Add JSON Dependency (`Jackson`) in `pom.xml`**
```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.15.3</version>
</dependency>
```

### **🔹 Step 2: Create JSON Utility**
```java
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JSONUtils {
    public static Object[][] getJSONData(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(filePath));
        Object[][] data = new Object[rootNode.size()][2];

        for (int i = 0; i < rootNode.size(); i++) {
            data[i][0] = rootNode.get(i).get("username").asText();
            data[i][1] = rootNode.get(i).get("password").asText();
        }
        return data;
    }
}
```

### **🔹 Step 3: Integrate JSON with `@DataProvider`**
```java
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;

public class JSONDataDrivenTest {
    @DataProvider(name = "jsonData")
    public Object[][] getData() throws IOException {
        return JSONUtils.getJSONData("testdata.json");
    }

    @Test(dataProvider = "jsonData")
    public void loginTest(String username, String password) {
        System.out.println("Logging in with: " + username + " | " + password);
    }
}
```
✅ **Best for:** **API testing** and structured test data.

---

## **📌 Summary Table**
| **Test Data Source** | **Best For** | **Example** |
|----------------|----------------|-------------|
| **`@DataProvider` (Inline)** ✅ | Small test data sets | `return new Object[][] { {"user1", "pass1"} }` |
| **Excel File (`Apache POI`)** ✅ | Large structured datasets | `ExcelUtils.getExcelData("testdata.xlsx", "Sheet1")` |
| **CSV File** | Simple text-based data | `CSVUtils.getCSVData("testdata.csv")` |
| **JSON File (`Jackson`)** | API testing, structured test data | `JSONUtils.getJSONData("testdata.json")` |

---

## **📌 5. Running Data-Driven Tests via TestNG XML**
Modify `testng.xml` for **parallel execution**.
```xml
<suite name="Data-Driven Suite" parallel="methods" thread-count="2">
    <test name="Data Test">
        <classes>
            <class name="ExcelDataDrivenTest"/>
        </classes>
    </test>
</suite>
```
✅ **Best for:** Running **data-driven tests in parallel**.

---

# **🔹 Handling `StaleElementReferenceException` in Selenium WebDriver** 🚀  

## **📌 What is `StaleElementReferenceException`?**
`StaleElementReferenceException` occurs when Selenium tries to interact with an element **that is no longer attached to the DOM (Document Object Model)**.  

✅ **Common Causes:**  
1️⃣ **Page refresh or navigation** (e.g., AJAX updates, dynamic UI changes).  
2️⃣ **Element reloading** after an event (e.g., clicking a button).  
3️⃣ **DOM changes** due to JavaScript execution.

---

## **📌 1. Solution: Re-Locate the Element Before Interaction** ✅  
Since the element reference becomes **stale**, find it **again** before performing any action.

### **🔹 Example: Re-Locate Element Before Clicking**
```java
WebElement button = driver.findElement(By.id("submitBtn"));
button.click();  // Works initially

// Page reloads or AJAX modifies the DOM
button = driver.findElement(By.id("submitBtn"));  // Find the element again
button.click();  // Now works
```
✅ **Best for:** Handling elements that disappear and reappear.  

---

## **📌 2. Solution: Use `try-catch` and Retry Mechanism** ✅  
Retry clicking the element if `StaleElementReferenceException` occurs.

### **🔹 Example: Retrying Click on Stale Element**
```java
for (int i = 0; i < 3; i++) {  // Retry up to 3 times
    try {
        WebElement button = driver.findElement(By.id("submitBtn"));
        button.click();
        break;  // Exit loop if successful
    } catch (StaleElementReferenceException e) {
        System.out.println("Element is stale, retrying...");
    }
}
```
✅ **Best for:** Elements that reload intermittently.  

---

## **📌 3. Solution: Use Explicit Wait (`WebDriverWait`)** ✅  
Wait for the element to become **clickable or visible**.

### **🔹 Example: Wait Until Element is Clickable**
```java
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.id("submitBtn")));
button.click();
```
✅ **Best for:** Handling elements that reload dynamically.  

---

## **📌 4. Solution: Use `FluentWait` for Intermittent DOM Updates** ✅  
FluentWait checks for the element **at regular intervals** until it becomes available.

### **🔹 Example: Handling Stale Element with `FluentWait`**
```java
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;

FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
        .withTimeout(Duration.ofSeconds(20))
        .pollingEvery(Duration.ofMillis(500))
        .ignoring(StaleElementReferenceException.class);

WebElement button = fluentWait.until(ExpectedConditions.elementToBeClickable(By.id("submitBtn")));
button.click();
```
✅ **Best for:** Elements that appear **randomly due to AJAX updates**.

---

## **📌 5. Solution: Refresh the Page and Re-Locate the Element** ✅  
If an element disappears completely, refresh the page and find it again.

### **🔹 Example: Refresh & Retry**
```java
driver.navigate().refresh();  // Refresh the page

WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.id("submitBtn")));
button.click();
```
✅ **Best for:** Elements that reload only after a **full page refresh**.

---

## **📌 Summary Table: Handling `StaleElementReferenceException`**
| **Solution** | **Best For** | **Example Code** |
|-------------|-------------|-----------------|
| **Re-locate Element Before Interaction** ✅ | Dynamic UI updates | `element = driver.findElement(By.id("button"));` |
| **Retry with `try-catch` Block** ✅ | Elements that reload intermittently | `for (int i = 0; i < 3; i++) { try { element.click(); } catch (StaleElementReferenceException e) { retry(); }}` |
| **Use Explicit Wait (`WebDriverWait`)** ✅ | Elements that load unpredictably | `wait.until(ExpectedConditions.elementToBeClickable(By.id("button")));` |
| **Use Fluent Wait for Polling** ✅ | AJAX-based elements appearing randomly | `fluentWait.until(ExpectedConditions.elementToBeClickable(By.id("button")));` |
| **Refresh the Page and Retry** ✅ | Elements that disappear completely | `driver.navigate().refresh();` |

---

# **🔹 Scrolling to a Specific Element Using Selenium WebDriver** 🚀  

Selenium does not have a built-in scroll function, but we can achieve scrolling using **JavaScript Executor** and **Actions class**.

---

## **📌 1. Scroll to an Element Using JavaScript Executor (Recommended ✅)**  
JavaScript’s `scrollIntoView(true)` ensures the element is visible in the viewport.

### **🔹 Example: Scroll to a Specific Element**
```java
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ScrollExample {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");

        WebElement element = driver.findElement(By.id("footer"));  // Target element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);  // Scroll to element

        driver.quit();
    }
}
```
✅ **Best for:** Ensuring an element is visible before interaction.  

---

## **📌 2. Scroll by Pixels (Fixed Position)**
If you know the exact scroll height, use `window.scrollBy(x, y)`.

### **🔹 Example: Scroll Down by 500 Pixels**
```java
js.executeScript("window.scrollBy(0,500);");
```
✅ **Best for:** Scrolling **fixed distances**.

---

## **📌 3. Scroll to the Bottom of the Page**
```java
js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
```
✅ **Best for:** Infinite scrolling pages **(e.g., Twitter, Instagram feeds)**.

---

## **📌 4. Scroll to the Top of the Page**
```java
js.executeScript("window.scrollTo(0, 0);");
```
✅ **Best for:** Resetting scroll position.

---

## **📌 5. Scroll Using Actions Class (For Mouse Wheel Simulation)**
The `Actions` class can simulate **mouse scroll** interactions.

### **🔹 Example: Scroll Using Actions Class**
```java
import org.openqa.selenium.interactions.Actions;

Actions actions = new Actions(driver);
actions.scrollToElement(driver.findElement(By.id("footer"))).perform();
```
✅ **Best for:** Simulating **user-like scrolling**.

---

## **📌 6. Handling Lazy Loading & Infinite Scrolling**
For pages that load content dynamically, scroll in small increments.

### **🔹 Example: Scroll Down Until Element is Found**
```java
while (driver.findElements(By.id("loadMoreButton")).size() == 0) {
    js.executeScript("window.scrollBy(0, 500);");
    Thread.sleep(1000);  // Wait for content to load
}
```
✅ **Best for:** **Lazy-loaded content** (e.g., product lists, infinite scrolling).

---

## **📌 Summary Table**
| **Scroll Type** | **Best For** | **Code** |
|--------------|-------------|-------------|
| **Scroll to Element** ✅ | Ensuring visibility before interaction | `js.executeScript("arguments[0].scrollIntoView(true);", element);` |
| **Scroll Down by Pixels** | Moving down a fixed amount | `js.executeScript("window.scrollBy(0,500);");` |
| **Scroll to Bottom** ✅ | Infinite scrolling pages | `js.executeScript("window.scrollTo(0, document.body.scrollHeight);");` |
| **Scroll to Top** | Resetting scroll position | `js.executeScript("window.scrollTo(0, 0);");` |
| **Scroll Using Actions Class** | Simulating real user scroll | `actions.scrollToElement(element).perform();` |
| **Lazy Loading Handling** ✅ | Loading hidden content dynamically | `while (!element.isDisplayed()) { scrollBy(500); }` |

---

# **🔹 Managing SSL Certificate Issues in Selenium WebDriver** 🔒🚀  

When accessing websites with **invalid SSL certificates**, Selenium may throw **SSL-related security warnings**, preventing further automation. You can bypass or handle these issues using browser-specific capabilities.

---

## **📌 1. Handling SSL Certificates in Chrome (Using `ChromeOptions`)** ✅  
Set the `acceptInsecureCerts` capability to `true` to **bypass SSL errors**.

### **🔹 Example: Ignore SSL Certificate Errors in Chrome**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class HandleSSLCertChrome {
    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);  // Accept insecure certificates

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://self-signed.badssl.com/");

        System.out.println("Page Title: " + driver.getTitle());
        driver.quit();
    }
}
```
✅ **Best for:** Handling SSL certificate issues in **Chrome**.

---

## **📌 2. Handling SSL Certificates in Firefox (Using `FirefoxOptions`)** ✅  
Firefox has a similar capability to **accept insecure certificates**.

### **🔹 Example: Ignore SSL Certificate Errors in Firefox**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class HandleSSLCertFirefox {
    public static void main(String[] args) {
        FirefoxOptions options = new FirefoxOptions();
        options.setAcceptInsecureCerts(true);  // Accept insecure certificates

        WebDriver driver = new FirefoxDriver(options);
        driver.get("https://self-signed.badssl.com/");

        System.out.println("Page Title: " + driver.getTitle());
        driver.quit();
    }
}
```
✅ **Best for:** Handling SSL certificate issues in **Firefox**.

---

## **📌 3. Handling SSL Certificates in Edge (Using `EdgeOptions`)**
For **Microsoft Edge**, use `EdgeOptions` with the same capability.

### **🔹 Example: Ignore SSL Certificate Errors in Edge**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class HandleSSLCertEdge {
    public static void main(String[] args) {
        EdgeOptions options = new EdgeOptions();
        options.setAcceptInsecureCerts(true);  // Accept insecure certificates

        WebDriver driver = new EdgeDriver(options);
        driver.get("https://self-signed.badssl.com/");

        System.out.println("Page Title: " + driver.getTitle());
        driver.quit();
    }
}
```
✅ **Best for:** Handling SSL certificate issues in **Microsoft Edge**.

---

## **📌 4. Handling SSL Certificates in Selenium Grid (Remote WebDriver)**
If using **Selenium Grid**, set the capability for remote execution.

### **🔹 Example: Ignore SSL Certificates in Selenium Grid**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.net.MalformedURLException;
import java.net.URL;

public class HandleSSLCertGrid {
    public static void main(String[] args) throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);  // Accept SSL warnings

        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"), options);
        driver.get("https://self-signed.badssl.com/");
        
        System.out.println("Page Title: " + driver.getTitle());
        driver.quit();
    }
}
```
✅ **Best for:** Running tests on **Selenium Grid**.

---

## **📌 5. Handling SSL Certificates in Headless Mode**
If running **headless tests**, make sure to **set SSL options explicitly**.

### **🔹 Example: Ignore SSL in Headless Chrome**
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless");
options.setAcceptInsecureCerts(true);
WebDriver driver = new ChromeDriver(options);
```
✅ **Best for:** **Jenkins, Docker, and CI/CD pipelines**.

---

## **📌 Summary Table: SSL Handling in Different Browsers**
| **Browser** | **Capability** | **Example Code** |
|------------|---------------|-----------------|
| **Chrome** ✅ | `options.setAcceptInsecureCerts(true);` | `new ChromeDriver(options);` |
| **Firefox** ✅ | `options.setAcceptInsecureCerts(true);` | `new FirefoxDriver(options);` |
| **Edge** ✅ | `options.setAcceptInsecureCerts(true);` | `new EdgeDriver(options);` |
| **Selenium Grid** ✅ | `options.setAcceptInsecureCerts(true);` | `new RemoteWebDriver(new URL("http://localhost:4444"), options);` |
| **Headless Mode** ✅ | `options.addArguments("--headless"); options.setAcceptInsecureCerts(true);` | `new ChromeDriver(options);` |

---

# **🔹 Common Challenges in Selenium Automation & How to Overcome Them** 🚀  

Selenium automation comes with several challenges, including **handling dynamic elements, browser compatibility, synchronization issues, and pop-ups**. Below are the most common challenges and their solutions.

---

## **📌 1. Handling Dynamic Elements (Changing IDs, Classes, XPaths)**
### **🔹 Challenge:**  
- Elements may have **dynamic attributes** that change every time the page loads.  
- Example: IDs like `id="button_1234"` keep changing.

### **🔹 Solution: Use Dynamic XPath or CSS Selectors**
✅ **Using XPath `contains()` to handle dynamic elements**
```java
driver.findElement(By.xpath("//button[contains(@id, 'button_')]")).click();
```
✅ **Using XPath `starts-with()`**
```java
driver.findElement(By.xpath("//input[starts-with(@id, 'input_')]")).sendKeys("test");
```
✅ **Using CSS Selectors for Partial Match**
```java
driver.findElement(By.cssSelector("button[id^='btn_']")).click();
```
✅ **Best for:** Finding elements whose attributes change dynamically.

---

## **📌 2. Handling Synchronization Issues (ElementNotVisibleException)**
### **🔹 Challenge:**  
- Elements take time to load due to **AJAX calls or animations**, leading to failures.

### **🔹 Solution: Use Explicit Wait (`WebDriverWait`)**
✅ **Wait until an element is clickable**
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginBtn")));
element.click();
```
✅ **Best for:** Pages with **dynamic loading**.

---

## **📌 3. Handling StaleElementReferenceException (Element Reloading)**
### **🔹 Challenge:**  
- The element **reloads** due to page refresh or AJAX updates.
- Trying to interact with it causes **`StaleElementReferenceException`**.

### **🔹 Solution: Retry Locating the Element**
✅ **Find the element again before interacting**
```java
for (int i = 0; i < 3; i++) {
    try {
        WebElement element = driver.findElement(By.id("dynamicBtn"));
        element.click();
        break;
    } catch (StaleElementReferenceException e) {
        System.out.println("Element is stale, retrying...");
    }
}
```
✅ **Best for:** **Retrying** when elements reload.

---

## **📌 4. Handling Pop-ups & Alerts**
### **🔹 Challenge:**  
- Pop-ups (JavaScript alerts, authentication pop-ups) interrupt execution.

### **🔹 Solution: Use `Alert` Class to Handle JavaScript Alerts**
✅ **Accept or dismiss an alert**
```java
Alert alert = driver.switchTo().alert();
alert.accept();  // Clicks "OK"
```
✅ **Handle Authentication Pop-ups**
```java
driver.get("https://username:password@website.com");
```
✅ **Best for:** Handling **alerts and pop-ups**.

---

## **📌 5. Handling File Uploads & Downloads**
### **🔹 Challenge:**  
- Selenium **cannot interact with OS file upload dialogs**.

### **🔹 Solution 1: Use `sendKeys()` for File Uploads**
✅ **For `<input type="file">` elements**
```java
driver.findElement(By.id("uploadBtn")).sendKeys("C:\\path\\to\\file.pdf");
```
✅ **Best for:** Standard HTML file upload buttons.

### **🔹 Solution 2: Modify Browser Preferences for File Downloads**
✅ **Set Chrome to Auto-Download Files**
```java
ChromeOptions options = new ChromeOptions();
HashMap<String, Object> prefs = new HashMap<>();
prefs.put("download.default_directory", "C:\\Downloads");
prefs.put("download.prompt_for_download", false);
options.setExperimentalOption("prefs", prefs);
WebDriver driver = new ChromeDriver(options);
```
✅ **Best for:** Automating **file downloads**.

---

## **📌 6. Handling Browser Compatibility (Cross-Browser Testing)**
### **🔹 Challenge:**  
- Tests work in **Chrome but fail in Firefox or Edge** due to UI differences.

### **🔹 Solution: Use Selenium Grid for Cross-Browser Testing**
✅ **Parameterize browser execution in TestNG**
```java
@Parameters("browser")
@BeforeMethod
public void setup(String browser) {
    if (browser.equalsIgnoreCase("chrome")) {
        driver = new ChromeDriver();
    } else if (browser.equalsIgnoreCase("firefox")) {
        driver = new FirefoxDriver();
    }
}
```
✅ **Best for:** Running tests on **multiple browsers**.

---

## **📌 7. Managing Captchas & OTPs**
### **🔹 Challenge:**  
- Captchas and OTPs block automation.

### **🔹 Solution: Bypass Using Temporary Solutions**
✅ **Ask Developers for a Test Mode**  
- Disable Captchas in **test environments**.

✅ **Use Third-Party APIs** (Only when permitted)  
- Services like **2Captcha, DeathByCaptcha** solve captchas automatically.

✅ **Best for:** Avoiding Captcha-related test failures.

---

## **📌 8. Handling SSL Certificate Errors**
### **🔹 Challenge:**  
- Browser shows an **"Insecure Connection"** warning.

### **🔹 Solution: Use `acceptInsecureCerts` in Browser Options**
✅ **Ignore SSL Warnings in Chrome**
```java
ChromeOptions options = new ChromeOptions();
options.setAcceptInsecureCerts(true);
WebDriver driver = new ChromeDriver(options);
```
✅ **Best for:** Sites with **self-signed SSL certificates**.

---

## **📌 9. Running Tests in Headless Mode (CI/CD)**
### **🔹 Challenge:**  
- Tests fail in **Jenkins or Docker** due to missing GUI.

### **🔹 Solution: Use Headless Browser Mode**
✅ **Run Chrome in Headless Mode**
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless");
WebDriver driver = new ChromeDriver(options);
```
✅ **Best for:** Running Selenium tests in **Jenkins, CI/CD, Docker**.

---

## **📌 10. Debugging Test Failures & Generating Reports**
### **🔹 Challenge:**  
- Hard to debug failures without screenshots/logs.

### **🔹 Solution: Capture Screenshots on Failure**
✅ **Take Screenshot When a Test Fails (TestNG)**
```java
@AfterMethod
public void takeScreenshotOnFailure(ITestResult result) {
    if (ITestResult.FAILURE == result.getStatus()) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File("screenshots/" + result.getName() + ".png"));
    }
}
```
✅ **Best for:** Debugging **failed test cases**.

---

## **📌 Summary Table: Selenium Challenges & Solutions**
| **Challenge** | **Solution** | **Best Practice** |
|-------------|-------------|----------------|
| **Dynamic Elements** | Use **XPath contains()**, `starts-with()` | `By.xpath("//button[contains(@id, 'btn_')]")` |
| **Synchronization Issues** | Use **Explicit Wait** | `WebDriverWait.until(ExpectedConditions.elementToBeClickable())` |
| **Stale Elements** | Re-locate element, use retry | Catch `StaleElementReferenceException` and retry |
| **Pop-ups & Alerts** | Use **Alert Class** | `driver.switchTo().alert().accept();` |
| **File Uploads** | Use `sendKeys()` | `uploadElement.sendKeys("C:\\file.pdf");` |
| **Cross-Browser Issues** | Use **Selenium Grid** | Run tests in **Firefox, Edge, Chrome** |
| **Captchas & OTPs** | Disable Captcha in test env | Use **mock APIs** for OTPs |
| **SSL Errors** | Use `acceptInsecureCerts` | `options.setAcceptInsecureCerts(true);` |
| **Headless Testing** | Use **Headless Mode** in CI/CD | `options.addArguments("--headless");` |
| **Debugging Failures** | Take **screenshots** on failure | `TakesScreenshot` for logs |

---

# **🔹 Simulating Browser Back, Forward, and Refresh in Selenium WebDriver** 🔄🚀  

Selenium WebDriver allows **simulating browser navigation actions** like **Back, Forward, and Refresh** using the `navigate()` method.

---

## **📌 1. Using `navigate().back()` (Simulate Browser Back Button)**
Navigates to the **previous page** in the browser history.

### **🔹 Example: Navigate Back**
```java
WebDriver driver = new ChromeDriver();
driver.get("https://google.com");  // Open Google
driver.get("https://example.com");  // Open Example.com

driver.navigate().back();  // Go back to Google
```
✅ **Best for:** Testing **back button behavior** in navigation flows.

---

## **📌 2. Using `navigate().forward()` (Simulate Browser Forward Button)**
Navigates to the **next page** in the browser history (if available).

### **🔹 Example: Navigate Forward**
```java
driver.navigate().back();  // Go back to Google
driver.navigate().forward();  // Go forward to Example.com
```
✅ **Best for:** Testing **forward navigation**.

---

## **📌 3. Using `navigate().refresh()` (Simulate Browser Refresh Button)**
Reloads the current webpage.

### **🔹 Example: Refresh the Page**
```java
driver.navigate().refresh();
```
✅ **Best for:** **Refreshing dynamically loaded pages** or handling session timeouts.

---

## **📌 4. Alternative: Refresh Using JavaScript Executor**
If `navigate().refresh()` doesn’t work, force refresh using JavaScript.

### **🔹 Example: Refresh with JavaScript**
```java
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("location.reload();");
```
✅ **Best for:** **Forcing page reload** when standard methods fail.

---

## **📌 5. Handling Page Load Delays After Navigation**
If the page loads slowly after navigating, use **Explicit Wait**.

### **🔹 Example: Wait Until Page is Fully Loaded**
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(ExpectedConditions.presenceOfElementLocated(By.id("elementOnNewPage")));
```
✅ **Best for:** **Ensuring the page is fully loaded** before interacting.

---

## **📌 Summary Table**
| **Action** | **Method** | **Alternative** |
|------------|-----------|----------------|
| **Back** | `driver.navigate().back();` | - |
| **Forward** | `driver.navigate().forward();` | - |
| **Refresh** | `driver.navigate().refresh();` | `js.executeScript("location.reload();");` |

---

# **🔹 Automating Browser Navigation in Selenium WebDriver** 🌍🔄🚀  

Selenium WebDriver provides the `navigate()` method to automate browser navigation actions like **opening URLs, going back, forward, and refreshing pages**.

---

## **📌 1. Open a URL Using `get()` or `navigate().to()`**
### **🔹 Example: Open a Webpage**
```java
WebDriver driver = new ChromeDriver();
driver.get("https://example.com");  // Open a webpage
```
✅ **Best for:** Opening a webpage.  

Alternatively, use `navigate().to()`:
```java
driver.navigate().to("https://example.com");
```
✅ **Both `get()` and `navigate().to()` work the same way**.  

---

## **📌 2. Navigate Back to the Previous Page (`navigate().back()`)**
Simulates clicking the **browser back button**.

### **🔹 Example: Navigate Back**
```java
driver.get("https://google.com");  // Open Google
driver.get("https://example.com");  // Open Example.com

driver.navigate().back();  // Go back to Google
```
✅ **Best for:** Testing **back button behavior**.

---

## **📌 3. Navigate Forward to the Next Page (`navigate().forward()`)**
Simulates clicking the **browser forward button**.

### **🔹 Example: Navigate Forward**
```java
driver.navigate().back();  // Go back to Google
driver.navigate().forward();  // Go forward to Example.com
```
✅ **Best for:** Testing **forward navigation**.

---

## **📌 4. Refresh the Current Page (`navigate().refresh()`)**
Simulates clicking the **browser refresh button**.

### **🔹 Example: Refresh the Page**
```java
driver.navigate().refresh();
```
✅ **Best for:** Refreshing **dynamic pages or handling session timeouts**.

---

## **📌 5. Alternative: Refresh Using JavaScript Executor**
If `navigate().refresh()` doesn’t work, use JavaScript.

### **🔹 Example: Refresh with JavaScript**
```java
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("location.reload();");
```
✅ **Best for:** **Forcing page reload** when standard methods fail.

---

## **📌 6. Handling Page Load Delays After Navigation**
If the page takes time to load, use **Explicit Wait**.

### **🔹 Example: Wait Until Page is Fully Loaded**
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(ExpectedConditions.presenceOfElementLocated(By.id("newPageElement")));
```
✅ **Best for:** Ensuring the **page is fully loaded** before interacting.

---

## **📌 Summary Table**
| **Action** | **Method** | **Alternative** |
|------------|-----------|----------------|
| **Open URL** | `driver.get("https://example.com");` | `driver.navigate().to("https://example.com");` |
| **Back** | `driver.navigate().back();` | - |
| **Forward** | `driver.navigate().forward();` | - |
| **Refresh** | `driver.navigate().refresh();` | `js.executeScript("location.reload();");` |

---

# **🔹 How to Verify Tooltips in Selenium WebDriver** 🏷️🚀  

A **tooltip** is a small text message that appears when you hover over an element. Tooltips are commonly implemented using **HTML attributes (`title`), JavaScript, or CSS tooltips**.

---

## **📌 1. Verifying Tooltips with the `title` Attribute (Simple Method ✅)**  
Some tooltips are stored in the **`title` attribute** of an element.

### **🔹 Example: Fetching Tooltip from `title` Attribute**
```java
WebElement tooltipElement = driver.findElement(By.id("tooltipButton"));
String tooltipText = tooltipElement.getAttribute("title");  // Get tooltip text
System.out.println("Tooltip Text: " + tooltipText);

// Verify the tooltip text
Assert.assertEquals(tooltipText, "Expected Tooltip Message");
```
✅ **Best for:** **Static tooltips** with `title` attributes.

---

## **📌 2. Verifying Tooltips Displayed on Hover (Using Actions Class)**
Some tooltips **appear only on hover** (CSS or JavaScript-based tooltips).

### **🔹 Example: Hover Over an Element and Capture Tooltip**
```java
import org.openqa.selenium.interactions.Actions;

WebElement tooltipElement = driver.findElement(By.id("hoverButton"));

Actions actions = new Actions(driver);
actions.moveToElement(tooltipElement).perform();  // Hover over the element

// Capture the tooltip that appears
WebElement tooltipTextElement = driver.findElement(By.xpath("//div[@class='tooltip-text']"));
String tooltipText = tooltipTextElement.getText();
System.out.println("Tooltip Text: " + tooltipText);

// Verify tooltip text
Assert.assertEquals(tooltipText, "Expected Tooltip Message");
```
✅ **Best for:** Tooltips that **appear dynamically on hover**.

---

## **📌 3. Verifying JavaScript Tooltips (Using JavaScript Executor)**
If Selenium cannot interact with the tooltip, use **JavaScript Executor**.

### **🔹 Example: Fetch Tooltip Using JavaScript**
```java
JavascriptExecutor js = (JavascriptExecutor) driver;
String tooltipText = (String) js.executeScript("return document.getElementById('tooltipButton').title;");
System.out.println("Tooltip Text: " + tooltipText);
```
✅ **Best for:** **JavaScript-based tooltips** that don’t appear in the DOM immediately.

---

## **📌 4. Handling Delayed Tooltips (Using Explicit Wait)**
If the tooltip takes time to appear, use **Explicit Wait**.

### **🔹 Example: Wait Until Tooltip Appears**
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement tooltipTextElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("tooltip-text")));
String tooltipText = tooltipTextElement.getText();
```
✅ **Best for:** **Slow-loading tooltips**.

---

## **📌 Summary Table**
| **Tooltip Type** | **Best Method** | **Example Code** |
|-----------------|----------------|------------------|
| **Tooltip in `title` attribute** ✅ | `getAttribute("title")` | `tooltipElement.getAttribute("title");` |
| **Tooltip appears on hover** ✅ | Actions Class | `actions.moveToElement(tooltipElement).perform();` |
| **JavaScript-based tooltip** | JavaScript Executor | `js.executeScript("return document.getElementById('tooltip').title;");` |
| **Tooltip appears after delay** | Explicit Wait | `wait.until(ExpectedConditions.visibilityOfElementLocated());` |

---

# **🔹 How to Wait for an Element to be Visible in Selenium WebDriver** ⏳🚀  

In Selenium WebDriver, waiting for an element to become visible is **essential to avoid `NoSuchElementException` or `ElementNotVisibleException` errors**.  

✅ **Best Practice:** Always use **Explicit Wait** (`WebDriverWait`) instead of **Implicit Wait** for better control.

---

## **📌 1. Using Explicit Wait (`WebDriverWait`) – Recommended ✅**
Explicit Wait waits **until a specific condition is met** (e.g., visibility, clickability).

### **🔹 Example: Wait Until Element is Visible**
```java
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myElement")));
element.click();
```
✅ **Best for:** Waiting for elements **that take time to load dynamically**.

---

## **📌 2. Using Fluent Wait (Advanced Waiting with Polling)**
Fluent Wait **retries at regular intervals** until the element appears.

### **🔹 Example: Wait Until Element is Visible with Custom Polling**
```java
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;
import java.util.NoSuchElementException;
import org.openqa.selenium.support.ui.Wait;

Wait<WebDriver> fluentWait = new FluentWait<>(driver)
        .withTimeout(Duration.ofSeconds(20))  // Max wait time
        .pollingEvery(Duration.ofMillis(500)) // Check every 500ms
        .ignoring(NoSuchElementException.class);

WebElement element = fluentWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myElement")));
element.click();
```
✅ **Best for:** Handling **slow-loading AJAX elements**.

---

## **📌 3. Using Implicit Wait (Not Recommended for Dynamic Elements)**
Implicit Wait **applies globally** and waits for an element **before throwing an exception**.

### **🔹 Example: Set Implicit Wait**
```java
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
WebElement element = driver.findElement(By.id("myElement"));
```
❌ **Not recommended** because it **applies to all elements** and **cannot wait for specific conditions**.

---

## **📌 4. Waiting Until Element is Clickable**
If an element **becomes visible but is not clickable**, use `elementToBeClickable()`.

### **🔹 Example: Wait Until Element is Clickable**
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("myButton")));
element.click();
```
✅ **Best for:** Buttons or links that **become clickable after an action**.

---

## **📌 5. Waiting Until Text Appears in an Element**
If an element is visible but its **text changes dynamically**, use `textToBePresentInElement()`.

### **🔹 Example: Wait Until Text is Present**
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("statusMessage"), "Success"));
```
✅ **Best for:** **Waiting for status messages** to update.

---

## **📌 Summary Table**
| **Scenario** | **Best Method** | **Example Code** |
|-------------|----------------|------------------|
| **Wait until element is visible** ✅ | `visibilityOfElementLocated()` | `wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myElement")));` |
| **Wait until element is clickable** ✅ | `elementToBeClickable()` | `wait.until(ExpectedConditions.elementToBeClickable(By.id("myButton")));` |
| **Wait for text to appear** | `textToBePresentInElementLocated()` | `wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("status"), "Success"));` |
| **Wait with polling** | Fluent Wait | `new FluentWait<>(driver).withTimeout(...).pollingEvery(...).until(...);` |
| **Set global wait (not recommended for dynamic elements)** | Implicit Wait | `driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));` |

---

# **🔹 How to Check Broken Links in a Webpage Using Selenium WebDriver** 🔗🚀  

A **broken link** is a hyperlink that returns a **404 (Not Found)** or other error responses when accessed. Selenium itself **cannot validate HTTP response codes**, so we integrate it with **Java's `HttpURLConnection`** to check the status of each link.

---

## **📌 1. Steps to Check for Broken Links**
✅ **Find all links (`<a>` tags) on the webpage.**  
✅ **Extract the `href` attribute.**  
✅ **Send an HTTP request to the URL using `HttpURLConnection`.**  
✅ **Verify the HTTP response code.**  

---

## **📌 2. Selenium Script to Find and Check Broken Links**
```java
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BrokenLinksChecker {
    public static void main(String[] args) throws IOException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");  // Change to the target URL

        // Get all links on the webpage
        List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("Total Links Found: " + links.size());

        for (WebElement link : links) {
            String url = link.getAttribute("href");  // Get the link's URL

            // Check if URL is valid
            if (url != null && !url.isEmpty()) {
                checkBrokenLink(url);  // Call method to verify the link
            }
        }
        
        driver.quit();
    }

    // Method to check if a link is broken
    public static void checkBrokenLink(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            int responseCode = connection.getResponseCode();

            if (responseCode >= 400) {
                System.out.println(url + " is BROKEN. Response Code: " + responseCode);
            } else {
                System.out.println(url + " is VALID. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            System.out.println(url + " is BROKEN. Error: " + e.getMessage());
        }
    }
}
```
✅ **Best for:** **Detecting broken links (404, 500 errors, etc.) on a webpage**.  

---

## **📌 3. How This Works**
✔ **Step 1:** Find all `<a>` elements on the page.  
✔ **Step 2:** Extract the `href` attribute.  
✔ **Step 3:** Open a connection using `HttpURLConnection`.  
✔ **Step 4:** Send an HTTP request (`HEAD` method).  
✔ **Step 5:** If the response code is `400+`, it's a **broken link**.  

---

## **📌 4. Handling Edge Cases**
🔹 **Some links may be empty or null** → Skip them.  
🔹 **Some links may have JavaScript actions (`javascript:void(0);`)** → Ignore them.  
🔹 **Some links may redirect (301, 302)** → Handle redirections if necessary.  

---

## **📌 Summary Table**
| **Scenario** | **Solution** | **Example Code** |
|-------------|-------------|------------------|
| **Find all links on a page** ✅ | `driver.findElements(By.tagName("a"))` | `List<WebElement> links = driver.findElements(By.tagName("a"));` |
| **Check if a link is broken** ✅ | Use `HttpURLConnection` | `int responseCode = connection.getResponseCode();` |
| **Skip empty or JavaScript links** ✅ | Check if `href` is null or `javascript:void(0);` | `if (url != null && !url.isEmpty())` |
| **Handle redirects (301, 302)** | Follow redirections | `connection.setInstanceFollowRedirects(true);` |

---

# **🔹 Best Practices for Writing Selenium Automation Scripts** 🚀  

To create **efficient, maintainable, and robust Selenium automation scripts**, follow these best practices:

---

## **📌 1. Use the Page Object Model (POM) for Better Maintainability**  
The **Page Object Model (POM)** improves test maintainability by separating **test logic from UI elements**.

### **🔹 Example: POM Implementation**
#### **🔹 Page Class (`LoginPage.java`)**
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    WebDriver driver;

    @FindBy(id = "username") 
    WebElement username;

    @FindBy(id = "password") 
    WebElement password;

    @FindBy(id = "loginBtn") 
    WebElement loginBtn;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String user, String pass) {
        username.sendKeys(user);
        password.sendKeys(pass);
        loginBtn.click();
    }
}
```
#### **🔹 Test Class (`LoginTest.java`)**
```java
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test
    public void verifyLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("admin", "password123");
    }
}
```
✅ **Why Use POM?**  
✔ **Reduces code duplication**  
✔ **Improves maintainability**  
✔ **Enhances readability**

---

## **📌 2. Use Explicit Waits Instead of Thread.sleep()**  
`Thread.sleep()` **slows down tests unnecessarily**, whereas **Explicit Waits** wait **only until** the element is ready.

### **🔹 Example: Explicit Wait**
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginBtn")));
element.click();
```
✅ **Why?**  
✔ **Avoids unnecessary delays**  
✔ **Handles dynamic elements efficiently**  

---

## **📌 3. Use Data-Driven Testing for Multiple Test Scenarios**  
Avoid **hardcoding test data**. Use **TestNG DataProvider, Excel, CSV, or JSON**.

### **🔹 Example: Using TestNG `@DataProvider`**
```java
import org.testng.annotations.DataProvider;

public class TestData {
    @DataProvider(name = "loginData")
    public Object[][] getData() {
        return new Object[][]{
                {"user1", "pass1"},
                {"user2", "pass2"},
                {"admin", "admin123"}
        };
    }
}
```
✅ **Why?**  
✔ **Easy to run tests with different data sets**  
✔ **Improves reusability**  

---

## **📌 4. Implement Logging for Better Debugging**  
Use **Log4j** or **TestNG Reporter** instead of **System.out.println()**.

### **🔹 Example: Using Log4j**
```java
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestLogger {
    private static final Logger logger = LogManager.getLogger(TestLogger.class);

    public static void main(String[] args) {
        logger.info("Test Started");
        logger.error("Test Failed");
    }
}
```
✅ **Why?**  
✔ **Helps in debugging test failures**  
✔ **Maintains structured logs**  

---

## **📌 5. Capture Screenshots on Test Failure**  
Take screenshots **when a test fails** for debugging.

### **🔹 Example: Screenshot Capture in TestNG**
```java
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import java.io.File;
import java.io.IOException;

@AfterMethod
public void captureScreenshotOnFailure(ITestResult result) {
    if (ITestResult.FAILURE == result.getStatus()) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("screenshots/" + result.getName() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```
✅ **Why?**  
✔ **Helps analyze failed test cases**  

---

## **📌 6. Run Tests in Parallel for Faster Execution**  
Use **TestNG parallel execution** to **reduce test execution time**.

### **🔹 Example: Parallel Execution in `testng.xml`**
```xml
<suite name="Parallel Suite" parallel="methods" thread-count="3">
    <test name="TestNG Parallel Execution">
        <classes>
            <class name="tests.LoginTest"/>
            <class name="tests.SearchTest"/>
        </classes>
    </test>
</suite>
```
✅ **Why?**  
✔ **Saves execution time**  
✔ **Improves test efficiency**  

---

## **📌 7. Use Headless Browsers for CI/CD Integration**  
Use **headless mode** for running tests in **Jenkins, Docker, and CI/CD pipelines**.

### **🔹 Example: Running Chrome in Headless Mode**
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless");
WebDriver driver = new ChromeDriver(options);
```
✅ **Why?**  
✔ **Faster execution**  
✔ **Ideal for Jenkins, CI/CD pipelines**  

---

## **📌 8. Use Assertions for Test Validation**  
Use **TestNG Assertions** instead of conditional checks.

### **🔹 Example: Verifying Page Title**
```java
import org.testng.Assert;

String actualTitle = driver.getTitle();
Assert.assertEquals(actualTitle, "Expected Page Title", "Title Mismatch!");
```
✅ **Why?**  
✔ **Ensures proper test validation**  

---

## **📌 9. Avoid Hardcoded Waits – Use Dynamic Locators**  
Instead of **hardcoding XPath**, use **dynamic strategies**.

### **🔹 Example: Using XPath with `contains()`**
```java
driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();
```
✅ **Why?**  
✔ **Handles dynamic elements better**  

---

## **📌 10. Integrate Selenium with CI/CD Pipelines**  
Run tests **automatically** in **Jenkins, GitHub Actions, or Docker**.

### **🔹 Example: Running Tests in Jenkins Pipeline**
```sh
mvn clean test
```
✅ **Why?**  
✔ **Ensures automation in CI/CD workflow**  

---

## **📌 Summary Table**
| **Best Practice** | **Why?** | **Example** |
|------------------|---------|------------|
| **Use Page Object Model (POM)** ✅ | Improves maintainability | `PageFactory.initElements(driver, this);` |
| **Use Explicit Waits** ✅ | Avoids `NoSuchElementException` | `wait.until(ExpectedConditions.visibilityOfElementLocated());` |
| **Data-Driven Testing** ✅ | Runs multiple test cases | `@DataProvider(name = "testData")` |
| **Logging (Log4j)** ✅ | Better debugging | `logger.info("Test Started");` |
| **Capture Screenshots on Failure** ✅ | Debugging failed tests | `((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);` |
| **Parallel Execution** ✅ | Faster test execution | `parallel="methods" thread-count="3"` |
| **Use Headless Browsers in CI/CD** ✅ | Run tests in Jenkins | `options.addArguments("--headless");` |
| **Use Assertions** ✅ | Ensure validation | `Assert.assertEquals(actual, expected);` |
| **Use Dynamic XPath** ✅ | Handle changing elements | `//button[contains(text(),'Login')]` |
| **Integrate with Jenkins/Docker** ✅ | Automate test execution | `mvn clean test` |

---

# **🔹 WebDriverWait in Selenium – A Complete Guide** ⏳🚀  

## **📌 What is `WebDriverWait`?**  
`WebDriverWait` is an **Explicit Wait** in Selenium that **waits for a specific condition** to be met **before performing actions** on elements.  

✅ **Why Use WebDriverWait?**  
✔ Handles **dynamic elements that take time to load**  
✔ Prevents `NoSuchElementException`, `ElementNotVisibleException`  
✔ Avoids **hardcoded delays (`Thread.sleep()`)**  

---

## **📌 1. How to Use `WebDriverWait` in Selenium**
### **🔹 Example: Wait Until an Element is Visible**
```java
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myElement")));
element.click();
```
✅ **Best for:** Handling **slow-loading elements dynamically**.

---

## **📌 2. Common Expected Conditions in `WebDriverWait`**
| **Condition** | **Method** | **Use Case** |
|-------------|------------|-------------|
| **Element is Visible** ✅ | `visibilityOfElementLocated(By.id("element"))` | Waits until an element appears on the page. |
| **Element is Clickable** ✅ | `elementToBeClickable(By.id("button"))` | Waits until a button or link is clickable. |
| **Element Exists in DOM** | `presenceOfElementLocated(By.xpath("//input"))` | Waits until the element exists (but may not be visible). |
| **Text is Present in Element** | `textToBePresentInElementLocated(By.id("msg"), "Success")` | Waits until a specific text appears inside an element. |
| **Title Contains Text** | `titleContains("Dashboard")` | Waits until the page title contains a keyword. |
| **Alert is Present** | `alertIsPresent()` | Waits for a JavaScript alert to appear. |

---

## **📌 3. Waiting Until an Element is Clickable**
If an element is **visible but not clickable**, use `elementToBeClickable()`.

### **🔹 Example: Wait Until a Button is Clickable**
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginButton")));
button.click();
```
✅ **Best for:** Waiting until **buttons or links** become clickable.

---

## **📌 4. Waiting for an Alert to Appear**
### **🔹 Example: Handle JavaScript Alert**
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
Alert alert = wait.until(ExpectedConditions.alertIsPresent());
alert.accept();  // Click OK on the alert
```
✅ **Best for:** Handling **pop-ups and JavaScript alerts**.

---

## **📌 5. Waiting Until Text Appears in an Element**
### **🔹 Example: Wait for Status Message**
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("statusMessage"), "Order Placed"));
```
✅ **Best for:** Waiting for **dynamic text updates** (e.g., confirmation messages).

---

## **📌 6. Using `FluentWait` for Custom Polling**
If `WebDriverWait` **checks too frequently**, use `FluentWait` to **poll at custom intervals**.

### **🔹 Example: Fluent Wait with Polling**
```java
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;

FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
        .withTimeout(Duration.ofSeconds(20))  // Max wait time
        .pollingEvery(Duration.ofMillis(500)) // Check every 500ms
        .ignoring(NoSuchElementException.class);

WebElement element = fluentWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dynamicElement")));
element.click();
```
✅ **Best for:** Handling **slow-loading AJAX elements**.

---

## **📌 7. Handling Timeout Exceptions**
If an element **does not appear within the wait time**, Selenium throws a `TimeoutException`.

### **🔹 Example: Handle Timeout Exception Gracefully**
```java
try {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nonExistentElement")));
} catch (TimeoutException e) {
    System.out.println("Element not found within the specified timeout!");
}
```
✅ **Best for:** Handling **unexpected delays gracefully**.

---

## **📌 Summary Table: `WebDriverWait` Best Practices**
| **Scenario** | **Best Method** | **Example** |
|-------------|----------------|-------------|
| **Wait for an element to be visible** ✅ | `visibilityOfElementLocated()` | `wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("element")));` |
| **Wait for an element to be clickable** ✅ | `elementToBeClickable()` | `wait.until(ExpectedConditions.elementToBeClickable(By.id("button")));` |
| **Wait for text to appear** | `textToBePresentInElementLocated()` | `wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("msg"), "Success"));` |
| **Wait for a page title** | `titleContains()` | `wait.until(ExpectedConditions.titleContains("Dashboard"));` |
| **Wait for an alert** | `alertIsPresent()` | `wait.until(ExpectedConditions.alertIsPresent());` |
| **Use Fluent Wait (custom polling)** | `FluentWait` | `fluentWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("element")));` |

---

# **🔹 Handling JavaScript Popups in Selenium WebDriver** 🚀  

JavaScript popups (alerts, confirmations, prompts) **interrupt test execution**, so Selenium provides the `Alert` interface to handle them.

---

## **📌 1. Types of JavaScript Popups in Selenium**
| **Popup Type** | **Example** | **How to Handle?** |
|--------------|-----------|----------------|
| **Alert** ✅ | `alert("This is an alert!")` | Click **OK** |
| **Confirmation Box** ✅ | `confirm("Are you sure?")` | Click **OK or Cancel** |
| **Prompt Box** ✅ | `prompt("Enter your name:")` | Enter text and click **OK or Cancel** |

---

## **📌 2. Handling JavaScript Alerts (`alert()`)** ✅  
An alert is a simple pop-up **with only an OK button**.

### **🔹 Example: Accept an Alert**
```java
import org.openqa.selenium.Alert;

Alert alert = driver.switchTo().alert();  // Switch to alert
System.out.println("Alert Message: " + alert.getText());  // Get alert text
alert.accept();  // Click OK
```
✅ **Best for:** **Dismissing popups that require only "OK".**  

---

## **📌 3. Handling JavaScript Confirmation Box (`confirm()`)** ✅  
A confirmation box has **OK and Cancel** buttons.

### **🔹 Example: Accept (OK) or Dismiss (Cancel)**
```java
Alert alert = driver.switchTo().alert();
System.out.println("Confirmation Message: " + alert.getText());

// Accept (Click OK)
alert.accept();

// OR Dismiss (Click Cancel)
// alert.dismiss();
```
✅ **Best for:** **Handling popups with user choices (OK/Cancel).**  

---

## **📌 4. Handling JavaScript Prompt (`prompt()`)** ✅  
A prompt asks for **text input** and has **OK and Cancel** buttons.

### **🔹 Example: Enter Text in a Prompt**
```java
Alert alert = driver.switchTo().alert();
System.out.println("Prompt Message: " + alert.getText());

alert.sendKeys("Selenium User");  // Enter text
alert.accept();  // Click OK
```
✅ **Best for:** **Handling input popups.**  

---

## **📌 5. Handling Alerts with `WebDriverWait` (If Alert Appears After Some Time)**
If an alert **does not appear immediately**, use `WebDriverWait`.

### **🔹 Example: Wait for Alert to Appear**
```java
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
Alert alert = wait.until(ExpectedConditions.alertIsPresent());
alert.accept();
```
✅ **Best for:** **Handling alerts that appear after AJAX calls.**  

---

## **📌 6. Handling Alerts Using JavaScript Executor (If Selenium Fails to Detect It)**
If an alert is **not detected** by Selenium, trigger it using JavaScript.

### **🔹 Example: Manually Trigger an Alert**
```java
import org.openqa.selenium.JavascriptExecutor;

JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("alert('This is a test alert');");
```
✅ **Best for:** **Debugging & testing alert handling manually.**  

---

## **📌 7. Handling Authentication Popups (`Basic Auth`)**
If the browser shows an authentication popup (**Username & Password** dialog), use the **URL trick**.

### **🔹 Example: Bypass Authentication Using URL**
```java
driver.get("https://username:password@website.com");
```
✅ **Best for:** **Bypassing basic authentication popups.**  

---

## **📌 Summary Table: JavaScript Popup Handling**
| **Popup Type** | **Method** | **Example Code** |
|--------------|-----------|------------------|
| **Alert (OK only)** ✅ | `alert.accept();` | `driver.switchTo().alert().accept();` |
| **Confirm (OK & Cancel)** ✅ | `alert.accept();` or `alert.dismiss();` | `driver.switchTo().alert().dismiss();` |
| **Prompt (Text Input, OK & Cancel)** ✅ | `alert.sendKeys("Text"); alert.accept();` | `driver.switchTo().alert().sendKeys("Text");` |
| **Wait for Alert** ✅ | `WebDriverWait` | `wait.until(ExpectedConditions.alertIsPresent());` |
| **Trigger Alert with JS** | `JavascriptExecutor` | `js.executeScript("alert('Test Alert');");` |
| **Handle Authentication Popup** | Use **username:password in URL** | `driver.get("https://username:password@site.com");` |

---

# **🔹 Handling Browser Window Resizing in Selenium WebDriver** 🖥️🔄🚀  

Selenium provides multiple ways to **resize, maximize, and minimize browser windows** during automation.

---

## **📌 1. Maximize Browser Window (`maximize()`)**
### **🔹 Example: Open Browser in Full Screen**
```java
driver.manage().window().maximize();
```
✅ **Best for:** Ensuring elements **fit within the viewport**.

---

## **📌 2. Minimize Browser Window (`minimize()`)**
### **🔹 Example: Minimize Browser**
```java
driver.manage().window().minimize();
```
✅ **Best for:** Running tests **in the background**.

---

## **📌 3. Set a Custom Browser Size (`setSize()`)**
To test responsiveness, **set the browser to a specific width and height**.

### **🔹 Example: Resize Browser to 1024x768**
```java
import org.openqa.selenium.Dimension;

driver.manage().window().setSize(new Dimension(1024, 768));
```
✅ **Best for:** Testing **mobile and tablet viewport sizes**.

---

## **📌 4. Run Tests in Full-Screen Mode (`fullscreen()`)**
Some apps behave differently in **full-screen vs maximized mode**.

### **🔹 Example: Full-Screen Mode**
```java
driver.manage().window().fullscreen();
```
✅ **Best for:** Applications that **require full-screen mode**.

---

## **📌 5. Get the Current Window Size (`getSize()`)**
To verify the browser dimensions **after resizing**.

### **🔹 Example: Print Window Size**
```java
Dimension size = driver.manage().window().getSize();
System.out.println("Width: " + size.getWidth() + ", Height: " + size.getHeight());
```
✅ **Best for:** **Debugging viewport changes**.

---

## **📌 6. Resize Window Using JavaScript Executor (Alternative)**
If `setSize()` does not work, use **JavaScript**.

### **🔹 Example: Resize Using JavaScript**
```java
import org.openqa.selenium.JavascriptExecutor;

JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("window.resizeTo(1024, 768);");
```
✅ **Best for:** Forcing **window resizing when WebDriver fails**.

---

## **📌 Summary Table**
| **Action** | **Method** | **Example Code** |
|------------|-----------|------------------|
| **Maximize Window** ✅ | `maximize()` | `driver.manage().window().maximize();` |
| **Minimize Window** ✅ | `minimize()` | `driver.manage().window().minimize();` |
| **Set Custom Size** ✅ | `setSize(new Dimension(w, h))` | `driver.manage().window().setSize(new Dimension(1024, 768));` |
| **Full-Screen Mode** | `fullscreen()` | `driver.manage().window().fullscreen();` |
| **Get Window Size** | `getSize()` | `driver.manage().window().getSize();` |
| **Resize Using JavaScript** | `resizeTo(w, h)` | `js.executeScript("window.resizeTo(1024, 768);");` |

---

# **🔹 Running Selenium Tests in Jenkins for Continuous Integration (CI) 🚀**  

Jenkins is a **popular CI/CD tool** used to **automate Selenium test execution** in a Continuous Integration (CI) pipeline.  

---

## **📌 1. Prerequisites**
✔ **Install Jenkins** (`.war` or Docker)  
✔ **Install Java & Maven** (`mvn -version`)  
✔ **Install Selenium & WebDriver** (ChromeDriver, GeckoDriver, EdgeDriver)  
✔ **Have a Selenium Maven/Gradle Project**  

---

## **📌 2. Start Jenkins**
Run Jenkins from the command line:  
```sh
java -jar jenkins.war --httpPort=8080
```
✅ **Access Jenkins at:** `http://localhost:8080`

---

## **📌 3. Install Required Jenkins Plugins**
Navigate to:  
**Jenkins → Manage Jenkins → Plugins → Available Plugins**  

🔹 Install:  
✅ **Maven Integration Plugin**  
✅ **TestNG Results Plugin** (For TestNG Reports)  
✅ **HTML Publisher Plugin** (For Extent Reports, Allure Reports)  

---

## **📌 4. Create a Selenium Job in Jenkins**
### **🔹 Step 1: Create a New Job**
🔹 **Jenkins Dashboard → New Item → Freestyle Project**  
🔹 **Enter Name (e.g., `SeleniumTests`)** → Click **OK**

### **🔹 Step 2: Configure the Job**
#### **🔹 Option 1: Run Selenium Tests Using Maven**
✔ **Go to "Build" Section**  
✔ **Select "Invoke top-level Maven targets"**  
✔ **Enter Goals:**  
```sh
clean test
```

#### **🔹 Option 2: Run Selenium Tests Using Shell Script**
✔ **Go to "Build" → Add "Execute Shell"**  
✔ **Enter Script:**
```sh
cd /path/to/project
mvn clean test
```
✅ **Best for:** Running tests in **Linux/Mac environments**.

---

## **📌 5. Schedule Automated Test Execution (Optional)**
✔ **Go to "Build Triggers"**  
✔ Select **"Build Periodically"**  
✔ Add a **cron expression**, e.g.:  
```sh
H 2 * * 1-5  # Runs every weekday at 2 AM
```
✅ **Best for:** **Running tests automatically** at specific times.

---

## **📌 6. View Test Reports in Jenkins**
### **🔹 Option 1: View Console Output**
Go to **Jenkins → Your Job → Console Output**  
```sh
mvn clean test
```

### **🔹 Option 2: TestNG Reports Plugin**
✔ **Post-build Actions → Publish TestNG Results**  
✔ Add:  
```sh
test-output/testng-results.xml
```
✅ **Best for:** **Viewing structured TestNG reports**.

### **🔹 Option 3: HTML Reports (Extent Reports, Allure)**
✔ **Install "HTML Publisher Plugin"**  
✔ **Post-build Actions → Publish HTML Report**  
✔ Report Path:  
```sh
target/surefire-reports/emailable-report.html
```
✅ **Best for:** **Advanced test reports in Jenkins**.

---

## **📌 7. Running Selenium Tests in Jenkins Headless Mode (CI/CD)**
Selenium requires a **display** to run tests, but Jenkins often runs in **headless servers**.  

### **🔹 Run Tests in Headless Chrome**
Modify **`ChromeOptions`** in your test setup:
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless");
options.addArguments("--disable-gpu");
options.addArguments("--window-size=1920,1080");

WebDriver driver = new ChromeDriver(options);
```
✅ **Best for:** Running tests in **Jenkins, Docker, and CI/CD pipelines**.

---

## **📌 8. Running Selenium Tests in Jenkins Using Docker**
### **🔹 Step 1: Run Selenium Grid in Docker**
```sh
docker run -d -p 4444:4444 --name selenium-hub selenium/hub
```
### **🔹 Step 2: Run Chrome Node**
```sh
docker run -d --link selenium-hub:hub selenium/node-chrome
```
### **🔹 Step 3: Connect Jenkins to Selenium Grid**
Modify test setup:
```java
WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), new ChromeOptions());
```
✅ **Best for:** Running tests in **Dockerized environments**.

---

## **📌 9. Running Selenium Tests in Jenkins Pipeline (Jenkinsfile)**
### **🔹 Example: `Jenkinsfile` for Selenium Maven Project**
```groovy
pipeline {
    agent any
    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/user/repository.git'
            }
        }
        stage('Build & Run Tests') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Publish Reports') {
            steps {
                publishHTML (target: [
                    reportDir: 'target/surefire-reports',
                    reportFiles: 'emailable-report.html',
                    reportName: 'Test Report'
                ])
            }
        }
    }
}
```
✅ **Best for:** **CI/CD pipelines with automated Selenium execution**.

---

## **📌 10. Troubleshooting Jenkins Selenium Integration**
| **Issue** | **Solution** |
|-----------|-------------|
| **Jenkins can’t find Chrome/GeckoDriver** | Add driver path: `System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");` |
| **Tests fail in Jenkins but pass locally** | Run tests **in headless mode** (`options.addArguments("--headless")`) |
| **Jenkins is running as a service, and tests don't open the browser** | Use **headless mode or Xvfb display** |
| **NoSuchSessionException in Selenium Grid** | Ensure nodes are **connected to the hub** (`docker logs selenium-hub`) |

---

## **📌 Summary Table: Running Selenium in Jenkins**
| **Task** | **Steps** | **Example** |
|-----------|-----------|------------|
| **Install Jenkins** ✅ | `java -jar jenkins.war` | Open `http://localhost:8080` |
| **Create Selenium Job** ✅ | Add **Maven/Execute Shell** step | `mvn clean test` |
| **Run in Headless Mode** ✅ | Add ChromeOptions | `options.addArguments("--headless");` |
| **Schedule Automated Execution** ✅ | Use **cron expression** | `H 2 * * 1-5` |
| **View Test Reports** ✅ | Install **TestNG & HTML Publisher Plugin** | `target/surefire-reports/emailable-report.html` |
| **Run in Docker** ✅ | Use **Selenium Grid** | `docker run -d -p 4444:4444 selenium/hub` |
| **Use Jenkins Pipeline** ✅ | Create `Jenkinsfile` | `sh 'mvn clean test'` |

---
# **🔹 Performing Responsive Testing Using Selenium WebDriver 📱💻**  

Responsive testing ensures that a website **adapts correctly** to different screen sizes, resolutions, and devices (desktop, tablet, mobile). Selenium WebDriver can **resize browser windows** or use **mobile emulation** to test responsiveness.

---

## **📌 1. Resize Browser Window (`setSize()`)** ✅  
Adjust the browser window size to **simulate different screen sizes**.

### **🔹 Example: Test Different Viewports**
```java
import org.openqa.selenium.Dimension;

driver.manage().window().setSize(new Dimension(375, 667));  // iPhone 6/7/8
```
✅ **Best for:** Testing different device resolutions.  

| **Device** | **Resolution (W × H)** |
|-----------|------------------|
| **Desktop (Full HD)** | `1920 × 1080` |
| **Laptop (MacBook Air)** | `1366 × 768` |
| **Tablet (iPad)** | `768 × 1024` |
| **Mobile (iPhone 12)** | `390 × 844` |

---

## **📌 2. Maximize Browser (`maximize()`)**
Ensure the site is responsive **at full screen**.

### **🔹 Example: Maximize Browser**
```java
driver.manage().window().maximize();
```
✅ **Best for:** **Verifying desktop responsiveness**.

---

## **📌 3. Use Chrome DevTools Protocol (CDP) for Mobile Emulation**  
Simulate **real mobile devices** using **Chrome DevTools Protocol (CDP)**.

### **🔹 Example: Emulate an iPhone 12**
```java
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.Map;

ChromeOptions options = new ChromeOptions();

Map<String, Object> deviceMetrics = new HashMap<>();
deviceMetrics.put("width", 390);
deviceMetrics.put("height", 844);
deviceMetrics.put("mobile", true);
deviceMetrics.put("deviceScaleFactor", 2.0);

Map<String, Object> mobileEmulation = new HashMap<>();
mobileEmulation.put("deviceMetrics", deviceMetrics);
mobileEmulation.put("userAgent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X)");

options.setExperimentalOption("mobileEmulation", mobileEmulation);
WebDriver driver = new ChromeDriver(options);
```
✅ **Best for:** **Testing actual mobile behavior** (touch events, viewport, user agent).

---

## **📌 4. Verify Responsive UI with JavaScript Executor**
Use **JavaScript Executor** to check if media queries are applied.

### **🔹 Example: Detect Mobile View**
```java
import org.openqa.selenium.JavascriptExecutor;

JavascriptExecutor js = (JavascriptExecutor) driver;
Boolean isMobileView = (Boolean) js.executeScript("return window.matchMedia('(max-width: 768px)').matches;");
System.out.println("Is Mobile View: " + isMobileView);
```
✅ **Best for:** Verifying **CSS breakpoints**.

---

## **📌 5. Take Screenshots for Different Viewports**
Capture **screenshots at different resolutions** to validate UI.

### **🔹 Example: Capture Screenshot for Responsive Testing**
```java
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import org.apache.commons.io.FileUtils;

File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
FileUtils.copyFile(src, new File("screenshots/mobile-view.png"));
```
✅ **Best for:** **Visual regression testing**.

---

## **📌 6. Run Responsive Tests in Headless Mode (CI/CD)**
### **🔹 Example: Run Tests in Headless Chrome**
```java
import org.openqa.selenium.chrome.ChromeOptions;

ChromeOptions options = new ChromeOptions();
options.addArguments("--headless");
options.addArguments("--window-size=375,667");  // Mobile screen size

WebDriver driver = new ChromeDriver(options);
```
✅ **Best for:** Running **tests in Jenkins, CI/CD, and Docker**.

---

## **📌 7. Using Selenium Grid for Parallel Responsive Testing**
Run tests on multiple screen sizes **simultaneously** using **Selenium Grid**.

### **🔹 Example: Parallel Execution with TestNG**
Modify `testng.xml`:
```xml
<suite name="ResponsiveTesting" parallel="tests" thread-count="2">
    <test name="Desktop View">
        <parameter name="width" value="1920"/>
        <parameter name="height" value="1080"/>
        <classes>
            <class name="ResponsiveTest"/>
        </classes>
    </test>
    <test name="Mobile View">
        <parameter name="width" value="375"/>
        <parameter name="height" value="667"/>
        <classes>
            <class name="ResponsiveTest"/>
        </classes>
    </test>
</suite>
```
### **🔹 Example: TestNG Script**
```java
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.Dimension;

public class ResponsiveTest {
    @Test
    @Parameters({"width", "height"})
    public void testResponsiveLayout(int width, int height) {
        driver.manage().window().setSize(new Dimension(width, height));
        System.out.println("Testing at " + width + "x" + height);
    }
}
```
✅ **Best for:** Running tests **on multiple resolutions in parallel**.

---

## **📌 Summary Table**
| **Responsive Testing Method** | **Best For** | **Example Code** |
|-------------------------------|-------------|------------------|
| **Resize Browser (`setSize()`)** ✅ | Desktop & Mobile testing | `driver.manage().window().setSize(new Dimension(375, 667));` |
| **Maximize Browser (`maximize()`)** ✅ | Desktop responsiveness | `driver.manage().window().maximize();` |
| **Mobile Emulation (CDP)** ✅ | Simulating real mobile devices | `options.setExperimentalOption("mobileEmulation", mobileEmulation);` |
| **JavaScript Viewport Detection** | Verifying CSS breakpoints | `js.executeScript("return window.matchMedia('(max-width: 768px)').matches;");` |
| **Screenshot Capture** | Visual validation | `((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);` |
| **Headless Testing** | CI/CD pipelines | `options.addArguments("--headless");` |
| **Parallel Execution (Selenium Grid)** ✅ | Multi-device testing | `testng.xml` parallel execution |

---

# **🔹 Performing Mouse Actions (Double-Click, Right-Click) in Selenium WebDriver** 🖱️🚀  

Selenium WebDriver provides the **Actions class** to simulate user interactions like **double-clicking, right-clicking, hovering, and drag-and-drop**.

---

## **📌 1. Using `Actions` Class**
First, import the `Actions` class:
```java
import org.openqa.selenium.interactions.Actions;
```

### **🔹 Step 1: Create an `Actions` Instance**
```java
Actions actions = new Actions(driver);
```
✅ **Required for all advanced user interactions.**  

---

## **📌 2. Perform a Double-Click (`doubleClick()`)**
### **🔹 Example: Double-Click on an Element**
```java
WebElement element = driver.findElement(By.id("doubleClickButton"));
Actions actions = new Actions(driver);
actions.doubleClick(element).perform();
```
✅ **Best for:** Testing **double-click actions** on buttons or fields.

---

## **📌 3. Perform a Right-Click (Context Click) (`contextClick()`)**
### **🔹 Example: Right-Click on an Element**
```java
WebElement element = driver.findElement(By.id("rightClickButton"));
Actions actions = new Actions(driver);
actions.contextClick(element).perform();
```
✅ **Best for:** Testing **context menus**.

---

## **📌 4. Hover Over an Element (`moveToElement()`)**
### **🔹 Example: Mouse Hover**
```java
WebElement menu = driver.findElement(By.id("menu"));
Actions actions = new Actions(driver);
actions.moveToElement(menu).perform();
```
✅ **Best for:** Testing **drop-down menus or tooltips**.

---

## **📌 5. Drag and Drop (`dragAndDrop()`)**
### **🔹 Example: Drag an Element to Another Location**
```java
WebElement source = driver.findElement(By.id("drag"));
WebElement target = driver.findElement(By.id("drop"));
Actions actions = new Actions(driver);
actions.dragAndDrop(source, target).perform();
```
✅ **Best for:** **Testing drag-and-drop UI components**.

---

## **📌 6. Drag and Drop Using Offsets (`clickAndHold() → moveByOffset()`)**
If `dragAndDrop()` fails, use **offset-based dragging**.

### **🔹 Example: Drag an Element by Offset**
```java
WebElement source = driver.findElement(By.id("drag"));
Actions actions = new Actions(driver);
actions.clickAndHold(source).moveByOffset(100, 50).release().perform();
```
✅ **Best for:** Handling **drag-and-drop on canvas elements**.

---

## **📌 7. Keyboard Actions (Press & Release Keys)**
### **🔹 Example: Press ENTER Key**
```java
import org.openqa.selenium.Keys;

WebElement inputField = driver.findElement(By.id("search"));
Actions actions = new Actions(driver);
actions.sendKeys(inputField, Keys.ENTER).perform();
```
✅ **Best for:** Testing **form submissions**.

---

## **📌 8. Combining Multiple Actions (Chained Actions)**
### **🔹 Example: Right-Click & Press Arrow Key**
```java
WebElement element = driver.findElement(By.id("menu"));
Actions actions = new Actions(driver);
actions.contextClick(element).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
```
✅ **Best for:** **Automating keyboard navigation in menus**.

---

## **📌 Summary Table**
| **Action** | **Method** | **Example Code** |
|-----------|------------|------------------|
| **Double Click** ✅ | `doubleClick(element)` | `actions.doubleClick(element).perform();` |
| **Right Click** ✅ | `contextClick(element)` | `actions.contextClick(element).perform();` |
| **Mouse Hover** ✅ | `moveToElement(element)` | `actions.moveToElement(menu).perform();` |
| **Drag & Drop** ✅ | `dragAndDrop(source, target)` | `actions.dragAndDrop(source, target).perform();` |
| **Drag & Drop (Offset)** | `clickAndHold() → moveByOffset()` | `actions.clickAndHold().moveByOffset(100, 50).release().perform();` |
| **Press Enter Key** | `sendKeys(Keys.ENTER)` | `actions.sendKeys(element, Keys.ENTER).perform();` |

---

# **🔹 Handling Test Data in Selenium for Data-Driven Testing** 📊🚀  

Data-driven testing (DDT) allows you to run the same test **multiple times** with different input values, improving test coverage.  

---

## **📌 1. Methods to Handle Test Data in Selenium**
| **Test Data Source** | **Best For** | **Example Method** |
|-----------------|------------|----------------|
| **TestNG `@DataProvider`** ✅ | Small data sets | `@DataProvider(name = "loginData")` |
| **Excel (Apache POI)** ✅ | Large structured data | `Workbook workbook = WorkbookFactory.create(file);` |
| **CSV File** | Simple structured data | `BufferedReader.readLine();` |
| **JSON File** | API testing & structured data | `ObjectMapper.readTree();` |
| **Database (SQL/Oracle)** | Backend data validation | `ResultSet rs = stmt.executeQuery();` |

---

## **📌 2. Using TestNG `@DataProvider` (Recommended ✅)**
TestNG’s `@DataProvider` allows parameterized tests **without external files**.

### **🔹 Example: Login Test with `@DataProvider`**
```java
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataDrivenTest {
    @DataProvider(name = "loginData")
    public Object[][] getData() {
        return new Object[][]{
                {"admin", "admin123"},
                {"user1", "password1"},
                {"user2", "password2"}
        };
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password) {
        System.out.println("Logging in with: " + username + " | " + password);
    }
}
```
✅ **Best for:** Small test data sets **inside the test class**.

---

## **📌 3. Using Excel File (Apache POI)**
For large data sets, **store test data in Excel** and read it dynamically.

### **🔹 Step 1: Add Apache POI Dependency (Maven)**
```xml
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.2.3</version>
</dependency>
```

### **🔹 Step 2: Read Data from Excel**
```java
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {
    public static Object[][] getExcelData(String filePath, String sheetName) throws IOException {
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheet(sheetName);
        int rows = sheet.getPhysicalNumberOfRows();
        int cols = sheet.getRow(0).getPhysicalNumberOfCells();

        Object[][] data = new Object[rows - 1][cols];
        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i - 1][j] = sheet.getRow(i).getCell(j).toString();
            }
        }
        workbook.close();
        return data;
    }
}
```

### **🔹 Step 3: Integrate with TestNG `@DataProvider`**
```java
import org.testng.annotations.DataProvider;
import java.io.IOException;

public class ExcelDataProvider {
    @DataProvider(name = "excelData")
    public Object[][] getData() throws IOException {
        return ExcelUtils.getExcelData("testdata.xlsx", "LoginData");
    }
}
```
✅ **Best for:** **Large structured data sets**.

---

## **📌 4. Using CSV File**
For simple structured text data, use a **CSV file**.

### **🔹 Example: Read Data from CSV**
```java
import java.io.*;
import java.util.*;

public class CSVUtils {
    public static List<String[]> getCSVData(String filePath) throws IOException {
        List<String[]> data = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            data.add(line.split(","));
        }
        br.close();
        return data;
    }
}
```
✅ **Best for:** **Lightweight data handling**.

---

## **📌 5. Using JSON File (For API Testing)**
For structured test data, use **JSON**.

### **🔹 Example: Read JSON Data**
```java
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JSONUtils {
    public static Object[][] getJSONData(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(filePath));
        Object[][] data = new Object[rootNode.size()][2];

        for (int i = 0; i < rootNode.size(); i++) {
            data[i][0] = rootNode.get(i).get("username").asText();
            data[i][1] = rootNode.get(i).get("password").asText();
        }
        return data;
    }
}
```
✅ **Best for:** **API testing & structured test data**.

---

## **📌 6. Using Database (SQL/Oracle)**
Fetch test data **directly from a database**.

### **🔹 Example: Read Data from MySQL**
```java
import java.sql.*;

public class DBUtils {
    public static Object[][] getDBData(String query) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "password");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        List<Object[]> data = new ArrayList<>();
        while (rs.next()) {
            data.add(new Object[]{rs.getString("username"), rs.getString("password")});
        }
        conn.close();
        return data.toArray(new Object[0][]);
    }
}
```
✅ **Best for:** **Validating backend test cases**.

---

## **📌 Summary Table**
| **Test Data Source** | **Best For** | **Example Code** |
|---------------------|-------------|------------------|
| **TestNG `@DataProvider`** ✅ | Small test data sets | `@DataProvider(name = "loginData")` |
| **Excel (Apache POI)** ✅ | Large structured data | `ExcelUtils.getExcelData("file.xlsx", "Sheet1");` |
| **CSV File** | Simple test data | `CSVUtils.getCSVData("testdata.csv");` |
| **JSON File** | API testing | `JSONUtils.getJSONData("testdata.json");` |
| **Database (SQL/Oracle)** | Backend validation | `DBUtils.getDBData("SELECT * FROM users");` |

---

## **📌 7. Running Data-Driven Tests in Parallel (TestNG)**
To **speed up test execution**, run data-driven tests **in parallel**.

### **🔹 Modify `testng.xml` for Parallel Execution**
```xml
<suite name="Data-Driven Suite" parallel="methods" thread-count="3">
    <test name="Data Test">
        <classes>
            <class name="ExcelDataDrivenTest"/>
        </classes>
    </test>
</suite>
```
✅ **Best for:** Running **data-driven tests faster**.

---

# **🔹 Challenges of Automating CAPTCHA with Selenium WebDriver 🤖🚫**  

**CAPTCHA (Completely Automated Public Turing test to tell Computers and Humans Apart)** is designed to **prevent automation and bot attacks**. This makes it extremely **difficult** to automate using Selenium WebDriver.  

---

## **📌 1. Why CAPTCHA is Hard to Automate?**
✅ **Designed to block bots** (like Selenium).  
✅ **Random & unpredictable challenges** (text, images, audio).  
✅ **Dynamic rendering** (not accessible via locators).  
✅ **Time-sensitive expiration** (some CAPTCHAs change after a few seconds).  

---

## **📌 2. Common CAPTCHA Types & Automation Challenges**
| **CAPTCHA Type** | **Example** | **Challenges in Selenium** |
|-----------------|------------|---------------------------|
| **Text-based (Distorted Text)** | Letters & numbers in distorted images | OCR tools (like Tesseract) are unreliable |
| **Image-based (Select Objects in Grid)** | "Select all traffic lights" | Requires AI to recognize images |
| **ReCAPTCHA (Google)** | "I’m not a robot" checkbox | Detects automation tools like Selenium |
| **Invisible reCAPTCHA** | Automatic verification after user actions | Requires real user interaction |
| **Math-based CAPTCHA** | Solve simple math (e.g., `3 + 5 = ?`) | Can be automated using Java logic |
| **Audio CAPTCHA** | Listen and type spoken numbers | Needs **speech-to-text** conversion |

---

## **📌 3. Approaches to Handle CAPTCHA in Selenium**
| **Approach** | **How It Works** | **Best For** | **Challenges** |
|-------------|-----------------|-------------|--------------|
| **Manual Intervention** ✅ | Prompt the user to solve CAPTCHA | Test environments | Slows down automation |
| **Bypassing CAPTCHA in Test Mode** ✅ | Ask developers to disable CAPTCHA for test runs | CI/CD & automation | Not applicable for production |
| **Using Third-Party Services** | Services like **2Captcha, Anti-Captcha** solve CAPTCHA | ReCAPTCHA & Image CAPTCHA | Paid service & API dependency |
| **Using Cookies & Session Tokens** | Store authentication tokens to avoid CAPTCHA on login | Authenticated test cases | Only works if CAPTCHA appears once per session |
| **Using AI & OCR (Tesseract)** | Convert CAPTCHA image to text and solve | Basic text-based CAPTCHA | Low accuracy, fails on distortions |

---

## **📌 4. Handling CAPTCHA in Automation Testing**
### **🔹 1. Manual Intervention (Best for UI Testing)**
When CAPTCHA appears, **pause the test** and allow the user to manually enter it.

```java
System.out.println("Solve the CAPTCHA manually and press Enter to continue...");
new Scanner(System.in).nextLine();
```
✅ **Best for:** **Occasional CAPTCHA handling** in manual+automation tests.  
❌ **Slows down execution**.

---

### **🔹 2. Disable CAPTCHA in Test Environments (Recommended ✅)**
**Ask developers** to provide a **test mode** where CAPTCHA is disabled.

✅ **Example: Use a "test" query parameter**  
```sh
https://example.com/login?disableCaptcha=true
```
✅ **Best for:** Running tests **without CAPTCHA in staging/QA environments**.  
❌ **Not applicable for production testing**.

---

### **🔹 3. Use CAPTCHA Solving Services (Paid API)**
Services like **2Captcha, Anti-Captcha, DeathByCaptcha** use **human solvers**.

#### **🔹 Example: Solve CAPTCHA Using 2Captcha API**
```java
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CaptchaSolver {
    public static void main(String[] args) throws Exception {
        String apiKey = "YOUR_2CAPTCHA_API_KEY";
        String captchaImageUrl = "https://example.com/captcha.png";
        
        String requestUrl = "http://2captcha.com/in.php?key=" + apiKey + "&method=base64&body=" + captchaImageUrl;
        HttpURLConnection conn = (HttpURLConnection) new URL(requestUrl).openConnection();
        Scanner sc = new Scanner(conn.getInputStream());
        
        String captchaId = sc.nextLine().split("\\|")[1];
        Thread.sleep(15000);  // Wait for CAPTCHA solution
        
        String responseUrl = "http://2captcha.com/res.php?key=" + apiKey + "&action=get&id=" + captchaId;
        conn = (HttpURLConnection) new URL(responseUrl).openConnection();
        sc = new Scanner(conn.getInputStream());
        
        String captchaText = sc.nextLine();
        System.out.println("CAPTCHA Solved: " + captchaText);
    }
}
```
✅ **Best for:** **ReCAPTCHA, image-based CAPTCHA**.  
❌ **Paid API & latency issues**.

---

### **🔹 4. Using Cookies & Session Tokens**
If CAPTCHA **only appears on first login**, store authentication tokens.

#### **Example: Store and Reuse Session Cookies**
```java
import org.openqa.selenium.Cookie;

Cookie sessionCookie = new Cookie("session_id", "ABC1234");
driver.manage().addCookie(sessionCookie);
driver.navigate().refresh();
```
✅ **Best for:** **Skipping CAPTCHA in repeated logins**.  
❌ **Does not work for every website**.

---

### **🔹 5. Using OCR (Tesseract) for Text CAPTCHA (Low Accuracy)**
OCR can **convert CAPTCHA images into text**, but it fails with distortions.

#### **Example: Use Tesseract OCR to Read CAPTCHA**
```java
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.io.File;

public class OCRCaptcha {
    public static void main(String[] args) throws TesseractException {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("tessdata");
        String captchaText = tesseract.doOCR(new File("captcha.png"));
        System.out.println("CAPTCHA Text: " + captchaText);
    }
}
```
✅ **Best for:** Simple **text-based CAPTCHA**.  
❌ **Fails for distorted CAPTCHAs**.

---

## **📌 5. Best Practices for CAPTCHA Handling in Automation**
| **Approach** | **Best For** | **Challenges** |
|-------------|-------------|---------------|
| **Manual CAPTCHA Entry** ✅ | Small test cases | Slows execution |
| **Disabling CAPTCHA in Staging** ✅ | CI/CD & Test Environments | Not possible in production |
| **Using CAPTCHA Solving Services (2Captcha, Anti-Captcha)** | Automated Testing | Requires API & costs money |
| **Using Session Cookies** | Repeated logins | Works only if CAPTCHA appears once per session |
| **OCR & AI (Tesseract, TensorFlow)** | Basic text-based CAPTCHA | Low accuracy |

---

## **📌 6. Why You Should NOT Automate CAPTCHA in Production**
❌ **Against CAPTCHA’s purpose** (Websites detect bots).  
❌ **Google ReCAPTCHA blocks automation** (Detects browser fingerprinting).  
❌ **Legal concerns** (Bypassing CAPTCHA might violate terms of service).  

### ✅ **Solution: Use API Keys & Whitelisting**
For test automation, ask **developers to disable CAPTCHA** or **whitelist test IPs**.

---

## **📌 Summary**
| **Challenge** | **Solution** |
|--------------|-------------|
| **CAPTCHA prevents automation** | Ask for a **test mode to disable CAPTCHA** |
| **ReCAPTCHA detects Selenium** | Use **CAPTCHA solving services (2Captcha, Anti-Captcha)** |
| **Image CAPTCHA requires human recognition** | Use **paid services or AI (OCR fails often)** |
| **CAPTCHA on login page** | **Store session cookies** to avoid repeated CAPTCHAs |

---

# **🔹 Generating Reports for Selenium Test Execution 📊🚀**  

To analyze test results in Selenium, you can generate reports using **TestNG Reports, Extent Reports, Allure Reports, and JUnit Reports**.

---

## **📌 1. Types of Reports in Selenium**
| **Report Type** | **Best For** | **Example Framework** |
|---------------|-------------|-----------------|
| **TestNG Default Reports** ✅ | Basic test execution summary | TestNG |
| **Extent Reports** ✅ | Rich UI, Screenshots, Logs | TestNG, JUnit |
| **Allure Reports** | Advanced logging, Graphical reports | TestNG, Cucumber |
| **JUnit Reports** | CI/CD pipelines (Jenkins, GitHub Actions) | JUnit, TestNG |
| **HTML Reports** | Customizable test reports | Selenium, TestNG |

---

## **📌 2. Generating TestNG Default Reports (Basic)**
TestNG automatically generates an **HTML report** after test execution.

### **🔹 Step 1: Run TestNG Tests**
```sh
mvn clean test
```
### **🔹 Step 2: View Reports in `test-output` Directory**
📌 **Path:**  
```
test-output/index.html
```
✅ **Best for:** **Basic test execution summary**.

---

## **📌 3. Generating Extent Reports (Recommended ✅)**
Extent Reports provide **detailed HTML reports with logs, screenshots, and charts**.

### **🔹 Step 1: Add Extent Reports Dependency (Maven)**
```xml
<dependency>
    <groupId>com.aventstack</groupId>
    <artifactId>extentreports</artifactId>
    <version>5.0.9</version>
</dependency>
```

### **🔹 Step 2: Implement Extent Reports in TestNG**
```java
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.annotations.*;

public class ExtentReportTest {
    ExtentReports extent;
    ExtentTest test;

    @BeforeSuite
    public void setup() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @Test
    public void sampleTest() {
        test = extent.createTest("Sample Test");
        test.pass("Test Passed Successfully");
    }

    @AfterSuite
    public void teardown() {
        extent.flush();  // Write results to report
    }
}
```
### **🔹 Step 3: View Report**
📌 **Path:**  
```
ExtentReport.html
```
✅ **Best for:** **Rich UI with Screenshots, Logs, Charts**.

---

## **📌 4. Generating Allure Reports (Advanced Logging)**
Allure Reports provide **detailed steps, logs, and graphical insights**.

### **🔹 Step 1: Add Allure Dependencies (Maven)**
```xml
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-testng</artifactId>
    <version>2.21.0</version>
</dependency>
```

### **🔹 Step 2: Annotate TestNG Test Cases**
```java
import io.qameta.allure.*;

public class AllureTest {
    @Test
    @Description("This is a test for login functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Login with username and password")
    public void loginTest() {
        System.out.println("Executing Login Test");
    }
}
```

### **🔹 Step 3: Run Tests and Generate Report**
```sh
mvn clean test
allure serve target/allure-results
```
✅ **Best for:** **Graphical Reports, API Testing**.

---

## **📌 5. Generating JUnit Reports (CI/CD Integration)**
JUnit Reports are **XML-based reports** for Jenkins, GitHub Actions.

### **🔹 Step 1: Enable JUnit Reporting in TestNG**
Modify `testng.xml`:
```xml
<suite name="Test Suite">
    <listeners>
        <listener class-name="org.testng.reporters.XMLReporter"/>
    </listeners>
    <test name="JUnit Report Test">
        <classes>
            <class name="tests.MyTest"/>
        </classes>
    </test>
</suite>
```

### **🔹 Step 2: Run Tests and Find JUnit Report**
📌 **Path:**  
```
test-output/testng-results.xml
```
✅ **Best for:** **CI/CD Pipelines (Jenkins, GitHub Actions)**.

---

## **📌 6. Capturing Screenshots in Reports**
### **🔹 Example: Capture Screenshot in TestNG**
```java
import org.openqa.selenium.*;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class ScreenshotUtil {
    public static String captureScreenshot(WebDriver driver, String testName) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = "screenshots/" + testName + ".png";
        try {
            FileUtils.copyFile(src, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
```
✅ **Best for:** **Debugging test failures**.

---

## **📌 7. Integrating Reports in Jenkins**
### **🔹 Step 1: Install Jenkins Plugins**
✔ **TestNG Results Plugin** (For TestNG XML Reports)  
✔ **HTML Publisher Plugin** (For Extent & Allure Reports)  

### **🔹 Step 2: Publish Reports in Jenkins**
✔ **Post-Build Actions → Publish HTML Reports**  
✔ **Report Path:**  
```
target/surefire-reports/emailable-report.html
```
✅ **Best for:** **CI/CD Execution Monitoring**.

---

## **📌 Summary Table: Selenium Reporting Options**
| **Report Type** | **Best For** | **Setup Complexity** | **Output Format** |
|----------------|-------------|----------------|----------------|
| **TestNG Reports** ✅ | Basic test summary | Easy | HTML/XML |
| **Extent Reports** ✅ | Detailed logs, UI reports | Moderate | HTML |
| **Allure Reports** | Advanced reporting, API testing | Moderate | HTML (Graphical) |
| **JUnit Reports** | CI/CD integration (Jenkins, GitHub) | Easy | XML |
| **HTML Reports** | Custom reports | Moderate | HTML |

---





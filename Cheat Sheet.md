Here's a quick cheat sheet for handling multiple windows in Selenium using Java:  

### **Handling Multiple Windows in Selenium (Java)**
#### **1. Get the Current Window Handle**
```java
String parentWindow = driver.getWindowHandle();
```
- Stores the main window’s handle for reference.

#### **2. Get All Window Handles**
```java
Set<String> allWindows = driver.getWindowHandles();
```
- Returns a set of all open window handles.

#### **3. Switch to a New Window**
```java
for (String windowHandle : allWindows) {
    if (!windowHandle.equals(parentWindow)) {
        driver.switchTo().window(windowHandle);
        break;
    }
}
```
- Iterates through all window handles and switches to the new one.

#### **4. Perform Actions in the New Window**
```java
driver.findElement(By.id("elementID")).click();
```
- Interact with elements in the new window.

#### **5. Close the New Window & Switch Back**
```java
driver.close(); // Closes the current window
driver.switchTo().window(parentWindow); // Switch back to main window
```
- Always switch back to the parent window after closing the new one.

#### **6. Using Iterator for Window Handling**
```java
Iterator<String> iterator = allWindows.iterator();
String mainWindow = iterator.next();
String childWindow = iterator.next();

driver.switchTo().window(childWindow);
// Perform actions on the child window
driver.close();

driver.switchTo().window(mainWindow);
```
- Uses an `Iterator` to handle multiple windows.

#### **7. Using a Method for Dynamic Window Handling**
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

Would you like to include more details or examples? 🚀

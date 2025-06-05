# Fixdpi [ver. 1.0.0]

Simple proxy server for watching YouTube in Russia.  
The code is written in the Java programming language.  
No third-party libraries are used in the project.  
The principle of operation is to use fragmentation to bypass DPI.  

## Table of Contents
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)

## Prerequisites
Before you begin, ensure you have met the following requirements:
* Java JDK installed (version 17+ recommended).
* Apache Maven installed.

## Installation
To install this project locally, follow these steps:

### Clone the Repository

```git clone https://github.com/yakovitalik/fixdpi.git```  
```cd fixdpi```  

### Build the Project  

```mvn clean install```  


## Usage
### Step 1:
There will be 2 .jar files in the target/ folder after generation. Take the one named fixdpi-1.0.0.0-jar-with-dependencies.jar.  
Rename it to a usable form, e.g. fixdpi.jar.  
After that, you can run this file with the command:  
```java -jar fixdpi.jar```

### Step 2:
You need to install a web browser that supports proxy installation in the settings, such as Mozilla Firefox browser.  
To do this, go to your browser settings, and make adjustments. Set the proxy host to 127.0.0.1 and port 8881. Be sure to check the box “Also use this proxy for HTTPS”.  
Images with examples are attached.  
***
<img src=https://github.com/yakovitalik/fixdpi/blob/master/Screen1.jpg>
***
<img src=https://github.com/yakovitalik/fixdpi/blob/master/Screen2.jpg>

It's all set up! You can go to the site youtube and watch videos, tested, it works!  

## Contributing
We welcome contributions! Please submit pull requests for bug fixes, new features, etc.

### Steps to Contribute
1. Fork the repository.
2. Create a branch (`git checkout -b feature/awesome_feature`).
3. Commit your changes (`git commit -am 'Add some awesome feature'`).
4. Push to the branch (`git push origin feature/awesome_feature`).
5. Submit a pull request!



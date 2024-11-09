# Software comprehension home work

This project is a software analyzer designed to identify modules in object-oriented applications, create a coupling graph, and transform the results into a dendrogram using **JDT** and **Spoon**.

## Requirements

* Java 17 or OpenJDK 17

## Installing OpenJDK 17

If OpenJDK 17 is not installed, you can install it using the following commands:

### Ubuntu/Debian-based systems
```bash
sudo apt install openjdk-17-jdk
```

### macOS with Homebrew
```bash
brew install openjdk@17
```

After installation, verify the installation:
```bash
java -version
```

## Project Setup

### 1. Downloading the Project
Download the project as a `.zip` file from the GitHub repository and extract it to your desired location.

### 2. Running the Analyzer
Navigate to the directory where the `software-comprehension.jar` file is located. Then, run the analyzer using the following command:

```bash
java -jar software-comprehension.jar
```

### 2.1 Enter Project Path
The analyzer will prompt you to enter the path to the project you wish to analyze. You can use the sample project located in `resources/LibraryManagement` by entering its path:

```bash
Please provide the project path you want to work on: resources/LibraryManagement
```

### 2.2 Enter JDK Path
Next, provide the path to your JDK. You can retrieve this path with:

```bash
echo $JAVA_HOME
```

Then, enter the path in the analyzer prompt:

```bash
Please provide the JDK path: /path/to/your/jdk
```

### 2.3 Menu Selection (JDT or Spoon)
Choose which tool to use for analysis:

```bash
Menu:
1 : Analyze using JDT.
2 : Analyze using Spoon.
0 : Exit.
What do you choose:
```

### 2.4 View the Output
The analyzer will process the project and output key metrics, including:
* Identified classes and method calls
* Total method invocations
* Generated call graph
* Coupling graph
* Dendrogram showing module clustering

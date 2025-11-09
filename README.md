# Simple Java Socket Chat Server

A simple TCP chat server built in Java. It uses only the standard `java.net` library, no external frameworks.It handles multiple clients simultaneously using threads.


## Demo

Link: https://drive.google.com/file/d/18JLTliQSkyz49XQXdUqQD3YFL65CJlfe/view?usp=sharing


## Run Locally

Clone the project

```bash
  git clone https://github.com/swayraj/socket-chat.git
```

Go to the project directory

```bash
  cd socket-chat
```

Compile the Java files

```bash
  javac ChatServer.java ClientHandler.java
```

Start the server

```bash
  java ChatServer
```

Make a connection(Windows specific!)

```bash
  telnet localhost 4000
```
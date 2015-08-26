# notify

A message sending module. The intention of developing Notify is to achieve one simple thing - Drop in a message into notify with the destination and type of service to use, and it does the rest to send it across.

Currently, it is being developed to make use of the following providers:

 - SMTP (can configure email templates too)
 - SMS Gateway (Rest API)
 - Pub-Sub System (Faye)

> NOTE: All instructions below are based on the assumption that you are using a Linux system like Ubuntu.

Setting Up The Build Tools
--------------------------

    $ sudo apt-get install build-essential libtool automake uuid-dev gcc-multilib pkg-config -y

> Also install **Apache Maven** in your system. The instructions to install it is easily available online else where, hence I am not including it here.

Compiling And Installing Protobuf Compiler
--------------------------

    $ wget https://github.com/google/protobuf/releases/download/v2.6.1/protobuf-2.6.1.tar.gz
    $ tar -xvf protobuf-2.6.1.tar.gz
    $ cd protobuf-2.6.1
    $ ./configure
    $ make
    $ make check
    $ sudo make install

Compiling And Installing Java Protocol Buffers Runtime Library
--------------------------

Continuing in the same folder as in the above instructions...

    $ cd java
    $ mvn test
    $ mvn install -DskipTests

The resulting artifact can be referenced as 

      <dependency>
        <groupId>com.google.protobuf</groupId>
        <artifactId>protobuf-java</artifactId>
        <version>2.6.1</version>
      </dependency>

To install the 'Lite' version use the lite profile

    $ mvn install -P lite -DskipTests
    
and the resulting artifact can be referenced as

      <dependency>
        <groupId>com.google.protobuf</groupId>
        <artifactId>protobuf-java</artifactId>
        <version>2.6.1</version>
        <classifier>lite</classifier>
      </dependency>

> Written with [StackEdit](https://stackedit.io/).

# notify

A message sending module. The intention of developing Notify is to achieve one simple thing - Drop in a message into notify with the destination and type of service to use, and it does the rest to send it across.

Currently, it is being developed to make use of the following providers:

 - SMTP (can configure email templates too)
 - SMS Gateway (Rest API)
 - PubSub Server (Faye)

> NOTE: All instructions below are based on the assumption that you are using a Linux system like Ubuntu.

# Setting Up The Build Tools

    $ sudo apt-get install build-essential libtool automake uuid-dev gcc-multilib pkg-config -y

# Compiling And Installing Protobuf Compiler

    $ cd /tmp
    $ wget https://github.com/google/protobuf/releases/download/v2.6.1/protobuf-2.6.1.tar.gz
    $ tar -xvf protobuf-2.6.1.tar.gz
    $ cd protobuf-2.6.1
    $ ./configure
    $ make
    $ make check
    $ sudo make install
    

> Written with [StackEdit](https://stackedit.io/).

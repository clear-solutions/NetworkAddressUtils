# NetworkAddressUtils
Overview
NetAddrUtils is a Java library designed to simplify network programming tasks, specifically focusing on retrieving the IPv4 address of a machine's network interfaces. It provides a straightforward API for developers to obtain the machine's local IP address, which is crucial for various network-based applications, especially those involving intra-network communication.

The library abstracts the complexities of Java's native networking APIs, offering a user-friendly way to fetch IPv4 addresses without delving into the lower-level details of network interface enumeration and IP address filtering.

## Purpose
The primary purpose of NetAddrUtils is to facilitate the development of Java applications that require network communication within a Local Area Network (LAN). It is particularly useful for:

- Network diagnostics and monitoring tools.
- Server-client applications that operate within a LAN.
- IoT (Internet of Things) applications that need to identify devices within the network.
- Multiplayer games that use local networking for player connections.

## Features
- Easy retrieval of the active IPv4 address of a machine.
- Filtering of non-relevant network interfaces (e.g., loopback, virtual, inactive interfaces).
- Simple API, making network programming accessible to developers without in-depth knowledge of Java's networking capabilities.

## Getting Started
### Prerequisites
Java JDK 21 or later
### Installation
To use NetAddrUtils in your project, include the following in your project's build file:

#### Maven
```shell
<dependency>
    <groupId>ltd.clearsolutions.networkaddressutils</groupId>
    <artifactId>networkaddressutils</artifactId>
    <version>1.0</version>
</dependency>
```

#### Gradle:
```shell

 implementation 'ltd.clearsolutions.networkaddressutils:networkaddressutils:1.0.0'
```

## Usage
Using NetAddrUtils is straightforward. Here's a quick example to get the IPv4 address of your machine:

```java
public static void main(String[] args) {
        IpAddressHelper ipHelper = new IpAddressHelper();
        String ipAddress = ipHelper.getMachineIpAddressInInternalNetwork();
        System.out.println("Machine IP Address: " + ipAddress);
    }
```

## Contributing
We welcome contributions to NetAddrUtils! If you have suggestions for improvements or encounter any issues, please feel free to open an issue or submit a pull request.

## License
NetAddrUtils is released under the MIT License. See the LICENSE file for more details.




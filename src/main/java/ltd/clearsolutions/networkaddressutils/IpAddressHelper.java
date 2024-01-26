package ltd.clearsolutions.networkaddressutils;

import ltd.clearsolutions.networkaddressutils.exception.NetworkInterfaceException;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class IpAddressHelper {

    /**
     * This method retrieves the IPv4 address of the machine in the internal network.
     * It enumerates all network interfaces and their addresses, filters out loopback and inactive interfaces,
     * and returns the IPv4 address of the first Ethernet interface found.
     *
     * @return The IPv4 address of the machine in the internal network.
     * @throws RuntimeException if the LAN IP address cannot be found.
     */
    public String getMachineIpAddressInInternalNetwork() {
        try {
            //Enumerate all network interfaces and their addresses
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // Filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || ! iface.isUp()) {
                    continue;
                }
                //lo0 = loopback
                //gif0 = Software Network Interface
                //stf0 = 6to4 tunnel interface
                //en0 = Ethernet 0
                //fw0 = Firewire
                //en1 = Ethernet 1
                //vmnet8 = Virtual Interface
                //vmnet1 = Virtual Interface
                if(! iface.getDisplayName().contains("en")){
                    continue;
                }

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (isIPv4Address(addr)) {
                        //TODO add logs
                        System.out.println(iface.getDisplayName() + " " + addr.getHostAddress());
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (UnknownHostException | SocketException e) {
            throw new NetworkInterfaceException("Failed to get machine IP address", e);
        }
        throw new NetworkInterfaceException("Can not find the LAN IP address");

    }

    /**
     * For communication between two devices on the same local network, you would typically use the IPv4 address. This is because:
     * - Compatibility: IPv4 is still the most widely used protocol, and all local network devices are likely to support it without any configuration needed.
     * - Simplicity: IPv4 addresses are shorter and easier to read and configure compared to IPv6 addresses.
     * - Local Network Communication: The IPv4 address you provided falls within the private IP address range commonly used for local networks, making it suitable for intra-network communication.
     */
    private boolean isIPv4Address(InetAddress address) throws UnknownHostException {
        return address instanceof Inet4Address && !address.isLoopbackAddress();
    }
}

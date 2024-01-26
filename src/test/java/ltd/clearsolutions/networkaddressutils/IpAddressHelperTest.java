package ltd.clearsolutions.networkaddressutils;

import ltd.clearsolutions.networkaddressutils.exception.NetworkInterfaceException;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import static java.util.Collections.enumeration;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class IpAddressHelperTest {

    @Test
    void getMachineIpAddressInInternalNetwork_ethernetInterfaceWithValidIPv4_returnInternalIp() throws Exception {
        InetAddress inetAddress = Mockito.mock(Inet4Address.class);
        NetworkInterface networkInterface = Mockito.mock(NetworkInterface.class);

        when(inetAddress.getHostAddress()).thenReturn("192.168.1.100");
        when(networkInterface.isLoopback()).thenReturn(false);
        when(networkInterface.isUp()).thenReturn(true);
        when(networkInterface.getInetAddresses()).thenReturn(enumeration(singletonList(inetAddress)));
        when(networkInterface.getDisplayName()).thenReturn("en0");

        try (MockedStatic<NetworkInterface> utilities = Mockito.mockStatic(NetworkInterface.class)) {
            utilities.when(NetworkInterface::getNetworkInterfaces).thenReturn(enumeration(singletonList(networkInterface)));

            IpAddressHelper helper = new IpAddressHelper();
            String ipAddress = helper.getMachineIpAddressInInternalNetwork();

            assertEquals("192.168.1.100", ipAddress);
        }
    }

    @Test
    void getMachineIpAddressInInternalNetwork_loopbackInterfaceOnly_throeException() throws Exception {
        NetworkInterface networkInterface = Mockito.mock(NetworkInterface.class);

        when(networkInterface.isLoopback()).thenReturn(true);

        try (MockedStatic<NetworkInterface> utilities = Mockito.mockStatic(NetworkInterface.class)) {
            utilities.when(NetworkInterface::getNetworkInterfaces)
                     .thenReturn(enumeration(singletonList(networkInterface)));

            IpAddressHelper helper = new IpAddressHelper();

            assertThatThrownBy(helper::getMachineIpAddressInInternalNetwork)
                .isInstanceOf(NetworkInterfaceException.class)
                .hasMessageContaining("Can not find the LAN IP address");
        }
    }

    @Test
    void getMachineIpAddressInInternalNetwork_testInterfaceDown_throeException() throws Exception {
        NetworkInterface networkInterface = Mockito.mock(NetworkInterface.class);

        when(networkInterface.isLoopback()).thenReturn(false);
        when(networkInterface.isUp()).thenReturn(false);

        try (MockedStatic<NetworkInterface> utilities = Mockito.mockStatic(NetworkInterface.class)) {
            utilities.when(NetworkInterface::getNetworkInterfaces)
                     .thenReturn(enumeration(singletonList(networkInterface)));

            IpAddressHelper helper = new IpAddressHelper();

            assertThatThrownBy(helper::getMachineIpAddressInInternalNetwork)
                .isInstanceOf(NetworkInterfaceException.class)
                .hasMessageContaining("Can not find the LAN IP address");
        }
    }

    @Test
    void getMachineIpAddressInInternalNetwork_testNoIPv4AddressAvailable_throeException() throws Exception {
        InetAddress inetAddress = Mockito.mock(Inet6Address.class);
        NetworkInterface networkInterface = Mockito.mock(NetworkInterface.class);

        when(networkInterface.getDisplayName()).thenReturn("en0");
        when(networkInterface.isLoopback()).thenReturn(false);
        when(networkInterface.isUp()).thenReturn(true);
        when(networkInterface.getInetAddresses()).thenReturn(
            enumeration(singletonList(inetAddress)));

        try (MockedStatic<NetworkInterface> utilities = Mockito.mockStatic(NetworkInterface.class)) {
            utilities.when(NetworkInterface::getNetworkInterfaces)
                     .thenReturn(enumeration(singletonList(networkInterface)));

            IpAddressHelper helper = new IpAddressHelper();

            assertThatThrownBy(helper::getMachineIpAddressInInternalNetwork)
                .isInstanceOf(NetworkInterfaceException.class)
                .hasMessageContaining("Can not find the LAN IP address");
        }
    }

    @Test
    void testSocketExceptionHandling() {
        try (MockedStatic<NetworkInterface> utilities = Mockito.mockStatic(NetworkInterface.class)) {
            utilities.when(NetworkInterface::getNetworkInterfaces).thenThrow(SocketException.class);

            IpAddressHelper helper = new IpAddressHelper();

            assertThatThrownBy(helper::getMachineIpAddressInInternalNetwork)
                .isInstanceOf(NetworkInterfaceException.class)
                .hasMessageContaining("Failed to get machine IP address");
        }
    }
}
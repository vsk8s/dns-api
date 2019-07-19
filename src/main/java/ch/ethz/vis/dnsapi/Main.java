package ch.ethz.vis.dnsapi;

import ch.ethz.vis.dnsapi.netcenter.types.ARecord;

import javax.xml.bind.JAXB;
import java.io.StringReader;

public class Main {
    public static void main(String[] args) {
        //Retrofit r = new Retrofit.Builder()
        //        .baseUrl("https://www.netcenter.ethz.ch/netcenter/rest/")
        //        .build();
        testXML();
    }

    public static void testXML() {
        String test =
                "  <usedIp>\n" +
                "    <ip>129.132.240.13</ip>\n" +
                "    <ipSubnet>129.132.240.0</ipSubnet>\n" +
                "    <fqname>avalon.ethz.ch</fqname>\n" +
                "    <forward>Y</forward>\n" +
                "    <reverse>N</reverse>\n" +
                "    <ttl>600</ttl>\n" +
                "    <dhcp>Y</dhcp>\n" +
                "    <dhcpMac>00-00-00-00-00-00-00-E0</dhcpMac>\n" +
                "    <ddns>N</ddns>\n" +
                "    <isgGroup>id-kom</isgGroup>\n" +
                "    <lastDetection>2009-06-25 15:10:17.0</lastDetection>\n" +
                //"    <views>\n" +
                //"      <view>intern</view>\n" +
                //"      <view>extern</view>\n" +
                //"    </views>\n" +
                "  </usedIp>";
        ARecord ar = JAXB.unmarshal(new StringReader(test), ARecord.class);

        if (ar != null) {
            System.out.println(ar.getIp() + " " + ar.getIsgGroup());
        }

        System.out.println("Done!");
    }
}

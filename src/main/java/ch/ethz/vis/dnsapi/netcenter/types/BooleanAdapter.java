package ch.ethz.vis.dnsapi.netcenter.types;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BooleanAdapter extends XmlAdapter<String, Boolean> {
    @Override
    public Boolean unmarshal(String s) throws Exception {
        return s.equals("Y");
    }

    @Override
    public String marshal(Boolean b) throws Exception {
        return b ? "Y" : "N";
    }
}

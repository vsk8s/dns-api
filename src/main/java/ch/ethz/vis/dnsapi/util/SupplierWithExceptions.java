package ch.ethz.vis.dnsapi.util;

@FunctionalInterface
public interface SupplierWithExceptions<T, E extends Exception>  {
    T accept() throws E;
}
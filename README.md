# DNS API

A GRPC API for creating DNS records via the ETH netcenter.

# Running

The config file is expected to be passed to the java environment via
`-Dch.ethz.vis.dnsapi.config=/path/config.properties`. If none is given it
defaults to `/etc/dnsapi.properties` which is recommended for production.

## Todo

* Write unittests for the netcenter API
* Handle different possible root XML elements gracefully
* Write the GRPC server

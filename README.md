# DNS API

[![Build Status](https://img.shields.io/circleci/build/github/vsk8s/dns-api?style=flat)](https://circleci.com/gh/vsk8s/dns-api)
[![codecov](https://codecov.io/gh/vsk8s/dns-api/branch/master/graph/badge.svg)](https://codecov.io/gh/vsk8s/dns-api)
[![Release](https://img.shields.io/github/tag/vsk8s/dns-api.svg?style=flat)](https://github.com/vsk8s/dns-api/releases/latest)

A GRPC API for creating DNS records via the ETH netcenter.

# Configuration

Set the following properties in the configuration file:

```properties
ch.ethz.vis.dnsapi.netcenter.username=changeme
```

The username of your Netcenter account.

```properties
ch.ethz.vis.dnsapi.netcenter.password=changeme
```

The password of your Netcenter account.

```properties
ch.ethz.vis.dnsapi.netcenter.isgGroup=changeme
```

The ISG group you want the records to belong to.

```properties
ch.ethz.vis.dnsapi.keyFilePath=key.pem
```

The path to a PEM encoded key file for serving the API over TLS.

```properties
ch.ethz.vis.dnsapi.certFilePath=cert.pem
```
The path to a PEM encoded certificate chain (server AND CA certificate) for
serving the API over TLS.

# Running

The config file is expected to be passed to the java environment via
`-Dch.ethz.vis.dnsapi.config=/path/config.properties`. If none is given it
defaults to `/etc/dnsapi.properties` which is recommended for production.

## Todo

* Write unittests for the netcenter API

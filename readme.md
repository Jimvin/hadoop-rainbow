HRainbow - Hadoop Rainbow Table Generator
--
This is a fork of HRainbow, a simple rainbow table generator designed to do hadoop performance testing. 
The aim is to provide the ability to generate large rainbow tables on Apache Hadoop clusters.

This version current supports the following hash algorithms:
- NTLM
- MD2
- MD4
- MD5
- SHA1
- SHA224
- SHA256
- SHA384
- SHA512
- WHIRLPOOL

Usage
---
Submit the application using the hadoop command below:
```
hadoop jar hadoop-rainbow-0.0.1-SNAPSHOT.jar RainbowGenerator --rainbow.algorithm NTLM output_dir
```

Set the following configuration options to control the hash type, password length and character set as well as the number or mapper and reducer tasks.

```
  -a,--rainbow.algorithm <arg>       Digest Algorithm
  -b,--rainbow.minLength <arg>       Minimum password Length
  -c,--rainbow.charset <arg>         Password Characterset
  -e,--rainbow.maxLength <arg>       Maximum password Length
  -h,--help                          Displays help
  -m,--rainbow.mappers <arg>         Mapper Count
  -r,--rainbow.reducers <arg>        Reducer Count
```


Enhancements
---
- Fixed command line parameter handling
- Dependencies update to a more modern version of Cloudera Distribution of Apache Hadoop (CDH)
- Replaced java.security.MessageDigest with sphlib to support more digest algorithms
- Added support for generating other hash types including NTLM, MD4 and Whirlpool 

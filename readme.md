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

License
---
HRainbow is forked from https://github.com/huhlig/hadoop-rainbow. The original author does not mention any specific licensing terms.

sphlib is licensed under the following MIT-like, BSD-like open-source license

```
Copyright (c) 2007-2011  Projet RNRT SAPHIR

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
Here's the second set of example code, using the XPP2 pull parser. You can 
download the XPP2 pull parser from http://www.extreme.indiana.edu/soap/xpp/.
To compile and run the sample you just need to have the PullParser.jar file in your 
classpath (this may actually have the version number included in the XPP 
distribution, with a name like PullParser2_1_7.jar; in the full distribution from
the XPP2 site this jar is found in the build/lib directory).

The TestDriver program is hard-coded to use the enclosed trades.xml file. It
parses and processes the file, then prints a brief summary of some results. You
can modify the trades.xml file to observe changes in the results.

To run the test program with XPP2 you just need to execute:

java -cp .:PullParser.jar TestDriver

If the PullParser.jar file is located in a different directory, you'll naturally
need to use the full path to the jar.

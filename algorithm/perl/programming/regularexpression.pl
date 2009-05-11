#!/applications/cygwin/bin/perl

use warnings;
#use re "debug";
#Bestiary
print "\\,\|,\(,\),\[,\{,\^,\$,\*,\+,\?,\.";
#double parsing: interpolation, regualr expression

$rotate13="XYZdef";
$rotate13 =~ tr/a-zA-Z/n-za-mN-ZA-M/;
print "$rotate13\n";

$string="password=xyz verbose=9 score=0";
%hash = $string =~ /(\w+)=(\w+)/g;
print %hash;

#Pattern Modifers:     /i /s /m /x /o
#Matching Modifier:    /i /m /s /x /o /g /cg
#Substitution Modifier:/i /m /s /x /o /g /e

#Remove duplicate
$_="Pairs is in THE THE THE spring!";
1 while s/\b(\w+) \1\b/$1/gi;
print "\n$_";

#fancy patterns
$_ = "Paris is in THE THE the spring and we can alow that that thing to happen";
s/ \b (\w+) \s(?= \1 \b)//xgi;
print "\n$_";
#allow speical case before 'thing'
$_ = "Paris is in THE THE the spring DON DON't and we can alow that that thing to happen";
s/ \b (\w+) \s(?= \1 \b(?! '\w | \s thing) )//xgi;
print "\n$_";
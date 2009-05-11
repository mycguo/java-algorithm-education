#!/applications/cygwin/bin/perl

#Here Document
print <<EOF x 10;
the camels are coming!
EOF

print << `EOC`;
echo hi there
echo li there
EOC

print(<<"THIS",23,<<'THAT')
here is a line 
or two
THIS
more
THAT
;
#list value and arrays
@stuff = ("one", "two", "three");
$stuff = ("one", "two", "three");
print "list in scalar context: $stuff\n";
$stuff = @stuff;
print "array in scalar context: $stuff\n";

#list assignment
($a,$b) = (7,7,7);
print "a=",$a,"b=",$b,"\n";
$x = (($a,$b) = (7,7,7));
print '$x=' . $x;

#input operators
#$perl_info = qx(ps $$);
#$shell_info = qx'ps $$';
#print $perl_info . " " . $shell_info;

#while (<STDIN>) {print if ("$_" ne "EOF");}
$string="key=value key2=value2 key3";
while (($k,$v) = $string =~ m/(\w+)=(\w*)/ ) {
	print "\nkey is $k value is $v\n";
	$string =~ s/$k=$v//g;
};




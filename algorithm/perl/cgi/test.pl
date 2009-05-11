#!/applications/cygwin/bin/perl -w
  
@ARGV = ( Something, More, More );

$temp = join( $", @ARGV);

print $temp, "\n";
print "@ARGV";  
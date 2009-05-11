#!/applications/cygwin/bin/perl

use warnings;

#passing reference

sub sum {
	my ($aref) = shift @_;
	my ($total) = 0;
	foreach (@$aref) { $total += $_};
	return $total	
}

@a = (1,2,3,4);
$total = sum( \@a);
print "$total\n"
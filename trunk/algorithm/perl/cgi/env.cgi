#!/applications/cygwin/bin/perl -w

# http://www.cgi101.com/book/ch3/text.html
use strict;
use CGI qw(:standard);
use CGI::Carp qw(warningsToBrowser fatalsToBrowser);

print header;
print start_html("Environment");

for my $key (sort(keys(%ENV))) {
	print "$key = $ENV{$key}<br>";
}
print  end_html;
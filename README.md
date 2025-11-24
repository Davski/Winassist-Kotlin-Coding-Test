# Winassist-Kotlin-Coding-Test
Coding test for Winassist where I translate a function from Delphi to Kotlin  

I first started by writing pseudo-code from the Delphi function, skipping all the UI stuff.  
This will help me with the translation to Kotlin.  

I did some changes to the data class Artikel since there is a line in the Delphi code at line 246.  
In this Line Netto takes a value from Artikel.inkop where in Delphi it's a string value that can be empty.  
In Kotlin this value is defined as a double, but I am allowing it to be null and checking for it for backwards compatibility.  
This doesnt really replicate the Delphi behavior but it is similar in logic in case you get incorrect values.
To make this work correctly like the Delphi code databases that can possibly input an empty string where one expects a double.  

Since the inkop value is defined as a double in the Artikel dataclass, I will not into take account situations where inkop can be an empty string.  
It would be fairly simple to just change the dataclass to a string and use a checker if the string is a double, like Delphis IsFloatString().  
The instructions however say that I should just assume that all numeric values are doubles, so I will only check for null in this case.  
This is an intended design choice by me and will not work if the code gets a '' value in the database.  
For real world usage this will be changed appropriately, of course.  

Just to make LagerRE print things properly I made BruttoStr and NettoStr that converts Brutto and Netto to their string equivalents.
I should probably make a commit or separate branch where I change the Artikel dataclass and change Brutto and Netto to strings to mimic the Delphi code better.

I noticed an issue with the helper functions where GetInkopsPris and GetInkopsPrisVNetto have arguments in the same order, but in the Delphi-code the arguments are not.
This doesn't actually change anything since the helperf unctions given to me in Kotlin don't use the arguments, but real helper functions would need to take this into account.  
I decided to keep the original Delphi argument order for the functions there since it didn't matter.

I wanted to write unit tests, but it got a bit too late and I got a head ache so I skipped it.  
It is possible to "test" the code with the DM data class by uncommenting the two println functions right before the return of the main function.
I added a bunch of different articles and lager posts to test different combinations of products.  

To run the code simply use make and it will compile the code and execute it. make clean removes the compiled files.


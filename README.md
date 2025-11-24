# Winassist-Kotlin-Coding-Test
Coding test for Winassist where I translate a function from Delphi to Kotlin

I first started by writing pseudo-code from the Delphi function, skipping all the UI stuff.
This will help me with the translation.

I did some changes to the data class Artikel since there is a line in the Delphi code at line 246.
In this Line Netto takes a value from Artikel.inkop where in Delphi it's a string value that can be empty.
In Kotlin this value is defined as a double, but I am allowing it to be null and checking for it for backwards compatibility.
This should replicate the Delphi behavior and work with older databases that can possibly input an empty string where one expects a double.

Since the inkop value is defined as a double in the Artikel dataclass, I will not into take account situations where inkop can be an empty string.
It would be fairly simple to just change the dataclass to a string and use a checker if the string is a double, like Delphis IsFloatString().
The instructions however say that I should just assume that all numeric values are doubles, so I will only check for null in this case.
This is intended and will not work if the code gets a '' value in the database.

I am however thinking of updating this once I get the code running by assuming everything is double.
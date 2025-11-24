# Winassist-Kotlin-Coding-Test
Coding test for Winassist where I translate a function from Delphi to Kotlin

I first started by writing pseudo-code from the Delphi function, skipping all the UI stuff.
This will help me with the translation.

I did some changes to the data class Artikel since there is a line in the Delphi code at line 246.
In this Line Netto takes a value from Artikel.inkop where in Delphi it's a string value that can be empty.
In Kotlin this value is defined as a double, but I am allowing it to be null and checking for it for backwards compatibility.
This should replicate the Delphi behavior and work with older databases that can possibly input an empty string where one expects a double.

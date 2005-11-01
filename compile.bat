:START
@CLS
javac -O -target 1.1 *.java
@IF ERRORLEVEL 1 GOTO ERROR
jar Mcf rcview.jar *.class res
@EXIT
:ERROR
@PAUSE
@GOTO START
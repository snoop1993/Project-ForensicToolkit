@ECHO OFF
IF EXIST C:\ForensicsTest ECHO Folder Already Exists GOTO END 
IF NOT EXIST C:\ForensicsTest ECHO Folder does not exist, Folder Created
mkdir C:\ForensicsTest  
:END

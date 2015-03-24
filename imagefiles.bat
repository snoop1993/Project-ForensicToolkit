@ECHO OFF
cd C:\Program Files (x86)\Minimal ADB and Fastboot
adb devices 
adb shell su -c "dd if=data/data/com.sec.android.provider.logsprovider/databases/logs.db of=/sdcard/logs.db"  
ECHO Saved Calllogs to SD card   
adb pull /sdcard/logs.db C:\ForensicsTest
ECHO Saved Call logs to Computer 

adb shell su -c "dd if=data/data/com.android.providers.telephony/databases/mmssms.db of=/sdcard/mmssms.db"  
ECHO Saved Messages to SD card   
adb pull /sdcard/mmssms.db C:\ForensicsTest
ECHO Saved Messages to Computer 
 
adb shell su -c "dd if=data/data/com.android.providers.contacts/databases/contacts2.db of=/sdcard/contacts2.db"  
ECHO Saved Contacts to SD card   
adb pull /sdcard/contacts2.db C:\ForensicsTest
ECHO Saved Contacts to Computer 

adb shell su -c "dd if=data/data/com.android.email/databases/EmailProvider.db of=/sdcard/EmailProvider.db"  
ECHO Saved Emails to SD card   
adb pull /sdcard/EmailProvider.db C:\ForensicsTest
ECHO Saved Emails to Computer 

adb shell su -c "dd if=data/data/com.google.android.gm/databases/mailstore.anoopselvin@googlemail.com.db of=/sdcard/gmail.db"  
ECHO Saved Gmails to SD card   
adb pull /sdcard/gmail.db C:\ForensicsTest
ECHO Saved Gmailsto Computer 

adb shell su -c "dd if=data/data/com.android.providers.calendar/databases/calendar.db of=/sdcard/calendar.db"  
ECHO Saved CalenderEvents to SD card   
adb pull /sdcard/calendar.db C:\ForensicsTest
ECHO Saved CalenderEvents to Computer 

adb shell su -c "dd if=data/data/com.samsung.android.app.memo/databases/memo.db of=/sdcard/memo.db"  
ECHO Saved Memo/notes to SD card   
adb pull /sdcard/memo.db C:\ForensicsTest
ECHO Saved Memo/notes to Computer 

adb shell su -c "dd if=data/data/com.android.providers.downloads/databases/downloads.db of=/sdcard/Downloads.db"  
ECHO Saved Downloads to SD card   
adb pull /sdcard/Downloads.db C:\ForensicsTest
ECHO Saved Downloads to Computer 


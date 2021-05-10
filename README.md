# VMS-Project-Final-Year
In order to run this system you need to install Android Studio from: https://developer.android.com/studio

Once installed you then need to import the project. File, new, import from version control.
Then the project should download and install. 
One issue that may occur is to do with the Gradel Build. Once the project has dowloaded open the project on the left hand side and then select gradle scripts to see more folders.
Double click the first file that says build.gradle (Project:VMS-Project). 
Inside here you will see a line of code that says classpath .... gradle:4.1.2 That either needs updating to the version it tells you or you need to press file, sync project with gradle files.

Then you either need an android device with developer mode enabled. To do this follow this guide: https://www.samsung.com/uk/support/mobile-devices/how-do-i-turn-on-the-developer-options-menu-on-my-samsung-galaxy-device/

If you do not have an android device you need to install an emulator. 
Select tools, AVD Manager, Create new virtual device. We used the PIXEL 2 API 27. 
Choose the R system image on the next screen, press next and change startup orientation to landscape. 

Once you have either of these options you can run the application. If you have a physical device you need to plug it into your machine and android studio will register it at the top of the screen. 

When you have a device press the Play button where it says the device name and allow it to install. Make sure the device is landscape or you may encounter an error. 


Additonal Emulator info or installation: https://docs.expo.io/workflow/android-studio-emulator/


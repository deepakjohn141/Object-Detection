# Object-Detection

Steps to build the app.
1. Download the code
2. unzip the code
3. load the code in android studio
4. enable developer option in your android device
5. connect the android device to android studio using device manager
6. run the app
7. to run the app in the emulator,
    replace \<uses-feature android:name="android.hardware.camera" />  with \<uses-feature android:name="android.hardware.camera" android:required="false"/>

Assumptions made
1. Don't need the ui in the example app
2. maximun limit of object detect is 3
3. number of threads to run is 2
4. only need to use one tensorflow lite model

Challenges faced
1. Lastest android studio used toml file for maintainting depedencies.
    Resolved by researching and learning about it.
2. To draw boxes with mutltiple colors.
   The straight forward way didn't draw the boxes with desired colors, but resolved it by researching about the issue.

# SCP-SL-QR-Reader
Tool for easy copying<br>
This tool can read the QR code from the Remote Admin menu and copy the ID of the User to the Clipboard.

## Detectable id types:

[long]@steam

[long]@discord

[string]@northwood

## Name Detection:

If you want to get the Steam/Northwood names, you will have to add a steam api key to the config.<br>
You can get a steam api key from [here](https://steamcommunity.com/dev/apikey "https://steamcommunity.com/dev/apikey")<br>
If you don't add a key the name field won't be visible.

##

If you find any bugs, please tell me in the issues section.<br>
## Building:

If you want to build it yourself, you will need this:

[core-3.3.0.jar](https://repo1.maven.org/maven2/com/google/zxing/core/3.3.0/core-3.3.0.jar "zxing core-3.3.0.jar")

[javase-3.3.0.jar](https://repo1.maven.org/maven2/com/google/zxing/javase/3.3.0/javase-3.3.0.jar "zxing javase-3.3.0.jar")

[json-20210307.jar](https://repo1.maven.org/maven2/org/json/json/20210307/json-20210307.jar "json-20210307.jar")

# SCP-SL-QR-Reader
[![Price](https://img.shields.io/badge/price-FREE-0098f7.svg?colorB=brightgreen)](https://github.com/Playofthes/SCP-SL-QR-Reader/blob/master/LICENSE)
[![Codesize](https://img.shields.io/github/languages/code-size/Playofthes/SCP-SL-QR-Reader?colorB=orange)](https://github.com/Playofthes/SCP-SL-QR-Reader/)
[![Release](https://img.shields.io/github/v/release/Playofthes/SCP-SL-QR-Reader)](https://github.com/Playofthes/SCP-SL-QR-Reader/releases/latest)
<br>
This tool can read the QR code from the Remote Admin menu and copy the ID of the User to the Clipboard.

## Detectable id types:

[long]@steam

[long]@discord

[string]@northwood

## Name detection:

If you want to get the steam/northwood names, you will have to add a steam api key to the config.<br>
You can get a steam api key from [here](https://steamcommunity.com/dev/apikey "https://steamcommunity.com/dev/apikey")<br>
If you don't add a key the name field won't be visible.

## Clipboard text:

WARNING: If you have haved used a older version in the past (Title: "SCP reader beta"), you will have to delete the config in order to be able to use the modified clipboard text function.

If you want to change the clipboard output text, you can do so in the config file.

### Example for noting down warns:<br>
clipboardtext :  - ID: \<id\>@\<type\>\<br\> - Name: \<name\>\<br\> - Reason:

  \- ID: 12345@steam<br>
  \- Name: Playofthes<br>
  \- Reason:
  
##

If you find any bugs, please tell me in the issues section.<br>
## Building:

If you want to build it yourself, you will need this:

[core-3.3.0.jar](https://repo1.maven.org/maven2/com/google/zxing/core/3.3.0/core-3.3.0.jar "zxing core-3.3.0.jar")

[javase-3.3.0.jar](https://repo1.maven.org/maven2/com/google/zxing/javase/3.3.0/javase-3.3.0.jar "zxing javase-3.3.0.jar")

[json-20210307.jar](https://repo1.maven.org/maven2/org/json/json/20210307/json-20210307.jar "json-20210307.jar")

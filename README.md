# CastVideos-android

CastVideos-android application shows how to cast videos from an android device in a way that is fully compliant with the Design Checklist.

## Dependencies
* cast-companion-android : can be downloaded here at https://github.com/googlecast/CastCompanionLibrary-android

## Setup Instructions
* Get a Chromecast device and set it up
* Register an application on the Developers Console (http://cast.google.com/publish). The easiest would be to use the Styled Media Receiver option there.
You will get an App ID when you finish registering your application.
* Setup the project dependencies
* Insert your App ID in the strings.xml in the res directory of the project (look for `YOUR_APP_ID` in that file)
* Compile and deploy to your Android device.

## References and How to report bugs
* [Cast Developer Documentation](http://developers.google.com/cast/)
* [Design Checklist](http://developers.google.com/cast/docs/design_checklist)
* If you find any issues with this library, please open a bug here on GitHub
* Question are answered on [StackOverflow](http://stackoverflow.com/questions/tagged/google-cast)

## How to make contributions?
Please read and follow the steps in the CONTRIBUTING.md

## License
See LICENSE

## Google+
Google Cast Developers Community on Google+ [http://goo.gl/TPLDxj](http://goo.gl/TPLDxj)

## Change List
 * Added gradle build scripts (make sure you have Android Support Repository). Since it depends on the
   CastCompanionLibrary, make sure you update build.gradle and settings.gradle to point to your CastCompanionLibrary.
 * Included a published app id in the res/values/strings.xml file so the project can be built and run without a need
   to register an app id. If you want to use your own receiver, update "app_id" in that file with your own app id.

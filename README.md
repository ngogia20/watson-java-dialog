# Dialog Java Starter Application

  The Dialog starter application in Java is a sample that demonstrates how the IBM Watson [Dialog service][service_url] works in a specific context.
<p align="center">
<img src="http://www.ibm.com/smarterplanet/us/en/ibmwatson/developercloud/img/service-gifs/dialog.gif" width="400">
</p>
**This application requires a dialog file to be preloaded. You can use the** [dialog-tool](https://github.com/watson-developer-cloud/dialog-tool) **to create a dialog.**

Give it a try! Click the button below to fork into IBM DevOps Services and deploy your own copy of this application on Bluemix.

[![Deploy to Bluemix](https://bluemix.net/deploy/button.png)](https://bluemix.net/deploy?repository=https://github.com/watson-developer-cloud/dialog-java)

## Getting started

  1. Create a Bluemix account. [Sign up][sign_up] in Bluemix or use an existing account. Watson services in beta are free to use.

  2. Download and install the [Cloud-foundry CLI][cloud_foundry] tool.

  3. Edit the `manifest.yml` file and replace `<application-name>` with a unique name for your copy of the application. The name that you specify determines the application's URL, such as `<application-name>.mybluemix.net`.

    ```none
    applications:
    - services:
      - dialog-service
      name: <application-name>
      path: webApp.war
      memory: 512M
    ```

  4. Connect to Bluemix by running the following commands in the command-line tool:

    ```sh
    $ cf api https://api.ng.bluemix.net
    $ cf login -u <your-Bluemix-ID>
    ```

  5. Create the Dialog service in Bluemix by running the following command:

    ```sh
    $ cf create-service dialog standard dialog-service
    ```

  6. Download and install the [ant][ant] compiler.

  7. Build the project by running the following command:

    ```sh
    $ ant
    ```
  
     You must use the Apache `ant` compiler to build the Java application. For information about the `ant` compiler and to download a copy for your operating system, visit ant.apache.org.

  8. Push it live by running the following command:

    ```sh
    $ cf push -p output/webApp.war
    ```

  9. Add your `DIALOG_ID` and restage the app:

    ```sh
    $ cf se <application-name> DIALOG_ID <dialog-id>
    $ cf restage <application-name>
    ```


## Running the application locally

  The application uses the WebSphere Liberty profile runtime as its server,
  so you must download and install the profile as part of the following steps.

  1. Copy the `username`, `password`, and `url` credentials from your `dialog-service` service in Bluemix to `DemoServlet.java`. To see the credentials, run the following command, where `<application-name>` is the unique name you specified:
  
    ```sh
    $ cf env <application-name>
    ```
   The following example shows credentials for the Dialog service:

    ```sh
    System-Provided:
    {
    "VCAP_SERVICES": {
      "dialog": [{
          "credentials": {
            "url": "<url>",
            "password": "<password>",
            "username": "<username>"
          },
        "label": "dialog",
        "name": "dialog-service",
        "plan": "standard"
     }]
    }
    }
    ```
  2. Install the [Liberty profile runtime][liberty] (If you are using Mac OS X, see this [guide][liberty_mac]).

  3. Create a Liberty profile server in Eclipse.

  4. Add the application to the server.

  5. Start the server.

  6. Open `http://localhost:9080/app/` to see the running application.

## Troubleshooting

  To troubleshoot your Bluemix application, the most useful source of
  information is the log files. To see them, run the following command:

  ```sh
  $ cf logs <application-name> --recent
  ```

## License

  This sample code is licensed under Apache 2.0. Full license text is available in [LICENSE](LICENSE).  
  This sample uses jQuery which is licensed under MIT.
## Contributing

  See [CONTRIBUTING](CONTRIBUTING.md).

## Open Source @ IBM

  Find more open source projects on the
  [IBM Github Page](http://ibm.github.io/).

[service_url]: http://www.ibm.com/smarterplanet/us/en/ibmwatson/developercloud/dialog.html
[cloud_foundry]: https://github.com/cloudfoundry/cli
[sign_up]: https://apps.admin.ibmcloud.com/manage/trial/bluemix.html?cm_mmc=WatsonDeveloperCloud-_-LandingSiteGetStarted-_-x-_-CreateAnAccountOnBluemixCLI
[liberty]: https://developer.ibm.com/wasdev/downloads/
[liberty_mac]: http://www.stormacq.com/how-to-install-websphere-8-5-liberty-profile-on-mac/
[ant]: http://ant.apache.org/bindownload.cgi

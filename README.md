# storm

[![GitHub Workflow Status](https://img.shields.io/github/workflow/status/fungover/storm/Java%20CI%20with%20Maven)](https://github.com/fungover/storm/actions/workflows/maven.yml)
[![pull requests](https://img.shields.io/github/issues-pr/fungover/storm)](https://github.com/fungover/storm/pulls)
[![issues](https://img.shields.io/github/issues/fungover/storm)](https://github.com/fungover/storm/issues)
[![Qodana](https://github.com/fungover/storm/actions/workflows/code_quality.yml/badge.svg)](https://github.com/fungover/storm/actions/workflows/code_quality.yml)
![coverage](../badges/jacoco.svg)
![branches coverage](../badges/branches.svg)
[![GitHub release](https://img.shields.io/github/v/release/fungover/storm)](https://github.com/fungover/storm/releases)
![Jokes Card](https://readme-jokes.vercel.app/api)


A simple webserver implementation

### **INSTRUCTIONS FOR HTTPS**

1. Create a keystore.jks self-signed certificate
    - Create the following directory: `etc/storm/ssl`
    - Open a terminal window in this directory
    - Run the following command:
        - `keytool -genkeypair -keyalg RSA -alias org.fungover.keystore -keystore keystore.jks -storepass password -dname "cn=Storm, ou=Java21, o=Fungover, c=SE"`
    - This will create the keystore.jks self-signed certificate file in the etc/storm/ssl directory
2. Set config.json file located in `config/`
    - Set `"port": 8443`
    - Set `"type": "https"`
3. Connect to server
    - Use a browser
        - Connect to `https://localhost:8443`
        - There will be a certificate warning since we are using a self-signed certificate
            - Click `Advanced` and choose to continue to the site anyway
    - Use Insomnia
        - Go to `Application/Preferences -> Request / Response`
            - Make sure Validate Certificates is **UN-TICKED**
        - Connect to `https://localhost:8443`
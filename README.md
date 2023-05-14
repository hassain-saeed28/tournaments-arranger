# Tournaments Arranger

## How to Run

### Backend

1. Modify line `18` in [pom.xml](pom.xml) file according to your java major version.

    You can use the following command to check you java version:

    ```powershell
    java --version
    ```

    For example, if you are using java 17, then modify that line to be:

    ```xml
    <java.version>17</java.version>
    ```

2. Run the following command in PowerShell:

    ```powershell
    del *.db; start .\mvnw.cmd spring-boot:run
    ```

### Frontend

1. Install flutter with SDK of a less `3.0.0` version from <https://dart.dev/get-dart/archive>.

2. Get the path from where flutter SDK is installed using the command:

    ```powershell
    flutter doctor -v
    ```

3. Remove the file `flutter_tools.stamp` inside `flutter\bin\cache`.

4. Open `flutter\packages\flutter_tools\lib\src\web\chrome.dart` file.

5. Find `--disable-extensions` and add `--disable-web-security` after it.

6. Install dependencies using the following command in PowerShell:

    ```powershell
    flutter pub get
    ```

7. Run the app using the following command in PowerShell:

    ```powershell
    flutter run -d chrome --no-sound-null-safety
    ```

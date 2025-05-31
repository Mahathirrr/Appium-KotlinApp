# ElektroniCare Mobile App - Automation Testing dengan Appium

**Dibuat oleh: Muhammad Mahathir (2208107010056)**

Proyek ini berisi automation testing untuk aplikasi mobile ElektroniCare menggunakan framework Appium dengan bahasa pemrograman Java dan pattern Page Object Model.

## Deskripsi Aplikasi

ElektroniCare adalah aplikasi mobile berbasis Android yang menyediakan layanan perbaikan perangkat elektronik. Aplikasi ini memungkinkan pengguna untuk:

- Melihat splash screen dan onboarding untuk pengenalan aplikasi
- Melakukan registrasi akun baru dengan validasi form yang lengkap
- Melakukan login menggunakan email/password atau Google Sign-In
- Mengakses dashboard dengan berbagai layanan yang tersedia
- Menjelajahi layanan perbaikan yang tersimpan di Firebase
- Membuat booking layanan perbaikan dengan upload gambar perangkat
- Melihat riwayat layanan perbaikan yang pernah dilakukan
- Mengelola profil pengguna

## Struktur Proyek

```
Appium-KotlinApp/
├── src/
│   ├── main/java/com/elektronicare/
│   │   ├── pages/              # Page Object Model classes
│   │   │   ├── BasePage.java
│   │   │   ├── SplashPage.java
│   │   │   ├── OnboardingPage.java
│   │   │   ├── WelcomePage.java
│   │   │   ├── RegisterPage.java
│   │   │   ├── LoginPage.java
│   │   │   ├── DashboardPage.java
│   │   │   ├── ServicesPage.java
│   │   │   ├── BookingPage.java
│   │   │   └── HistoryPage.java
│   │   ├── utils/              # Utility classes
│   │   │   ├── TestUtils.java
│   │   │   ├── ExtentReportManager.java
│   │   │   └── DriverManager.java
│   │   └── config/             # Configuration classes
│   │       └── AppiumConfig.java
│   └── test/
│       ├── java/com/elektronicare/
│       │   ├── tests/          # Test classes
│       │   │   ├── BaseTest.java
│       │   │   ├── OnboardingTest.java
│       │   │   ├── WelcomeTest.java
│       │   │   ├── RegisterTest.java
│       │   │   ├── LoginTest.java
│       │   │   ├── DashboardTest.java
│       │   │   ├── HistoryTest.java
│       │   │   └── ProfileTest.java
│       │   └── stepdefinitions/ # Cucumber step definitions
│       │       ├── OnboardingSteps.java
│       │       ├── WelcomeSteps.java
│       │       └── RegisterSteps.java
│       └── resources/
│           ├── features/       # Cucumber feature files
│           └── config/         # Test configuration files
├── reports/                   # Test reports
├── screenshots/              # Test screenshots
├── app-debug.apk            # APK file aplikasi
├── appium-inspector-config.json # Konfigurasi Appium Inspector
├── pom.xml                  # Maven dependencies
└── README.md               # Dokumentasi ini
```

## Prasyarat Sistem

### 1. Java Development Kit (JDK)
- Install JDK 17 atau versi yang lebih baru
- Set JAVA_HOME environment variable

```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-17
set PATH=%JAVA_HOME%\bin;%PATH%

# macOS/Linux
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
export PATH=$JAVA_HOME/bin:$PATH
```

### 2. Android SDK
- Install Android SDK melalui Android Studio atau command line tools
- Set ANDROID_HOME environment variable
- Tambahkan platform-tools ke PATH
```bash
# Windows
set ANDROID_HOME=C:\Users\%USERNAME%\AppData\Local\Android\Sdk
set PATH=%ANDROID_HOME%\platform-tools;%ANDROID_HOME%\tools;%PATH%

# macOS/Linux
export ANDROID_HOME=$HOME/Android/Sdk
export PATH=$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools:$PATH
```

### 3. Node.js dan Appium Server
```bash
# Install Node.js (versi 16 atau lebih baru)
# Download dari https://nodejs.org/

# Install Appium
npm install -g appium

# Install Appium drivers
appium driver install uiautomator2

# Verifikasi instalasi
appium --version
appium driver list
```

### 4. Android Emulator atau Device Fisik
```bash
# Untuk emulator, buat AVD melalui Android Studio atau command line:
avdmanager create avd -n test_device -k "system-images;android-30;google_apis;x86_64"

# Untuk device fisik:
# 1. Aktifkan Developer Options
# 2. Aktifkan USB Debugging
# 3. Hubungkan device dan verifikasi: adb devices
```

## Instalasi dan Konfigurasi

### 1. Clone Repository
```bash
git clone https://github.com/Mahathirrr/Appium-KotlinApp.git
cd Appium-KotlinApp
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Konfigurasi Appium
Edit file `src/main/java/com/elektronicare/config/AppiumConfig.java` sesuai dengan environment Anda:

```java
// Device configuration
capabilities.setCapability("platformName", "Android");
capabilities.setCapability("deviceName", "Your_Device_Name");
capabilities.setCapability("platformVersion", "Your_Android_Version");
capabilities.setCapability("app", "/path/to/app-debug.apk");
capabilities.setCapability("appPackage", "com.example.elektronicarebeta1");
capabilities.setCapability("appActivity", "com.example.elektronicarebeta1.SplashActivity");
```

### 4. Start Appium Server
```bash
# Start Appium server
appium

# Atau dengan port spesifik
appium -p 4723

# Dengan logging detail
appium --log-level debug
```

### 5. Verifikasi Setup
```bash
# Check connected devices
adb devices

# Check Appium server status
curl http://localhost:4723/wd/hub/status
```

## Menjalankan Tests

### 1. Menjalankan Semua Tests
```bash
mvn test
```

### 2. Menjalankan Test Berdasarkan Group
```bash
# Smoke tests
mvn test -Dgroups=smoke

# Functional tests
mvn test -Dgroups=functional

# UI tests
mvn test -Dgroups=ui
```

### 3. Menjalankan Test Class Tertentu
```bash
mvn test -Dtest=OnboardingTest
mvn test -Dtest=RegisterTest
mvn test -Dtest=LoginTest
mvn test -Dtest=DashboardTest
```

### 4. Menjalankan Tests dengan Script
```bash
# Linux/macOS
./run-tests.sh

# Windows
run-tests.bat

# Dengan logging
./run-tests-with-log.sh
```

## Test Cases yang Tersedia

### 1. Onboarding Tests
- **testOnboardingPageDisplay**: Verifikasi tampilan halaman onboarding
- **testOnboardingNavigation**: Test navigasi menggunakan tombol Next
- **testOnboardingSwipeNavigation**: Test navigasi menggunakan swipe gesture
- **testOnboardingSkip**: Test fungsi skip onboarding
- **testOnboardingComplete**: Test menyelesaikan onboarding

### 2. Welcome Screen Tests
- **testWelcomePageDisplay**: Verifikasi tampilan welcome screen
- **testWelcomePageElements**: Test elemen UI welcome screen
- **testNavigateToRegister**: Test navigasi ke halaman register
- **testNavigateToLogin**: Test navigasi ke halaman login

### 3. Registration Tests
- **testRegisterPageDisplay**: Verifikasi tampilan halaman register
- **testRegisterValidation**: Test validasi form register
- **testInvalidEmailValidation**: Test validasi email tidak valid
- **testPasswordVisibilityToggle**: Test toggle visibility password
- **testValidRegistration**: Test registrasi dengan data valid
- **testGoogleSignInButton**: Test tombol Google Sign-In

### 4. Login Tests
- **testLoginPageElements**: Verifikasi elemen-elemen di halaman login
- **testValidLogin**: Test login dengan kredensial yang valid
- **testInvalidLogin**: Test login dengan kredensial yang tidak valid
- **testEmptyFieldsLogin**: Test login dengan field kosong
- **testPasswordVisibility**: Test toggle visibility password
- **testGoogleSignIn**: Test Google Sign-In functionality

### 5. Dashboard Tests
- **testDashboardElements**: Verifikasi elemen-elemen dashboard
- **testServiceCardNavigation**: Test navigasi melalui service cards
- **testQuickActionButtons**: Test tombol quick action
- **testBottomNavigation**: Test bottom navigation
- **testWelcomeMessage**: Test tampilan welcome message

### 6. History Tests
- **testHistoryPageElements**: Verifikasi elemen-elemen halaman history
- **testFilterFunctionality**: Test fungsi filter
- **testHistoryItemInteraction**: Test interaksi dengan item history
- **testEmptyHistoryState**: Test state ketika history kosong

## Page Object Model

Proyek ini menggunakan Page Object Model (POM) pattern untuk maintainability yang lebih baik:

### BasePage
Class dasar yang berisi method-method umum seperti:
- `waitForElement()`: Menunggu element muncul
- `clickElement()`: Click element dengan wait
- `sendKeys()`: Input text dengan wait
- `isElementDisplayed()`: Check visibility element
- `performSwipe()`: Swipe gesture
- `scrollToElement()`: Scroll ke element

### Page Classes
Setiap halaman aplikasi memiliki class tersendiri yang mewarisi dari BasePage:
- **OnboardingPage**: Interaksi dengan halaman onboarding
- **WelcomePage**: Interaksi dengan welcome screen
- **RegisterPage**: Form registration dengan validasi
- **LoginPage**: Halaman login dan authentication
- **DashboardPage**: Halaman utama aplikasi
- **HistoryPage**: Halaman riwayat layanan

## Utilities

### TestUtils
Berisi utility methods untuk:
- Screenshot capture dengan timestamp
- Advanced wait strategies
- Element interactions dengan retry
- Test data generation
- Device gesture handling

### ExtentReportManager
Mengelola reporting dengan Extent Reports:
- HTML report generation dengan screenshots
- Test status tracking dan categorization
- Step-by-step logging
- Failure analysis

### DriverManager
Mengelola Appium driver instance:
- Driver initialization dengan capabilities
- Driver cleanup dan session management
- Singleton pattern implementation

## Reporting

Test reports akan di-generate di folder `reports/`:
- **ExtentReport.html**: Detailed HTML report dengan screenshots
- **TestNG reports**: Default TestNG reports
- **Cucumber reports**: Cucumber HTML reports (jika menggunakan Cucumber)
- **Screenshots**: Failure dan step screenshots
- **Logs**: Detailed execution logs

## Teknologi yang Digunakan

- **Java 17**: Bahasa pemrograman utama
- **Appium 9.3.0**: Mobile automation framework
- **Selenium 4.25.0**: WebDriver untuk automation
- **TestNG 7.10.2**: Testing framework
- **ExtentReports 5.1.2**: Reporting framework
- **Maven**: Build dan dependency management
- **Cucumber**: BDD testing framework (opsional)

## Best Practices

1. **Page Object Model**: Gunakan POM untuk maintainability
2. **Explicit Waits**: Gunakan WebDriverWait untuk stability
3. **Test Data Management**: Pisahkan test data dari test logic
4. **Error Handling**: Implement proper exception handling
5. **Screenshots**: Capture screenshots untuk debugging
6. **Test Independence**: Setiap test harus independent
7. **Clean Test Data**: Cleanup test data setelah execution

## Troubleshooting

### Common Issues

1. **Appium Server Connection**
   ```bash
   # Check Appium server status
   curl http://localhost:4723/wd/hub/status
   
   # Restart Appium server
   pkill -f appium
   appium
   ```

2. **Device/Emulator Issues**
   ```bash
   # Check connected devices
   adb devices
   
   # Restart ADB server
   adb kill-server && adb start-server
   
   # Start emulator
   emulator -avd test_device
   ```

3. **Element Not Found**
   - Increase wait timeouts di AppiumConfig
   - Verify element locators menggunakan Appium Inspector
   - Check app state dan navigation flow
   - Use dynamic waits instead of Thread.sleep

4. **App Installation Issues**
   ```bash
   # Verify APK
   aapt dump badging app-debug.apk
   
   # Manual install
   adb install app-debug.apk
   
   # Check app permissions
   adb shell pm list permissions com.example.elektronicarebeta1
   ```

## Kontribusi

1. Fork repository
2. Create feature branch: `git checkout -b feature/new-test`
3. Commit changes: `git commit -am 'Add new test'`
4. Push to branch: `git push origin feature/new-test`
5. Create Pull Request

### Code Standards
- Follow Java naming conventions
- Add JavaDoc untuk public methods
- Include test descriptions dan priorities
- Update documentation untuk new features

## Kontak

**Muhammad Mahathir (2208107010056)**
- GitHub: https://github.com/Mahathirrr/Appium-KotlinApp

## Lisensi

Proyek ini dibuat untuk keperluan pembelajaran dan pengembangan automation testing pada aplikasi mobile.
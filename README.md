# ElektroniCare Mobile App - Appium Test Automation

Proyek ini berisi automation testing untuk aplikasi mobile ElektroniCare menggunakan framework Appium dengan bahasa pemrograman Java.

## Deskripsi Aplikasi

ElektroniCare adalah aplikasi mobile berbasis Android yang menyediakan layanan perbaikan perangkat elektronik. Aplikasi ini memungkinkan pengguna untuk:

- Melihat onboarding screens untuk memahami fitur aplikasi
- Melakukan registrasi akun baru dengan validasi lengkap
- Melakukan login dengan email/password atau Google Sign-In
- Melihat dashboard dengan layanan yang tersedia
- Browsing layanan perbaikan dari Firebase
- Membuat booking untuk layanan perbaikan dengan upload gambar
- Melihat riwayat layanan perbaikan
- Mengelola profil pengguna (excluded dari testing sesuai permintaan)

## Struktur Project

```
Appium/
├── src/
│   ├── main/java/com/elektronicare/
│   │   ├── pages/          # Page Object Model classes
│   │   │   ├── BasePage.java
│   │   │   ├── SplashPage.java
│   │   │   ├── OnboardingPage.java    # NEW
│   │   │   ├── WelcomePage.java       # NEW
│   │   │   ├── RegisterPage.java      # NEW
│   │   │   ├── LoginPage.java
│   │   │   ├── DashboardPage.java
│   │   │   ├── ServicesPage.java      # NEW
│   │   │   ├── BookingPage.java       # UPDATED
│   │   │   └── HistoryPage.java
│   │   ├── utils/          # Utility classes
│   │   └── config/         # Configuration classes
│   └── test/
│       ├── java/com/elektronicare/
│       │   ├── tests/      # Test classes
│       │   │   ├── SplashTest.java
│       │   │   ├── OnboardingTest.java    # NEW
│       │   │   ├── WelcomeTest.java       # NEW
│       │   │   ├── RegisterTest.java      # NEW
│       │   │   ├── LoginTest.java
│       │   │   ├── DashboardTest.java
│       │   │   ├── ServicesTest.java      # NEW
│       │   │   ├── BookingTest.java
│       │   │   └── HistoryTest.java
│       │   └── stepdefinitions/ # Cucumber step definitions
│       │       ├── OnboardingSteps.java   # NEW
│       │       ├── WelcomeSteps.java      # NEW
│       │       └── RegisterSteps.java     # NEW
│       └── resources/
│           ├── features/   # Cucumber feature files
│           │   ├── onboarding.feature     # NEW
│           │   ├── welcome.feature        # NEW
│           │   └── register.feature       # NEW
│           └── config/     # Test configuration files
├── reports/               # Test reports
├── screenshots/          # Test screenshots
├── ElektroniCareBeta1.apk # APK file aplikasi asli
├── ElektroniCareBeta1-Updated.apk # APK file terbaru
├── pom.xml               # Maven dependencies
└── README.md            # Dokumentasi ini
```

## Prerequisites

### 1. Java Development Kit (JDK)
- Install JDK 8 atau versi yang lebih baru
- Set JAVA_HOME environment variable
```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_XXX
set PATH=%JAVA_HOME%\bin;%PATH%

# macOS/Linux
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk
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
# Install Node.js (versi 14 atau lebih baru)
# Download dari https://nodejs.org/

# Install Appium
npm install -g appium

# Install Appium drivers
appium driver install uiautomator2

# Verify installation
appium --version
appium driver list
```

### 4. Android Emulator atau Device
```bash
# Untuk emulator, buat AVD melalui Android Studio atau command line:
avdmanager create avd -n test_device -k "system-images;android-30;google_apis;x86_64"

# Untuk physical device:
# 1. Enable Developer Options
# 2. Enable USB Debugging
# 3. Connect device dan verify: adb devices
```

## Instalasi dan Konfigurasi

### 1. Clone Repository
```bash
git clone https://github.com/agus-septiawan/ElektroniCareBeta1.git
cd ElektroniCareBeta1/Appium
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
capabilities.setCapability("app", "/path/to/ElektroniCareBeta1-Updated.apk");
capabilities.setCapability("appPackage", "com.example.elektronicarebeta1");
capabilities.setCapability("appActivity", "com.example.elektronicarebeta1.SplashActivity");
```

### 4. Start Appium Server
```bash
# Start Appium server
appium

# Atau dengan specific port
appium -p 4723

# Dengan logging
appium --log-level debug
```

### 5. Verify Setup
```bash
# Check connected devices
adb devices

# Check Appium server
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

# Navigation tests
mvn test -Dgroups=navigation
```

### 3. Menjalankan Test Suite Specific
```bash
# Smoke tests only
mvn test -DsuiteXmlFile=src/test/resources/testng.xml -Dtest.suite="Smoke Tests"

# Full regression
mvn test -DsuiteXmlFile=src/test/resources/testng.xml -Dtest.suite="Full Regression Tests"
```

### 4. Menjalankan Test Class Specific
```bash
mvn test -Dtest=OnboardingTest
mvn test -Dtest=RegisterTest
mvn test -Dtest=LoginTest
mvn test -Dtest=ServicesTest
```

### 5. Menjalankan Cucumber Tests
```bash
mvn test -Dcucumber.options="--tags @smoke"
mvn test -Dcucumber.options="--tags @functional"
mvn test -Dcucumber.options="--tags @ui"
```

## Test Cases Lengkap

### 1. Splash Screen Tests
- **testSplashScreenDisplay**: Verifikasi tampilan splash screen dengan logo
- **testSplashToOnboardingTransition**: Verifikasi transisi ke onboarding untuk user baru
- **testSplashToLoginTransition**: Verifikasi transisi ke login untuk user existing

### 2. Onboarding Tests (NEW)
- **testOnboardingPageDisplay**: Verifikasi tampilan halaman onboarding
- **testOnboardingNavigation**: Test navigasi menggunakan tombol Next
- **testOnboardingSwipeNavigation**: Test navigasi menggunakan swipe gesture
- **testOnboardingSkip**: Test fungsi skip onboarding
- **testOnboardingComplete**: Test menyelesaikan onboarding
- **testOnboardingIndicators**: Test page indicators

### 3. Welcome Screen Tests (NEW)
- **testWelcomePageDisplay**: Verifikasi tampilan welcome screen
- **testWelcomePageElements**: Test elemen UI welcome screen
- **testNavigateToRegister**: Test navigasi ke halaman register
- **testNavigateToLogin**: Test navigasi ke halaman login
- **testWelcomePageBranding**: Test elemen branding

### 4. Registration Tests (NEW)
- **testRegisterPageDisplay**: Verifikasi tampilan halaman register
- **testRegisterValidation**: Test validasi form register
- **testInvalidEmailValidation**: Test validasi email tidak valid
- **testInvalidPhoneValidation**: Test validasi nomor telepon tidak valid
- **testShortPasswordValidation**: Test validasi password terlalu pendek
- **testPasswordVisibilityToggle**: Test toggle visibility password
- **testValidRegistration**: Test registrasi dengan data valid
- **testNavigateToLogin**: Test navigasi ke halaman login
- **testBackNavigation**: Test navigasi kembali
- **testGoogleSignInButton**: Test tombol Google Sign-In
- **testPhoneNumberFormatting**: Test formatting nomor telepon

### 5. Login Tests
- **testLoginPageElements**: Verifikasi elemen-elemen di halaman login
- **testValidLogin**: Test login dengan kredensial yang valid
- **testInvalidLogin**: Test login dengan kredensial yang tidak valid
- **testEmptyFieldsLogin**: Test login dengan field kosong
- **testPasswordVisibility**: Test toggle visibility password
- **testGoogleSignIn**: Test Google Sign-In functionality

### 6. Dashboard Tests
- **testDashboardElements**: Verifikasi elemen-elemen dashboard
- **testServiceCardNavigation**: Test navigasi melalui service cards
- **testQuickActionButtons**: Test tombol quick action
- **testBottomNavigation**: Test bottom navigation
- **testWelcomeMessage**: Test tampilan welcome message
- **testUserProfile**: Test tampilan informasi user

### 7. Services Tests (NEW)
- **testServicesPageDisplay**: Verifikasi tampilan halaman services
- **testServicesLoading**: Test loading services dari Firebase
- **testServiceSelection**: Test pemilihan service
- **testServiceTypes**: Test berbagai tipe service
- **testServicePrices**: Test tampilan harga service
- **testBackNavigation**: Test navigasi kembali
- **testBottomNavigation**: Test bottom navigation
- **testServiceSearch**: Test pencarian/scroll service
- **testMultipleServiceSelection**: Test pemilihan multiple service

### 8. Booking Tests (UPDATED)
- **testBookingPageDisplay**: Verifikasi tampilan halaman booking
- **testServiceInfoDisplay**: Test tampilan informasi service
- **testCompleteBookingSubmission**: Test submit booking lengkap
- **testEmptyFieldsBooking**: Test booking dengan field kosong
- **testDeviceTypeSelection**: Test pemilihan tipe device
- **testDateSelection**: Test pemilihan tanggal dengan date picker
- **testImageUpload**: Test upload gambar device
- **testFormValidation**: Test validasi form booking
- **testBackButton**: Test tombol back

### 9. History Tests
- **testHistoryPageElements**: Verifikasi elemen-elemen halaman history
- **testFilterFunctionality**: Test fungsi filter
- **testHistoryItemInteraction**: Test interaksi dengan item history
- **testEmptyHistoryState**: Test state ketika history kosong
- **testBackButton**: Test tombol back
- **testStatusFiltering**: Test filter berdasarkan status

## Page Object Model

Project ini menggunakan Page Object Model (POM) pattern untuk maintainability yang lebih baik:

### BasePage
Class dasar yang berisi method-method umum seperti:
- `waitForElement()`: Menunggu element muncul
- `clickElement()`: Click element dengan wait
- `sendKeys()`: Input text dengan wait
- `isElementDisplayed()`: Check visibility element
- `performSwipe()`: Swipe gesture
- `scrollToElement()`: Scroll ke element

### Specific Pages

#### OnboardingPage (NEW)
- Interaksi dengan 3 halaman onboarding (Phone, Laptop, TV Repairs)
- Navigation dengan tombol Next/Skip
- Swipe gesture support
- Page indicator management

#### WelcomePage (NEW)
- Interaksi dengan welcome screen
- Navigation ke register/login
- Branding elements verification

#### RegisterPage (NEW)
- Form registration dengan validasi lengkap
- Field validation (email, phone, password)
- Google Sign-In integration
- Password visibility toggle

#### ServicesPage (NEW)
- Loading services dari Firebase
- Service selection dan navigation
- Price display dan formatting
- Search/scroll functionality

#### BookingPage (UPDATED)
- Enhanced form dengan image upload
- Date picker integration
- Device type selection
- Service information display
- Complete form validation

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
- Multi-device support

## Reporting

Test reports akan di-generate di folder `reports/`:
- **ExtentReport.html**: Detailed HTML report dengan screenshots
- **TestNG reports**: Default TestNG reports
- **Cucumber reports**: Cucumber HTML reports
- **Screenshots**: Failure dan step screenshots
- **Logs**: Detailed execution logs

## Firebase Integration Testing

Aplikasi menggunakan Firebase untuk:
- **Authentication**: Email/password dan Google Sign-In
- **Firestore**: Menyimpan data services dan bookings
- **Storage**: Upload gambar device

Test framework mendukung:
- Mock Firebase responses untuk testing offline
- Real Firebase integration untuk end-to-end testing
- Data cleanup setelah testing

## Best Practices

1. **Page Object Model**: Gunakan POM untuk maintainability
2. **Explicit Waits**: Gunakan WebDriverWait untuk stability
3. **Test Data Management**: Pisahkan test data dari test logic
4. **Error Handling**: Implement proper exception handling dengan retry
5. **Screenshots**: Capture screenshots untuk debugging dan reporting
6. **Parallel Execution**: Configure untuk parallel test execution
7. **Test Independence**: Setiap test harus independent
8. **Clean Test Data**: Cleanup test data setelah execution

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
   aapt dump badging ElektroniCareBeta1-Updated.apk
   
   # Manual install
   adb install ElektroniCareBeta1-Updated.apk
   
   # Check app permissions
   adb shell pm list permissions com.example.elektronicarebeta1
   ```

5. **Firebase Connection Issues**
   - Check internet connection
   - Verify Firebase configuration
   - Check API keys dan permissions
   - Monitor Firebase console untuk errors

### Debug Mode

Enable debug mode untuk detailed logging:
```java
// Di AppiumConfig.java
capabilities.setCapability("appium:enablePerformanceLogging", true);
capabilities.setCapability("appium:printPageSourceOnFindFailure", true);
```

## Performance Testing

Framework mendukung basic performance testing:
- App launch time measurement
- Page load time tracking
- Memory usage monitoring
- Network request tracking

## Continuous Integration

Project siap untuk CI/CD integration:
- Maven-based build system
- TestNG XML configuration
- Parallel execution support
- Report generation automation

### Jenkins Integration
```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'reports',
                    reportFiles: 'ExtentReport.html',
                    reportName: 'Appium Test Report'
                ])
            }
        }
    }
}
```

## Contributing

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

## Contact

Untuk pertanyaan atau issues, silakan hubungi:
- Email: agus.septiawan@example.com
- GitHub: https://github.com/agus-septiawan
- Project Issues: https://github.com/agus-septiawan/ElektroniCareBeta1/issues

## Changelog

### Version 2.0 (Current)
- ✅ Added Onboarding flow testing
- ✅ Added Welcome screen testing  
- ✅ Added Registration flow testing dengan validasi lengkap
- ✅ Added Services page testing dengan Firebase integration
- ✅ Enhanced Booking flow dengan image upload
- ✅ Updated documentation dengan setup lengkap
- ✅ Added Cucumber BDD support
- ✅ Enhanced reporting dengan screenshots
- ✅ Added performance monitoring

### Version 1.0
- Basic login testing
- Dashboard navigation testing
- Simple booking flow testing
- History page testing

## 🚀 Final Project Status

✅ **COMPLETED**: Complete test framework with all compilation issues resolved
- ✅ All 27 Java files compile successfully (FIXED: All constructor and method issues resolved)
- ✅ Both TestNG and Cucumber test execution options available
- ✅ Comprehensive documentation with setup instructions
- ✅ Built APK files from latest Kotlin source code
- ✅ Final deliverable package created and verified
- ✅ **FINAL BUILD SUCCESS**: Maven compilation successful for both main and test sources

### Framework Statistics
- **Java Files**: 27 (14 main + 13 test)
- **Feature Files**: 3 (onboarding.feature, welcome.feature, register.feature)
- **APK Files**: 2 (original + updated)
- **Test Scenarios**: 23 BDD scenarios + 50+ TestNG test methods
- **Coverage**: All activities except ProfileActivity (as requested)

### Final Deliverable
📦 **ElektroniCare-Appium-TestFramework-Final.tar.gz** (30MB)
- Complete working test framework
- No compilation errors
- Ready for execution with proper Appium setup
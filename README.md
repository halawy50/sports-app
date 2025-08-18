
# Any Challenger | هل من منافس 🏆


https://github.com/user-attachments/assets/870a8a2c-aadc-45fb-a9c8-c3ae78219f97


An Android application built with Kotlin that allows users to create, manage, and participate in various challenges. The app follows Clean Architecture principles with MVVM pattern and uses Jetpack Compose for modern UI development.

## 🔗 Live API
- **Base URL**: `https://anychallenger-fbda9ed74e45.herokuapp.com`
- **API Documentation**: [https://any-challenger-api.halawy.xyz/](https://any-challenger-api.halawy.xyz/)
- **Download Demo**: [https://any-challenger-app.halawy.xyz/](https://any-challenger-app.halawy.xyz/)

## 🚀 Features

- **Challenge Management**: Create, update, and delete challenges
- **User Authentication**: Secure login/register with OTP verification
- **Challenge Discovery**: Browse and search for challenges created by other users
- **Profile Management**: Complete user profile with personal information
- **Multi-language Support**: Arabic and English language support
- **Location Services**: City and Governorate selection
- **Real-time Updates**: Live challenge updates
- **Modern UI**: Built with Jetpack Compose for smooth user experience

## 🏗️ Architecture

This project follows **Clean Architecture** principles with **MVVM** pattern:

### Architecture Layers

```
📱 Presentation Layer (UI)
    ├── Jetpack Compose Screens
    ├── ViewModels
    └── Navigation
    
🔄 Domain Layer (Business Logic)
    ├── Use Cases
    ├── Repository Interfaces
    └── Models
    
💾 Data Layer
    ├── Repository Implementations
    ├── API Services
    └── Local Storage
```

## 📂 Project Structure

```
app/
├── data/
│   ├── apiService/                 # API Service Interfaces
│   │   ├── AuthService.kt
│   │   ├── ChallengeService.kt
│   │   ├── CityAndGovernorateService.kt
│   │   └── TokenService.kt
│   ├── local/                      # Local Data Management
│   │   ├── LocalManager.kt
│   │   └── TokenManager.kt
│   └── repository/                 # Repository Implementations
│       ├── AddNewChallengeRepositoryImpl.kt
│       ├── ChallengesUserRepositoryImpl.kt
│       ├── CityAndGovernorateRepositoryImpl.kt
│       ├── DeleteChallengeRepositoryImpl.kt
│       ├── HomeChallengesRepositoryImpl.kt
│       ├── InformationUserRepositoryImpl.kt
│       ├── LoginRepositoryImpl.kt
│       ├── LogOutRepositoryImpl.kt
│       ├── RegisterRepositoryImpl.kt
│       ├── ResetPasswordRepositoryImpl.kt
│       ├── SendOTPRepositoryImpl.kt
│       ├── TokenRepositoryImpl.kt
│       ├── UpdateChallengeRepositoryImpl.kt
│       └── VerifyCodeRepositoryImpl.kt
│
├── domain/
│   ├── model/                      # Data Models
│   │   ├── challenge_model/
│   │   │   ├── challenge_model.kt
│   │   │   ├── ChallengeRequest.kt
│   │   │   ├── ChallengeUserResponse.kt
│   │   │   ├── FilterRequest.kt
│   │   │   ├── PagedResult.kt
│   │   │   └── ResponseData.kt
│   │   ├── generate_otp/
│   │   │   ├── GenerateOTPRequest.kt
│   │   │   └── GenerateOTPResponse.kt
│   │   ├── login_model/
│   │   │   ├── LoginRequest.kt
│   │   │   └── LoginResponse.kt
│   │   ├── register_model/
│   │   │   ├── RegisterRequest.kt
│   │   │   └── RegisterResponse.kt
│   │   ├── reset_password/
│   │   │   ├── NewPasswordAndOtpRequest.kt
│   │   │   └── NewPasswordAndOtpResponse.kt
│   │   ├── token_model/
│   │   │   ├── AccessToken.kt
│   │   │   └── Token.kt
│   │   └── verify_otp/
│   │       ├── VerifyOTPRequest.kt
│   │       └── VerifyOTPResponse.kt
│   │   ├── City.kt
│   │   ├── EntryModel.kt
│   │   ├── Gender.kt
│   │   ├── Governorate.kt
│   │   ├── InformationUser.kt
│   │   ├── LogOutResponse.kt
│   │   ├── NavItem.kt
│   │   ├── OnBoardingModel.kt
│   │   ├── OtpData.kt
│   │   ├── PreviewDataUser.kt
│   │   └── WrongVerify.kt
│   ├── repository/                 # Repository Interfaces
│   │   ├── AddNewChallengeRepository.kt
│   │   ├── ChallengesUserRepository.kt
│   │   ├── CityAndGovernorateRepository.kt
│   │   ├── DeleteChallengeRepository.kt
│   │   ├── HomeChallengesRepository.kt
│   │   ├── InformationUserRepository.kt
│   │   ├── LoginRepository.kt
│   │   ├── LogOutRepository.kt
│   │   ├── RegisterRepository.kt
│   │   ├── ResetPasswordRepository.kt
│   │   ├── SendOTPRepository.kt
│   │   ├── TokenRepository.kt
│   │   ├── UpdateChallengeRepository.kt
│   │   └── VerifyCodeRepository.kt
│   └── usecase/                    # Use Cases (Business Logic)
│       ├── AddNewChallengeUseCase.kt
│       ├── ChallengesUserUseCase.kt
│       ├── CityAndGovernorateUseCase.kt
│       ├── DeleteChallengeUseCase.kt
│       ├── HomeChallengesUseCase.kt
│       ├── InformationUserUseCase.kt
│       ├── LoginUseCase.kt
│       ├── LogOutUseCase.kt
│       ├── RegisterUseCase.kt
│       ├── ResetPasswordUseCase.kt
│       ├── SendOTPUseCase.kt
│       ├── TokenUseCase.kt
│       ├── UpdateChallengeUseCase.kt
│       └── VerifyCodeUseCase.kt
│
└── presentation/
    ├── components/                 # Reusable UI Components
    │   ├── ButtonsComponents/
    │   │   ├── ButtonFill.kt
    │   │   └── ButtonWithBorder.kt
    │   ├── HomeComponents/
    │   │   ├── CardHomePageShimmer.kt
    │   │   ├── EmptyChallenges.kt
    │   │   ├── HeaderHome.kt
    │   │   ├── ItemChallenger.kt
    │   │   └── LoadingShimmer.kt
    │   ├── InputsComponents/
    │   │   ├── DropDawnSelect.kt
    │   │   ├── InputEmail.kt
    │   │   ├── InputNumber.kt
    │   │   ├── InputPassword.kt
    │   │   ├── InputText.kt
    │   │   ├── LongText.kt
    │   │   └── MultiSelectDropdown.kt
    │   └── components/
    │       ├── AlertDialog.kt
    │       ├── BottomSheetComponent.kt
    │       ├── GifFromDrawable.kt
    │       ├── HeaderText.kt
    │       ├── HeaderTopBar.kt
    │       ├── HideStatusBar.kt
    │       ├── ItemOnBoarding.kt
    │       ├── LoadingDialog.kt
    │       ├── ParagraphText.kt
    │       └── SnackBar.kt
    ├── constant/                   # Constants & Configuration
    │   ├── routes/
    │   │   ├── Routes.kt
    │   │   └── RoutesAuth.kt
    │   └── constant/
    │       ├── BaseUrl.kt
    │       ├── ChangeLanguage.kt
    │       ├── constant_list.kt
    │       ├── LocalData.kt
    │       └── onboarding_list.kt
    ├── di/                         # Dependency Injection Modules
    │   ├── auth_module/
    │   │   ├── RepositoryAuthModule.kt
    │   │   └── UseCaseAuthModule.kt
    │   ├── challenge_module/
    │   │   ├── ChallengeRepositoryModule.kt
    │   │   └── UseCaseChallengeModule.kt
    │   ├── city_and_governorate_module/
    │   │   ├── RepositoryCityAndGovernorateModule.kt
    │   │   └── UseCaseCityAndGovernorateModule.kt
    │   ├── token_module/
    │   │   ├── RepositoryTokenModule.kt
    │   │   └── UseCaseTokenModule.kt
    │   └── NetworkModule.kt
    ├── navController/              # Navigation Controllers
    │   ├── AuthNavController.kt
    │   └── NavController.kt
    ├── screens/                    # Application Screens
    │   ├── authScreen/
    │   │   ├── ForgetPasswordPage.kt
    │   │   ├── LoginPage.kt
    │   │   ├── ResetPasswordPage.kt
    │   │   ├── SignUpPage.kt
    │   │   └── VerifyOTPPage.kt
    │   ├── ChangeLanguageScreen.kt
    │   ├── main/
    │   │   ├── MainScreen.kt
    │   │   └── pages/
    │   │   │   ├── HomePage.kt
    │   │   │   ├── MessagePage.kt
    │   │   │   └── setting_page/
    │   │   │       ├── SettingPage.kt
    │   │   │       └── page/
    │   │   │           ├── AddNewChallengePage.kt
    │   │   │           ├── ChallengesUserPage.kt
    │   │   │           ├── InformationUserPage.kt
    │   │   │           ├── LanguagePage.kt
    │   │   │           ├── PrivacyPolicyPage.kt
    │   │   │           └── UpdateChallengePage.kt
    │   ├── onBoardingScreen/
    │   │   │   └── OnBoardingScreen.kt
    │   │   └── splashScreen/
    │   │       └── SplashScreen.kt
    └── viewmodel/                  # ViewModels (MVVM)
        ├── AddNewChallengeViewModel.kt
        ├── ChallengesUserViewModel.kt
        ├── CityAndGovernorateViewModel.kt
        ├── DeleteChallengeViewModel.kt
        ├── FilterViewModel.kt
        ├── HomeChallengesViewModel.kt
        ├── InformationUserViewModel.kt
        ├── LocalManagerViewModel.kt
        ├── LoginViewModel.kt
        ├── LogOutViewModel.kt
        ├── MainScreenViewModel.kt
        ├── RegisterViewModel.kt
        ├── ResetPasswordViewModel.kt
        ├── SendOTPViewModel.kt
        ├── SplashScreenViewModel.kt
        ├── TokenManagerViewModel.kt
        ├── UpdateChallengeViewModel.kt
        └── VerifyOTPViewModel.kt
```

## 🔄 User Flow

### Authentication Flow
```
Splash Screen → OnBoarding → Login/Register → OTP Verification → Home Screen
```

### Challenge Management Flow
```
Home → View Challenges → Create/Update Challenge → Challenge Details → Participation
```

### Complete User Journey
```
App Launch
├── Splash Screen (Check authentication)
├── OnBoarding (First time users)
└── Authentication
    ├── Login
    │   ├── Enter Credentials
    │   └── Success → Home Screen
    ├── Register
    │   ├── Enter User Info
    │   └── Success → Home Screen
    ├── Forget Password
    │   ├── Enter Email And Send OTP
    │   ├── Verify OTP
    │   ├── Create New Password
    │   └── Success → Login Page

            
Main Application
├── Home Page
│   ├── Browse Challenges
│   ├── Search & Filter
├── Challenge Management
│   ├── Create New Challenge
│   ├── Update Challenge
│   └── Delete Challenge
└── Settings
    ├── View Profile
    ├── My Challenges
    ├── Language Selection
    ├── Privacy Policy
    └── Logout
```

## 🛠️ Tech Stack

### Frontend (Android)
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: Clean Architecture + MVVM
- **Dependency Injection**: Hilt/Dagger
- **Navigation**: Compose Navigation
- **State Management**: ViewModel + StateFlow/LiveData
- **Network**: Retrofit + OkHttp
- **Local Storage**: Room Database / SharedPreferences
- **Async Operations**: Coroutines + Flow

### Backend
- **Framework**: Ktor (Kotlin)
- **API Documentation**: Available at the base URL
- **Hosting**: Custom server deployment

## 📱 Key Features Implementation

### 1. Authentication System
- **Login/Register** with email validation
- **OTP Verification** for secure registration
- **Password Reset** functionality
- **Token Management** for session handling

### 2. Challenge Management
- **CRUD Operations** for challenges
- **Real-time Updates** using API calls
- **Image Upload** support
- **Category Filtering** and search

### User Profile
- **View Profile** information and details
- **Location Services** (City/Governorate)
- **Multi-language** preferences
- **Privacy Settings**

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Kotlin 1.8+
- Android SDK 24+
- Internet connection for API calls

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/halawy50/Any-Challenger-App.git
   cd Any-Challenger-App
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory

3. **Configure API Base URL**
   ```kotlin
   // In BaseUrl.kt
   object BaseUrl {
       const val BASE_URL = "https://anychallenger-fbda9ed74e45.herokuapp.com/"
   }
   ```

4. **Build and Run**
   - Sync the project
   - Build the APK
   - Run on emulator or physical device

## 🔧 Configuration

### API Configuration
The app connects to the open-source Ktor backend:
```kotlin
object BaseUrl {
    const val BASE_URL = "https://anychallenger-fbda9ed74e45.herokuapp.com/"
}
```

For complete API documentation, visit: [https://any-challenger-api.halawy.xyz/](https://any-challenger-api.halawy.xyz/)

### Language Support
```kotlin
object ChangeLanguage {
    const val ARABIC = "ar"
    const val ENGLISH = "en"
}
```

## 📋 API Endpoints

### Authentication
- `POST /login` - User login
- `POST /register` - User registration
- `POST /verify_code` - Verify OTP code
- `POST reset_password` - Reset password
- `POST /logout` - User logout

### Token Management
- `POST /generate_access_token` - Generete New Access Token Use Refresh Token
- `POST /verify_access_token` - Verify Access Token

### Challenge Management
- `GET /challenges/total_page` - Get count total page
- `GET /challenges/{page}` - Get all challenges
- `POST /new_challenge` - Create new challenge
- `GET /challenge/{postId}` - Get challenge by ID
- `GET /challenges/total_page/{user_id}` - Get total count challenges specific user
- `GET /challenges_user/{user_id}/{page}` - Get all challenges  specific user
- `PUT /update_challenge/{postId}` - Update challenge
- `DELETE /delete_challenge/{postID}` - Delete challenge
- `GET /filter_challenges/{page}` - Filter Challenger

### Location Services
- `GET /governorates` - Get All Governorates
- `GET /cities/{governorateId}` - Get Cities based on Governorates

### User Profile
- `GET /information_user/{user_id}` - Get user profile information


## 🎨 UI Components

### Reusable Components
- **ButtonFill** - Primary action buttons
- **ButtonWithBorder** - Secondary action buttons
- **InputEmail/Password/Text** - Form input fields
- **DropDownSelect** - Selection components
- **AlertDialog** - Confirmation dialogs
- **LoadingDialog** - Loading states
- **SnackBar** - Toast messages

### Screens
- **Authentication Screens**: Login, Register, OTP, Reset Password
- **Main Screens**: Home, Profile, Settings
- **Challenge Screens**: Create, Update, View, My Challenges
- **Utility Screens**: Onboarding, Splash, Language Selection

## 🧪 Testing

Run tests using:
```bash
./gradlew test
```

Run instrumentation tests:
```bash
./gradlew connectedAndroidTest
```

## 🏗️ Build Variants

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

## 📦 Deployment

### Generate APK
```bash
./gradlew assembleRelease
```

### Generate AAB (Play Store)
```bash
./gradlew bundleRelease
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable names
- Add comments for complex logic
- Maintain Clean Architecture principles

## 📱 Screenshots

*Add screenshots of your app here showing key features*

## 🌐 Internationalization

The app supports:
- **Arabic (العربية)** - RTL layout support
- **English** - Default language

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**halawy50**
- GitHub: [@halawy50](https://github.com/halawy50)
- API Documentation: [Any Challenger API Docs](https://any-challenger-api.halawy.xyz/)
- Backend hosted on: Heroku

## 🙏 Acknowledgments

- Jetpack Compose team for the modern UI toolkit
- Ktor framework for the powerful backend
- Open source community for inspiration and resources

## 📞 Support

For support and questions:
1. Check the [Issues](https://github.com/halawy50/Any-Challenger-App/issues) page
2. Create a new issue with detailed information
3. Contact through GitHub profile

## 🔄 Version History

- **v1.0.0** - Initial release with core features
- **Future Updates** - Enhanced UI, more challenge types, social features

---

⭐ **If you find this project helpful, please consider giving it a star!** ⭐

🚀 **Ready to take on any challenge? Download and start challenging!** 🚀

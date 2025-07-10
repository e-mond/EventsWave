# EventWave Setup Guide 🚀

## Quick Start

### Prerequisites
- **Android Studio** Hedgehog (2023.1.1) or later
- **JDK 8** or higher
- **Android SDK** with API 24+ (Android 7.0)
- **Device/Emulator** running Android 7.0+

### 5-Minute Setup

1. **Clone the Repository**
   ```bash
   git clone [repository-url]
   cd EventWave
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Click "Open an Existing Project"
   - Navigate to the EventWave folder
   - Click "OK"

3. **Sync Project**
   - Wait for Gradle sync to complete
   - If prompted, download missing SDK components
   - Ensure Build Tools 34.0.0 is installed

4. **Run the App**
   - Click the green "Run" button (▶️)
   - Select your device/emulator
   - Wait for installation

## 🎉 You're Ready!

The app will launch with:
- ✅ 5 sample events pre-loaded
- ✅ Beautiful event discovery interface
- ✅ Complete 3-step booking flow
- ✅ Payment simulation and QR tickets

## 🧪 Testing the Features

### Event Browsing
- Scroll through the event grid
- Try the search functionality
- Use category and city filters

### Complete Booking Flow
1. **Tap any event card** → View event details
2. **Click "Book Now"** → Select ticket quantity
3. **Continue to Payment** → Choose payment method
4. **Process Payment** → See success screen with QR ticket

## 📱 Supported Features

### ✅ Working Features
- Event discovery and filtering
- Event detail views with rich information
- 3-step booking process
- Multiple payment methods
- QR ticket generation
- Error handling and loading states
- Dark/light mode support

### 🚧 Coming Soon
- User ticket dashboard
- Organizer event creation
- Bottom navigation
- Real QR code scanning

## 🛠️ Development Notes

### Project Structure
```
app/src/main/kotlin/com/eventwave/app/
├── data/          # Room database, repositories
├── di/            # Hilt dependency injection
├── navigation/    # Navigation routes
├── ui/
│   ├── screens/   # All app screens
│   ├── components/# Reusable UI components
│   ├── theme/     # Material 3 theming
│   └── viewmodel/ # Screen ViewModels
```

### Key Technologies
- **Jetpack Compose** - Modern UI toolkit
- **Hilt** - Dependency injection
- **Room** - Local database
- **Navigation Compose** - Screen navigation
- **Material 3** - Design system

### Sample Data
The app automatically populates with realistic sample data on first launch. No additional setup required!

## 🐛 Troubleshooting

### Common Issues

**Gradle Sync Failed**
- Check internet connection
- Try `File > Invalidate Caches and Restart`

**Build Errors**
- Ensure Android SDK 34 is installed
- Check that Kotlin version matches `build.gradle.kts`

**App Crashes on Launch**
- Check device API level (minimum 24)
- Clear app data and restart

### Performance Tips
- Use hardware acceleration on emulator
- Enable R8 minification for release builds
- Test on physical device for best performance

## 🤝 Contributing

The codebase follows Android development best practices:
- MVVM architecture with Clean Architecture principles
- Reactive programming with Coroutines and Flow
- Type-safe navigation with Navigation Compose
- Comprehensive error handling

Ready to add features? Check out `PROJECT_SUMMARY.md` for the development roadmap!

---

**Happy coding! 🎪✨**
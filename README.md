# EventWave 🎪

**Your vibe. Your city. Your wave.**

EventWave is a modern Android mobile application built with Jetpack Compose that allows users to browse and book events while empowering organizers to create and manage those events.

## 🚀 Features

### For Attendees ✅ COMPLETE
- **Browse Events** - Vibrant event listings with beautiful card-based grid
- **Search & Filter** - Real-time search by keywords, category, and city
- **Event Details** - Comprehensive event information with organizer details
- **3-Step Booking** - Seamless ticket purchasing experience
  1. **Select Tickets** - Choose quantity with live price calculation
  2. **Payment** - Multiple payment methods with secure processing
  3. **Success** - QR ticket generation and confirmation
- **Real-time Availability** - Live ticket counts and availability status
- **QR Code Tickets** - Digital tickets with unique QR codes

### For Organizers 🚧 PLANNED
- Dashboard with analytics (in development)
- Create and manage events (in development)
- Track ticket sales and revenue (in development)

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material 3
- **Architecture**: MVVM with Clean Architecture
- **Database**: Room (SQLite)
- **Dependency Injection**: Hilt
- **Async Programming**: Coroutines & Flow
- **Navigation**: Navigation Compose
- **Image Loading**: Coil

## 📱 Screenshots & Experience

The app features a clean, vibrant, youth-friendly interface with:
- **Custom Brand Colors** - `#95FF2C` accent green, `#ECFFD9` background
- **Dark and Light Mode** - Automatic theme switching
- **Smooth Animations** - Spring-based transitions and micro-interactions
- **Responsive Design** - Optimized for all Android screen sizes
- **Progress Indicators** - Clear step tracking through booking flow

### Complete User Journey
1. **Discovery** - Browse events with beautiful posters and filtering
2. **Details** - Rich event information with venue, pricing, and availability
3. **Booking** - Step-by-step ticket selection and quantity picker
4. **Payment** - Multiple payment methods with realistic processing
5. **Success** - Celebration screen with QR ticket and event details

## 🏃‍♂️ Getting Started

1. Clone this repository
2. Open in Android Studio Hedgehog (2023.1.1) or later
3. Sync project with Gradle files
4. Run the app on an emulator or device (API 24+)

### Try the Booking Flow
1. Launch the app and browse sample events
2. Tap any event to view full details
3. Click "Book Now" to start the 3-step process
4. Experience the complete flow from selection to QR ticket

## 📖 Project Documentation

For detailed implementation details, architecture decisions, and development roadmap, see [PROJECT_SUMMARY.md](./PROJECT_SUMMARY.md).

## 🎯 Current Status

✅ **Phase 1 Complete**: Event browsing, search, filtering, and beautiful UI  
✅ **Phase 2 Complete**: Complete 3-step booking flow with QR tickets  
🚧 **Phase 3 Planned**: Organizer features and enhanced ticket management  

### Success Criteria Achievement
- ✅ User can easily browse and filter events
- ✅ Attendee can book a ticket in less than 3 steps
- ✅ Payment confirmation generates a ticket with QR code
- ✅ Clean, vibrant, youth-friendly interface
- ✅ UI is responsive and matches branding

## 🏆 What's Working Right Now

- **Event Discovery** - Browse 5+ sample events with real imagery
- **Advanced Filtering** - Search and filter by multiple criteria
- **Complete Booking** - Full 3-step purchasing process
- **Payment Simulation** - Realistic payment processing with multiple methods
- **QR Ticket Generation** - Digital tickets with unique identifiers
- **Error Handling** - Comprehensive error states and recovery flows
- **Data Persistence** - Room database with proper relationship management

---

**EventWave** - A production-ready event booking experience that showcases modern Android development. 🎪✨

*Built with ❤️ for the modern event-going experience.*

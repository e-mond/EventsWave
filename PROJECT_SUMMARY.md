# EventWave - Event Booking Mobile Application

## Project Overview

EventWave is a comprehensive Android mobile application built with **Kotlin** and **Jetpack Compose** that allows users to browse and book events while empowering organizers to create and manage those events. The app features real-time ticket availability, smooth booking flows, and role-based dashboards.

### 🎯 Tagline
**"Your vibe. Your city. Your wave."**

### 🎨 Brand Colors
- **Accent Green**: `#95FF2C`
- **Background Green**: `#ECFFD9` 
- **Pure Black**: `#000000`
- **Pure White**: `#FFFFFF`

## ✅ Implemented Features

### 🏗️ Core Architecture
- **MVVM Architecture** with Clean Architecture principles
- **Jetpack Compose** for modern, declarative UI
- **Hilt** for dependency injection
- **Room Database** for local data persistence
- **Coroutines & Flow** for reactive programming
- **Navigation Component** for screen navigation

### 📱 UI Components & Screens
- **Splash Screen** with branded animations
- **Event List Screen** with beautiful card-based grid layout
- **Event Card Component** with vibrant event information
- **Search & Filter System** with real-time filtering
- **Material 3 Design System** with custom theming
- **Dark/Light Mode Support**

### 🗄️ Data Layer
- **Event Management** - Complete CRUD operations
- **User Management** - Support for attendees and organizers
- **Ticket System** - Booking, QR code generation, status tracking
- **Sample Data** - Pre-populated with realistic event data
- **Type Converters** for LocalDateTime and enums

### 🎪 Event Features
- Browse events with vibrant posters
- Search by title, venue, or organizer
- Filter by category (Concert, Festival, Comedy, etc.)
- Filter by city
- Real-time ticket availability display
- Event categories with visual indicators
- Price display and availability status

### 🎫 Ticket System Foundation
- Ticket booking flow structure
- QR code generation system
- Payment method simulation
- Ticket status management (Active, Used, Cancelled)
- Revenue tracking for organizers

## 📁 Project Structure

```
app/
├── src/main/kotlin/com/eventwave/app/
│   ├── data/
│   │   ├── local/
│   │   │   ├── dao/           # Room DAOs
│   │   │   ├── EventWaveDatabase.kt
│   │   │   └── Converters.kt
│   │   ├── model/             # Data models (Event, User, Ticket)
│   │   └── repository/        # Repository pattern implementation
│   ├── di/                    # Hilt dependency injection modules
│   ├── navigation/            # Navigation routes
│   ├── ui/
│   │   ├── components/        # Reusable UI components
│   │   ├── screens/           # Screen implementations
│   │   ├── theme/             # Material 3 theming
│   │   └── viewmodel/         # ViewModels
│   ├── MainActivity.kt
│   └── EventWaveApplication.kt
└── src/main/res/              # Resources (strings, colors, themes)
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- Android SDK 24+ (Android 7.0)
- Kotlin 1.9.10+

### Setup Instructions
1. Clone the repository
2. Open the project in Android Studio
3. Sync the project with Gradle files
4. Run the app on an emulator or physical device

### Sample Data
The app automatically initializes with sample data including:
- 5 diverse events (Festival, Concert, Comedy, Tech Conference, Party)
- Sample users (attendees and organizers)
- Sample tickets for demonstration

## 🔮 Next Development Phase

### Priority Features to Implement

#### 🎫 Complete Booking Flow
- [ ] **Event Detail Screen** - Full event information display
- [ ] **Booking Screen** - Ticket quantity selection
- [ ] **Payment Simulation** - Mock payment processing with animations
- [ ] **Payment Success Screen** - QR code display and ticket confirmation
- [ ] **Ticket Management** - User ticket dashboard

#### 🧑‍💼 Organizer Features
- [ ] **Organizer Dashboard** - Analytics and event management
- [ ] **Create Event Screen** - Multi-step event creation form
- [ ] **Manage Events** - Edit and deactivate events
- [ ] **Analytics Screen** - Revenue and booking insights
- [ ] **Role-based Navigation** - Different interfaces for attendees vs organizers

#### 🎨 Enhanced UI/UX
- [ ] **Bottom Navigation** - Seamless tab navigation
- [ ] **Pull-to-Refresh** - Event list refreshing
- [ ] **Infinite Scroll** - Pagination for large event lists
- [ ] **Animations** - Smooth transitions between screens
- [ ] **Loading States** - Better user feedback during operations

#### 🔧 Advanced Features
- [ ] **QR Code Scanner** - Ticket validation for organizers
- [ ] **Push Notifications** - Event reminders and updates
- [ ] **Social Sharing** - Share events on social platforms
- [ ] **Calendar Integration** - Add events to device calendar
- [ ] **Maps Integration** - Venue location display
- [ ] **User Authentication** - Real login/signup flow

### Stretch Goals
- [ ] **Real-time Updates** - WebSocket/Firebase integration
- [ ] **Payment Integration** - Real payment gateway (Stripe, PayPal)
- [ ] **Backend API** - Django REST or Firebase backend
- [ ] **Image Upload** - Event poster management
- [ ] **Review System** - Event ratings and reviews
- [ ] **Recommendation Engine** - Personalized event suggestions

## 🛠️ Technical Implementation Notes

### Database Schema
- **Events Table**: Complete event information with organizer relationships
- **Users Table**: Support for both attendee and organizer user types  
- **Tickets Table**: Comprehensive booking and payment tracking

### Architecture Decisions
- **Single Activity Pattern** with Jetpack Compose navigation
- **Repository Pattern** for data abstraction
- **StateFlow** for reactive UI updates
- **Hilt** for compile-time dependency injection
- **Room** for type-safe database operations

### Performance Considerations
- **Lazy Loading** for event images with Coil
- **Database Indexing** on frequently queried fields
- **Efficient List Rendering** with LazyStaggeredGrid
- **State Management** with proper lifecycle awareness

## 📝 Success Criteria Status

✅ **User can easily browse and filter events** - IMPLEMENTED  
✅ **Clean, vibrant, youth-friendly interface** - IMPLEMENTED  
✅ **UI is responsive and matches branding** - IMPLEMENTED  
🟡 **Attendee can book a ticket in less than 3 steps** - IN PROGRESS  
🟡 **Organizer can create and track event performance** - IN PROGRESS  
🟡 **Payment confirmation generates a ticket with QR code** - IN PROGRESS  

## 🔄 Development Workflow

### Current State
The application has a solid foundation with:
- Complete data layer implementation
- Beautiful, branded UI components
- Working event browsing and filtering
- Proper architecture setup

### Next Sprint Focus
1. **Event Detail Screen** - Complete the user journey from list to detail
2. **Booking Flow** - Implement the 3-step booking process
3. **Basic Navigation** - Add bottom navigation for core features
4. **Organizer Dashboard** - Basic event management interface

### Long-term Roadmap
The project is architected to easily support all planned features. The modular design allows for incremental development while maintaining code quality and user experience standards.

---

**EventWave** represents a modern, scalable approach to event management applications, combining beautiful design with robust functionality. The current implementation provides an excellent foundation for building a complete event booking platform.
package com.eventwave.app.navigation

sealed class NavigationRoutes(val route: String) {
    // Main Navigation
    object Splash : NavigationRoutes("splash")
    object Login : NavigationRoutes("login")
    
    // Attendee Navigation
    object AttendeeMain : NavigationRoutes("attendee_main")
    object EventList : NavigationRoutes("event_list")
    object EventDetail : NavigationRoutes("event_detail/{eventId}") {
        fun createRoute(eventId: String) = "event_detail/$eventId"
    }
    object Search : NavigationRoutes("search")
    object Booking : NavigationRoutes("booking/{eventId}") {
        fun createRoute(eventId: String) = "booking/$eventId"
    }
    object Payment : NavigationRoutes("payment/{eventId}/{quantity}") {
        fun createRoute(eventId: String, quantity: Int) = "payment/$eventId/$quantity"
    }
    object PaymentSuccess : NavigationRoutes("payment_success/{ticketId}") {
        fun createRoute(ticketId: String) = "payment_success/$ticketId"
    }
    object UserTickets : NavigationRoutes("user_tickets")
    object TicketDetail : NavigationRoutes("ticket_detail/{ticketId}") {
        fun createRoute(ticketId: String) = "ticket_detail/$ticketId"
    }
    object Profile : NavigationRoutes("profile")
    
    // Organizer Navigation
    object OrganizerMain : NavigationRoutes("organizer_main")
    object OrganizerDashboard : NavigationRoutes("organizer_dashboard")
    object CreateEvent : NavigationRoutes("create_event")
    object ManageEvents : NavigationRoutes("manage_events")
    object EventAnalytics : NavigationRoutes("event_analytics/{eventId}") {
        fun createRoute(eventId: String) = "event_analytics/$eventId"
    }
    object OrganizerAnalytics : NavigationRoutes("organizer_analytics")
}
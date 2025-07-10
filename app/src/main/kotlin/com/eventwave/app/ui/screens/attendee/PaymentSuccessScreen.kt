package com.eventwave.app.ui.screens.attendee

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.eventwave.app.data.model.Ticket
import com.eventwave.app.navigation.NavigationRoutes
import com.eventwave.app.ui.theme.AccentGreen
import com.eventwave.app.ui.theme.BackgroundGreen
import com.eventwave.app.ui.viewmodel.PaymentSuccessViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentSuccessScreen(
    ticketId: String,
    navController: NavController,
    viewModel: PaymentSuccessViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    LaunchedEffect(ticketId) {
        viewModel.loadTicket(ticketId)
    }
    
    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (uiState.error != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Error: ${uiState.error}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        navController.navigate(NavigationRoutes.AttendeeMain.route) {
                            popUpTo(NavigationRoutes.AttendeeMain.route) { inclusive = true }
                        }
                    }
                ) {
                    Text("Back to Events")
                }
            }
        }
    } else {
        uiState.ticket?.let { ticket ->
            PaymentSuccessContent(
                ticket = ticket,
                onBackToEvents = {
                    navController.navigate(NavigationRoutes.AttendeeMain.route) {
                        popUpTo(NavigationRoutes.AttendeeMain.route) { inclusive = true }
                    }
                },
                onViewTickets = {
                    navController.navigate(NavigationRoutes.UserTickets.route) {
                        popUpTo(NavigationRoutes.AttendeeMain.route)
                    }
                }
            )
        }
    }
}

@Composable
private fun PaymentSuccessContent(
    ticket: Ticket,
    onBackToEvents: () -> Unit,
    onViewTickets: () -> Unit
) {
    // Animation for success celebration
    val scale = remember { Animatable(0f) }
    val checkmarkScale = remember { Animatable(0f) }
    
    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        checkmarkScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )
    }
    
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BackgroundGreen.copy(alpha = 0.3f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        // Success Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Step indicator
            Text(
                text = "Step 3 of 3 • Complete",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Success Icon with Animation
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .scale(scale.value)
                    .background(
                        AccentGreen,
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Success",
                    modifier = Modifier
                        .size(60.dp)
                        .scale(checkmarkScale.value),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Payment Successful!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Your ticket has been generated",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
        
        // Progress indicator
        LinearProgressIndicator(
            progress = { 3f / 3f },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        )
        
        // Scrollable Content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(24.dp)
        ) {
            // QR Code Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your Ticket",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // QR Code Placeholder (In real app, use QR code library)
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant,
                                RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.QrCode,
                                contentDescription = "QR Code",
                                modifier = Modifier.size(80.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "QR Code",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Show this QR code at the venue",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Ticket ID: ${ticket.qrCode}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Ticket Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Event Details",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    TicketDetailRow(
                        label = "Event",
                        value = ticket.eventTitle
                    )
                    
                    TicketDetailRow(
                        label = "Date & Time",
                        value = ticket.eventDateTime.format(
                            DateTimeFormatter.ofPattern("MMM dd, yyyy • HH:mm", Locale.getDefault())
                        )
                    )
                    
                    TicketDetailRow(
                        label = "Venue",
                        value = ticket.eventVenue
                    )
                    
                    TicketDetailRow(
                        label = "Tickets",
                        value = "${ticket.quantity}x General Admission"
                    )
                    
                    TicketDetailRow(
                        label = "Total Paid",
                        value = "$${String.format("%.2f", ticket.totalPrice)}",
                        isHighlight = true
                    )
                    
                    TicketDetailRow(
                        label = "Payment Method",
                        value = ticket.paymentMethod.displayName
                    )
                    
                    TicketDetailRow(
                        label = "Purchased",
                        value = ticket.purchasedAt.format(
                            DateTimeFormatter.ofPattern("MMM dd, yyyy • HH:mm", Locale.getDefault())
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onViewTickets,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ConfirmationNumber,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("My Tickets")
                }
                
                Button(
                    onClick = onBackToEvents,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("More Events")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Additional Actions
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Quick Actions",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        QuickActionButton(
                            icon = Icons.Default.CalendarToday,
                            text = "Add to\nCalendar",
                            onClick = { /* TODO: Implement calendar integration */ }
                        )
                        
                        QuickActionButton(
                            icon = Icons.Default.Share,
                            text = "Share\nEvent",
                            onClick = { /* TODO: Implement sharing */ }
                        )
                        
                        QuickActionButton(
                            icon = Icons.Default.LocationOn,
                            text = "Get\nDirections",
                            onClick = { /* TODO: Implement maps integration */ }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TicketDetailRow(
    label: String,
    value: String,
    isHighlight: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.Normal,
            color = if (isHighlight) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun QuickActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .height(64.dp)
            .weight(1f),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                lineHeight = MaterialTheme.typography.labelSmall.lineHeight
            )
        }
    }
}
package com.eventwave.app.ui.screens.attendee

import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.eventwave.app.data.model.Event
import com.eventwave.app.data.model.PaymentMethod
import com.eventwave.app.navigation.NavigationRoutes
import com.eventwave.app.ui.viewmodel.PaymentViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    eventId: String,
    quantity: Int,
    navController: NavController,
    viewModel: PaymentViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    LaunchedEffect(eventId, quantity) {
        viewModel.loadPaymentData(eventId, quantity)
    }
    
    // Navigate to success screen when payment is successful
    LaunchedEffect(uiState.paymentSuccess, uiState.createdTicket) {
        if (uiState.paymentSuccess && uiState.createdTicket != null) {
            navController.navigate(
                NavigationRoutes.PaymentSuccess.createRoute(uiState.createdTicket!!.id)
            ) {
                // Clear the back stack to prevent going back to payment screen
                popUpTo(NavigationRoutes.AttendeeMain.route)
            }
        }
    }
    
    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (uiState.error != null && !uiState.isProcessingPayment) {
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
                        viewModel.clearError()
                        navController.popBackStack() 
                    }
                ) {
                    Text("Go Back")
                }
            }
        }
    } else {
        uiState.event?.let { event ->
            PaymentContent(
                event = event,
                quantity = uiState.quantity,
                totalPrice = uiState.totalPrice,
                selectedPaymentMethod = uiState.selectedPaymentMethod,
                isProcessingPayment = uiState.isProcessingPayment,
                error = uiState.error,
                onPaymentMethodSelected = viewModel::selectPaymentMethod,
                onProcessPayment = viewModel::processPayment,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
private fun PaymentContent(
    event: Event,
    quantity: Int,
    totalPrice: Double,
    selectedPaymentMethod: PaymentMethod,
    isProcessingPayment: Boolean,
    error: String?,
    onPaymentMethodSelected: (PaymentMethod) -> Unit,
    onProcessPayment: () -> Unit,
    onBackClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header with step indicator
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = "Payment",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Step 2 of 3",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = onBackClick,
                    enabled = !isProcessingPayment
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Progress indicator
        LinearProgressIndicator(
            progress = { 2f / 3f },
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            // Order Summary
            Text(
                text = "Order Summary",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(event.imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = event.title,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = event.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2
                            )
                            
                            Text(
                                text = event.dateTime.format(
                                    DateTimeFormatter.ofPattern("MMM dd, yyyy • HH:mm", Locale.getDefault())
                                ),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            Text(
                                text = event.venue,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Divider()
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Price breakdown
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Tickets (${quantity}x)",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "$${String.format("%.2f", event.ticketPrice * quantity)}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Fees",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "$0.00",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Divider()
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "$${String.format("%.2f", totalPrice)}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Payment Methods
            Text(
                text = "Payment Method",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            PaymentMethod.values().forEach { method ->
                PaymentMethodCard(
                    paymentMethod = method,
                    isSelected = selectedPaymentMethod == method,
                    onClick = { onPaymentMethodSelected(method) },
                    enabled = !isProcessingPayment
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            if (error != null) {
                Spacer(modifier = Modifier.height(16.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = error,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
        }
        
        // Pay Now Button
        Button(
            onClick = onProcessPayment,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = !isProcessingPayment,
            shape = RoundedCornerShape(16.dp)
        ) {
            if (isProcessingPayment) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Processing...",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Pay $${String.format("%.2f", totalPrice)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun PaymentMethodCard(
    paymentMethod: PaymentMethod,
    isSelected: Boolean,
    onClick: () -> Unit,
    enabled: Boolean
) {
    val borderColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outline
    }
    
    val icon = when (paymentMethod) {
        PaymentMethod.CARD -> Icons.Default.CreditCard
        PaymentMethod.MOBILE_MONEY -> Icons.Default.Phone
        PaymentMethod.PAYPAL -> Icons.Default.AccountBalance
        PaymentMethod.BANK_TRANSFER -> Icons.Default.AccountBalance
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(enabled = enabled) { onClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 4.dp else 2.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = paymentMethod.displayName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
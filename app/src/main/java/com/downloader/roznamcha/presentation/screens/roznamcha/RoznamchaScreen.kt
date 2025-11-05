package com.downloader.roznamcha.presentation.screens.roznamcha

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.downloader.roznamcha.domain.models.RozNamchaPaymentUi
import com.downloader.roznamcha.presentation.screens.roznamcha.components.SlidingTopBar
import org.koin.compose.viewmodel.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RoznamchaScreen(
    viewModel: RoznamchaViewModel = koinViewModel(),
) {
    val currentGroup by viewModel.currentGroup.collectAsState()
    val groups by viewModel.groups.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(horizontal = 5.dp),
                onClick = {
                    showDialog = true
                }
            ) {
                Text(
                    "Quick Add",
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                )
            }
        }) {

        if (showDialog) {
            RozNamchaPaymentDialog(
                onDismiss = { showDialog = false },
                onSaved = { showDialog = false })
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            SlidingTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = currentGroup?.date ?: "No entries",
                onNext = { viewModel.next() },
                onBack = { viewModel.previous() })

            Spacer(modifier = Modifier.height(8.dp))

            if (currentGroup == null) {
                // Empty state
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No payments yet",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            } else {
                // show only current group's payments
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(currentGroup!!.payments, key = { it.id }) { payment ->
                        PaymentItem(payment = payment)
                    }

                    item {
                        // small footer showing paging info
                        Text(
                            text = "Showing ${currentIndex + 1} of ${groups.size}",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentItem(payment: RozNamchaPaymentUi) {
    val backgroundColor = if (payment.isMyIncome) Color(0xFFDFF7E7) else Color(0xFFFFF0F0)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = if (payment.isMyIncome) "Income" else "Expense",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = payment.addedByEmployee,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(text = "Rs ${payment.amount}", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                // optional show time-of-day (hh:mm) if you want:
                val time = remember(payment.timeMillis) {
                    SimpleDateFormat(
                        "hh:mm a", Locale.getDefault()
                    ).format(Date(payment.timeMillis))
                }
                Text(text = time, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}


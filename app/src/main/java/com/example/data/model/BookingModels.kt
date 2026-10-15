package com.example.data.model

import java.util.UUID

enum class PitchType(
    val title: String,
    val subtitle: String,
    val description: String,
    val baseRateRsd: Int,
    val badge: String,
    val dimensions: String = "40 x 20m",
    val recommendedPlayers: String = "5-na-5 (10-12 igrača)"
) {
    MALI(
        title = "Mali Teren",
        subtitle = "5-na-5 · od 4.000 RSD",
        description = "Savršena elastična podloga za brz rekreativni 5-na-5 fudbal, tehničke vežbe i dečije mečeve.",
        baseRateRsd = 4000,
        badge = "Nova Trava 2026",
        dimensions = "40 x 20m",
        recommendedPlayers = "5-na-5 (10-12 igrača)"
    ),
    VELIKI(
        title = "Veliki Teren",
        subtitle = "Utakmice · od 6.000 RSD",
        description = "Profesionalne dimenzije, vrhunska veštačka trava 2026 i kapacitet za turnire i 7-na-7 / 8-na-8 utakmice.",
        baseRateRsd = 6000,
        badge = "Turnirski Format",
        dimensions = "60 x 40m",
        recommendedPlayers = "7-na-7 / 8-na-8 (14-18 igrača)"
    ),
    BALON(
        title = "Balon Teren",
        subtitle = "Zatvoreni teren · od 5.000 RSD",
        description = "Klimatizovana zimska dvorana sa mekanom podlogom i savršenim uslovima tokom cele godine bez obzira na vremenske prilike.",
        baseRateRsd = 5000,
        badge = "Grejanje & Klima",
        dimensions = "42 x 22m",
        recommendedPlayers = "5-na-5 ili 6-na-6"
    )
}

enum class ReservationStatus(val label: String) {
    CONFIRMED("POTVRĐENO"),
    PENDING("NA ČEKANJU"),
    CANCELLED("OTKAZANO")
}

enum class PaymentMethod(val label: String) {
    ON_SITE("Plaćanje na licu mesta (gotovina/kartica)"),
    INVOICE("Plaćanje preko računa firme (faktura)")
}

data class BookingDate(
    val label: String,      // "Danas", "Sutra", "Petak", "Subota", "Nedelja"
    val dayNumber: String,  // "15", "16", "17", "18", "19", "20"
    val dayName: String,    // "Sre", "Čet", "Pet", "Vikend", "Vikend", "Pon"
    val isWeekend: Boolean = false,
    val fullDateString: String = ""
)

data class TimeSlot(
    val time: String,
    val isOccupied: Boolean = false,
    val isClosed: Boolean = false,
    val isNightSlot: Boolean = false
)

enum class MatchDuration(val minutes: Int, val label: String, val subLabel: String, val multiplier: Double) {
    MIN_60(60, "60 min", "1 Termin", 1.0),
    MIN_90(90, "90 min", "+50% cena", 1.5),
    MIN_120(120, "120 min", "Dupli termin", 2.0)
}

data class BookingReservation(
    val id: String = "SAVA-" + UUID.randomUUID().toString().take(6).uppercase(),
    val pitchType: PitchType,
    val dateString: String,
    val timeString: String,
    val durationMins: Int,
    val totalPriceRsd: Int,
    val captainName: String,
    val phoneNumber: String,
    val note: String = "",
    val cafeTableReserved: Boolean = false,
    val ballsAndBibsIncluded: Boolean = true,
    val status: String = "POTVRĐENO",
    val paymentMethod: PaymentMethod = PaymentMethod.ON_SITE,
    val createdAtMillis: Long = System.currentTimeMillis()
)

data class CafeMenuItem(
    val id: String,
    val name: String,
    val description: String,
    val priceRsd: Int,
    val category: String,
    val badge: String? = null,
    val icon: String = "coffee"
)

data class SchoolGroup(
    val name: String,
    val ageRange: String,
    val schedule: String,
    val description: String,
    val highlight: String
)

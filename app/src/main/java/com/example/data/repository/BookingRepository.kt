package com.example.data.repository

import com.example.data.model.BookingDate
import com.example.data.model.BookingReservation
import com.example.data.model.CafeMenuItem
import com.example.data.model.MatchDuration
import com.example.data.model.PitchType
import com.example.data.model.SchoolGroup
import com.example.data.model.TimeSlot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class BookingRepository {

    val availableDates: List<BookingDate> = listOf(
        BookingDate("Danas", "15", "Sre", false, "Danas (15. Maj)"),
        BookingDate("Sutra", "16", "Čet", false, "Sutra (16. Maj)"),
        BookingDate("Petak", "17", "Pet", false, "Petak (17. Maj)"),
        BookingDate("Subota", "18", "Vikend", true, "Subota (18. Maj)"),
        BookingDate("Nedelja", "19", "Vikend", true, "Nedelja (19. Maj)"),
        BookingDate("Ponedeljak", "20", "Pon", false, "Ponedeljak (20. Maj)")
    )

    val timeSlots: List<TimeSlot> = listOf(
        TimeSlot("16:00", isOccupied = false, isClosed = false, isNightSlot = false),
        TimeSlot("17:00", isOccupied = false, isClosed = false, isNightSlot = false),
        TimeSlot("18:00", isOccupied = false, isClosed = false, isNightSlot = false),
        TimeSlot("19:00", isOccupied = true, isClosed = false, isNightSlot = true),
        TimeSlot("20:00", isOccupied = false, isClosed = false, isNightSlot = true),
        TimeSlot("21:00", isOccupied = false, isClosed = false, isNightSlot = true),
        TimeSlot("22:00", isOccupied = false, isClosed = false, isNightSlot = true),
        TimeSlot("23:00", isOccupied = false, isClosed = false, isNightSlot = true),
        TimeSlot("00:00", isOccupied = false, isClosed = true, isNightSlot = true)
    )

    val cafeMenuItems: List<CafeMenuItem> = listOf(
        CafeMenuItem(
            id = "c1",
            name = "Espresso / Macchiato",
            description = "Sa mlekom ili bez",
            priceRsd = 190,
            category = "kafa",
            badge = "100% Arabica",
            icon = "coffee"
        ),
        CafeMenuItem(
            id = "c2",
            name = "Zaječarsko Svetlo",
            description = "Hladno posle termina",
            priceRsd = 260,
            category = "pivo",
            badge = "Točeno 0.5L",
            icon = "sports_bar"
        ),
        CafeMenuItem(
            id = "c3",
            name = "Whey Proteinski Šejk",
            description = "Čokolada ili Vanila",
            priceRsd = 350,
            category = "fit",
            badge = "25g Proteina",
            icon = "fitness_center"
        ),
        CafeMenuItem(
            id = "c4",
            name = "Ceđeni Sok Miks",
            description = "Pomorandža, jabuka, đumbir",
            priceRsd = 320,
            category = "kafa",
            badge = "Sveže voće",
            icon = "nutrition"
        ),
        CafeMenuItem(
            id = "c5",
            name = "Heineken 0.33",
            description = "Flaširano & Ledeno",
            priceRsd = 310,
            category = "pivo",
            badge = "Premium",
            icon = "liquor"
        ),
        CafeMenuItem(
            id = "c6",
            name = "Isostar Sport 0.5L",
            description = "Brza rehidracija",
            priceRsd = 240,
            category = "fit",
            badge = "Izotonik",
            icon = "water_drop"
        )
    )

    val schoolGroups: List<SchoolGroup> = listOf(
        SchoolGroup(
            name = "Bambini Školica",
            ageRange = "5 – 7 godina",
            schedule = "Utorak i Četvrtak od 17:30h, Subota 10:00h",
            description = "Upoznavanje sa loptom, motorički poligoni i koordinacija kroz igru uz licencirane trenere.",
            highlight = "Prvi trening gratis"
        ),
        SchoolGroup(
            name = "Mlađi Pioniri & Petlići",
            ageRange = "8 – 11 godina",
            schedule = "Ponedeljak, Sreda i Petak od 18:30h",
            description = "Fudbalska tehnika, pas igra, postavljanje i učešće u Fer-Plej ligi Beograda.",
            highlight = "UEFA B Licenca"
        ),
        SchoolGroup(
            name = "Kadeti & Takmičari",
            ageRange = "12 – 15 godina",
            schedule = "Svaki radni dan od 19:30h",
            description = "Napredna taktička priprema, rad na snazi i brzini, priprema za seniorske lige.",
            highlight = "Turniri & Prvenstva"
        )
    )

    private val _reservations = MutableStateFlow<List<BookingReservation>>(
        listOf(
            BookingReservation(
                id = "sava-demo-1",
                pitchType = PitchType.MALI,
                dateString = "Sutra, 16. Maj",
                timeString = "20:00 - 21:00",
                durationMins = 60,
                totalPriceRsd = 4000,
                captainName = "Milan Jovanović - Ekipa Blok 45",
                phoneNumber = "+381 64 289 4410",
                note = "Treba nam 2 kompleta markera (crveni i plavi)",
                cafeTableReserved = true,
                status = "POTVRĐENO"
            )
        )
    )
    val reservations: StateFlow<List<BookingReservation>> = _reservations.asStateFlow()

    fun addReservation(reservation: BookingReservation) {
        val current = _reservations.value.toMutableList()
        current.add(0, reservation)
        _reservations.value = current
    }

    fun cancelReservation(id: String) {
        _reservations.value = _reservations.value.filterNot { it.id == id }
    }
}

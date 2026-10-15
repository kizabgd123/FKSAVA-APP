package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.BookingDate
import com.example.data.model.BookingReservation
import com.example.data.model.MatchDuration
import com.example.data.model.PitchType
import com.example.data.model.TimeSlot
import com.example.data.repository.BookingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.UUID

enum class AppScreen {
    SPLASH,
    LOGIN,
    REZERVISI,
    SKOLICA,
    KAFIC,
    MOJI_TERMINI
}

data class BookingUiState(
    val currentScreen: AppScreen = AppScreen.SPLASH,
    val selectedPitch: PitchType = PitchType.MALI,
    val selectedDate: BookingDate? = null,
    val selectedSlot: TimeSlot? = null,
    val selectedDuration: MatchDuration = MatchDuration.MIN_60,
    val cafeTableSelected: Boolean = false,
    val captainName: String = "Milan Jovanović - Ekipa Blok 45",
    val phoneNumber: String = "+381 64 289 4410",
    val bookingNote: String = "Treba nam 2 kompleta markera (crveni i plavi)",
    val isConfirmationSheetOpen: Boolean = false,
    val loginMode: String = "email", // "email" or "phone"
    val loginEmail: String = "kapiten@fksava45.rs",
    val loginPassword: String = "••••••••",
    val loginPhone: String = "",
    val isPasswordVisible: Boolean = false,
    val cafeZone: String = "kafic", // "kafic" or "terasa"
    val cafeMenuFilter: String = "all", // "all", "kafa", "pivo", "fit"
    val toastMessage: String? = null,
    val userIsLoggedIn: Boolean = false,
    val showDatePickerDialog: Boolean = false,
    val lastConfirmedReservation: BookingReservation? = null,
    val showSuccessVoucher: Boolean = false
) {
    val totalPriceRsd: Int
        get() = (selectedPitch.baseRateRsd * selectedDuration.multiplier).toInt()

    val formattedPriceRsd: String
        get() = String.format("%,d", totalPriceRsd).replace(',', '.')

    val bookingSummaryMeta: String
        get() {
            val date = selectedDate?.fullDateString ?: "Sutra (16. Maj)"
            val time = selectedSlot?.time ?: "20:00"
            return "${selectedPitch.title} · $date u $time (${selectedDuration.minutes} min)"
        }
}

class BookingViewModel(
    val repository: BookingRepository = BookingRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookingUiState())
    val uiState: StateFlow<BookingUiState> = _uiState.asStateFlow()

    val reservations: StateFlow<List<BookingReservation>> = repository.reservations

    init {
        // Select "Sutra" and 20:00 as default matching the mockups
        val defaultDate = repository.availableDates.getOrNull(1) ?: repository.availableDates.first()
        val defaultSlot = repository.timeSlots.find { it.time == "20:00" } ?: repository.timeSlots.first()
        _uiState.value = _uiState.value.copy(
            selectedDate = defaultDate,
            selectedSlot = defaultSlot
        )
    }

    fun navigateTo(screen: AppScreen) {
        _uiState.value = _uiState.value.copy(currentScreen = screen)
    }

    fun selectPitch(pitch: PitchType) {
        _uiState.value = _uiState.value.copy(selectedPitch = pitch)
    }

    fun selectDate(date: BookingDate) {
        _uiState.value = _uiState.value.copy(selectedDate = date)
    }

    fun selectSlot(slot: TimeSlot) {
        if (slot.isOccupied || slot.isClosed) return
        _uiState.value = _uiState.value.copy(selectedSlot = slot)
    }

    fun selectDuration(duration: MatchDuration) {
        _uiState.value = _uiState.value.copy(selectedDuration = duration)
    }

    fun toggleCafeTable(checked: Boolean) {
        _uiState.value = _uiState.value.copy(cafeTableSelected = checked)
    }

    fun updateCaptainName(name: String) {
        _uiState.value = _uiState.value.copy(captainName = name)
    }

    fun updatePhoneNumber(phone: String) {
        _uiState.value = _uiState.value.copy(phoneNumber = phone)
    }

    fun updateBookingNote(note: String) {
        _uiState.value = _uiState.value.copy(bookingNote = note)
    }

    fun openConfirmationSheet() {
        _uiState.value = _uiState.value.copy(isConfirmationSheetOpen = true)
    }

    fun closeConfirmationSheet() {
        _uiState.value = _uiState.value.copy(isConfirmationSheetOpen = false)
    }

    fun setLoginMode(mode: String) {
        _uiState.value = _uiState.value.copy(loginMode = mode)
    }

    fun updateLoginEmail(email: String) {
        _uiState.value = _uiState.value.copy(loginEmail = email)
    }

    fun updateLoginPassword(pass: String) {
        _uiState.value = _uiState.value.copy(loginPassword = pass)
    }

    fun updateLoginPhone(phone: String) {
        _uiState.value = _uiState.value.copy(loginPhone = phone)
    }

    fun togglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
    }

    fun setCafeZone(zone: String) {
        _uiState.value = _uiState.value.copy(cafeZone = zone)
    }

    fun setCafeMenuFilter(category: String) {
        _uiState.value = _uiState.value.copy(cafeMenuFilter = category)
    }

    fun performLogin(successMessage: String = "Uspešna prijava! Dobrodošli.") {
        _uiState.value = _uiState.value.copy(
            userIsLoggedIn = true,
            currentScreen = AppScreen.REZERVISI,
            toastMessage = successMessage
        )
    }

    fun continueAsGuest() {
        _uiState.value = _uiState.value.copy(
            currentScreen = AppScreen.REZERVISI,
            toastMessage = "Nastavljeno kao gost (pregled slobodnih termina)"
        )
    }

    fun openDatePicker() {
        _uiState.value = _uiState.value.copy(showDatePickerDialog = true)
    }

    fun closeDatePicker() {
        _uiState.value = _uiState.value.copy(showDatePickerDialog = false)
    }

    fun selectCustomDate(day: Int, monthNumber: Int, monthName: String, dayName: String) {
        val newDate = BookingDate(
            label = "$day. $monthName",
            dayNumber = day.toString(),
            dayName = dayName,
            isWeekend = dayName.contains("Sub", ignoreCase = true) || dayName.contains("Ned", ignoreCase = true),
            fullDateString = "$dayName ($day. $monthName)"
        )
        _uiState.value = _uiState.value.copy(
            selectedDate = newDate,
            showDatePickerDialog = false
        )
    }

    fun dismissSuccessVoucher() {
        _uiState.value = _uiState.value.copy(showSuccessVoucher = false)
    }

    fun confirmBooking(): BookingReservation {
        val state = _uiState.value
        val dateStr = state.selectedDate?.fullDateString ?: "Sutra, 16. Maj"
        val timeStart = state.selectedSlot?.time ?: "20:00"
        val timeStr = "$timeStart (${state.selectedDuration.minutes} min)"

        val reservation = BookingReservation(
            id = "SAVA-" + UUID.randomUUID().toString().take(6).uppercase(),
            pitchType = state.selectedPitch,
            dateString = dateStr,
            timeString = timeStr,
            durationMins = state.selectedDuration.minutes,
            totalPriceRsd = state.totalPriceRsd,
            captainName = state.captainName.ifBlank { "Ekipa Blok 45" },
            phoneNumber = state.phoneNumber.ifBlank { "+381 64 289 4410" },
            note = state.bookingNote,
            cafeTableReserved = state.cafeTableSelected,
            status = "POTVRĐENO"
        )
        repository.addReservation(reservation)
        _uiState.value = _uiState.value.copy(
            isConfirmationSheetOpen = false,
            lastConfirmedReservation = reservation,
            showSuccessVoucher = true,
            toastMessage = "Termin je uspešno rezervisan!"
        )
        return reservation
    }

    fun cancelBooking(id: String) {
        repository.cancelReservation(id)
        _uiState.value = _uiState.value.copy(toastMessage = "Termin je otkazan.")
    }

    fun clearToast() {
        _uiState.value = _uiState.value.copy(toastMessage = null)
    }

    fun getWhatsAppBookingUrl(): String {
        val state = _uiState.value
        val pitchLabel = state.selectedPitch.title
        val dateLabel = state.selectedDate?.fullDateString ?: "Sutra (16. Maj)"
        val timeLabel = state.selectedSlot?.time ?: "20:00"
        val durationLabel = "${state.selectedDuration.minutes} min"
        val priceStr = state.formattedPriceRsd

        val message = """
            Pozdrav, želim da rezervišem termin na FK SAVA 45:
            - Teren: $pitchLabel
            - Datum: $dateLabel
            - Vreme: $timeLabel ($durationLabel)
            - Kapiten: ${state.captainName}
            - Telefon: ${state.phoneNumber}
            - Cena: $priceStr RSD
            ${if (state.cafeTableSelected) "- Dodatno: Rezervisan sto u kafiću\n" else ""}${if (state.bookingNote.isNotBlank()) "- Napomena: ${state.bookingNote}\n" else ""}
            Molim za potvrdu.
        """.trimIndent()

        val encoded = URLEncoder.encode(message, StandardCharsets.UTF_8.toString())
        return "https://wa.me/381631110407?text=$encoded"
    }

    fun getWhatsAppCafeUrl(): String {
        val message = "Pozdrav, želimo da rezervišemo sto u kafiću / balkonu FK SAVA 45 za ekipu."
        val encoded = URLEncoder.encode(message, StandardCharsets.UTF_8.toString())
        return "https://wa.me/381631110407?text=$encoded"
    }

    fun getWhatsAppSchoolUrl(childName: String, age: String, parentPhone: String): String {
        val message = "Pozdrav, želim da prijavim dete za besplatan probni trening u Školi Fudbala FK SAVA 45:\n- Dete: $childName (godište/uzrast: $age)\n- Kontakt: $parentPhone"
        val encoded = URLEncoder.encode(message, StandardCharsets.UTF_8.toString())
        return "https://wa.me/381631110407?text=$encoded"
    }
}

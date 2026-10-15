package com.example.t5

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formbiodata(modifier: Modifier) {
    // state untuk menyimpan isian form
    var namaDepan by remember { mutableStateOf("") }
    var namaBelakang by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var tglLahir by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    val skillTerpilih = remember { mutableStateListOf<String>() }

    // state untuk dialog & menu
    var tampilDatePicker by remember { mutableStateOf(false) }
    var tampilMenu by remember { mutableStateOf(false) }
    var tampilHasil by remember { mutableStateOf(false) }

    // variabel penyimpan data yang ditampilkan di popup
    var hasilNama by remember { mutableStateOf("") }
    var hasilEmail by remember { mutableStateOf("") }
    var hasilTglLahir by remember { mutableStateOf("") }
    var hasilExperience by remember { mutableStateOf("") }
    var hasilSkill by remember { mutableStateOf("") }

    val daftarSkill = listOf(
        stringResource(R.string.skill_android),
        stringResource(R.string.skill_ui_ux),
        stringResource(R.string.skill_web)
    )
    val daftarExperience = stringArrayResource(R.array.experience_options)
    val formatTanggal = stringResource(R.string.date_pattern)

    // tombol aktif jika semua sudah terisi
    val formLengkap = namaDepan.isNotEmpty() && namaBelakang.isNotEmpty() &&
            email.isNotEmpty() && tglLahir.isNotEmpty() &&
            experience.isNotEmpty() && skillTerpilih.isNotEmpty()

    val bentukKartu = RoundedCornerShape(
        topStart = dimensionResource(R.dimen.card_corner),
        topEnd = dimensionResource(R.dimen.card_corner)
    )
    val bentukField = RoundedCornerShape(dimensionResource(R.dimen.corner_radius))
    val warnaField = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colorResource(R.color.purple_primary),
        unfocusedBorderColor = colorResource(R.color.grey_border),
        focusedPlaceholderColor = colorResource(R.color.grey_hint),
        unfocusedPlaceholderColor = colorResource(R.color.grey_hint),
        cursorColor = colorResource(R.color.purple_primary)
    )

    Scaffold(containerColor = colorResource(R.color.white)) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {

            // gambar header
            Image(
                painter = painterResource(R.drawable.ic_background),
                contentDescription = stringResource(R.string.cd_header_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.header_height))
            )

            // Box kartu yang mengapung di atas Scaffold
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .shadow(dimensionResource(R.dimen.card_elevation), bentukKartu)
                    .background(colorResource(R.color.white), bentukKartu)
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(R.dimen.padding_screen),
                        vertical = dimensionResource(R.dimen.padding_card)
                    ),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.header_title),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.purple_primary)
                    )
                    Text(
                        text = stringResource(R.string.header_subtitle),
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(R.color.grey_text),
                        textAlign = TextAlign.Center
                    )

                    // nama depan & nama belakang
                    Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))) {
                        OutlinedTextField(
                            value = namaDepan,
                            onValueChange = { namaDepan = it },
                            placeholder = { Text(stringResource(R.string.hint_first_name)) },
                            singleLine = true,
                            shape = bentukField,
                            colors = warnaField,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = namaBelakang,
                            onValueChange = { namaBelakang = it },
                            placeholder = { Text(stringResource(R.string.hint_last_name)) },
                            singleLine = true,
                            shape = bentukField,
                            colors = warnaField,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text(stringResource(R.string.hint_email)) },
                        singleLine = true,
                        shape = bentukField,
                        colors = warnaField,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // tanggal lahir & experience (klik ikon untuk membuka)
                    Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))) {
                        OutlinedTextField(
                            value = tglLahir,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = { Text(stringResource(R.string.hint_birth_date)) },
                            trailingIcon = {
                                Image(
                                    painter = painterResource(R.drawable.ic_kalender),
                                    contentDescription = stringResource(R.string.cd_calendar_icon),
                                    modifier = Modifier
                                        .size(dimensionResource(R.dimen.icon_size))
                                        .clickable { tampilDatePicker = true }
                                )
                            },
                            singleLine = true,
                            shape = bentukField,
                            colors = warnaField,
                            modifier = Modifier.weight(1f)
                        )
                        Box(modifier = Modifier.weight(1f)) {
                            OutlinedTextField(
                                value = experience,
                                onValueChange = {},
                                readOnly = true,
                                placeholder = { Text(stringResource(R.string.hint_experience)) },
                                trailingIcon = {
                                    Image(
                                        painter = painterResource(R.drawable.ic_dropdown),
                                        contentDescription = stringResource(R.string.cd_dropdown_icon),
                                        modifier = Modifier
                                            .size(dimensionResource(R.dimen.icon_size))
                                            .clickable { tampilMenu = true }
                                    )
                                },
                                singleLine = true,
                                shape = bentukField,
                                colors = warnaField,
                                modifier = Modifier.fillMaxWidth()
                            )
                            DropdownMenu(
                                expanded = tampilMenu,
                                onDismissRequest = { tampilMenu = false }
                            ) {
                                daftarExperience.forEach { item ->
                                    DropdownMenuItem(
                                        text = { Text(item) },
                                        onClick = {
                                            experience = item
                                            tampilMenu = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // pilihan skill
                    Text(
                        text = stringResource(R.string.label_skill),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        daftarSkill.forEach { skill ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = skill in skillTerpilih,
                                    onCheckedChange = {
                                        if (it) skillTerpilih.add(skill) else skillTerpilih.remove(skill)
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = colorResource(R.color.purple_primary),
                                        uncheckedColor = colorResource(R.color.grey_text),
                                        checkmarkColor = colorResource(R.color.white)
                                    )
                                )
                                Text(text = skill, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }

                    // tombol proceed
                    Button(
                        onClick = {
                            hasilNama = "$namaDepan $namaBelakang"
                            hasilEmail = email
                            hasilTglLahir = tglLahir
                            hasilExperience = experience
                            hasilSkill = skillTerpilih.joinToString(", ")
                            tampilHasil = true
                        },
                        enabled = formLengkap,
                        shape = bentukField,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.purple_primary),
                            contentColor = colorResource(R.color.white),
                            disabledContainerColor = colorResource(R.color.grey_disabled),
                            disabledContentColor = colorResource(R.color.white)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimensionResource(R.dimen.field_height))
                    ) {
                        Text(stringResource(R.string.btn_proceed))
                    }
                }
            }
        }
    }

    // dialog pilih tanggal
    if (tampilDatePicker) {
        val dateState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { tampilDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    dateState.selectedDateMillis?.let {
                        tglLahir = SimpleDateFormat(formatTanggal, Locale.getDefault()).format(Date(it))
                    }
                    tampilDatePicker = false
                }) { Text(stringResource(R.string.dialog_ok)) }
            },
            dismissButton = {
                TextButton(onClick = { tampilDatePicker = false }) {
                    Text(stringResource(R.string.dialog_cancel))
                }
            }
        ) {
            DatePicker(state = dateState)
        }
    }

    // popup hasil isian (card hitam seperti di FormDataDiri)
    if (tampilHasil) {
        val warnaTeksHasil = colorResource(R.color.result_card_text)
        Dialog(onDismissRequest = { tampilHasil = false }) {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = dimensionResource(R.dimen.result_card_elevation)
                ),
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.result_card_bg)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(dimensionResource(R.dimen.result_card_padding)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.result_card_spacing))
                ) {
                    Text(text = stringResource(R.string.result_name, hasilNama), color = warnaTeksHasil)
                    Text(text = stringResource(R.string.result_email, hasilEmail), color = warnaTeksHasil)
                    Text(text = stringResource(R.string.result_birth_date, hasilTglLahir), color = warnaTeksHasil)
                    Text(text = stringResource(R.string.result_experience, hasilExperience), color = warnaTeksHasil)
                    Text(text = stringResource(R.string.result_skill, hasilSkill), color = warnaTeksHasil)

                    Button(
                        onClick = { tampilHasil = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.dialog_ok))
                    }
                }
            }
        }
    }
}